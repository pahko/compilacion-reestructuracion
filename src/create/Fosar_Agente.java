package create;

import com.codinet.facture.cfdv2.exceptions.CFDException;
import gob.sat.sgi.SgiLibException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.com.codinet.err.ComprobanteArchRefException;
import org.jaxen.JaxenException;
import org.jdom.JDOMException;

// Referenced classes of package create:
//            ConexionParadox, Archivos, Fosar_Main, FOSAR, 
//            AppcfdExample

public class Fosar_Agente implements Runnable {
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
    public String TIPO;
    public String SERIE;
    public int IDTDOCTO;
    AppcfdExample appcfdexample;
    int ERROR;

    /**El constructor de la clase. Crea objetos de las clases: 
     * ConexionParadox()
     * Archivos() **/
    public Fosar_Agente() {
        sql = "";
        cnx = new ConexionParadox();
        Arch = new Archivos(1);
        Ultimo_Folio = "";
        DNS = "FOSAR";
        NUM_REG = -1;
        TIPO = "I";
        SERIE = "MO";
        IDTDOCTO = 1;
        ERROR = 0;
        System.out.println("BUSCANDO ......");
    }

    /**Contructor con diferentes parametros. Crea objetos de las clases:
     * ConexionParadox()
     * Archivos() 
     * Y manda llamar al metodo Nucleo() **/
    public Fosar_Agente(int i) throws SQLException, ClassNotFoundException,
        IOException {
        sql = "";
        cnx = new ConexionParadox();
        Arch = new Archivos(1);
        Ultimo_Folio = "";
        DNS = "FOSAR";
        NUM_REG = -1;
        TIPO = "I";
        SERIE = "MO";
        IDTDOCTO = 1;
        ERROR = 0;
        Nucleo();
    }

    /**Método que crea objeto del tipo Archivos() **/
    void Inicializa_Series() {
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

    /**Método booleano que retorna la variable ban **/
    boolean Consulta() {
        boolean ban = true;
        if(Ultimo_Folio.contentEquals("-1"))
            ban = false;
        return ban;
    }
    
    /**Método Nucleo. Manda llamar al método BUSCAR() si Consulta() = true **/
    synchronized void Nucleo() throws SQLException, ClassNotFoundException,
        IOException {
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
    }

    /**Constructor con diferentes parámetros. Crea objetos de las clases:
     * ConexionParadox ()
     * Archivos()
     * Y manda llamar al método Nucleo() **/
    public Fosar_Agente(String dns) throws SQLException, ClassNotFoundException,
        IOException {
        sql = "";
        cnx = new ConexionParadox();
        Arch = new Archivos(1);
        Ultimo_Folio = "";
        DNS = "FOSAR";
        NUM_REG = -1;
        TIPO = "I";
        SERIE = "MO";
        IDTDOCTO = 1;
        ERROR = 0;
        DNS = dns;
        Nucleo();
    }

    /**Este método crea un objeto de la clase FosarMain() **/
    void Genera_Scripts(String NUM_REG) throws SQLException,
        ClassNotFoundException, IOException {
        FosarMain = new Fosar_Main(TIPO, SERIE, IDTDOCTO, NUM_REG);
        ERROR = FosarMain.ERROR;
    }

    /**Este método crea objetos del tipo Fosar_Main() y manda llamar la clase:
     * FOSAR() **/
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
        Fosar = new FOSAR(CVE_DOC, serie, rfc, ent, ent2, dom, dom2, clt, clt2, comprobante, descuentos, descuentos_2, conceptos, impuestos, (new StringBuilder()).append("").append(NUM_REG).toString(), folioaspel);
        ERROR = Fosar.ERROR;
    }

    /**Este método crea un objeto de la clase:
     * AppcfdExample() **/
    void Ejecuta_Factura() throws ComprobanteArchRefException, SQLException,
        CFDException, JDOMException, HeadlessException, SgiLibException,
        JaxenException, IOException, NumberFormatException,
        ClassNotFoundException {
        String args[] = new String[4];
        args[0] = "1";
        args[1] = (new StringBuilder()).append("").append(Fosar.FOLIO).toString();
        args[2] = FosarMain.SERIE;
        args[3] = "1";
        appcfdexample = new AppcfdExample();
        appcfdexample.AppcfdExample_main(args);
    }

    /**Este método manda llamar a los métodos:
     * Ejecuta_Scripts()
     * Ejecuta_Factura() **/
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
            if(Fosar.ERROR == 0) {
                try {
                    System.out.println("GENERANDO FACTURA");
                    Ejecuta_Factura();
                    System.out.println("FACTURA GENERADA");
                    centinela = false;
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**Este método actualiza el contenido del archivo haciendo uso del objeto de la clase Archivos()• **/
    void Actualiza_Archivo(String NUM_REG) {
        Arch.Escribir((new StringBuilder()).append(NUM_REG).append("|").toString());
    }

    /**Este método crea un objeto tipo ArrayList(), hace conexion conla base de datos utilizando el onjeto
     * de la clase ConexionParadox() y actualiza el Archivo haciendo uso de los objetos de clase Archivos() **/
    void BUSCAR() throws ClassNotFoundException, SQLException, IOException {
        String sql = "";
        String num_reg = "";
        boolean centinela = true;
        cnx.conectarParadox();
        int i = 0;
        List_NUMREG = new ArrayList();
        sql = (new StringBuilder()).append("SELECT NUM_REG, TIP_DOC FROM FACT01 WHERE NUM_REG > ").append(Ultimo_Folio).toString();
        try {
            rs = cnx.consulta(sql, true);
            centinela = false;
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        System.gc();
        String tip_doc = "";
        int numreg = 0;
        do {
            if(!rs.next())
                break;
            tip_doc = rs.getString("TIP_DOC");
            numreg = rs.getInt("NUM_REG");
            if(tip_doc.equals("F"))
                List_NUMREG.add((new StringBuilder()).append("").append(numreg).toString());
        } while(true);
        cnx.finalizar();
        System.gc();
        if(List_NUMREG.size() > 0) {
            for(i = 0; i < List_NUMREG.size(); i++) {
                Obtener_Registros(List_NUMREG.get(i).toString());
                Actualiza_Archivo((new StringBuilder()).append("").append(NUM_REG).toString());
            }
        }

        else {
            Actualiza_Archivo((new StringBuilder()).append("").append(Ultimo_Folio).toString());
        }
    }

    /**Este método manda llamar al mñetodo Genera_Factura() **/
    public void Obtener_Registros(String num_reg) throws IOException,
        IOException, SQLException, ClassNotFoundException {
        NUM_REG = Integer.parseInt(num_reg);
        System.out.println((new StringBuilder()).append("GENERANDO FACTURA DE -----> ").append(num_reg).toString());
        Genera_Factura(num_reg);
        Ultimo_Folio = num_reg;
        System.out.println((new StringBuilder()).append("FACTURA GENERADA DE -----> ").append(num_reg).toString());
    }

    /**Método principal de la clase. Crea objeto de la clase Fosar_Agente() **/
    public static void main(String args[]) throws SQLException,
        ClassNotFoundException, IOException {
        new Fosar_Agente(1);
        System.exit(0);
    }

    /**Método que manda llamar al método Nucleo() y atrapa las posibles Ecepciones que puedan ocurrir. **/
    public void run() {
        try {
			Nucleo();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
