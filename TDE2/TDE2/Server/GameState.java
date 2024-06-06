package TDE2.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import TDE2.Game.*;


public class GameState implements Serializable {
    private GameTimer timer;
    public List<Player> players;
    public List<Moeda> coinPositions;
    public Mapa map;
    public Player winner = null;
    public boolean gameRunning = true;
    public Object player;

    public GameState() {
        this.players = new ArrayList<Player>();
        this.coinPositions = new ArrayList<Moeda>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Moeda> getCoinPositions() {
        return coinPositions;
    }

    public Mapa getMap() {
        return map;
    }

    public Player getWinner() {
        return winner;
    }

    public Player setWinner() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCoinCounter() > winner.getCoinCounter()) {
                winner = players.get(i);
            }
        }
        return winner;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void checkGameRunning() {
        if (timer.getTempoRestante() == 0) {
            gameRunning = false;
        }
    }

    public void addPlayer(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPlayer'");
    }

}