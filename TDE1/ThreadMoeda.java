import java.util.Random;

public class ThreadMoeda implements Runnable {
    private Mapa mapa;
    private Random random = new Random();
    private Object lock = new Object(); // Lock object
    private int numCoins = 0; // Counter for number of coins
    
    public ThreadMoeda(Mapa mapa) {
        this.mapa = mapa;
    }
    
    @Override
    public void run() {
        synchronized (lock) {
            if (numCoins < 30) {
                for (int i = 0; i < 30; i++) {
                    int x = random.nextInt(mapa.getNumLinhas());
                    int y = random.nextInt(mapa.getNumColunas());
    
                    try {
                        // Usando o novo método para verificar se a posição é válida
                        if (mapa.podeColocarMoeda(x, y)) {
                            mapa.colocarMoeda(x, y);
                            numCoins++; // Incrementa o número de moedas
                            Thread.sleep(500); // Pausa após colocar uma nova moeda
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e) {
                        System.err.println("Tentativa de acesso a índice inválido: " + e.getMessage());
                    }
                }
            }
            lock.notify(); // Desbloqueia e notifica as threads em espera
        }
    }
}
