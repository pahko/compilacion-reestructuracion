// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:20:18 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RelojModeloUtil.java

package create;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;

// Referenced classes of package create:
//            RelojModeloUtil

static class RelojModeloUtil$1
    implements Observer
{

    public void update(Observable unObservable, Object dato)
    {
        System.out.println(dato);
    }

    RelojModeloUtil$1()
    {
    }
}
