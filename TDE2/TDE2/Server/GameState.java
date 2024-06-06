package TDE2.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import TDE2.Game.Mapa;
import TDE2.Game.Moeda;

class Jogador {
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

public class GameState extends UnicastRemoteObject implements GameServerInterface {
    private List<Jogador> jogadores;
    private Mapa mapa;
    private List<Moeda> moedas;
    private int recordeMoedas = 0;
    
    public GameState() throws RemoteException {
        // Coloco algo??
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


    public void registerClient(String clientId) throws RemoteException {
        // Register Client
    }


    public void sendCommand(String clientId, int sequenceNumber, int posX, int posY, int numMoedas) throws RemoteException {
        // Send Command
    }


    public GameState getGameState() throws RemoteException {
        return this;
    }
    
    }
