package Server;

import Interfaces.Login;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Administrador {

    String IPMul="228.5.6.7";
    int PuertoMul=6791;
    MulticastSocket s=null;
    InetAddress group;
    TCP conexTCP;
    boolean ganador=false;
    Multicast servMul;

    public Administrador(){

    }

    public void setMulticast(Multicast m){
        this.servMul=m;
    }

    public synchronized void ganador(String usr){
        System.out.println(usr);
        servMul.ganador(usr);
    }

    public static void main(String[] args) throws InterruptedException{

        try{
            Administrador admin= new Administrador();
            Multicast servMul=new Multicast();
            servMul.setAdm(admin);
            admin.setMulticast(servMul);
            servMul.MandaMulticast();
            TCP conexTCP=new TCP(7899);
            Game engine = new Game();
            conexTCP.setJuego(engine);
            engine.setAdm(admin);
            conexTCP.start();

            //RMI para el servicio de Login
            System.setProperty("java.security.policy","src/Server/server.policy");
            LocateRegistry.createRegistry(1099);
            String name="Login";
            Login stub= (Login) UnicastRemoteObject.exportObject(engine,0);
            Registry registry=LocateRegistry.getRegistry();
            registry.rebind(name,stub);

            while(true){
                if(!engine.revisaPlayers()){
                    Thread.sleep(200);
                }else{
                    if(!engine.juegue && engine.fin){
                        engine.reinicia();
                        servMul.start();
                        engine.fin=false;
                    }else if(engine.juegue && !engine.fin){
                        engine.sigueJuego();
                    }else{
                        if(engine.revisaPlayers()){
                            engine.juegue=false;
                            engine.reinicia();
                            servMul=new Multicast();
                            servMul.setAdm(admin);
                            admin.setMulticast(servMul);
                            servMul.MandaMulticast();
                            servMul.start();
                            engine.fin=false;
                        }else{
                            Thread.sleep(100);
                        }
                    }
                }
            }
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
