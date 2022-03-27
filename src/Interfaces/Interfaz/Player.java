package Interfaz;

import java.io.Serializable;

// ===                                                  Jugador                                                   === //
// > Creamos al objeto player con sus atributos y métodos
// ===================================================================================================================//
public class Player implements Serializable{
    String idJugador;
    int points;

    public Player(String idJugador, int puntaje) {
        this.idJugador = idJugador;
        this.points = puntaje;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
