package matrixmultiplication;

import java.util.Random;

public class MatrixMultiplication {

    static final int SIZE = 100;

    public static void main(String[] args) {

        int[][] matrixA = new int[SIZE][SIZE];
        int[][] matrixB = new int[SIZE][SIZE];
        long[][] result = new long[SIZE][SIZE];

        Random random = new Random();

        // Generate random values for the matrices
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrixA[i][j] = random.nextInt(10);
                matrixB[i][j] = random.nextInt(10);
            }
        }

        System.out.println("Matrix size: " + SIZE + " x " + SIZE);
        System.out.println("Starting multithreaded matrix multiplication...");
        System.out.println("Each scalar multiplication is assigned to a virtual thread.");

        long startTime = System.currentTimeMillis();

        for (int row = 0; row < SIZE; row++) {

            for (int column = 0; column < SIZE; column++) {

                final int currentRow = row;
                final int currentColumn = column;

                long[] products = new long[SIZE];
                Thread[] multiplicationThreads = new Thread[SIZE];

                /*
                 * Each multiplication operation gets its own virtual thread.
                 */
                for (int k = 0; k < SIZE; k++) {

                    final int index = k;

                    multiplicationThreads[k] = Thread.ofVirtual().start(() -> {
                    	products[index] =
                    	        (long) matrixA[currentRow][index]
                    	        * matrixB[index][currentColumn];
                    });
                }

                /*
                 * Wait for all multiplication threads belonging
                 * to the current result element.
                 */
                for (Thread thread : multiplicationThreads) {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // Add the individual multiplication results
                long sum = 0;

                for (long product : products) {
                    sum += product;
                }

                result[row][column] = sum;
            }

            // Display progress after completing each row
            if ((row + 1) % 10 == 0) {
                System.out.println(
                        "Completed rows: " + (row + 1) + "/" + SIZE
                );
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\nMatrix multiplication completed.");
        System.out.println(
                "Total execution time: " + (endTime - startTime) + " ms"
        );

        System.out.println("\nFirst 5 x 5 elements of the result matrix:");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.printf("%8d", result[i][j]);
            }
            System.out.println();
        }
     // Simple correctness verification
        int[][] testA = {
            {1, 2},
            {3, 4}
        };

        int[][] testB = {
            {5, 6},
            {7, 8}
        };

        int[][] expected = {
            {19, 22},
            {43, 50}
        };

        boolean correct = true;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

                int sum = 0;

                for (int k = 0; k < 2; k++) {
                    sum += testA[i][k] * testB[k][j];
                }

                if (sum != expected[i][j]) {
                    correct = false;
                }
            }
        }

        if (correct) {
            System.out.println("\nCorrectness verification: PASSED");
        } else {
            System.out.println("\nCorrectness verification: FAILED");
        }
    }
}
