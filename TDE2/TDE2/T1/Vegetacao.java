package TDE2.T1;

import java.awt.Color;

public class Vegetacao implements ElementoMapa, colorInterface {
    private Color cor;
    private Character simbolo;

    public Vegetacao(Character simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }
    
    @Override
    public Character getSimbolo() {
        return simbolo;
    }

    @Override
    public Color getCor() {
        return cor;
    }
    
    public void setCor(Color novaCor) {
        this.cor = novaCor;
    }

    @Override
    public boolean podeSerAtravessado() {
        return true;
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