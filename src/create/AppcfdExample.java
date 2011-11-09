// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:51:54 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AppcfdExample.java

package create;

import com.codinet.facture.cfd.Certificado;
import com.codinet.facture.cfd.SelloDigital;
import com.codinet.facture.cfdv2.Comprobante;
import com.codinet.facture.cfdv2.exceptions.CFDException;
import com.codinet.facture.util.*;
import com.lowagie.text.DocumentException;
import gob.sat.sgi.SgiLibException;
import java.awt.HeadlessException;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.*;
import java.sql.SQLException;
import mx.com.codinet.err.ComprobanteArchRefException;
import mx.com.codinet.err.ImpresionRefException;
import org.jaxen.JaxenException;
import org.jdom.JDOMException;

// Referenced classes of package create:
//            FOSAR_AlmacenaCFD, Comprobantebd, Appcfd, SelloDigitalV1, 
//            SelloDigitalV2, GeneraSello, GeneraSelloV2, ComprobanteImp, 
//            ValidaFolio, ValidadorFacture, ProgressBarSample, Mensajes, 
//            ConexionFirebird

public class AppcfdExample
{

    private static void GuardaPaths(String direccion, String prefijo)
        throws SQLException, ComprobanteArchRefException, ClassNotFoundException, IOException
    {
        System.out.println((new StringBuilder()).append("RUTA : ").append(direccion).append(prefijo).toString());
        new FOSAR_AlmacenaCFD(direccion, prefijo, args[1], args[2]);
    }

    public AppcfdExample()
    {
        appcfdRUTA = "";
        appcfdPREFIJO = "";
    }

    public static PageFormat getDefaultPrinterPageFormat()
    {
        PageFormat printerSupportedPageFormat = new PageFormat();
        Paper printerSupportedPaper = new Paper();
        printerSupportedPaper.setSize(612D, 792D);
        printerSupportedPaper.setImageableArea(0.0D, 0.0D, printerSupportedPaper.getWidth(), printerSupportedPaper.getHeight());
        printerSupportedPageFormat.setPaper(printerSupportedPaper);
        return printerSupportedPageFormat;
    }

    void AsignaArgs(String args[])
    {
        AppcfdExample _tmp = this;
        args = new String[4];
        AppcfdExample _tmp1 = this;
        args = args;
    }

