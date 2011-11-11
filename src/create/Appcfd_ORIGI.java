// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov 
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Appcfd_ORIGI.java

package create;

import anyObject.CrearObjetoAmlAdenda;
import com.codinet.facture.cfdv2.Comprobante;
import com.codinet.facture.cfdv2.ObjectFactory;
import com.codinet.facture.cfdv2.TUbicacion;
import com.codinet.facture.cfdv2.TUbicacionFiscal;
import com.codinet.facture.cfdv2.exceptions.ComprobanteException;
import com.codinet.facture.cfdv2.exceptions.ConceptoException;
import com.codinet.facture.cfdv2.exceptions.ReceptorException;
import com.codinet.facture.cfdv2.exceptions.TUbicacionException;
import com.codinet.facture.cfdv2.exceptions.TUbicacionFiscalException;
import com.codinet.facture.util.CadenaOriginalv3;
import com.codinet.facture.util.GenerarCadenaOriginalException;
import cripfia.ext.FirmasDigitales;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom.Element;
import org.jdom.Namespace;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

// Referenced classes of package create:
//            ConexionFirebird, Mensajes, ProgressBarSample

public class Appcfd_ORIGI
{

    public void CrearProgreso()
    {
        ps.CreaFrame("Generando documento");
        ps.CreaProgreso();
        ps.SetBorde("Generando Objeto Comprobante");
        ps.CreaBorde();
    }

    public Appcfd_ORIGI()
        throws SQLException, FileNotFoundException, IOException
    {
        firmasdigitales = new FirmasDigitales();
        direccion = null;
        cnx = new ConexionFirebird();
        con = null;
        m = new Mensajes();
        comprobante = null;
        emisor = null;
        receptor = null;
        conceptos = null;
        impuestos = null;
        adden = null;
        comple = null;
        impuestosRetenciones = null;
        impuestosTraslados = null;
        ofactory = new ObjectFactory();
        ps = new ProgressBarSample();
        nocertificado = null;
        ncertificado = null;
        timpuesto = null;
        rimpuesto = null;
        comprob = null;
        nombre_empresa = null;
        nombre_sucursal = null;
        prefijo = null;
        nemonico = null;
        pagge = null;
        type = null;
        demo = " FACTURE DEMO FACTURE DEMO FACTURE DEMO FACTURE DEMO FACTURE DEMO FACTURE DEMO FACTURE DEMO FACTURE DEMO FACTURE DEMO";
        subtotal = 0.0D;
        descuento = 0.0D;
        total_impuestos = 0.0D;
        idEntidadEmisor = 0L;
        idEntidadReceptor = 0L;
        domEmisor = 0L;
        domReceptor = 0L;
        anio = 0L;
        anio_aprob = 0L;
        naprovacion = 0L;
        idEmpresa = 0L;
        len = 0;
        pagos = 0;
        plazos = 0;
        idadd = 0;
        idcom = 0;
        bytes = new byte[8096];
        blobInputStream = null;
        rs = null;
        rs2 = null;
        rs_company = null;
        rs_val = null;
        try
        {
            ConexionFirebird.conectarFirebird();
        }
        catch(Exception e1)
        {
            e1.printStackTrace();
        }
    }

    public static Properties loadProperties(String fileName)
    {
        Properties tempProp = new Properties();
        try
        {
            InputStream propsFile = new FileInputStream(fileName);
            tempProp.load(propsFile);
            propsFile.close();
        }
        catch(IOException ioe)
        {
            System.out.println("I/O Exception.");
            ioe.printStackTrace();
            System.exit(0);
        }
        return tempProp;
    }

    public static void ModifyXML(String file, String Prefix, String Value)
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            org.w3c.dom.Element root = doc.getDocumentElement();
            root.setAttributeNS("http://www.w3.org/2000/xmlns/", Prefix, Value);
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();
            byte buf[] = xmlString.getBytes();
            OutputStream f0 = new FileOutputStream(file);
            for(int i = 0; i < buf.length; i++)
                f0.write(buf[i]);

