package create;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Referenced classes of package create:
//            ClienteChat

public class ControlCliente extends JFrame implements Runnable {
	private DataInputStream dataInput;
    private DataOutputStream dataOutput;
    Socket socket;
    int TIPO;

    public ControlCliente(Socket Socket, int tipo) {
        TIPO = 0;
        setAlwaysOnTop(true);
        TIPO = tipo;
        socket = Socket;

        try {
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            Thread hilo = new Thread(this);
            hilo.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void Enviar_Texto(String msj, int tipo) {
        try {
            dataOutput.writeUTF((new StringBuilder()).append(tipo).append("--").append(msj).toString());
        } catch(Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    String CalculaFecha() {
        return (new StringBuilder()).append("").append(new Date()).toString();
    }

    boolean isTexto(String texto) {
        int i = texto.indexOf("-");
        texto = texto.substring(0, i);
        return TIPO == Integer.parseInt(texto);
    }

    public void run() {
        String fecha = "";
        try {
            do {
                String texto;
                do {
                    System.out.println("CLIENTE INICIADO Y CONECTADO!!");
                    texto = dataInput.readUTF();
                } while(!isTexto(texto));
                setVisible(true);
                fecha = CalculaFecha();
                JOptionPane.showMessageDialog(this, texto, fecha, 0);
                setVisible(false);
            } while(true);
        } catch(Exception e) { 
            e.printStackTrace();
        }

        boolean ban = true;

        do {
            try {
                new ClienteChat(TIPO);
                ban = false;
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } while(ban);
    }
}
