// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE_PRIN.java

package create;
import java.awt.Container;
import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;

// Referenced classes of package create:
//            EJECUTA_COMANDO, Fosar_Agente

public class AGENTE_PRIN extends JFrame {
	
	/**Establece el Relog*/
    public class RelojModeloUtil extends Observable {
        TimerTask timerTask;
        final AGENTE_PRIN this$0;

        public RelojModeloUtil() {
            super();
            this$0 = AGENTE_PRIN.this;
            timerTask = new TimerTask() {

                public void run() {
                    setChanged();
                    notifyObservers(new Date());
                }
            };
            
            Timer timer = new Timer();
            timer.schedule(timerTask, 0L, 60000L);
        }
    }

    /**Constructor de la Clase AGENTE_PRIN 
     * iniciliza los valores*/
    public AGENTE_PRIN() {
        SEGUNDOS = 0;
        seg = 0;
        FACTURANDO = false;
        FACTURA = true;
        initComponents();
        String system = "C:\\FDE\\FDE.BAT";
        try {
            Process hijo = Runtime.getRuntime().exec(system);
            hijo.waitFor();
            if(hijo.exitValue() == 0)
                System.out.println("Finalizado");
            else
                System.out.println((new StringBuilder()).append("imposible " +
                		"finalizar . Exit code: ").append(hijo.exitValue()).toString());
        }
        catch(IOException e) {
            System.out.println("Incapaz de matar soffice.");
        }
        catch(InterruptedException e) {
            System.out.println("Incapaz de matar soffice.");
        }
    }
    /**Inicializa el Relog*/
    void Init_Reloj() {
        RelojModeloUtil modelo = new RelojModeloUtil();
        modelo.addObserver(new Observer() {

            public void update(Observable unObservable, Object dato) {
                Reloj.setText(dato.toString());
                System.out.println(dato);
                System.out.println((new StringBuilder()).append("SEG : ")
                		.append(seg).toString());
                (new EJECUTA_COMANDO()).EJECUTA();
            }
        });
    }
    
    /**Inicializa los Componentes*/
    private void initComponents() {
        Reloj = new JLabel();
        setDefaultCloseOperation(3);
        setTitle("FACT_ELECTRONICA");
        setAlwaysOnTop(true);
        setLocationByPlatform(true);
        setName("FACT_ELECT");
        setResizable(false);
        Reloj.setText("INICIALIZANDO....");
        Reloj.setBorder(new SoftBevelBorder(0));
        Reloj.setEnabled(false);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing
        		.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
        				.addContainerGap().addComponent(Reloj, -2, 189, -2).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing
        		.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup()
        				.addContainerGap().addComponent(Reloj, -2, 32, -2).addContainerGap(-1, 32767)));
        pack();
    }
    /**Main de la Clase AGENTE_PRIN*/
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                (new AGENTE_PRIN()).setVisible(true);
            }
        });
    }

    int SEGUNDOS;
    int seg;
    boolean FACTURANDO;
    boolean FACTURA;
    public JLabel Reloj;
}
