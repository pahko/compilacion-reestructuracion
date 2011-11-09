// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:39:45 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE_NotasCredito.java

package create;

import java.util.Date;
import java.util.TimerTask;

// Referenced classes of package create:
//            AGENTE_NotasCredito

class AGENTE_NotasCredito$RelojModeloUtil$1 extends TimerTask
{

    public void run()
    {
        AGENTE_NotasCredito.RelojModeloUtil.access$100(AGENTE_NotasCredito.RelojModeloUtil.this);
        notifyObservers(new Date());
    }

    final AGENTE_NotasCredito.RelojModeloUtil this$1;

    AGENTE_NotasCredito$RelojModeloUtil$1()
    {
        this$1 = AGENTE_NotasCredito.RelojModeloUtil.this;
        super();
    }
}
