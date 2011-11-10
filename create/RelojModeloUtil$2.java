// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:20:24 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RelojModeloUtil.java

package create;

import java.util.Date;
import java.util.TimerTask;

// Referenced classes of package create:
//            RelojModeloUtil

class RelojModeloUtil$2 extends TimerTask
{

    public void run()
    {
        RelojModeloUtil.access$000(RelojModeloUtil.this);
        notifyObservers(new Date());
    }

    final RelojModeloUtil this$0;

    RelojModeloUtil$2()
    {
        this$0 = RelojModeloUtil.this;
        super();
    }
}
