package TDE2.Game;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private Mapa mapa;
    private int contadorMoedas;
    private List<Moeda> moedasColetadas;
    private int posX;
    private int posY;

    public GameState(String arquivoMapa) {
        mapa = new Mapa(arquivoMapa);
        contadorMoedas = 0;
        moedasColetadas = new ArrayList<>();
        posX = mapa.getX();
        posY = mapa.getY();
    }

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public int getContadorMoedas() {
        return contadorMoedas;
    }

    public void setContadorMoedas(int contadorMoedas) {
        this.contadorMoedas = contadorMoedas;
    }

    public List<Moeda> getMoedasColetadas() {
        return moedasColetadas;
    }

    public void setMoedasColetadas(List<Moeda> moedasColetadas) {
        this.moedasColetadas = moedasColetadas;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void coletarMoeda(Moeda moeda) {
        moedasColetadas.add(moeda);
        contadorMoedas++;
    }

    public void atualizarPosicao(int novoX, int novoY) {
        this.posX = novoX;
        this.posY = novoY;
    }
}
