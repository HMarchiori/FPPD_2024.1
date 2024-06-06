package TDE2.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameServer extends UnicastRemoteObject implements GameServerInterface {
    private GameState gameState;
    private List<Player> players;
    private Map<String, Integer> lastProcessedSequenceNumbers;

    protected GameServer() throws RemoteException {
        this.gameState = new GameState();
        this.lastProcessedSequenceNumbers = new HashMap<>();
    }

    public void sendCommand(String clientId, int sequenceNumber, int posX, int posY, int numMoedas)
            throws RemoteException {
       if (lastProcessedSequenceNumbers.get(clientId) < sequenceNumber) {
            players.updatePosition(clientId, posX, posY);
            lastProcessedSequenceNumbers.put(clientId, sequenceNumber);
        }
    }
  
    public void registerClient(String clientId) throws RemoteException {
        if (!lastProcessedSequenceNumbers.containsKey(clientId)) {
            lastProcessedSequenceNumbers.put(clientId, -1);
            gameState.addPlayer(new Player(clientId));
        }
    }


    public GameState getGameState() throws RemoteException {
        return gameState;
    }

    public static void main(String[] args) {
        try {
            GameServer gameServer = new GameServer();
            java.rmi.registry.LocateRegistry.createRegistry(1099).rebind("GameServer", gameServer);
            System.out.println("Game server started!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
