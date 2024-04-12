import java.util.Random;

public class ThreadMoeda implements Runnable {
    private Jogo jogo;
    private Mapa mapa;
    private Random random = new Random();
    
    public ThreadMoeda(Jogo jogo, Mapa mapa) {
        this.jogo = jogo;
        this.mapa = mapa;
    }


    @Override
    public void run() {
    while (true) {
            int x = random.nextInt(mapa.getNumColunas());
            int y = random.nextInt(mapa.getNumLinhas());

            if (mapa.getElemento(x, y).getSimbolo() == null) {
                mapa.colocarMoeda(x, y);
                jogo.repaint();
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