            f0.close();
            buf = (byte[])null;
        }
        catch(SAXException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch(TransformerConfigurationException e)
        {
            e.printStackTrace();
        }
        catch(TransformerException e)
        {
            e.printStackTrace();
        }
    }

    public Comprobante generarComprobante(long idempresa, String folio, String serie, long sucursal)
        throws IOException, SQLException
    {
        CrearProgreso();
        ps.SetBorde("Crea Configuracion");
        try
        {
            sql = "SELECT * FROM CNET_MODULOS ";
            rs = cnx.consultaBLOB(sql, true);
            if(rs.next())
                nemonico = rs.getString("NEMONICO").trim();
            sql = (new StringBuilder("SELECT * FROM cfd_comprobante,cfd_config  WHERE cfd_comprobante.idempresa = cfd_config.idEmpresa and cfd_comprobante.sucursal = cfd_config.idSucursal and cfd_comprobante.nocertificado = cfd_config.SERIECERTIFICADO and cfd_config.idEmpresa =")).append(idempresa).append(" AND ").append("cfd_config.idSucursal = ").append(sucursal).append(" AND ").append("cfd_comprobante.FOLIO =").append(folio).append(" AND ").append("cfd_comprobante.SERIE ='").append(serie).append("'").toString();
            rs = cnx.consultaBLOB(sql, true);
            try
            {
                if(rs.next())
                {
                    direccion = rs.getString("dirContenedor");
                    pass = rs.getString("direccion").trim();
                    pagge = rs.getString("pagina").trim();
                    pubkey = rs.getBlob("dirPubKey");
                    certificado = new File("pukey.cer");
                    in_certificado = new BufferedInputStream(pubkey.getBinaryStream());
                    in_certificadp_comp = new BufferedInputStream(pubkey.getBinaryStream());
                    in_certificado64 = new BufferedInputStream(pubkey.getBinaryStream());
                    prikey = rs.getBlob("dirPrivKey");
                    llave = new File("pikey.key");
                    llave.getAbsolutePath();
                    in_llave = new BufferedInputStream(prikey.getBinaryStream());
                    im = rs.getBlob("imgFactura");
                    image = new File("factura.jpg");
                    in_image = new BufferedInputStream(im.getBinaryStream());
                    out = new BufferedOutputStream(new FileOutputStream(image));
                    while((len = in_image.read(bytes)) > 0) 
                        out.write(bytes, 0, len);
                    out.flush();
                    out.close();
                    in_image.close();
                    len = 0;
                    java.security.cert.X509Certificate x509certificate = firmasdigitales.cargaCert(new BufferedInputStream(pubkey.getBinaryStream()));
                    if(x509certificate != null)
                        if(!firmasdigitales.certNumSerie(x509certificate).equals(rs.getString("seriecertificado")))
                        {
                            m.GetMensajes("Numero de serie incorrecto");
                            System.out.println((new StringBuilder("Numero de serie incorrecto: ")).append(firmasdigitales.certNumSerie(x509certificate)).append(" vs ").append(rs.getString("seriecertificado")).toString());
                        } else
                        {
                            System.out.println((new StringBuilder("Numero de serie correcto: ")).append(firmasdigitales.certNumSerie(x509certificate)).append(" vs ").append(rs.getString("seriecertificado")).toString());
                        }
                    ps.SetBorde("Crea Emisor");
                    sql = (new StringBuilder("SELECT  emisor,receptor,dom_emisor,dom_receptor,idEmpresa,EXTRACT(YEAR FROM fecha) as anio,noAprobacion,noCertificado,sello,vers FROM cfd_comprobante  WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
                    rs = cnx.consulta(sql, true);
                    if(rs.next())
                    {
                        idEntidadEmisor = rs.getLong("emisor");
                        idEntidadReceptor = rs.getLong("receptor");
                        domEmisor = rs.getLong("dom_emisor");
                        domReceptor = rs.getLong("dom_receptor");
                        idEmpresa = rs.getLong("idEmpresa");
                        anio = rs.getInt("anio");
                        ncertificado = rs.getString("noCertificado").trim();
                        sello = rs.getString("sello");
                        version = rs.getString("vers");
                    }
                    sql = (new StringBuilder("SELECT cnet_entidades.nombre,cnet_entidades.rfc,cnet_domicilios.calle,cnet_colonias.codigo_postal,cnet_colonias.descripcion as colonia,cnet_estados.descripcion as estado,cnet_delegacion.nombre as delegacion,cnet_domicilios.n_exterior,cnet_domicilios.n_interior,cnet_pais.nombre as nombrepais,cnet_estados.pais FROM cnet_colonias,cnet_delegacion,cnet_domicilios,cnet_entidades,cnet_pais,cnet_estados WHERE ( cnet_entidades.idEntidad = cnet_domicilios.idEntidad ) and( cnet_domicilios.idDomicilio = ")).append(domEmisor).append(" ) and").append("( cnet_domicilios.idEstado = cnet_estados.idEstado ) and").append("( cnet_domicilios.idColonia = cnet_colonias.idColonia ) and").append("( cnet_colonias.idDelegacion = cnet_delegacion.idDelegacion ) and").append("( cnet_estados.pais = cnet_pais.id ) and").append("( ( cnet_entidades.idEntidad =").append(idEntidadEmisor).append("))").toString();
                    rs = cnx.consulta(sql, true);
                    if(rs.next())
                    {
                        cp = Remplazar(rs.getString("codigo_postal"));
                        interior = Remplazar(rs.getString("n_interior"));
                        exterior = Remplazar(rs.getString("n_exterior"));
                        rfc_emisor = Remplazar(rs.getString("rfc"));
                        emisor = ofactory.createComprobanteEmisor(new TUbicacionFiscal(rs.getString("calle"), rs.getString("delegacion"), rs.getString("estado"), rs.getString("nombrepais"), cp), rs.getString("nombre"), rfc_emisor);
                        emisor.getDomicilioFiscal().setColonia(rs.getString("colonia"));
                        emisor.getDomicilioFiscal().setNoExterior(exterior);
                        if(!interior.equals("0"))
                            emisor.getDomicilioFiscal().setNoInterior(interior);
                        sql = (new StringBuilder("SELECT cnet_entidades.nombre,cnet_estados.pais,cnet_colonias.codigo_postal,cnet_colonias.descripcion as colonia,cnet_estados.descripcion as estado,cnet_delegacion.nombre as delegacion,cnet_domicilios.calle,cnet_domicilios.n_exterior,cnet_pais.nombre as nombrepais,cnet_sucursal.tipo,cnet_domicilios.n_interior FROM cnet_entidades,cnet_sucursal,cnet_domicilios,cnet_colonias,cnet_delegacion,cnet_pais,cnet_estados WHERE ( cnet_sucursal.identidad = cnet_entidades.idEntidad ) and( cnet_domicilios.identidad  = cnet_sucursal.identidad ) and( cnet_domicilios.idDelegacion = cnet_delegacion.idDelegacion ) and( cnet_domicilios.idEstado = cnet_estados.idEstado ) and( cnet_domicilios.idColonia = cnet_colonias.idColonia ) and( cnet_estados.pais = cnet_pais.id ) and( ( cnet_sucursal.idsucursal = ")).append(sucursal).append(") )").toString();
                        rs = cnx.consulta(sql, true);
                        if(rs.next())
                        {
                            if(!rs.getString("tipo").equals("M"))
                            {
                                TUbicacion suc = new TUbicacion();
                                suc.setCalle(rs.getString("calle"));
                                suc.setColonia(rs.getString("colonia"));
                                suc.setCodigoPostal(rs.getString("codigo_postal"));
                                suc.setEstado(rs.getString("estado"));
                                suc.setMunicipio(rs.getString("delegacion"));
                                suc.setNoExterior(rs.getString("n_exterior"));
                                if(!rs.getString("n_interior").equals("0"))
                                    suc.setNoInterior(rs.getString("n_interior"));
                                suc.setPais(rs.getString("nombrepais"));
                                emisor.setExpedidoEn(suc);
                            }
                        } else
                        {
                            System.out.println("No existe referencia de la sucursal");
                        }
                    }
                    ps.SetBorde("Crea Receptor");
                    sql = (new StringBuilder("SELECT cnet_entidades.nombre,cnet_entidades.rfc,cnet_domicilios.calle,cnet_colonias.codigo_postal,cnet_colonias.descripcion as colonia,cnet_estados.descripcion as estado,cnet_delegacion.nombre as delegacion,cnet_domicilios.n_exterior,cnet_domicilios.n_interior,cnet_pais.nombre as nombrepais,cnet_estados.pais FROM cnet_colonias,cnet_delegacion,cnet_domicilios,cnet_entidades,cnet_pais,cnet_estados WHERE ( cnet_entidades.idEntidad = cnet_domicilios.idEntidad ) and( cnet_domicilios.idDomicilio = ")).append(domReceptor).append(" ) and").append("( cnet_domicilios.idEstado = cnet_estados.idEstado ) and").append("( cnet_domicilios.idColonia = cnet_colonias.idColonia ) and").append("( cnet_colonias.idDelegacion = cnet_delegacion.idDelegacion ) and").append("( cnet_estados.pais = cnet_pais.id ) and").append("( ( cnet_entidades.idEntidad =").append(idEntidadReceptor).append("))").toString();
                    rs = cnx.consulta(sql, true);
                    if(rs.next())
                    {
                        receptor = ofactory.createComprobanteReceptor(rs.getString("rfc"), rs.getString("nombre"), new TUbicacion(rs.getString("calle")));
                        receptor.getDomicilio().setCalle(rs.getString("calle"));
                        receptor.getDomicilio().setMunicipio(rs.getString("delegacion"));
                        receptor.getDomicilio().setEstado(rs.getString("estado"));
                        receptor.getDomicilio().setPais(rs.getString("nombrepais"));
                        receptor.getDomicilio().setCodigoPostal(rs.getString("codigo_postal"));
                        receptor.getDomicilio().setColonia(rs.getString("colonia"));
                        receptor.getDomicilio().setNoExterior(rs.getString("n_exterior"));
                        if(!rs.getString("n_interior").equals("0"))
                            receptor.getDomicilio().setNoInterior(rs.getString("n_interior"));
                    }
                    ps.SetBorde("Crea Conceptos");
                    sql = (new StringBuilder("SELECT cfd_conceptos.cantidad,cfd_conceptos.descripcion,cfd_conceptos.importe,cfd_conceptos.valorUnitario FROM cfd_conceptos WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
                    rs = cnx.consulta(sql, true);
                    conceptos = ofactory.createComprobanteConceptos();
                    while(rs.next()) 
                    {
                        conceptos.getConcepto().add(ofactory.createComprobanteConceptosConcepto(rs.getDouble("cantidad"), rs.getString("descripcion"), rs.getDouble("importe"), rs.getDouble("valorUnitario")));
                        subtotal += rs.getDouble("importe");
                    }
                    ps.SetBorde("Crea Descuentos");
                    sql = (new StringBuilder("SELECT IMPORTE_TOTAL FROM CFD_DESCUENTOS WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
                    for(rs = cnx.consulta(sql, true); rs.next();)
                        descuento += rs.getDouble("IMPORTE_TOTAL");

                    subtotal -= descuento;
                    ps.SetBorde("Crea Impuestos");
                    impuestos = ofactory.createComprobanteImpuestos();
                    impuestosTraslados = ofactory.createComprobanteImpuestosTraslados();
                    impuestosRetenciones = ofactory.createComprobanteImpuestosRetenciones();
                    ps.SetBorde("Crea Traslados");
                    sql = (new StringBuilder("SELECT sum(cfd_impuestos.importe) as importe,cnet_impuesto.descripcion,cfd_impuestos.idCNImpuesto,cfd_impuestos.idCatImpuesto FROM cfd_impuestos,cnet_impuesto WHERE ( cfd_impuestos.idCNImpuesto = cnet_impuesto.idCNImpuesto ) and( ( cfd_impuestos.folio = ")).append(folio).append(" ) AND").append("( cfd_impuestos.serie = '").append(serie).append("' ) AND").append("( cfd_impuestos.idEmpresa =").append(idempresa).append(") AND").append("(( cfd_impuestos.tipo = '+' )or( cfd_impuestos.tipo = 'T' ))) group by cnet_impuesto.descripcion,cfd_impuestos.idCNImpuesto,cfd_impuestos.idCatImpuesto").toString();
                    rs = cnx.consulta(sql, true);
                    do
                    {
                        if(!rs.next())
                            break;
                        sql = (new StringBuilder("SELECT cfd_det_impuestos.impuesto FROM   cfd_det_impuestos WHERE ( cfd_det_impuestos.idCNImpuesto = ")).append(rs.getInt("idCNImpuesto")).append(") and").append("( cfd_det_impuestos.idCatImpuesto =").append(rs.getInt("idCatImpuesto")).append(")").toString();
                        rs2 = cnx.consulta(sql, true);
                        if(rs2.next())
                        {
                            if(rs.getString("descripcion").trim().equals("IEPS"))
                                impuestosTraslados.getTraslado().add(ofactory.createComprobanteImpuestosTrasladosTrasladoIEPS(Math.abs(rs.getDouble("importe")), rs2.getDouble("impuesto")));
                            if(rs.getString("descripcion").trim().equals("IVA"))
                                impuestosTraslados.getTraslado().add(ofactory.createComprobanteImpuestosTrasladosTrasladoIVA(Math.abs(rs.getDouble("importe")), rs2.getDouble("impuesto")));
                        }
                    } while(true);
                    impuestos.setTraslados(impuestosTraslados);
                    ps.SetBorde("Crea Retenciones");
                    sql = (new StringBuilder("SELECT sum(cfd_impuestos.importe)as importe,cnet_impuesto.descripcion FROM cfd_impuestos,cnet_impuesto WHERE ( cfd_impuestos.idCNImpuesto = cnet_impuesto.idCNImpuesto ) and( ( cfd_impuestos.folio = ")).append(folio).append(" ) AND").append("( cfd_impuestos.serie = '").append(serie).append("' ) AND").append("( cfd_impuestos.idEmpresa =").append(idempresa).append(") AND").append("(( cfd_impuestos.tipo = '-' )or( cfd_impuestos.tipo = 'R' ))) group by cnet_impuesto.descripcion,cfd_impuestos.idCNImpuesto,cfd_impuestos.idCatImpuesto").toString();
                    rs = cnx.consulta(sql, true);
                    do
                    {
                        if(!rs.next())
                            break;
                        rs.getDouble("importe");
                        if(rs.getString("descripcion").trim().equals("IVA"))
                            impuestosRetenciones.getRetencion().add(ofactory.createComprobanteImpuestosRetencionesRetencionIVA(Math.abs(rs.getDouble("importe"))));
                        if(rs.getString("descripcion").trim().equals("ISR"))
                            impuestosRetenciones.getRetencion().add(ofactory.createComprobanteImpuestosRetencionesRetencionISR(Math.abs(rs.getDouble("importe"))));
                    } while(true);
                    if(rs.last())
                        impuestos.setRetenciones(impuestosRetenciones);
                    ps.SetBorde("Crea Total Impuestos");
                    sql = (new StringBuilder("SELECT sum(cfd_impuestos.importe) as importe  FROM cfd_impuestos,cnet_impuesto WHERE ( cfd_impuestos.idCNImpuesto = cnet_impuesto.idCNImpuesto ) and( ( cfd_impuestos.folio = ")).append(folio).append(" ) AND").append("( cfd_impuestos.serie = '").append(serie).append("' ) AND").append("( cfd_impuestos.idEmpresa =").append(idempresa).append("))").toString();
                    rs = cnx.consulta(sql, true);
                    if(rs.next())
                        total_impuestos = rs.getDouble("importe");
                    System.out.println(emisor.getNombre());
                    System.out.println(receptor.getNombre());
                    System.out.println(conceptos.toString());
                    System.out.println(impuestos.getTotalImpuestosTrasladados());
                    System.out.println(impuestos.getTotalImpuestosRetenidos());
                    System.out.println(folio);
                    System.out.println(new Date());
                    System.out.println(naprovacion);
                    System.out.println(anio);
                    System.out.println(ncertificado);
                    System.out.println(subtotal);
                    System.out.println(total_impuestos);
                    ps.SetBorde("Creando Objeto Comprobante");
                    comprobante = ofactory.createComprobanteIngreso(emisor, receptor, conceptos, impuestos, folio, new Date(), "SELLO", naprovacion, anio_aprob, ncertificado, subtotal, subtotal + total_impuestos);
                    comprobante.setDescuento(descuento);
                    sql = (new StringBuilder("SELECT * FROM cfd_comprobante WHERE folio = ")).append(folio).append(" AND ").append("serie='").append(serie.trim()).append("' AND ").append("idEmpresa = ").append(idempresa).toString();
                    rs = cnx.consulta(sql, true);
                    if(rs.next())
                    {
                        comprobante.setFecha(rs.getTimestamp("fecha"));
                        Format formatter = new SimpleDateFormat("yyyyMMdd");
                        String dt = formatter.format(rs.getTimestamp("fecha"));
                        ps.SetBorde("Definiendo Nombre de Documento");
                        switch(rs.getInt("IDTDOCTO"))
                        {
                        case 1: // '\001'
                            prefijo = (new StringBuilder("FACT_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "I";
                            comprobante.setTipoDeComprobante(1);
                            break;

                        case 2: // '\002'
                            prefijo = (new StringBuilder("RHN_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "I";
                            break;

                        case 3: // '\003'
                            prefijo = (new StringBuilder("NCA_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "I";
                            break;

                        case 4: // '\004'
                            prefijo = (new StringBuilder("NCR_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "E";
                            comprobante.setTipoDeComprobante(2);
                            break;

                        case 5: // '\005'
                            prefijo = (new StringBuilder("RPG_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "I";
                            break;

                        case 6: // '\006'
                            prefijo = (new StringBuilder("RAO_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "I";
                            break;

                        default:
                            prefijo = (new StringBuilder("XXX_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
                            type = "I";
                            break;
                        }
                        ps.f.setTitle((new StringBuilder(String.valueOf(prefijo))).append(" ").append(nemonico).toString());
                        if(rs.getString("f_pago") != null)
                        {
                            int longitud = rs.getString("f_pago").length();
                            if(longitud > 3)
                            {
                                int divide = rs.getString("f_pago").length() % 2;
                                if(divide > 0)
                                    divide = rs.getString("f_pago").length() / 2 + 1;
                                else
                                    divide = rs.getString("f_pago").length() / 2;
                                pagos = Integer.parseInt(rs.getString("f_pago").substring(0, divide - 1).trim());
                                plazos = Integer.parseInt(rs.getString("f_pago").substring(divide + 1, longitud).trim());
                                if(plazos > 0)
                                    comprobante.setFormaDePagoParcialidades(pagos, plazos);
                            }
                        }
                    }
                    if(serie.equals("_"))
                        comprobante.setSerie("");
                    else
                        comprobante.setSerie(serie);
                    sql = (new StringBuilder("SELECT empresa.nombrecomercial as nombre_empresa, sucursal.descripcion as nombre_sucursal FROM cnet_empresa as empresa, cnet_sucursal as sucursal WHERE empresa.idempresa = ")).append(idempresa).append("and ").append("sucursal.idempresa = empresa.idempresa and ").append("sucursal.idsucursal =").append(sucursal).toString();
                    rs = cnx.consulta(sql, true);
                    if(rs.next())
                    {
                        nombre_empresa = rs.getString("nombre_empresa").trim();
                        nombre_sucursal = rs.getString("nombre_sucursal").trim();
                    }
                    comple = ofactory.createComprobanteComplemento();
                    adden = ofactory.createComprobanteAddenda();
                    comprobante.setAddenda(adden);
                    comprobante.setComplemento(comple);
                    ps.SetBorde("Crea Addenda");
                    long reg = 0L;
                    long rem = 0L;
                    long art = 0L;
                    long ped = 0L;
                    boolean empty = false;
                    String sql_company = "";
                    String sql_val = "";
                    sql = (new StringBuilder("SELECT CFD_VALADE.IDVAL,CFD_CAMADE.NOMBRE,CFD_CAMADE.IDADD,CFD_CAMADE.FORMATO,CFD_CAMADE.COMPANY,CFD_VALADE.IDCAMPO,CFD_VALADE.FOLIO,CFD_VALADE.SERIE,CFD_VALADE.VAL,CFD_VALADE.CVEART,CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  LEFT JOIN CFD_ADDENDA ON  CFD_CAMADE.IDADD = CFD_ADDENDA.IDADD  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append("CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append("CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND CFD_CAMADE.IDPADRE = 0 AND CFD_CAMADE.IDATRIBUTO = 0 AND CFD_ADDENDA.TIPO = 1 ").toString();
                    Element adendaElement;
                    for(rs = cnx.consulta(sql, true); rs.next(); adden.getAny().add((new CrearObjetoAmlAdenda()).construirAdenda(adendaElement)))
                    {
                        idadd = (int)rs.getLong("IDADD");
                        sql2 = (new StringBuilder("SELECT VACIO FROM CFD_ADDENDA WHERE CFD_ADDENDA.IDADD =")).append(rs.getLong("IDADD")).toString();
                        rs2 = cnx.consulta(sql2, true);
                        if(rs2.next())
                            if(rs2.getLong("VACIO") > 0L)
                                empty = true;
                            else
                                empty = false;
                        System.out.println(rs.getString("NOMBRE"));
                        adendaElement = null;
                        if(rs.getString("NOMBRE").indexOf(":") > 0)
                        {
                            adendaElement = new Element(Remplazar(rs.getString("NOMBRE").substring(rs.getString("NOMBRE").indexOf(":") + 1, rs.getString("NOMBRE").length())));
                            adendaElement.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                        } else
                        {
                            adendaElement = new Element(Remplazar(rs.getString("NOMBRE")));
                        }
                        if(Remplazar(rs.getString("VAL").trim()) == null)
                            adendaElement.addContent("");
                        else
                            adendaElement.addContent(Remplazar(rs.getString("VAL").trim()));
                        GetAtributosAddenda(adendaElement, rs.getString("NOMBRE"), Long.valueOf(Long.parseLong(rs.getString("IDCAMPO"))), Long.valueOf(Long.parseLong("1")), Long.valueOf(Long.parseLong(folio)), serie.trim(), Long.valueOf(idempresa), Boolean.valueOf(empty));
                        GetHijosAddenda(adendaElement, rs.getString("NOMBRE"), Long.valueOf(Long.parseLong(rs.getString("IDCAMPO"))), Long.valueOf(Long.parseLong("1")), Long.valueOf(Long.parseLong(folio)), serie.trim(), Long.valueOf(idempresa), Boolean.valueOf(empty));
                    }

                    comprobante.setAddenda(adden);
                    ps.SetBorde("Crea Complemento(s)");
                    reg = 0L;
                    rem = 0L;
                    art = 0L;
                    ped = 0L;
                    empty = false;
                    sql_company = "";
                    sql_val = "";
                    sql = (new StringBuilder("SELECT CFD_VALADE.IDVAL,CFD_CAMADE.NOMBRE,CFD_CAMADE.IDADD,CFD_CAMADE.FORMATO,CFD_CAMADE.COMPANY,CFD_VALADE.IDCAMPO,CFD_VALADE.FOLIO,CFD_VALADE.SERIE,CFD_VALADE.VAL,CFD_VALADE.CVEART,CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  LEFT JOIN CFD_ADDENDA ON CFD_CAMADE.IDADD = CFD_ADDENDA.IDADD  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append("CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append("CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND CFD_CAMADE.IDPADRE = 0 AND CFD_CAMADE.IDATRIBUTO = 0 AND CFD_ADDENDA.TIPO = 2 ").toString();
                    Element complementoElement;
                    for(rs = cnx.consulta(sql, true); rs.next(); comple.getAny().add((new CrearObjetoAmlAdenda()).construirAdenda(complementoElement)))
                    {
                        idcom = (int)rs.getLong("IDADD");
                        sql2 = (new StringBuilder("SELECT VACIO FROM CFD_ADDENDA WHERE CFD_ADDENDA.IDADD =")).append(rs.getLong("IDADD")).toString();
                        rs2 = cnx.consulta(sql2, true);
                        if(rs2.next())
                            if(rs2.getLong("VACIO") > 0L)
                                empty = true;
                            else
                                empty = false;
                        System.out.println(rs.getString("NOMBRE"));
                        complementoElement = null;
                        if(rs.getString("NOMBRE").indexOf(":") > 0)
                        {
                            complementoElement = new Element(Remplazar(rs.getString("NOMBRE").substring(rs.getString("NOMBRE").indexOf(":") + 1, rs.getString("NOMBRE").length())));
                            complementoElement.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                        } else
                        {
                            complementoElement = new Element(Remplazar(rs.getString("NOMBRE")));
                        }
                        if(Remplazar(rs.getString("VAL").trim()) == null)
                            complementoElement.addContent("");
                        else
                            complementoElement.addContent(Remplazar(rs.getString("VAL").trim()));
                        GetAtributosAddenda(complementoElement, rs.getString("NOMBRE"), Long.valueOf(Long.parseLong(rs.getString("IDCAMPO"))), Long.valueOf(Long.parseLong("1")), Long.valueOf(Long.parseLong(folio)), serie.trim(), Long.valueOf(idempresa), Boolean.valueOf(empty));
                        GetHijosAddenda(complementoElement, rs.getString("NOMBRE"), Long.valueOf(Long.parseLong(rs.getString("IDCAMPO"))), Long.valueOf(Long.parseLong("1")), Long.valueOf(Long.parseLong(folio)), serie.trim(), Long.valueOf(idempresa), Boolean.valueOf(empty));
                    }

                    comprobante.setComplemento(comple);
                }
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch(ComprobanteException ex)
        {
            System.out.println((new StringBuilder("error.")).append(ex.getTipoError()).toString());
            ex.printStackTrace();
        }
        catch(ReceptorException ex)
        {
            ex.printStackTrace();
        }
        catch(TUbicacionFiscalException ex)
        {
            ex.printStackTrace();
        }
        catch(ConceptoException ex)
        {
            ex.printStackTrace();
        }
        catch(TUbicacionException ex)
        {
            ex.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return comprobante;
    }

    public String Remplazar(String cadena)
    {
        cadena = cadena.replaceAll(" ", "");
        cadena = cadena.replace('\321', 'N');
        cadena = cadena.replace('n', 'n');
        return cadena.trim();
    }

    public String Getidfolio(String empresa, String serie)
        throws SQLException
    {
        if(serie.equals("_"))
        {
            sql = (new StringBuilder("SELECT * FROM cfd_folios WHERE idEmpresa = ")).append(empresa).append(" AND ").append("serie is null or serie = '' ").toString();
            rs = cnx.consulta(sql, true);
        } else
        {
            sql = (new StringBuilder("SELECT * FROM cfd_folios WHERE idEmpresa = ")).append(empresa).append(" AND ").append("serie = '").append(serie).append("'").toString();
            rs = cnx.consulta(sql, true);
        }
        if(rs.next())
        {
            anio_aprob = rs.getLong("aaprob");
            naprovacion = rs.getInt("noaprovacion");
            return rs.getString("idfolios");
        } else
        {
            anio_aprob = 1900L;
            return "0";
        }
    }

    public void GetHijosAddenda(Element adde, String comp, Long padre, Long plantilla, Long folio, String serie, Long idempresa, 
            Boolean vacio, Long idval)
        throws SQLException
    {
        long values = 0L;
        sql = (new StringBuilder("SELECT cfd_camade.nombre, CFD_VALADE.IDVAL, CFD_VALADE.IDVALPADRE, cfd_camade.orden,  cfd_camade.formato,  cfd_camade.plantilla,  cfd_camade.idcampo,  cfd_camade.company,  cfd_camade.IDFORMATO,  cfd_camade.IDPADRE,  cfd_camade.IDATRIBUTO,  cfd_dataformat.formato as form,  cfd_dataformat.mascara,  cfd_dataformat.longitud  FROM cfd_camade left join cfd_dataformat on cfd_camade.idformato = cfd_dataformat.idformat  RIGHT join CFD_VALADE ON CFD_VALADE.IDCAMPO = CFD_CAMADE.IDCAMPO  WHERE idpadre = ")).append(padre).append(" AND  IDVALPADRE = ").append(idval).append(" AND CFD_VALADE.FOLIO = ").append(folio).append(" AND CFD_VALADE.SERIE = '").append(serie).append("'").append(" order by cfd_camade.orden ").toString();
        ResultSet rs_hijos = cnx.consulta(sql, true);
        do
        {
            if(!rs_hijos.next())
                break;
            sql2 = (new StringBuilder("SELECT CFD_VALADE.IDVAL, CFD_CAMADE.NOMBRE, CFD_CAMADE.FORMATO, CFD_CAMADE.COMPANY, CFD_CAMADE.REQUERIDO, CFD_VALADE.IDCAMPO, CFD_VALADE.FOLIO, CFD_VALADE.SERIE, CFD_VALADE.VAL, CFD_VALADE.CVEART, CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append(" CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append(" CFD_VALADE.IDVALPADRE = ").append(rs_hijos.getInt("IDVALPADRE")).append(" AND ").append(" CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append(" CFD_CAMADE.IDCAMPO = ").append(rs_hijos.getInt("idcampo")).append(" ORDER BY CFD_CAMADE.IDCAMPO").toString();
            ResultSet rs_hijos2 = cnx.consulta(sql2, true);
            if(rs_hijos2.next())
            {
                Element Hijos = null;
                if(Remplazar(rs_hijos2.getString("VAL").trim()) == null || Remplazar(rs_hijos2.getString("VAL").trim()).equals(""))
                {
                    values = 0L;
                    if(rs_hijos2.getInt("REQUERIDO") == 0)
                        if(values > 0L)
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            Hijos.addContent("");
                            adde.addContent(Hijos);
                            if(idval.longValue() > 0L)
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        } else
                        if(!vacio.booleanValue())
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            Hijos.addContent("");
                            adde.addContent(Hijos);
                            if(idval.longValue() > 0L)
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        }
                    if(rs_hijos2.getInt("REQUERIDO") == 1)
                        if(values > 0L)
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            Hijos.addContent(Remplazar(rs_hijos2.getString("VAL").trim()));
                            adde.addContent(Hijos);
                            if(idval.longValue() > 0L)
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        } else
                        if(!vacio.booleanValue())
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            Hijos.addContent(Remplazar(rs_hijos2.getString("VAL").trim()));
                            adde.addContent(Hijos);
                            if(idval.longValue() > 0L)
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        }
                } else
                {
                    System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                    if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                    {
                        Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                        Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                    } else
                    {
                        Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                    }
                    Hijos.addContent(Remplazar(rs_hijos2.getString("VAL").trim()));
                    adde.addContent(Hijos);
                    if(idval.longValue() > 0L)
                    {
                        GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                        GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                    } else
                    {
                        GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                        GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                    }
                }
            }
        } while(true);
    }

    public void GetHijosAddenda(Element adde, String comp, Long padre, Long plantilla, Long folio, String serie, Long idempresa, 
            Boolean vacio)
        throws SQLException
    {
        long values = 0L;
        sql = (new StringBuilder("SELECT cfd_camade.nombre, CFD_VALADE.IDVAL, CFD_VALADE.IDVALPADRE, cfd_camade.orden,  cfd_camade.formato,  cfd_camade.plantilla,  cfd_camade.idcampo,  cfd_camade.company,  cfd_camade.IDFORMATO,  cfd_camade.IDPADRE,  cfd_camade.IDATRIBUTO,  cfd_dataformat.formato as form,  cfd_dataformat.mascara,  cfd_dataformat.longitud  FROM cfd_camade left join cfd_dataformat on cfd_camade.idformato = cfd_dataformat.idformat  RIGHT join CFD_VALADE ON CFD_VALADE.IDCAMPO = CFD_CAMADE.IDCAMPO  WHERE idpadre = ")).append(padre).append("AND CFD_VALADE.FOLIO = ").append(folio).append(" AND CFD_VALADE.SERIE = '").append(serie).append("'").append(" order by cfd_camade.orden ").toString();
        ResultSet rs_hijos = cnx.consulta(sql, true);
        do
        {
            if(!rs_hijos.next())
                break;
            sql2 = (new StringBuilder("SELECT CFD_VALADE.IDVAL, CFD_CAMADE.NOMBRE, CFD_CAMADE.FORMATO, CFD_CAMADE.COMPANY, CFD_CAMADE.REQUERIDO, CFD_VALADE.IDCAMPO, CFD_VALADE.FOLIO, CFD_VALADE.SERIE, CFD_VALADE.VAL, CFD_VALADE.CVEART, CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append(" CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append(" CFD_VALADE.IDVALPADRE = ").append(rs_hijos.getInt("IDVALPADRE")).append(" AND ").append(" CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append(" CFD_CAMADE.IDCAMPO = ").append(rs_hijos.getInt("idcampo")).append(" ORDER BY CFD_CAMADE.IDCAMPO").toString();
            ResultSet rs_hijos2 = cnx.consulta(sql2, true);
            if(rs_hijos2.next())
            {
                Element Hijos = null;
                if(Remplazar(rs_hijos2.getString("VAL").trim()) == null || Remplazar(rs_hijos2.getString("VAL").trim()).equals(""))
                {
                    values = 0L;
                    if(rs_hijos2.getInt("REQUERIDO") == 0)
                        if(values > 0L)
                        {
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("lineItem"))
                                System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            else
                                System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            Hijos.addContent("");
                            adde.addContent(Hijos);
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("lineItem"))
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        } else
                        if(!vacio.booleanValue())
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            Hijos.addContent("");
                            adde.addContent(Hijos);
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("lineItem"))
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        }
                    if(rs_hijos2.getInt("REQUERIDO") == 1)
                        if(values > 0L)
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("Cadena"))
                            {
                                CadenaOriginalv3 cad = new CadenaOriginalv3();
                                try
                                {
                                    cad.generarCadenaOriginal(comprobante);
                                }
                                catch(GenerarCadenaOriginalException e)
                                {
                                    e.printStackTrace();
                                }
                                try
                                {
                                    Hijos.addContent(cad.getCadenaOriginalUTF8());
                                }
                                catch(UnsupportedEncodingException e)
                                {
                                    e.printStackTrace();
                                }
                                adde.addContent(Hijos);
                            } else
                            {
                                Hijos.addContent(Remplazar(rs_hijos2.getString("VAL").trim()));
                                adde.addContent(Hijos);
                            }
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("lineItem"))
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        } else
                        if(!vacio.booleanValue())
                        {
                            System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                            if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                                Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                            } else
                            {
                                Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                            }
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("Cadena"))
                            {
                                CadenaOriginalv3 cad = new CadenaOriginalv3();
                                try
                                {
                                    cad.generarCadenaOriginal(comprobante);
                                }
                                catch(GenerarCadenaOriginalException e)
                                {
                                    e.printStackTrace();
                                }
                                try
                                {
                                    Hijos.addContent(cad.getCadenaOriginalUTF8());
                                }
                                catch(UnsupportedEncodingException e)
                                {
                                    e.printStackTrace();
                                }
                                adde.addContent(Hijos);
                            } else
                            {
                                Hijos.addContent(Remplazar(rs_hijos2.getString("VAL").trim()));
                                adde.addContent(Hijos);
                            }
                            if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("lineItem"))
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                            } else
                            {
                                GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                                GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                            }
                        }
                } else
                {
                    System.out.println((new StringBuilder("IDC:")).append(rs_hijos.getLong("idcampo")).append(" ").append("IDP:").append(rs_hijos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_hijos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_hijos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_hijos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_hijos2.getString("NOMBRE")).toString());
                    if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                    {
                        Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                        Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                    } else
                    {
                        Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                    }
                    if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("Cadena"))
                    {
                        CadenaOriginalv3 cad = new CadenaOriginalv3();
                        try
                        {
                            cad.generarCadenaOriginal(comprobante);
                        }
                        catch(GenerarCadenaOriginalException e)
                        {
                            e.printStackTrace();
                        }
                        try
                        {
                            Hijos.addContent(cad.getCadenaOriginalUTF8());
                        }
                        catch(UnsupportedEncodingException e)
                        {
                            e.printStackTrace();
                        }
                        adde.addContent(Hijos);
                    } else
                    {
                        Hijos.addContent(Remplazar(rs_hijos2.getString("VAL").trim()));
                        adde.addContent(Hijos);
                    }
                    if(Remplazar(rs_hijos2.getString("NOMBRE")).equals("lineItem"))
                    {
                        GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                        GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio, Long.valueOf(rs_hijos.getLong("IDVAL")));
                    } else
                    {
                        GetAtributosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                        GetHijosAddenda(Hijos, comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa, vacio);
                    }
                }
            }
        } while(true);
    }

    public void GetAtributosAddenda(Element atri, String comp, Long atributo, Long plantilla, Long folio, String serie, Long idempresa, 
            Boolean vacio, Long idval)
        throws SQLException
    {
        sql = (new StringBuilder("SELECT cfd_camade.nombre, CFD_VALADE.IDVAL, CFD_VALADE.IDVALPADRE, cfd_camade.orden,  cfd_camade.formato,  cfd_camade.plantilla,  cfd_camade.idcampo,  cfd_camade.company,  cfd_camade.IDFORMATO,  cfd_camade.IDPADRE,  cfd_camade.IDATRIBUTO,  cfd_dataformat.formato as form,  cfd_dataformat.mascara,  cfd_dataformat.longitud  FROM cfd_camade left join cfd_dataformat on cfd_camade.idformato = cfd_dataformat.idformat  RIGHT join CFD_VALADE ON CFD_VALADE.IDCAMPO = CFD_CAMADE.IDCAMPO  WHERE  idatributo = ")).append(atributo).append(" AND IDVALPADRE =").append(idval).append("AND CFD_VALADE.FOLIO = ").append(folio).append(" AND CFD_VALADE.SERIE = '").append(serie).append("'").append(" order by cfd_camade.orden ").toString();
        ResultSet rs_atributos = cnx.consulta(sql, true);
        do
        {
            if(!rs_atributos.next())
                break;
            sql2 = (new StringBuilder("SELECT CFD_VALADE.IDVAL, CFD_CAMADE.NOMBRE, CFD_CAMADE.FORMATO, CFD_CAMADE.COMPANY, CFD_CAMADE.REQUERIDO, CFD_VALADE.IDCAMPO, CFD_VALADE.FOLIO, CFD_VALADE.SERIE, CFD_VALADE.VAL, CFD_VALADE.CVEART, CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append(" CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append(" CFD_VALADE.IDVALPADRE = ").append(rs_atributos.getInt("IDVALPADRE")).append(" AND ").append(" CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append(" CFD_CAMADE.IDCAMPO = ").append(rs_atributos.getInt("idcampo")).append(" ORDER BY CFD_CAMADE.IDCAMPO").toString();
            ResultSet rs_atributos2 = cnx.consulta(sql2, true);
            if(rs_atributos2.next())
            {
                System.out.println((new StringBuilder("IDC:")).append(rs_atributos.getLong("idcampo")).append(" ").append("IDP:").append(rs_atributos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_atributos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_atributos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_atributos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_atributos2.getString("NOMBRE")).toString());
                if(Remplazar(rs_atributos2.getString("VAL").trim()) == null || Remplazar(rs_atributos2.getString("VAL").trim()).equals(""))
                {
                    if(rs_atributos2.getInt("REQUERIDO") == 1)
                        atri.setAttribute(Remplazar(rs_atributos2.getString("NOMBRE")), "");
                } else
                {
                    atri.setAttribute(Remplazar(rs_atributos2.getString("NOMBRE")), (new StringBuilder()).append(Remplazar(rs_atributos2.getString("VAL").trim())).toString());
                }
            }
        } while(true);
    }

    public void GetAtributosAddenda(Element atri, String comp, Long atributo, Long plantilla, Long folio, String serie, Long idempresa, 
            Boolean vacio)
        throws SQLException
    {
        sql = (new StringBuilder("SELECT cfd_camade.nombre, CFD_VALADE.IDVAL, CFD_VALADE.IDVALPADRE, cfd_camade.orden,  cfd_camade.formato,  cfd_camade.plantilla,  cfd_camade.idcampo,  cfd_camade.company,  cfd_camade.IDFORMATO,  cfd_camade.IDPADRE,  cfd_camade.IDATRIBUTO,  cfd_dataformat.formato as form,  cfd_dataformat.mascara,  cfd_dataformat.longitud  FROM cfd_camade left join cfd_dataformat on cfd_camade.idformato = cfd_dataformat.idformat  RIGHT join CFD_VALADE ON CFD_VALADE.IDCAMPO = CFD_CAMADE.IDCAMPO  WHERE  idatributo = ")).append(atributo).append(" AND CFD_VALADE.FOLIO = ").append(folio).append(" AND CFD_VALADE.SERIE = '").append(serie).append("'").append(" order by cfd_camade.orden ").toString();
        ResultSet rs_atributos = cnx.consulta(sql, true);
        do
        {
            if(!rs_atributos.next())
                break;
            sql2 = (new StringBuilder("SELECT CFD_VALADE.IDVAL, CFD_CAMADE.NOMBRE, CFD_CAMADE.FORMATO, CFD_CAMADE.COMPANY, CFD_CAMADE.REQUERIDO, CFD_VALADE.IDCAMPO, CFD_VALADE.FOLIO, CFD_VALADE.SERIE, CFD_VALADE.VAL, CFD_VALADE.CVEART, CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append(" CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append(" CFD_VALADE.IDVALPADRE = ").append(rs_atributos.getInt("IDVALPADRE")).append(" AND ").append(" CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append(" CFD_CAMADE.IDCAMPO = ").append(rs_atributos.getInt("idcampo")).append(" ORDER BY CFD_CAMADE.IDCAMPO").toString();
            ResultSet rs_atributos2 = cnx.consulta(sql2, true);
            if(rs_atributos2.next())
            {
                System.out.println((new StringBuilder("IDC:")).append(rs_atributos.getLong("idcampo")).append(" ").append("IDP:").append(rs_atributos.getLong("IDPADRE")).append(" ").append("IDA:").append(rs_atributos.getLong("IDATRIBUTO")).append(" ").append("IDVAL:").append(rs_atributos.getLong("IDVAL")).append(" ").append("IDVALPADRE:").append(rs_atributos.getLong("IDVALPADRE")).append(" NOMBRE: ").append(rs_atributos2.getString("NOMBRE")).toString());
                if(Remplazar(rs_atributos2.getString("VAL").trim()) == null || Remplazar(rs_atributos2.getString("VAL").trim()).equals(""))
                {
                    if(rs_atributos2.getInt("REQUERIDO") == 1)
                        atri.setAttribute(Remplazar(rs_atributos2.getString("NOMBRE")), "");
                } else
                {
                    atri.setAttribute(Remplazar(rs_atributos2.getString("NOMBRE")), (new StringBuilder()).append(Remplazar(rs_atributos2.getString("VAL").trim())).toString());
                }
            }
        } while(true);
    }

    public long GetValHijosAddenda(String comp, Long padre, Long plantilla, Long folio, String serie, Long idempresa)
        throws SQLException
    {
        long values = 0L;
        sql = (new StringBuilder("SELECT cfd_camade.nombre, CFD_VALADE.IDVAL, CFD_VALADE.IDVALPADRE, cfd_camade.orden,  cfd_camade.formato,  cfd_camade.plantilla,  cfd_camade.idcampo,  cfd_camade.company,  cfd_camade.IDFORMATO,  cfd_camade.IDPADRE,  cfd_camade.IDATRIBUTO,  cfd_dataformat.formato as form,  cfd_dataformat.mascara,  cfd_dataformat.longitud  FROM cfd_camade left join cfd_dataformat on cfd_camade.idformato = cfd_dataformat.idformat  RIGHT join CFD_VALADE ON CFD_VALADE.IDCAMPO = CFD_CAMADE.IDCAMPO  WHERE idpadre = ")).append(padre).append("AND CFD_VALADE.FOLIO = ").append(folio).append(" AND CFD_VALADE.SERIE = '").append(serie).append("'").append(" order by cfd_camade.orden ").toString();
        ResultSet rs_hijos = cnx.consulta(sql, true);
        do
        {
            if(!rs_hijos.next())
                break;
            sql2 = (new StringBuilder("SELECT CFD_VALADE.IDVAL, CFD_CAMADE.NOMBRE, CFD_CAMADE.FORMATO, CFD_CAMADE.COMPANY, CFD_CAMADE.REQUERIDO, CFD_VALADE.IDCAMPO, CFD_VALADE.FOLIO, CFD_VALADE.SERIE, CFD_VALADE.VAL, CFD_VALADE.CVEART, CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append(" CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append(" CFD_VALADE.IDVALPADRE = ").append(rs_hijos.getInt("IDVALPADRE")).append(" AND ").append(" CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append(" CFD_CAMADE.IDCAMPO = ").append(rs_hijos.getInt("idcampo")).append(" ORDER BY CFD_CAMADE.IDCAMPO").toString();
            ResultSet rs_hijos2 = cnx.consulta(sql2, true);
            if(rs_hijos2.next())
            {
                Element Hijos = null;
                if(rs_hijos2.getString("NOMBRE").indexOf(":") > 0)
                {
                    Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE").substring(rs_hijos2.getString("NOMBRE").indexOf(":") + 1, rs_hijos2.getString("NOMBRE").length())));
                    Hijos.setNamespace(Namespace.getNamespace("detallista", "http://www.sat.gob.mx/detallista"));
                } else
                {
                    Hijos = new Element(Remplazar(rs_hijos2.getString("NOMBRE")));
                }
                if(Remplazar(rs_hijos2.getString("VAL").trim()) != null && !Remplazar(rs_hijos2.getString("VAL").trim()).equals(""))
                    values++;
                values += GetValAtributosAddenda(comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa);
                values += GetValHijosAddenda(comp, Long.valueOf(rs_hijos.getLong("idcampo")), plantilla, folio, serie.trim(), idempresa);
            }
        } while(true);
        return values;
    }

    public long GetValAtributosAddenda(String comp, Long atributo, Long plantilla, Long folio, String serie, Long idempresa)
        throws SQLException
    {
        long value = 0L;
        sql = (new StringBuilder("SELECT cfd_camade.nombre, CFD_VALADE.IDVAL, CFD_VALADE.IDVALPADRE, cfd_camade.orden,  cfd_camade.formato,  cfd_camade.plantilla,  cfd_camade.idcampo,  cfd_camade.company,  cfd_camade.IDFORMATO,  cfd_camade.IDPADRE,  cfd_camade.IDATRIBUTO,  cfd_dataformat.formato as form,  cfd_dataformat.mascara,  cfd_dataformat.longitud  FROM cfd_camade left join cfd_dataformat on cfd_camade.idformato = cfd_dataformat.idformat  RIGHT join CFD_VALADE ON CFD_VALADE.IDCAMPO = CFD_CAMADE.IDCAMPO  WHERE  idatributo = ")).append(atributo).append(" AND CFD_VALADE.FOLIO = ").append(folio).append(" AND CFD_VALADE.SERIE = '").append(serie).append("'").append(" order by cfd_camade.orden ").toString();
        ResultSet rs_atributos = cnx.consulta(sql, true);
        do
        {
            if(!rs_atributos.next())
                break;
            sql2 = (new StringBuilder("SELECT CFD_VALADE.IDVAL, CFD_CAMADE.NOMBRE, CFD_CAMADE.FORMATO, CFD_CAMADE.COMPANY, CFD_CAMADE.REQUERIDO, CFD_VALADE.IDCAMPO, CFD_VALADE.FOLIO, CFD_VALADE.SERIE, CFD_VALADE.VAL, CFD_VALADE.CVEART, CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append(" CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append(" CFD_VALADE.IDVALPADRE = ").append(rs_atributos.getInt("IDVALPADRE")).append(" AND ").append(" CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append(" CFD_CAMADE.IDCAMPO = ").append(rs_atributos.getInt("idcampo")).append(" ORDER BY CFD_CAMADE.IDCAMPO").toString();
            ResultSet rs_atributos2 = cnx.consulta(sql2, true);
            if(rs_atributos2.next() && Remplazar(rs_atributos2.getString("VAL").trim()) != null && !Remplazar(rs_atributos2.getString("VAL").trim()).equals(""))
                value++;
        } while(true);
        return value;
    }

    FileInputStream f;
    FileOutputStream t;
    FirmasDigitales firmasdigitales;
    BufferedInputStream in_certificado;
    BufferedInputStream in_certificadp_comp;
    BufferedInputStream in_certificado64;
    BufferedInputStream in_llave;
    BufferedInputStream in_image;
    File certificado;
    File llave;
    File image;
    String rfc_emisor;
    String pass;
    String direccion;
    ConexionFirebird cnx;
    Connection con;
    Mensajes m;
    Comprobante comprobante;
    com.codinet.facture.cfdv2.Comprobante.Emisor emisor;
    com.codinet.facture.cfdv2.Comprobante.Receptor receptor;
    com.codinet.facture.cfdv2.Comprobante.Conceptos conceptos;
    com.codinet.facture.cfdv2.Comprobante.Impuestos impuestos;
    com.codinet.facture.cfdv2.Comprobante.Addenda adden;
    com.codinet.facture.cfdv2.Comprobante.Complemento comple;
    com.codinet.facture.cfdv2.Comprobante.Impuestos.Retenciones impuestosRetenciones;
    com.codinet.facture.cfdv2.Comprobante.Impuestos.Traslados impuestosTraslados;
    ObjectFactory ofactory;
    ProgressBarSample ps;
    String host;
    String bd;
    String us;
    String pss;
    String d;
    String sql;
    String sql2;
    String msg;
    String nombre;
    String rfc;
    String calle;
    String colonia;
    String cp;
    String interior;
    String exterior;
    String estados;
    String delegacion;
    String pais;
    String emi;
    String recep;
    String descripcion;
    String concep;
    String nocertificado;
    String sello;
    String version;
    String ncertificado;
    String timpuesto;
    String rimpuesto;
    String comprob;
    String nombre_empresa;
    String nombre_sucursal;
    String prefijo;
    String nemonico;
    String pagge;
    String type;
    String demo;
    Blob pubkey;
    Blob prikey;
    Blob im;
    Date fecha;
    double cantidad;
    double subtotal;
    double descuento;
    double total_impuestos;
    double importe;
    double valor_unitario;
    long idEntidadEmisor;
    long idEntidadReceptor;
    long domEmisor;
    long domReceptor;
    long anio;
    long anio_aprob;
    long naprovacion;
    long idEmpresa;
    int len;
    int pagos;
    int plazos;
    int idadd;
    int idcom;
    byte bytes[];
    long position;
    InputStream blobInputStream;
    BufferedInputStream in;
    BufferedOutputStream out;
    ResultSet rs;
    ResultSet rs2;
    ResultSet rs_company;
    ResultSet rs_val;
    Properties myProp;
}
