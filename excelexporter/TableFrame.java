// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:11:21 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TableFrame.java

package excelexporter;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

// Referenced classes of package excelexporter:
//            ExcelTableExporter

public class TableFrame extends JFrame
{

    public TableFrame()
    {
        initComponents();
    }

    private void initComponents()
    {
        jTabbedPane1 = new JTabbedPane();
        jScrollPane1 = new JScrollPane();
        tablaGeneral = new JTable();
        jScrollPane2 = new JScrollPane();
        tablaInfo = new JTable();
        jToolBar1 = new JToolBar();
        jButton1 = new JButton();
        setDefaultCloseOperation(3);
        tablaGeneral.setModel(new DefaultTableModel(new Object[][] {
            new Object[] {
                "Java", "5", "8", "9"
            }, new Object[] {
                "Java", "2", "8", "6"
            }, new Object[] {
                "Swign", "2", "4", "11"
            }, new Object[] {
                "Microsystem", "2", "3", null
            }
        }, new String[] {
            "Titulo", "Otro Titulo", "Mas Titulos", "Ultimo Titulo"
        }));
        jScrollPane1.setViewportView(tablaGeneral);
        jTabbedPane1.addTab("Hoja1", jScrollPane1);
        tablaInfo.setModel(new DefaultTableModel(new Object[][] {
            new Object[] {
                "John Doe", "40"
            }, new Object[] {
                "Jose Mendoza", "11"
            }, new Object[] {
                "Jimmy Wale", "45"
            }, new Object[] {
                "Juan Perez", "25"
            }
        }, new String[] {
            "Nombre", "Edad"
        }) {

            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }

            Class types[] = {
                java/lang/String, java/lang/String
            };
            final TableFrame this$0;

            
            {
                this$0 = TableFrame.this;
                super(x0, x1);
            }
        }
);
        jScrollPane2.setViewportView(tablaInfo);
        jTabbedPane1.addTab("Hoja2", jScrollPane2);
        getContentPane().add(jTabbedPane1, "Center");
        jToolBar1.setRollover(true);
        jButton1.setIcon(new ImageIcon(getClass().getResource("/excelexporter/icon/export.png")));
        jButton1.setText("Exportar");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(0);
        jButton1.setVerticalTextPosition(3);
        jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }

            final TableFrame this$0;

            
            {
                this$0 = TableFrame.this;
                super();
            }
        }
);
        jToolBar1.add(jButton1);
        getContentPane().add(jToolBar1, "First");
        pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt)
    {
        try
        {
            java.util.List tables = new ArrayList();
            java.util.List sheetsName = new ArrayList();
            tables.add(tablaGeneral);
            tables.add(tablaInfo);
            sheetsName.add("General");
            sheetsName.add("Info");
            ExcelTableExporter excelExporter = new ExcelTableExporter(tables, new File("exportar.xls"), sheetsName);
            if(excelExporter.export())
                JOptionPane.showMessageDialog(null, "Exportado con exito!");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                (new TableFrame()).setVisible(true);
            }

        }
);
    }

    private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTabbedPane jTabbedPane1;
    private JToolBar jToolBar1;
    private JTable tablaGeneral;
    private JTable tablaInfo;

}
