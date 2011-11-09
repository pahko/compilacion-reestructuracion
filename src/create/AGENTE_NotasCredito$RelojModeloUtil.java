// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:39:52 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE_NotasCredito.java

package create;

import java.util.*;

// Referenced classes of package create:
//            AGENTE_NotasCredito

public class AGENTE_NotasCredito$RelojModeloUtil extends Observable
{

    TimerTask timerTask;
    final AGENTE_NotasCredito this$0;


    public AGENTE_NotasCredito$RelojModeloUtil()
    {
        this$0 = AGENTE_NotasCredito.this;
        super();
        timerTask = new TimerTask() {

            public void run()
            {
                setChanged();
                notifyObservers(new Date());
            }

            final AGENTE_NotasCredito.RelojModeloUtil this$1;

            
            {
                this$1 = AGENTE_NotasCredito.RelojModeloUtil.this;
                super();
            }
        }
;
        Timer timer = new Timer();
        timer.schedule(timerTask, 0L, 1000L);
    }
}
