// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:26:58 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ServidorChat.java

package create;

import java.io.PrintStream;
import java.net.ServerSocket;
import javax.swing.DefaultListModel;

// Referenced classes of package create:
//            HiloDeCliente

public class ServidorChat
    implements Runnable
{

    public void run()
    {
        try
        {
            ServidorChat_Inicio();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public ServidorChat()
    {
        charla = new DefaultListModel();
    }

    public void ServidorChat_Inicio()
    {
        try
        {
            ServerSocket socketServidor = new ServerSocket(5557);
            do
            {
                System.out.println("DENTRO DEL SERVIDOR!!");
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
        System.exit(1);
    }

    private DefaultListModel charla;
}
