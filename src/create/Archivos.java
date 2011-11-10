// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:52:34 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Archivos.java

package create;

import java.io.*;

public class Archivos
{

    Archivos(String Path)
    {
        PATH = "";
        PATH = Path;
    }

    Archivos(int opcion)
    {
        PATH = "";
        int i = 0;
        String archivo;
        if(opcion == 1)
            archivo = "Ultima_Factura.txt";
        else
        if(opcion == 2)
            archivo = "Ultima_Devolucion.txt";
        else
            archivo = "Ultima_NotaCredito.txt";
        PATH = "C:\\FDE\\Rutas.txt";
        PATH = Leer();
        i = PATH.indexOf("|");
        PATH = PATH.substring(0, i);
        PATH = (new StringBuilder()).append(PATH).append(archivo).toString();
        System.out.println(PATH);
    }

    public void Escribir(String dato)
    {
        try
        {
            File f = new File(PATH);
            FileWriter linea_tx = new FileWriter(f);
            linea_tx.write(dato);
            linea_tx.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public String Leer()
    {
        String linea_arch = "";
        try
        {
            File f = new File(PATH);
            FileInputStream f1 = new FileInputStream(f);
            InputStreamReader f2 = new InputStreamReader(f1);
            BufferedReader linea = new BufferedReader(f2);
            linea_arch = linea.readLine();
            System.out.println(linea_arch);
            linea.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return linea_arch;
    }

    public static void main(String args[])
    {
        Archivos arch = new Archivos(1);
        arch.Leer();
    }

    String PATH;
}
