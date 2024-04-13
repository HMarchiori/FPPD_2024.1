import java.awt.Color;
import java.util.Random;

public class RandomPaint implements Runnable {
    private final Mapa mapa;
    private final int delay;
    private final Random random;
    private Jogo jogo;
    private final Object lock; // Add a lock object

    public RandomPaint(Jogo jogo, Mapa mapa, int delay) {
        this.jogo = jogo;
        this.mapa = mapa;
        this.delay = delay;
        this.random = new Random();
        this.lock = new Object(); // Initialize the lock object
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Percorre todos os elementos do mapa
                synchronized (lock) { // Acquire the lock
                    for (int i = 0; i < mapa.getNumLinhas(); i++) {
                        for (int j = 0; j < mapa.getNumColunas(); j++) {
                            ElementoMapa elemento = mapa.getElemento(j, i);
                            if (elemento instanceof colorInterface) {
                                colorInterface colorInterface = (colorInterface) elemento;
                                Color novaCor = gerarCorAleatoria();
                                colorInterface.setCor(novaCor);
                            }
                        }
                    }
                    lock.notifyAll(); // Notify all threads waiting on the lock
                } // Release the lock
                
                // Repinta o mapa
                jogo.repaint();
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Color gerarCorAleatoria() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }
}
