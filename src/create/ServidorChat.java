// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ServidorChat.java

package create;

import java.io.PrintStream;
import java.net.ServerSocket;
import javax.swing.DefaultListModel;

// Referenced classes of package create:
//            HiloDeCliente

public class ServidorChat implements Runnable {

	private DefaultListModel charla;
	
	/**Manda llamar al metodo: ServidorChat_Inicio() **/
    public void run() {
        try {
            ServidorChat_Inicio();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    /**Constructor de la clase. Crea un objeto de la clase DefaultListModel. **/
    public ServidorChat() {
        charla = new DefaultListModel();
    }

    /**Este metodo crea un objeto de la clase ServidorSocket y de la clase HiloDeCliente **/
    public void ServidorChat_Inicio() {
        try {
            ServerSocket socketServidor = new ServerSocket(5557);
            do {
                System.out.println("DENTRO DEL SERVIDOR!!");
                java.net.Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new HiloDeCliente(charla, cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            } while (true);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.exit(1);
    }
}
