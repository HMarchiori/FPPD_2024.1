package TDE2.Game;

import java.util.List;

public class GameState {
    private List<Jogador> jogadores;
    private Mapa mapa;
    private List<Moeda> moedas;
    private int recordeMoedas = 0;
    
    public GameState(List<Jogador> jogadores, Mapa mapa, List<Moeda> moedas) {
        this.jogadores = jogadores;
        this.mapa = mapa;
        this.moedas = moedas;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        jogadores.remove(jogador);
    }

    public Mapa getMapa() {
        return mapa;
    }

    public List<Moeda> getMoedas() {
        return moedas;
    }

    public void adicionarMoeda(Moeda moeda) {
        moedas.add(moeda);
    }

    public void removerMoeda(Moeda moeda) {
        moedas.remove(moeda);
    }

    public void atualizarPosicao(Jogador jogador, int posX, int posY) {
        jogador.setPosX(posX);
        jogador.setPosY(posY);
    }

    public Jogador vencedor() {
        Jogador vencedor = null;
        for (int i = 0; i < jogadores.size(); i++) {
            if (jogadores.get(i).getNumMoedas() > recordeMoedas) {
                recordeMoedas = jogadores.get(i).getNumMoedas();
                vencedor = jogadores.get(i);
            }
        }
        return vencedor;
    }
    
    }
