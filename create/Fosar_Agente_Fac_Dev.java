// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:01:59 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Fosar_Agente_Fac_Dev.java

package create;

import java.io.IOException;
import java.sql.SQLException;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            ServidorChat, Fosar_Agente, Fosar_Agente_Devoluciones

public class Fosar_Agente_Fac_Dev
{

    public Fosar_Agente_Fac_Dev()
    {
    }

    public static void main(String args[])
        throws SQLException, ClassNotFoundException, IOException, ComprobanteArchRefException
    {
        Runnable servidor = new ServidorChat();
        Thread hilo = new Thread(servidor);
        hilo.start();
        new Fosar_Agente();
        new Fosar_Agente_Devoluciones();
    }
}
