// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:18:34 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Modal.java

package create;

import java.awt.Component;
import javax.swing.event.*;

// Referenced classes of package create:
//            Modal

static class Modal$ModalAdapter extends InternalFrameAdapter
{

    public void internalFrameClosed(InternalFrameEvent e)
    {
        glass.setVisible(false);
    }

    Component glass;

    public Modal$ModalAdapter(Component glass)
    {
        this.glass = glass;
        MouseInputAdapter adapter = new MouseInputAdapter() {

            final Modal.ModalAdapter this$1;
            final Modal.ModalAdapter this$0;


// JavaClassFileOutputException: Invalid index accessing method local variables table of <init>
        }
;
        glass.addMouseListener(adapter);
        glass.addMouseMotionListener(adapter);
    }
}
