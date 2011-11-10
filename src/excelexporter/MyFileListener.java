// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:10:34 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MySelector.java

package excelexporter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

class MyFileListener
    implements ActionListener
{

    public MyFileListener(JFrame frame)
    {
        chooser = new JFileChooser();
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent ae)
    {
        String textButton = ae.getActionCommand();
        String dialogTitle = "Abrir un fichero";
        if(textButton.equals("Guardar"))
        {
            dialogTitle = "Guardar un fichero";
            chooser.setDialogTitle(dialogTitle);
            chooser.setMultiSelectionEnabled(false);
            chooser.setFileSelectionMode(0);
            int sel = chooser.showOpenDialog(frame);
            if(sel == 0)
            {
                File selectedFile = chooser.getSelectedFile();
                JOptionPane.showMessageDialog(frame, selectedFile.getAbsolutePath());
            } else
            {
                return;
            }
        }
    }

    private JFileChooser chooser;
    private JFrame frame;
}
