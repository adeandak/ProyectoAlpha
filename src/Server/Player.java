package Server;

public class Player {

    public String id;
    public int puntos;
    public boolean jugando;
    public boolean terminoTurno;
    public boolean listo;

    public Player(String id, int puntos) {
        this.id = id;
        this.puntos = puntos;
        this.jugando=true;
        this.listo=false;
        this.terminoTurno=false;
    }

    public Player(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Player{" + "id='" + id + ":" + puntos + '}';
    }

    public String getId() {
        return id;
    }

    public int getPuntos() {
        return puntos;
    }

    public boolean isJugando() {
        return jugando;
    }

    public boolean isTerminoTurno() {
        return terminoTurno;
    }

    public boolean isListo() {
        return listo;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public void setTerminoTurno(boolean terminoTurno) {
        this.terminoTurno = terminoTurno;
    }

    public void setListo(boolean listo) {
        this.listo = listo;
    }

    public int aumentaPuntos(){
        this.puntos++;
        return this.puntos;
    }

    public void resetPuntos(){
        this.puntos=0;
    }
}
