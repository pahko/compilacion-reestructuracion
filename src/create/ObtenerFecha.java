// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:19:54 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ObtenerFecha.java

package create;

import java.io.PrintStream;
import java.util.*;

public class ObtenerFecha
{

    public ObtenerFecha()
    {
    }

    public static void main(String args[])
    {
        System.out.println(new Date());
        Calendar c = new GregorianCalendar();
        String dia = Integer.toString(c.get(5));
        String mes = Integer.toString(c.get(2));
        String annio = Integer.toString(c.get(1));
        System.out.println((new StringBuilder()).append(dia).append("/").append(mes).append("/").append(annio).toString());
    }
}
