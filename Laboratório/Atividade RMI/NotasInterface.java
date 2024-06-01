import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotasInterface extends Remote {
	public double obtemNota(String nome) throws RemoteException;
}

