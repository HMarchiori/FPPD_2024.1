
import java.rmi.registry.Registry;

public class GameClient {
    private GameServerInterface gameServer;
    private GameState gameState;
    private String clientId;

    public GameClient(String serverAddress) {
        try {
            Registry registry = java.rmi.registry.LocateRegistry.getRegistry(serverAddress);
            gameServer = (GameServerInterface) registry.lookup("GameServer");
            this.clientId = java.util.UUID.randomUUID().toString();
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


    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java GameClient <server_address>");
            return;
        }
        new GameClient(args[0]);
    }
}