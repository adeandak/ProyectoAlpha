package Interfaces;

import Server.ConexionRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Login extends Remote {

    public ConexionRMI conecta(String IDPlayer) throws RemoteException;
    public void logout(String IDPlayer) throws RemoteException;
    public String puntaje() throws RemoteException;
    public int puntosJugador(String IDPlayer) throws RemoteException;
    public void jugadorListo(String IDPlayer) throws RemoteException;


}
