

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class GameServer extends UnicastRemoteObject implements GameServerInterface {
    private static final long serialVersionUID = 1L;
    private GameState gameState;
    private Map<String, Integer> sequenceNumbers;

    public GameServer() throws RemoteException {
        super();
        this.gameState = new GameState();
        this.sequenceNumbers = new HashMap<>();
    }

    public synchronized void registerClient(String clientId) throws RemoteException {
        if (!sequenceNumbers.containsKey(clientId)) {
            sequenceNumbers.put(clientId, 0);
            gameState.addPlayer(new Player(clientId));
        }
    }

    public synchronized void sendCommand(String clientId, int sequenceNumber, int posX, int posY, int coinCounter) throws RemoteException {
        if (sequenceNumbers.get(clientId) < sequenceNumber) {
            sequenceNumbers.put(clientId, sequenceNumber);
            gameState.updatePlayerPosition(clientId, posX, posY);
            gameState.updatePlayerCoinCounter(clientId, coinCounter);
        }
    }

    public synchronized GameState getGameState() throws RemoteException {
        return gameState;
    }

    public static void main(String[] args) {
        try {
            GameServer gameServer = new GameServer();
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            java.rmi.registry.LocateRegistry.getRegistry().rebind("GameServer", gameServer);
            System.out.println("GameServer is running...");
        } catch (Exception e) {
            System.err.println("GameServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
