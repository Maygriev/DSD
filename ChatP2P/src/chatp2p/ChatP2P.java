/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatp2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Vanilo
 */
public class ChatP2P {

    private static Layout tela = new Layout();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        iniciaServidor();
        iniciaChat();
//        iniciaCliente("127.0.0.1", 12345);
    }

    public static void iniciaServidor() throws IOException {
        JLabel lblMessage = new JLabel("Porta do Servidor:");
        JTextField txtPorta = new JTextField("12345");
        Object[] texts = {lblMessage, txtPorta};
        JOptionPane.showMessageDialog(null, texts);
        Servidor serv = new Servidor(new ServerSocket(
                Integer.parseInt(txtPorta.getText())));
        JOptionPane.showMessageDialog(null, "Servidor ativo na porta: "
                + txtPorta.getText());
        serv.addChat(tela.getTextoChat());

        new Thread() {
            public void run() {
                try {
                    serv.iniciar();
                } catch (IOException ex) {
                    Logger.getLogger(ChatP2P.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

//    public static void iniciaCliente(String ip, int porta) throws IOException {
//        Cliente clie = new Cliente(new Socket(ip, porta));
//        clie.iniciar();
//    }
    public static void iniciaChat() {
        tela.setVisible(true);
    }
}
