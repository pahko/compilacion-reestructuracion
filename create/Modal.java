// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Modal.java

package create;

import java.awt.Component;
import java.awt.Container;
import javax.swing.*;
import javax.swing.event.*;

public class Modal {
    static class ModalAdapter extends InternalFrameAdapter {
        Component glass;

        public void internalFrameClosed(InternalFrameEvent e) {
            glass.setVisible(false);
        }

        public ModalAdapter(Component glass) {
            this.glass = glass;
            MouseInputAdapter adapter = new MouseInputAdapter() {

// JavaClassFileOutputException: Invalid index accessing method local variables table of <init>
            };
            glass.addMouseListener(adapter);
            glass.addMouseMotionListener(adapter);
        }
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Modal Internal Frame");
        frame.setDefaultCloseOperation(3);
        JDesktopPane desktop = new JDesktopPane();
        JInternalFrame internal = new JInternalFrame("Opener");
        desktop.add(internal);
        JButton button = new JButton("Open");
        Container iContent = internal.getContentPane();
        iContent.add(button, "Center");
        internal.setBounds(25, 25, 200, 100);
        internal.setVisible(true);
        Container content = frame.getContentPane();
        content.add(desktop, "Center");
        frame.setSize(500, 300);
        frame.setVisible(true);
    }
}
