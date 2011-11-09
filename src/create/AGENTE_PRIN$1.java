// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:40:24 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE_PRIN.java

package create;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

// Referenced classes of package create:
//            EJECUTA_COMANDO, AGENTE_PRIN, Fosar_Agente

class AGENTE_PRIN$1
    implements Observer
{

    public void update(Observable unObservable, Object dato)
    {
        AGENTE_PRIN.access$000(AGENTE_PRIN.this).setText(dato.toString());
        System.out.println(dato);
        System.out.println((new StringBuilder()).append("SEG : ").append(seg).toString());
        (new EJECUTA_COMANDO()).EJECUTA();
    }

    private Fosar_Agente fosa;
    final AGENTE_PRIN this$0;

    AGENTE_PRIN$1()
    {
        this$0 = AGENTE_PRIN.this;
        super();
    }
}
