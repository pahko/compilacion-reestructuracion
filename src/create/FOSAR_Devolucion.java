// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:07:08 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR_Devolucion.java

package create;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            ConexionFirebird

public class FOSAR_Devolucion
{

    FOSAR_Devolucion(String cve_doc)
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        CVE_DOC = "";
        FOLIO = 0;
        SERIE = "";
        NUM_REG = 0;
        sql = "";
        cnx = new ConexionFirebird();
        ConexionFirebird _tmp = cnx;
        ConexionFirebird.conectarFirebird();
        CVE_DOC = cve_doc;
        Localiza_Registro();
        Cancela_Factura();
        Actualiza_Bitacora();
        cnx.finalizar();
        System.gc();
    }

    void Localiza_Registro()
        throws SQLException
    {
        String sqly = (new StringBuilder()).append("SELECT FOLIO, SERIE, NUM_REG FROM CANCELADAS_FOSAR WHERE CVE_DOC = '").append(CVE_DOC).append("'").toString();
        rs = cnx.consulta(sqly, true);
        if(rs.next())
        {
            FOLIO = rs.getInt("FOLIO");
            SERIE = rs.getString("SERIE");
            NUM_REG = rs.getInt("NUM_REG");
        }
    }

    void Cancela_Factura()
        throws SQLException
    {
        String sqly = (new StringBuilder()).append("UPDATE CFD_COMPROBANTE SET ESTADO = 'X' WHERE FOLIO = ").append(FOLIO).append(" AND SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqly, false);
        cnx.commit();
        sqly = (new StringBuilder()).append("INSERT INTO CFD_CANCELACION (FOLIO, SERIE, IDEMPRESA, FECHA) VALUES( ").append(FOLIO).append(",'").append(SERIE).append("',1,CURRENT_TIMESTAMP)").toString();
        rs = cnx.consulta(sqly, false);
        cnx.commit();
    }

    void Actualiza_Bitacora()
        throws SQLException
    {
        String sqlry = (new StringBuilder()).append("UPDATE CANCELADAS_FOSAR SET ESTADO = 'C' WHERE NUM_REG = ").append(NUM_REG).append(" AND FOLIO = ").append(FOLIO).append(" AND SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlry, false);
        cnx.commit();
    }

    public static void main(String args[])
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        ArrayList Conceptos = new ArrayList();
        ArrayList Impuestos = new ArrayList();
        for(int i = 5; i < args.length; i += 2)
        {
            Conceptos.add(args[i]);
            Impuestos.add(args[i + 1]);
        }

        System.gc();
    }

    String CVE_DOC;
    int FOLIO;
    String SERIE;
    int NUM_REG;
    String sql;
    ConexionFirebird cnx;
    ResultSet rs;
}
