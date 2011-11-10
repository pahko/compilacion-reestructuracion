// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:20:11 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Prueba.java

package create;

import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;

// Referenced classes of package create:
//            Comprobantebd, ConexionFirebird

public class Prueba
{

    public Prueba()
    {
    }

    public static void main(String args[])
        throws IOException, SQLException, NumberFormatException, HeadlessException, ClassNotFoundException
    {
        Comprobantebd cbd = new Comprobantebd();
        ConexionFirebird cnx = new ConexionFirebird();
        try
        {
            ConexionFirebird _tmp = cnx;
            ConexionFirebird.conectarFirebird();
            cbd.estableceConexion(cnx);
            cbd.reporteMensual("C:\\Documents and Settings\\Usuario\\Escritorio\\", "20101201", "20101231");
            System.out.println("Reporte creado en C:\\DB !!");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
