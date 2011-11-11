package create;

import java.io.*;
import java.sql.*;
import java.util.Properties;


public class ConexionFirebird {

    static String maquina;
    static String port;
    static String ruta;
    static String Installdir;
    static String InstallDb;
    static Driver d = null;
    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static FileInputStream fproperties = null;
    static Properties p = new Properties();
    PreparedStatement pst;
    static Mensajes m = new Mensajes();
    static ProgressBarSample ps = new ProgressBarSample();

    public static void conectarFirebird() throws Exception {
    	try {
	        System.out.println("Ruta de instalacion de Base Facture " +
	        		"FB..C:\\FDE\\ ");
	        System.out.println("Leyendo archivo de propiedades en ruta " +
	        		"princial  C:\\FDE\\");
	        fproperties = new FileInputStream("C:\\FDE\\firebird.properties");
	        p.load(fproperties);
	        maquina = p.getProperty("firebird.drda.host");
	        if (maquina.equals("0.0.0.0")) {
	        	maquina = "localhost";
	        }
	        port = p.getProperty("firebird.drda.portNumber");
	        ruta = p.getProperty("firebird.drda.path");
	        System.out.println("Cerrando Archivo en C:\\FDE\\ .......");
	        fproperties.close();
	        System.out.println("Archivo en C:\\FDE\\  CERRADO !!!!");
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}   
        try {
            System.out.println("Ruta de instalacion de Base Facture FB..C:\\FDE\\ ");
            System.out.println("Leyendo archivo de propiedades en ruta princial  C:\\FDE\\");
            fproperties = new FileInputStream("C:\\FDE\\firebird.properties");
            p.load(fproperties);
            maquina = p.getProperty("firebird.drda.host");
            if(maquina.equals("0.0.0.0"))
                maquina = "localhost";
            port = p.getProperty("firebird.drda.portNumber");
            ruta = p.getProperty("firebird.drda.path");
            System.out.println("Cerrando Archivo en C:\\FDE\\ .......");
            fproperties.close();
            System.out.println("Archivo en C:\\FDE\\  CERRADO !!!!");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        try {
            String databaseURL = (new StringBuilder())
            	.append("jdbc:firebirdsql:").append(maquina).append("/")
            	.append(port).append(":").append(ruta).toString();
            System.out.println((new StringBuilder("Ejecutando de conexion..[ "))
            		.append(databaseURL).append(" ]").toString());
            String user = "SYSDBA";
            String password = "masterkey";
            String driverName = "org.firebirdsql.jdbc.FBDriver";
            Class.forName(driverName);
            if(!databaseURL.equals(null)) {
                try {
                    con = DriverManager.getConnection(databaseURL, user, 
                    								  password);
                    con.setAutoCommit(false);
                } catch(SQLException e) {
                    System.out.println((new StringBuilder("Servicio FireBird " +
                    		"No Activo en ruta\n")).append(databaseURL)
                    		.append("\nContacte A Su Proveedor").toString());
                    m.GetMensaje("CONEXION A SERVIDOR DE FACTURACION " +
                    		"ERRONEA, FAVOR DE VERIFICAR SU RED");
                    System.exit(1);
                }
            } else {
                m.GetMensaje("CONEXION A SERVIDOR DE FACTURACION ERRONEA, " +
                		"FAVOR DE VERIFICAR SU RED");
                System.exit(1);
            }
        }
        // Misplaced declaration of an exception variable
        catch(Exception e) {
            m.GetMensaje("CONEXION A SERVIDOR DE FACTURACION ERRONEA, FAVOR " +
            		"DE VERIFICAR SU RED");
            System.exit(1);
        }
        return;
    }

    public ResultSet consulta(String qry, boolean result)
        throws SQLException {
        stmt = con.createStatement(1004, 1008);
        if(result) {
        	rs = stmt.executeQuery(qry);
        } else {
        	stmt.execute(qry);
        }
        return rs;
    }

    public ResultSet consultaBLOB(String qry, boolean result) 
    		throws SQLException {
        stmt = con.createStatement();
        if (result)
            rs = stmt.executeQuery(qry);
        else
            stmt.execute(qry);
        return rs;
    }

    public void prepara_consulta(String qry) {
        try {
            pst = con.prepareStatement(qry);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void aplicar_prepara_consulta() {
        try {
            pst.executeUpdate();
            pst.close();
            con.commit();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void consultaUpdate(String qry, boolean result) throws SQLException {
        stmt = con.createStatement();
        stmt.executeUpdate(qry);
    }

    public void libera(ResultSet rs) throws SQLException {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        rs = null;
        stmt = null;
    }

    public void commit() throws SQLException {
        con.commit();
    }

    public void finalizar() throws SQLException {
        try {
            con.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public String rollback() {
        try {
        	con.rollback();
        } catch(SQLException e) {
            return e.getMessage();
        }
        return "";
    }

}
