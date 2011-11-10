// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:58:35 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConexionParadox.java

package create;

import java.io.*;
import java.sql.*;
import java.util.Properties;

// Referenced classes of package create:
//            Archivos, Mensajes, ProgressBarSample

public class ConexionParadox
{

    public ConexionParadox()
    {
    }

    String Paradox()
    {
        String ruta = "";
        int i = 0;
        Archivos Arch = new Archivos("C:\\FDE\\Paradox.txt");
        ruta = Arch.Leer();
        i = ruta.indexOf("|");
        ruta = (new StringBuilder()).append("jdbc:paradox:///").append(ruta.substring(0, i)).toString();
        return ruta;
    }

    public void conectarParadox()
        throws ClassNotFoundException, SQLException, IOException
    {
        String databaseURL = Paradox();
        try
        {
            System.out.println((new StringBuilder("Ejecutando de conexion..[ ")).append(databaseURL).append(" ]").toString());
            String user = "";
            String password = "";
            String driverName = "com.hxtt.sql.paradox.ParadoxDriver";
            Class.forName(driverName);
            con = DriverManager.getConnection(databaseURL, user, password);
        }
        catch(Exception e)
        {
            m.GetMensaje((new StringBuilder()).append("ERROR DE CONEXION A DIRECTORIO DE DATOS:\n").append(databaseURL.substring(16)).append("\nFAVOR DE VERIFICAR SU EXISTENCIA Y CONEXION").toString());
            System.exit(1);
        }
        con.setAutoCommit(true);
    }

    public static void conectarSunODBC(String path)
        throws ClassNotFoundException, SQLException, IOException
    {
        try
        {
            String databaseURL = (new StringBuilder()).append("jdbc:paradox:///").append(path).toString();
            System.out.println((new StringBuilder("Ejecutando de conexion..[ ")).append(databaseURL).append(" ]").toString());
            String user = "";
            String password = "";
            String driverName = "com.hxtt.sql.paradox.ParadoxDriver";
            Class.forName(driverName);
            con = DriverManager.getConnection(databaseURL, user, password);
        }
        catch(Exception e)
        {
            m.GetMensaje("Servicio Paradox No Activo");
            System.exit(1);
        }
        con.setAutoCommit(true);
    }

    public ResultSet consulta(String qry, boolean result)
    {
        try
        {
            con.commit();
            stmt = con.createStatement(1004, 1008);
            stmt = con.createStatement();
            if(result)
                rs = stmt.executeQuery(qry);
            else
                stmt.execute(qry);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.exit(0);
        }
        return rs;
    }

    public ResultSet consultaBLOB(String qry, boolean result)
        throws SQLException
    {
        stmt = con.createStatement();
        if(result)
            rs = stmt.executeQuery(qry);
        else
            stmt.execute(qry);
        return rs;
    }

    public void prepara_consulta(String qry)
    {
        try
        {
            pst = con.prepareStatement(qry);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void aplicar_prepara_consulta()
    {
        try
        {
            pst.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            pst.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            con.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void consultaUpdate(String qry, boolean result)
        throws SQLException
    {
        stmt = con.createStatement();
        stmt.executeUpdate(qry);
    }

    public void libera(ResultSet rs)
        throws SQLException
    {
        if(rs != null)
            rs.close();
        if(rs != null)
            rs.close();
        if(stmt != null)
            stmt.close();
        rs = null;
        stmt = null;
    }

    public void commit()
        throws SQLException
    {
        con.commit();
    }

    public void finalizar()
        throws SQLException
    {
        try
        {
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String rollback()
    {
        try
        {
            con.rollback();
        }
        catch(SQLException e)
        {
            return e.getMessage();
        }
        return "";
    }

    public static void main(String args[])
        throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
    {
        try
        {
            ConexionParadox con = new ConexionParadox();
            con.conectarParadox();
            ResultSet rs = con.consulta("select * from FACT01", true);
            String folio = "";
            String tipdoc = "";
            for(; rs.next(); System.out.println((new StringBuilder()).append("Valores: FOLIO ---> ").append(folio).append("   TIPO DOC ---> ").append(tipdoc).toString()))
            {
                folio = rs.getString("CVE_DOC");
                tipdoc = rs.getString("TIP_DOC");
            }

            rs.close();
            con.finalizar();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    static String maquina;
    static String port;
    static String Installdir;
    static String InstallDb;
    static Driver d = null;
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static FileInputStream fproperties = null;
    static Properties p = new Properties();
    PreparedStatement pst;
    static Mensajes m = new Mensajes();
    static ProgressBarSample ps = new ProgressBarSample();

}
