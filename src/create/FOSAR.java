// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:59:51 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR.java

package create;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            ConexionFirebird, Mensajes, ClienteChat

public class FOSAR
{

    FOSAR(String cve_doc, String serie, String rfc, String ent, String ent2, String dom, String dom2, 
            String clt, String clt2, String comprobante, String descuentos, String descuentos_2, ArrayList conceptos, ArrayList impuestos, 
            String numreg, int folioaspel)
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        RFC = "";
        IDDOM = 0;
        IDENT = 0;
        FOLIO = 0;
        SERIE = "MO";
        IDCONCEPTO = 0;
        IDIMPUESTO = 0;
        IDDESC = 0;
        NUM_REG = "";
        CVE_DOC = "";
        NOCERTIFICADO = "00001000000102255472";
        NAPROBACION = 0x3c8b2;
        ANIOAPROB = 2010;
        sql = "";
        cnx = new ConexionFirebird();
        ERROR = 0;
        ConexionFirebird _tmp = cnx;
        ConexionFirebird.conectarFirebird();
        RFC = rfc;
        SERIE = serie;
        NUM_REG = numreg;
        CVE_DOC = cve_doc;
        CalculaFolio();
        if(FOLIO != folioaspel)
        {
            ERROR = 4;
            String msj = (new StringBuilder()).append("ERROR DE COMPATIBILIDAD DE FOLIOS ENTRE ASPEL - SAE Y SISTEMA DE FACTURACION ELECTRONICA\nASPEL - SAE: FOLIO: ").append(folioaspel).append("\n").append("SIGUIENTE FOLIO EN SIST. FACT. ELECTRONICA: ").append(FOLIO).append("\n").append("FAVOR DE CANCELAR DOCUMENTO EN ASPEL - SAE").toString();
            if(SERIE.indexOf("D") > 0)
            {
                FOSAR _tmp1 = this;
                ClienteChat.Envia_Mensaje(msj, 2);
            } else
            {
                FOSAR _tmp2 = this;
                ClienteChat.Envia_Mensaje(msj, 1);
            }
        } else
        {
            if(!ExisteRFC())
            {
                InsertaCliente(ent, dom, clt);
            } else
            {
                Cnet_Entidades_2(ent2);
                Cnet_Domicilios_2(dom2);
                Cnet_Clientes_2(clt2);
            }
            cnx.commit();
            ObtenDatosCertificado();
            InsertaFacturas(comprobante, descuentos, descuentos_2, conceptos, impuestos);
            Actualiza_Bitacora();
        }
        cnx.finalizar();
        System.gc();
    }

    void ObtenDatosCertificado()
        throws SQLException
    {
        String sqlrfc = "SELECT SERIECERTIFICADO FROM CFD_CONFIG WHERE IDEMPRESA = 1 AND IDSUCURSAL = 1";
        rs = cnx.consulta(sqlrfc, true);
        rs.next();
        NOCERTIFICADO = rs.getString("SERIECERTIFICADO");
        sqlrfc = (new StringBuilder()).append("SELECT NOAPROVACION, AAPROB FROM CFD_FOLIOS WHERE SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlrfc, true);
        rs.next();
        NAPROBACION = rs.getInt("NOAPROVACION");
        ANIOAPROB = rs.getInt("AAPROB");
    }

    void Actualiza_Bitacora()
        throws SQLException
    {
        String cvedoc = CVE_DOC.replaceAll(" ", "");
        String sqlry = (new StringBuilder()).append("INSERT INTO CANCELADAS_FOSAR (NUM_REG, FOLIO, SERIE, ESTADO, CVE_DOC) VALUES( ").append(NUM_REG).append(",").append(FOLIO).append(",'").append(SERIE).append("','A','").append(cvedoc).append("')").toString();
        rs = cnx.consulta(sqlry, false);
        cnx.commit();
    }

    boolean ExisteRFC()
        throws SQLException
    {
        boolean existe = false;
        String sqlrfc = (new StringBuilder()).append("SELECT IDENTIDAD, IDDOMICILIO FROM CNET_ENTIDADES WHERE RFC = '").append(RFC).append("'").toString();
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
        {
            IDENT = rs.getInt("IDENTIDAD");
            IDDOM = rs.getInt("IDDOMICILIO");
            existe = true;
        }
        return existe;
    }

    void CalculaEntidad()
        throws SQLException
    {
        String sqlrfc = "SELECT MAX(IDENTIDAD) + 1 FROM CNET_ENTIDADES";
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            IDENT = rs.getInt(1);
        else
            IDENT = 1;
    }

    void CalculaDOM()
        throws SQLException
    {
        String sqlrfc = "SELECT MAX(IDDOMICILIO) + 1 FROM CNET_DOMICILIOS";
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            IDDOM = rs.getInt(1);
        else
            IDDOM = 1;
    }

    void Cnet_Entidades(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDENT", (new StringBuilder()).append("").append(IDENT).toString());
        sqry = sqry.replaceAll("IDDOM", (new StringBuilder()).append("").append(IDDOM).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cnet_Entidades_2(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDENT", (new StringBuilder()).append("").append(IDENT).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cnet_Domicilios(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDENT", (new StringBuilder()).append("").append(IDENT).toString());
        sqry = sqry.replaceAll("IDDOM", (new StringBuilder()).append("").append(IDDOM).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cnet_Domicilios_2(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDDOM", (new StringBuilder()).append("").append(IDDOM).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cnet_Clientes(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDENT", (new StringBuilder()).append("").append(IDENT).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cnet_Clientes_2(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDENT", (new StringBuilder()).append("").append(IDENT).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void InsertaCliente(String ent, String dom, String clt)
        throws SQLException
    {
        CalculaEntidad();
        CalculaDOM();
        Cnet_Entidades(ent);
        Cnet_Domicilios(dom);
        Cnet_Clientes(clt);
    }

    void CalculaFolio()
        throws SQLException
    {
        String sqlrfc = (new StringBuilder()).append("SELECT FOLIOACTUAL FROM CFD_FOLIOS WHERE SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            FOLIO = rs.getInt(1) + 1;
        else
            FOLIO = 1;
        if(FOLIO == 0)
            FOLIO = 1;
        System.out.println((new StringBuilder()).append("CONSULTA ---> ").append(sqlrfc).toString());
        System.out.println((new StringBuilder()).append("FOLIO ------->  ").append(FOLIO).toString());
    }

    void ActualizaFolio()
        throws SQLException
    {
        String sqlrfc = (new StringBuilder()).append("UPDATE CFD_FOLIOS SET FOLIOACTUAL = ").append(FOLIO).append(" WHERE SERIE = '").append(SERIE).append("'").toString();
        rs = cnx.consulta(sqlrfc, false);
        cnx.commit();
    }

    void CalculaConcepto()
        throws SQLException
    {
        String sqlrfc = "SELECT MAX(IDCONCEPTO) + 1 FROM CFD_CONCEPTOS";
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            IDCONCEPTO = rs.getInt(1);
        else
            IDCONCEPTO = 1;
        if(IDCONCEPTO == 0)
            IDCONCEPTO = 1;
    }

    void CalculaImpuesto()
        throws SQLException
    {
        String sqlrfc = "SELECT MAX(IDIMPUESTO) + 1 FROM CFD_IMPUESTOS";
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            IDIMPUESTO = rs.getInt(1);
        else
            IDIMPUESTO = 1;
        if(IDIMPUESTO == 0)
            IDIMPUESTO = 1;
    }

    void CalculaDescuentos()
        throws SQLException
    {
        String sqlrfc = "SELECT MAX(IDDECUENTO) + 1 FROM CFD_DESCUENTOS";
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
            IDDESC = rs.getInt(1);
        else
            IDDESC = 1;
        if(IDDESC == 0)
            IDDESC = 1;
    }

    void Cfd_Comprobante(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDENT", (new StringBuilder()).append("").append(IDENT).toString());
        sqry = sqry.replaceAll("IDDOM", (new StringBuilder()).append("").append(IDDOM).toString());
        sqry = sqry.replaceAll("FOLIO", (new StringBuilder()).append("").append(FOLIO).toString());
        sqry = sqry.replaceAll("NOCERTIFICADO", (new StringBuilder()).append("").append(NOCERTIFICADO).toString());
        sqry = sqry.replaceAll("ANIOAPROB", (new StringBuilder()).append("").append(ANIOAPROB).toString());
        sqry = sqry.replaceAll("NAPROBACION", (new StringBuilder()).append("").append(NAPROBACION).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
        ActualizaFolio();
    }

    void Cfd_Conceptos(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("FOLIO", (new StringBuilder()).append("").append(FOLIO).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cfd_Impuestos(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("FOLIO", (new StringBuilder()).append("").append(FOLIO).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void Cfd_Descuentos(String sqry)
        throws SQLException
    {
        sqry = sqry.replaceAll("IDDESC", (new StringBuilder()).append("").append(IDDESC).toString());
        sqry = sqry.replaceAll("FOLIO", (new StringBuilder()).append("").append(FOLIO).toString());
        System.out.println(sqry);
        rs = cnx.consulta(sqry, false);
        cnx.commit();
    }

    void InsertaFacturas(String comprobante, String descuentos, String descuentos_2, ArrayList conceptos, ArrayList impuestos)
        throws SQLException
    {
        CalculaFolio();
        CalculaConcepto();
        CalculaImpuesto();
        Cfd_Comprobante(comprobante);
        if(descuentos.length() > 1)
        {
            CalculaDescuentos();
            Cfd_Descuentos(descuentos);
        }
        if(descuentos_2.length() > 1)
        {
            CalculaDescuentos();
            Cfd_Descuentos(descuentos_2);
        }
        if(impuestos.size() > 0)
            Cfd_Impuestos(impuestos.get(0).toString());
        for(int i = 0; i < conceptos.size(); i++)
        {
            Cfd_Conceptos(conceptos.get(i).toString());
            IDCONCEPTO++;
            IDIMPUESTO++;
        }

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

    String RFC;
    int IDDOM;
    int IDENT;
    public int FOLIO;
    String SERIE;
    int IDCONCEPTO;
    int IDIMPUESTO;
    int IDDESC;
    String NUM_REG;
    String CVE_DOC;
    String NOCERTIFICADO;
    int NAPROBACION;
    int ANIOAPROB;
    String sql;
    ConexionFirebird cnx;
    ResultSet rs;
    int ERROR;
    static Mensajes m = new Mensajes();
    static ClienteChat ClienteChat = new ClienteChat(0);

}
