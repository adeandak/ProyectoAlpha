package Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;


public class Multicast extends Thread{

    boolean sigueJuego=true;
    String IPMul="228.5.6.7";
    int Puerto=49155;
    MulticastSocket s=null;
    InetAddress group=null;
    String ganador="";

    public void MandaMulticast(){
        MulticastSocket socket = null;
        try {
            while(true){
                group = InetAddress.getByName(IPMul); // destination multicast group
                s = new MulticastSocket(Puerto);
                s.joinGroup(group);
                s.setTimeToLive(1);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null) socket.close();
        }
    }

    @Override
    public void run(){
        int aux;
        while(sigueJuego){
            try{
                aux=(int)Math.floor(Math.random()*11+1);
                String Mensaje=aux+"";
                byte [] m=Mensaje.getBytes();
                InetAddress group = InetAddress.getByName(IPMul); // destination multicast group
                DatagramPacket messageOut= new DatagramPacket(m, m.length, group, Puerto);
                s.send(messageOut);
                Thread.sleep(800);
            }catch(IOException e){

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Determinando Ganador");
        try{
            String Mensaje="-1";
            byte [] m=Mensaje.getBytes();
            DatagramPacket messageOut= new DatagramPacket(m, m.length, group, Puerto);
            s.send(messageOut);
            System.out.println(ganador);
            m=ganador.getBytes();
            messageOut= new DatagramPacket(m, m.length, group, Puerto);
            s.send(messageOut);
            System.out.println("Ganador Determinado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
