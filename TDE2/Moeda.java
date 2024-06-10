

import java.awt.Color;

public class Moeda implements ElementoMapa {
    private Color cor;
    private Character simbolo;
    private int posX;
    private int posY;

    public Moeda(Character simbolo, Color cor, int posX, int posY) {
        this.simbolo = simbolo;
        this.cor = cor;
        this.posX = posX;
        this.posY = posY;
    }
    
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Character getSimbolo() {
        return simbolo;
    }

    public Color getCor() {
        return cor;
    }

    @Override
    public boolean podeSerAtravessado() {
        return false;
    }

    @Override
    public boolean podeInteragir() {
        return false;
    }

    @Override
    public String interage() {
        return null;
    }
}
