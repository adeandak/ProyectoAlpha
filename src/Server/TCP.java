package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP extends Thread{

    ServerSocket listenSocket;

    public TCP(int serverPort){
        try{
            listenSocket= new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
