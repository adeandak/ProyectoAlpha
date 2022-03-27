package Interfaz;

import java.io.Serializable;

/*
 > Lo que sucede cuando el jugador realiza una acción, por lo que podemos saber el id del jugador, la casilla que se
   hace el click.
*/


public class Reply implements Serializable{

    int idJugadaActual;
    int casillaClicked;
    String idJugador;

    public Reply(int idJugadaActual, int casillaClicked, String idJugador) {
        this.idJugadaActual = idJugadaActual;
        this.casillaClicked = casillaClicked;
        this.idJugador = idJugador;
    }

    public int getIdJugadaActual() {
        return idJugadaActual;
    }

    public void setIdJugadaActual(int idJugadaActual) {
        this.idJugadaActual = idJugadaActual;
    }

    public int getCasillaClicked() {
        return casillaClicked;
    }

    public void setCasillaClicked(int casillaClicked) {
        this.casillaClicked = casillaClicked;
    }

    public String getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(String idJugador) {
        this.idJugador = idJugador;
    }


}
