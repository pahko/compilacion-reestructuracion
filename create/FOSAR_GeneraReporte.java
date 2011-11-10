// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:07:51 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR_GeneraReporte.java

package create;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Referenced classes of package create:
//            ConexionFirebird

public class FOSAR_GeneraReporte
{

    FOSAR_GeneraReporte(String FInicial, String FFinal, String serie)
        throws ClassNotFoundException, SQLException, IOException
    {
        cnx = new ConexionFirebird();
        FINICIAL = "";
        FFINAL = "";
        SERIE = "";
        FACTURAS = new ArrayList();
        REPORTE = new ArrayList();
        FINICIAL = FInicial;
        FFINAL = (new StringBuilder()).append(FFinal).append(" 23:59:59").toString();
        SERIE = serie;
        ConexionFirebird _tmp = cnx;
        ConexionFirebird.conectarFirebird();
        Genera_Reporte();
        cnx.finalizar();
        System.gc();
    }

    void Genera_Reporte()
        throws SQLException
    {
        Obten_Facturas();
        Obten_Detalles_Total();
        Imprime_Facturas();
    }

    void Imprime_Facturas()
    {
        System.out.println("FECHA-->ESTADO-->FOLIO-->SERIE-->RFC-->RAZON_SOCIAL-->SUBTOTAL-->DESCUENTOS-->SUBTOTAL-->IMPUESTOS-->TOTAL");
        for(int i = 0; i < FACTURAS.size(); i++)
        {
            ArrayList Factura = (ArrayList)FACTURAS.get(i);
            System.out.println(Factura);
        }

    }

    void Obten_Facturas()
        throws SQLException
    {
        String estado = "";
        String sql = (new StringBuilder()).append("SELECT FECHA, ESTADO, FOLIO, SERIE, RECEPTOR, ESTADO FROM CFD_COMPROBANTE WHERE FECHA >= CAST('").append(FINICIAL).append("' as date) ").append("and FECHA <= CAST('").append(FFINAL).append("' as timestamp) ").append(" and SERIE = '").append(SERIE).append("'").append(" ORDER BY FOLIO").toString();
        System.out.println((new StringBuilder()).append("VALOR DE CONSULTA: \n ").append(sql).toString());
        rs = cnx.consulta(sql, true);
        FACTURAS = new ArrayList();
        ArrayList Factura;
        for(; rs.next(); FACTURAS.add(Factura))
        {
            Factura = new ArrayList();
            Factura.add(rs.getDate("FECHA"));
            estado = rs.getString("ESTADO");
            if(estado.contains("T"))
                Factura.add("VIGENTE");
            else
                Factura.add("CANCELADO");
            Factura.add(new Integer(rs.getInt("FOLIO")));
            Factura.add(rs.getString("SERIE"));
            Factura.add(Integer.valueOf(rs.getInt("RECEPTOR")));
            System.out.println(Factura);
        }

    }

    void Obten_Detalles_Total()
        throws SQLException
    {
        String Folio = "";
        String entidad = "";
        double subtotal = 0.0D;
        double descuentos = 0.0D;
        double impuestos = 0.0D;
        for(int i = 0; i < FACTURAS.size(); i++)
        {
            ArrayList Factura = (ArrayList)FACTURAS.get(i);
            Folio = Factura.get(2).toString();
            entidad = Factura.get(4).toString();
            subtotal = Obten_Detalle_SubTotal(Folio);
            descuentos = Obten_Detalle_Descuentos(Folio);
            impuestos = Obten_Detalle_Impuestos(Folio);
            ArrayList Receptor = Obten_Detalle_Receptor(entidad);
            Factura.remove(4);
            Factura.add(Receptor.get(0));
            Factura.add(Receptor.get(1));
            Factura.add(new Double(subtotal));
            Factura.add(new Double(descuentos));
            subtotal -= descuentos;
            Factura.add(new Double(subtotal));
            Factura.add(new Double(impuestos));
            subtotal += impuestos;
            Factura.add(new Double(subtotal));
            REPORTE.add(Factura);
        }

    }

    double Obten_Detalle_SubTotal(String Folio)
        throws SQLException
    {
        double subtotal = 0.0D;
        String sqlrfc = (new StringBuilder()).append("SELECT SUM(IMPORTE) AS SUBTOTAL  FROM CFD_CONCEPTOS  WHERE FOLIO = ").append(Folio).append(" AND SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            subtotal = rs.getDouble("SUBTOTAL");
        System.out.println((new StringBuilder()).append("CONSULTA ---> ").append(sqlrfc).toString());
        System.out.println((new StringBuilder()).append("Subtotal ------->  ").append(subtotal).toString());
        return subtotal;
    }

    double Obten_Detalle_Descuentos(String Folio)
        throws SQLException
    {
        double subtotal = 0.0D;
        String sqlrfc = (new StringBuilder()).append("SELECT SUM(IMPORTE_TOTAL) AS DESCUENTOS FROM CFD_DESCUENTOS WHERE FOLIO = ").append(Folio).append(" AND SERIE = '").append(SERIE).append("'").toString();
        System.out.println((new StringBuilder()).append("CONSULTA ---> ").append(sqlrfc).toString());
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            subtotal = rs.getDouble("DESCUENTOS");
        System.out.println((new StringBuilder()).append("Descuentos ------->  ").append(subtotal).toString());
        return subtotal;
    }

    double Obten_Detalle_Impuestos(String Folio)
        throws SQLException
    {
        double subtotal = 0.0D;
        String sqlrfc = (new StringBuilder()).append("SELECT SUM(IMPORTE) AS IMPUESTOS FROM CFD_IMPUESTOS WHERE FOLIO = ").append(Folio).append(" AND SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            subtotal = rs.getDouble("IMPUESTOS");
        System.out.println((new StringBuilder()).append("CONSULTA ---> ").append(sqlrfc).toString());
        System.out.println((new StringBuilder()).append("Descuentos ------->  ").append(subtotal).toString());
        return subtotal;
    }

    ArrayList Obten_Detalle_Receptor(String entidad)
        throws SQLException
    {
        ArrayList receptor = new ArrayList();
        String sqlrfc = (new StringBuilder()).append("SELECT RFC, NOMBRE FROM CNET_ENTIDADES WHERE IDENTIDAD = ").append(entidad).toString();
        System.out.println((new StringBuilder()).append("CONSULTA ---> ").append(sqlrfc).toString());
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
        {
            receptor.add(rs.getString("RFC"));
            receptor.add(rs.getString("NOMBRE"));
        }
        System.out.println((new StringBuilder()).append("Receptor ------->  ").append(receptor).toString());
        return receptor;
    }

    public static void main(String args[])
        throws ClassNotFoundException, SQLException, IOException
    {
        new FOSAR_GeneraReporte("01/01/2011", "03/05/2011", "MO");
    }

    ConexionFirebird cnx;
    ResultSet rs;
    String FINICIAL;
    String FFINAL;
    String SERIE;
    ArrayList FACTURAS;
    public ArrayList REPORTE;
}
