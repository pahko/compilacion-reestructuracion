// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:17:09 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   JDialogProblem.java

package create;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

final class JDialogProblem
{

    JDialogProblem()
    {
    }

    public static void main(String args[])
    {
        JFrame frame = new JFrame("Do following!") {

            public Dimension getPreferredSize()
            {
                return new Dimension(600, 600);
            }

        }
;
        JLabel label = new JLabel("Click your mouse here =============>.");
        frame.getContentPane().add("Center", label);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }

        }
);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(200, 100);
    }

    static final String s1 = "Click your mouse here =============>.";
    static final String s2 = "You see, you can't do anything. To get it back, overlap it by any windows appl. and minimize it back";
}
