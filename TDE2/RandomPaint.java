import java.awt.Color;
import java.util.Random;

public class RandomPaint implements Runnable {
    private final Mapa mapa;
    private final Random random;
    private Jogo jogo;
    private final Object lock;
    private final GameTimer gameTimer; // Referência ao GameTimer

    public RandomPaint(Jogo jogo, Mapa mapa, GameTimer gameTimer) {
        this.jogo = jogo;
        this.mapa = mapa;
        this.random = new Random();
        this.lock = new Object(); // Inicializa o objeto de lock
        this.gameTimer = gameTimer; // Inicializa a referência ao GameTimer
    }

    @Override
    public void run() {
        try {
            while (true) {
                int tempoRestante = gameTimer.getTempoRestante();
                
                // Define o intervalo de troca de cores com base no tempo restante
                long intervalo;
                if (tempoRestante <= 5) {
                    intervalo = 500; // Meio segundo
                } else {
                    intervalo = 1500;
                }
                
                // Percorre todos os elementos do mapa
                synchronized (lock) {
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
                    lock.notifyAll();
                }

                // Repinta o mapa
                jogo.repaint();
                Thread.sleep(intervalo); // Dorme pelo intervalo calculado
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
