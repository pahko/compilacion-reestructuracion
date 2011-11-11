// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:06:57 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Fosar_Configuracion.java

package create;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

// Referenced classes of package create:
//            Archivos, ConexionParadox, ConexionFirebird, ProgressBarSample

public class Fosar_Configuracion extends JFrame
{

    public Fosar_Configuracion()
    {
        Paradox = new Archivos("C:\\FDE\\Paradox.txt");
        Rutas = new Archivos("C:\\FDE\\Rutas.txt");
        Acrobat = new Archivos("C:\\FDE\\Acrobat.txt");
        Series = new Archivos("C:\\FDE\\Series.txt");
        Scripts = new Archivos("C:\\FDE\\LimpiezaBD.txt");
        Facturas = new Archivos(1);
        Devoluciones = new Archivos(2);
        NotasCredito = new Archivos(3);
        cnx = new ConexionParadox();
        fbx = new ConexionFirebird();
        ps = new ProgressBarSample();
        initComponents();
    }

    private void initComponents()
    {
        jCheckBox1 = new JCheckBox();
        jTabbedPane1 = new JTabbedPane();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        TxtParadox = new JTextField();
        jLabel2 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jPanel2 = new JPanel();
        jLabel3 = new JLabel();
        TxtRegistros = new JTextField();
        jLabel4 = new JLabel();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jPanel3 = new JPanel();
        jLabel5 = new JLabel();
        TxtFacArch = new JTextField();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        TxtFacPrx = new JTextField();
        jLabel9 = new JLabel();
        TxtDevArch = new JTextField();
        TxtDevPrx = new JTextField();
        jLabel10 = new JLabel();
        TxtNceArch = new JTextField();
        TxtNcePrx = new JTextField();
        jButton5 = new JButton();
        jButton6 = new JButton();
        jLabel11 = new JLabel();
        jPanel4 = new JPanel();
        jLabel12 = new JLabel();
        TxtAcrobat = new JTextField();
        jLabel13 = new JLabel();
        jButton7 = new JButton();
        jButton8 = new JButton();
        jPanel5 = new JPanel();
        jLabel14 = new JLabel();
        TxtSerFac = new JTextField();
        jLabel15 = new JLabel();
        TxtSerDev = new JTextField();
        jLabel16 = new JLabel();
        TxtSerNce = new JTextField();
        jButton9 = new JButton();
        jButton10 = new JButton();
        jPanel6 = new JPanel();
        jScrollPane1 = new JScrollPane();
        TxtScripts = new JTextPane();
        jButton12 = new JButton();
        jCheckBox1.setText("jCheckBox1");
        setDefaultCloseOperation(3);
        setTitle("CONFIGURACION");
        setAlwaysOnTop(true);
        setResizable(false);
        jLabel1.setText("RUTA:");
        TxtParadox.setName("TxtParadox");
        jLabel2.setFont(new Font("Tahoma", 1, 12));
        jLabel2.setText("NOTA: Sustituya todos los \\ con / y finalize con /");
        jButton1.setText("Actualizar");
        jButton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.setText("Guardar");
        jButton2.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton2ActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel1, -2, 44, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGap(43, 43, 43).addComponent(TxtParadox, -2, 291, -2)).addGroup(jPanel1Layout.createSequentialGroup().addGap(22, 22, 22).addComponent(jButton1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, 32767).addComponent(jButton2, -2, 88, -2)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel2, -2, 336, -2))).addContainerGap(93, -2)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(30, 30, 30).addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(TxtParadox, -2, -1, -2).addGap(18, 18, 18).addComponent(jLabel2, -2, 29, -2).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton1).addComponent(jButton2)).addContainerGap(99, 32767)));
        jButton1ActionPerformed(null);
        jTabbedPane1.addTab("PARADOX", jPanel1);
        jLabel3.setText("RUTA:");
        TxtRegistros.setName("TxtParadox");
        jLabel4.setFont(new Font("Tahoma", 1, 12));
        jLabel4.setText("NOTA: Sustituya todos los \\ con \\\\ y finalize con \\\\");
        jButton3.setText("Actualizar");
        jButton3.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton3ActionPerformed(evt);
            }
        });
        jButton4.setText("Guardar");
        jButton4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton4ActionPerformed(evt);
            }
        });
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel3, -2, 44, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGap(43, 43, 43).addComponent(TxtRegistros, -2, 291, -2)).addGroup(jPanel2Layout.createSequentialGroup().addGap(22, 22, 22).addComponent(jButton3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, 32767).addComponent(jButton4, -2, 88, -2)).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jLabel4, -2, 336, -2))).addContainerGap(93, -2)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGap(30, 30, 30).addComponent(jLabel3).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(TxtRegistros, -2, -1, -2).addGap(18, 18, 18).addComponent(jLabel4, -2, 29, -2).addGap(18, 18, 18).addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton3).addComponent(jButton4)).addContainerGap(99, 32767)));
        jButton3ActionPerformed(null);
        jTabbedPane1.addTab("RUTAS", jPanel2);
        jLabel5.setText("FACTURAS:");
        jLabel6.setFont(new Font("Tahoma", 1, 11));
        jLabel6.setText("REGISTRO DE:");
        jLabel7.setFont(new Font("Tahoma", 1, 11));
        jLabel7.setText("VALOR EN ARCHIVO:");
        jLabel8.setFont(new Font("Tahoma", 1, 11));
        jLabel8.setText("VALOR EN PARADOX:");
        TxtFacPrx.setEditable(false);
        jLabel9.setText("DEVOLUCIONES:");
        TxtDevPrx.setEditable(false);
        jLabel10.setText("NOTAS DE CREDITO:");
        TxtNcePrx.setEditable(false);
        jButton5.setText("Actualizar");
        jButton5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton5ActionPerformed(evt);
            }
        });
        jButton6.setText("Guardar");
        jButton6.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton6ActionPerformed(evt);
            }
        });
        jLabel11.setFont(new Font("Tahoma", 1, 11));
        jLabel11.setText("NOTA: TECLEAR UNICAMENTE NUMEROS ENTEROS SIN FORMATO.");
        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel11, -1, 429, 32767).addGroup(jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel9, -1, 138, 32767).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)).addGroup(jPanel3Layout.createSequentialGroup().addComponent(jLabel5, -2, 91, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)).addComponent(jLabel6, -1, 138, 32767).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jButton5).addComponent(jLabel10, -1, 138, 32767)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))).addGap(18, 18, 18).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel7).addComponent(TxtFacArch, -2, 82, -2).addComponent(TxtDevArch, -2, 82, -2).addComponent(TxtNceArch, -2, 82, -2)).addGap(23, 23, 23).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(TxtFacPrx, -2, 79, -2).addComponent(jLabel8).addComponent(TxtDevPrx, -2, 79, -2).addComponent(TxtNcePrx, -2, 79, -2).addComponent(jButton6)).addGap(27, 27, 27)))));
        jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel6).addComponent(jLabel7).addComponent(jLabel8)).addGap(35, 35, 35).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel5).addComponent(TxtFacArch, -2, -1, -2).addComponent(TxtFacPrx, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel9).addComponent(TxtDevArch, -2, -1, -2).addComponent(TxtDevPrx, -2, -1, -2)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel10).addComponent(TxtNceArch, -2, -1, -2).addComponent(TxtNcePrx, -2, -1, -2)).addGap(26, 26, 26).addComponent(jLabel11).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, 32767).addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton6).addComponent(jButton5)).addGap(20, 20, 20)));
        jTabbedPane1.addTab("REGISTROS", jPanel3);
        jLabel12.setText("RUTA:");
        TxtAcrobat.setName("TxtParadox");
        jLabel13.setFont(new Font("Tahoma", 1, 12));
        jLabel13.setText("NOTA: Sustituya todos los \\ con \\\\");
        jButton7.setText("Actualizar");
        jButton7.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton7ActionPerformed(evt);
            }
        });
        jButton8.setText("Guardar");
        jButton8.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton8ActionPerformed(evt);
            }
        });
        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup().addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(TxtAcrobat, -1, 336, 32767)).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(jLabel12, -2, 44, -2)).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addGap(22, 22, 22).addComponent(jButton7).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, 32767).addComponent(jButton8, -2, 88, -2)).addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup().addContainerGap().addComponent(jLabel13, -1, 336, 32767))).addContainerGap(93, -2)));
        jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel4Layout.createSequentialGroup().addGap(30, 30, 30).addComponent(jLabel12).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(TxtAcrobat, -2, -1, -2).addGap(18, 18, 18).addComponent(jLabel13, -2, 29, -2).addGap(18, 18, 18).addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton7).addComponent(jButton8)).addContainerGap(99, 32767)));
        jButton7ActionPerformed(null);
        jTabbedPane1.addTab("ACROBAT", jPanel4);
        jLabel14.setText("PARA FACTURACION:");
        jLabel15.setText("PARA DEVOLUCION:");
        jLabel16.setText("PARA REBAJAS:");
        jButton9.setText("Actualizar");
        jButton9.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton9ActionPerformed(evt);
            }
        });
        jButton10.setText("Guardar");
        jButton10.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                jButton10ActionPerformed(evt);
            }
        });
        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addContainerGap().addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(jButton9).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false).addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING, -1, -1, 32767).addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, -1, 125, 32767))).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(TxtSerDev).addComponent(TxtSerFac).addComponent(TxtSerNce, -2, 63, -2))).addGroup(jPanel5Layout.createSequentialGroup().addGap(69, 69, 69).addComponent(jButton10))).addContainerGap(162, 32767)));
        jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel5Layout.createSequentialGroup().addGap(48, 48, 48).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel14).addComponent(TxtSerFac, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel15).addComponent(TxtSerDev, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel16).addComponent(TxtSerNce, -2, -1, -2)).addGap(38, 38, 38).addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jButton9).addComponent(jButton10)).addContainerGap(52, 32767)));
        jButton9ActionPerformed(null);
        jTabbedPane1.addTab("SERIES", jPanel5);
        TxtScripts.setEditable(false);
        TxtScripts.setText("delete from CFD_IMPUESTOS;\ndelete from CFD_CONCEPTOS;\ndelete from CFD_DESCUENTOS;\ndelete from CFD_CANCELACION;\ndelete from CFD_COMPROBANTE;\ndelete from CNET_CLIENTE;\ndelete from CNET_DOMICILIOS where iddomicilio > 1;\ndelete from CNET_ENTIDADES where identidad > 1;\ndelete from CFD_COBRANZA;\ndelete from CANCELADAS_FOSAR;\nupdate CFD_FOLIOS set FOLIOACTUAL = 0;");
        jScrollPane1.setViewportView(TxtScripts);
        jButton12.setText("Ejecutar");
        jButton12.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt)
            {
                try {
					jButton12ActionPerformed(evt);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel6Layout.createSequentialGroup().addContainerGap().addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1, -2, 356, -2).addComponent(jButton12)).addContainerGap(73, 32767)));
        jPanel6Layout.setVerticalGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -1, 201, 32767).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton12).addContainerGap()));
        jTabbedPane1.addTab("INICIALIZAR BD", jPanel6);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane1, -1, 380, 32767).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jTabbedPane1, -1, 278, 32767).addContainerGap()));
        pack();
    }

    String Formato_Texto(String dato)
    {
        int i = 0;
        i = dato.indexOf("|");
        dato = dato.substring(0, i);
        return dato;
    }

    private void jButton1ActionPerformed(ActionEvent evt)
    {
        String ruta = Paradox.Leer();
        ruta = Formato_Texto(ruta);
        TxtParadox.setText(ruta);
    }

    void Mensaje(String Mensaje)
    {
        JOptionPane.showMessageDialog(this, Mensaje);
    }

    private void jButton2ActionPerformed(ActionEvent evt)
    {
        String ruta = TxtParadox.getText();
        if(ruta.indexOf("\\") > 0)
        {
            Mensaje("POR FAVOR LEA LA NOTA Y VERIFIQUE LA RUTA TECLEADA");
        } else
        {
            Paradox.Escribir((new StringBuilder()).append(ruta).append("|").toString());
            Mensaje("DATOS GUARDADOS DE MANERA CORRECTA");
        }
    }

    private void jButton3ActionPerformed(ActionEvent evt)
    {
        String ruta = Rutas.Leer();
        ruta = Formato_Texto(ruta);
        TxtRegistros.setText(ruta);
    }

    private void jButton4ActionPerformed(ActionEvent evt)
    {
        String ruta = TxtRegistros.getText();
        if(ruta.indexOf("/") > 0)
        {
            Mensaje("POR FAVOR LEA LA NOTA Y VERIFIQUE LA RUTA TECLEADA");
        } else
        {
            Rutas.Escribir((new StringBuilder()).append(ruta).append("|").toString());
            Mensaje("DATOS GUARDADOS DE MANERA CORRECTA");
        }
    }

    private void jButton5ActionPerformed(ActionEvent evt)
    {
        Get_Datos();
    }

    private void jButton6ActionPerformed(ActionEvent evt)
    {
        Guarda_Registros();
    }

    void Guarda_Registros()
    {
        int i = 0;
        i += Guarda_Factura();
        i += Guarda_Devolucion();
        i += Guarda_NotaCredito();
        if(i == 0)
            Mensaje("DATOS GUARDADOS DE MANERA CORRECTA");
    }

    int Guarda_Factura()
    {
        String numreg = TxtFacArch.getText();
        int NUM_REG = 0;
        boolean ban = true;
        try
        {
            NUM_REG = Integer.parseInt(numreg);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ban = false;
        }
        if(ban && NUM_REG >= 0)
        {
            Facturas.Escribir((new StringBuilder()).append(numreg).append("|").toString());
            return 0;
        } else
        {
            Mensaje("SOLO TECLEE NUMEROS ENTEROS SIN FORMATO Y POSITIVOS");
            TxtFacArch.grabFocus();
            return 1;
        }
    }

    int Guarda_Devolucion()
    {
        String numreg = TxtDevArch.getText();
        int NUM_REG = 0;
        boolean ban = true;
        try
        {
            NUM_REG = Integer.parseInt(numreg);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ban = false;
        }
        if(ban && NUM_REG >= 0)
        {
            Devoluciones.Escribir((new StringBuilder()).append(numreg).append("|").toString());
            return 0;
        } else
        {
            Mensaje("SOLO TECLEE NUMEROS ENTEROS SIN FORMATO Y POSITIVOS");
            TxtDevArch.grabFocus();
            return 1;
        }
    }

    int Guarda_NotaCredito()
    {
        String numreg = TxtNceArch.getText();
        int NUM_REG = 0;
        boolean ban = true;
        try
        {
            NUM_REG = Integer.parseInt(numreg);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ban = false;
        }
        if(ban && NUM_REG >= 0)
        {
            NotasCredito.Escribir((new StringBuilder()).append(numreg).append("|").toString());
            return 0;
        } else
        {
            Mensaje("SOLO TECLEE NUMEROS ENTEROS SIN FORMATO Y POSITIVOS");
            TxtNceArch.grabFocus();
            return 1;
        }
    }

    public void CrearProgreso()
    {
        ps.CreaFrame("RECOPILANDO INFORMACION DE PARADOX");
        ps.CreaProgreso();
        ps.SetBorde("RECOPILANDO DATOS DE PARADOX");
        ps.CreaBorde();
    }

    void Get_Datos()
    {
        CrearProgreso();
        ps.SetBorde("RECOPILANDO DATOS DE PARADOX");
        Facturas_txt();
        Devoluciones_txt();
        NotasCredito_txt();
        try {
				Facturas_Prx();
				Devoluciones_Prx();
				NotasCredito_Prx();				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        ps.CerrarProgreso();
    }

    void Facturas_txt()
    {
        String numreg = Facturas.Leer();
        numreg = Formato_Texto(numreg);
        TxtFacArch.setText(numreg);
    }

    void Devoluciones_txt()
    {
        String numreg = Devoluciones.Leer();
        numreg = Formato_Texto(numreg);
        TxtDevArch.setText(numreg);
    }

    void Facturas_Prx()
        throws ClassNotFoundException, SQLException, IOException
    {
        String numreg = Conexion("FACT01");
        TxtFacPrx.setText(numreg);
    }

    void Devoluciones_Prx()
        throws ClassNotFoundException, SQLException, IOException
    {
        String numreg = Conexion("FACT01");
        TxtDevPrx.setText(numreg);
    }

    void NotasCredito_txt()
    {
        String numreg = NotasCredito.Leer();
        numreg = Formato_Texto(numreg);
        TxtNceArch.setText(numreg);
    }

    void NotasCredito_Prx()
        throws ClassNotFoundException, SQLException, IOException
    {
        String numreg = Conexion("CUEN01");
        TxtNcePrx.setText(numreg);
    }

    String Conexion(String tabla)
        throws ClassNotFoundException, SQLException, IOException
    {
        String sql = "";
        String numreg = "SIN ACCESO PARADOX";
        boolean ban = true;
        cnx.conectarParadox();
        sql = (new StringBuilder()).append("SELECT MAX(NUM_REG) AS numreg FROM ").append(tabla).toString();
        try
        {
            rs = cnx.consulta(sql, true);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ban = false;
        }
        if(ban && rs.next())
            numreg = (new StringBuilder()).append("").append(rs.getInt("numreg")).toString();
        cnx.finalizar();
        System.gc();
        return numreg;
    }

    private void jButton7ActionPerformed(ActionEvent evt)
    {
        String ruta = Acrobat.Leer();
        ruta = Formato_Texto(ruta);
        TxtAcrobat.setText(ruta);
    }

    private void jButton8ActionPerformed(ActionEvent evt)
    {
        String ruta = TxtAcrobat.getText();
        if(ruta.indexOf("/") > 0)
        {
            Mensaje("POR FAVOR LEA LA NOTA Y VERIFIQUE LA RUTA TECLEADA");
        } else
        {
            Acrobat.Escribir((new StringBuilder()).append(ruta).append("|").toString());
            Mensaje("DATOS GUARDADOS DE MANERA CORRECTA");
        }
    }

    private void jButton9ActionPerformed(ActionEvent evt)
    {
        int i = 0;
        String series = Series.Leer();
        series = Formato_Texto(series);
        i = series.indexOf(",");
        TxtSerFac.setText(series.substring(0, i));
        series = series.substring(i + 1);
        i = series.indexOf(",");
        TxtSerDev.setText(series.substring(0, i));
        series = series.substring(i + 1);
        TxtSerNce.setText(series);
    }

    private void jButton10ActionPerformed(ActionEvent evt)
    {
        String serie = TxtSerFac.getText().replaceAll(" ", "");
        serie = (new StringBuilder()).append(serie).append(",").append(TxtSerDev.getText().replaceAll(" ", "")).toString();
        serie = (new StringBuilder()).append(serie).append(",").append(TxtSerNce.getText().replaceAll(" ", "")).toString();
        Series.Escribir((new StringBuilder()).append(serie).append("|").toString());
        Mensaje("DATOS GUARDADOS DE MANERA CORRECTA");
    }

    private void jButton12ActionPerformed(ActionEvent evt) throws Exception
    {
    	try {
			Realiza_Scripts();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    void Realiza_Scripts() throws Exception {
        ConexionFirebird _tmp = fbx;
        ConexionFirebird.conectarFirebird();
        ObtenScripts();
        EjecutaScripts();
        fbx.finalizar();
        System.gc();
        Mensaje("BASE DE DATOS INICIALIZADA");
    }

    void ObtenScripts()
    {
        String scripts = TxtScripts.getText();
        SCRIPTS = new ArrayList();
        for(int i = 0; (i = scripts.indexOf(";")) > 0;)
        {
            SCRIPTS.add(scripts.substring(0, i));
            if(i + 2 < scripts.length())
                scripts = scripts.substring(i + 2);
            else
                scripts = "";
        }

    }

    void EjecutaScripts()
        throws ClassNotFoundException, SQLException, IOException
    {
        int i = 0;
        String sql = "";
        do
        {
            if(i >= SCRIPTS.size())
                break;
            sql = SCRIPTS.get(i).toString();
            if(!ConexionFB(sql))
            {
                Mensaje((new StringBuilder()).append("CONSULTA ---> ").append(sql).append(" SIN EJECUTARSE, REVISE CONEXION ").toString());
                break;
            }
            i++;
        } while(true);
    }

    boolean ConexionFB(String sql)
        throws ClassNotFoundException, SQLException, IOException
    {
        boolean ban = true;
        try
        {
            rs = fbx.consulta(sql, false);
            fbx.commit();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ban = false;
        }
        return ban;
    }

    public static void main(String args[])
    {
        EventQueue.invokeLater(new Runnable() {

            public void run()
            {
                (new Fosar_Configuracion()).setVisible(true);
            }

        }
);
    }

    Archivos Paradox;
    Archivos Rutas;
    Archivos Acrobat;
    Archivos Series;
    Archivos Scripts;
    Archivos Facturas;
    Archivos Devoluciones;
    Archivos NotasCredito;
    ConexionParadox cnx;
    ResultSet rs;
    ConexionFirebird fbx;
    ArrayList SCRIPTS;
    ProgressBarSample ps;
    private JTextField TxtAcrobat;
    private JTextField TxtDevArch;
    private JTextField TxtDevPrx;
    private JTextField TxtFacArch;
    private JTextField TxtFacPrx;
    private JTextField TxtNceArch;
    private JTextField TxtNcePrx;
    private JTextField TxtParadox;
    private JTextField TxtRegistros;
    private JTextPane TxtScripts;
    private JTextField TxtSerDev;
    private JTextField TxtSerFac;
    private JTextField TxtSerNce;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton12;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JCheckBox jCheckBox1;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JTabbedPane jTabbedPane1;
}
