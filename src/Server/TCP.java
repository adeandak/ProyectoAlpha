package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP extends Thread{

    Administrador adm;
    Game juego;
    ServerSocket listenSocket;

    public TCP(int serverPort){
        try{
            listenSocket= new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAdm(Administrador adm) {
        this.adm = adm;
    }

    public void setJuego(Game juego) {
        this.juego = juego;
    }

    @Override
    public void run(){
        boolean aux;
        try{
            while(true){
                Socket clientSocket=listenSocket.accept();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
