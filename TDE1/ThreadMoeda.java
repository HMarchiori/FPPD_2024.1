import java.util.Random;

public class ThreadMoeda implements Runnable {
    private Jogo jogo;
    private Mapa mapa;
    private Random random = new Random();
    private Object lock = new Object(); // Lock object
    
    public ThreadMoeda(Jogo jogo, Mapa mapa) {
        this.jogo = jogo;
        this.mapa = mapa;
    }

    @Override
    public void run() {
        while (true) {
            int x = random.nextInt(mapa.getNumColunas());
            int y = random.nextInt(mapa.getNumLinhas());
    
            synchronized (lock) { // Acquire lock
                if (mapa.getMapa().get(x).charAt(y) == ' '){
                    mapa.colocarMoeda(x, y);
                    jogo.repaint(x, y, mapa.getTamanhoCelula(), mapa.getTamanhoCelula());
                }
                lock.notify(); // Unlock and notify waiting threads
            } // Release lock
            
    
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
