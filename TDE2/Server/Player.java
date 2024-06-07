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
        this.posX = 0; 
        this.posY = 0;
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

    public int updateCoinCounter(Player player, int value) {
        player.coinCounter = value;
        return player.coinCounter;
    }

    public void updatePosition(Player player, int posX, int posY) {
        player.setPosition(posX, posY);
    }

}
