

import java.awt.Color;

public class Parede implements ElementoMapa, colorInterface {
    private Color cor;
    private Character simbolo;

    public Parede(Character simbolo, Color cor) {
        this.simbolo = simbolo;
        this.cor = cor;
    }
    
    public Character getSimbolo() {
        return simbolo;
    }

    public Color getCor() {
        return cor;
    }

    public void setCor(Color novaCor) {
        this.cor = novaCor;
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
