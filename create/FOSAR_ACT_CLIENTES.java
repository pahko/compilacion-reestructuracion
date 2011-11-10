// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:00:30 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR_ACT_CLIENTES.java

package create;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            ConexionFirebird, ConexionParadox

public class FOSAR_ACT_CLIENTES
{

    FOSAR_ACT_CLIENTES()
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        sql = "";
        cnx = new ConexionFirebird();
        pnx = new ConexionParadox();
        ARTS = new ArrayList();
        MAX_NUM_REG = 0;
        ConexionFirebird _tmp = cnx;
        ConexionFirebird.conectarFirebird();
        pnx.conectarParadox();
        Recupera_Ult_Registro_FDB();
        Recupera_Arts_Paradox();
        Inserta_Arts_FDEB();
        cnx.finalizar();
        pnx.finalizar();
        System.gc();
    }

    void Recupera_Ult_Registro_FDB()
        throws SQLException
    {
        String sqlrfc = "SELECT MAX(NUM_REG) FROM FOSAR_CLIENTES";
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            MAX_NUM_REG = rs.getInt(1);
        System.out.println((new StringBuilder()).append("ULTIMO_NUM_REG ---> ").append(MAX_NUM_REG).toString());
    }

    void Recupera_Arts_Paradox()
        throws SQLException
    {
        String clv_art = "";
        String sqlry = (new StringBuilder()).append("SELECT NUM_REG, CCLIE FROM CLIE01  WHERE NUM_REG > ").append(MAX_NUM_REG).toString();
        System.out.println((new StringBuilder()).append("VALOR DE CONSULTA: \n ").append(sqlry).toString());
        rs = pnx.consulta(sqlry, true);
        ARTS = new ArrayList();
        ArrayList registro;
        for(; rs.next(); System.out.println((new StringBuilder()).append("REGISTRO --> ").append(registro).toString()))
        {
            registro = new ArrayList();
            registro.add(rs.getString("NUM_REG"));
            clv_art = rs.getString("CCLIE");
            if(clv_art != null)
                clv_art = clv_art.replaceAll(" ", "");
            else
                clv_art = "";
            registro.add(clv_art);
            ARTS.add(registro);
        }

    }

    void Inserta_Arts_FDEB()
        throws SQLException
    {
        for(int i = 0; i < ARTS.size(); i++)
        {
            ArrayList registro = (ArrayList)ARTS.get(i);
            Inserta_Art_FDB(registro.get(0).toString(), registro.get(1).toString());
        }

    }

    void Inserta_Art_FDB(String num_reg, String clv_art)
        throws SQLException
    {
        String sqlr = (new StringBuilder()).append("INSERT INTO FOSAR_CLIENTES (NUM_REG, CCLIE) VALUES( ").append(num_reg).append(",'").append(clv_art).append("')").toString();
        rs = cnx.consulta(sqlr, false);
        cnx.commit();
        System.out.println((new StringBuilder()).append("Insertando ---> ").append(num_reg).append(" ---- ").append(clv_art).toString());
    }

    public static void main(String args[])
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        try
        {
            new FOSAR_ACT_CLIENTES();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    String sql;
    ConexionFirebird cnx;
    ResultSet rs;
    ConexionParadox pnx;
    ArrayList ARTS;
    int MAX_NUM_REG;
}
