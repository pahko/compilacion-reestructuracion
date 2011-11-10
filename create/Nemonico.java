// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:19:47 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Nemonico.java

package create;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

// Referenced classes of package create:
//            ConexionFirebird

public class Nemonico
{

    public Nemonico()
        throws SQLException, FileNotFoundException, IOException
    {
        sql = null;
        cnx = new ConexionFirebird();
        try
        {
            ConexionFirebird.conectarFirebird();
        }
        catch(ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }

    public void Activa_Nemonico(String Version)
    {
        try
        {
            sql = (new StringBuilder("UPDATE CNET_MODULOS SET NEMONICO = '")).append(Version).append("' WHERE IDMODULO = 1").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("NEMONICO YA ACTIVO...");
        }
    }

    public static void main(String args[])
        throws FileNotFoundException, SQLException, IOException
    {
        Nemonico ne = new Nemonico();
        ne.Activa_Nemonico(args[0]);
    }

    String sql;
    ResultSet rs;
    ConexionFirebird cnx;
}
