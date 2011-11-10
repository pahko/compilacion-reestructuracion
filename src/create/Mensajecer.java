// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Mensajecer.java

package create;

import java.io.*;
import java.sql.SQLException;

public class Mensajecer {
    public static void main(String args[]) throws IOException, SQLException {
        Mensaje m = new Mensaje();
        java.io.InputStream in_certificado = new BufferedInputStream(new FileInputStream(new File(args[0])));
        java.io.InputStream in_llave = new BufferedInputStream(new FileInputStream(new File(args[1])));
        Readcer rc = new Readcer();
        rc.generarSello(in_certificado, in_llave, args[2], args[3], "CADENAORIGINAL", args[4]);
        File fichero = new File("error.txt");
        if(fichero.exists()) {
            BufferedReader in = new BufferedReader(new FileReader("error.txt"));
            m.Mensajes(in.readLine());
            System.out.println(in.readLine());
            fichero.delete();
        }
    }
}
