package create;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.*;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import mx.com.codinet.CoversorLetras;
import mx.com.codinet.err.ImpresionRefException;

// Referenced classes of package create:
//            Mensajes, ConexionFirebird

public class ComprobanteImp {
	Document doc;
    PdfWriter writer;
    Image image;
    ConexionFirebird db;
    CoversorLetras cov;
    int numCadenas;
    int px;
    int py;
    int px1;
    int py1;
    String archivo;
    String pic;
    String sql;
    String tpagina;
    Mensajes m;
    ResultSet rsCoordenadas;
    ResultSet rsConceptos;
    ResultSet rsImpuestos;
    ResultSet rsDescuentos;
    ResultSet rs;

    public ComprobanteImp() {
        m = new Mensajes();
        rsCoordenadas = null;
        rsConceptos = null;
        rsImpuestos = null;
        rsDescuentos = null;
        rs = null;
    }

    public void setNombre(String dir, String fondo, String Page)
        throws DocumentException, MalformedURLException, IOException {
        tpagina = Page;
        if(Page.equals("LT")) {
            doc = new Document();
        } else {
            doc = new Document(PageSize.HALFLETTER.rotate(), 55F, 55F, 5F, 5F);
        }
        archivo = dir;
        try {
            writer = PdfWriter.getInstance(doc, new FileOutputStream(archivo));
        } catch(IOException e) {
            m.GetMensaje("El archivo de su factura se encuentra abierto, cierre " +
            		"el archivo");
        }
        pic = fondo;
        image = Image.getInstance(pic);
        image.setAlignment(8);
        if(Page.equals("LT")) {
            image.scaleAbsolute(500F, 750F);
        } else {
            image.scaleAbsolute(500F, 375F);
        }
    }

    String FormateaFolio(String folio) {
        int ln = folio.length();
        for(int i = ln; i < 5; i++)
            folio = (new StringBuilder()).append("0").append(folio).toString();
        return folio;
    }

