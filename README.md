# OS Threading Assignment

This repository contains the implementation of two Operating Systems threading problems using Java:

1. Producer-Consumer Problem
2. Multithreaded Matrix Multiplication

A Swing-based animation is also included to demonstrate the working of the matrix multiplication program.

## Technologies Used

- Java 21
- Java Threads
- Java Virtual Threads
- Java Swing
- Eclipse IDE

## 1. Producer-Consumer Problem

The Producer-Consumer problem demonstrates communication between two threads using a shared buffer.

The producer adds items to the buffer, while the consumer removes items from it. Synchronization is used to make sure that the producer does not add items when the buffer is full and the consumer does not remove items when the buffer is empty.

### Concepts Used

- Threads
- Shared buffer
- Synchronization
- `synchronized`
- `wait()`
- `notifyAll()`
- `Thread.sleep()`

## 2. Multithreaded Matrix Multiplication

The matrix multiplication program multiplies two `100 x 100` matrices.

For each element of the result matrix, the scalar multiplication operations are assigned to Java virtual threads. The individual products are then added to obtain the final value.

The program also displays the progress of the calculation and performs a small correctness verification.

### Concepts Used

- Multithreading
- Java Virtual Threads
- Matrix multiplication
- `Thread.join()`
- Thread synchronization
- Performance measurement

## 3. Matrix Multiplication Animation

A Java Swing-based animation is provided to demonstrate the execution of the multithreaded matrix multiplication.

The animation displays:

- Matrix multiplication progress
- Current operation
- Thread activity
- Completion percentage
- Final calculation status

This provides a visual demonstration of how the program progresses while the multiplication is being performed.

## Screenshots

The repository contains screenshots showing:

- Producer-Consumer program output
- Matrix multiplication output
- Matrix multiplication animation while running
- Matrix multiplication animation after completion

## Project Files

| File | Description |
|------|-------------|
| `ProducerConsumer.java` | Implementation of the Producer-Consumer problem |
| `MatrixMultiplication.java` | Multithreaded 100 x 100 matrix multiplication |
| `MatrixAnimation.java` | Swing-based visualization of matrix multiplication |
| `module-info.java` | Java module configuration |
| `producer-consumer.jpeg` | Producer-Consumer output screenshot |
| `matrix-output.jpeg` | Matrix multiplication output screenshot |
| `animation-running.jpeg` | Animation during execution |
| `animation-complete.jpeg` | Animation after completion |

## Result

Both threading programs were successfully executed using Java 21.

The matrix multiplication program completed the `100 x 100` matrix calculation successfully, and the correctness verification passed.

The animation successfully demonstrated the progress of the matrix multiplication process from the beginning to completion.

## Conclusion

This assignment demonstrates the use of Java threads and virtual threads to solve common Operating Systems problems. It also provides a visual representation of multithreaded matrix multiplication through a Java Swing animation.
