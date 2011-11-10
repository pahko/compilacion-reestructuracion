// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:09:57 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ServidorChat.java

package servidor;

import java.io.PrintStream;
import java.net.ServerSocket;
import javax.swing.DefaultListModel;

// Referenced classes of package servidor:
//            HiloDeCliente

public class ServidorChat
{

    public static void main(String args[])
    {
        new ServidorChat();
    }

    public ServidorChat()
    {
        charla = new DefaultListModel();
        try
        {
            ServerSocket socketServidor = new ServerSocket(5557);
            System.out.println("SERVIDOR INICIADO!!!");
            do
            {
                java.net.Socket cliente = socketServidor.accept();
                Runnable nuevoCliente = new HiloDeCliente(charla, cliente);
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
            } while(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        new ServidorChat();
    }

    private DefaultListModel charla;
}
