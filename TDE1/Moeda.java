import java.awt.Color;
import java.util.Random;

public class Moeda implements ElementoMapa, Runnable {
    private Mapa mapa;
    private Random random;
    private Jogo jogo;

    public Moeda(Jogo jogo, Mapa mapa) {
        this.jogo = jogo;
        this.mapa = mapa;
        this.random = new Random();
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

    @Override
    public Character getSimbolo() {
        return 'M';
    }

    @Override
    public Color getCor() {
        return Color.YELLOW;
    }

    @Override
    public boolean podeSerAtravessado() {
        return true;
    }

    @Override
    public boolean podeInteragir() {
        return true;
    }

    @Override
    public String interage() {
        return "Pegou a moeda!";
    }
}