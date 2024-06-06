package TDE2.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServerInterface extends Remote {
    void registerClient(String clientId) throws RemoteException;
    void sendCommand(String clientId, int sequenceNumber, int posX, int posY, int coinCounter) throws RemoteException;
    GameState getGameState() throws RemoteException;
}
