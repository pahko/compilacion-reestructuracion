// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:25:48 p.m.
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

class RelojVisual$1$1
    implements Runnable
{

    public void run()
    {
        setText(format.format(val$fecha));
    }

    final Object val$fecha;
    final RelojVisual._cls1 this$1;

    RelojVisual$1$1()
    {
        this$1 = final__pcls1;
        val$fecha = Object.this;
        super();
    }

    // Unreferenced inner class create/RelojVisual$1

/* anonymous class */
    class RelojVisual._cls1
        implements Observer
    {

        public void update(Observable o, Object arg)
        {
            Object fecha = arg;
            SwingUtilities.invokeLater(((RelojVisual._cls1._cls1) (fecha)). new RelojVisual._cls1._cls1());
        }

        final RelojVisual this$0;

            
            {
                this$0 = RelojVisual.this;
                super();
            }
    }

}
