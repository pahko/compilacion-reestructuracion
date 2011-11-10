// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:14:19 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Fosar_Main.java

package create;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Referenced classes of package create:
//            ConexionParadox, ProgressBarSample, ConexionFirebird, FOSAR_ACT_CLIENTES, 
//            EJECUTA_COMANDO, FOSAR_ACT_ARTICULOS, Mensajes, ClienteChat, 
//            FOSAR

public class Fosar_Main
{

    int Tipo()
    {
        int tipo = 1;
        if(TIPO.equals("E"))
            tipo = 2;
        return tipo;
    }

    public void CrearProgreso()
    {
        ps.CreaFrame("RECOPILANDO INFORMACION DE ASPEL SAE");
        ps.CreaProgreso();
        ps.SetBorde("RECOPILANDO DATOS DE ASPEL - SAE");
        ps.CreaBorde();
    }

    Fosar_Main(String tipo, String serie, int idtdocto, String nreg)
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
        CVE_ART = "";
        DESC_TOT = 0.0F;
        DESC_FIN = 0.0F;
        CVE_VEND = "";
        NOM_VEND = "";
        sql = "";
        cnx = new ConexionParadox();
        ps = new ProgressBarSample();
        ERROR = 0;
        FOLIOASPEL = 0;
        TAMESP = 56;
        TIPO = tipo;
        SERIE = serie;
        IDTDOCTO = idtdocto;
        Nucleo(nreg);
    }

    Fosar_Main(String nreg)
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
        CVE_ART = "";
        DESC_TOT = 0.0F;
        DESC_FIN = 0.0F;
        CVE_VEND = "";
        NOM_VEND = "";
        sql = "";
        cnx = new ConexionParadox();
        ps = new ProgressBarSample();
        ERROR = 0;
        FOLIOASPEL = 0;
        TAMESP = 56;
        Nucleo(nreg);
        System.gc();
    }

    void Nucleo(String nreg)
        throws SQLException, ClassNotFoundException, IOException
    {
        NUM_REG = Integer.parseInt(nreg);
        CrearProgreso();
        ps.SetBorde("RECOPILANDO DATOS DE ASPEL - SAE");
        cnx.conectarParadox();
        ConstruyeDocumento();
        ConstruyeConceptosImpuestos();
        Construye_Descuentos();
        ObtenVendedor();
        ConstruyeCliente();
        cnx.finalizar();
        ps.CerrarProgreso();
        System.gc();
    }

    void ObtenVendedor()
    {
        String sqlrfc = (new StringBuilder()).append("SELECT NOMBRE  FROM VEND01 WHERE CLV_VEND = '").append(CVE_VEND).append("'").toString();
        try
        {
            rs = cnx.consulta(sqlrfc, true);
            if(rs.next())
                NOM_VEND = rs.getString("NOMBRE");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
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
        SQL_CNET_CLIENTES = (new StringBuilder()).append("INSERT INTO CNET_CLIENTE (RAZON_SOCIAL, identidad,CONTACTO, TEL_CONTACTO, EMAIL) VALUES( '").append(nom).append("', IDENT , '").append(nom).append("', '").append(tel).append("', '").append(CVE_VEND).append("-").append(NOM_VEND).append("')").toString();
        Imprime(SQL_CNET_CLIENTES);
    }

    void CNET_CLIENTES_2(String nom, String tel)
    {
        SQL_CNET_CLIENTES_2 = (new StringBuilder()).append("UPDATE CNET_CLIENTE SET RAZON_SOCIAL = '").append(nom).append("', ").append("CONTACTO = '").append(nom).append("', ").append("TEL_CONTACTO = '").append(tel).append("', ").append("EMAIL = '").append(CVE_VEND).append("-").append(NOM_VEND).append("' ").append(" WHERE identidad = IDENT ").toString();
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
                nom = (new StringBuilder()).append(CVE_CLPV).append("-").append(rs.getString("NOMBRE")).toString();
                tel = rs.getString("TELEFONO");
                rfc = rs.getString("RFC");
                if(rfc != null && rfc.length() > 5)
                {
                    rfc = rfc.toUpperCase();
                    rfc = rfc.replaceAll(" ", "");
                    rfc = rfc.replaceAll("-", "");
                }
                if(!rs.getString("DIR").equals(null) || !rs.getString("COLONIA").equals(null) || !rs.getString("CODIGO").equals(null) || !rs.getString("POB").equals(null))
                    dir = ProcesaCadena(rs.getString("DIR"), rs.getString("COLONIA"), rs.getString("CODIGO"), rs.getString("POB"));
                else
                    dir = "";
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
                    String msj = (new StringBuilder()).append("DATOS ERRONEOS DEL CLIENTE: ").append(CVE_CLPV).append("\nLOS DATOS OBTENIDOS:\n").append("RFC: ").append(rs.getString("RFC")).append("     RFC NO DEBE INCLUIR GUIONES, ESPACIOS, \321, &, ACENTOS Y DE UN TAMA\321O DE NO MAS DE 13 CARACTERES \n").append("RAZON SOCIAL: ").append(nom).append("\n").append("DIRECCION: ").append(dir).append("\n").append("FAVOR DE VERIFICARLOS Y CANCELAR DOCUMENTO DE ASPEL - SAE").toString();
                    Fosar_Main _tmp = this;
                    ClienteChat.Envia_Mensaje(msj, Tipo());
                }
            } else
            {
                ERROR = 1;
                String msj = (new StringBuilder()).append("DATOS DE CLIENTE VACIOS O INCOMPLETOS FAVOR DE VERIFICAR EN ASPEL - SAE Y CANCELAR DOCUMENTO\nCLIENTE: ").append(CVE_CLPV).toString();
                Fosar_Main _tmp1 = this;
                ClienteChat.Envia_Mensaje(msj, Tipo());
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ERROR = 1;
            String msj = (new StringBuilder()).append("IMPOSIBLE LEER DATOS DE CLIENTE EN ASPEL - SAE\nFAVOR DE VERIFICAR DATOS EN ASPEL - SAE Y CANCELAR DOCUMENTO\nCLIENTE: ").append(CVE_CLPV).toString();
            Fosar_Main _tmp2 = this;
            ClienteChat.Envia_Mensaje(msj, Tipo());
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
        ps.SetBorde((new StringBuilder()).append("FORZANDO LA OBTENCION DE CLIENTE DE ").append(CVE_DOC).toString());
        System.out.println("Eliminando Bloqueos .... ");
        new EJECUTA_COMANDO("C:\\FDE\\BORRAR.BAT", 1);
        System.out.println("Copiando Clientes .... ");
        new EJECUTA_COMANDO("C:\\FDE\\CLIENTES.BAT", 5);
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

    String CFD_CONCEPTO(float cant, String descrip, float imp, double vunit, String cveart)
    {
        String SQL_CFD_CONCEPTO = (new StringBuilder()).append("INSERT INTO CFD_CONCEPTOS ( folio, cantidad, descripcion, importe, valorunitario,  serie, idempresa, clavearticulo) Values (  FOLIO , ").append(cant).append(",'").append(descrip).append("',").append(imp).append(",").append(vunit).append(",'").append(SERIE).append("',1,'").append(cveart).append("')").toString();
        Imprime(SQL_CFD_CONCEPTO);
        return SQL_CFD_CONCEPTO;
    }

    String CFD_IMPUESTO(float imp_imp)
    {
        String SQL_CFD_IMPUESTO = (new StringBuilder()).append("INSERT INTO CFD_IMPUESTOS (IDCNIMPUESTO, IDARTICULO, folio, SERIE, IMPORTE, TIPO, IDEMPRESA, IDCATIMPUESTO) Values ( 1, 1, FOLIO , '").append(SERIE).append("', ").append(imp_imp).append(", '+', 1, 2)").toString();
        Imprime(SQL_CFD_IMPUESTO);
        return SQL_CFD_IMPUESTO;
    }

    void CFD_DESCUENTOS(float desc_tot)
    {
        SQL_CFD_DESCUENTOS = (new StringBuilder()).append("INSERT INTO CFD_DESCUENTOS (iddecuento, idcdecuento, idempresa, folio, serie, importe_total, tipo) Values (IDDESC, 1, 1, FOLIO, '").append(SERIE).append("',").append(desc_tot).append(",'D')").toString();
        Imprime(SQL_CFD_DESCUENTOS);
    }

    void CFD_DESCUENTOS_2(float desc_tot)
    {
        SQL_CFD_DESCUENTOS_2 = (new StringBuilder()).append("INSERT INTO CFD_DESCUENTOS (iddecuento, idcdecuento, idempresa, folio, serie, importe_total, tipo) Values (IDDESC, 2, 1, FOLIO, '").append(SERIE).append("',").append(desc_tot).append(",'D')").toString();
        Imprime(SQL_CFD_DESCUENTOS);
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
        String obs = "";
        String CV_DOC = "";
        float descuento = 0.0F;
        float descuento_2 = 0.0F;
        float imp_imp = 0.0F;
        String sqlrfc = (new StringBuilder()).append("SELECT CVE_DOC, OBS_COND, DOCANTSIG, CVE_CLPV, DES_TOT, DES_FIN, IMP_TOT2, CVE_VEND FROM FACT01 WHERE NUM_REG = ").append(NUM_REG).toString();
        rs = cnx.consulta(sqlrfc, true);
        if(rs.next())
        {
            CVE_DOC = rs.getString("CVE_DOC");
            obs = rs.getString("OBS_COND");
            CVE_CLPV = rs.getString("CVE_CLPV");
            ObtenSERIE(CVE_DOC);
            DESC_TOT = rs.getFloat("DES_TOT");
            DESC_FIN = rs.getFloat("DES_FIN");
            imp_imp = rs.getFloat("IMP_TOT2");
            CVE_VEND = rs.getString("CVE_VEND");
            CFD_COMPROBANTE(obs);
            SQL_CFD_IMPUESTOS.add(CFD_IMPUESTO(imp_imp));
        } else
        {
            ERROR = 2;
            String msj = "IMPOSIBLE OBTENER DATOS DE LA ULTIMA FACTURA\nFAVOR DE VERIFICAR ARCHIVOS DE ASPEL - SAE";
            Fosar_Main _tmp = this;
            ClienteChat.Envia_Mensaje(msj, Tipo());
        }
    }

    void Construye_Descuentos()
    {
        if(DESC_TOT > 0.0F)
            CFD_DESCUENTOS(DESC_TOT);
        if(DESC_FIN > 0.0F)
            CFD_DESCUENTOS_2(DESC_FIN);
    }

    int Recupera_ArticuloFDB(String cveart)
        throws ClassNotFoundException, SQLException, IOException
    {
        String fbsql = "";
        int num_reg = 0;
        ConexionFirebird fbx = new ConexionFirebird();
        ConexionFirebird _tmp = fbx;
        ConexionFirebird.conectarFirebird();
        cveart = cveart.replaceAll(" ", "");
        int ban = 0;
        do
        {
            fbsql = (new StringBuilder()).append("SELECT NUM_REG FROM FOSAR_ARTICULOS WHERE CLV_ART = '").append(cveart).append("'").toString();
            ResultSet fbrs = fbx.consulta(fbsql, true);
            if(fbrs.next())
            {
                num_reg = fbrs.getInt(1);
                ban = 2;
            } else
            {
                try
                {
                    new FOSAR_ACT_ARTICULOS();
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                ban++;
            }
        } while(ban != 2);
        fbx.finalizar();
        System.out.println((new StringBuilder()).append("VALOR NUM_REG ARTS ---> ").append(num_reg).toString());
        return num_reg;
    }

    ArrayList DatosArticulo(String cveart)
        throws ClassNotFoundException, SQLException, IOException
    {
        ArrayList articulo = new ArrayList();
        int num_reg_arts = -1;
        try
        {
            num_reg_arts = Recupera_ArticuloFDB(cveart);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            num_reg_arts = -1;
        }
        String sqlrfc;
        if(num_reg_arts == -1)
            sqlrfc = (new StringBuilder()).append("SELECT DESCR, PRECIO1, UNI_MED FROM INVE01 WHERE CLV_ART = '").append(cveart).append("'").toString();
        else
            sqlrfc = (new StringBuilder()).append("SELECT DESCR, PRECIO1, UNI_MED FROM INVE01 WHERE num_reg = ").append(num_reg_arts).toString();
        rs1 = cnx.consulta(sqlrfc, true);
        if(rs1.next())
        {
            articulo.add(rs1.getString("DESCR"));
            articulo.add(Float.valueOf(rs1.getFloat("PRECIO1")));
            articulo.add(rs1.getString("UNI_MED"));
        } else
        {
            ERROR = 3;
            String msj = (new StringBuilder()).append("IMPOSIBLE RECUPERAR DETALLE DE ARTICULO: ").append(cveart).append("\n").append("FAVOR DE VERIFICARLO EN ASPEL - SAE").toString();
            Fosar_Main _tmp = this;
            ClienteChat.Envia_Mensaje(msj, Tipo());
        }
        return articulo;
    }

    void ConstruyeConceptosImpuestos()
        throws SQLException, ClassNotFoundException, IOException
    {
        String cveart = "";
        boolean centinela = true;
        ps.SetBorde((new StringBuilder()).append("OBTENIENDO DETALLE DE ").append(CVE_DOC).toString());
        String sqlrfc = (new StringBuilder()).append("SELECT CANT, PREC, CVE_ART  FROM FA0TY1 WHERE NUM_REG > ").append(NUM_REG).append(" AND ").append("CVE_DOC = '").append(CVE_DOC).append("'").toString();
        try
        {
            for(rs = cnx.consulta(sqlrfc, true); rs.next();)
            {
                ArrayList inve_e = new ArrayList();
                float cant = rs.getFloat("CANT");
                float prec = rs.getFloat("PREC");
                cveart = rs.getString("CVE_ART");
                float imp = cant * prec;
                inve_e = DatosArticulo(cveart);
                String descrip;
                if(inve_e.size() > 0)
                {
                    descrip = (new StringBuilder()).append(inve_e.get(2).toString()).append("             ").append(cveart).append("             ").append(inve_e.get(0).toString()).toString();
                    descrip = descrip.replaceAll("'", " ");
                    double vunit = Double.parseDouble(inve_e.get(1).toString());
                } else
                {
                    descrip = (new StringBuilder()).append("               ").append(cveart).append("             ").append("ARTICULO ---> ").append(cveart).toString();
                    descrip = descrip.replaceAll("'", " ");
                    double vunit = prec / cant;
                }
                SQL_CFD_CONCEPTOS.add(CFD_CONCEPTO(cant, descrip, imp, prec, cveart));
                centinela = false;
            }

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String args[])
        throws SQLException, ClassNotFoundException, IOException
    {
        Fosar_Main fosar_Main = new Fosar_Main("85559");
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
    String CVE_ART;
    float DESC_TOT;
    float DESC_FIN;
    String CVE_VEND;
    String NOM_VEND;
    String sql;
    ConexionParadox cnx;
    ResultSet rs;
    ResultSet rs1;
    FOSAR Fosar;
    ProgressBarSample ps;
    public int ERROR;
    static Mensajes m = new Mensajes();
    static ClienteChat ClienteChat = new ClienteChat(0);
    public int FOLIOASPEL;
    int TAMESP;

}
