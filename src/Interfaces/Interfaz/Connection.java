package Interfaz;

import java.io.Serializable;

// Conectamos con el servidor, se va a oedir el ip_addres y el número de puerto
public class Connection implements Serializable {

    String ip_address;
    int num_port;

    public Connection(String ip_address, int numPort) {
        this.ip_address = ip_address;
        this.num_port = numPort;
        //this.idOkay = idOkay;
    }

    public Connection(){
        super();
    }

    public String getIpaddress() {
        return ip_address;
    }

    public int getNo_port() {
        return num_port;
    }

}
