// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:26:17 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RelojVisual.java

package create;

import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingUtilities;

// Referenced classes of package create:
//            RelojVisual

class RelojVisual$1
    implements Observer
{

    public void update(Observable o, Object arg)
    {
        final Object fecha = arg;
        SwingUtilities.invokeLater(new Runnable() {

            public void run()
            {
                setText(format.format(fecha));
            }

            final Object val$fecha;
            final RelojVisual._cls1 this$1;

            
            {
                this$1 = RelojVisual._cls1.this;
                fecha = obj;
                super();
            }
        }
);
    }

    final RelojVisual this$0;

    RelojVisual$1()
    {
        this$0 = RelojVisual.this;
        super();
    }
}
