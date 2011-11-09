// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:01:54 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Fosar_Agente_Devoluciones.java

package create;

import com.codinet.facture.cfdv2.exceptions.CFDException;
import gob.sat.sgi.SgiLibException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.com.codinet.err.ComprobanteArchRefException;
import org.jaxen.JaxenException;
import org.jdom.JDOMException;

// Referenced classes of package create:
//            Archivos, ConexionParadox, Fosar_Main, FOSAR, 
//            AppcfdExample

public class Fosar_Agente_Devoluciones
{

    void Inicializa_Series()
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

    boolean Consulta()
    {
        boolean ban = true;
        if(Ultimo_Folio.contentEquals("-1"))
            ban = false;
        return ban;
    }

    void Bloquea_Archivo()
    {
    }

    public Fosar_Agente_Devoluciones()
        throws SQLException, ClassNotFoundException, IOException, ComprobanteArchRefException
    {
        sql = "";
        cnx = new ConexionParadox();
        Arch = new Archivos(2);
        Ultimo_Folio = "";
        DNS = "";
        NUM_REG = -1;
        TIPO = "E";
        SERIE = "MD";
        IDTDOCTO = 4;
        Inicializa_Series();
        String folio = Arch.Leer();
        int inx = folio.indexOf("|");
        Ultimo_Folio = folio.substring(0, inx);
        System.out.println((new StringBuilder()).append("Ultimo Folio ---> ").append(Ultimo_Folio).toString());
        Bloquea_Archivo();
        if(Consulta())
        {
            BUSCAR();
            cnx.finalizar();
        }
        System.gc();
    }

    void Genera_Scripts(String NUM_REG)
        throws SQLException, ClassNotFoundException, IOException
    {
        FosarMain = new Fosar_Main(TIPO, SERIE, IDTDOCTO, NUM_REG);
    }

    void Ejecuta_Scripts()
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        String serie = FosarMain.SERIE;
        String rfc = FosarMain.RFC;
        String ent = FosarMain.SQL_CNET_ENTIDADES;
        String ent2 = FosarMain.SQL_CNET_ENTIDADES_2;
        String dom = FosarMain.SQL_CNET_DOMICILIOS;
        String dom2 = FosarMain.SQL_CNET_DOMICILIOS_2;
        String clt = FosarMain.SQL_CNET_CLIENTES;
        String clt2 = FosarMain.SQL_CNET_CLIENTES_2;
        String comprobante = FosarMain.SQL_CFD_COMPROBANTE;
        String descuentos = FosarMain.SQL_CFD_DESCUENTOS;
        String descuentos_2 = FosarMain.SQL_CFD_DESCUENTOS_2;
        ArrayList conceptos = FosarMain.SQL_CFD_CONCEPTOS;
        ArrayList impuestos = FosarMain.SQL_CFD_IMPUESTOS;
        String CVE_DOC = FosarMain.CVE_DOC;
        int folioaspel = FosarMain.FOLIOASPEL;
        Fosar = new FOSAR(CVE_DOC, serie, rfc, ent, ent2, dom, dom2, clt, clt2, comprobante, descuentos, descuentos_2, conceptos, impuestos, (new StringBuilder()).append("").append(NUM_REG).toString(), folioaspel);
    }

    void Ejecuta_Factura()
        throws ComprobanteArchRefException, SQLException, CFDException, JDOMException, HeadlessException, SgiLibException, JaxenException, IOException, NumberFormatException, ClassNotFoundException
    {
        String args[] = new String[4];
        args[0] = "1";
        args[1] = (new StringBuilder()).append("").append(Fosar.FOLIO).toString();
        args[2] = FosarMain.SERIE;
        args[3] = "1";
        (new AppcfdExample()).AppcfdExample_main(args);
    }

    void Genera_Factura(String NUM_REG)
        throws IOException, SQLException, ClassNotFoundException
    {
        boolean centinela = true;
        System.out.println("GENERANDO SCRIPTS");
        Genera_Scripts(NUM_REG);
        System.out.println("SCRIPTS GENERADOS");
        if(FosarMain.ERROR == 0)
        {
            try
            {
                System.out.println("EJECUTANDO SCRIPTS");
                Ejecuta_Scripts();
                System.out.println("SCRIPTS EJECUTADOS");
                centinela = false;
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            centinela = true;
            if(Fosar.ERROR == 0)
                try
                {
                    System.out.println("GENERANDO FACTURA");
                    Ejecuta_Factura();
                    System.out.println("FACTURA GENERADA");
                    centinela = false;
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
        }
    }

    void Actualiza_Archivo(String NUM_REG)
    {
        Arch.Escribir((new StringBuilder()).append(NUM_REG).append("|").toString());
    }

    void BUSCAR()
        throws ClassNotFoundException, SQLException, IOException, ComprobanteArchRefException
    {
        String sql = "";
        String num_reg = "";
        String tipdoc = "";
        List_NUMREG = new ArrayList();
        List_CVE_DOC = new ArrayList();
        cnx.conectarParadox();
        sql = (new StringBuilder()).append("SELECT NUM_REG, TIP_DOC, CVE_DOC FROM FACT01 WHERE NUM_REG > ").append(Ultimo_Folio).toString();
        rs = cnx.consulta(sql, true);
        do
        {
            if(!rs.next())
                break;
            tipdoc = rs.getString("TIP_DOC");
            if(tipdoc.equals("D"))
            {
                List_NUMREG.add((new StringBuilder()).append("").append(rs.getInt("NUM_REG")).toString());
                List_CVE_DOC.add(rs.getString("CVE_DOC"));
            }
        } while(true);
        cnx.finalizar();
        System.gc();
        if(List_NUMREG.size() > 0)
        {
            for(int i = 0; i < List_NUMREG.size(); i++)
            {
                Obtener_Registros(List_NUMREG.get(i).toString());
                Cancelar_Factura(List_CVE_DOC.get(i).toString());
            }

            Actualiza_Archivo((new StringBuilder()).append("").append(NUM_REG).toString());
        } else
        {
            Actualiza_Archivo((new StringBuilder()).append("").append(Ultimo_Folio).toString());
        }
    }

    void Cancelar_Factura(String cve_doc)
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        cve_doc = cve_doc.replaceAll("F-", "");
        cve_doc = cve_doc.replaceAll(" ", "");
        BUSCAR_CVE_DOC_CUEN(cve_doc);
    }

    private void BUSCAR_CVE_DOC_CUEN(String s)
    {
    }

    public void Obtener_Registros(String num_reg)
        throws IOException, IOException, SQLException, ClassNotFoundException
    {
        NUM_REG = Integer.parseInt(num_reg);
        System.out.println((new StringBuilder()).append("GENERANDO NOTA DE CREDITO DE -----> ").append(num_reg).toString());
        Genera_Factura(num_reg);
        Ultimo_Folio = num_reg;
        System.out.println((new StringBuilder()).append("NOTA DE CREDITO GENERADA DE -----> ").append(num_reg).toString());
    }

    public static void main(String args[])
        throws SQLException, ClassNotFoundException, IOException, ComprobanteArchRefException
    {
        new Fosar_Agente_Devoluciones();
        System.exit(1);
    }

    String sql;
    ConexionParadox cnx;
    ResultSet rs;
    Archivos Arch;
    String Ultimo_Folio;
    Fosar_Main FosarMain;
    FOSAR Fosar;
    String DNS;
    int NUM_REG;
    ArrayList List_NUMREG;
    ArrayList List_CVE_DOC;
    public String TIPO;
    public String SERIE;
    public int IDTDOCTO;
}
