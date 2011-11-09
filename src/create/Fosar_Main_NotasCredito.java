// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:14:48 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Fosar_Main_NotasCredito.java

package create;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Referenced classes of package create:
//            ConexionParadox, ProgressBarSample, ConexionFirebird, FOSAR_ACT_CLIENTES, 
//            Mensajes, ClienteChat, FOSAR

public class Fosar_Main_NotasCredito
{

    public void CrearProgreso()
    {
    }

    Fosar_Main_NotasCredito(String tipo, String serie, int idtdocto, String nreg, String no_factura, String cclie, double importe, String no_Factura_ref)
        throws SQLException, ClassNotFoundException, IOException
    {
        SQL_CNET_ENTIDADES = "";
        SQL_CNET_ENTIDADES_2 = "";
        SQL_CNET_DOMICILIOS = "";
        SQL_CNET_DOMICILIOS_2 = "";
        SQL_CNET_CLIENTES = "";
        SQL_CNET_CLIENTES_2 = "";
        RFC = "";
        CVE_CLPV = "";
        SQL_CFD_COMPROBANTE = "";
        SQL_CFD_CONCEPTOS = new ArrayList();
        SQL_CFD_IMPUESTOS = new ArrayList();
        SQL_CFD_DESCUENTOS = "";
        SQL_CFD_DESCUENTOS_2 = "";
        CVE_DOC = "";
        TIPO = "I";
        SERIE = "MO";
        IDTDOCTO = 1;
        sql = "";
        cnx = new ConexionParadox();
        ps = new ProgressBarSample();
        IMPORTE = 0.0D;
        NO_FACTURA_REF = "";
        ERROR = 0;
        FOLIOASPEL = 0;
        TAMESP = 56;
        TIPO = tipo;
        SERIE = serie;
        IDTDOCTO = idtdocto;
        CVE_DOC = no_factura;
        CVE_CLPV = cclie;
        IMPORTE = importe;
        NO_FACTURA_REF = no_Factura_ref;
        Nucleo(nreg);
    }

    void Nucleo(String nreg)
        throws SQLException, ClassNotFoundException, IOException
    {
        NUM_REG = Integer.parseInt(nreg);
        cnx.conectarParadox();
        ConstruyeDocumento();
        ConstruyeConceptosImpuestos();
        ConstruyeCliente();
        cnx.finalizar();
        System.gc();
    }

    void ObtenSERIE(String CVE_DOC)
    {
        CVE_DOC = CVE_DOC.toUpperCase();
        CVE_DOC = CVE_DOC.replaceAll(" ", "");
        CVE_DOC = CVE_DOC.replaceAll("-", "");
        String serie = CVE_DOC.substring(0, 2);
        String folio = CVE_DOC.substring(2);
        System.out.println((new StringBuilder()).append("Valores SERIE ---> ").append(serie).toString());
        System.out.println((new StringBuilder()).append("Valores FOLIO ---> ").append(folio).toString());
        FOLIOASPEL = Integer.parseInt(folio);
    }

    void ConstruyeDocumento()
        throws SQLException, ClassNotFoundException, IOException
    {
        String obs = " ";
        ObtenSERIE(CVE_DOC);
        CFD_COMPROBANTE(obs);
    }

    void ConstruyeConceptosImpuestos()
        throws SQLException, ClassNotFoundException, IOException
    {
        String descrip = (new StringBuilder()).append("                                        NOTA DE CREDITO DE ").append(NO_FACTURA_REF).toString();
        float imp_tot = 0.0F;
        double cant = IMPORTE / 1.1599999999999999D;
        SQL_CFD_CONCEPTOS.add(CFD_CONCEPTO(1.0F, descrip, cant, cant, "1"));
        imp_tot = (float)(IMPORTE * 16D) / 116F;
        SQL_CFD_IMPUESTOS.add(CFD_IMPUESTO(imp_tot));
    }

    void Imprime(String cadena)
    {
        System.out.println(cadena);
    }

    void CNET_ENTIDADES(String nom, String tel, String rfc)
    {
        SQL_CNET_ENTIDADES = (new StringBuilder()).append("INSERT INTO CNET_ENTIDADES (identidad, NOMBRE, TELEFONO, RFC, IDEMPRESA, iddomicilio) VALUES ( IDENT ,'").append(nom).append("', '").append(tel).append("',").append("'").append(rfc).append("',1, IDDOM )").toString();
        RFC = rfc;
        Imprime(SQL_CNET_ENTIDADES);
    }

