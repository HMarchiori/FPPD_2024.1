public class Contador extends Thread {
    private final int threadId;

    private static final int COUNT = 100000;
    private static int counter = 0;
    private static final Object lock = new Object();

    public Contador(int threadId) {
        this.threadId = threadId;
    }

    @Override
    public void run() {
        System.out.println("Iniciando thread " + threadId);
        for (int i = 0; i < COUNT; i++) {
            synchronized (lock) {
                counter++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final int NUM_THREADS = 10;
        Contador[] threads = new Contador[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            System.out.println("Criando thread " + i);
            threads[i] = new Contador(i);
            threads[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }

        System.out.println("Resultado final: " + counter);
    }
}
