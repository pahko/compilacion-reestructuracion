// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:02:50 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR_AlmacenaCFD.java

package create;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            ConexionFirebird

public class FOSAR_AlmacenaCFD
{

    String FormatoRuta(String ruta)
    {
        String tmp = "";
        int i = 0;
        tmp = ruta.substring(0, ruta.indexOf("\\\\") + 2);
        for(ruta = ruta.substring(ruta.indexOf("\\\\") + 2); (i = ruta.indexOf("\\")) > -1; ruta = ruta.substring(i + 1))
            tmp = (new StringBuilder()).append(tmp).append(ruta.substring(0, i + 1)).append("\\").toString();

        return tmp;
    }

    FOSAR_AlmacenaCFD(String ruta, String prefijo, String folio, String serie)
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        FOLIO = "";
        SERIE = "MO";
        appcfdRUTA = "";
        appcfdPREFIJO = "";
        sql = "";
        cnx = new ConexionFirebird();
        ConexionFirebird _tmp = cnx;
        ConexionFirebird.conectarFirebird();
        SERIE = serie;
        FOLIO = folio;
        appcfdPREFIJO = (new StringBuilder()).append(prefijo).append(".pdf").toString();
        appcfdRUTA = FormatoRuta(ruta);
        cnx.commit();
        Actualiza_Bitacora();
        cnx.finalizar();
        System.gc();
    }

    void Actualiza_Bitacora()
        throws SQLException
    {
        String sqlry = (new StringBuilder()).append("UPDATE CANCELADAS_FOSAR SET RUTA = '").append(appcfdRUTA).append("',").append(" PREFIJO = '").append(appcfdPREFIJO).append("'").append(" WHERE SERIE = '").append(SERIE).append("'").append(" AND FOLIO = ").append(FOLIO).toString();
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

    String FOLIO;
    String SERIE;
    public String appcfdRUTA;
    public String appcfdPREFIJO;
    String sql;
    ConexionFirebird cnx;
    ResultSet rs;
}
