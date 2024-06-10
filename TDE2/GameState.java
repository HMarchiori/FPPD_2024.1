package TDE2.Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import TDE2.Game.*;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;
    private GameTimer timer;
    private List<Player> players;
    private List<Moeda> coinPositions;
    private Mapa map;
    private Player winner = null;
    private boolean gameRunning = true;

    public GameState() {
        this.players = new ArrayList<>();
        this.coinPositions = new ArrayList<>();
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

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public boolean isGameRunning() {
        return (gameRunning && timer.getTempoRestante() > 0);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void updatePlayerPosition(String id, int posX, int posY) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                player.setPosition(posX, posY);
                break;
            }
        }
    }

    public void updatePlayerCoinCounter(String id, int coinCounter) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                player.updateCoinCounter(player, coinCounter);
                break;
            }
        }
    }
}
