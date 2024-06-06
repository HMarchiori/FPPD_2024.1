package TDE2.Server;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private int posX;
    private int posY;
    private int coinCounter;

    public Player(String id) {
        this.id = id;
        this.posX = 0; // Posição inicial padrão
        this.posY = 0; // Posição inicial padrão
    }

    public String getId() {
        return id;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public int getCoinCounter() {
        return coinCounter;
    }

    public int updateCoinCounter(int value) {
        this.coinCounter += value;
        return this.coinCounter;
    }

    public void updatePosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

}
