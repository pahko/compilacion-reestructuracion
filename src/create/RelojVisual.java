// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:26:27 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RelojVisual.java

package create;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RelojVisual extends JLabel {

	SimpleDateFormat format;
	
	/**Constructor de la clase RelojVisual. Crea un onjeto de la clase:
	 * SimpleDateFormat. **/
    public RelojVisual(Observable modelo) {
        format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        setHorizontalAlignment(0);
        modelo.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                final Object fecha = arg;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        setText(format.format(fecha));
                    }
                });
            }
        });
        setPreferredSize(new Dimension(200, 50));
    }

    /**Este metodo asigna el formato **/
    public void setFormat(SimpleDateFormat unFormato) {
        format = unFormato;
    }
}
