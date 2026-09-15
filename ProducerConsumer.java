package producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

    // Shared buffer used by both producer and consumer
    static class SharedBuffer {
        private final Queue<Integer> buffer = new LinkedList<>();
        private final int capacity;

        SharedBuffer(int capacity) {
            this.capacity = capacity;
        }

        // Adds an item to the buffer
        public synchronized void produce(int value) throws InterruptedException {
            while (buffer.size() == capacity) {
                System.out.println("Buffer is full. Producer is waiting...");
                wait();
            }

            buffer.add(value);
            System.out.println("Produced: " + value);

            notifyAll();
        }

        // Removes an item from the buffer
        public synchronized int consume() throws InterruptedException {
            while (buffer.isEmpty()) {
                System.out.println("Buffer is empty. Consumer is waiting...");
                wait();
            }

            int value = buffer.remove();
            System.out.println("Consumed: " + value);

            notifyAll();

            return value;
        }
    }

    // Producer thread
    static class Producer extends Thread {
        private final SharedBuffer buffer;

        Producer(SharedBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.produce(i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Consumer thread
    static class Consumer extends Thread {
        private final SharedBuffer buffer;

        Consumer(SharedBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {
                    buffer.consume();
                    Thread.sleep(400);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int bufferSize = 5;

        SharedBuffer buffer = new SharedBuffer(bufferSize);

        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\nProducer-Consumer execution completed.");
    }
}