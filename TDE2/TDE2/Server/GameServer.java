package TDE2.Server;
import java.rmi.Naming;
import java.rmi.RemoteException;

import TDE2.Game.GameState;

public class GameServer {
    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (RemoteException e) {
            System.out.println("RMI registry already running.");
        }
        try {
            Naming.rebind("Jogador", new GameState());
            System.out.println("GameServer is ready.");
        } catch (Exception e) {
            System.out.println("GameServer failed: " + e);
            e.printStackTrace();
        }
    }
}
