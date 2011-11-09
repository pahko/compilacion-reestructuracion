// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:15:56 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FOSAR_REPORTE.java

package create;

import de.wannawork.jcalendar.JCalendarComboBox;
import excelexporter.ExcelTableExporter;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;

// Referenced classes of package create:
//            Archivos, MyTableModel_FOSAR, FOSAR_GeneraReporte, Comprobantebd, 
//            ConexionFirebird

public class FOSAR_REPORTE extends JFrame
{

    String Formato_Texto(String dato)
    {
        int i = 0;
        i = dato.indexOf("|");
        dato = dato.substring(0, i);
        return dato;
    }

    void Inicializa_Series()
    {
        int i = 0;
        String series = Series.Leer();
        series = Formato_Texto(series);
        i = series.indexOf(",");
        JCSeries.addItem(series.substring(0, i));
        series = series.substring(i + 1);
        i = series.indexOf(",");
        JCSeries.addItem(series.substring(0, i));
        series = series.substring(i + 1);
        JCSeries.addItem(series);
    }

    public FOSAR_REPORTE()
    {
        Series = new Archivos("C:\\FDE\\Series.txt");
        initComponents();
    }

    private void initComponents()
    {
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        FInicial = new JCalendarComboBox();
        FFinal = new JCalendarComboBox();
        JCSeries = new JComboBox();
        Inicializa_Series();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jToolBar1 = new JToolBar();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        lbMensajes = new JLabel();
        setDefaultCloseOperation(3);
        setTitle("REPORTES DE FACTURACION ELECTRONICA");
        setFont(new Font("Arial Black", 1, 14));
        setResizable(false);
        jLabel1.setFont(new Font("Arial", 2, 18));
        jLabel1.setText("GENERACION DE REPORTES");
        jLabel2.setText("FECHA INICIAL: ");
        jLabel3.setText("FECHA FINAL:");
        jLabel4.setText("SERIE:");
        jTable1.setModel(new MyTableModel_FOSAR());
        jScrollPane1.setViewportView(jTable1);
        jToolBar1.setRollover(true);
        jButton1.setIcon(new ImageIcon(getClass().getResource("/excelexporter/icon/Buscar.gif")));
        jButton1.setToolTipText("Buscar");
        jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }

