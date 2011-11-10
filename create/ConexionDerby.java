// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:56:31 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ConexionDerby.java

package create;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.derby.client.am.*;
import org.apache.derby.jdbc.ClientDriver;

// Referenced classes of package create:
//            Mensajes, RegQuery

public class ConexionDerby
{

    public ConexionDerby()
    {
        con = null;
        stmt = null;
        cd = new ClientDriver();
        p = new Properties();
        rs = null;
        m = new Mensajes();
    }

    public void conectarDerby()
        throws ClassNotFoundException, SQLException, FileNotFoundException, IOException
    {
        RegQuery rq = new RegQuery();
        Installdir = (new File(RegQuery.getCurrentCodinetFolderPath("Facture", "InstallDir"))).getPath();
        p.load(new FileInputStream((new StringBuilder(String.valueOf(Installdir))).append("\\Derby\\bin\\derby.properties").toString()));
        String maquina = p.getProperty("derby.drda.host");
        if(maquina.equals("0.0.0.0"))
            maquina = "localhost";
        String port = p.getProperty("derby.drda.portNumber");
        String url = (new StringBuilder("jdbc:derby://")).append(maquina).append(":").append(port).append("/etherea;schema=ETHEREA").toString();
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Properties properties = new Properties();
        properties.put("user", "etherea");
        properties.put("password", "etherea");
        try
        {
            con = (Connection)DriverManager.getConnection(url, properties);
        }
        catch(DisconnectException e)
        {
            m.GetMensaje("Servicio Derby No Activo");
        }
        con.setAutoCommit(true);
    }

    public ResultSet consulta(String qry, boolean result)
        throws SQLException
    {
        stmt = (Statement)con.createStatement(1004, 1008);
        if(result)
            rs = (ResultSet)stmt.executeQuery(qry);
        else
            stmt.execute(qry);
        return rs;
    }

    public ResultSet consultaBLOB(String qry, boolean result)
        throws SQLException
    {
        stmt = (Statement)con.createStatement();
        if(result)
            rs = (ResultSet)stmt.executeQuery(qry);
        else
            stmt.execute(qry);
        return rs;
    }

    public void consultaUpdate(String qry, boolean result)
        throws SQLException
    {
        stmt = (Statement)con.createStatement();
        stmt.executeUpdate(qry);
    }

    public void libera(ResultSet rs)
        throws SQLException
    {
        if(rs != null)
            rs.close();
        if(this.rs != null)
            this.rs.close();
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
    {
        try
        {
            con.close();
        }
        catch(SqlException e)
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

    private Connection con;
    private Statement stmt;
    private ClientDriver cd;
    Properties p;
    ResultSet rs;
    Mensajes m;
    static String Installdir;
}
