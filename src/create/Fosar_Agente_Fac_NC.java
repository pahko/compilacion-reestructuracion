package create;

import java.io.IOException;
import java.sql.SQLException;
import mx.com.codinet.err.ComprobanteArchRefException;

// Referenced classes of package create:
//            ServidorChat, Fosar_Agente, Fosar_Agente_NotasCredito

public class Fosar_Agente_Fac_NC {
    public static void main(String args[]) throws SQLException,
        ClassNotFoundException, IOException, ComprobanteArchRefException {
        Runnable servidor = new ServidorChat();
        Thread hilo = new Thread(servidor);
        hilo.start();
        new Fosar_Agente();
        new Fosar_Agente_NotasCredito();
    }
}
