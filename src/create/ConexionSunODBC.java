package create;

import java.io.*;
import java.sql.*;
import java.util.Properties;

// Referenced classes of package create:
//            Mensajes, ProgressBarSample

public class ConexionSunODBC {
    static String maquina;
    static String port;
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

    public static void conectarSunODBC() throws ClassNotFoundException,
        SQLException, IOException {
        try {
            String databaseURL = "jdbc:paradox:///F:/datosw/";
            System.out.println((new StringBuilder("Ejecutando de conexion..[ ")).append(databaseURL).append(" ]").toString());
            String user = "";
            String password = "";
            String driverName = "com.hxtt.sql.paradox.ParadoxDriver";
            Class.forName(driverName);
            con = DriverManager.getConnection(databaseURL, user, password);
        } catch(Exception e) {
            m.GetMensaje("Servicio Paradox No Activo");
        }
        con.setAutoCommit(true);
    }

    public static void conectarSunODBC(String dns) throws ClassNotFoundException,
        SQLException, IOException {
        try {
            String databaseURL = (new StringBuilder()).append("jdbc:odbc:").append(dns).toString();
            System.out.println((new StringBuilder("Ejecutando de conexion..[ ")).append(databaseURL).append(" ]").toString());
            String user = "Usuario";
            String password = "98121127";
            String driverName = "sun.jdbc.odbc.JdbcOdbcDriver";
            Class.forName(driverName);
            con = DriverManager.getConnection(databaseURL, user, password);
        } catch(Exception e) {
            m.GetMensaje("Servicio SUN - ODBC No Activo DSN no encontrado");
        }
        con.setAutoCommit(true);
    }

    public ResultSet consulta(String qry, boolean result) throws SQLException {
        stmt = con.createStatement();
        if(result) {
            rs = stmt.executeQuery(qry);
        }
        else {
            stmt.execute(qry);
        }
        return rs;
    }

    public ResultSet consultaBLOB(String qry, boolean result) throws SQLException {
        stmt = con.createStatement();
        if(result) {
            rs = stmt.executeQuery(qry);
        }
        else {
            stmt.execute(qry);
        }
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
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
            pst.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        try {
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
        if(rs != null) {
            rs.close();
        }
        if(rs != null) {
            rs.close();
        }
        if(stmt != null) {
            stmt.close();
        }
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

    public static void main(String args[]) throws FileNotFoundException,
        ClassNotFoundException, SQLException, IOException {
        try {
            ConexionSunODBC con = new ConexionSunODBC();
            ConexionSunODBC _tmp = con;
            conectarSunODBC();
            ResultSet rs = con.consulta("select * from FACT01", true);
            String folio = "";
            String tipdoc = "";
            for(; rs.next(); System.out.println((new StringBuilder()).append("Valores: FOLIO ---> ").append(folio).append("   TIPO DOC ---> ").append(tipdoc).toString())) {
                folio = rs.getString("CVE_DOC");
                tipdoc = rs.getString("TIP_DOC");
            }
            rs.close();
            con.finalizar();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
