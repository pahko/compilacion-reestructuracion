package create;

import java.io.*;

// Referenced classes of package create:
//            Comprobantebd, ConexionFirebird

public class Test {

    public Test() {
    	
    }

    public static void main(String args[]) throws Exception {
        Comprobantebd cbd = new Comprobantebd();
        ConexionFirebird cnx = new ConexionFirebird();
        try {
            ConexionFirebird.conectarFirebird();
            cbd.estableceConexion(cnx);
            cbd.reporteMensual("MAYO2010.txt", "2010-05-01", "2010-05-31");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    static void Escribearchivo() {
    	try {
	        File archivo = new File("MAYO2010.txt");
	        FileWriter fichero = new FileWriter(archivo, true);
	        PrintWriter pw = new PrintWriter(fichero);
	        for(int i = 0; i < 10; i++)
	            pw.println((new StringBuilder()).append("Linea ").append(i).toString());
	        
	            if(fichero != null)
	                fichero.close();
	            
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}
