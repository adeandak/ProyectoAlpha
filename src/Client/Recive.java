package client;

import Interfaz.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recive extends Thread{

    private Interfaz interfaz;
    private MulticastSocket multisocket;
    private Move currentPlay;
    private ObjectInputStream iStream;

    public Recive(Interfaz interfaz, MulticastSocket multisocket)
    {
        this.interfaz = interfaz;
        this.multisocket = multisocket;
    }

    public void run(){

        try {
            while(true)
            {
                //código para recibir los mensajes multicast
                byte[] buffer = new byte[10000];
                DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
                multisocket.receive(messageIn);

                iStream = new ObjectInputStream(new ByteArrayInputStream(messageIn.getData()));
                Object datain = (Object) iStream.readObject();
                try {
                    currentPlay = (Move) datain;
                    System.out.println(currentPlay.getCasillaEncendida());
                    iStream.close();

                    interfaz.newPlay(currentPlay);
                } catch (java.lang.ClassCastException ex) {

                    Player ganador = (Player) datain;

                    try {
                        iStream.close();

                        interfaz.ganador(ganador);
                    } catch (IOException ex1) {
                        Logger.getLogger(Recive.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        }   catch (SocketException ex) {
            return;
        }   catch (IOException ex) {
            Logger.getLogger(Recive.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Recive.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
