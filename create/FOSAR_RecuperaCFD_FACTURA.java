// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:15:23 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR_RecuperaCFD_FACTURA.java

package create;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            Archivos, ConexionFirebird, ClienteChat, Muestra_Factura

public class FOSAR_RecuperaCFD_FACTURA
{

    void Inicializa_UltimoFolio()
        throws SQLException
    {
        if(TIPO == 1)
            Arch = new Archivos("C:\\FDE\\UltimoFolioFactura.txt");
        else
        if(TIPO == 2)
            Arch = new Archivos("C:\\FDE\\UltimoFolioDevolucion.txt");
        else
        if(TIPO == 3)
            Arch = new Archivos("C:\\FDE\\UltimoFolioNotasCredito.txt");
        String folio = Arch.Leer();
        int inx = folio.indexOf("|");
        folio = folio.substring(0, inx);
        UltimoFolio = Integer.parseInt(folio);
        System.out.println((new StringBuilder()).append("Ultimo Folio ---> ").append(UltimoFolio).toString());
        if(UltimoFolio < 0)
            UltimoFolio = 0;
    }

    void Inicializa_TIPO()
        throws SQLException
    {
        Archivos Arch = new Archivos("C:\\FDE\\TIPO.txt");
        String tipo = Arch.Leer();
        int inx = tipo.indexOf("|");
        tipo = tipo.substring(0, inx);
        TIPO = Integer.parseInt(tipo);
        System.out.println((new StringBuilder()).append("Tipo_Agente ---> ").append(TIPO).toString());
    }

    void Actualiza_UltimoFolio()
    {
        Arch.Escribir((new StringBuilder()).append(FOLIO).append("|").toString());
    }

    void Inicializa_Series_Factura()
    {
        Archivos Arch = new Archivos("C:\\FDE\\Series.txt");
        String series = Arch.Leer();
        int i = series.indexOf("|");
        series = (new StringBuilder()).append(series.substring(0, i)).append(" ").toString();
        i = series.indexOf(",");
        series = series.substring(0, i);
        series = series.replaceAll(" ", "");
        SERIE = series;
        System.out.println((new StringBuilder()).append("VALOR DE SERIE ---> ").append(series).append("|").toString());
    }

    void Inicializa_Series_Devoluciones()
    {
        Archivos Arch = new Archivos("C:\\FDE\\Series.txt");
        String series = Arch.Leer();
        int i = series.indexOf("|");
        series = (new StringBuilder()).append(series.substring(0, i)).append(" ").toString();
        i = series.indexOf(",");
        series = series.substring(i + 1);
        i = series.indexOf(",");
        series = series.substring(0, i);
        series = series.replaceAll(" ", "");
        SERIE = series;
        System.out.println((new StringBuilder()).append("VALOR DE SERIE ---> ").append(series).append("|").toString());
    }

    void Inicializa_Series_NotasCredito()
    {
        Archivos Arch = new Archivos("C:\\FDE\\Series.txt");
        String series = Arch.Leer();
        int i = series.indexOf("|");
        series = (new StringBuilder()).append(series.substring(0, i)).append(" ").toString();
        i = series.lastIndexOf(",");
        series = series.substring(i + 1);
        series = series.replaceAll(" ", "");
        SERIE = series;
        System.out.println((new StringBuilder()).append("VALOR DE SERIE ---> ").append(series).append("|").toString());
    }

