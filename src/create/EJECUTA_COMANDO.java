package create;

import java.io.PrintStream;

public class EJECUTA_COMANDO {
	
	public EJECUTA_COMANDO(){
	}
	
    public void EJECUTA_COPIA_FACT() {
        String comando = "xcopy f:\\datosw\\FACT01*.* c:\\FDE\\datosw\\ /H /I /C /R /Y /F";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA_COPIA_FA0TY() {
        String comando = "xcopy f:\\datosw\\FA0TY1*.* c:\\FDE\\datosw\\ /H /I /C /R /Y /F";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA_COPIA_INVE() {
        String comando = "xcopy f:\\datosw\\INVE_E*.* c:\\FDE\\datosw\\ /H /I /C /R /Y /F";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA_COPIA_CLIE() {
        String comando = "xcopy f:\\datosw\\CLIE_E*.* c:\\FDE\\datosw\\ /H /I /C /R /Y /F";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA_COPIA_CUEN() {
        String comando = "xcopy f:\\datosw\\CUEN*.* c:\\FDE\\datosw\\ /H /I /C /R /Y /F";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA_BORRAR() {
        String comando = "RD C:\\FDE\\datosw\\ /S /Q";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA() {
        String comando = "C:\\FDE\\FDE.BAT";
        Process pr;
        try {
            pr = Runtime.getRuntime().exec(comando);
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
    }

    public void EJECUTA_COPIAS() {
        EJECUTA_COPIA_FACT();
        EJECUTA_COPIA_FA0TY();
        EJECUTA_COPIA_INVE();
        EJECUTA_COPIA_CLIE();
        EJECUTA_COPIA_CUEN();
    }

    EJECUTA_COMANDO(String comando, int segs) {
        try {
            Process pr = Runtime.getRuntime().exec(comando);
            System.out.println((new StringBuilder()).append("COMANDO EJECUTADO --> ").append(comando).toString());
        }
        catch(Exception ex) {
            System.out.println((new StringBuilder()).append("Ha ocurrido un error al ejecutar el comando. Error: ").append(ex).toString());
        }
        try {
            Thread.sleep(segs * 1000);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        String exec = "c:\\Archivos de programa\\Adobe\\Reader 9.0\\Reader\\AcroRd32.exe ";
        String arch = "C:\\Documents and Settings\\Usuario\\Escritorio\\Mis Facturas\\FOSAR ILUMINANCION, S.A. DE C.V\\MORELIA\\FACT_8_MO_20101207.pdf";
        new EJECUTA_COMANDO("C:\\FDE\\COPIA.BAT", 20);
    }
}
