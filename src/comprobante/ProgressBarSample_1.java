package comprobante;

import java.awt.Container;
import javax.swing.*;
import javax.swing.border.Border;

public class ProgressBarSample_1 { 

    String borde;
    public JFrame f;
    Container content;
    JProgressBar progressBar;
    Border border;
	
    public ProgressBarSample_1() {
        f = null;
        progressBar = null;
        border = null;
    }

    public void CreaFrame(String title) {
        f = new JFrame(title);
        f.setAlwaysOnTop(true);
        f.setLocationRelativeTo(null);
        f.setUndecorated(true);
        f.setDefaultCloseOperation(3);
        JFrame.setDefaultLookAndFeelDecorated(true);
        f.getRootPane().setWindowDecorationStyle(2);
    }

    public void CreaProgreso() {
        content = f.getContentPane();
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
    }

    public void CreaBorde() {
        content.add(progressBar, "North");
        f.setSize(300, 80);
        f.setVisible(true);
    }

    public void SetBorde(String borde) {
        border = BorderFactory.createTitledBorder(borde);
        progressBar.setBorder(border);
    }

    public void CerrarProgreso() {
        f.setVisible(false);
        f.dispose();
    }
}
