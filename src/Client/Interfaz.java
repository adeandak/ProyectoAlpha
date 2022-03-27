package client;//import Interfaces.*;

import Interfaz.*;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Interfaz extends javax.swing.JFrame {

    // Colores de monstruos
    private final  Color monstruoColor;
    private final Color normalColor;

    // Botones de la interfaz
    JButton botones[] = new JButton[16];

    private Connection info;
    private Socket s;
    private ObjectOutputStream out;
    private String idJugador;
    private Player player;
    private MulticastSocket multisocket;
    private InetAddress group;
    private Move play;

    private AudioInputStream audio;
    private Clip bonk;

    private Cursor cursor;
    private Cursor cursorClicked;

    private ImageIcon iconHoyo;
    private ImageIcon iconMonstruo;
    private ImageIcon iconOuch;

    Interfaz login;

    public Interfaz(){
        initComponents();
        monstruoColor = null;
        normalColor = Color.gray;
    }

    // Recibe puertos y lookup de RMI
    public Interfaz(Color monstruoColor, Color normalColor) {
        initComponents();
        this.monstruoColor = Color.GREEN;
        this.normalColor = Color.ORANGE;

        this.info = info;
        this.idJugador = idJugador;
        this.login = login;

        // ============================== IMAGEN DE LOS MONSTRUOS =====================
        System.out.println("./src/Imagenes/Ardilla_suicida.png");

        //iconHoyo = new ImageIcon(".\\src\\resources\\iconHoyo.png");
        iconMonstruo = new ImageIcon("./src/Imagenes/Ardilla_suicida.png");
        iconOuch = new ImageIcon("./src/Imagenes/Ardilla_suicida_ouch.png");
        // =============================================================================

        jTextField1.setEditable(false);
        txtPuntos.setEditable(false);

        setMouse();

        llenaArre(); // los botones se guardan en un arreglo
        setColores(); // pone todas las casillas en gris

        this.edoBotones(false);
    }

    public void setMouse() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage("src/Imagenes/cursor.png");
        Image cursorImageClicked = toolkit.getImage("src/Imagenes/cursorClicked.png");
        cursor = toolkit.createCustomCursor(cursorImage , new Point(0,0), "cursor");
        cursorClicked = toolkit.createCustomCursor(cursorImageClicked , new Point(0,0), "cursorClicked");
        setCursor(cursor);

        MouseDetected listener = new MouseDetected(cursor, cursorClicked, this);

        b11.addMouseListener(listener);
        b12.addMouseListener(listener);
        b13.addMouseListener(listener);
        b14.addMouseListener(listener);
        b21.addMouseListener(listener);
        b22.addMouseListener(listener);
        b23.addMouseListener(listener);
        b24.addMouseListener(listener);
        b31.addMouseListener(listener);
        b32.addMouseListener(listener);
        b33.addMouseListener(listener);
        b34.addMouseListener(listener);

        btnInicio.addMouseListener(listener);
        btnSal.addMouseListener(listener);

    }

    public void llenaArre(){
        botones[0] = b11;
        botones[1] = b12;
        botones[2] = b13;
        botones[3] = b14;
        botones[4] = b21;
        botones[5] = b22;
        botones[6] = b23;
        botones[7] = b24;
        botones[8] = b31;
        botones[9] = b32;
        botones[10] = b33;
        botones[11] = b34;
    }

    public void edoBotones(boolean edo){
        botones[0].setEnabled(edo);
        botones[1].setEnabled(edo);
        botones[2].setEnabled(edo);
        botones[3].setEnabled(edo);
        botones[4].setEnabled(edo);
        botones[5].setEnabled(edo);
        botones[6].setEnabled(edo);
        botones[7].setEnabled(edo);
        botones[8].setEnabled(edo);
        botones[9].setEnabled(edo);
        botones[10].setEnabled(edo);
        botones[11].setEnabled(edo);
    }

    public void setColores(){
        this.edoBotones(true);
        botones[0].setBackground(normalColor);
        botones[1].setBackground(normalColor);
        botones[2].setBackground(normalColor);
        botones[3].setBackground(normalColor);
        botones[4].setBackground(normalColor);
        botones[5].setBackground(normalColor);
        botones[6].setBackground(normalColor);
        botones[7].setBackground(normalColor);
        botones[8].setBackground(normalColor);
        botones[9].setBackground(normalColor);
        botones[10].setBackground(normalColor);
        botones[11].setBackground(normalColor);

        botones[0].setIcon(iconHoyo);
        botones[1].setIcon(iconHoyo);
        botones[2].setIcon(iconHoyo);
        botones[3].setIcon(iconHoyo);
        botones[4].setIcon(iconHoyo);
        botones[5].setIcon(iconHoyo);
        botones[6].setIcon(iconHoyo);
        botones[7].setIcon(iconHoyo);
        botones[8].setIcon(iconHoyo);
        botones[9].setIcon(iconHoyo);
        botones[10].setIcon(iconHoyo);
        botones[11].setIcon(iconHoyo);

    }

    // Escribe el ganador en el cuuadro de texto de la derecha.
    public void ganador(Player ganador){
        jTextField1.setText("El ganador \nes el jugador: \n" + ganador.getIdJugador());
        this.edoBotones(false);
        logout();
        btnInicio.setEnabled(true);
        avi.setText("Quiere jugar otra vez?");
        txtPuntos.setText("");

    }

    // Metodo que verifica que el boton que apretaron era el del
    // monstruo.
    public void click(int id){

        bonk.stop();
        bonk.setFramePosition(0);
        bonk.start();

        if(id==play.getCasillaEncendida()){
            try {
                botones[id].setIcon(iconOuch);
                jTextField1.setText("Ouch!!");
                Reply resp = new Reply(play.getIdJugadaActual(), id, player.getIdJugador());
                out.writeObject(resp);
            } catch (IOException ex) {
                Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            jTextField1.setText("Fallaste");
        }

    }

    //Metodos para cambiar cosas de la intrfaz desde Reciever.java
    public void newPlay(Move play)
    {
        this.play = play;
        avi.setText("Jugando");
        llenaPuntaje((HashPlayer) play.getJugadoresActuales());
        setColores();
        setMonstruo(play.getCasillaEncendida());
        edoBotones(true);
    }

    public void llenaPuntaje(HashPlayer jugadores)
    {
        StringBuilder puntaje = new StringBuilder();

        for (Map.Entry<String, Integer> entry : jugadores.entrySet()) {
            puntaje.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        txtPuntos.setText(puntaje.toString());
    }

    //Poone un nueva ardilla en posicioin
    public void setMonstruo(int id){
        botones[id].setIcon(iconMonstruo);
    }
    public void quitaInicio(){
        btnInicio.setEnabled(false);
    }
    public void habilitaInicio(){
        btnInicio.setEnabled(true);
    }

    //Para poner un boton en color diiferente, se usa cuando la partida
    //acaba
    public void setM(){
        botones[1].setBackground(Color.BLUE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        b11 = new javax.swing.JButton();
        b12 = new javax.swing.JButton();
        b14 = new javax.swing.JButton();
        b13 = new javax.swing.JButton();
        b21 = new javax.swing.JButton();
        b22 = new javax.swing.JButton();
        b24 = new javax.swing.JButton();
        b23 = new javax.swing.JButton();
        b34 = new javax.swing.JButton();
        b31 = new javax.swing.JButton();
        b32 = new javax.swing.JButton();
        b33 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        txtPuntos = new javax.swing.JTextArea();
        btnInicio = new javax.swing.JButton();
        btnSal = new javax.swing.JButton();
        avi = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        b11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b11ActionPerformed(evt);
            }
        });

        b12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b12ActionPerformed(evt);
            }
        });

        b14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b14ActionPerformed(evt);
            }
        });

        b13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b13ActionPerformed(evt);
            }
        });

        b21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b21ActionPerformed(evt);
            }
        });

        b22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b22ActionPerformed(evt);
            }
        });

        b24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b24ActionPerformed(evt);
            }
        });

        b23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b23ActionPerformed(evt);
            }
        });

        b34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b34ActionPerformed(evt);
            }
        });

        b31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b31ActionPerformed(evt);
            }
        });

        b32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b32ActionPerformed(evt);
            }
        });

        b33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b33ActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setText("Puntajes");

        txtPuntos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        btnInicio.setText("Iniciar");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        btnSal.setText("Salir");
        btnSal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(txtPuntos)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(b11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(b21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(b31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(b34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addGap(31, 31, 31)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addComponent(btnInicio)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(btnSal))
                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(avi)))))
                                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(28, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(b13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(b23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b24, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b21, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(b33, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b34, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b32, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(b31, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addComponent(jTextField1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnInicio)
                                        .addComponent(btnSal))
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(avi))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b11ActionPerformed
        // TODO add your handling code here:
        click(0);

    }//GEN-LAST:event_b11ActionPerformed

    private void b12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b12ActionPerformed
        // TODO add your handling code here:
        click(1);
    }//GEN-LAST:event_b12ActionPerformed

    private void b13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b13ActionPerformed
        // TODO add your handling code here:
        click(2);
    }//GEN-LAST:event_b13ActionPerformed

    private void b14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b14ActionPerformed
        // TODO add your handling code here:
        click(3);
    }//GEN-LAST:event_b14ActionPerformed

    private void b21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b21ActionPerformed
        // TODO add your handling code here:
        click(4);
    }//GEN-LAST:event_b21ActionPerformed

    private void b22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b22ActionPerformed
        // TODO add your handling code here:
        click(5);
    }//GEN-LAST:event_b22ActionPerformed

    private void b23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b23ActionPerformed
        // TODO add your handling code here:
        click(6);
    }//GEN-LAST:event_b23ActionPerformed

    private void b24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b24ActionPerformed
        // TODO add your handling code here:
        click(7);
    }//GEN-LAST:event_b24ActionPerformed

    private void b31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b31ActionPerformed
        // TODO add your handling code here:
        click(8);
    }//GEN-LAST:event_b31ActionPerformed

    private void b32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b32ActionPerformed
        // TODO add your handling code here:
        click(9);
    }//GEN-LAST:event_b32ActionPerformed

    private void b33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b33ActionPerformed
        // TODO add your handling code here:
        click(10);
    }//GEN-LAST:event_b33ActionPerformed

    private void b34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b34ActionPerformed
        // TODO add your handling code here:
        click(11);
    }//GEN-LAST:event_b34ActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed

        login();
        btnInicio.setEnabled(false);
        jTextField1.setText("");
        avi.setText("Esperando a otro Jugador");


    }//GEN-LAST:event_btnInicioActionPerformed

    private void login()
    {
        try {

            s = new Socket(info.getIpaddress(), info.getNo_port()); // Se establece la conexion con el servidor
            out = new ObjectOutputStream(s.getOutputStream());

            // EL PRIMER MENSAJE QUE RECIBA EL SERVIDOR DEBE CONTAR AL JUGADOR
            player = new Player(idJugador,0);
            out.writeObject(player);
            //System.out.println(player);

            // Se debe unir al grupo multicast para recibir las jugadas
            group = InetAddress.getByName("228.5.6.7"); // destination multicast group
            multisocket = new MulticastSocket(6789);
            multisocket.joinGroup(group);

            Recive receptor = new Recive(this, multisocket);

            receptor.start();

        }
        catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void logout()
    {
        Reply resp = new Reply(0, -1, player.getIdJugador());
        try {
            out.writeObject(resp);
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            s.close();
            multisocket.leaveGroup(group);
            multisocket.close();
        }
        catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

    private void btnSalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalActionPerformed

        //Este boton es para "salir" del juego
        if(s != null && !s.isClosed()){
            logout();
        }
        login.setVisible(true);
        this.setVisible(false);
        dispose();


    }//GEN-LAST:event_btnSalActionPerformed

    private javax.swing.JLabel avi;
    private javax.swing.JButton b11;
    private javax.swing.JButton b12;
    private javax.swing.JButton b13;
    private javax.swing.JButton b14;
    private javax.swing.JButton b21;
    private javax.swing.JButton b22;
    private javax.swing.JButton b23;
    private javax.swing.JButton b24;
    private javax.swing.JButton b31;
    private javax.swing.JButton b32;
    private javax.swing.JButton b33;
    private javax.swing.JButton b34;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnSal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea jTextField1;
    private javax.swing.JTextArea txtPuntos;
}
