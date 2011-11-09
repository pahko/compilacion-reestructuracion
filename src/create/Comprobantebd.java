// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:52:58 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Comprobantebd.java

package create;

import com.codinet.facture.cfdv2.*;
import com.codinet.facture.util.MarshallCFDv2XML;
import java.io.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.com.codinet.err.ComprobanteArchRefException;
import mx.com.codinet.err.ComprobanteStatusException;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

// Referenced classes of package create:
//            ConexionFirebird

public class Comprobantebd
{

    public Comprobantebd()
    {
        cnx1 = new ConexionFirebird();
        rs = null;
        rs_con = null;
    }

    public void estableceConexion(ConexionFirebird cnx)
    {
        db = cnx;
    }

    private String valorEnSTR(String str, int pos)
    {
        String data[] = str.split("\\|");
        return data[pos];
    }

    public String insertaCFD(String co, int idEmpresa, String type, int idsucursal)
        throws JDOMException, IOException, SQLException
    {
        org.jdom.Document doc = null;
        SAXBuilder sxb = new SAXBuilder();
        String tipo = null;
        Comprobante c = MarshallCFDv2XML.unmarshalCfdV2(new FileInputStream(co));
        com.codinet.facture.cfdv2.Comprobante.Emisor emisor = null;
        com.codinet.facture.cfdv2.Comprobante.Receptor receptor = null;
        com.codinet.facture.cfdv2.Comprobante.Conceptos concep = null;
        com.codinet.facture.cfdv2.Comprobante.Impuestos imp = null;
        com.codinet.facture.cfdv2.Comprobante.Impuestos.Retenciones impuestosRetenciones = null;
        com.codinet.facture.cfdv2.Comprobante.Impuestos.Traslados impuestosTraslados = null;
        TUbicacionFiscal ubicacionf = null;
        TUbicacion ubicacion = null;
        ObjectFactory ofactory = new ObjectFactory();
        String folio = c.getFolio();
        String serie = c.getSerie();
        if(serie == null || serie.equals(""))
            serie = "_";
        String fecha = (new StringBuilder()).append(c.getFecha().toString().substring(0, 10)).toString();
        String sello = c.getSello();
        String noAprobacion = (new StringBuilder()).append(c.getNoAprobacion()).toString();
        if(noAprobacion.equals(""))
            noAprobacion = "0";
        String version = c.getVersion();
        String noCertificado = c.getNoCertificado();
        emisor = c.getEmisor();
        String nombre = emisor.getNombre();
        String rfc = emisor.getRfc();
        ubicacionf = emisor.getDomicilioFiscal();
        ubicacion = emisor.getExpedidoEn();
        String calle = ubicacionf.getCalle();
        String codigoPostal = ubicacionf.getCodigoPostal();
        String colonia = ubicacionf.getColonia();
        String estado = ubicacionf.getEstado();
        String municipio = ubicacionf.getMunicipio();
        String noExterior = ubicacionf.getNoExterior();
        String noInterior = ubicacionf.getNoInterior();
        String pais = ubicacionf.getPais();
        String sql = (new StringBuilder("SELECT idEntidad FROM cnet_entidades WHERE nombre = '")).append(nombre).append("' AND ").append("rfc = '").append(rfc).append("' AND ").append("idEmpresa = ").append(idEmpresa).toString();
        ResultSet rs = db.consulta(sql, true);
        long idEntidadEmisor;
        if(rs.next())
        {
            idEntidadEmisor = rs.getLong("idEntidad");
        } else
        {
            idEntidadEmisor = nextID("idEntidad", "cnet_entidades");
            sql = (new StringBuilder("INSERT INTO cnet_entidades(nombre,telefono,rfc,idcc,estado,idEmpresa,idDomicilio,ccontable)  VALUES('")).append(nombre).append("', null ,'").append(rfc).append("' ,").append(1).append(",'A', ").append(idEmpresa).append(" ,").append(1).append(" ,null)").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT * FROM cnet_proveedor where identidad = ")).append(idEntidadEmisor).toString();
        rs = db.consulta(sql, true);
        db.commit();
        if(!rs.next())
        {
            sql = (new StringBuilder("INSERT INTO cnet_proveedor (identidad, razon_social) values (")).append(idEntidadEmisor).append(", '").append(nombre).append("')").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idEstado FROM cnet_estados WHERE descripcion = '")).append(estado).append("'").toString();
        rs = db.consulta(sql, true);
        db.commit();
        long idEstado;
        if(rs.next())
        {
            idEstado = rs.getLong("idEstado");
        } else
        {
            idEstado = nextID("idEstado", "cnet_estados");
            sql = (new StringBuilder("INSERT INTO cnet_estados(descripcion,abreviacion)  VALUES('")).append(estado).append("', '").append(estado.substring(0, 3)).append("')").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idDelegacion FROM cnet_delegacion WHERE nombre = '")).append(municipio).append("' AND idEstado = ").append(idEstado).toString();
        rs = db.consulta(sql, true);
        db.commit();
        long idDelegacion;
        if(rs.next())
        {
            idDelegacion = rs.getLong("idDelegacion");
        } else
        {
            idDelegacion = nextID("idDelegacion", "cnet_delegacion");
            sql = (new StringBuilder("INSERT INTO cnet_delegacion (nombre, idestado) VALUES('")).append(municipio).append("', ").append(idEstado).append(")").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idColonia FROM cnet_colonias WHERE codigo_postal = '")).append(codigoPostal).append("' AND ").append("descripcion = '").append(colonia).append("' AND ").append("idDelegacion = ").append(idDelegacion).toString();
        rs = db.consulta(sql, true);
        db.commit();
        long idColonia;
        if(rs.next())
        {
            idColonia = rs.getLong("idColonia");
        } else
        {
            idColonia = nextID("idColonia", "cnet_colonias");
            sql = (new StringBuilder("INSERT INTO cnet_colonias (codigo_postal, descripcion, iddelegacion) VALUES('")).append(codigoPostal).append("', '").append(colonia).append("', ").append(idDelegacion).append(")").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idDomicilio FROM cnet_domicilios WHERE idEstado = ")).append(idEstado).append(" AND ").append("idColonia = ").append(idColonia).append(" AND ").append("idDelegacion = ").append(idDelegacion).append(" AND ").append("calle = '").append(calle).append("' AND ").append("n_exterior = '").append(noExterior).append("' AND ").append("idEntidad = ").append(idEntidadEmisor).toString();
        if(noInterior != null)
            sql = (new StringBuilder(String.valueOf(sql))).append(" AND n_interior = '").append(noInterior).append("'").toString();
        rs = db.consulta(sql, true);
        db.commit();
        long idDomicilioEmisor;
        if(rs.next())
        {
            idDomicilioEmisor = rs.getLong("idDomicilio");
        } else
        {
            idDomicilioEmisor = nextID("idDomicilio", "cnet_domicilios");
            sql = (new StringBuilder("INSERT INTO cnet_domicilios(IDESTADO, IDCOLONIA, IDENTIDAD, IDDELEGACION,CALLE, N_INTERIOR, N_EXTERIOR) VALUES(")).append(idEstado).append(", ").append(idColonia).append(",").append(idEntidadEmisor).append(",").append(idDelegacion).append(", '").append(calle).append("'").toString();
            if(noInterior != null)
                sql = (new StringBuilder(String.valueOf(sql))).append(", '").append(noInterior).append("'").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(", '0'").toString();
            if(noExterior != null)
                sql = (new StringBuilder(String.valueOf(sql))).append(", '").append(noExterior).append("'").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(", '0'").toString();
            sql = (new StringBuilder(String.valueOf(sql))).append(")").toString();
            db.consulta(sql, false);
            db.commit();
        }
        try
        {
            db.consulta(sql, false);
            db.commit();
        }
        catch(SQLException sqlexception)
        {
            System.out.println("Detalle de conexion!!");
        }
        receptor = c.getReceptor();
        nombre = receptor.getNombre();
        rfc = receptor.getRfc();
        rfc = rfc.replaceAll(" ", "");
        rfc = rfc.replaceAll("-", "");
        ubicacion = receptor.getDomicilio();
        calle = ubicacion.getCalle();
        codigoPostal = ubicacion.getCodigoPostal();
        colonia = ubicacion.getColonia();
        estado = ubicacion.getEstado();
        municipio = ubicacion.getMunicipio();
        noExterior = ubicacion.getNoExterior();
        noInterior = ubicacion.getNoInterior();
        pais = ubicacion.getPais();
        String localidad = ubicacion.getLocalidad();
        tipo = "R";
        sql = (new StringBuilder("SELECT idEntidad FROM cnet_entidades WHERE rfc = '")).append(rfc).append("' AND ").append("idEmpresa = ").append(idEmpresa).toString();
        rs = db.consulta(sql, true);
        db.commit();
        long idEntidadReceptor;
        if(rs.next())
        {
            idEntidadReceptor = rs.getLong("idEntidad");
        } else
        {
            idEntidadReceptor = nextID("idEntidad", "cnet_entidades");
            sql = (new StringBuilder("INSERT INTO cnet_entidades(nombre,telefono,rfc,idcc,estado,idEmpresa,idDomicilio,ccontable) VALUES('")).append(nombre).append("', null ,'").append(rfc).append("' ,").append(1).append(",'A', ").append(idEmpresa).append(" ,").append(1).append(" ,null)").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT * FROM cnet_cliente where identidad = ")).append(idEntidadReceptor).toString();
        rs = db.consulta(sql, true);
        db.commit();
        if(!rs.next())
        {
            sql = (new StringBuilder("INSERT INTO cnet_cliente (identidad, razon_social) values (")).append(idEntidadReceptor).append(", '").append(nombre).append("')").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idEstado FROM cnet_estados WHERE descripcion = '")).append(estado).append("'").toString();
        rs = db.consulta(sql, true);
        db.commit();
        if(rs.next())
        {
            idEstado = rs.getLong("idEstado");
        } else
        {
            idEstado = nextID("idEstado", "cnet_estados");
            sql = (new StringBuilder("INSERT INTO cnet_estados(descripcion,abreviacion) VALUES('")).append(estado).append("', '").append(estado.substring(0, 3)).append("')").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idDelegacion FROM cnet_delegacion WHERE nombre = '")).append(municipio).append("' AND idEstado = ").append(idEstado).toString();
        rs = db.consulta(sql, true);
        db.commit();
        if(rs.next())
        {
            idDelegacion = rs.getLong("idDelegacion");
        } else
        {
            idDelegacion = nextID("idDelegacion", "cnet_delegacion");
            sql = (new StringBuilder("INSERT INTO cnet_delegacion (nombre, idestado) VALUES('")).append(municipio).append("', ").append(idEstado).append(")").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idColonia FROM cnet_colonias WHERE codigo_postal = '")).append(codigoPostal).append("' AND ").append("descripcion = '").append(colonia).append("' AND ").append("idDelegacion = ").append(idDelegacion).toString();
        rs = db.consulta(sql, true);
        db.commit();
        if(rs.next())
        {
            idColonia = rs.getLong("idColonia");
        } else
        {
            idColonia = nextID("idColonia", "cnet_colonias");
            sql = (new StringBuilder("INSERT INTO cnet_colonias (codigo_postal, descripcion, iddelegacion) VALUES('")).append(codigoPostal).append("', '").append(colonia).append("', ").append(idDelegacion).append(")").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("SELECT idDomicilio FROM cnet_domicilios WHERE idEstado = ")).append(idEstado).append(" AND ").append("idColonia = ").append(idColonia).append(" AND ").append("idDelegacion = ").append(idDelegacion).append(" AND ").append("calle = '").append(calle).append("' AND ").append("n_exterior = '").append(noExterior).append("' AND ").append("idEntidad = ").append(idEntidadReceptor).toString();
        if(noInterior != null)
            sql = (new StringBuilder(String.valueOf(sql))).append(" AND n_interior = '").append(noInterior).append("'").toString();
        rs = db.consulta(sql, true);
        db.commit();
        long idDomicilioReceptor;
        if(rs.next())
        {
            idDomicilioReceptor = rs.getLong("idDomicilio");
        } else
        {
            idDomicilioReceptor = nextID("idDomicilio", "cnet_domicilios");
            sql = (new StringBuilder("INSERT INTO cnet_domicilios(IDESTADO, IDCOLONIA, IDENTIDAD, IDDELEGACION, CALLE, N_INTERIOR, N_EXTERIOR) VALUES(")).append(idEstado).append(", ").append(idColonia).append(",").append(idEntidadReceptor).append(",").append(idDelegacion).append(", '").append(calle).append("'").toString();
            if(noInterior != null)
                sql = (new StringBuilder(String.valueOf(sql))).append(", '").append(noInterior).append("'").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(", '0'").toString();
            if(noExterior != null)
                sql = (new StringBuilder(String.valueOf(sql))).append(", '").append(noExterior).append("'").toString();
            else
                sql = (new StringBuilder(String.valueOf(sql))).append(", '0'").toString();
            sql = (new StringBuilder(String.valueOf(sql))).append(")").toString();
            db.consulta(sql, false);
            db.commit();
        }
        sql = (new StringBuilder("INSERT INTO cfd_personas(rfc,tipo,nombre) VALUES ('")).append(rfc).append("','").append(tipo).append("','").append(nombre).append("')").toString();
        try
        {
            db.consulta(sql, false);
            db.commit();
        }
        catch(SQLException sqlexception1)
        {
            System.out.println("Error consulta");
        }
        sql = (new StringBuilder("SELECT * FROM cfd_comprobante WHERE folio = ")).append(folio.trim()).append(" AND ").append("serie = '").append(serie).append("' AND ").append("idEmpresa = ").append(idEmpresa).toString();
        rs = db.consulta(sql, true);
        db.commit();
        if(rs.next())
        {
            try
            {
                sql = (new StringBuilder("UPDATE cfd_comprobante set sello = '")).append(sello).append("', estado = 'T', tipo = '").append(type).append("', vers = '").append(version.trim()).append("' WHERE folio = ").append(folio.trim()).append(" and serie = '").append(serie).append("' and idEmpresa = ").append(idEmpresa).toString();
                rs = db.consulta(sql, false);
                db.commit();
            }
            catch(Exception ex)
            {
                System.out.println("Error actualizacion Sello");
                ex.printStackTrace();
            }
        } else
        {
            sql = (new StringBuilder("INSERT INTO cfd_comprobante(folio,serie,tipo,fecha,noAprobacion,noCertificado,sello,vers,emisor,dom_emisor,receptor,dom_receptor,estado,idEmpresa,sucursal) VALUES (")).append(folio.trim()).append(",'").append(serie.trim()).append("','").append(type).append("'").append(",").append("'").append(fecha).append("'").append(",").append(noAprobacion.trim()).append(",'").append(noCertificado.trim()).append("','").append(sello.trim()).append("','").append(version.trim()).append("',").append(idEntidadEmisor).append(",").append(idDomicilioEmisor).append(",").append(idEntidadReceptor).append(",").append(idDomicilioReceptor).append(",'T'").append(",").append(idEmpresa).append(",").append(idsucursal).append(")").toString();
            try
            {
                db.consulta(sql, false);
                db.commit();
            }
            catch(SQLException sqlexception2) { }
        }
        concep = c.getConceptos();
        List conceptos = concep.getConcepto();
        Iterator li = conceptos.iterator();
        for(long max_concep = nextID("idConcepto", "cfd_conceptos"); li.hasNext(); max_concep++)
        {
            com.codinet.facture.cfdv2.Comprobante.Conceptos.Concepto concepto = (com.codinet.facture.cfdv2.Comprobante.Conceptos.Concepto)li.next();
            String cantidad = (new StringBuilder()).append(concepto.getCantidad()).toString();
            String descripcion = concepto.getDescripcion();
            String importe = (new StringBuilder()).append(concepto.getImporte()).toString();
            String valorUnitario = (new StringBuilder()).append(concepto.getValorUnitario()).toString();
        }

        BigDecimal descuentos = c.getDescuento();
        if(descuentos != null && descuentos.doubleValue() > 0.0D)
        {
            sql = (new StringBuilder("SELECT * FROM cfd_descuentos WHERE folio = ")).append(folio.trim()).append(" AND ").append("serie = '").append(serie).append("' AND ").append("idEmpresa = ").append(idEmpresa).toString();
            rs = db.consulta(sql, true);
            db.commit();
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO cfd_descuentos(iddecuento, folio,importe_total,serie,idEmpresa) VALUES (")).append(nextID("iddecuento", "cfd_descuentos")).append(",").append(folio).append(",").append(descuentos).append(",'").append(serie).append("',").append(idEmpresa).append(")").toString();
                db.consulta(sql, false);
                db.commit();
            }
        }
        impuestosTraslados = ofactory.createComprobanteImpuestosTraslados();
        impuestosRetenciones = ofactory.createComprobanteImpuestosRetenciones();
        try
        {
            imp = c.getImpuestos();
            impuestosTraslados = imp.getTraslados();
            List impuesto_tra = impuestosTraslados.getTraslado();
            Iterator ii = impuesto_tra.iterator();
            long max_impuesto = nextID("idImpuesto", "cfd_impuestos");
            long max_idcatimp = nextID("idCatImpuesto", "cfd_cat_impuestos");
            while(ii.hasNext()) 
            {
                com.codinet.facture.cfdv2.Comprobante.Impuestos.Traslados.Traslado e = (com.codinet.facture.cfdv2.Comprobante.Impuestos.Traslados.Traslado)ii.next();
                String importe_i = (new StringBuilder()).append(e.getImporte()).toString();
                String impuesto_t = e.getImpuesto();
                max_impuesto++;
                sql = (new StringBuilder("SELECT idCNImpuesto FROM cnet_impuesto WHERE (( cnet_impuesto.tipo = '+' )or( cnet_impuesto.tipo = 'T' )) AND descripcion = '")).append(impuesto_t).append("' AND idempresa = ").append(idEmpresa).toString();
                rs = db.consulta(sql, true);
                db.commit();
                long idCNImpuesto;
                if(rs.next())
                {
                    idCNImpuesto = rs.getLong("idCNImpuesto");
                } else
                {
                    sql = (new StringBuilder("INSERT INTO cnet_impuesto (descripcion, x, y, idempresa, estado, tipo, ccontable) VALUES('")).append(impuesto_t).append("', ").append("null,").append(" null, ").append(idEmpresa).append(", 'A', '+', null)").toString();
                    idCNImpuesto = max_idcatimp++;
                    db.consulta(sql, false);
                    db.commit();
                }
                sql = (new StringBuilder("SELECT * FROM cfd_impuestos WHERE folio = ")).append(folio.trim()).append(" AND ").append("serie = '").append(serie).append("' AND ").append("idCNImpuesto = ").append(idCNImpuesto).append(" AND ").append("(( cfd_impuestos.tipo = '+' )or( cfd_impuestos.tipo = 'T' )) AND ").append("idEmpresa = ").append(idEmpresa).toString();
                rs = db.consulta(sql, true);
                db.commit();
                if(rs.next())
                {
                    sql = (new StringBuilder("UPDATE cfd_impuestos set importe = ")).append(importe_i).append("WHERE folio = ").append(folio.trim()).append(" AND ").append("serie = '").append(serie).append("' AND ").append("idCNImpuesto = ").append(idCNImpuesto).append(" AND ").append("importe = ").append(importe_i).append(" AND ").append("(( cfd_impuestos.tipo = '+' )or( cfd_impuestos.tipo = 'T' )) AND ").append("idEmpresa = ").append(idEmpresa).toString();
                    rs = db.consulta(sql, false);
                    db.commit();
                } else
                {
                    sql = (new StringBuilder("INSERT INTO cfd_impuestos(idCNImpuesto,folio,serie,importe,idEmpresa,tipo) VALUES (")).append(idCNImpuesto).append(",").append(folio).append(",'").append(serie).append("',").append(importe_i).append(",").append(idEmpresa).append(",'+')").toString();
                    db.consulta(sql, false);
                    db.commit();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        try
        {
            imp = c.getImpuestos();
            impuestosRetenciones = imp.getRetenciones();
            List impuesto_ret = impuestosRetenciones.getRetencion();
            Iterator iii = impuesto_ret.iterator();
            long max_impuesto = nextID("idImpuesto", "cfd_impuestos");
            long max_idcatimp = nextID("idCatImpuesto", "cfd_cat_impuestos");
            while(iii.hasNext()) 
            {
                com.codinet.facture.cfdv2.Comprobante.Impuestos.Retenciones.Retencion e1 = (com.codinet.facture.cfdv2.Comprobante.Impuestos.Retenciones.Retencion)iii.next();
                String importe_i = (new StringBuilder()).append(e1.getImporte()).toString();
                String impuesto_t = e1.getImpuesto();
                max_impuesto++;
                sql = (new StringBuilder("SELECT idCNImpuesto FROM cnet_impuesto WHERE (( cnet_impuesto.tipo = '-' )or( cnet_impuesto.tipo = 'R' )) AND descripcion = '")).append(impuesto_t).append("' AND idempresa = ").append(idEmpresa).toString();
                rs = db.consulta(sql, true);
                db.commit();
                long idCNImpuesto;
                if(rs.next())
                {
                    idCNImpuesto = rs.getLong("idCNImpuesto");
                } else
                {
                    sql = (new StringBuilder("INSERT INTO cnet_impuesto (descripcion, x, y, idempresa, estado, tipo, ccontable) VALUES('")).append(impuesto_t).append("', ").append("null,").append(" null, ").append(idEmpresa).append(", 'A', '-', null )").toString();
                    idCNImpuesto = max_idcatimp++;
                    db.consulta(sql, false);
                    db.commit();
                }
                sql = (new StringBuilder("SELECT * FROM cfd_impuestos WHERE folio = ")).append(folio.trim()).append(" AND ").append("serie = '").append(serie).append("' AND ").append("idCNImpuesto = ").append(idCNImpuesto).append(" AND ").append("(( cfd_impuestos.tipo = '-' )or( cfd_impuestos.tipo = 'R' )) AND ").append("idEmpresa = ").append(idEmpresa).toString();
                rs = db.consulta(sql, true);
                db.commit();
                if(rs.next())
                {
                    sql = (new StringBuilder("UPDATE cfd_impuestos set importe = ")).append(importe_i).append("WHERE folio = ").append(folio.trim()).append(" AND ").append("serie = '").append(serie).append("' AND ").append("idCNImpuesto = ").append(idCNImpuesto).append(" AND ").append("importe = ").append(importe_i).append(" AND ").append("(( cfd_impuestos.tipo = '-' )or( cfd_impuestos.tipo = 'R' )) AND ").append("idEmpresa = ").append(idEmpresa).toString();
                    rs = db.consulta(sql, false);
                    db.commit();
                } else
                {
                    sql = (new StringBuilder("INSERT INTO cfd_impuestos(idCNImpuesto,folio,serie,importe,idEmpresa,tipo) VALUES (")).append(idCNImpuesto).append(",").append(folio).append(",'").append(serie).append("',").append(importe_i).append(",").append(idEmpresa).append(",'-')").toString();
                    db.consulta(sql, false);
                    db.commit();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        db.libera(rs);
        return "";
    }

    String GeneraNombreArchivoReporte(String fini)
    {
        String name = "1FIL870928PM4082011";
        String mesanio = "";
        int ind = fini.indexOf("/");
        mesanio = fini.substring(0, ind).length() >= 2 ? fini.substring(0, ind) : (new StringBuilder()).append("0").append(fini.substring(0, ind)).toString();
        fini = fini.substring(ind + 1);
        ind = fini.indexOf("/");
        fini = fini.substring(ind + 1);
        mesanio = (new StringBuilder()).append(mesanio).append(fini.substring(0, 4)).toString();
        try
        {
            String sql = "Select RFC from CNET_ENTIDADES where identidad = 1";
            rs1 = cnx1.consulta(sql, true);
            if(rs1.next())
                name = (new StringBuilder()).append("1").append(rs1.getString("RFC")).toString();
        }
        catch(SQLException ex)
        {
            Logger.getLogger(create/Comprobantebd.getName()).log(Level.SEVERE, null, ex);
        }
        return (new StringBuilder()).append(name).append(mesanio).append(".txt").toString();
    }

    double TotalDescuento(String serie, int folio)
    {
        double totaldesc = 0.0D;
        String sql = "";
        try
        {
            sql = (new StringBuilder()).append("select sum(importe_total) as tot  from CFD_DESCUENTOS  where serie = '").append(serie).append("' and ").append(" folio =").append(folio).toString();
            rs1 = cnx1.consulta(sql, true);
            if(rs1.next())
                totaldesc = rs1.getDouble("tot");
        }
        catch(SQLException ex)
        {
            Logger.getLogger(create/Comprobantebd.getName()).log(Level.SEVERE, null, ex);
        }
        return totaldesc;
    }

    public void reporteMensual(String dir, String fini, String ffin)
        throws SQLException, IOException, ClassNotFoundException
    {
        DecimalFormat df = new DecimalFormat("0.00");
        SimpleDateFormat sdf_rep = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf_arg = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat agno = new SimpleDateFormat("yyyy");
        ConexionFirebird _tmp = cnx1;
        ConexionFirebird.conectarFirebird();
        dir = (new StringBuilder()).append(dir).append(GeneraNombreArchivoReporte(fini)).toString();
        FileWriter fw = new FileWriter(dir, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        String sql = (new StringBuilder()).append("select ent.rfc as receptor, CASE WHEN com.serie = '_' THEN '' ELSE com.serie END AS serie, com.folio, com.noaprobacion, com.fecha, com.estado, com.TIPO, con.tot as totConceptos, imp.tot as totImpuestos from cfd_comprobante com inner join( select folio, serie, sum(importe) as tot from cfd_conceptos group by serie, folio order by serie, folio ) as con on com.serie = con.serie and com.folio = con.folio inner join( select folio, serie, sum(importe) as tot from cfd_impuestos group by serie, folio order by serie, folio ) as imp on com.serie = imp.serie and com.folio = imp.folio inner join( select identidad, rfc from CNET_ENTIDADES order by identidad )as ent on com.receptor = ent.identidad where com.fecha between CAST('").append(fini).append("' as TIMESTAMP )").append(" AND CAST('").append(ffin).append("' as timestamp)").append(" order by com.emisor, com.serie, com.folio").toString();
        String str_start = "";
        String str_end = "";
        for(rs = db.consulta(sql, true); rs.next(); pw.println((new StringBuilder(String.valueOf(str_start))).append(str_end).toString()))
        {
            String status = rs.getString("estado");
            String noAprobacion = rs.getString("noAprobacion");
            switch(status.charAt(0))
            {
            case 88: // 'X'
                status = "0";
                break;

            default:
                status = "1";
                break;
            }
            str_start = (new StringBuilder("|")).append(rs.getString("receptor")).append("|").append(rs.getString("serie")).append("|").append(rs.getInt("folio")).toString();
            java.sql.Date fechaComprobante = rs.getDate("fecha");
            double totConceptos = rs.getDouble("totConceptos");
            double totImpuestos = rs.getDouble("totImpuestos");
            double totdesc = TotalDescuento(rs.getString("serie"), rs.getInt("folio"));
            totConceptos -= totdesc;
            str_end = (new StringBuilder("|")).append(sdf_rep.format(fechaComprobante)).append("|").append(df.format(totConceptos)).append("|").append(df.format(totImpuestos)).append("|").append(status).append("|").toString();
            if(rs.getString("TIPO").equals("I"))
                str_end = (new StringBuilder()).append(str_end).append("I||||").toString();
            else
                str_end = (new StringBuilder()).append(str_end).append("E||||").toString();
            sql = (new StringBuilder("SELECT fechaSolicitud FROM cfd_folios WHERE noAprovacion = ")).append(noAprobacion).toString();
            ResultSet rst = db.consulta(sql, true);
            if(rst.next())
                str_start = (new StringBuilder(String.valueOf(str_start))).append("|").append(agno.format(rst.getDate("fechaSolicitud"))).append(noAprobacion).toString();
            else
                str_start = (new StringBuilder(String.valueOf(str_start))).append("|1900").append(noAprobacion).toString();
            db.libera(rst);
        }

        str_start = "";
        str_end = "";
        db.libera(rs);
        sql = (new StringBuilder()).append("select ent.rfc as receptor, CASE WHEN com.serie = '_' THEN '' ELSE com.serie END AS serie, com.folio, com.noaprobacion, can.fecha, com.estado as status, com.TIPO, con.tot as totConceptos, imp.tot as totImpuestos from cfd_comprobante com inner join( select folio, serie, sum(importe) as tot from cfd_conceptos group by serie, folio order by serie, folio ) as con on com.serie = con.serie and com.folio = con.folio inner join( select folio, serie, sum(importe) as tot from cfd_impuestos group by serie, folio order by serie, folio ) as imp on com.serie = imp.serie and com.folio = imp.folio inner join( select identidad, rfc from CNET_ENTIDADES order by identidad )as ent on com.receptor = ent.identidad inner join cfd_cancelacion can on com.folio = can.folio and com.serie = can.serie where can.fecha between CAST('").append(fini).append("' as TIMESTAMP )").append(" AND CAST('").append(ffin).append("' as timestamp)").append(" order by com.emisor, com.serie, com.folio").toString();
        for(rs = db.consulta(sql, true); rs.next(); pw.println((new StringBuilder(String.valueOf(str_start))).append(str_end).toString()))
        {
            String status = rs.getString("status");
            if(status.charAt(0) != 'X')
                continue;
            String noAprobacion = rs.getString("noAprobacion");
            switch(status.charAt(0))
            {
            case 88: // 'X'
                status = "0";
                break;

            default:
                status = "1";
                break;
            }
            str_start = (new StringBuilder("|")).append(rs.getString("receptor")).append("|").append(rs.getString("serie")).append("|").append(rs.getInt("folio")).toString();
            java.sql.Date fechaComprobante = rs.getDate("fecha");
            double totConceptos = rs.getDouble("totConceptos");
            double totImpuestos = rs.getDouble("totImpuestos");
            double totdesc = TotalDescuento(rs.getString("serie"), rs.getInt("folio"));
            totConceptos -= totdesc;
            str_end = (new StringBuilder("|")).append(sdf_rep.format(fechaComprobante)).append("|").append(df.format(totConceptos)).append("|").append(df.format(totImpuestos)).append("|").append(status).append("|").toString();
            if(rs.getString("TIPO").equals("I"))
                str_end = (new StringBuilder()).append(str_end).append("I||||").toString();
            else
                str_end = (new StringBuilder()).append(str_end).append("E||||").toString();
            sql = (new StringBuilder("SELECT fechaSolicitud FROM cfd_folios WHERE noAprovacion = ")).append(noAprobacion).toString();
            ResultSet rst = db.consulta(sql, true);
            if(rst.next())
                str_start = (new StringBuilder(String.valueOf(str_start))).append("|").append(agno.format(rst.getDate("fechaSolicitud"))).append(noAprobacion).toString();
            else
                str_start = (new StringBuilder(String.valueOf(str_start))).append("|1900").append(noAprobacion).toString();
            db.libera(rst);
        }

        db.libera(rs);
        pw.close();
        bw.close();
        fw.close();
        cnx1.finalizar();
    }

    public void reporteMensualV2(String dir, String fini, String ffin)
        throws SQLException, IOException, ClassNotFoundException
    {
        DecimalFormat df = new DecimalFormat("0.00");
        SimpleDateFormat sdf_rep = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf_arg = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat agno = new SimpleDateFormat("yyyy");
        ConexionFirebird _tmp = cnx1;
        ConexionFirebird.conectarFirebird();
        dir = (new StringBuilder()).append(dir).append(GeneraNombreArchivoReporte(fini)).toString();
        FileWriter fw = new FileWriter(dir, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);
        String sql = (new StringBuilder()).append("select ent.rfc as receptor, CASE WHEN com.serie = '_' THEN '' ELSE com.serie END AS serie, com.folio, com.noaprobacion, com.fecha, com.estado, com.TIPO, con.tot as totConceptos, imp.tot as totImpuestos from cfd_comprobante com inner join( select folio, serie, sum(importe) as tot from cfd_conceptos group by serie, folio order by serie, folio ) as con on com.serie = con.serie and com.folio = con.folio inner join( select folio, serie, sum(importe) as tot from cfd_impuestos group by serie, folio order by serie, folio ) as imp on com.serie = imp.serie and com.folio = imp.folio inner join( select identidad, rfc from CNET_ENTIDADES order by identidad )as ent on com.receptor = ent.identidad where com.fecha between CAST('").append(fini).append("' as TIMESTAMP )").append(" AND CAST('").append(ffin).append("' as timestamp)").append(" order by com.emisor, com.serie, com.folio").toString();
        String str_start = "";
        String str_end = "";
        for(rs = db.consulta(sql, true); rs.next(); pw.println((new StringBuilder(String.valueOf(str_start))).append(str_end).toString()))
        {
            String status = rs.getString("estado");
            String noAprobacion = rs.getString("noAprobacion");
            switch(status.charAt(0))
            {
            case 88: // 'X'
                status = "0";
                break;

            default:
                status = "1";
                break;
            }
            str_start = (new StringBuilder("|")).append(rs.getString("receptor")).append("|").append(rs.getString("serie")).append("|").append(rs.getInt("folio")).toString();
            java.sql.Date fechaComprobante = rs.getDate("fecha");
            double totConceptos = rs.getDouble("totConceptos");
            double totImpuestos = rs.getDouble("totImpuestos");
            double totdesc = TotalDescuento(rs.getString("serie"), rs.getInt("folio"));
            totConceptos -= totdesc;
            str_end = (new StringBuilder("|")).append(sdf_rep.format(fechaComprobante)).append("|").append(df.format(totConceptos)).append("|").append(df.format(totImpuestos)).append("|").append(status).append("|").toString();
            if(rs.getString("TIPO").equals("I"))
                str_end = (new StringBuilder()).append(str_end).append("I||||").toString();
            else
                str_end = (new StringBuilder()).append(str_end).append("E||||").toString();
            sql = (new StringBuilder("SELECT fechaSolicitud FROM cfd_folios WHERE noAprovacion = ")).append(noAprobacion).toString();
            ResultSet rst = db.consulta(sql, true);
            if(rst.next())
                str_start = (new StringBuilder(String.valueOf(str_start))).append("|").append(agno.format(rst.getDate("fechaSolicitud"))).append(noAprobacion).toString();
            else
                str_start = (new StringBuilder(String.valueOf(str_start))).append("|1900").append(noAprobacion).toString();
            db.libera(rst);
        }

        str_start = "";
        str_end = "";
        db.libera(rs);
        pw.close();
        bw.close();
        fw.close();
        cnx1.finalizar();
    }

    public void setPubKeyDir(String dir)
        throws ComprobanteArchRefException, SQLException
    {
        File f = new File(dir);
        if(!f.exists())
            throw new ComprobanteArchRefException((new StringBuilder("La direccion [")).append(dir).append("] no es valida").toString());
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(dirPubKey) VALUES('")).append(dir).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET dirPubKey = '")).append(dir).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public void setPrivKeyDir(String dir)
        throws ComprobanteArchRefException, SQLException
    {
        File f = new File(dir);
        if(!f.exists())
            throw new ComprobanteArchRefException((new StringBuilder("La direccion [")).append(dir).append("] no es valida").toString());
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(dirPrivKey) VALUES('")).append(dir).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET dirPrivKey = '")).append(dir).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public void setRfcGeneral(String rfc)
        throws SQLException
    {
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(rfcGeneral) VALUES('")).append(rfc).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET rfcGeneral = '")).append(rfc).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public void setImgFactura(String dir)
        throws SQLException
    {
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(imgFactura) VALUES('")).append(dir).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET imgFactura = '")).append(dir).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public void setPwdPrivKey(String pwd)
        throws SQLException
    {
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(direccion) VALUES('")).append(pwd).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET direccion = '")).append(pwd).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public void setDirContenedor(String dir)
        throws SQLException
    {
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(dirContenedor) VALUES('")).append(dir).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET dirContenedor = '")).append(dir).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public void setSerieCertificado(String ser)
        throws SQLException
    {
        String sql = "SELECT MAX(id) as m_id FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        long max_config = rs.getLong("m_id");
        if(max_config <= 0L)
            sql = (new StringBuilder("INSERT INTO cfd_config(serieCertificado) VALUES('")).append(ser).append("')").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_config SET serieCertificado = '")).append(ser).append("'").toString();
        db.consulta(sql, false);
        db.libera(rs);
    }

    public String getPubKeyDir()
        throws SQLException
    {
        String sql = "SELECT dirPubKey FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String dir = rs.getString("dirPubKey");
        return dir;
    }

    public String getPrivKeyDir()
        throws SQLException
    {
        String sql = "SELECT dirPrivKey FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String dir = rs.getString("dirPrivKey");
        return dir;
    }

    public String getRfcGeneral()
        throws SQLException
    {
        String sql = "SELECT rfcGeneral FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String rfc = rs.getString("rfcGeneral");
        return rfc;
    }

    public String getImgFactura()
        throws SQLException
    {
        String sql = "SELECT imgFactura FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String rfc = rs.getString("imgFactura");
        return rfc;
    }

    public String getPwdPrivKey()
        throws SQLException
    {
        String sql = "SELECT direccion FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String rfc = rs.getString("direccion");
        return rfc;
    }

    public String getDirContenedor()
        throws SQLException
    {
        String sql = "SELECT dirContenedor FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String rfc = rs.getString("dirContenedor");
        return rfc;
    }

    public String getSerieCertificado()
        throws SQLException
    {
        String sql = "SELECT serieCertificado FROM cfd_config";
        rs = db.consulta(sql, true);
        rs.next();
        String rfc = rs.getString("serieCertificado");
        return rfc;
    }

    public String getStatus(int folio, String serie)
        throws SQLException
    {
        String sql;
        if(serie.trim().equals(""))
            sql = (new StringBuilder("SELECT status FROM cfd_comprobante WHERE folio=")).append(folio).append(" and (serie='null' or serie='' or serie is null)").toString();
        else
            sql = (new StringBuilder("SELECT status FROM cfd_comprobante WHERE folio=")).append(folio).append(" and serie='").append(serie).append("'").toString();
        rs = db.consulta(sql, true);
        String status;
        if(rs.next())
            status = rs.getString("status");
        else
            status = "I";
        return status;
    }

    public void setStatus(int folio, String serie, String status)
        throws SQLException, ComprobanteStatusException
    {
        String sql = null;
        SimpleDateFormat sdf_arg = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String estado = getStatus(folio, serie);
        if(estado.equals("X"))
            throw new ComprobanteStatusException("La factura ya esta cancelada\nNo se puede cambiar el estado");
        if(serie.trim().equals(""))
            sql = (new StringBuilder("UPDATE cfd_comprobante set status='")).append(status).append("'  WHERE folio=").append(folio).append(" and (serie='' or serie is null)").toString();
        else
            sql = (new StringBuilder("UPDATE cfd_comprobante set status='")).append(status).append("' WHERE folio=").append(folio).append(" and serie='").append(serie).append("'").toString();
        db.consulta(sql, false);
        if(status.equals("X"))
        {
            sql = (new StringBuilder("INSERT INTO cfd_cancelaciones VALUES(")).append(folio).append(", '").append(serie).append("', '").append(sdf_arg.format(cal.getTime())).append("')").toString();
            db.consulta(sql, false);
        }
    }

    public void eliminaCFD(int folio, String serie)
        throws SQLException, ComprobanteStatusException
    {
        String sql = null;
        String estado = getStatus(folio, serie);
        if(estado.equals("X") || estado.equals("R"))
            throw new ComprobanteStatusException("La factura no puede ser eliminada\nNo se puede cambiar el estado");
        if(serie.trim().equals(""))
            sql = (new StringBuilder("DELETE FROM cfd_comprobante  WHERE folio=")).append(folio).append(" and (serie='' or serie is null)").toString();
        else
            sql = (new StringBuilder("DELETE FROM cfd_comprobante  WHERE folio=")).append(folio).append(" and serie='").append(serie).append("'").toString();
        db.consulta(sql, false);
        if(serie.trim().equals(""))
            sql = (new StringBuilder("DELETE FROM cfd_impuestos  WHERE folio=")).append(folio).append(" and (serie='' or serie is null)").toString();
        else
            sql = (new StringBuilder("DELETE FROM cfd_impuestos  WHERE folio=")).append(folio).append(" and serie='").append(serie).append("'").toString();
        db.consulta(sql, false);
        if(serie.trim().equals(""))
            sql = (new StringBuilder("DELETE FROM cfd_conceptos  WHERE folio=")).append(folio).append(" and (serie='' or serie is null)").toString();
        else
            sql = (new StringBuilder("DELETE FROM cfd_conceptos  WHERE folio=")).append(folio).append(" and serie='").append(serie).append("'").toString();
        db.consulta(sql, false);
    }

    public String getAprobacion(String folio, String serie)
        throws SQLException
    {
        if(serie == null)
            serie = "";
        String sql;
        if(!serie.trim().equals(""))
            sql = (new StringBuilder("SELECT noAprovacion FROM cfd_folios WHERE folioInicial <= ")).append(folio).append(" and folioFinal >= ").append(folio).append(" and serie = '").append(serie).append("'").toString();
        else
            sql = (new StringBuilder("SELECT noAprovacion FROM cfd_folios WHERE folioInicial <= ")).append(folio).append(" and folioFinal >= ").append(folio).append(" and (serie IS NULL or serie = '").append(serie).append("')").toString();
        rs = db.consulta(sql, true);
        rs.next();
        String aprobacion = rs.getString("noAprovacion");
        return aprobacion;
    }

    private long nextID(String campoID, String tabla)
        throws SQLException
    {
        String sql = (new StringBuilder("SELECT MAX(")).append(campoID).append(") as m_id FROM ").append(tabla).toString();
        ResultSet rs = db.consulta(sql, true);
        rs.next();
        long id = rs.getLong("m_id");
        if(id == 0L)
            id = 1L;
        else
            id++;
        return id;
    }

    ConexionFirebird cnx1;
    ResultSet rs1;
    ConexionFirebird db;
    ResultSet rs;
    ResultSet rs_con;
}