    public void AppcfdExample_main(String args[])
        throws ComprobanteArchRefException, IOException, SQLException, CFDException, JDOMException, NumberFormatException, HeadlessException, ClassNotFoundException, SgiLibException, JaxenException
    {
        AsignaArgs(args);
        boolean pathflag = false;
        boolean selloflag = false;
        Comprobante c = null;
        CodificarBase64 c64 = new CodificarBase64();
        Comprobantebd cbd = new Comprobantebd();
        Appcfd cfd = new Appcfd();
        CadenaOriginalv3 co3 = new CadenaOriginalv3();
        SelloDigital sign = new SelloDigital();
        SelloDigitalV1 signv1 = new SelloDigitalV1();
        SelloDigitalV2 signv2 = new SelloDigitalV2();
        GeneraSello sign_v1 = new GeneraSello();
        GeneraSelloV2 sign_v2 = new GeneraSelloV2();
        ComprobanteImp imp = new ComprobanteImp();
        Certificado cer = new Certificado();
        try
        {
            ValidaFolio vf = new ValidaFolio(cfd.Getidfolio(args[0], args[2], args[1]), Integer.parseInt(args[1]));
            vf.estableceConexion(cfd.cnx);
            imp.estableceConexion(cfd.cnx);
            cbd.estableceConexion(cfd.cnx);
            c = cfd.generarComprobante(Integer.parseInt(args[0]), args[1], args[2], Integer.parseInt(args[3]));
            if(c != null)
            {
                try
                {
                    c.setCertificado(c64.base64Encode(cfd.in_certificado64));
                    System.out.println((new StringBuilder("Certificado: ")).append(c.getCertificado()).toString());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                try
                {
                    cfd.ps.SetBorde("Generando Cadena Orgininal");
                    co3.generarCadenaOriginal(c);
                    System.out.println((new StringBuilder("Cadena original UTF8: ")).append(co3.getCadenaOriginalUTF8()).toString());
                    cfd.ps.SetBorde("Validando ruta de Archivos");
                    if((new File(cfd.direccion)).exists())
                    {
                        File fichero = new File((new StringBuilder(String.valueOf(cfd.direccion))).append("\\Mis Facturas\\").append(cfd.nombre_empresa).append("\\").append(cfd.nombre_sucursal).append("\\").toString());
                        pathflag = true;
                        if(!fichero.exists())
                        {
                            cfd.ps.SetBorde("Creando directorio");
                            fichero.mkdirs();
                            cfd.direccion = (new StringBuilder(String.valueOf(cfd.direccion))).append("\\Mis Facturas\\").append(cfd.nombre_empresa).append("\\").append(cfd.nombre_sucursal).append("\\").toString();
                        } else
                        {
                            cfd.ps.SetBorde("Cargando directorio");
                            cfd.direccion = (new StringBuilder(String.valueOf(cfd.direccion))).append("\\Mis Facturas\\").append(cfd.nombre_empresa).append("\\").append(cfd.nombre_sucursal).append("\\").toString();
                        }
                    } else
                    {
                        pathflag = false;
                        cfd.m.GetMensaje("Carpeta contenedora no existe, se genera en unidad C:\\Mis Facturas");
                        cfd.ps.CerrarProgreso();
                        File fichero = new File((new StringBuilder("C:\\Mis Facturas\\")).append(cfd.nombre_empresa).append("\\").append(cfd.nombre_sucursal).append("\\").toString());
                        pathflag = true;
                        if(!fichero.exists())
                        {
                            cfd.ps.SetBorde("Creando directorio");
                            fichero.mkdirs();
                            cfd.direccion = (new StringBuilder("C:\\Mis Facturas\\")).append(cfd.nombre_empresa).append("\\").append(cfd.nombre_sucursal).append("\\").toString();
                        } else
                        {
                            cfd.ps.SetBorde("Cargando directorio");
                            cfd.direccion = (new StringBuilder("C:\\Mis Facturas\\")).append(cfd.nombre_empresa).append("\\").append(cfd.nombre_sucursal).append("\\").toString();
                        }
                    }
                }
                catch(GenerarCadenaOriginalException e)
                {
                    e.printStackTrace();
                    cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                    cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                    cfd.cnx.commit();
                }
                if(Integer.parseInt(cfd.yearFactura) <= cfd.yearComparacion)
                {
                    cfd.ps.SetBorde("Crea Sello Digital V1");
                    signv1.generarSello(cfd.in_certificado, cfd.in_llave, cfd.pass, cfd.rfc_emisor, co3.getCadenaOriginalUTF8());
                    System.out.println((new StringBuilder("VERIFICANDO Sello_V1: ")).append(signv1.getSello()).toString());
                    if(sign_v1.Verificar(cfd.in_certificadp_comp, co3.getCadenaOriginalUTF8(), signv1.abyte0))
                        selloflag = true;
                    else
                        selloflag = false;
                } else
                if(Integer.parseInt(cfd.yearFactura) > cfd.yearComparacion)
                {
                    cfd.ps.SetBorde("Crea Sello Digital V2");
                    signv2.generarSello(cfd.in_certificado, cfd.in_llave, cfd.pass, cfd.rfc_emisor, co3.getCadenaOriginalUTF8());
                    System.out.println((new StringBuilder("Sello_V2: ")).append(signv2.getSello()).toString());
                    if(sign_v2.Verificar(cfd.in_certificadp_comp, co3.getCadenaOriginalUTF8(), signv2.abyte0))
                        selloflag = true;
                    else
                        selloflag = false;
                }
                if(pathflag && selloflag)
                {
                    if(cfd.nemonico.equals("DEMO") || cfd.nemonico.equals("BRIDD"))
                    {
                        cfd.ps.SetBorde("Define sello como demo");
                        c.setSello(cfd.demo);
                    } else
                    {
                        cfd.ps.SetBorde((new StringBuilder("Validando documento XML creado - ")).append(cfd.nemonico).toString());
                        if(Integer.parseInt(cfd.yearFactura) > cfd.yearComparacion)
                        {
                            c.setSello(signv2.getSello());
                            System.out.println((new StringBuilder("SE DEFINE Sello_V2: ")).append(signv2.getSello()).toString());
                        } else
                        {
                            c.setSello(signv1.getSello());
                            System.out.println((new StringBuilder("SE DEFINE Sello_V1: ")).append(signv1.getSello()).toString());
                        }
                    }
                    try
                    {
                        cfd.ps.SetBorde("Crea XML");
                        if(cfd.idcom > 0 || cfd.idadd > 0)
                        {
                            switch(cfd.idadd)
                            {
                            case 1: // '\001'
                                new MarshallCFDv2XML();
                                MarshallCFDv2XML.marshalCfdV2(c, new FileOutputStream((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString()));
                                break;

                            case 2: // '\002'
                                new MarshallCFDv2XML();
                                MarshallCFDv2XML.marshalCfdV2(c, new FileOutputStream((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString()));
                                break;

                            case 3: // '\003'
                                new MarshallCFDv2XML();
                                MarshallCFDv2XML.marshalCfdV2(c, new FileOutputStream((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString()));
                                break;

                            case 4: // '\004'
                                new MarshallCFDv2XML();
                                MarshallCFDv2XML.marshalCfdV2(c, new FileOutputStream((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString()));
                                break;
                            }
                            switch(cfd.idcom)
                            {
                            case 6: // '\006'
                                new MarshallCFDv2XML();
                                MarshallCFDv2XML.marshalCfdV2AddSchema(c, new FileOutputStream((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString()), "http://www.sat.gob.mx/detallista  http://www.sat.gob.mx/sitio_internet/cfd/detallista/detallista.xsd ");
                                Appcfd.ModifyXML((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString(), "xmlns:detallista", "http://www.sat.gob.mx/detallista");
                                break;
                            }
                        } else
                        {
                            new MarshallCFDv2XML();
                            MarshallCFDv2XML.marshalCfdV2(c, new FileOutputStream((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString()));
                        }
                        System.out.println((new StringBuilder("Archivo ")).append(cfd.direccion).append(cfd.prefijo).append(".xml").append(" creado...").toString());
                    }
                    catch(FileNotFoundException ex)
                    {
                        cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                        cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                        cfd.cnx.commit();
                        ex.printStackTrace();
                    }
                    cfd.ps.SetBorde((new StringBuilder("Validando documento XML creado - ")).append(cfd.nemonico).toString());
                    if((new ValidadorFacture()).validarFactura((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString(), cfd.nemonico))
                    {
                        try
                        {
                            cbd.insertaCFD((new StringBuilder(String.valueOf(cfd.direccion.trim()))).append(cfd.prefijo).append(".xml").toString(), Integer.parseInt(args[0]), cfd.type, Integer.parseInt(args[3]));
                            try
                            {
                                cfd.ps.SetBorde("Crea PDF");
                                imp.setNombre((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".pdf").toString(), cfd.image.getAbsolutePath(), cfd.pagge);
                                if(cfd.nemonico.equals("DEMO") || cfd.nemonico.equals("BRIDD"))
                                {
                                    cfd.ps.SetBorde("Define sello como demo");
                                    imp.construyeComprobante(args[1], args[2], cfd.demo, Integer.parseInt(args[0]), Integer.parseInt(args[3]), cfd.idDesign);
                                } else
                                {
                                    imp.construyeComprobante(args[1], args[2], co3.getCadenaOriginal(), Integer.parseInt(args[0]), Integer.parseInt(args[3]), cfd.idDesign);
                                }
                                System.out.println((new StringBuilder("Archivo ")).append(cfd.direccion).append(cfd.prefijo).append(".pdf").append(" creado...").toString());
                                Appcfd.RemoveNameSpace(cfd.xmlfile);
                                System.out.println("Modificando etiquetas de XML para addendas...");
                            }
                            catch(DocumentException e)
                            {
                                cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                                cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                                cfd.cnx.commit();
                                e.printStackTrace();
                                cfd.m.GetMensaje("Error al crear PDF");
                                cfd.ps.CerrarProgreso();
                            }
                            catch(ImpresionRefException e)
                            {
                                cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                                cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                                cfd.cnx.commit();
                                cfd.m.GetMensaje("Error al crear PDF");
                                cfd.ps.CerrarProgreso();
                                e.printStackTrace();
                            }
                            catch(Exception e)
                            {
                                cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                                cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                                cfd.cnx.commit();
                                e.printStackTrace();
                                cfd.m.GetMensaje("Error al crear PDF");
                                cfd.ps.CerrarProgreso();
                            }
                            if((new File((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".pdf").toString())).exists() && (new File((new StringBuilder(String.valueOf(cfd.direccion))).append(cfd.prefijo).append(".xml").toString())).exists())
                            {
                                cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'T' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                                cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                                cfd.cnx.commit();
                                cfd.ps.CerrarProgreso();
                                System.out.println("*******************************");
                                System.out.println((new StringBuilder()).append("Valor de Direccion ").append(cfd.direccion).toString());
                                System.out.println((new StringBuilder()).append("Valor de Prefijo ").append(cfd.prefijo).toString());
                                System.out.println("*******************************");
                                GuardaPaths(cfd.direccion, cfd.prefijo);
                            } else
                            {
                                cfd.m.GetMensaje((new StringBuilder("No fue posible generar su factura: ")).append(cfd.prefijo).toString());
                                cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                                cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                                cfd.cnx.commit();
                                cfd.ps.CerrarProgreso();
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            cfd.ps.CerrarProgreso();
                        }
                    } else
                    {
                        cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                        cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                        cfd.cnx.commit();
                        cfd.m.GetMensaje("El documento XML no es valido");
                        cfd.ps.CerrarProgreso();
                    }
                } else
                {
                    cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                    cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                    cfd.cnx.commit();
                    cfd.m.GetMensaje(sign.getMsgErrorTxt());
                    cfd.ps.CerrarProgreso();
                }
            } else
            {
                cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
                cfd.rs = cfd.cnx.consulta(cfd.sql, false);
                cfd.cnx.commit();
                cfd.m.GetMensaje("El certificado no es valido \363 no existe la referencia, no fue posible generar su documento");
                cfd.ps.CerrarProgreso();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            cfd.sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(args[1].trim()).append(" and serie = '").append(args[2]).append("' and idEmpresa = ").append(args[0]).toString();
            cfd.rs = cfd.cnx.consulta(cfd.sql, false);
            cfd.cnx.commit();
            cfd.ps.CerrarProgreso();
        }
        cfd.ps.CerrarProgreso();
    }

    public String appcfdRUTA;
    public String appcfdPREFIJO;
    static String args[] = new String[4];

}
