package matrixmultiplication;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class MatrixAnimation extends JPanel {

    private static final int SIZE = 100;

    private final int[][] matrixA = new int[SIZE][SIZE];
    private final int[][] matrixB = new int[SIZE][SIZE];
    private final long[][] result = new long[SIZE][SIZE];

    private volatile int currentRow = 0;
    private volatile int currentColumn = 0;
    private volatile int currentK = 0;

    private volatile long completedOperations = 0;
    private volatile boolean calculationFinished = false;

    public MatrixAnimation() {

        setPreferredSize(new Dimension(1000, 650));
        setBackground(Color.WHITE);

        generateMatrices();
    }

    private void generateMatrices() {

        Random random = new Random();

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                matrixA[i][j] = random.nextInt(10);
                matrixB[i][j] = random.nextInt(10);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Title
        g2.setFont(new Font("Arial", Font.BOLD, 28));

        g2.drawString(
                "Multithreaded Matrix Multiplication",
                250,
                50
        );

        // Matrix A
        drawMatrix(g2, 80, 120, "Matrix A");

        // Multiplication symbol
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.drawString("x", 335, 260);

        // Matrix B
        drawMatrix(g2, 420, 120, "Matrix B");

        // Equal symbol
        g2.drawString("=", 670, 260);

        // Result matrix
        drawResultMatrix(g2, 730, 120);

        // Current operation
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        g2.drawString(
                "Current operation:",
                80,
                430
        );

        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        String operation;

        if (!calculationFinished) {

            operation =
                    "A[" + currentRow + "][" + currentK
                    + "] x B[" + currentK + "]["
                    + currentColumn + "]";

        } else {

            operation = "All multiplication operations completed!";
        }

        g2.drawString(
                operation,
                300,
                430
        );

        // Thread information
        g2.setFont(new Font("Arial", Font.BOLD, 18));

        g2.drawString(
                "Thread:",
                80,
                475
        );

        g2.setFont(new Font("Arial", Font.PLAIN, 18));

        String threadStatus;

        if (!calculationFinished) {

            threadStatus =
                    "Virtual thread performing scalar multiplication";

        } else {

            threadStatus =
                    "Calculation completed";
        }

        g2.drawString(
                threadStatus,
                180,
                475
        );

        // Progress
        long totalOperations =
                (long) SIZE * SIZE * SIZE;

        double progress =
                (completedOperations * 100.0)
                / totalOperations;

        g2.setFont(new Font("Arial", Font.BOLD, 18));

        g2.drawString(
                String.format("Progress: %.2f%%", progress),
                80,
                520
        );

        // Progress bar
        int barWidth = 800;
        int barHeight = 25;

        g2.drawRect(
                80,
                540,
                barWidth,
                barHeight
        );

        int filledWidth =
                (int) (barWidth * progress / 100.0);

        g2.fillRect(
                80,
                540,
                filledWidth,
                barHeight
        );

        // Status
        g2.setFont(new Font("Arial", Font.BOLD, 16));

        if (calculationFinished) {

            g2.drawString(
                    "STATUS: Matrix multiplication completed successfully",
                    80,
                    600
            );

        } else {

            g2.drawString(
                    "STATUS: Multiplication in progress...",
                    80,
                    600
            );
        }
    }

    private void drawMatrix(
            Graphics2D g2,
            int x,
            int y,
            String title
    ) {

        g2.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        g2.drawString(
                title,
                x + 35,
                y - 20
        );

        int cellSize = 25;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {

                int cellX =
                        x + j * cellSize;

                int cellY =
                        y + i * cellSize;

                g2.drawRect(
                        cellX,
                        cellY,
                        cellSize,
                        cellSize
                );

                g2.setFont(
                        new Font("Arial", Font.PLAIN, 11)
                );

                g2.drawString(
                        "A",
                        cellX + 9,
                        cellY + 16
                );
            }
        }

        g2.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        g2.drawString(
                "Actual size: 100 x 100",
                x,
                y + 155
        );
    }

    private void drawResultMatrix(
            Graphics2D g2,
            int x,
            int y
    ) {

        g2.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        g2.drawString(
                "Result C",
                x,
                y - 20
        );

        int cellSize = 25;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 5; j++) {

                int cellX =
                        x + j * cellSize;

                int cellY =
                        y + i * cellSize;

                g2.drawRect(
                        cellX,
                        cellY,
                        cellSize,
                        cellSize
                );

                g2.setFont(
                        new Font("Arial", Font.PLAIN, 11)
                );

                g2.drawString(
                        "C",
                        cellX + 9,
                        cellY + 16
                );
            }
        }

        g2.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        g2.drawString(
                "Actual size: 100 x 100",
                x - 5,
                y + 155
        );
    }

    private void performMultiplication() {

        Thread.ofVirtual().start(() -> {

            for (int row = 0; row < SIZE; row++) {

                for (int column = 0; column < SIZE; column++) {

                    final int r = row;
                    final int c = column;

                    long[] products =
                            new long[SIZE];

                    Thread[] threads =
                            new Thread[SIZE];

                    /*
                     * One virtual thread is created
                     * for every scalar multiplication.
                     */
                    for (int k = 0; k < SIZE; k++) {

                        final int index = k;

                        currentRow = r;
                        currentColumn = c;
                        currentK = index;

                        threads[index] =
                                Thread.ofVirtual().start(() -> {

                                    products[index] =
                                            (long) matrixA[r][index]
                                            * matrixB[index][c];
                                });
                    }

                    /*
                     * Wait for all 100 multiplication
                     * threads to finish.
                     */
                    for (Thread thread : threads) {

                        try {

                            thread.join();

                        } catch (InterruptedException e) {

                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    // Add all products
                    long sum = 0;

                    for (long product : products) {

                        sum += product;
                    }

                    result[r][c] = sum;

                    // 100 scalar multiplication operations completed
                    completedOperations += SIZE;
                }

                /*
                 * Small delay between rows so that the
                 * animation progress can be observed.
                 */
                try {

                    Thread.sleep(200);

                } catch (InterruptedException e) {

                    Thread.currentThread().interrupt();
                    return;
                }
            }

            calculationFinished = true;
        });
    }

    public void startAnimation() {

        Timer timer =
                new Timer(
                        50,
                        e -> repaint()
                );

        timer.start();

        performMultiplication();
    }

    public static void main(String[] args) {

        JFrame frame =
                new JFrame(
                        "Matrix Multiplication Animation"
                );

        MatrixAnimation animation =
                new MatrixAnimation();

        frame.add(animation);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        frame.setVisible(true);

        animation.startAnimation();
    }
}
