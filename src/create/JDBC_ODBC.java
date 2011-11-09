// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:16:32 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   JDBC_ODBC.java

package create;

import java.io.PrintStream;
import java.sql.*;

class JDBC_ODBC
{

    JDBC_ODBC()
    {
    }

    public static void main(String args[])
    {
        try
        {
            String urlBD = "jdbc:odbc:FOSA";
            String usuarioBD = "SYSDBA";
            String passwordBD = "masterkey";
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection conexion = DriverManager.getConnection(urlBD, usuarioBD, passwordBD);
            Statement select = conexion.createStatement();
            ResultSet resultadoSelect = select.executeQuery("SELECT MAX(NUM_REG) AS numreg FROM FACT01");
            System.out.println("COMANDO EXITOSO");
            System.out.println("numreg");
            System.out.println("------");
            int col = resultadoSelect.findColumn("numreg");
            for(boolean seguir = resultadoSelect.next(); seguir; seguir = resultadoSelect.next())
                System.out.println(resultadoSelect.getInt(col));

            resultadoSelect.close();
            select.close();
            conexion.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Error: SQLException");
            while(ex != null) 
            {
                System.out.println((new StringBuilder()).append("SQLState: ").append(ex.getSQLState()).toString());
                System.out.println((new StringBuilder()).append("Mensaje: ").append(ex.getMessage()).toString());
                System.out.println((new StringBuilder()).append("Vendedor: ").append(ex.getErrorCode()).toString());
                ex = ex.getNextException();
                System.out.println("");
            }
        }
        catch(Exception ex)
        {
            System.out.println("Se produjo un error inesperado");
        }
    }
}
