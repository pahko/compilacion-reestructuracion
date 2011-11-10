// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:02:19 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Fosar_Agente_Todos.java

package create;

import java.io.IOException;
import java.sql.SQLException;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            Fosar_Agente, Fosar_Agente_Devoluciones, Fosar_Agente_NotasCredito

public class Fosar_Agente_Todos
{

    public Fosar_Agente_Todos()
    {
    }

    public static void main(String args[])
        throws SQLException, ClassNotFoundException, IOException, ComprobanteArchRefException
    {
        new Fosar_Agente(1);
        new Fosar_Agente_Devoluciones();
        new Fosar_Agente_NotasCredito();
        System.exit(0);
    }
}
