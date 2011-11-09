package comprobante;
import anyObject.CrearObjetoAmlAdenda;
import com.codinet.facture.cfd.Certificado;
import com.codinet.facture.cfd.SelloDigital;
import com.codinet.facture.cfdv2.*;
import com.codinet.facture.util.*;
import com.lowagie.text.DocumentException;
import create.*;
import cripfia.ext.FirmasDigitales;
import java.io.*;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import mx.com.codinet.err.ImpresionRefException;
import org.jdom.Element;

public class AppcfdComprobante implements Runnable {
	public AppcfdComprobante() {
		firmasdigitales = new FirmasDigitales();
		direccion = null;
		cnx = new ConexionFirebird();
		vf = null;
		m = new Mensaje();
		comprobante = null;
		emisor = null;
		receptor = null;
		conceptos = null;
		impuestos = null;
		adden = null;
		impuestosRetenciones = null;
		impuestosTraslados = null;
		ofactory = new ObjectFactory();
		fr = null;
		c64 = new CodificarBase64();
		cbd = new Comprobantebd();
		co = new CadenaOriginalv2();
		sign = new SelloDigital();
		imp = new ComprobanteImp();
		cer = new Certificado();
		ps = new ProgressBarSample();
		th = null;
		ncertificado = null;
		timpuesto = null;
		rimpuesto = null;
		comprob = null;
		nombre_empresa = null;
		nombre_sucursal = null;
		prefijo = null;
		pagge = null;
		subtotal = 0.0D;
		descuento = 0.0D;
		total_impuestos = 0.0D;
		idEntidadEmisor = 0L;
		idEntidadReceptor = 0L;
		anio = 0L;
		naprovacion = 0L;
		idEmpresa = 0L;
		len = 0;
		pagos = 0;
		plazos = 0;
		bytes = new byte[8096];
		blobInputStream = null;
		rs = null;
		rs2 = null;
		rs_company = null;
		rs_val = null;
	}