            final FOSAR_REPORTE this$0;

            
            {
                this$0 = FOSAR_REPORTE.this;
                super();
            }
        }
);
        jToolBar1.add(jButton1);
        jButton2.setIcon(new ImageIcon(getClass().getResource("/excelexporter/icon/excel.png")));
        jButton2.setToolTipText("Exportar Excel");
        jButton2.setAutoscrolls(true);
        jButton2.setHorizontalAlignment(11);
        jButton2.setVerticalAlignment(3);
        jButton2.setVerticalTextPosition(3);
        jButton2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }

            final FOSAR_REPORTE this$0;

            
            {
                this$0 = FOSAR_REPORTE.this;
                super();
            }
        }
);
        jToolBar1.add(jButton2);
        jButton3.setIcon(new ImageIcon(getClass().getResource("/excelexporter/icon/reporte_SAT_ico.png")));
        jButton3.setToolTipText("Reporte Mensual SAT");
        jButton3.setAutoscrolls(true);
        jButton3.setHorizontalAlignment(11);
        jButton3.setVerticalAlignment(3);
        jButton3.setVerticalTextPosition(3);
        jButton3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }

            final FOSAR_REPORTE this$0;

            
            {
                this$0 = FOSAR_REPORTE.this;
                super();
            }
        }
);
        jToolBar1.add(jButton3);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(75, 75, 75).addComponent(jLabel1, -1, 525, 32767).addGap(78, 78, 78)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -1, 658, 32767).addContainerGap()).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(jLabel2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(FInicial, -2, 121, -2)).addComponent(jToolBar1, -1, -1, 32767)).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(28, 28, 28).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(FFinal, -1, 110, 32767).addGap(27, 27, 27).addComponent(jLabel4, -2, 49, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(JCSeries, -2, 114, -2)).addGroup(layout.createSequentialGroup().addGap(126, 126, 126).addComponent(lbMensajes, -1, 285, 32767))).addGap(51, 51, 51)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel2).addGroup(layout.createSequentialGroup().addGap(4, 4, 4).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(FInicial, -2, -1, -2).addComponent(FFinal, -2, -1, -2).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(JCSeries, -2, -1, -2))).addComponent(jLabel3)))).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(36, 36, 36).addComponent(lbMensajes, -2, 32, -2)).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addComponent(jToolBar1, -2, 55, -2))).addGap(18, 18, 18).addComponent(jScrollPane1, -1, 256, 32767).addContainerGap()));
        pack();
    }

    private void jButton1ActionPerformed(ActionEvent evt)
    {
        FOSAR_REPORTE _tmp = this;
        lbMensajes.setText("REALIZANDO BUSQUEDA .....!!");
        String finicial = Formato_Fecha(FInicial.getCalendar(), 0);
        String ffinal = Formato_Fecha(FFinal.getCalendar(), 0);
        try
        {
            FOSAR_GeneraReporte datos = new FOSAR_GeneraReporte(finicial, ffinal, JCSeries.getSelectedItem().toString());
            MyTableModel_FOSAR modelo = (MyTableModel_FOSAR)jTable1.getModel();
            modelo.Actualizar_Datos(datos.REPORTE);
            jScrollPane1.setViewportView(jTable1);
        }
        catch(ClassNotFoundException ex)
        {
            Logger.getLogger(create/FOSAR_REPORTE.getName()).log(Level.SEVERE, null, ex);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(create/FOSAR_REPORTE.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex)
        {
            Logger.getLogger(create/FOSAR_REPORTE.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println((new StringBuilder()).append("Parametros: FInicial ").append(finicial).append(" FFinal ").append(ffinal).append(" SERIE ").append(JCSeries.getSelectedItem().toString()).toString());
        FOSAR_REPORTE _tmp1 = this;
        lbMensajes.setText("");
    }

    private void jButton2ActionPerformed(ActionEvent evt)
    {
        FOSAR_REPORTE _tmp = this;
        lbMensajes.setText("EXPORTANDO INFORMACION...!!");
        String ruta = Selecciona_Archivo();
        if(ruta.length() > 0)
            Exporta_Tabla_XLS(ruta);
        FOSAR_REPORTE _tmp1 = this;
        lbMensajes.setText("");
    }

    private void jButton3ActionPerformed(ActionEvent evt)
    {
        JOptionPane.showMessageDialog(this, "RECUERDE PRIMERO HABER SELECCIONADO EL PRIMER Y EL ULTIMO\nDIA DEL MES EN FECHA INICIAL Y FECHA FINAL, RESPECTIVAMENTE,\nPARA QUE EL REPORTE TENGA EFECTOS FISCALES.", "REPORTE MENSUAL SAT", 1);
        FOSAR_REPORTE _tmp = this;
        lbMensajes.setText("GENERANDO REPORTE MENSUAL SAT.....!!");
        String ruta = Selecciona_Archivo_ReporteMensual();
        String finicial = (new StringBuilder()).append(Formato_Fecha(FInicial.getCalendar(), 0)).append(" 00:00:00").toString();
        String ffinal = (new StringBuilder()).append(Formato_Fecha(FFinal.getCalendar(), 0)).append(" 23:59:59").toString();
        if(ruta.length() > 0)
            Genera_reporteSAT(finicial, ffinal, ruta);
        FOSAR_REPORTE _tmp1 = this;
        lbMensajes.setText("");
    }

    void Genera_reporteSAT(String finicial, String ffinal, String ruta)
    {
        try
        {
            Comprobantebd cbd = new Comprobantebd();
            ConexionFirebird cnx = new ConexionFirebird();
            ConexionFirebird _tmp = cnx;
            ConexionFirebird.conectarFirebird();
            cbd.estableceConexion(cnx);
            cbd.reporteMensualV2(ruta, finicial, ffinal);
            JOptionPane.showMessageDialog(this, (new StringBuilder()).append("REPORTE GENERADO EN :\n").append(ruta).toString(), "REPORTE MENSUAL SAT", 1);
        }
        catch(ClassNotFoundException ex)
        {
            Logger.getLogger(create/FOSAR_REPORTE.getName()).log(Level.SEVERE, null, ex);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(create/FOSAR_REPORTE.getName()).log(Level.SEVERE, null, ex);
        }
        catch(IOException ex)
        {
            Logger.getLogger(create/FOSAR_REPORTE.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String Selecciona_Archivo_ReporteMensual()
    {
        String dialogTitle = "Guardar Archivo";
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(dialogTitle);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(1);
        int sel = chooser.showOpenDialog(null);
        if(sel == 0)
        {
            File selectedFile = chooser.getSelectedFile();
            File _tmp = selectedFile;
            path = (new StringBuilder()).append(selectedFile.getPath().toString()).append(File.separator.toString()).toString();
        }
        return path;
    }

    String Selecciona_Archivo()
    {
        String dialogTitle = "Guardar Archivo";
        String path = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(dialogTitle);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(0);
        int sel = chooser.showOpenDialog(null);
        if(sel == 0)
        {
            File selectedFile = chooser.getSelectedFile();
            path = selectedFile.getAbsolutePath().toString();
        }
        return path;
    }

    void Exporta_Tabla_XLS(String ruta)
    {
        try
        {
            java.util.List tables = new ArrayList();
            java.util.List sheetsName = new ArrayList();
            tables.add(jTable1);
            sheetsName.add("DOCUMENTOS_FOSAR");
            ExcelTableExporter excelExporter = new ExcelTableExporter(tables, new File((new StringBuilder()).append(ruta).append(".xls").toString()), sheetsName);
            if(excelExporter.export())
                JOptionPane.showMessageDialog(null, "Exportado con exito!");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    String Formato_Fecha(Calendar cal, int sumadias)
    {
        Calendar _tmp = cal;
        int mes = cal.get(2) + 1;
        Calendar _tmp1 = cal;
        int dia = cal.get(5) + sumadias;
        String fecha = (new StringBuilder()).append(mes).append("/").toString();
        fecha = (new StringBuilder()).append(fecha).append(dia).append("/").toString();
        Calendar _tmp2 = cal;
        fecha = (new StringBuilder()).append(fecha).append(cal.get(1)).toString();
        return fecha;
    }

    String Formato_FechaReporteSAT(Calendar cal, int sumadias)
    {
        Calendar _tmp = cal;
        String mes = (new StringBuilder()).append("").append(cal.get(2) + 1).toString();
        Calendar _tmp1 = cal;
        String dia = (new StringBuilder()).append("").append(cal.get(5) + sumadias).toString();
        Calendar _tmp2 = cal;
        String anio = (new StringBuilder()).append("").append(cal.get(1)).toString();
        String fecha = anio;
        mes = mes.length() >= 2 ? mes : (new StringBuilder()).append("0").append(mes).toString();
        fecha = (new StringBuilder()).append(fecha).append(mes).toString();
        dia = dia.length() >= 2 ? dia : (new StringBuilder()).append("0").append(dia).toString();
        fecha = (new StringBuilder()).append(fecha).append(dia).toString();
        return fecha;
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                (new FOSAR_REPORTE()).setVisible(true);
            }

        }
);
    }

    Archivos Series;
    private JCalendarComboBox FFinal;
    private JCalendarComboBox FInicial;
    private JComboBox JCSeries;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JToolBar jToolBar1;
    private static JLabel lbMensajes;



}
