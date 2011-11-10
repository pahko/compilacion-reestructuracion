// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Mensaje.java

package create;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Mensaje {
    private static JFrame getJFrame(String mensaje) {
        if(jFrame == null) {
            jFrame = new JFrame();
            jFrame.setSize(new Dimension(400, 150));
            jFrame.setResizable(false);
            jFrame.setUndecorated(false);
            jFrame.setTitle("Aviso");
            jFrame.setLocationRelativeTo(null);
            jFrame.setDefaultCloseOperation(3);
            jFrame.setAlwaysOnTop(true);
            jFrame.setVisible(true);
            jFrame.setContentPane(getJPanel(mensaje));
        }
        return jFrame;
    }

    private static JPanel getJPanel(String text) {
        if(jPanel == null) {
            jLabel = new JLabel();
            jLabel.setSize(new Dimension(400, 100));
            jLabel.setText(text);
            jLabel.setHorizontalAlignment(0);
            jLabel.setVerticalAlignment(0);
            jLabel.setVisible(true);
            jPanel = new JPanel();
            jPanel.setLayout(null);
            jPanel.setSize(new Dimension(400, 200));
            jPanel.add(getJButton(), null);
            jPanel.add(jLabel, null);
            jPanel.setVisible(true);
        }
        return jPanel;
    }

    private static JButton getJButton() {
        if(jButton == null) {
            jButton = new JButton();
            jButton.setBounds(new Rectangle(125, 78, 169, 23));
            jButton.setVisible(true);
            jButton.setText("Aceptar");
            jButton.setText("Aceptar");
            jButton.addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent evt) {
                    Mensaje.MouseClicked(evt);
                }
            });
        }
        return jButton;
    }

    private static void MouseClicked(MouseEvent evt) {
        System.exit(0);
    }

    public void Mensajes(String args) {
        getJFrame(args);
    }

    private static JFrame jFrame = null;
    private static JPanel jPanel = null;
    private static JButton jButton = null;
    private static JLabel jLabel = null;
    private static JTextArea jTextArea = null;
}
