// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:52:47 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ClienteChat.java

package create;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

// Referenced classes of package create:
//            ControlCliente

public class ClienteChat
{

    public static void main(String args[])
    {
        try
        {
            new ClienteChat(1);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void ObtenServer()
        throws IOException
    {
        System.out.println("Leyendo archivo de propiedades en ruta princial  C:\\FDE\\");
        fproperties = new FileInputStream("C:\\FDE\\firebird.properties");
        p.load(fproperties);
        maquina = p.getProperty("firebird.drda.host");
        if(maquina.equals("0.0.0.0"))
            maquina = "localhost";
        System.out.println("Cerrando Archivo en C:\\FDE\\ .......");
        fproperties.close();
        System.out.println("Archivo en C:\\FDE\\  CERRADO !!!!");
        break MISSING_BLOCK_LABEL_136;
        Exception ex;
        ex;
        ex.printStackTrace();
        System.out.println("Cerrando Archivo en C:\\FDE\\ .......");
        fproperties.close();
        System.out.println("Archivo en C:\\FDE\\  CERRADO !!!!");
        break MISSING_BLOCK_LABEL_136;
        Exception exception;
        exception;
        System.out.println("Cerrando Archivo en C:\\FDE\\ .......");
        fproperties.close();
        System.out.println("Archivo en C:\\FDE\\  CERRADO !!!!");
        throw exception;
    }

    public ClienteChat(int tipo)
    {
        TIPO = 0;
        TIPO = tipo;
        try
        {
            System.out.println("CLIENTE INICIADO");
            ObtenServer();
            socket = new Socket(maquina, 5557);
            control = new ControlCliente(socket, TIPO);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            boolean ban = true;
            do
                try
                {
                    new ClienteChat(TIPO);
                    ban = false;
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            while(ban);
        }
    }

    public void Envia_Mensaje(String texto, int tipo)
    {
        control.Enviar_Texto(texto, tipo);
    }

    private Socket socket;
    ControlCliente control;
    static FileInputStream fproperties = null;
    static Properties p = new Properties();
    static String maquina;
    int TIPO;

}
