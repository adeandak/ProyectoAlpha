package Server;

import Interfaces.Login;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class Game implements Login{

    public boolean fin;
    public boolean juegue;
    public ArrayList<Player> players;
    Administrador adm;
    String puntajes;


    public Game(){
        fin=true;
        juegue=false;
        this.players= new ArrayList<Player>();
    }

    public void setAdm(Administrador adm) {
        this.adm = adm;
    }

    public boolean revisaPlayers(){
        int numJug=players.size();
        boolean res=false;
        if(numJug>0){
            res=true;
            for (int i=0;i<numJug;i++){
                if(players.get(i).isJugando()){
                    res=players.get(i).isListo() && res;
                }
            }
            if(res){
                for(int i=0;i<numJug;i++){
                    if(!players.get(i).isJugando()){
                        players.remove(i);
                        i--;
                        numJug--;
                    }
                }
            }
        }
        return res;
    }


    @Override
    public ConexionRMI conecta(String IDPlayer) throws RemoteException {
        ConexionRMI con;
        int num=players.size();
        boolean repetido=false;
        int i=0;
        Player dummy=new Player(IDPlayer);
        while(i<num && !repetido){
            repetido=players.get(i).equals(dummy);
            i++;
        }
        if(repetido && !players.get(i-1).isJugando()){
            players.get(i-1).setJugando(true);
            players.get(i-1).setListo(false);
            players.get(i-1).setTerminoTurno(false);
            con= new ConexionRMI(IDPlayer,players.get(i-1).getPuntos(),7899,"localhost",6791,"228.5.6.7");
        }else if(repetido){
            con=new ConexionRMI(null);
        }else{
            players.add(new Player(IDPlayer,0));
            System.out.println("Se añadió el nuevo jugador: "+IDPlayer);
            con= new ConexionRMI(IDPlayer,7899,"localhost",6791,"228.5.6.7");
        }
        return con;
    }

    @Override
    public void logout(String IDPlayer) throws RemoteException {
        int index;
        Player aux = new Player(IDPlayer);
        index = players.indexOf(aux);
        players.get(index).setJugando(false);
    }

    @Override
    public String puntaje() throws RemoteException {
        if(!fin){
            puntajes=players.toString();
        }
        return puntajes;
    }

    @Override
    public int puntosJugador(String IDPlayer) throws RemoteException {
        int index;
        Player aux = new Player(IDPlayer);
        index = players.indexOf(aux);
        return players.get(index).getPuntos();
    }

    @Override
    public void jugadorListo(String IDPlayer) throws RemoteException {
        int index;
        Player aux= new Player(IDPlayer);
        index=players.indexOf(aux);
        players.get(index).setListo(true);
    }

    public void reinicia(){
        try{
            this.puntaje();
            int num=players.size();
            fin=true;
            for(int i=0;i<num;i++){
                players.get(i).resetPuntos();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void nuevoJuego(){
        reinicia();
        juegue=true;
        int num= players.size();
        for(int i=0;i<num;i++){
            players.get(i).setTerminoTurno(false);
            players.get(i).setListo(false);
            if(!players.get(i).isJugando()){
                players.remove(i);
                i--;
                num--;
            }
        }
    }

    void sigueJuego(){
        juegue=true;
        int num=players.size();
        for(int i=0;i<num;i++){
            players.get(i).setListo(false);
            players.get(i).setTerminoTurno(false);
        }
    }

    public int getIndex(String nom){
        return players.indexOf(new Player(nom));
    }

    public int getPuntos(int index){
        return players.get(index).aumentaPuntos();
    }

}
