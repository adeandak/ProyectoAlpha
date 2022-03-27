package Interfaz;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Interfaz con los métodos que se implementaran
public interface Login extends Remote{

    public Connection userLogin(String playerID) throws RemoteException;
    public void userLogout(String playerID) throws RemoteException;
}
