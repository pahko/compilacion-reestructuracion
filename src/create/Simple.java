// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:27:04 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Simple.java

package create;

import java.io.PrintStream;

public class Simple
{

    public Simple()
    {
    }

    public static String sayHello()
    {
        return "Hello world";
    }

    public String sayNonStaticHello()
    {
        return "Hello world";
    }

    public static void main(String args[])
    {
        System.out.println(sayHello());
    }
}
