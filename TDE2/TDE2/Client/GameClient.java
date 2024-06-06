package TDE2.Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import TDE2.Server.*;

public class GameClient {
    private GameServerInterface gameServer;
    private String clientId;
    private int sequenceNumber;
    private GameState localGameState;

    public GameClient(String serverAddress, String clientId) {
        this.clientId = clientId;
        this.sequenceNumber = 0;

        try {
            gameServer = (GameServerInterface) Naming.lookup("//" + serverAddress + "/GameServer");
            gameServer.registerClient(clientId);
            localGameState = gameServer.getGameState();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    localGameState = gameServer.getGameState();
                    // Atualizar a interface gráfica com o novo estado do jogo
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000); // Atualiza a cada segundo
    }

    public void sendCommand(int posX, int posY) {
        try {
            gameServer.sendCommand(clientId, sequenceNumber++, posX, posY, posY);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public GameState getLocalGameState() {
        return localGameState;
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        GameClient client = new GameClient("localhost", "Player1");
        // Configurar a interface gráfica e iniciar o jogo
    }
}
