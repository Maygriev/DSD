/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatp2p;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Vanilo
 */
public class Cliente {

    private Socket con;
    private OutputStream ou;
    private OutputStreamWriter ouw;
    private BufferedWriter bfw;
    private javax.swing.JTextField textoMSG;
    private javax.swing.JTextPane textoChat;
    private InputStream in;
    private InputStreamReader inr;
    private BufferedReader bfr;

    public void iniciar(javax.swing.JTextField textoMSG,
            javax.swing.JTextPane textoChat) throws IOException {
        ou = con.getOutputStream();
        ouw = new OutputStreamWriter(ou);
        bfw = new BufferedWriter(ouw);
        this.textoMSG = textoMSG;
        this.textoChat = textoChat;
        bfw.write("Conexão estabelecida!\r\n");
        bfw.flush();
//        escutar();
    }

    public void enviarMensagem() throws IOException {
        String msg = textoMSG.getText();
        if (msg.equalsIgnoreCase("Sair")) {
            bfw.write("Desconectado \r\n");
            appendChat("Você foi desconectado! \r\n");
            this.fechar();
        } else {
            bfw.write(msg + "\r\n");
            appendChat("Você: " + msg + "\r\n");
        }
        bfw.flush();
        textoMSG.setText("");
    }

    public void escutar() throws IOException {

        in = con.getInputStream();
        inr = new InputStreamReader(in);
        bfr = new BufferedReader(inr);

        new Thread() {
            @Override
            public void run() {
                String msg = "";
                while (!"Sair".equalsIgnoreCase(msg)) {
                    try {
                        msg = bfr.readLine();
                        if (msg.equals("Sair")) {
                            appendChat("Servidor caiu! \r\n");
                        } else {
                            appendChat("Remoto: " + msg + "\r\n");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }

    public void appendChat(String txt) {
        int tam = textoChat.getStyledDocument().getLength();
        try {
            textoChat.getStyledDocument().insertString(tam, txt, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fechar() throws IOException {
        bfw.write("Sair \r\n");
        bfw.flush();
        con.close();
        System.out.println("Fechou a conexão");
    }

    public Cliente(Socket cliente) {
        this.con = cliente;
    }
}
