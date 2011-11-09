// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:00:42 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ProgressBarSample_1.java

package comprobante;

import java.awt.Container;
import javax.swing.*;
import javax.swing.border.Border;

public class ProgressBarSample_1
{

    public ProgressBarSample_1()
    {
        f = null;
        progressBar = null;
        border = null;
    }

    public void CreaFrame(String title)
    {
        f = new JFrame(title);
        f.setAlwaysOnTop(true);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        f.setDefaultCloseOperation(3);
        JFrame.setDefaultLookAndFeelDecorated(true);
        f.getRootPane().setWindowDecorationStyle(2);
    }

    public void CreaProgreso()
    {
        content = f.getContentPane();
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
    }

    public void CreaBorde()
    {
        content.add(progressBar, "North");
        f.setSize(300, 80);
        f.setVisible(true);
    }

    public void SetBorde(String borde)
    {
        border = BorderFactory.createTitledBorder(borde);
        progressBar.setBorder(border);
    }

    public void CreaObjeto(String s, String s1)
    {
    }

    public void CerrarProgreso()
    {
        f.setVisible(false);
        f.dispose();
    }

    String borde;
    public JFrame f;
    Container content;
    JProgressBar progressBar;
    Border border;
}
