/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatp2p;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Vanilo
 */
public class Servidor {

    private ServerSocket servidor;
    private Socket cliente;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;
    private javax.swing.JTextPane textoChat;
    private boolean conectado = false;

    public void iniciar() throws IOException {
        System.out.println("Servidor aguardando Conexão na porta "
                + servidor.getLocalPort() + "...");
        
        while(true) {
            cliente = servidor.accept();
            System.out.println("Cliente: "
                    + cliente.getInetAddress() + " conectado");
            conectado = true;

            in = cliente.getInputStream();
            inr = new InputStreamReader(in);
            bfr = new BufferedReader(inr);

            String msg = "";

            while (!"Sair".equalsIgnoreCase(msg) && msg != null) {
                msg = bfr.readLine();
                appendChat("Remoto: " + msg + "\r\n");
            }
        }
    }

    public void addChat(javax.swing.JTextPane textoChat) {
        this.textoChat = textoChat;
    }

    public void appendChat(String txt) {
        int tam = textoChat.getStyledDocument().getLength();
        try {
            textoChat.getStyledDocument().insertString(tam, txt, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Servidor(ServerSocket servidor) {
        this.servidor = servidor;
    }
}
