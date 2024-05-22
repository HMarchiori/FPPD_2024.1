import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final int TIMES_TO_EAT = 5;

    static class Philosopher extends Thread {
        private final int id;
        private final Semaphore leftFork;
        private final Semaphore rightFork;

        Philosopher(int id, Semaphore leftFork, Semaphore rightFork) {
            this.id = id;
            this.leftFork = leftFork;
            this.rightFork = rightFork;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < TIMES_TO_EAT; i++) {
                    // Thinking
                    System.out.println("Philosopher " + id + " is thinking.");
                    // Sleep random time between 0 and 1 second
                    long sleepTime = (long) (Math.random() * 2000);
                    Thread.sleep(sleepTime);

                    // Picking up forks
                    leftFork.acquire();
                    //System.out.println("Philosopher " + id + " picked up left fork.");
                    rightFork.acquire();
                    //System.out.println("Philosopher " + id + " picked up right fork.");

                    // Eating
                    System.out.println("Philosopher " + id + " is eating.");
                    sleepTime = (long) (Math.random() * 1000);
                    Thread.sleep(sleepTime);

                    // Putting down forks
                    leftFork.release();
                    System.out.println("Philosopher " + id + " put down left fork.");
                    rightFork.release();
                    System.out.println("Philosopher " + id + " put down right fork.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        Thread[] philosophers = new Thread[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Semaphore leftFork = forks[i];
            Semaphore rightFork = forks[(i + 1) % NUM_PHILOSOPHERS];
            philosophers[i] = new Philosopher(i, leftFork, rightFork);
            philosophers[i].start();
        }

        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        double executionTime = (endTime - startTime)/1000.0;
        System.out.println("Execution time: " + executionTime + "s");
    }
}