package TDE2.Game;

public class Jogador {
    private String nome;
    private int posX;
    private int posY;
    private int numMoedas;

    public Jogador(String nome, int posX, int posY) {
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
        this.numMoedas = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public int getNumMoedas() {
        return numMoedas;
    }

}
