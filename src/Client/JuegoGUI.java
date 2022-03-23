package Client;

import Interfaces.Login;

import javax.swing.*;
import java.awt.*;

public class JuegoGUI extends javax.swing.JFrame{

    private final Color monstruoColor;
    private final Color color;
    JuegoCliente jugador;
    JButton botones[]=new JButton[16];
    Login Log;
    String IDPlayer;
    int TCPPort;
    String TCPIP;
    int MulPort;
    String MulIP;
    boolean juegoInicial=true;

    public Juego(String jug, int puntos, int tcpPort, String tcpIP, int mulPort, String mulIP,LoginPartida Log){
        initComponents();
        monstruoColor = Color.ORANGE;
        normalColor = Color.gray;
        this.Log = Log;
        this.IDPlayer = jug;
        this.tcpPort = tcpPort;
        this.tcpIP = tcpIP;
        this.mulPort = mulPort;
        this.mulIP = mulIP;
        llenaArre(); // los botones se guardan en un arreglo
        setColores(); // pone todas las casillas en gris
        setJugador(jug, puntos,tcpPort, tcpIP, mulPort, mulIP);
    }

    public final void setJugador(String jug, int puntos, int tcpPort, String tcpIP, int mulPort, String mulIP){
        jugador = new JuegoCliente(jug,puntos,tcpPort,tcpIP, mulPort,mulIP, Log);
        if(puntos >=0){
            juegoInicial = false;
        }
        jugador.setGui(this);
        jugador.start();
        jTextField1.setText(jug);
    }

}
