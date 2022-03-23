package Server;

import java.io.Serializable;

public class ConexionRMI implements Serializable {

    String idPlayer;
    int puntos;
    int TCPPort;
    String TCPIP;
    int PuertoMul;
    String IPMul;

    public ConexionRMI(String idPlayer, int puntos, int TCPPort, String TCPIP, int puertoMul, String IPMul) {
        this.idPlayer = idPlayer;
        this.puntos = puntos;
        this.TCPPort = TCPPort;
        this.TCPIP = TCPIP;
        PuertoMul = puertoMul;
        this.IPMul = IPMul;
    }

    public ConexionRMI(String idPlayer) {
        this.idPlayer = idPlayer;
    }

    public ConexionRMI(String idPlayer, int TCPPort, String TCPIP, int puertoMul, String IPMul) {
        this.idPlayer = idPlayer;
        this.puntos = -1;
        this.TCPPort = TCPPort;
        this.TCPIP = TCPIP;
        PuertoMul = puertoMul;
        this.IPMul = IPMul;
    }

    public String getIdPlayer() {
        return idPlayer;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getTCPPort() {
        return TCPPort;
    }

    public String getTCPIP() {
        return TCPIP;
    }

    public int getPuertoMul() {
        return PuertoMul;
    }

    public String getIPMul() {
        return IPMul;
    }
}