    void CNET_ENTIDADES_2(String nom, String tel, String rfc)
    {
        SQL_CNET_ENTIDADES_2 = (new StringBuilder()).append("UPDATE CNET_ENTIDADES  SET NOMBRE = '").append(nom).append("', ").append(" TELEFONO = '").append(tel).append("'").append(" WHERE identidad = IDENT").toString();
        Imprime(SQL_CNET_ENTIDADES_2);
    }

    void CNET_DOMICILIOS(String dir)
    {
        SQL_CNET_DOMICILIOS = (new StringBuilder()).append("INSERT INTO CNET_DOMICILIOS (iddomicilio,IDESTADO, IDCOLONIA, IDDELEGACION, identidad, CALLE, N_INTERIOR, N_EXTERIOR) VALUES ( IDDOM , 37, 79000, 2500, IDENT , '").append(dir).append("',0,0)").toString();
        Imprime(SQL_CNET_DOMICILIOS);
    }

    void CNET_DOMICILIOS_2(String dir)
    {
        SQL_CNET_DOMICILIOS_2 = (new StringBuilder()).append("UPDATE CNET_DOMICILIOS SET CALLE = '").append(dir).append("' WHERE iddomicilio = IDDOM").toString();
        Imprime(SQL_CNET_DOMICILIOS_2);
    }

    void CNET_CLIENTES(String nom, String tel)
    {
        SQL_CNET_CLIENTES = (new StringBuilder()).append("INSERT INTO CNET_CLIENTE (RAZON_SOCIAL, identidad,CONTACTO, TEL_CONTACTO) VALUES( '").append(nom).append("', IDENT , '").append(nom).append("', '").append(tel).append("')").toString();
        Imprime(SQL_CNET_CLIENTES);
    }

    void CNET_CLIENTES_2(String nom, String tel)
    {
        SQL_CNET_CLIENTES_2 = (new StringBuilder()).append("UPDATE CNET_CLIENTE SET RAZON_SOCIAL = '").append(nom).append("', ").append("CONTACTO = '").append(nom).append("', ").append("TEL_CONTACTO = '").append(tel).append("' ").append(" WHERE identidad = IDENT ").toString();
        Imprime(SQL_CNET_CLIENTES_2);
    }

