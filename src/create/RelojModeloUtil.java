// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   RelojModeloUtil.java

package create;

import java.io.PrintStream;
import java.util.*;

public class RelojModeloUtil extends Observable {

	TimerTask timerTask;

	/**Constructor de la clase. Crea objetis de las clases:
	 * Timer
	 * TimerTask **/
    public RelojModeloUtil() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0L, 1000L);
        timerTask = new TimerTask() {
            public void run() {
                setChanged();
                notifyObservers(new Date());
            }
        };
    }
    
    /**Metodo principal de la clase. Crea un objeto de la clase:
     * RelojModeloUtil **/
    public static void main(String args[]) {
        RelojModeloUtil modelo = new RelojModeloUtil();
        modelo.addObserver(new Observer() {
            public void update(Observable unObservable, Object dato) {
                System.out.println(dato);
            }
        });
    }
}
