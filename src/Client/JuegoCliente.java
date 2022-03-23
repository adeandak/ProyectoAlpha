package Client;

import Interfaces.Login;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class JuegoCliente extends Thread{
    String idJuego;
    public JuegoGui gui;
    int TCPPort;
    String TCPIP;
    int MulPort;
    String MulIP;
    Login Log;
    int puntos;

    public JuegoCliente(String idJuego,int puntos, int tcpPort, String tcpIP, int mulPort, String mulIP , Login Log) {
        this.idJuego = idJuego;
        this.TCPPort = tcpPort;
        this.TCPIP = tcpIP;
        this.MulPort = mulPort;
        this.MulIP = mulIP;
        this.Log = Log;
        this.puntos = puntos;
    }

    @Override
    public void run(){
        try{
            MulticastSocket s=null;
            InetAddress group= InetAddress.getByName(MulIP);
            s=new MulticastSocket(MulPort);
            s.joinGroup(group);
            byte[] buffer;
            DatagramPacket topo;
            int id;
            String aux;
            String puntaje;
            while(true){
                buffer=new byte[1000];
                topo=new DatagramPacket(buffer,buffer.length);
                puntaje=Log.puntaje();
                gui.cambiaPuntos(puntajes); // imprime en la interfaz los puntajes de todos
                gui.cambiaAvi("¡Dele al Topo!");
                s.receive(topo);
                id= Integer.parseInt(new String(topo.getData(),0, topo.getLength()));

                if(id==-1){
                    s.receive(topo);
                    aux=new String(topo.getData(),0, topo.getLength());
                    gui.ganador(aux);

                    puntajes=Log.puntaje();
                    gui.aumentaPuntos(puntajes);
                    break;
                }else{
                    gui.set
                }
            }



        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void golpe(){
        Socket s= null;
        try{
            puntos++;
            s=new Socket(TCPIP,TCPPort);
            DataOutputStream out= new DataOutputStream(s.getOutputStream());
            out.writeUTF(idJuego);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(s != null) try {
                s.close();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setGui(JuegoGui gui){
        this.gui=gui;
    }
}
