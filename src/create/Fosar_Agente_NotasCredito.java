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
//            Archivos, ConexionParadox, Fosar_Main_NotasCredito, FOSAR_NotasCredito, 
//            AppcfdExample, FOSAR_Devolucion

public class Fosar_Agente_NotasCredito {
    String sql;
    ConexionParadox cnx;
    ResultSet rs;
    Archivos Arch;
    String Ultimo_Folio;
    Fosar_Main_NotasCredito FosarMain;
    FOSAR_NotasCredito Fosar;
    String DNS;
    ArrayList List_NUMREG;
    ArrayList List_CVE_DOC;
    public String TIPO;
    public String SERIE;
    public int IDTDOCTO;
    int NUM_REG;
    String NO_FACTURA;
    String CCLIE;
    double IMPORTE;
    String NO_FACTURA_REF;
    boolean EXISTE;
    int ERROR;

    void Inicializa_Series() {
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

    boolean Consulta() {
        boolean ban = true;
        if(Ultimo_Folio.contentEquals("-1")) {
            ban = false;
        }
        return ban;
    }

    public Fosar_Agente_NotasCredito() throws SQLException,
        ClassNotFoundException, IOException, ComprobanteArchRefException {
        sql = "";
        cnx = new ConexionParadox();
        Arch = new Archivos(3);
        Ultimo_Folio = "";
        DNS = "";
        TIPO = "E";
        SERIE = "MR";
        IDTDOCTO = 4;
        NUM_REG = -1;
        NO_FACTURA = "";
        CCLIE = "";
        IMPORTE = 0.0D;
        NO_FACTURA_REF = "";
        EXISTE = false;
        ERROR = 0;
        Inicializa_Series();
        String folio = Arch.Leer();
        int inx = folio.indexOf("|");
        Ultimo_Folio = folio.substring(0, inx);
        System.out.println((new StringBuilder()).append("Ultimo Folio ---> ").append(Ultimo_Folio).toString());
        if(Consulta()) {
            BUSCAR();
            cnx.finalizar();
        }
        System.gc();
        System.exit(0);
    }

    void Genera_Scripts(String NUM_REG) throws SQLException,
        ClassNotFoundException, IOException {
        FosarMain = new Fosar_Main_NotasCredito(TIPO, SERIE, IDTDOCTO, NUM_REG, NO_FACTURA, CCLIE, IMPORTE, NO_FACTURA_REF);
        ERROR = FosarMain.ERROR;
    }

    void Ejecuta_Scripts() throws SQLException, ComprobanteArchRefException,
        ClassNotFoundException, IOException {
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
        Fosar = new FOSAR_NotasCredito(CVE_DOC, serie, rfc, ent, ent2, dom, dom2, clt, clt2, comprobante, descuentos, descuentos_2, conceptos, impuestos, (new StringBuilder()).append("").append(NUM_REG).toString(), folioaspel);
        EXISTE = Fosar.EXISTE;
        ERROR = Fosar.ERROR;
    }

    void Ejecuta_Factura() throws ComprobanteArchRefException, SQLException,
        CFDException, JDOMException, HeadlessException, SgiLibException,
        JaxenException, IOException, NumberFormatException,
        ClassNotFoundException {
        String args[] = new String[4];
        args[0] = "1";
        args[1] = (new StringBuilder()).append("").append(Fosar.FOLIO).toString();
        args[2] = FosarMain.SERIE;
        args[3] = "1";
        (new AppcfdExample()).AppcfdExample_main(args);
    }

    void Genera_Factura(String NUM_REG) throws IOException, SQLException,
        ClassNotFoundException {
        boolean centinela = true;
        System.out.println("GENERANDO SCRIPTS");
        Genera_Scripts(NUM_REG);
        System.out.println("SCRIPTS GENERADOS");
        if(FosarMain.ERROR == 0) {
            try {
                System.out.println("EJECUTANDO SCRIPTS");
                Ejecuta_Scripts();
                System.out.println("SCRIPTS EJECUTADOS");
                centinela = false;
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
            centinela = true;
            if(Fosar.ERROR == 0)
                try {
                    System.out.println("GENERANDO NOTA DE CREDITO");
                    if(!EXISTE)
                        Ejecuta_Factura();
                    System.out.println("NOTA DE CREDITO GENERADA");
                    centinela = false;
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
        }
    }

    void Actualiza_Archivo(String NUM_REG) {
        Arch.Escribir((new StringBuilder()).append(NUM_REG).append("|").toString());
    }

    void BUSCAR() throws ClassNotFoundException, SQLException, IOException,
        ComprobanteArchRefException {
        String sql = "";
        String num_reg = "";
        int tipmov = 0;
        String status = "";
        String refer = "";
        String docto = "";
        String no_factura = "";
        boolean acepta = true;
        List_NUMREG = new ArrayList();
        cnx.conectarParadox();
        sql = (new StringBuilder()).append("SELECT NUM_REG, NO_FACTURA, CCLIE, IMPORTE, DOCTO, TIPO_MOV, STATUS, REFER FROM CUEN01 WHERE NUM_REG > ").append(Ultimo_Folio).toString();
        rs = cnx.consulta(sql, true);
        do {
            if(!rs.next()) {
                break;
            }
            tipmov = rs.getInt("TIPO_MOV");
            status = rs.getString("STATUS");
            if(tipmov == 12 && status.equals("A") && acepta) {
                refer = rs.getString("REFER");
                docto = rs.getString("DOCTO");
                no_factura = rs.getString("NO_FACTURA");
                if(docto.equals(null)) {
                    docto = "";
                }
                if(refer.equals(null)) {
                    refer = "";
                }
                if(no_factura.equals(null)) {
                    no_factura = "";
                }
                acepta = TIPO_MOV(refer.toUpperCase(), docto.toUpperCase(), no_factura.toUpperCase());
                ArrayList registro = new ArrayList();
                registro.add((new StringBuilder()).append("").append(rs.getInt("NUM_REG")).toString());
                registro.add(rs.getString("DOCTO"));
                registro.add(rs.getString("CCLIE"));
                registro.add(Float.valueOf(rs.getFloat("IMPORTE")));
                registro.add(rs.getString("NO_FACTURA"));
                List_NUMREG.add(registro);
            }
        } while(true);
        cnx.finalizar();
        System.gc();
        if(List_NUMREG.size() > 0) {
            for(int i = 0; i < List_NUMREG.size(); i++) {
                Obtener_Registros((ArrayList)List_NUMREG.get(i));
                Actualiza_Archivo((new StringBuilder()).append("").append(NUM_REG).toString());
                if(ERROR != 0)
                    System.exit(1);
            }
        } else {
            Actualiza_Archivo((new StringBuilder()).append("").append(Ultimo_Folio).toString());
        }
    }

    boolean TIPO_MOV(String refer, String docto, String no_factura) {
        boolean acepta = false;
        if(refer.indexOf(SERIE) > 0) {
            acepta = true;
        } else {
            if(docto.indexOf(SERIE) > 0) {
                acepta = true;
            }
            else {
                if(no_factura.indexOf(SERIE) > 0) {
                    acepta = true;
                }
            }
        }
        return acepta;
    }

    void Cancelar_Factura(String cve_doc) throws SQLException,
        ComprobanteArchRefException, ClassNotFoundException, IOException {
        cve_doc = cve_doc.replaceAll("F-", "");
        new FOSAR_Devolucion(cve_doc);
    }

    public void Obtener_Registros(ArrayList registro) throws IOException,
        IOException, SQLException, ClassNotFoundException {
        NUM_REG = Integer.parseInt(registro.get(0).toString());
        if(registro.get(1) == null) {
            NO_FACTURA = "_";
        } else {
            NO_FACTURA = registro.get(1).toString();
        }
        CCLIE = registro.get(2).toString();
        IMPORTE = Double.parseDouble(registro.get(3).toString());
        NO_FACTURA_REF = registro.get(4).toString();
        String num_reg = registro.get(0).toString();
        System.out.println((new StringBuilder()).append("GENERANDO NOTA DE CREDITO DE -----> ").append(num_reg).toString());
        Genera_Factura(num_reg);
        Ultimo_Folio = num_reg;
        System.out.println((new StringBuilder()).append("NOTA DE CREDITO GENERADA DE -----> ").append(num_reg).toString());
    }

    public static void main(String args[]) throws SQLException, ClassNotFoundException, IOException, ComprobanteArchRefException {
        new Fosar_Agente_NotasCredito();
    }
}
