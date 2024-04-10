import java.util.Random;

public class ThreadMoeda implements Runnable {
    private Jogo jogo;
    private Mapa mapa;

    public ThreadMoeda(Jogo jogo, int x, int y, Mapa mapa) {
        this.jogo = jogo;
        this.mapa = mapa;
    }

    public void colocarMoeda(int x, int y,Mapa mapa) {
        if (mapa.getElemento(x, y).getSimbolo() != '#') {
            mapa.setElementoMapa(x, y, this);   
            jogo.repaint();     
        }
    }

    @Override
    public void run() {
    while (true) {
            int x = random.nextInt(mapa.getNumColunas());
            int y = random.nextInt(mapa.getNumLinhas());

            if (mapa.getElemento(x, y).getSimbolo() == null) {
                colocarMoeda(x, y, mapa);
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
