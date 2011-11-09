// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 03:57:43 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MultiThreadDemo.java

package comprobante;

import java.io.PrintStream;

// Referenced classes of package comprobante:
//            NewThread

class MultiThreadDemo
{

    MultiThreadDemo()
    {
    }

    public static void main(String args[])
    {
        new NewThread("One");
        new NewThread("Two");
        new NewThread("Three");
        try
        {
            Thread.sleep(10000L);
        }
        catch(InterruptedException e)
        {
            System.out.println("Main thread Interrupted");
        }
        System.out.println("Main thread exiting.");
    }
}
