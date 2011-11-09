// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:04:31 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AGENTE.java

package create;

import java.awt.Container;
import java.awt.EventQueue;
import java.io.PrintStream;
import java.util.*;
import javax.swing.*;
import javax.swing.border.SoftBevelBorder;

// Referenced classes of package create:
//            Fosar_Agente

public class AGENTE extends JFrame
{
    public class RelojModeloUtil extends Observable
    {

        TimerTask timerTask;
        final AGENTE this$0;


        public RelojModeloUtil()
        {
            this$0 = AGENTE.this;
            super();
            timerTask = new TimerTask() {

                public void run()
                {
                    setChanged();
                    notifyObservers(new Date());
                }

                final RelojModeloUtil this$1;

                
                {
                    this$1 = RelojModeloUtil.this;
                    super();
                }
            }
;
            Timer timer = new Timer();
            timer.schedule(timerTask, 0L, 1000L);
        }
    }


    public AGENTE()
    {
        SEGUNDOS = 0;
        seg = 0;
        FACTURANDO = false;
        FACTURA = true;
        initComponents();
        Init_Reloj();
    }

    void Init_Reloj()
    {
        RelojModeloUtil modelo = new RelojModeloUtil();
        modelo.addObserver(new Observer() {

            public void update(Observable unObservable, Object dato)
            {
                Reloj.setText(dato.toString());
                System.out.println(dato);
                System.out.println((new StringBuilder()).append("SEG : ").append(seg).toString());
                if(FACTURA && (seg == 0 || seg == 10 || seg == 20 || seg == 30 || seg == 40 || seg == 50))
                    try
                    {
                        FACTURANDO = true;
                        Fosar_Agente fosa;
                        try
                        {
                            fosa = new Fosar_Agente(1);
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                        FACTURANDO = false;
                        System.gc();
                    }
                    catch(Exception ex)
                    {
                        ex.printStackTrace();
                    }
                if(seg >= 60)
                {
                    seg = 0;
                    System.exit(0);
                } else
                {
                    seg++;
                }
            }

            private Fosar_Agente fosa;
            final AGENTE this$0;

            
            {
                this$0 = AGENTE.this;
                super();
            }
        }
);
    }

    private void initComponents()
    {
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
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(Reloj, -2, 189, -2).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(Reloj, -2, 32, -2).addContainerGap(-1, 32767)));
        pack();
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                (new AGENTE()).setVisible(true);
            }

        }
);
    }

    int SEGUNDOS;
    int seg;
    boolean FACTURANDO;
    boolean FACTURA;
    private JLabel Reloj;

}
