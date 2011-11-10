// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:09:53 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   HiloDeCliente.java

package servidor;

import java.io.*;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class HiloDeCliente
    implements Runnable, ListDataListener
{

    public HiloDeCliente(DefaultListModel charla, Socket socket)
    {
        this.charla = charla;
        this.socket = socket;
        try
        {
            dataInput = new DataInputStream(socket.getInputStream());
            dataOutput = new DataOutputStream(socket.getOutputStream());
            charla.addListDataListener(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            String texto = dataInput.readUTF();
            synchronized(charla)
            {
                charla.addElement(texto);
                System.out.println(texto);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void intervalAdded(ListDataEvent e)
    {
        String texto = (String)charla.getElementAt(e.getIndex0());
        try
        {
            dataOutput.writeUTF(texto);
        }
        catch(Exception excepcion)
        {
            excepcion.printStackTrace();
        }
    }

    public void intervalRemoved(ListDataEvent listdataevent)
    {
    }

    public void contentsChanged(ListDataEvent listdataevent)
    {
    }

    private DefaultListModel charla;
    private Socket socket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;
}
