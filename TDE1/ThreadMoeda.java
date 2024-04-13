import java.util.Random;

public class ThreadMoeda implements Runnable {
    private Jogo jogo;
    private Mapa mapa;
    private Random random = new Random();
    private Object lock = new Object(); // Lock object
    private int numCoins = 0; // Counter for number of coins
    
    public ThreadMoeda(Jogo jogo, Mapa mapa) {
        this.jogo = jogo;
        this.mapa = mapa;
    }

    @Override
    public void run() {
        synchronized (lock) { // Acquire lock
            if (numCoins < 10) { // Check if number of coins is less than 10
                for (int i = 0; i < 10; i++) {
                    int x = random.nextInt(mapa.getNumColunas());
                    int y = random.nextInt(mapa.getNumLinhas());

                    if (mapa.getMapa().get(x).charAt(y) == ' ') {
                        mapa.colocarMoeda(x, y);
                        numCoins++; // Increment number of coins
                        try {
                            Thread.sleep(1500); // Sleep after placing a new coin
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            lock.notify(); // Unlock and notify waiting threads
        } // Release lock
    }
}
