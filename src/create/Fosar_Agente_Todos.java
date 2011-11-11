package create;

import java.io.IOException;
import java.sql.SQLException;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            Fosar_Agente, Fosar_Agente_Devoluciones, Fosar_Agente_NotasCredito

public class Fosar_Agente_Todos {
    public static void main(String args[]) throws SQLException,
        ClassNotFoundException, IOException, ComprobanteArchRefException {
        new Fosar_Agente(1);
        new Fosar_Agente_Devoluciones();
        new Fosar_Agente_NotasCredito();
        System.exit(0);
    }
}