    int Recupera_ClienteFDB(String cclie)
        throws ClassNotFoundException, SQLException, IOException
    {
        String fbsql = "";
        int num_reg = 0;
        ConexionFirebird fbx = new ConexionFirebird();
        ConexionFirebird _tmp = fbx;
        ConexionFirebird.conectarFirebird();
        cclie = cclie.replaceAll(" ", "");
        int ban = 0;
        do
        {
            fbsql = (new StringBuilder()).append("SELECT NUM_REG FROM FOSAR_CLIENTES WHERE CCLIE = '").append(cclie).append("'").toString();
            ResultSet fbrs = fbx.consulta(fbsql, true);
            if(fbrs.next())
            {
                num_reg = fbrs.getInt(1);
                ban = 2;
            } else
            {
                try
                {
                    new FOSAR_ACT_CLIENTES();
                    fbx = new ConexionFirebird();
                    ConexionFirebird _tmp1 = fbx;
                    ConexionFirebird.conectarFirebird();
                    cnx = new ConexionParadox();
                    cnx.conectarParadox();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                ban++;
            }
        } while(ban != 2);
        fbx.finalizar();
        System.out.println((new StringBuilder()).append("VALOR NUM_REG CLTS ---> ").append(num_reg).toString());
        return num_reg;
    }

    void ConstruyeCliente()
        throws SQLException, ClassNotFoundException, IOException
    {
        String nom = "";
        String tel = "";
        String rfc = "";
        String dir = "";
        boolean bandera = true;
        try
        {
            int num_reg_clts = -1;
            try
            {
                num_reg_clts = Recupera_ClienteFDB(CVE_CLPV);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                num_reg_clts = -1;
            }
            String sqlrfc;
            if(num_reg_clts == -1)
                sqlrfc = (new StringBuilder()).append("SELECT NOMBRE, TELEFONO, RFC, DIR , COLONIA , CODIGO ,POB  FROM CLIE01 WHERE CCLIE = '").append(CVE_CLPV).append("'").toString();
            else
                sqlrfc = (new StringBuilder()).append("SELECT NOMBRE, TELEFONO, RFC, DIR , COLONIA , CODIGO ,POB  FROM CLIE01 WHERE num_reg = ").append(num_reg_clts).toString();
            rs = cnx.consulta(sqlrfc, true);
            if(rs.next())
            {
                nom = rs.getString("NOMBRE");
                tel = rs.getString("TELEFONO");
                rfc = rs.getString("RFC");
                if(rfc != null && rfc.length() > 5)
                {
                    rfc = rfc.toUpperCase();
                    rfc = rfc.replaceAll(" ", "");
                    rfc = rfc.replaceAll("-", "");
                }
                dir = ProcesaCadena(rs.getString("DIR"), rs.getString("COLONIA"), rs.getString("CODIGO"), rs.getString("POB"));
                if(ValidaDatosCliente(rfc, nom, dir))
                {
                    CNET_ENTIDADES(nom, tel, rfc);
                    CNET_ENTIDADES_2(nom, tel, rfc);
                    CNET_DOMICILIOS(dir);
                    CNET_DOMICILIOS_2(dir);
                    CNET_CLIENTES(nom, tel);
                    CNET_CLIENTES_2(nom, tel);
                    bandera = false;
                } else
                {
                    ERROR = 1;
                    String msj = (new StringBuilder()).append("DATOS ERRONEOS DEL CLIENTE: ").append(CVE_CLPV).append("\nLOS DATOS OBTENIDOS:\n").append("RFC: ").append(rs.getString("RFC")).append("     RFC NO DEBE INCLUIR GUIONES, ESPACIOS, \321, &, ACENTOS Y DE UN TAMA\321O NO MAS DE 13 CARACTERES \n").append("RAZON SOCIAL: ").append(nom).append("\n").append("DIRECCION: ").append(dir).append("\n").append("FAVOR DE VERIFICARLOS Y CANCELAR DOCUMENTO DE ASPEL - SAE").toString();
                    Fosar_Main_NotasCredito _tmp = this;
                    ClienteChat.Envia_Mensaje(msj, TipMSJ);
                }
            } else
            {
                ERROR = 1;
                String msj = (new StringBuilder()).append("DATOS DE CLIENTE VACIOS O INCOMPLETOS FAVOR DE VERIFICAR EN ASPEL - SAE Y CANCELAR DOCUMENTO\nCLIENTE: ").append(CVE_CLPV).toString();
                Fosar_Main_NotasCredito _tmp1 = this;
                ClienteChat.Envia_Mensaje(msj, TipMSJ);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ERROR = 1;
            String msj = (new StringBuilder()).append("IMPOSIBLE LEER DATOS DE CLIENTE EN ASPEL - SAE\nFAVOR DE VERIFICAR DATOS EN ASPEL - SAE Y CANCELAR DOCUMENTO\nCLIENTE: ").append(CVE_CLPV).toString();
            Fosar_Main_NotasCredito _tmp2 = this;
            ClienteChat.Envia_Mensaje(msj, TipMSJ);
        }
    }

    boolean ValidaDatosCliente(String rfc, String nom, String dir)
    {
        boolean ban = true;
        if(rfc == null)
            ban = false;
        else
        if(rfc.length() > 13)
            ban = false;
        else
        if(rfc.indexOf("\321") > 0)
            ban = false;
        nom = nom.replaceAll(" ", "");
        if(nom.length() < 10)
            ban = false;
        dir = dir.replaceAll(" ", "");
        if(dir.length() < 10)
            ban = false;
        return ban;
    }

    String Ajustar(String cad, int len)
    {
        int ln = cad.length();
        for(int i = ln; i < len; i++)
            cad = (new StringBuilder()).append(cad).append(" ").toString();

        return cad;
    }

    String ProcesaCadena(String dir, String col, String cp, String pob)
    {
        String cadena = "";
        dir = Ajustar(dir, TAMESP);
        col = Ajustar((new StringBuilder()).append("COL. ").append(col).append(" C.P. ").append(cp).toString(), TAMESP);
        pob = Ajustar(pob, TAMESP);
        cadena = (new StringBuilder()).append(dir).append(col).append(pob).toString();
        return cadena;
    }

    void Forzar_Cliente()
        throws ClassNotFoundException, ClassNotFoundException, ClassNotFoundException, SQLException, IOException
    {
        cnx.finalizar();
        System.out.println("Eliminando Bloqueos .... ");
        System.out.println("Copiando Clientes .... ");
        System.out.println("Clientes copiados !!!!");
        cnx = new ConexionParadox();
        cnx.conectarParadox();
        SQL_CNET_ENTIDADES = "";
        SQL_CNET_DOMICILIOS = "";
        SQL_CNET_DOMICILIOS_2 = "";
        SQL_CNET_CLIENTES = "";
    }

    void CFD_COMPROBANTE(String obs)
    {
        SQL_CFD_COMPROBANTE = (new StringBuilder()).append("INSERT INTO CFD_COMPROBANTE (folio, SERIE,TIPO, NOAPROBACION, nocertificado, sello, vers, emisor, dom_emisor, receptor, dom_receptor, idempresa, observacion, idtdocto, sucursal, moneda, anioaprob)VALUES ( FOLIO ,'").append(SERIE).append("','").append(TIPO).append("',NAPROBACION,'NOCERTIFICADO', '  ', 2.0, 1, 1, IDENT , IDDOM , 1,").append("'").append(obs).append("',").append(IDTDOCTO).append(", 1, 'PSM', ANIOAPROB)").toString();
        Imprime(SQL_CFD_COMPROBANTE);
    }

    String CFD_CONCEPTO(float cant, String descrip, double imp, double vunit, String cveart)
    {
        String SQL_CFD_CONCEPTO = (new StringBuilder()).append("INSERT INTO CFD_CONCEPTOS ( folio, cantidad, descripcion, importe, valorunitario,  serie, idempresa, clavearticulo) Values ( FOLIO , ").append(cant).append(",'").append(descrip).append("',").append(imp).append(",").append(vunit).append(",'").append(SERIE).append("',1,'").append(cveart).append("')").toString();
        Imprime(SQL_CFD_CONCEPTO);
        return SQL_CFD_CONCEPTO;
    }

    String CFD_IMPUESTO(float imp_imp)
    {
        String SQL_CFD_IMPUESTO = (new StringBuilder()).append("INSERT INTO CFD_IMPUESTOS ( IDCNIMPUESTO, IDARTICULO, folio, SERIE, IMPORTE, TIPO, IDEMPRESA, IDCATIMPUESTO) Values (  1, 1, FOLIO , '").append(SERIE).append("', ").append(imp_imp).append(", '+', 1, 2)").toString();
        Imprime(SQL_CFD_IMPUESTO);
        return SQL_CFD_IMPUESTO;
    }

    public static void main(String args1[])
        throws SQLException, ClassNotFoundException, IOException
    {
    }

    public String SQL_CNET_ENTIDADES;
    public String SQL_CNET_ENTIDADES_2;
    public String SQL_CNET_DOMICILIOS;
    public String SQL_CNET_DOMICILIOS_2;
    public String SQL_CNET_CLIENTES;
    public String SQL_CNET_CLIENTES_2;
    public String RFC;
    public String CVE_CLPV;
    public String SQL_CFD_COMPROBANTE;
    public ArrayList SQL_CFD_CONCEPTOS;
    public ArrayList SQL_CFD_IMPUESTOS;
    public String SQL_CFD_DESCUENTOS;
    public String SQL_CFD_DESCUENTOS_2;
    int NUM_REG;
    String CVE_DOC;
    public String TIPO;
    public String SERIE;
    public int IDTDOCTO;
    String sql;
    ConexionParadox cnx;
    ResultSet rs;
    ResultSet rs1;
    FOSAR Fosar;
    ProgressBarSample ps;
    double IMPORTE;
    String NO_FACTURA_REF;
    public int ERROR;
    static Mensajes m = new Mensajes();
    static ClienteChat ClienteChat = new ClienteChat(0);
    static int TipMSJ = 3;
    public int FOLIOASPEL;
    int TAMESP;

}