    public String construyeComprobante(String folio, String serie,
    								   String cadOriginal, long idempresa,
    								   long sucursal, long design)
        throws Exception {
        double cantidad = 0.0D;
        double importe = 0.0D;
        double valorUnitario = 0.0D;
        double decimales = 0.0D;
        double totalImpuestos = 0.0D;
        double retencion_iva = 0.0D;
        double retencion_isr = 0.0D;
        double traslados_iva = 0.0D;
        double traslados_ieps = 0.0D;
        double traslados_ish = 0.0D;
        double subtotal = 0.0D;
        double subtotal_honorarios = 0.0D;
        double total = 0.0D;
        double dpag = 0.0D;
        int noAprobacion = 0;
        int anioaprob = 0;
        double subtotal_sin_descuentos = 0.0D;
        int dom_emisor = 0;
        int dom_receptor = 0;
        int nconceptos = 0;
        int reg = 1;
        int regi = 1;
        int regd = 1;
        int reg_cadena = 0;
        int num_art = 0;
        int total_articulos = 0;
        int total_impuestos = 0;
        int total_descuentos = 0;
        int pag = 1;
        int epag = 0;
        int npag = 0;
        String p[] = new String[2];
        String fecha = "";
        String noCertificado = "";
        String sello = "";
        String receptor = "";
        String rfc = "";
        String calle = "";
        String codigoPostal = "";
        String colonia = "";
        String estado = "";
        String municipio = "";
        String noExterior = "";
        String noInterior = "";
        String pais = "";
        String descripcion = "";
        String clave = "";
        String nombre = "";
        String telefono = "";
        String email = "";
        String observaciones = "";
        String tipo_docto = "";
        String importeLetra = "";
        String moneda = "";
        String domicilio = "";
        String scalle = "";
        String scodigoPostal = "";
        String scolonia = "";
        String sestado = "";
        String smunicipio = "";
        String snoExterior = "";
        String snoInterior = "";
        String spais = "";
        String sname = "";
        String stipo = "";
        String srfc = "";
        int tamCad = 0;
        int tamArt = 0;
        int idEstado = 0;
        int idColonia = 0;
        int idEntidad = 0;
        int idDelegacion = 0;
        int idPais = 0;
        int s = 0;
        int posicionesx[] = new int[5];
        DecimalFormat dec = null;
        String zeros = "0000000000";
        sql = (new StringBuilder("SELECT DECIMALES FROM CFD_CONFIG WHERE " +
        		"IDEMPRESA = ")).append(idempresa).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            zeros = (new StringBuilder("#,##0.")).append(
            		zeros.substring(0, rs.getInt("DECIMALES"))).toString();
            dec = new DecimalFormat(zeros);
        } else {
            dec = new DecimalFormat("#,##0.00");
        }
        cov = new CoversorLetras();
        doc.open();
        doc.add(image);
        sql = (new StringBuilder(
        		"SELECT * FROM cfd_comprobante WHERE folio = ")
        	).append(folio).append(" AND ").append("serie='")
        	.append(serie.trim()).append("' AND ").append("idEmpresa = ")
        	.append(idempresa).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            fecha = formatter.format(rs.getTimestamp("fecha"));
            noAprobacion = rs.getInt("noAprobacion");
            noCertificado = rs.getString("noCertificado");
            sello = rs.getString("sello");
            dom_emisor = rs.getInt("dom_emisor");
            receptor = rs.getString("receptor");
            dom_receptor = rs.getInt("dom_receptor");
            observaciones = rs.getString("observacion");
            moneda = rs.getString("moneda");
            anioaprob = rs.getInt("anioaprob");
            switch(rs.getInt("IDTDOCTO")) {
            case 1:
                tipo_docto = "FACTURA";
                break;
            case 2:
                tipo_docto = "RECIBO DE HONORARIOS";
                break;
            case 3:
                tipo_docto = "NOTA DE CARGO";
                break;
            case 4:
                tipo_docto = "NOTA DE CREDITO";
                break;
            case 5:
                tipo_docto = "RECIBO DE PAGO";
                break;
            case 6:
                tipo_docto = "RECIBO DE ARRENDAMIENTO";
                break;
            default:
                tipo_docto = "XXX";
                break;
            }
        } else {
            throw new ImpresionRefException((new StringBuilder("No se " +
            		"encontro el registro comprobante Folio:")).append(folio)
            		.append(serie).toString());
        }
        sql = (new StringBuilder("SELECT cnet_entidades.nombre," +
        		"cnet_entidades.rfc,cnet_estados.descripcion as estado," +
        		"cnet_pais.nombre as pais,cnet_colonias.descripcion as " +
        		"colonia,cnet_colonias.codigo_postal,cnet_delegacion.nombre " +
        		"as delegacion,cnet_domicilios.calle,cnet_sucursal.tipo," +
        		"cnet_domicilios.n_exterior,cnet_domicilios.n_interior " +
        		"FROM cnet_entidades,cnet_sucursal,cnet_domicilios," +
        		"cnet_colonias,cnet_pais,cnet_delegacion,cnet_estados " +
        		"WHERE ( cnet_sucursal.identidad = cnet_entidades.idEntidad" +
        		" ) and( cnet_domicilios.identidad  = cnet_sucursal.identidad" +
        		" ) and( cnet_domicilios.idDelegacion = " +
        		"cnet_delegacion.idDelegacion ) and( cnet_domicilios.idEstado" +
        		" = cnet_estados.idEstado ) and( cnet_pais.id = " +
        		"cnet_estados.pais ) and( cnet_domicilios.idColonia = " +
        		"cnet_colonias.idColonia ) and( ( cnet_sucursal.idsucursal" +
        		" = ")).append(sucursal).append(") )").toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            scalle = rs.getString("calle");
            srfc = rs.getString("rfc");
            scodigoPostal = rs.getString("codigo_postal");
            scolonia = rs.getString("colonia");
            sestado = rs.getString("estado");
            smunicipio = rs.getString("delegacion");
            snoExterior = rs.getString("n_exterior");
            snoInterior = rs.getString("n_interior");
            spais = rs.getString("pais");
            sname = rs.getString("nombre");
            stipo = rs.getString("tipo");
        } else {
            System.out.println((new StringBuilder("No se encontro el " +
            		"registro de la sucursal:")).append(sucursal).toString());
        }
        sql = (new StringBuilder("SELECT * FROM cnet_domicilios WHERE " +
        		"idDomicilio = ")).append(dom_receptor).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            idEstado = rs.getInt("idEstado");
            idColonia = rs.getInt("idColonia");
            idEntidad = rs.getInt("idEntidad");
            idDelegacion = rs.getInt("idDelegacion");
            calle = rs.getString("calle");
            noInterior = rs.getString("n_interior");
            noExterior = rs.getString("n_exterior");
        } else {
            throw new ImpresionRefException((new StringBuilder("No se " +
            		"encontro el registro idDomicilio:")).append(dom_receptor)
            		.toString());
        }
        sql = (new StringBuilder("SELECT * FROM cnet_estados WHERE " +
        		"idEstado = ")).append(idEstado).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            estado = rs.getString("descripcion");
            idPais = rs.getInt("pais");
        } else {
            throw new ImpresionRefException((new StringBuilder("No se encontro" +
            		" el registro idEstado:")).append(idEstado).toString());
        }
        sql = (new StringBuilder("SELECT * FROM cnet_pais WHERE id = "))
        .append(idPais).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            pais = rs.getString("nombre");
        }
        else {
            throw new ImpresionRefException((new StringBuilder("No se encontro" +
            		" el pais idPais:")).append(idPais).toString());
        }
        sql = (new StringBuilder("SELECT codigo_postal, descripcion," +
        		" idDelegacion FROM cnet_colonias WHERE idColonia = "))
        		.append(idColonia).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            codigoPostal = rs.getString("codigo_postal");
            colonia = rs.getString("descripcion");
        } else {
            throw new ImpresionRefException((new StringBuilder("No se encontro" +
            		" el registro idColonia:")).append(idColonia).toString());
        }
        sql = (new StringBuilder("SELECT * FROM cnet_entidades WHERE" +
        		" idEntidad = ")).append(receptor).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            nombre = rs.getString("nombre");
            rfc = rs.getString("rfc");
            telefono = rs.getString("telefono");
        } else {
            throw new ImpresionRefException((new StringBuilder("No se " +
            		"encontro el registro idEntidad:")).append(idEntidad)
            		.toString());
        }
        sql = (new StringBuilder("SELECT * FROM cnet_cliente WHERE " +
        		"idEntidad = ")).append(receptor).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            email = rs.getString("email");
        }
        else {
            throw new ImpresionRefException((new StringBuilder("No se " +
            		"encontro el registro del receptro idEntidad:"))
            		.append(receptor).toString());
        }
        sql = (new StringBuilder("SELECT nombre FROM cnet_delegacion " +
        		"WHERE idDelegacion = ")).append(idDelegacion).toString();
        rs = db.consulta(sql, true);
        if(rs.next()) {
            municipio = rs.getString("nombre");
        } else {
            throw new ImpresionRefException((new StringBuilder("No se e" +
            		"ncontro el registro idDelegacion:"))
            		.append(idDelegacion).toString());
        }

        int countpag = 1;
        int count_des = 0;
        int count_art = 0;
        int num_lineas = 0;
        int t_articulos = 0;
        int ly = 0;

        estableceCoordenadas(serie, idempresa, "38", sucursal);
        if(px > 0) {
            num_art = px;
        } else {
            num_art = 10;
        }
        PdfContentByte cb = writer.getDirectContent();
        do {
            sql = (new StringBuilder("SELECT a.campo AS campo,b.x AS x,b.y AS" +
            		" y FROM cfd_campo AS a INNER JOIN cfd_impresion AS b ON" +
            		" a.idCampo = b.idImpresion WHERE  b.serie = '"))
            		.append(serie).append("' AND ").append("b.idEmpresa = ")
            		.append(idempresa).append(" AND b.idsucursal =  ")
            		.append(sucursal).append(" ORDER BY a.idCampo").toString();
            rsCoordenadas = db.consulta(sql, true);
            if(!rsCoordenadas.next()) {
                sql = (new StringBuilder("SELECT cfd_campo.idCampo, '"))
                .append(serie).append("' as serie,").append("cfd_impresion.x,")
                .append("cfd_impresion.y,").append("cfd_impresion.x1,")
                .append("cfd_impresion.y1,").append("cfd_impresion.visible,")
                .append("cfd_impresion.banda,")
                .append("cfd_impresion.idEmpresa ")
                .append("FROM {oj cfd_campo LEFT OUTER JOIN cfd_impresion ON " +
                		"cfd_campo.idCampo = cfd_impresion.idImpresion} ")
                		.append("WHERE  cfd_impresion.idEmpresa = ")
                		.append(idempresa).append(" AND cfd_impresion." +
                				"idsucursal = ").append(sucursal).toString();
                rs = db.consulta(sql, true);
                if(rs.next()) {
                    sql = (new StringBuilder("INSERT INTO cfd_impresion " +
                    		"SELECT cfd_campo.idCampo, '")).append(serie)
                    		.append("' as serie,").append("cfd_impresion.x,")
                    		.append("cfd_impresion.y,")
                    		.append("cfd_impresion.x1,")
                    		.append("cfd_impresion.y1,")
                    		.append("cfd_impresion.visible,")
                    		.append("cfd_impresion.banda,")
                    		.append(idempresa).append(" as idEmpresa,")
                    		.append(sucursal).append("as idsucursal ")
                    		.append("FROM {oj cfd_campo LEFT OUTER JOIN " +
                    				"cfd_impresion ON cfd_campo.idCampo = " +
                    				"cfd_impresion.idImpresion} ")
            				.append("WHERE  cfd_impresion.idEmpresa = ")
            				.append(idempresa)
            				.append(" AND cfd_impresion.idsucursal = ")
            				.append(sucursal).toString();
                    db.consultaUpdate(sql, false);
                    db.commit();
                } else
                if(countpag == 1) {
                    m.GetMensaje("No existe configuracion de campos, PDF no creado...");
                }
                sql = (new StringBuilder("SELECT a.campo AS campo,b.x AS " +
                		"x,b.y AS y FROM cfd_campo AS a INNER JOIN " +
                		"cfd_impresion AS b ON a.idCampo = b.idImpresion WHERE" +
                		"  b.serie = '")).append(serie).append("' AND ")
                		.append("b.idEmpresa = ").append(idempresa)
                		.append(" AND b.idsucursal =  ").append(sucursal)
                		.append(" ORDER BY a.idCampo").toString();
                rsCoordenadas = db.consulta(sql, true);
            } else {
                rsCoordenadas.beforeFirst();
            }
            if(rsCoordenadas.next()) {
                cb.setFontAndSize(BaseFont.createFont("Helvetica", "Cp1252", false), 7F);
                cb.beginText();
                int cc;
                String arregloCadenas[];
                if(countpag <= pag) {
                    estableceCoordenadas(serie, idempresa, "1", sucursal);
                    if(noCertificado == null) {
                        noCertificado = "";
                    }
                    cb.showTextAligned(1, noCertificado, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "2", sucursal);
                    cb.showTextAligned(1, fecha, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "3", sucursal);
                    cc = py;
                    tamCad = 100;
                    arregloCadenas = descomponerDescripcion(tamCad, nombre);
                    for(int ii = 0; ii < numCadenas; ii++) {
                        cb.showTextAligned(0, arregloCadenas[ii], px, cc, 0.0F);
                        cc -= 8;
                    }

                    estableceCoordenadas(serie, idempresa, "4", sucursal);
                    cb.showTextAligned(1, rfc, px, py, 0.0F);
                    String interior = null;
                    estableceCoordenadas(serie, idempresa, "5", sucursal);
                    if(noInterior == null || noInterior.equals("0")) {
                        interior = "";
                    }
                    if(noInterior != "" && !noInterior.equals("0")) {
                        interior = noInterior;
                    }
                    if(noExterior == null || noExterior.equals("0")) {
                        noExterior = "";
                    }
                    cc = py;
                    tamCad = px;
                    domicilio = (new StringBuilder(String.valueOf(calle)))
                    .append(" ").append(noExterior).append(" ")
                    .append(interior).append(" ").append(colonia).toString();
                    arregloCadenas = descomponerDescripcion(tamCad, domicilio);
                    for(int ii = 0; ii < numCadenas; ii++) {
                        cb.showTextAligned(0, arregloCadenas[ii], tamCad, cc, 0.0F);
                        cc -= 8;
                    }

                    estableceCoordenadas(serie, idempresa, "40", sucursal);
                    cb.showTextAligned(1, calle, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "41", sucursal);
                    cb.showTextAligned(1, noExterior, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "42", sucursal);
                    cb.showTextAligned(1, interior, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "43", sucursal);
                    cb.showTextAligned(1, colonia, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "6", sucursal);
                    cb.showTextAligned(1, municipio, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "7", sucursal);
                    cb.showTextAligned(0, estado, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "8", sucursal);
                    cb.showTextAligned(1, codigoPostal, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "9", sucursal);
                    cb.showTextAligned(1, pais, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "10", sucursal);
                    cb.showTextAligned(0, telefono, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "11", sucursal);
                    cb.showTextAligned(1, changenull(email).trim(), px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "12", sucursal);
                    if(serie.equals("_")) {
                        cb.showTextAligned(1, FormateaFolio(folio), px, py, 0.0F);
                    } else {
                        cb.showTextAligned(1, (new StringBuilder(
                        		String.valueOf(serie))).append(" ")
                        		.append(FormateaFolio(folio))
                        		.toString(), px, py, 0.0F);
                    }
                    estableceCoordenadas(serie, idempresa, "34", sucursal);
                    cb.showTextAligned(1, tipo_docto, px, py, 0.0F);
                    if(observaciones == null) {
                        observaciones = "";
                    }
                    estableceCoordenadas(serie, idempresa, "35", sucursal);
                    tamCad = (int)((double)px1 / 29.170000000000002D);
                    arregloCadenas = descomponerCadena(tamCad, observaciones);
                    s = py;
                    for(int ii = 0; ii < numCadenas; ii++) {
                        cb.showTextAligned(0, arregloCadenas[ii], px, s, 0.0F);
                        s -= 8;
                    }

                } else {
                    rsCoordenadas.absolute(rsCoordenadas.getRow() + 11);
                }
                estableceCoordenadas(serie, idempresa, "13", sucursal);
                sql = (new StringBuilder("SELECT cantidad, descripcion, " +
                		"importe, valorUnitario, clavearticulo FROM " +
                		"cfd_conceptos WHERE folio = ")).append(folio)
                		.append(" AND ").append("serie = '").append(serie)
                		.append("' AND ").append("idEmpresa = ")
                		.append(idempresa).append(" ORDER BY valorUnitario desc")
                		.toString();
                rsConceptos = db.consulta(sql, true);
                for(num_lineas = 0; rsConceptos.next(); num_lineas += numCadenas + 1) {
                    descripcion = rsConceptos.getString("descripcion");
                    estableceCoordenadas(serie, idempresa, "14", sucursal);
                    tamCad = (int)((double)px1 / 29.170000000000002D);
                    descomponerDescripcion(tamCad, descripcion);
                }

                rsConceptos.last();
                total_articulos = num_lineas;
                t_articulos = rsConceptos.getRow();
                sql = (new StringBuilder("SELECT cnet_impuesto.descripcion, " +
                		"cfd_det_impuestos.impuesto, sum(cfd_impuestos.importe)" +
                		" as importe FROM cfd_impuestos, cnet_impuesto, " +
                		"cfd_det_impuestos WHERE ( cfd_impuestos.idCNImpuesto" +
                		" = cnet_impuesto.idCNImpuesto ) and ( " +
                		"cfd_impuestos.idCNImpuesto = cfd_det_impuestos." +
                		"idCNImpuesto ) and ( cfd_impuestos.idCatImpuesto = " +
                		"cfd_det_impuestos.idCatImpuesto ) and ( " +
                		"cfd_det_impuestos.estado <> 'N') and( ( " +
                		"cfd_impuestos.idEmpresa =")).append(idempresa)
                		.append(") AND ").append("( cfd_impuestos.folio =")
                		.append(folio).append(") AND ")
                		.append("( cfd_impuestos.serie = '")
                		.append(serie).append("' ) ) ")
                		.append("GROUP BY cnet_impuesto.descripcion, ")
                		.append("cfd_det_impuestos.impuesto ").toString();
                rsImpuestos = db.consulta(sql, true);
                if(rsImpuestos.next()) {
                    rsImpuestos.last();
                    total_impuestos = rsImpuestos.getRow();
                } else {
                    sql = (new StringBuilder(" SELECT cnet_impuesto." +
                    		"descripcion,  cfd_impuestos.idcatimpuesto as" +
                    		" impuesto,  sum(cfd_impuestos.importe) as" +
                    		" importe  FROM cfd_impuestos, cnet_impuesto" +
                    		"  WHERE ( cfd_impuestos.idCNImpuesto = " +
                    		"cnet_impuesto.idCNImpuesto ) and ( ( " +
                    		"cfd_impuestos.idEmpresa =")).append(idempresa)
                    		.append(") AND ").append("( cfd_impuestos." +
                    				"folio =").append(folio).append(") AND ")
                    				.append("( cfd_impuestos.serie = '")
                    				.append(serie).append("' ) ) ")
                    				.append("GROUP BY cnet_impuesto." +
                    						"descripcion, cfd_impuestos." +
                    						"idcatimpuesto").toString();
                    rsImpuestos = db.consulta(sql, true);
                    rsImpuestos.last();
                    total_impuestos = rsImpuestos.getRow();
                }
                sql = (new StringBuilder("SELECT IMPORTE_TOTAL FROM " +
                		"CFD_DESCUENTOS WHERE tipo = 'D' and folio = "))
                		.append(folio).append(" and serie = '")
                		.append(serie).append("' and CFD_DESCUENTOS." +
                				"idEmpresa = ").append(idempresa).toString();
                rsDescuentos = db.consulta(sql, true);
                rsDescuentos.last();
                total_descuentos = rsDescuentos.getRow();
                cc = py;
                estableceCoordenadas(serie, idempresa, "13", sucursal);
                posicionesx[0] = px;
                estableceCoordenadas(serie, idempresa, "14", sucursal);
                posicionesx[1] = px;
                estableceCoordenadas(serie, idempresa, "15", sucursal);
                posicionesx[2] = px;
                estableceCoordenadas(serie, idempresa, "16", sucursal);
                posicionesx[3] = px;
                estableceCoordenadas(serie, idempresa, "39", sucursal);
                posicionesx[4] = px;
                s = cc;
                nconceptos = 1;
                if(reg != 1 || regi != 1) {
                    if(reg != 0) {
                        rsConceptos.absolute(reg);
                    }
                    if(regi != 0) {
                        rsImpuestos.absolute(regi);
                    }
                } else {
                    rsConceptos.last();
                    rsImpuestos.last();
                    rsDescuentos.last();
                    estableceCoordenadas(serie, idempresa, "14", sucursal);
                    tamArt = (int)((double)py1 / 47.329999999999998D);
                    if((int)((double)py1 % 47.329999999999998D) > 0) {
                        tamArt++;
                    }
                    npag = (num_lineas + total_descuentos + rsImpuestos
                    		.getRow() * 2 + 2) / num_art;
                    dpag = (num_lineas + total_descuentos + rsImpuestos
                    		.getRow() * 2 + 2) % num_art;
                    if(dpag > 0.0D) {
                        npag++;
                    }
                    estableceCoordenadas(serie, idempresa, "22", sucursal);
                    tamCad = (int)((double)px1 / 29.170000000000002D);
                    arregloCadenas = descomponerCadena(tamCad, cadOriginal);
                    if(total_articulos > num_art || 
                    		numCadenas > (int)((double)py1 / 47.329999999999998D)) {
                        epag += numCadenas / 60;
                        dpag = numCadenas % 60;
                        if(dpag > 0.0D) {
                            epag++;
                        }
                        npag += epag;
                    }
                    rsConceptos.beforeFirst();
                    rsImpuestos.beforeFirst();
                }
                estableceCoordenadas(serie, idempresa, "36", sucursal);
                cb.showTextAligned(1, (new StringBuilder("pagina "))
                		.append(countpag).append(" de ")
                		.append(npag).toString(), px, py, 0.0F);
                System.out.println((new StringBuilder("pagina "))
                		.append(countpag).append(" de ").append(npag).toString());
                if(reg == t_articulos) {
                    rsConceptos.previous();
                }
                if(rsConceptos.next()) {
                    if(rsConceptos.getRow() != 1) {
                        if(count_art > 0) {
                            rsConceptos.absolute(reg);
                        } else {
                            rsConceptos.absolute(reg - 1);
                        }
                        sql = (new StringBuilder("SELECT CFD_DESCUENTOS." +
                        		"IMPORTE_TOTAL, CFD_CATDESCUENTOS.DESCRIPCION," +
                        		" CFD_CATDESCUENTOS.TASA FROM CFD_DESCUENTOS" +
                        		" LEFT JOIN CFD_CATDESCUENTOS ON " +
                        		"CFD_DESCUENTOS.IDCDECUENTO = " +
                        		"CFD_CATDESCUENTOS.IDCDECUENTO WHERE " +
                        		"CFD_DESCUENTOS.tipo = 'D' and folio = "))
                        		.append(folio).append(" and serie = '")
                        		.append(serie).append("' and " +
                        				"CFD_DESCUENTOS.idEmpresa = ")
                        				.append(idempresa)
                        				.append(" and cveart ='")
                        				.append(rsConceptos
                        						.getString("clavearticulo"))
                        						.append("'").toString();
                        rsDescuentos = db.consulta(sql, true);
                        rsDescuentos.last();
                        if(regd <= rsDescuentos.getRow() && 
                    	   regd > 0 && count_art == 0) {
                            rsDescuentos.absolute(regd);
                            do {
                                estableceCoordenadas(serie, idempresa,
                                					 "14", sucursal);
                                tamCad = (int)((double)px1 / 29.170000000000002D);
                                if(count_des > 0) {
                                    arregloCadenas = descomponerDescripcion(
                                		tamCad,
                                		rsDescuentos.getString("DESCRIPCION")
                            		);
                                    for(int ii = count_des; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++) {
                                        cb.showTextAligned(0, arregloCadenas[ii],
                                        				   posicionesx[1], s, 0.0F);
                                        s -= 8;
                                        if(ii == numCadenas - 1) {
                                            count_des = 0;
                                        } else {
                                            count_des = ii + 1;
                                        }
                                        nconceptos++;
                                    }

                                    s -= 8;
                                    nconceptos++;
                                } else {
                                    cc = s;
                                    arregloCadenas = descomponerDescripcion(
                                		tamCad,
                                		rsDescuentos.getString("DESCRIPCION")
                            		);
                                    for(int ii = count_des; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++) {
                                        cb.showTextAligned(
                                    		0,
                                    		arregloCadenas[ii],
                                    		posicionesx[1], s, 0.0F
                                		);
                                        s -= 8;
                                        if(ii == numCadenas - 1) {
                                            count_des = 0;
                                        } else {
                                            count_des = ii + 1;
                                        }
                                        nconceptos++;
                                    }

                                    cb.showTextAligned(2, (new StringBuilder())
                                    		.append(dec.format(rsDescuentos
                                    				.getFloat("TASA")))
                                    				.toString(), posicionesx[2],
                                    				cc, 0.0F);
                                    cb.showTextAligned(2, (new StringBuilder())
                                    		.append(dec.format(
                                				roundNum(
                            						rsDescuentos
	                            						.getFloat("IMPORTE_TOTAL"))))
	                            						.toString(),
                            						posicionesx[3], cc, 0.0F);
                                    s -= 8;
                                    cc -= numCadenas * 8;
                                    ly = cc;
                                    nconceptos++;
                                }
                            } while(rsDescuentos.next() && nconceptos <= num_art && count_des == 0);
                            regd = rsDescuentos.getRow();
                            if(count_des > 0) {
                                regd--;
                            }
                        }
                        rsConceptos.absolute(reg);
                    }
                    if(nconceptos <= num_art) {
                        do {
                            cantidad = rsConceptos.getDouble("cantidad");
                            descripcion = rsConceptos.getString("descripcion");
                            importe = roundNum(rsConceptos.getFloat("importe"));
                            valorUnitario = roundNum(rsConceptos
                            		.getFloat("valorUnitario"));
                            clave = rsConceptos.getString("clavearticulo");
                            cb.setFontAndSize(BaseFont
                        		.createFont("Helvetica", "Cp1252", false), 7F);
                            estableceCoordenadas(serie, idempresa, "14", sucursal);
                            tamCad = (int)((double)px1 / 29.170000000000002D);
                            if(count_art > 0) {
                                arregloCadenas = descomponerDescripcion(tamCad, descripcion);
                                for(int ii = count_art; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++) {
                                    cb.showTextAligned(
                                		0, arregloCadenas[ii], posicionesx[1],
                                		s, 0.0F
                            		);
                                    s -= 8;
                                    if(ii == numCadenas - 1) {
                                        count_art = 0;
                                    } else {
                                        count_art = ii + 1;
                                    }
                                    nconceptos++;
                                }

                                s -= 8;
                                nconceptos++;
                            } else {
                                cc = s;
                                arregloCadenas = descomponerDescripcion(tamCad, descripcion);
                                for(int ii = count_art; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++) {
                                    cb.showTextAligned(0, arregloCadenas[ii],
                                    				   posicionesx[1], s, 0.0F);
                                    s -= 8;
                                    if(ii == numCadenas - 1)
                                        count_art = 0;
                                    else
                                        count_art = ii + 1;
                                    nconceptos++;
                                }

                                cb.showTextAligned(1, (new StringBuilder())
                            		.append(clave).toString(), posicionesx[4],
                            				cc, 0.0F);
                                cb.showTextAligned(1, (new StringBuilder())
                                		.append(cantidad).toString(), posicionesx[0], cc, 0.0F);
                                cb.showTextAligned(2, (new StringBuilder())
                                		.append(dec.format(valorUnitario))
                                		.toString(), posicionesx[2], cc, 0.0F);
                                cb.showTextAligned(2, (new StringBuilder())
                                		.append(dec.format(importe))
                                		.toString(), posicionesx[3], cc, 0.0F);
                                subtotal += importe;
                                s -= 8;
                                cc -= numCadenas * 8;
                                ly = cc;
                                nconceptos++;
                            }
                            if(nconceptos <= num_art)
                            {
                                sql = (new StringBuilder("SELECT " +
                                		"CFD_DESCUENTOS.IMPORTE_TOTAL, " +
                                		"CFD_CATDESCUENTOS.DESCRIPCION, " +
                                		"CFD_CATDESCUENTOS.TASA FROM " +
                                		"CFD_DESCUENTOS LEFT JOIN " +
                                		"CFD_CATDESCUENTOS ON " +
                                		"CFD_DESCUENTOS.IDCDECUENTO = " +
                                		"CFD_CATDESCUENTOS.IDCDECUENTO " +
                                		"WHERE CFD_DESCUENTOS.tipo = 'D' " +
                                		"and folio = ")).append(folio)
                                		.append(" and serie = '").append(serie)
                                		.append("' and CFD_DESCUENTOS." +
                                				"idEmpresa = ")
                        				.append(idempresa)
                        				.append(" and cveart ='")
                        				.append(
                    						rsConceptos.getString("clavearticulo"))
                    						.append("'").toString();
                                rsDescuentos = db.consulta(sql, true);
                                if(rsDescuentos.next())
                                {
                                    do
                                        if(count_des > 0)
                                        {
                                            arregloCadenas = descomponerDescripcion(tamCad, rsDescuentos.getString("DESCRIPCION"));
                                            for(int ii = count_des; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++)
                                            {
                                                cb.showTextAligned(0, arregloCadenas[ii], posicionesx[1], s, 0.0F);
                                                s -= 8;
                                                if(ii == numCadenas - 1)
                                                    count_des = 0;
                                                else
                                                    count_des = ii + 1;
                                                nconceptos++;
                                            }

                                            s -= 8;
                                            nconceptos++;
                                        } else
                                        {
                                            cc = s;
                                            arregloCadenas = descomponerDescripcion(tamCad, rsDescuentos.getString("DESCRIPCION"));
                                            for(int ii = count_des; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++)
                                            {
                                                cb.showTextAligned(0, arregloCadenas[ii], posicionesx[1], s, 0.0F);
                                                s -= 8;
                                                if(ii == numCadenas - 1)
                                                    count_des = 0;
                                                else
                                                    count_des = ii + 1;
                                                nconceptos++;
                                            }

                                            cb.showTextAligned(2, (new StringBuilder()).append(dec.format(rsDescuentos.getFloat("TASA"))).toString(), posicionesx[2], cc, 0.0F);
                                            cb.showTextAligned(2, (new StringBuilder()).append(dec.format(roundNum(rsDescuentos.getFloat("IMPORTE_TOTAL")))).toString(), posicionesx[3], cc, 0.0F);
                                            s -= 8;
                                            cc -= numCadenas * 8;
                                            ly = cc;
                                            nconceptos++;
                                        }
                                    while(rsDescuentos.next() && nconceptos <= num_art && count_des == 0);
                                    regd = rsDescuentos.getRow();
                                    if(count_des > 0)
                                        regd--;
                                }
                            }
                        } while(rsConceptos.next() && nconceptos <= num_art && count_art == 0);
                        reg = rsConceptos.getRow();
                        if(count_art > 0)
                            reg--;
                    }
                    subtotal_sin_descuentos = subtotal;
                }
                if(rsConceptos.isAfterLast() && nconceptos <= num_art)
                {
                    sql = (new StringBuilder("SELECT " +
                    		"CFD_DESCUENTOS.IMPORTE_TOTAL, " +
                    		"CFD_CATDESCUENTOS.DESCRIPCION, " +
                    		"CFD_CATDESCUENTOS.TASA FROM CFD_DESCUENTOS " +
                    		"LEFT JOIN CFD_CATDESCUENTOS ON " +
                    		"CFD_DESCUENTOS.IDCDECUENTO = " +
                    		"CFD_CATDESCUENTOS.IDCDECUENTO WHERE " +
                    		"CFD_DESCUENTOS.tipo = 'D' and folio = "))
                    		.append(folio).append(" and serie = '")
                    		.append(serie).append("' and " +
                    				"CFD_DESCUENTOS.idEmpresa = ")
                    				.append(idempresa).append(" and (cveart" +
                    						" is null or cveart = '-1')").toString();
                    rsDescuentos = db.consulta(sql, true);
                    rsDescuentos.first();
                    int cc1 = 360;
                    if(regd <= rsDescuentos.getRow() && regd > 0)
                        do
                            if(count_des > 0)
                            {
                                cc = s;
                                System.out.println((new StringBuilder())
                                		.append("----------------------> VALOR" +
                                				" DE S = ").append(s)
                                				.append(" <------------------" +
                                						"-----").toString());
                                arregloCadenas = descomponerDescripcion(
                            		tamCad, (new StringBuilder())
                            		.append(rsDescuentos.getString("DESCRIPCION"))
                            		.append(" FINANCIERO").toString());
                                for(int ii = 0; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++) {
                                    cb.showTextAligned(
                                		0, arregloCadenas[ii],
                                		posicionesx[1], cc1, 0.0F);
                                    s -= 8;
                                    if(ii == numCadenas)
                                        count_des = 0;
                                    else
                                        count_des = ii + 1;
                                    nconceptos++;
                                }

                                cb.showTextAligned(2, (new StringBuilder())
                                		.append("  ").toString(), posicionesx[2],
                                		cc1, 0.0F);
                                cb.showTextAligned(2, (new StringBuilder())
                            		.append(dec.format(roundNum(
                        				rsDescuentos.getFloat("IMPORTE_TOTAL"))))
                        				.toString(), posicionesx[3], cc1, 0.0F);
                                s -= 8;
                                cc -= numCadenas * 8;
                                ly = cc;
                                nconceptos++;
                            } else
                            {
                                cc = s;
                                System.out.println((new StringBuilder())
                                		.append("----------------------> " +
                                				"VALOR DE S1 = ").append(s)
                                				.append(" <---------------" +
                                						"--------").toString());
                                arregloCadenas = descomponerDescripcion(tamCad, (new StringBuilder()).append(rsDescuentos.getString("DESCRIPCION")).append(" VOLUMEN").toString());
                                for(int ii = 0; ii < numCadenas && ii < num_art && nconceptos < num_art; ii++)
                                {
                                    cb.showTextAligned(0, "                  " +
                                    		"                                               SUBTOTAL", posicionesx[1], s, 0.0F);
                                    cb.showTextAligned(0, arregloCadenas[ii],
                                    		posicionesx[1], cc1, 0.0F);
                                    s -= 8;
                                    if(ii == numCadenas)
                                        count_des = 0;
                                    else
                                        count_des = ii + 1;
                                    nconceptos++;
                                }

                                cb.showTextAligned(2, (new StringBuilder())
                            		.append(dec.format(roundNum(
                            				subtotal_sin_descuentos)))
                            				.toString(), posicionesx[3],
                            				cc, 0.0F);
                                cb.showTextAligned(2, (new StringBuilder())
                            		.append("  ").toString(), posicionesx[2],
                            		cc1, 0.0F);
                                cb.showTextAligned(2, (new StringBuilder())
                            		.append(dec.format(roundNum(
                        				rsDescuentos.getFloat("IMPORTE_TOTAL"))))
                        				.toString(), posicionesx[3], cc1, 0.0F);
                                s -= 8;
                                cc -= numCadenas * 8;
                                cc1 -= numCadenas * 8;
                                ly = cc;
                                nconceptos++;
                            }
                        while(rsDescuentos.next() && nconceptos <= num_art);
                    nconceptos += 2;
                }
                if(rsConceptos.isAfterLast() && nconceptos <= num_art && regi != 0)
                {
                    cb.setFontAndSize(BaseFont.createFont(
                    		"Helvetica", "Cp1252",
                    		false), 7F);
                    tamCad = 50;
                    if(!rfc.equals("XAXX010101000"))
                    {
                        estableceCoordenadas(serie, idempresa, "49", sucursal);
                        rsImpuestos.previous();
                        if(rsImpuestos.next() && px > 0 && py > 0)
                        {
                            s -= 16;
                            cb.showTextAligned(0, "Desglose de impuestos:",
                            				   posicionesx[1], s, 0.0F);
                            s -= 8;
                            do
                            {
                                String impuesto_tasa = null;
                                if(rsImpuestos.getString("impuesto") == null)
                                    impuesto_tasa = "";
                                else
                                    impuesto_tasa = rsImpuestos.getString("impuesto");
                                arregloCadenas = descomponerDescripcion(
                            		tamCad,(new StringBuilder(
                        				String.valueOf(
                    						rsImpuestos.getString("descripcion"))))
                    						.append(" TASA ")
                    						.append(impuesto_tasa).toString());
                                for(int ii = 0; ii < numCadenas; ii++) {
                                    cb.showTextAligned(
                                		0, arregloCadenas[ii],
                                		posicionesx[1] + 20, s, 0.0F);
                                    s -= 8;
                                }

                                cc = s;
                                cb.showTextAligned(2, (
                            		new StringBuilder())
                            		.append(dec.format(roundNum(
                        				Math.abs(rsImpuestos.getFloat("importe")))))
                        				.toString(), posicionesx[3], cc, 0.0F);
                                s -= 8;
                                cc -= numCadenas * 8;
                                nconceptos += 2;
                            } while(rsImpuestos.next() && nconceptos <= num_art);
                        }
                    }
                    regi = rsImpuestos.getRow();
                }
                cb.setFontAndSize(BaseFont.createFont("Helvetica", "Cp1252", false), 7F);
                estableceCoordenadas(serie, idempresa, "22", sucursal);
                tamCad = (int)((double)px1 / 29.170000000000002D);
                arregloCadenas = descomponerCadena(tamCad, cadOriginal);
                if(total_articulos > num_art ||
            		numCadenas > (int)((double)py1 / 47.329999999999998D))
                    pag = npag - epag;
                else
                    pag = npag;
                if(countpag == pag) {
                    sql = (new StringBuilder("SELECT FOLIO, SERIE, IDEMPRESA," +
                    		"SUM(IMPORTE_TOTAL) AS DESCUENTOS FROM " +
                    		"CFD_DESCUENTOS WHERE tipo = 'D' and folio = "))
                    		.append(folio).append(" and serie = '")
                    		.append(serie).append("' and idEmpresa = ")
                    		.append(idempresa).append(" GROUP BY FOLIO, " +
                    				"SERIE, IDEMPRESA").toString();
                    rsDescuentos = db.consulta(sql, true);
                    if(rsDescuentos.next())
                        subtotal = roundNum(subtotal - rsDescuentos.getDouble("DESCUENTOS"));
                    sql = (new StringBuilder("SELECT FOLIO, SERIE, IDEMPRESA" +
                    		",SUM(IMPORTE_TOTAL) AS DESCUENTOS FROM }" +
                    		"CFD_DESCUENTOS WHERE tipo = 'C' and folio = "))
                    		.append(folio).append(" and serie = '")
                    		.append(serie).append("' and idEmpresa = ")
                    		.append(idempresa).append(" GROUP BY FOLIO, " +
                    				"SERIE, IDEMPRESA").toString();
                    rsDescuentos = db.consulta(sql, true);
                    if(rsDescuentos.next())
                        subtotal = roundNum(subtotal + rsDescuentos.getDouble("DESCUENTOS"));
                    System.out.println("----------------------------> " +
                    		"VALOR DE PX, PY de subtotales <-----------------" +
                    		"------------------");
                    estableceCoordenadas(serie, idempresa, "17", sucursal);
                    if(!rfc.equals("XAXX010101000"))
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(subtotal)).toString(),
                        				px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "18", sucursal);
                    if(!rfc.equals("XAXX010101000"))
                    {
                        sql = (new StringBuilder("SELECT cnet_impuesto." +
                        		"descripcion, cfd_det_impuestos.impuesto," +
                        		"sum(cfd_impuestos.importe) as importe FROM " +
                        		"cfd_impuestos, cnet_impuesto, " +
                        		"cfd_det_impuestos WHERE ( cfd_impuestos." +
                        		"idCNImpuesto = cnet_impuesto.idCNImpuesto ) " +
                        		"and ( cfd_impuestos.idCNImpuesto = " +
                        		"cfd_det_impuestos.idCNImpuesto ) and ( " +
                        		"cfd_impuestos.idCatImpuesto = " +
                        		"cfd_det_impuestos.idCatImpuesto ) and ( " +
                        		"cfd_det_impuestos.estado <> 'N') and( ( " +
                        		"cfd_impuestos.idEmpresa ="))
                        		.append(idempresa).append(") AND ")
                        		.append("( cfd_impuestos.folio =")
                        		.append(folio).append(") AND ")
                        		.append("( cfd_impuestos.serie = '")
                        		.append(serie).append("' ) ) AND " +
                        				"(cnet_impuesto.tipo = 'T' OR " +
                        				"cnet_impuesto.tipo = '+')")
                        				.append("GROUP BY cnet_impuesto." +
                        						"descripcion, " +
                        						"cfd_det_impuestos.impuesto")
                        						.toString();
                        rs = db.consulta(sql, true);
                        for(totalImpuestos = 0.0D; rs.next(); totalImpuestos += roundNum(rs.getFloat("importe")))
                        {
                            if(rs.getString("descripcion").trim().equals("IEPS"))
                                traslados_ieps += roundNum(rs.getFloat("importe"));
                            if(rs.getString("descripcion").trim().equals("IVA"))
                                traslados_iva += roundNum(rs.getFloat("importe"));
                            if(rs.getString("descripcion").trim().equals("ISH"))
                                traslados_ish += roundNum(rs.getFloat("importe"));
                        }

                        if(totalImpuestos == 0.0D)
                        {
                            sql = (new StringBuilder("SELECT cnet_impuesto." +
                            		"descripcion, sum(cfd_impuestos.importe) " +
                            		"as importe FROM cfd_impuestos, " +
                            		"cnet_impuesto WHERE ( cfd_impuestos." +
                            		"idCNImpuesto = cnet_impuesto.idCNImpuesto" +
                            		" ) and ( ( cfd_impuestos.idEmpresa ="))
                            		.append(idempresa).append(") AND ")
                            		.append("( cfd_impuestos.folio =")
                            		.append(folio).append(") AND ")
                            		.append("( cfd_impuestos.serie = '")
                            		.append(serie).append("' ) ) AND " +
                            				"(cnet_impuesto.tipo = 'T' " +
                            				"OR cnet_impuesto.tipo = '+')")
                            				.append("GROUP BY " +
                            						"cnet_impuesto.descripcion")
                            						.toString();
                            rs = db.consulta(sql, true);
                            for(totalImpuestos = 0.0D; rs.next(); totalImpuestos += roundNum(rs.getFloat("importe")))
                            {
                                if(rs.getString("descripcion").trim().equals("IEPS"))
                                    traslados_ieps += roundNum(rs.getFloat("importe"));
                                if(rs.getString("descripcion").trim().equals("IVA"))
                                    traslados_iva += roundNum(rs.getFloat("importe"));
                                if(rs.getString("descripcion").trim().equals("ISH"))
                                    traslados_ish += roundNum(rs.getFloat("importe"));
                            }

                        }
                        subtotal_honorarios = subtotal + totalImpuestos;
                        estableceCoordenadas(serie, idempresa, "32", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(totalImpuestos)))
                        		.toString(), px, py, 0.0F);
                        estableceCoordenadas(serie, idempresa, "52", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(traslados_iva)))
                        		.toString(), px, py, 0.0F);
                        estableceCoordenadas(serie, idempresa, "53", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(traslados_ieps)))
                        		.toString(), px, py, 0.0F);
                        estableceCoordenadas(serie, idempresa, "60", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(traslados_ish)))
                        		.toString(), px, py, 0.0F);
                        estableceCoordenadas(serie, idempresa, "54", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(subtotal_honorarios)))
                        		.toString(), px, py, 0.0F);
                        subtotal_honorarios = subtotal + traslados_iva;
                        estableceCoordenadas(serie, idempresa, "56", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(subtotal_honorarios)))
                        		.toString(), px, py, 0.0F);
                        subtotal_honorarios = subtotal + traslados_ieps;
                        estableceCoordenadas(serie, idempresa, "58", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(subtotal_honorarios)))
                        		.toString(), px, py, 0.0F);
                        sql = (new StringBuilder("SELECT cnet_impuesto." +
                        		"descripcion, cfd_det_impuestos.impuesto, " +
                        		"sum(cfd_impuestos.importe) as importe FROM " +
                        		"cfd_impuestos, cnet_impuesto, " +
                        		"cfd_det_impuestos WHERE ( cfd_impuestos." +
                        		"idCNImpuesto = cnet_impuesto.idCNImpuesto )" +
                        		" and ( cfd_impuestos.idCNImpuesto = " +
                        		"cfd_det_impuestos.idCNImpuesto ) and ( " +
                        		"cfd_impuestos.idCatImpuesto = " +
                        		"cfd_det_impuestos.idCatImpuesto ) and ( " +
                        		"cfd_det_impuestos.estado <> 'N') and( ( " +
                        		"cfd_impuestos.idEmpresa =")).append(idempresa)
                        		.append(") AND ").append("( cfd_impuestos.folio =")
                        		.append(folio).append(") AND ")
                        		.append("( cfd_impuestos.serie = '")
                        		.append(serie).append("' ) ) AND " +
                        				"(cnet_impuesto.tipo = 'R' OR " +
                        				"cnet_impuesto.tipo = '-')")
                        				.append("GROUP BY cnet_impuesto." +
                        						"descripcion, cfd_det_impuestos" +
                        						".impuesto").toString();
                        rs = db.consulta(sql, true);
                        for(totalImpuestos = 0.0D; rs.next(); totalImpuestos += roundNum(rs.getFloat("importe")))
                        {
                            if(rs.getString("descripcion").trim().equals("IVA"))
                                retencion_iva += roundNum(rs.getFloat("importe"));
                            if(rs.getString("descripcion").trim().equals("ISR"))
                                retencion_isr += roundNum(rs.getFloat("importe"));
                        }

                        if(totalImpuestos == 0.0D)
                        {
                            sql = (new StringBuilder("SELECT cnet_impuesto." +
                            		"descripcion, sum(cfd_impuestos.importe) " +
                            		"as importe FROM cfd_impuestos, " +
                            		"cnet_impuesto WHERE ( cfd_impuestos." +
                            		"idCNImpuesto = cnet_impuesto.idCNImpuesto" +
                            		" ) and ( ( cfd_impuestos.idEmpresa ="))
                            		.append(idempresa).append(") AND ")
                            		.append("( cfd_impuestos.folio =")
                            		.append(folio).append(") AND ")
                            		.append("( cfd_impuestos.serie = '")
                            		.append(serie).append("' ) ) AND " +
                            				"(cnet_impuesto.tipo = 'R' OR " +
                            				"cnet_impuesto.tipo = '-')")
                            				.append("GROUP BY cnet_impuesto." +
                            						"descripcion").toString();
                            rs = db.consulta(sql, true);
                            for(totalImpuestos = 0.0D; rs.next(); totalImpuestos += roundNum(rs.getFloat("importe")))
                            {
                                if(rs.getString("descripcion").trim().equals("IVA"))
                                    retencion_iva += roundNum(rs.getFloat("importe"));
                                if(rs.getString("descripcion").trim().equals("ISR"))
                                    retencion_isr += roundNum(rs.getFloat("importe"));
                            }

                        }
                        estableceCoordenadas(serie, idempresa, "33", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(totalImpuestos)))
                        		.toString(), px, py, 0.0F);
                        estableceCoordenadas(serie, idempresa, "50", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(retencion_iva)))
                        		.toString(), px, py, 0.0F);
                        estableceCoordenadas(serie, idempresa, "51", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(retencion_isr)))
                        		.toString(), px, py, 0.0F);
                        subtotal_honorarios = subtotal + retencion_iva;
                        estableceCoordenadas(serie, idempresa, "59", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(subtotal_honorarios)))
                        		.toString(), px, py, 0.0F);
                        subtotal_honorarios = subtotal + retencion_isr;
                        estableceCoordenadas(serie, idempresa, "57", sucursal);
                        cb.showTextAligned(1, (new StringBuilder())
                        		.append(dec.format(Math.abs(subtotal_honorarios)))
                        		.toString(), px, py, 0.0F);
                    }
                    sql = (new StringBuilder("SELECT cnet_impuesto." +
                    		"descripcion, cfd_det_impuestos.impuesto, " +
                    		"cfd_impuestos.tipo, sum(cfd_impuestos.importe) " +
                    		"as importe FROM cfd_impuestos, cnet_impuesto, " +
                    		"cfd_det_impuestos WHERE ( cfd_impuestos." +
                    		"idCNImpuesto = cnet_impuesto.idCNImpuesto ) " +
                    		"and ( cfd_impuestos.idCNImpuesto = " +
                    		"cfd_det_impuestos.idCNImpuesto ) and ( " +
                    		"cfd_impuestos.idCatImpuesto = cfd_det_impuestos" +
                    		".idCatImpuesto ) and ( " +
                    		"cfd_det_impuestos.estado <> 'N') and( ( " +
                    		"cfd_impuestos.idEmpresa =")).append(idempresa)
                    		.append(") AND ").append("( cfd_impuestos.folio =")
                    		.append(folio).append(") AND ")
                    		.append("( cfd_impuestos.serie = '")
                    		.append(serie).append("' ) ) ")
                    		.append("GROUP BY cnet_impuesto.descripcion, " +
                    				"cfd_det_impuestos.impuesto, " +
                    				"cfd_impuestos.tipo ").toString();
                    rs = db.consulta(sql, true);
                    totalImpuestos = 0.0D;
                    total = subtotal;
                    while(rs.next()) 
                    {
                        totalImpuestos += roundNum(rs.getFloat("importe"));
                        if(rs.getString("tipo").equals("+") || 
                        		rs.getString("tipo").equals("T".toUpperCase()))
                            total = roundNum(total + Math.abs(rs.getDouble("importe")));
                        else
                            total = roundNum(total - Math.abs(rs.getDouble("importe")));
                    }
                    if(totalImpuestos == 0.0D)
                    {
                        sql = (new StringBuilder("SELECT cnet_impuesto." +
                        		"descripcion,  cfd_impuestos.tipo, " +
                        		"sum(cfd_impuestos.importe) as importe " +
                        		"FROM cfd_impuestos, cnet_impuesto WHERE ( " +
                        		"cfd_impuestos.idCNImpuesto = cnet_impuesto." +
                        		"idCNImpuesto ) and ( ( cfd_impuestos." +
                        		"idEmpresa =")).append(idempresa)
                        		.append(") AND ")
                        		.append("( cfd_impuestos.folio =")
                        		.append(folio).append(") AND ")
                        		.append("( cfd_impuestos.serie = '")
                        		.append(serie).append("' ) )")
                        		.append("GROUP BY cnet_impuesto.descripcion, cfd_impuestos.tipo").toString();
                        rs = db.consulta(sql, true);
                        totalImpuestos = 0.0D;
                        total = subtotal;
                        while(rs.next()) 
                        {
                            totalImpuestos += rs.getFloat("importe");
                            if(rs.getString("tipo").equals("+") || 
                        		rs.getString("tipo").equals("T".toUpperCase()))
                                total = roundNum(total + Math.abs(rs.getDouble("importe")));
                            else
                                total = roundNum(total - Math.abs(rs.getDouble("importe")));
                        }
                    }
                    estableceCoordenadas(serie, idempresa, "18", sucursal);
                    if(!rfc.equals("XAXX010101000"))
                        cb.showTextAligned(1, (new StringBuilder())
                    		.append(dec.format(Math.abs(totalImpuestos)))
                    		.toString(), px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "19", sucursal);
                    total = roundNum(total);
                    cb.showTextAligned(1, (new StringBuilder())
                		.append(dec.format(total)).toString(), px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "20", sucursal);
                    cb.setFontAndSize(BaseFont.createFont("Courier", "Cp1250", false), 7F);
                    total = Double.parseDouble(FormatoDecimal(total, idempresa));
                    importeLetra = cov.convertirLetras((int)roundNum(total));
                    if(importeLetra != null)
                    {
                        decimales = Double.parseDouble(
                    		FormatoDecimal(Double.parseDouble(
                				FormatoDecimal(total -
                						(double)(int)total, idempresa)),
                						idempresa));
                        try
                        {
                            decimales *= 100D;
                            decimales = Math.round(decimales);
                            decimales = roundNum(decimales);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        if(moneda.equals("PSM"))
                            if(decimales == 0.0D)
                                cb.showTextAligned(0, (new StringBuilder(String
                            		.valueOf(importeLetra.toUpperCase())))
                            		.append(" PESOS ").append("00")
                            		.append("/100 M.N.").toString(), px, py, 0.0F);
                            else
                                cb.showTextAligned(0, (new StringBuilder(String
                            		.valueOf(importeLetra.toUpperCase())))
                            		.append(" PESOS ").append((int)decimales)
                            		.append("/100 M.N.").toString(), px, py, 0.0F);
                        if(moneda.equals("DLA"))
                            if(decimales == 0.0D)
                                cb.showTextAligned(0, (new StringBuilder(String
                            		.valueOf(importeLetra.toUpperCase())))
                            		.append(" DOLARES ").append("00")
                            		.append("/100 U.S.D.").toString(), px, py, 0.0F);
                            else
                                cb.showTextAligned(0, (new StringBuilder(String
                                		.valueOf(importeLetra.toUpperCase())))
                                		.append(" DOLARES ").append((int)decimales)
                                		.append("/100 U.S.D.").toString(), px, py, 0.0F);
                    }
                } else
                {
                    rsCoordenadas.next();
                    rsCoordenadas.next();
                    rsCoordenadas.next();
                }
                cb.setFontAndSize(BaseFont.createFont("Courier", "Cp1252", false), 7F);
                estableceCoordenadas(serie, idempresa, "22", sucursal);
                tamCad = (int)((double)px1 / 29.170000000000002D);
                arregloCadenas = descomponerCadena(tamCad, cadOriginal);
                int numero = (int)((double)py1 / 47.329999999999998D);
                if(numero > 0)
                    if(total_articulos < num_art && numCadenas <= numero)
                    {
                        tamCad = (int)((double)px1 / 29.170000000000002D);
                        arregloCadenas = descomponerCadena(tamCad, cadOriginal);
                        int h = py;
                        for(int ii = 0; ii < numCadenas; ii++)
                        {
                            cb.showTextAligned(0, arregloCadenas[ii], px, h, 0.0F);
                            h -= 10;
                        }

                    } else
                    {
                        tamCad = 105;
                        if(countpag <= pag)
                        {
                            arregloCadenas = descomponerCadena(tamCad, "Ver anexo");
                            int h = py;
                            for(int ii = 0; ii < numCadenas; ii++)
                            {
                                cb.showTextAligned(0, arregloCadenas[ii], px, h, 0.0F);
                                h -= 10;
                            }

                        } else
                        {
                            arregloCadenas = descomponerCadena(tamCad, cadOriginal);
                            estableceCoordenadas(serie, idempresa, "37", sucursal);
                            px = 58;
                            py = 794;
                            cb.showTextAligned(0, "Anexo Cadena Original:", px, py, 0.0F);
                            int h = py - 10;
                            int ii = 0;
                            for(int cr = 0; ii < numCadenas; cr++)
                            {
                                cb.showTextAligned(0, arregloCadenas[ii], px, h, 0.0F);
                                h -= 10;
                                if(cr > 60)
                                {
                                    cr = 0;
                                    h = py;
                                    if(countpag != npag)
                                    {
                                        cb.endText();
                                        doc.newPage();
                                        if(countpag < pag)
                                            doc.add(image);
                                    }
                                    countpag++;
                                    cb = writer.getDirectContent();
                                    cb.setFontAndSize(BaseFont.createFont(
                                		"Courier", "Cp1250", false), 7F
                            		);
                                    cb.beginText();
                                    estableceCoordenadas(serie, idempresa, "37", sucursal);
                                    px = 58;
                                    py = 794;
                                    cb.showTextAligned(0, "Anexo Cadena Original:", px, py, 0.0F);
                                    cb.showTextAligned(1, (new StringBuilder("pagina "))
                                		.append(countpag).append(" de ")
                                		.append(npag).toString(), 500F, 50F, 0.0F);
                                    System.out.println((new StringBuilder("pagina "))
                                		.append(countpag).append(" de ")
                                		.append(npag).toString());
                                }
                                ii++;
                            }

                        }
                    }
                estableceCoordenadas(serie, idempresa, "21", sucursal);
                if(countpag <= pag)
                {
                    tamCad = (int)((double)px1 / 29.170000000000002D);
                    if(tamCad > 0)
                    {
                        arregloCadenas = descomponerCadena(tamCad, sello);
                        int j = py;
                        for(int ii = 0; ii < numCadenas; ii++)
                        {
                            cb.showTextAligned(0, arregloCadenas[ii], px, j, 0.0F);
                            j -= 10;
                        }

                    }
                    rsCoordenadas.next();
                    estableceCoordenadas(serie, idempresa, "23", sucursal);
                    cb.showTextAligned(2, (new StringBuilder())
                		.append((new StringBuilder()).append(noAprobacion)
        				.append(" ").append(anioaprob).toString()).toString(),
        				px, py, 0.0F);
                    rsCoordenadas.next();
                }
                cb.setFontAndSize(BaseFont.createFont("Courier", "Cp1250", false), 7F);
                if(countpag <= pag)
                {
                    estableceCoordenadas(serie, idempresa, "25", sucursal);
                    cc = py;
                    tamCad = 50;
                    arregloCadenas = descomponerDescripcion(tamCad, sname);
                    for(int ii = 0; ii < numCadenas; ii++)
                    {
                        cb.showTextAligned(0, arregloCadenas[ii], px, cc, 0.0F);
                        cc -= 8;
                    }

                    estableceCoordenadas(serie, idempresa, "26", sucursal);
                    cb.showTextAligned(0, srfc, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "27", sucursal);
                    if(snoInterior == null || snoInterior.equals("0"))
                        snoInterior = "";
                    else
                        snoInterior = snoInterior;
                    if(snoExterior == null || snoExterior.equals("0"))
                        snoExterior = "";
                    cc = py;
                    tamCad = 50;
                    domicilio = (new StringBuilder(String.valueOf(scalle)))
                    .append(" ").append(snoExterior).append(" ").append(snoInterior)
                    .append(" ").append(scolonia).toString();
                    arregloCadenas = descomponerDescripcion(tamCad, domicilio);
                    for(int ii = 0; ii < numCadenas; ii++)
                    {
                        cb.showTextAligned(0, arregloCadenas[ii], px, cc, 0.0F);
                        cc -= 8;
                    }

                    estableceCoordenadas(serie, idempresa, "44", sucursal);
                    cb.showTextAligned(1, scalle, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "45", sucursal);
                    cb.showTextAligned(1, snoExterior, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "46", sucursal);
                    cb.showTextAligned(1, snoInterior, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "47", sucursal);
                    cb.showTextAligned(1, scolonia, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "28", sucursal);
                    cb.showTextAligned(0, smunicipio, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "29", sucursal);
                    cb.showTextAligned(0, sestado, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "30", sucursal);
                    cb.showTextAligned(0, scodigoPostal, px, py, 0.0F);
                    estableceCoordenadas(serie, idempresa, "31", sucursal);
                    cb.showTextAligned(0, spais, px, py, 0.0F);
                }
            } else
            {
                cb.endText();
                doc.close();
                break;
            }
            if(countpag != npag)
            {
                cb.endText();
                doc.newPage();
                if(countpag < pag)
                    doc.add(image);
            }
        } while(++countpag != npag + 1);
        cb.endText();
        doc.close();
        return "";
    }

    public void estableceConexion(ConexionFirebird cnx)
    {
        db = cnx;
    }

    public void estableceCoordenadas(String serie, long idempresa, String campo, long sucursal)
        throws SQLException
    {
        sql = (new StringBuilder("SELECT a.campo AS campo,a.idcampo AS " +
        		"idcampo,b.x AS x,b.y AS y,b.x1 AS x1,b.y1 AS y1 FROM " +
        		"cfd_campo AS a INNER JOIN cfd_impresion AS b ON a.idCampo = " +
        		"b.idImpresion WHERE  b.serie = '")).append(serie)
        		.append("' AND ").append("b.idEmpresa = ").append(idempresa)
        		.append(" AND b.idsucursal = ").append(sucursal).append(" AND ")
        		.append("a.idcampo = ").append(campo).toString();
        rs = db.consulta(sql, true);
        if(rs.next())
        {
            px = rs.getInt(3);
            py = rs.getInt(4);
            px1 = rs.getInt(5);
            py1 = rs.getInt(6);
        } else
        {
            sql = (new StringBuilder("SELECT a.campo AS campo,a.idcampo AS " +
            		"idcampo,b.x AS x,b.y AS y,b.x1 AS x1,b.y1 AS y1 FROM " +
            		"cfd_campo AS a INNER JOIN cfd_impresion AS b ON a." +
            		"idCampo = b.idImpresion WHERE  b.serie = '"))
            		.append(serie).append("' AND ").append("b.idEmpresa = ")
            		.append(idempresa).append(" AND b.idsucursal = ")
            		.append(sucursal).toString();
            rs = db.consulta(sql, true);
            if(rs.next())
            {
                px = -500;
                py = -500;
                px1 = -500;
                py1 = -500;
            } else
            {
                sql = "SELECT a.campo AS campo,a.idcampo AS idcampo,b.x AS " +
                		"x,b.y AS y,b.x1 AS x1,b.y1 AS y1 FROM cfd_campo AS " +
                		"a INNER JOIN cfd_impresion AS b ON a.idCampo = " +
                		"b.idImpresion WHERE  b.idEmpresa = -1";
                rs = db.consulta(sql, true);
                if(rs.next())
                {
                    px = rs.getInt(3);
                    py = rs.getInt(4);
                    px1 = rs.getInt(5);
                    py1 = rs.getInt(6);
                }
            }
        }
    }

    public String changenull(String cadena)
    {
        if(cadena == null)
            cadena = "";
        else
            cadena = cadena;
        return cadena;
    }

    public String[] descomponerCadena(int tamCad, String cadenota)
    {
        String arregloCadenas[] = new String[500];
        int i = 0;
        int ind = 0;
        if(tamCad > 0)
            for(; i < cadenota.length(); i += tamCad)
                if(i + tamCad <= cadenota.length())
                    arregloCadenas[ind++] = cadenota.substring(i, i + tamCad);
                else
                    arregloCadenas[ind++] = cadenota.substring(i, cadenota.length());

        else
            arregloCadenas[ind] = "";
        numCadenas = ind;
        return arregloCadenas;
    }

    public static double roundNum(double num)
        throws Exception
    {
        double valor = 0.0D;
        valor = num;
        valor *= 100D;
        valor /= 100D;
        return valor;
    }

    public String FormatoDecimal(double amount, long empresa)
        throws SQLException
    {
        String monto = null;
        sql = (new StringBuilder("SELECT DECIMALES FROM CFD_CONFIG WHERE IDEMPRESA = ")).append(empresa).toString();
        rs = db.consulta(sql, true);
        if(rs.next())
        {
            DecimalFormat curren = new DecimalFormat((new StringBuilder("#0."))
            		.append("0000000000".substring(0, rs.getInt("DECIMALES")))
            		.toString());
            monto = curren.format(amount);
        }
        return monto;
    }

    public String[] descomponerDescripcion(int tamCad, String cadenota)
    {
        String arregloCadenas[] = new String[500];
        int i = 0;
        int ind = 0;
        if(tamCad > 0)
            for(; i < cadenota.length(); i += tamCad)
                if(i + tamCad <= cadenota.length())
                    arregloCadenas[ind++] = cadenota.substring(i, i + tamCad);
                else
                    arregloCadenas[ind++] = cadenota.substring(i, cadenota.length());

        else
            arregloCadenas[ind] = "";
        numCadenas = ind;
        return arregloCadenas;
    }
}
