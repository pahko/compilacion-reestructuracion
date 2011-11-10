// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:17:59 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Mensajes.java

package create;

import javax.swing.*;

public class Mensajes
{

    public Mensajes()
    {
        fr = new JFrame("Aviso");
        fr.setAlwaysOnTop(true);
        fr.setLocationRelativeTo(null);
        fr.setUndecorated(true);
        fr.setDefaultCloseOperation(3);
        JFrame.setDefaultLookAndFeelDecorated(true);
        fr.getRootPane().setWindowDecorationStyle(2);
    }

    public void GetMensaje(String m)
    {
        fr.setVisible(true);
        JOptionPane.showMessageDialog(fr, m, "Aviso", 1);
        fr.setVisible(false);
        fr.dispose();
    }

    void GetMensajes(String m)
    {
        fr.setVisible(true);
        JOptionPane.showMessageDialog(fr, m, "Aviso", 1);
        fr.setVisible(false);
        fr.dispose();
    }

    static JFrame fr = null;

}
