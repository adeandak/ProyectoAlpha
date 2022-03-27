package Interfaz;

import java.io.Serializable;
import java.util.Hashtable;

// Jugada
// Métodos y funciones de la jugada

public class Move implements Serializable{

    private int idJugadaActual = 0;
    private int casillaEncendida = -1;
    Hashtable<String,Integer> jugadoresActuales = null;

    public Move(){};

    public Move(int idJugadaActual, int casillaEncendida, Hashtable<String, Integer> jugadoresActuales) {
        this.idJugadaActual = idJugadaActual;
        this.casillaEncendida = casillaEncendida;
        this.jugadoresActuales = jugadoresActuales;
    }

    public int getIdJugadaActual() {
        return idJugadaActual;
    }

    public void setIdJugadaActual(int idJugadaActual) {
        this.idJugadaActual = idJugadaActual;
    }

    public int getCasillaEncendida() {
        return casillaEncendida;
    }

    public void setCasillaEncendida(int casillaEncendida) {
        this.casillaEncendida = casillaEncendida;
    }

    public Hashtable<String, Integer> getJugadoresActuales() {
        return jugadoresActuales;
    }

    public void setJugadoresActuales(Hashtable<String, Integer> jugadoresActuales) {
        this.jugadoresActuales = jugadoresActuales;
    }

}
