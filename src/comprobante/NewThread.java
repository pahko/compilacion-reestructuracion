// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 03:58:01 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   NewThread.java

package comprobante;

import java.io.PrintStream;

class NewThread
    implements Runnable
{

    NewThread(String threadname)
    {
        name = threadname;
        t = new Thread(this, name);
        System.out.println((new StringBuilder("New thread: ")).append(t).toString());
        t.start();
    }

    public void run()
    {
        try
        {
            for(int i = 5; i > 0; i--)
            {
                System.out.println((new StringBuilder(String.valueOf(name))).append(": ").append(i).toString());
                Thread.sleep(1000L);
            }

        }
        catch(InterruptedException e)
        {
            System.out.println((new StringBuilder(String.valueOf(name))).append("Interrupted").toString());
        }
        System.out.println((new StringBuilder(String.valueOf(name))).append(" exiting.").toString());
    }

    String name;
    Thread t;
}
