package TDE2.Client;

import java.rmi.registry.Registry;

import TDE2.Server.*;

public class GameClient {
    private GameServerInterface gameServer;
    private GameState gameState;
    private String clientId;
    @SuppressWarnings("unused")
    private int sequenceNumber;

    public GameClient(String serverAddress) {
        try {
            Registry registry = java.rmi.registry.LocateRegistry.getRegistry(serverAddress);
            gameServer = (GameServerInterface) registry.lookup("GameServer");
            this.clientId = java.util.UUID.randomUUID().toString();
            this.sequenceNumber = 0;
            gameServer.registerClient(clientId);
            new Thread(this::fetchUpdates).start();
        } catch (Exception e) {
            System.err.println("GameClient exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private void fetchUpdates() {
        try {
            while (true) {
                gameState = gameServer.getGameState();
                if (!gameState.isGameRunning()) {
                    break;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("GameClient exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /* private void sendCommand(int posX, int posY, int coinCounter) {
        try {
            sequenceNumber++;
            gameServer.sendCommand(clientId, sequenceNumber, posX, posY, coinCounter);
        } catch (Exception e) {
            System.err.println("GameClient exception: " + e.toString());
            e.printStackTrace();
        }
    }
        */

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GameClient <server_address>");
            return;
        }
        new GameClient(args[0]);
    }
}