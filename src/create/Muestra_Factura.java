// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:19:10 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Muestra_Factura.java

package create;


// Referenced classes of package create:
//            Archivos, EJECUTA_COMANDO

class Muestra_Factura {
    String ADOBE;

    void Obten_Ruta() {
        Archivos Arch = new Archivos("C:\\FDE\\Acrobat.txt");
        ADOBE = Arch.Leer();
        int i = ADOBE.indexOf("|");
        ADOBE = (new StringBuilder()).append(ADOBE.substring(0, i)).append(" ").toString();
    }

    public Muestra_Factura(String direccion, String nombre) {
        ADOBE = "";
        Obten_Ruta();
        new EJECUTA_COMANDO((new StringBuilder()).append(ADOBE).append(direccion).append(nombre).toString(), 1);
    }
}
