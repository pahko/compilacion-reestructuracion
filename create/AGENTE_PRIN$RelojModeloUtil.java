// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE_PRIN.java

package create;

import java.util.*;

// Referenced classes of package create:
//            AGENTE_PRIN

public class AGENTE_PRIN$RelojModeloUtil extends Observable {
    TimerTask timerTask;
    final AGENTE_PRIN this$0;

    public AGENTE_PRIN$RelojModeloUtil() {
    	super();
        this$0 = AGENTE_PRIN.this;
        timerTask = new TimerTask() {

            public void run() {
                setChanged();
                notifyObservers(new Date());
            }

            final AGENTE_PRIN.RelojModeloUtil this$1;

            {
                this$1 = AGENTE_PRIN.RelojModeloUtil.this;
//                super();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0L, 60000L);
    }
}
