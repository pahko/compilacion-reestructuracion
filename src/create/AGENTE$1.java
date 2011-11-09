// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:03:50 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE.java

package create;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;

// Referenced classes of package create:
//            Fosar_Agente, AGENTE

class AGENTE$1
    implements Observer
{

    public void update(Observable unObservable, Object dato)
    {
        AGENTE.access$000(AGENTE.this).setText(dato.toString());
        System.out.println(dato);
        System.out.println((new StringBuilder()).append("SEG : ").append(seg).toString());
        if(FACTURA && (seg == 0 || seg == 10 || seg == 20 || seg == 30 || seg == 40 || seg == 50))
            try
            {
                FACTURANDO = true;
                Fosar_Agente fosa;
                try
                {
                    fosa = new Fosar_Agente(1);
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                FACTURANDO = false;
                System.gc();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        if(seg >= 60)
        {
            seg = 0;
            System.exit(0);
        } else
        {
            seg++;
        }
    }

    private Fosar_Agente fosa;
    final AGENTE this$0;

    AGENTE$1()
    {
        this$0 = AGENTE.this;
        super();
    }
}
