// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:10:43 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MySelector.java

package excelexporter;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;

// Referenced classes of package excelexporter:
//            MyFileListener

class MyFrame extends JFrame
{

    public MyFrame()
    {
        super("Ejemplo de JFileChooser");
        botonOpen = new JButton("Abrir");
        botonSave = new JButton("Guardar");
        myFileListener = new MyFileListener(this);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(botonOpen, "West");
        container.add(botonSave, "East");
        botonOpen.addActionListener(myFileListener);
        botonSave.addActionListener(myFileListener);
    }

    private JButton botonOpen;
    private JButton botonSave;
    private MyFileListener myFileListener;
}
