/**
    Interface Calculadora com assinatura dos métodos 
 */

import java.rmi.*;

public interface Calculadora extends Remote {
    public Double soma (double x, double y) throws RemoteException;
    public Double sub (double x, double y) throws RemoteException;
    public Double mult (double x, double y) throws RemoteException;
    public Double div (double x, double y) throws RemoteException;
    public void store (int x, double y) throws RemoteException;
    public Double load (int x) throws RemoteException;
}