    FOSAR_RecuperaCFD_FACTURA(boolean Inicializado)
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        TIPO = 1;
        UltimoFolio = 0;
        FOLIO = 0;
        SERIE = "MO";
        appcfdRUTA = "";
        appcfdPREFIJO = "";
        sql = "";
        cnx = new ConexionFirebird();
        INICIALIZADO = true;
        Inicializa_TIPO();
        if(TIPO == 1)
            Inicializa_Series_Factura();
        else
        if(TIPO == 2)
            Inicializa_Series_Devoluciones();
        else
        if(TIPO == 3)
            Inicializa_Series_NotasCredito();
        INICIALIZADO = Inicializado;
        if(INICIALIZADO)
        {
            new ClienteChat(TIPO);
            INICIALIZADO = false;
        }
        ConexionFirebird _tmp = cnx;
        ConexionFirebird.conectarFirebird();
        Inicializa_UltimoFolio();
        Recupera_UltimoFolio();
        cnx.finalizar();
        System.gc();
    }

    void Recupera_UltimoFolio()
        throws SQLException
    {
        FOLIO = Recupera_Folio();
        if(FOLIO > UltimoFolio)
        {
            RecuperaCFD();
            for(int i = 0; i < RUTAS.size(); i++)
            {
                RUTA = (ArrayList)RUTAS.get(i);
                appcfdRUTA = RUTA.get(0).toString();
                appcfdPREFIJO = RUTA.get(1).toString();
                if(appcfdRUTA != null && appcfdRUTA.length() > 1)
                    new Muestra_Factura(appcfdRUTA, appcfdPREFIJO);
                Actualiza_UltimoFolio();
            }

        }
    }

    int Recupera_Folio()
        throws SQLException
    {
        int UltimoFolio = -1;
        String sqlrfc = (new StringBuilder()).append("SELECT FOLIOACTUAL FROM CFD_FOLIOS WHERE SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            UltimoFolio = rs.getInt(1);
        System.out.println((new StringBuilder()).append("CONSULTA ---> ").append(sqlrfc).toString());
        System.out.println((new StringBuilder()).append("FOLIO ------->  ").append(UltimoFolio).toString());
        return UltimoFolio;
    }

    void RecuperaCFD()
        throws SQLException
    {
        String sqlry = (new StringBuilder()).append("SELECT RUTA, PREFIJO, FOLIO FROM CANCELADAS_FOSAR  WHERE SERIE = '").append(SERIE).append("'").append(" AND FOLIO > ").append(UltimoFolio).toString();
        System.out.println((new StringBuilder()).append("VALOR DE CONSULTA: \n ").append(sqlry).toString());
        rs = cnx.consulta(sqlry, true);
        RUTAS = new ArrayList();
        for(; rs.next(); System.out.println((new StringBuilder()).append("VALOR DE PREFIJO RECUPERADO --> ").append(appcfdPREFIJO).toString()))
        {
            RUTA = new ArrayList();
            appcfdRUTA = rs.getString("RUTA");
            appcfdPREFIJO = rs.getString("PREFIJO");
            if(appcfdRUTA == null)
            {
                System.out.println((new StringBuilder()).append("IMPOSIBLE RECUPERAR FOLIO --->  ").append(rs.getInt("FOLIO")).toString());
            } else
            {
                RUTA.add(appcfdRUTA);
                RUTA.add(appcfdPREFIJO);
                RUTAS.add(RUTA);
            }
            System.out.println((new StringBuilder()).append("VALOR DE RUTA RECUPERADA -----> ").append(appcfdRUTA).toString());
        }

        System.out.println((new StringBuilder()).append("VALOR DE PREFIJO ").append(appcfdPREFIJO).toString());
        System.out.println((new StringBuilder()).append("VALOR DE RUTA ").append(appcfdRUTA).toString());
    }

    public static void main(String args[])
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        boolean Inicializado = true;
        do
            try
            {
                new FOSAR_RecuperaCFD_FACTURA(Inicializado);
                Inicializado = false;
                System.gc();
                Thread.sleep(5000L);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        while(true);
    }

    int TIPO;
    int UltimoFolio;
    int FOLIO;
    ArrayList RUTAS;
    ArrayList RUTA;
    String SERIE;
    String appcfdRUTA;
    String appcfdPREFIJO;
    String sql;
    ConexionFirebird cnx;
    ResultSet rs;
    Archivos Arch;
    boolean INICIALIZADO;
}