	public void conected() throws FileNotFoundException, SQLException,
		IOException {
		try {
			ConexionFirebird.conectarFirebird();
		}
		catch(ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public void disconect() {
		try {
			cnx.finalizar();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void setParams(long parm1, String parm2, String parm3, long parm4)
		throws Exception {
		fr = new JFrame();
		fr.setAlwaysOnTop(true);
		fr.setLocationRelativeTo(null);
		fr.setUndecorated(true);
		fr.setDefaultCloseOperation(3);
		JFrame.setDefaultLookAndFeelDecorated(true);
		fr.getRootPane().setWindowDecorationStyle(2);
		fr.setVisible(true);
		ps.CreaFrame("Generando documento");
		ps.CreaProgreso();
		ps.SetBorde("Generando Objeto Comprobante");
		ps.CreaBorde();
		idempresa = parm1;
		folio = parm2;
		serie = parm3;
		sucursal = parm4;
		vf = new ValidaFolio(Getidfolio(Long.toString(parm1), parm3), Integer.parseInt(parm2));
	}

	public String Getidfolio(String empresa, String serie)
		throws SQLException {
		if(serie.equals("_")) {
			sql = (new StringBuilder("SELECT * FROM cfd_folios WHERE idEmpresa = ")).append(empresa).append(" AND ").append("serie is null or serie = '' ").toString();
			rs = cnx.consulta(sql, true);
		} else {
			sql = (new StringBuilder("SELECT * FROM cfd_folios WHERE idEmpresa = ")).append(empresa).append(" AND ").append("serie = '").append(serie).append("'").toString();
			rs = cnx.consulta(sql, true);
		}
		if(rs.next())
			return Integer.toString(rs.getInt("idfolios"));
		else
			return "0";
	}

	public static Properties loadProperties(String fileName) {
		Properties tempProp = new Properties();
		try {
			InputStream propsFile = new FileInputStream(fileName);
			tempProp.load(propsFile);
			propsFile.close();
		}
		catch(IOException ioe) {
			System.out.println("I/O Exception.");
			ioe.printStackTrace();
			System.exit(0);
		}
		return tempProp;
	}

	public void reiniciaObjetos() {
		comprobante = new Comprobante();
		emisor = null;
		receptor = null;
		ofactory = null;
		conceptos = null;
		impuestos = null;
		impuestosTraslados = null;
		impuestosRetenciones = null;
		sellodigital = null;
		cadenaoriginal = null;
		System.gc();
		comprobante = new Comprobante();
		emisor = new com.codinet.facture.cfdv2.Comprobante.Emisor();
		receptor = new com.codinet.facture.cfdv2.Comprobante.Receptor();
		ofactory = new ObjectFactory();
		conceptos = new com.codinet.facture.cfdv2.Comprobante.Conceptos();
		sellodigital = new SelloDigital();
		cadenaoriginal = new CadenaOriginalv2();
		certificadoAsignado = false;
	}

	public void creaConfiguracion() throws SQLException, IOException {
		ps.SetBorde("Crea Configuracion");
		sql = (new StringBuilder("SELECT * FROM cfd_config  WHERE cfd_config.idEmpresa =")).append(idempresa).append(" AND ").append("cfd_config.idSucursal = ").append(sucursal).toString();
		rs = cnx.consultaBLOB(sql, true);
		try {
			if(rs.next()) {
				direccion = rs.getString("dirContenedor");
				pass = rs.getString("direccion").trim();
				pagge = rs.getString("pagina").trim();
				pubkey = rs.getBlob("dirPubKey");
				cert = new File("pukey.cer");
				in_certificado = new BufferedInputStream(pubkey.getBinaryStream());
				in_certificado64 = new BufferedInputStream(pubkey.getBinaryStream());
				prikey = rs.getBlob("dirPrivKey");
				llave = new File("pikey.cer");
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
					if(!firmasdigitales.certNumSerie(x509certificate).equals(rs.getString("seriecertificado"))) {
						m.Mensajes("Numero de serie incorrecto");
						System.out.println((new StringBuilder("Numero de serie incorrecto: ")).append(firmasdigitales.certNumSerie(x509certificate)).append(" vs ").append(rs.getString("seriecertificado")).toString());
					} else {
						System.out.println((new StringBuilder("Numero de serie correcto: ")).append(firmasdigitales.certNumSerie(x509certificate)).append(" vs ").append(rs.getString("seriecertificado")).toString());
					}
			}
		}
		catch(SQLException sqlexception) { }
	}

	public void creaEmisor() throws SQLException, Exception {
		ps.SetBorde("Crea Emisor");
		sql = (new StringBuilder("SELECT  emisor,receptor,idEmpresa,EXTRACT(YEAR FROM fecha) as anio,noAprobacion,noCertificado,sello,vers FROM cfd_comprobante  WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
		rs = cnx.consulta(sql, true);
		if(rs.next()) {
			idEntidadEmisor = rs.getLong("emisor");
			idEntidadReceptor = rs.getLong("receptor");
			idEmpresa = rs.getLong("idEmpresa");
			anio = rs.getInt("anio");
			naprovacion = rs.getInt("noAprobacion");
			ncertificado = rs.getString("noCertificado").trim();
			sello = rs.getString("sello");
			version = rs.getString("vers");
		}
		sql = (new StringBuilder("SELECT cnet_entidades.nombre,cnet_entidades.rfc,cnet_domicilios.calle,cnet_colonias.codigo_postal,cnet_colonias.descripcion as colonia,cnet_estados.descripcion as estado,cnet_delegacion.nombre as delegacion,cnet_domicilios.n_exterior,cnet_domicilios.n_interior,cnet_pais.nombre as nombrepais,cnet_estados.pais FROM cnet_colonias,cnet_delegacion,cnet_domicilios,cnet_entidades,cnet_pais,cnet_estados WHERE ( cnet_entidades.idEntidad = cnet_domicilios.idEntidad ) and( cnet_domicilios.idEstado = cnet_estados.idEstado ) and( cnet_domicilios.idColonia = cnet_colonias.idColonia ) and( cnet_colonias.idDelegacion = cnet_delegacion.idDelegacion ) and( cnet_estados.pais = cnet_pais.id ) and( ( cnet_entidades.idEntidad =")).append(idEntidadEmisor).append("))").toString();
		rs = cnx.consulta(sql, true);
		if(rs.next()) {
			cp = rs.getString("codigo_postal");
			interior = rs.getString("n_interior");
			exterior = rs.getString("n_exterior");
			System.out.println(rs.getString("calle"));
			System.out.println(rs.getString("delegacion"));
			System.out.println(rs.getString("estado"));
			System.out.println(rs.getString("nombrepais"));
			System.out.println(rfc_emisor);
			System.out.println(rs.getString("nombre"));
			rfc_emisor = rs.getString("rfc");
			emisor = ofactory.createComprobanteEmisor(new TUbicacionFiscal(rs.getString("calle"), rs.getString("delegacion"), rs.getString("estado"), rs.getString("nombrepais"), cp), rs.getString("nombre"), rfc_emisor);
			emisor.getDomicilioFiscal().setColonia(rs.getString("colonia"));
			emisor.getDomicilioFiscal().setNoExterior(exterior);
			if(!interior.equals("0"))
				emisor.getDomicilioFiscal().setNoInterior(interior);
		}
	}

	public void creaReceptor() throws SQLException, Exception {
		ps.SetBorde("Crea Receptor");
		sql = (new StringBuilder("SELECT cnet_entidades.nombre,cnet_entidades.rfc,cnet_domicilios.calle,cnet_colonias.codigo_postal,cnet_colonias.descripcion as colonia,cnet_estados.descripcion as estado,cnet_delegacion.nombre as delegacion,cnet_domicilios.n_exterior,cnet_domicilios.n_interior,cnet_pais.nombre as nombrepais,cnet_estados.pais FROM cnet_colonias,cnet_delegacion,cnet_domicilios,cnet_entidades,cnet_pais,cnet_estados WHERE ( cnet_entidades.idEntidad = cnet_domicilios.idEntidad ) and( cnet_domicilios.idEstado = cnet_estados.idEstado ) and( cnet_domicilios.idColonia = cnet_colonias.idColonia ) and( cnet_colonias.idDelegacion = cnet_delegacion.idDelegacion ) and( cnet_estados.pais = cnet_pais.id ) and( ( cnet_entidades.idEntidad =")).append(idEntidadReceptor).append("))").toString();
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
	}

	public void creaLugarExpedicion() throws Exception {
		ps.SetBorde("Crea Lugar de Expedicion");
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
		} else {
			System.out.println("No existe referencia de la sucursal");
		}
	}

	public void creaConceptos() throws Exception {
		ps.SetBorde("Crea Conceptos");
		sql = (new StringBuilder("SELECT cfd_conceptos.cantidad,cfd_conceptos.descripcion,cfd_conceptos.importe,cfd_conceptos.valorUnitario FROM cfd_conceptos WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
		rs = cnx.consulta(sql, true);
		conceptos = ofactory.createComprobanteConceptos();
		while(rs.next()) {
			conceptos.getConcepto().add(ofactory.createComprobanteConceptosConcepto(rs.getDouble("cantidad"), rs.getString("descripcion"), rs.getDouble("importe"), rs.getDouble("valorUnitario")));
			subtotal += rs.getDouble("importe");
		}
		sql = (new StringBuilder("SELECT IMPORTE_TOTAL FROM CFD_DESCUENTOS WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
		for(rs = cnx.consulta(sql, true); rs.next();)
			descuento += rs.getDouble("IMPORTE_TOTAL");

		subtotal -= descuento;
	}

	public void creaImpuestos() throws SQLException {
		ps.SetBorde("Crea Impuestos");
		impuestos = ofactory.createComprobanteImpuestos();
		impuestosTraslados = ofactory.createComprobanteImpuestosTraslados();
		impuestosRetenciones = ofactory.createComprobanteImpuestosRetenciones();
		sql = (new StringBuilder("SELECT sum(cfd_impuestos.importe) as importe,cnet_impuesto.descripcion,cfd_impuestos.idCNImpuesto,cfd_impuestos.idCatImpuesto FROM cfd_impuestos,cnet_impuesto WHERE ( cfd_impuestos.idCNImpuesto = cnet_impuesto.idCNImpuesto ) and( ( cfd_impuestos.folio = ")).append(folio).append(" ) AND").append("( cfd_impuestos.serie = '").append(serie).append("' ) AND").append("( cfd_impuestos.idEmpresa =").append(idempresa).append(") AND").append("(( cfd_impuestos.tipo = '+' )or( cfd_impuestos.tipo = 'T' ))) group by cnet_impuesto.descripcion,cfd_impuestos.idCNImpuesto,cfd_impuestos.idCatImpuesto").toString();
		rs = cnx.consulta(sql, true);
		do {
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
		sql = (new StringBuilder("SELECT sum(cfd_impuestos.importe)as importe,cnet_impuesto.descripcion FROM cfd_impuestos,cnet_impuesto WHERE ( cfd_impuestos.idCNImpuesto = cnet_impuesto.idCNImpuesto ) and( ( cfd_impuestos.folio = ")).append(folio).append(" ) AND").append("( cfd_impuestos.serie = '").append(serie).append("' ) AND").append("( cfd_impuestos.idEmpresa =").append(idempresa).append(") AND").append("(( cfd_impuestos.tipo = '-' )or( cfd_impuestos.tipo = 'R' ))) group by cnet_impuesto.descripcion,cfd_impuestos.idCNImpuesto,cfd_impuestos.idCatImpuesto").toString();
		rs = cnx.consulta(sql, true);
		do {
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
		sql = (new StringBuilder("SELECT sum(cfd_impuestos.importe) as importe  FROM cfd_impuestos,cnet_impuesto WHERE ( cfd_impuestos.idCNImpuesto = cnet_impuesto.idCNImpuesto ) and( ( cfd_impuestos.folio = ")).append(folio).append(" ) AND").append("( cfd_impuestos.serie = '").append(serie).append("' ) AND").append("( cfd_impuestos.idEmpresa =").append(idempresa).append("))").toString();
		rs = cnx.consulta(sql, true);
		if(rs.next())
			total_impuestos = rs.getDouble("importe");
	}

	public void creaObjetoComprobante() throws Exception {
		ps.SetBorde("Crea Objeto Comprobante");
		comprobante = ofactory.createComprobanteIngreso(emisor, receptor, conceptos, impuestos, folio, new Date(), "SELLO", naprovacion, anio, ncertificado, subtotal, subtotal + total_impuestos);
		comprobante.setDescuento(descuento);
		sql = (new StringBuilder("SELECT * FROM cfd_comprobante WHERE folio = ")).append(folio).append(" AND ").append("serie='").append(serie.trim()).append("' AND ").append("idEmpresa = ").append(idempresa).toString();
		rs = cnx.consulta(sql, true);
		if(rs.next()) {
			comprobante.setFecha(rs.getTimestamp("fecha"));
			Format formatter = new SimpleDateFormat("yyyyMMdd");
			String dt = formatter.format(rs.getTimestamp("fecha"));
			switch(rs.getInt("IDTDOCTO"))
			{
			case 1: // '\001'
				prefijo = (new StringBuilder("FACT_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
				break;

			case 2: // '\002'
				prefijo = (new StringBuilder("RHN_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
				break;

			case 3: // '\003'
				prefijo = (new StringBuilder("NCA_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
				break;

			case 4: // '\004'
				prefijo = (new StringBuilder("NCR_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
				break;

			default:
				prefijo = (new StringBuilder("XXX_")).append(folio).append("_").append(serie.trim()).append("_").append(dt).toString();
				break;
			}
			ps.f.setTitle(prefijo);
			if(rs.getString("f_pago") != null) {
				int longitud = rs.getString("f_pago").length();
				if(longitud > 3) {
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
		if(rs.next()) {
			nombre_empresa = rs.getString("nombre_empresa");
			nombre_sucursal = rs.getString("nombre_sucursal");
		}
	}

	public void crearAddenda() throws Exception {
		ps.SetBorde("Crea Addenda");
		String sql_company = "";
		String sql_val = "";
		sql = (new StringBuilder("SELECT CFD_VALADE.IDVAL,CFD_CAMADE.NOMBRE,CFD_CAMADE.FORMATO,CFD_CAMADE.COMPANY,CFD_VALADE.IDCAMPO,CFD_VALADE.FOLIO,CFD_VALADE.SERIE,CFD_VALADE.VAL,CFD_VALADE.CVEART,CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append("CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append("CFD_VALADE.IDEMPRESA = ").append(idempresa).toString();
		rs = cnx.consulta(sql, true);
		if(rs.next()) {
			sql_company = (new StringBuilder("SELECT cfd_camade.formato FROM cfd_camade WHERE cfd_camade.company = '")).append(rs.getString("company")).append("'").append("group by cfd_camade.formato ").toString();
			rs_company = cnx.consulta(sql_company, true);
			adden = ofactory.createComprobanteAddenda();
			Element adendaElement2 = new Element(rs.getString("company"));
			do {
				if(!rs_company.next())
					break;
				sql_val = (new StringBuilder("SELECT CFD_VALADE.IDVAL,CFD_CAMADE.NOMBRE,CFD_CAMADE.FORMATO,CFD_CAMADE.COMPANY,CFD_VALADE.IDCAMPO,CFD_VALADE.FOLIO,CFD_VALADE.SERIE,CFD_VALADE.VAL,CFD_VALADE.CVEART,CFD_VALADE.IDEMPRESA FROM CFD_VALADE LEFT JOIN CFD_CAMADE ON CFD_CAMADE.IDCAMPO = CFD_VALADE.IDCAMPO  WHERE CFD_VALADE.FOLIO =")).append(folio).append(" AND ").append("CFD_VALADE.SERIE ='").append(serie.trim()).append("' AND ").append("CFD_VALADE.CVEART = '-1' AND ").append("CFD_VALADE.IDEMPRESA = ").append(idempresa).append(" AND ").append("CFD_CAMADE.FORMATO = '").append(rs_company.getString("formato")).append("' ").append("ORDER BY CFD_CAMADE.IDCAMPO").toString();
				rs_val = cnx.consulta(sql_val, true);
				Element remision = new Element(rs_company.getString("formato"));
				Element contenido_r;
				for(; rs_val.next(); remision.addContent(contenido_r))
				{
					contenido_r = new Element(rs_val.getString("NOMBRE").replaceAll(" ", ""));
					contenido_r.addContent(rs_val.getString("VAL"));
				}

				if(rs_val.getRow() > 0)
					adendaElement2.addContent(remision);
			} while(true);
			adden.getAny().add((new CrearObjetoAmlAdenda()).construirAdenda(adendaElement2));
			comprobante.setAddenda(adden);
		}
	}

	public void creaComprobante() throws Exception {
		(new Thread(this)).start();
	}

	public void run() {
		try {
			vf.estableceConexion(cnx);
			imp.estableceConexion(cnx);
			cbd.estableceConexion(cnx);
			ps.SetBorde("Validado folios");
			if(vf.Validar()) {
				creaConfiguracion();
				creaEmisor();
				creaReceptor();
				creaLugarExpedicion();
				creaConceptos();
				creaImpuestos();
				creaObjetoComprobante();
				crearAddenda();
				if(comprobante != null)
					try {
						ps.SetBorde("Crea Cadena Original");
						co.generarCadenaOriginal(comprobante);
						System.out.println((new StringBuilder("Cadena original: ")).append(co.getCadenaOriginal()).toString());
						File fichero = new File((new StringBuilder(String.valueOf(direccion))).append("\\Mis Facturas\\").append(nombre_empresa).append("\\").append(nombre_sucursal).append("\\").toString());
						if(!fichero.exists())
						{
							fichero.mkdirs();
							direccion = (new StringBuilder(String.valueOf(direccion))).append("\\Mis Facturas\\").append(nombre_empresa).append("\\").append(nombre_sucursal).append("\\").toString();
						} else
						{
							direccion = (new StringBuilder(String.valueOf(direccion))).append("\\Mis Facturas\\").append(nombre_empresa).append("\\").append(nombre_sucursal).append("\\").toString();
						}
					}
					catch(GenerarCadenaOriginalException e) {
						e.printStackTrace();
						sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
						rs = cnx.consulta(sql, false);
						cnx.commit();
					}
				ps.SetBorde("Crea Sello Digital");
				sign.generarSello(in_certificado, in_llave, pass, rfc_emisor, co.getCadenaOriginal());
				try {
					comprobante.setCertificado(c64.base64Encode(in_certificado64));
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				if(sign != null && sign.getSello() != null && sign.getSello() != "") {
					comprobante.setSello(sign.getSello());
					System.out.println((new StringBuilder("Sello: ")).append(sign.getSello()).toString());
					try {
						ps.SetBorde("Crea XML");
						if(comprobante != null)
						{
							new MarshallCFDv2XML();
							MarshallCFDv2XML.marshalCfdV2(comprobante, new FileOutputStream((new StringBuilder(String.valueOf(direccion))).append(prefijo).append(".xml").toString()));
							System.out.println((new StringBuilder("Archivo ")).append(direccion).append(prefijo).append(".xml").append(" creado...").toString());
						}
					}
					catch(FileNotFoundException ex) {
						sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
						rs = cnx.consulta(sql, false);
						cnx.commit();
						JOptionPane.showMessageDialog(fr, (new StringBuilder("No se pudo generar XML de factura: ")).append(prefijo).toString(), "Aviso", 1);
						ps.CerrarProgreso();
						ex.printStackTrace();
					}
					catch(Exception ex) {
						JOptionPane.showMessageDialog(fr, (new StringBuilder("No se pudo generar XML de factura: ")).append(prefijo).toString(), "Aviso", 1);
						ps.CerrarProgreso();
						ex.printStackTrace();
					}
					try {
						cbd.insertaCFD((new StringBuilder(String.valueOf(direccion))).append(prefijo).append(".xml").toString(), Integer.parseInt(Long.toString(idempresa)), "I", Integer.parseInt(Long.toString(sucursal)));
						try {
							ps.SetBorde("Crea PDF");
							imp.setNombre((new StringBuilder(String.valueOf(direccion))).append(prefijo).append(".pdf").toString(), image.getAbsolutePath(), pagge);
							imp.construyeComprobante(folio, serie, co.getCadenaOriginal(), idempresa, sucursal, 1L);
							System.out.println((new StringBuilder("Archivo ")).append(direccion).append(prefijo).append(".pdf").append(" creado...").toString());
						}
						catch(DocumentException e) {
							sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
							rs = cnx.consulta(sql, false);
							cnx.commit();
							JOptionPane.showMessageDialog(fr, (new StringBuilder("No se pudo generar PDF de factura: ")).append(prefijo).toString(), "Aviso", 1);
							ps.CerrarProgreso();
							e.printStackTrace();
						}
						catch(ImpresionRefException e) {
							sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
							rs = cnx.consulta(sql, false);
							cnx.commit();
							JOptionPane.showMessageDialog(fr, (new StringBuilder("No se pudo generar PDF de factura: ")).append(prefijo).toString(), "Aviso", 1);
							ps.CerrarProgreso();
							e.printStackTrace();
						}
						catch(Exception e) {
							JOptionPane.showMessageDialog(fr, (new StringBuilder("No se pudo generar PDF de factura: ")).append(prefijo).toString(), "Aviso", 1);
							ps.CerrarProgreso();
							e.printStackTrace();
						}
						if((new File((new StringBuilder(String.valueOf(direccion))).append(prefijo).append(".pdf").toString())).exists() && (new File((new StringBuilder(String.valueOf(direccion))).append(prefijo).append(".xml").toString())).exists()) {
							ps.SetBorde("Se concluye proseso");
							sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'V' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
							rs = cnx.consulta(sql, false);
							cnx.commit();
							JOptionPane.showMessageDialog(fr, (new StringBuilder("Se ha generado su factura: ")).append(prefijo).toString(), "Aviso", 1);
							ps.CerrarProgreso();
						} else {
							sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
							rs = cnx.consulta(sql, false);
							cnx.commit();
							JOptionPane.showMessageDialog(fr, "No fue posible generar su factura", "Aviso", 1);
							ps.CerrarProgreso();
						}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(fr, sign.getMsgErrorTxt(), "Aviso", 1);
					ps.CerrarProgreso();
				}
			} else {
				JOptionPane.showMessageDialog(fr, "El folio no es valido, no fue posible generar su factura", "Aviso", 1);
				ps.CerrarProgreso();
				sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
				rs = cnx.consulta(sql, false);
				cnx.commit();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			sql = (new StringBuilder("UPDATE cfd_comprobante set estado = 'X' WHERE folio = ")).append(folio).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idempresa).toString();
			try {
				rs = cnx.consulta(sql, false);
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
			try {
				cnx.commit();
			}
			catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		fr.setVisible(false);
		fr.dispose();
		System.gc();
	}

	FileInputStream f;
	FileOutputStream t;
	FirmasDigitales firmasdigitales;
	BufferedInputStream in_certificado;
	BufferedInputStream in_certificado64;
	BufferedInputStream in_llave;
	BufferedInputStream in_image;
	File cert;
	File llave;
	File image;
	String rfc_emisor;
	String pass;
	String direccion;
	ConexionFirebird cnx;
	ValidaFolio vf;
	Mensaje m;
	long idempresa;
	long sucursal;
	String folio;
	String serie;
	Comprobante comprobante;
	com.codinet.facture.cfdv2.Comprobante.Emisor emisor;
	com.codinet.facture.cfdv2.Comprobante.Receptor receptor;
	com.codinet.facture.cfdv2.Comprobante.Conceptos conceptos;
	com.codinet.facture.cfdv2.Comprobante.Impuestos impuestos;
	com.codinet.facture.cfdv2.Comprobante.Addenda adden;
	com.codinet.facture.cfdv2.Comprobante.Impuestos.Retenciones impuestosRetenciones;
	com.codinet.facture.cfdv2.Comprobante.Impuestos.Traslados impuestosTraslados;
	ObjectFactory ofactory;
	SelloDigital sellodigital;
	CadenaOriginalv2 cadenaoriginal;
	JFrame fr;
	CodificarBase64 c64;
	Comprobantebd cbd;
	CadenaOriginalv2 co;
	SelloDigital sign;
	ComprobanteImp imp;
	Certificado cer;
	ProgressBarSample ps;
	Thread th;
	String host;
	String bd;
	String us;
	String pss;
	String d;
	String sql;
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
	String ncertificado;
	String sello;
	String version;
	String timpuesto;
	String rimpuesto;
	String comprob;
	String nombre_empresa;
	String nombre_sucursal;
	String prefijo;
	String pagge;
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
	long anio;
	long naprovacion;
	long idEmpresa;
	int len;
	int pagos;
	int plazos;
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
	String cad_original;
	String sello_generado;
	String pwdLlave;
	String certificado;
	String xmlRutaDestino;
	String com[];
	InputStream isLlavePrivada;
	InputStream isLlavePublica;
	boolean certificadoAsignado;
}
