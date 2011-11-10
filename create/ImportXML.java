// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:16:18 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportXML.java

package create;

import com.codinet.facture.cfdv2.Comprobante;
import com.codinet.facture.util.MarshallCFDv2XML;
import java.io.*;
import java.sql.SQLException;
import org.jdom.JDOMException;

// Referenced classes of package create:
//            Mensajes, ConexionFirebird, Comprobantebd, ProgressBarSample

public class ImportXML
{

    public ImportXML()
        throws SQLException, FileNotFoundException, IOException
    {
        m = new Mensajes();
        cnx = new ConexionFirebird();
        cbd = new Comprobantebd();
        emisor = null;
        try
        {
            ConexionFirebird.conectarFirebird();
        }
        catch(ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }

    public static void CrearProgreso()
    {
        ps.CreaFrame("Importando XML");
        ps.CreaProgreso();
        ps.SetBorde("Generando Objeto Comprobante");
        ps.CreaBorde();
    }

    public static void main(String args[])
        throws SQLException, NumberFormatException, JDOMException, IOException
    {
        ImportXML imp = new ImportXML();
        CrearProgreso();
        ps.SetBorde("Definiendo factura");
        imp.cbd.estableceConexion(imp.cnx);
        Comprobante c = MarshallCFDv2XML.unmarshalCfdV2(new FileInputStream(args[0]));
        imp.emisor = c.getEmisor();
        if(args[2].equals(imp.emisor.getRfc()))
        {
            ps.SetBorde((new StringBuilder("Factura: ")).append(c.getFolio()).append(" ").append(c.getSerie()).append(" de Ingreso").toString());
            imp.cbd.insertaCFD(args[0], Integer.parseInt(args[1]), "I", Integer.parseInt(args[3]));
            imp.m.GetMensajes((new StringBuilder("Factura: ")).append(c.getFolio()).append(" ").append(c.getSerie()).append(" de Ingreso Importada").toString());
            ps.CerrarProgreso();
            System.out.println("Factura de ingreso");
        } else
        {
            ps.SetBorde((new StringBuilder("Factura: ")).append(c.getFolio()).append(" ").append(c.getSerie()).append(" de Egreso").toString());
            imp.cbd.insertaCFD(args[0], Integer.parseInt(args[1]), "E", Integer.parseInt(args[3]));
            imp.m.GetMensajes((new StringBuilder("Factura: ")).append(c.getFolio()).append(" ").append(c.getSerie()).append(" de Egreso Importada").toString());
            ps.CerrarProgreso();
            System.out.println("Factura de egreso");
        }
    }

    Mensajes m;
    static ProgressBarSample ps = new ProgressBarSample();
    ConexionFirebird cnx;
    Comprobantebd cbd;
    com.codinet.facture.cfdv2.Comprobante.Emisor emisor;
    String host;
    String bd;
    String us;
    String pss;
    String d;
    String sql;

}
