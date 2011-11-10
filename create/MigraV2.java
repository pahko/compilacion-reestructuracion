// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MigraV2.java

package create;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.derby.client.am.DisconnectException;
import org.apache.derby.client.am.ResultSet;

// Referenced classes of package create:
//            ConexionFirebird, ProgressBarSample, Mensajes, ConexionDerby

public class MigraV2
{

    public void CrearProgreso() {
        ps.CreaFrame("Migrando base de datos V2.0");
        ps.CreaProgreso();
        ps.SetBorde("Iniciando proceso");
        ps.CreaBorde();
    }

    public MigraV2() throws SQLException, FileNotFoundException, IOException {
        id_descdetalle = 0L;
        id_descgrupo = 0L;
        idEntidad = 0L;
        idEntidad_drb = 0L;
        idEmpresa_drb = 0L;
        idEmpresa = 0L;
        idEstado = 0L;
        idColonia = 0L;
        idDelegacion = 0L;
        idDomicilio = 0L;
        idSucursal = 0L;
        idSucursal_drb = 0L;
        idpais = 0L;
        nombre = null;
        rfc = null;
        nombre_comercial = null;
        abreviado = null;
        tipo_sucursal = null;
        estado = null;
        municipio = null;
        colonia = null;
        codigoPostal = null;
        calle = null;
        noExterior = null;
        noInterior = null;
        tipo = null;
        nombre_sucursal = null;
        sql_frb = null;
        sql_frb_2 = null;
        sql_drb = null;
        sql_drb_2 = null;
        sql_drb_3 = null;
        cnx_frb = new ConexionFirebird();
        ps = new ProgressBarSample();
        m = new Mensajes();
        CrearProgreso();
        ps.SetBorde("Creando conexiones");
        try
        {
            ps.SetBorde("Conectando Firebird");
            ConexionFirebird.conectarFirebird();
            ps.SetBorde("Conectando Derby");
            cnx_drb.conectarDerby();
        }
        catch(ClassNotFoundException e1)
        {
            ps.CerrarProgreso();
        }
        catch(DisconnectException e)
        {
            ps.CerrarProgreso();
        }
        catch(Exception e)
        {
            ps.CerrarProgreso();
        }
    }

    public void Entidades()
        throws SQLException
    {
        ps.SetBorde("Migrando Entidades");
        sql_drb = " SELECT cnet_entidades.identidad, cnet_entidades.nombre, cnet_entidades.telefono, cnet_entidades.rfc, cnet_entidades.idcc, cnet_entidades.rfc, cnet_entidades.estado, cnet_entidades.idempresa, cnet_entidades.iddepto, cnet_entidades.iddomicilio, cnet_entidades.ccontable, cnet_entidades.idcondicionpago, cnet_entidades.limite_credito FROM cnet_entidades";
        for(rs_drb_entidades = cnx_drb.consulta(sql_drb, true); rs_drb_entidades.next();)
        {
            idEntidad_drb = rs_drb_entidades.getLong("identidad");
            idEmpresa_drb = rs_drb_entidades.getLong("idempresa");
            sql_frb = (new StringBuilder("SELECT idEntidad FROM cnet_entidades WHERE nombre = '")).append(rs_drb_entidades.getString("NOMBRE")).append("' AND ").append("rfc = '").append(rs_drb_entidades.getString("RFC")).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(rs_frb.next())
            {
                idEntidad = rs_frb.getLong("IDENTIDAD");
                nombre = rs_drb_entidades.getString("NOMBRE").trim();
                rfc = rs_drb_entidades.getString("RFC").trim();
            } else
            {
                nombre = rs_drb_entidades.getString("NOMBRE").trim();
                rfc = rs_drb_entidades.getString("RFC").trim();
                idEntidad = nextID("idEntidad", "cnet_entidades", "CNET_ENTIDADES_GEN");
                sql_frb = (new StringBuilder("INSERT INTO cnet_entidades(nombre,telefono,rfc,idcc,estado,idDomicilio,ccontable) VALUES('")).append(nombre).append("', null ,'").append(rfc).append("' ,").append(1).append(",'A',").append(1).append(" ,null)").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
            sql_drb_2 = (new StringBuilder("SELECT * FROM CNET_EMPRESA WHERE IDEMPRESA = ")).append(rs_drb_entidades.getLong("idempresa")).toString();
            rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
            if(rs_drb_2.next())
            {
                nombre_comercial = rs_drb_2.getString("NOMBRECOMERCIAL");
                ps.SetBorde((new StringBuilder("Migrando Empresa")).append(nombre_comercial).toString());
                sql_frb = (new StringBuilder("SELECT IDEMPRESA, IDENTIDAD, NOMBRECOMERCIAL, SMTPSERVER, SMTPUSERID, SMTPPASSWORD, SMTPPORT, SMTPAUTH, MAILTYPE, NRECORD FROM CNET_EMPRESA WHERE NOMBRECOMERCIAL = '")).append(nombre_comercial).append("'").toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(rs_frb.next())
                {
                    idEmpresa = rs_frb.getLong("idempresa");
                } else
                {
                    idEmpresa = nextID("idempresa", "CNET_EMPRESA", "CNET_EMPRESA_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CNET_EMPRESA(idempresa,identidad, NOMBRECOMERCIAL) VALUES(")).append(idEmpresa).append(",").append(idEntidad).append(",'").append(nombre_comercial).append("')").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                }
            }
            try
            {
                sql_frb = (new StringBuilder("UPDATE cnet_entidades SET idempresa = ")).append(idEmpresa).append(" WHERE IDENTIDAD = ").append(idEntidad).toString();
                rs_frb = cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            sql_drb_3 = (new StringBuilder("SELECT * FROM CNET_SUCURSAL WHERE IDEMPRESA = ")).append(rs_drb_entidades.getLong("idempresa")).toString();
            rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
            while(rs_drb_3.next()) 
            {
                idSucursal_drb = rs_drb_3.getLong("idsucursal");
                nombre_sucursal = rs_drb_3.getString("descripcion");
                ps.SetBorde((new StringBuilder("Migrando Sucursal")).append(nombre_sucursal).toString());
                tipo_sucursal = rs_drb_3.getString("tipo");
                sql_frb = (new StringBuilder("SELECT * FROM CNET_SUCURSAL WHERE DESCRIPCION = '")).append(nombre_sucursal).append("' AND IDEMPRESA = ").append(idEmpresa).toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(rs_frb.next())
                {
                    idSucursal = rs_frb.getLong("idsucursal");
                } else
                {
                    idSucursal = nextID("idsucursal", "CNET_SUCURSAL", "CNET_SUCURSAL_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CNET_SUCURSAL(idsucursal,idempresa,identidad,descripcion,estado,tipo) VALUES(")).append(idSucursal).append(",").append(idEmpresa).append(",").append(idEntidad).append(",'").append(nombre_sucursal).append("','A','").append(tipo_sucursal).append("')").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                }
                Domicilios(idEntidad_drb, idEntidad);
                sql_drb_2 = (new StringBuilder("SELECT * FROM cnet_proveedor where identidad = ")).append(idEntidad_drb).toString();
                rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                if(rs_drb_2.next())
                {
                    ps.SetBorde("Migrando Datos de Proveedor");
                    sql_frb_2 = (new StringBuilder("SELECT * FROM cnet_proveedor where identidad = ")).append(idEntidad).toString();
                    rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
                    if(!rs_frb_2.next())
                    {
                        tipo = "P";
                        sql_frb = (new StringBuilder("INSERT INTO cnet_proveedor (identidad, razon_social) values (")).append(idEntidad).append(", '").append(nombre).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    }
                    Configuracion(idEmpresa_drb, idSucursal_drb, idEmpresa, idSucursal, idEntidad_drb, idEntidad);
                    Impuestos(idEmpresa_drb, idSucursal_drb, idEmpresa, idSucursal);
                    Articulos(idEmpresa_drb, idSucursal_drb, idEmpresa, idSucursal);
                    Comprobates(idEmpresa_drb, idSucursal_drb, idEmpresa, idSucursal);
                }
                sql_drb_2 = (new StringBuilder("SELECT * FROM cnet_cliente where identidad = ")).append(idEntidad_drb).toString();
                rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                if(rs_drb_2.next())
                {
                    ps.SetBorde("Migrando Datos de Cliente");
                    sql_frb_2 = (new StringBuilder("SELECT * FROM cnet_cliente where identidad = ")).append(idEntidad).toString();
                    rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
                    if(!rs_frb_2.next())
                    {
                        tipo = "R";
                        sql_frb = (new StringBuilder("INSERT INTO cnet_cliente (identidad, razon_social) values (")).append(idEntidad).append(", '").append(nombre).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    }
                }
            }
        }

    }

    public void Domicilios(long l, long l1)
        throws SQLException
    {
    }

    private long nextID(String campoID, String tabla, String generador)
        throws SQLException
    {
        sql_frb = (new StringBuilder("SELECT MAX(")).append(campoID).append(") as m_id FROM ").append(tabla).toString();
        rs_frb = cnx_frb.consulta(sql_frb, true);
        rs_frb.next();
        long id = rs_frb.getLong("m_id");
        try
        {
            sql_frb = (new StringBuilder("ALTER SEQUENCE CNET_ENTIDADES_GEN RESTART WITH ")).append(id).toString();
            rs_frb = cnx_frb.consulta(sql_frb, false);
            cnx_frb.commit();
        }
        catch(SQLException e)
        {
            System.out.println("No se puede afectar los indices");
        }
        if(id == 0L)
            id = 1L;
        else
            id++;
        return id;
    }

    public long FindEntidad(long entidad_drb)
        throws SQLException
    {
        ps.SetBorde("Identificando Entidad");
        sql_drb = (new StringBuilder(" SELECT cnet_entidades.identidad, cnet_entidades.nombre, cnet_entidades.telefono, cnet_entidades.rfc, cnet_entidades.idcc, cnet_entidades.rfc, cnet_entidades.estado, cnet_entidades.idempresa, cnet_entidades.iddepto, cnet_entidades.iddomicilio, cnet_entidades.ccontable, cnet_entidades.idcondicionpago, cnet_entidades.limite_credito FROM cnet_entidades WHERE IDENTIDAD = ")).append(entidad_drb).toString();
        rs_drb_entidades = cnx_drb.consulta(sql_drb, true);
        if(rs_drb_entidades.next())
        {
            idEntidad_drb = rs_drb_entidades.getLong("identidad");
            idEmpresa_drb = rs_drb_entidades.getLong("idempresa");
            sql_frb = (new StringBuilder("SELECT idEntidad FROM cnet_entidades WHERE nombre = '")).append(rs_drb_entidades.getString("NOMBRE")).append("' AND ").append("rfc = '").append(rs_drb_entidades.getString("RFC")).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(rs_frb.next())
            {
                idEntidad = rs_frb.getLong("IDENTIDAD");
                nombre = rs_drb_entidades.getString("NOMBRE").trim();
                rfc = rs_drb_entidades.getString("RFC").trim();
            } else
            {
                nombre = rs_drb_entidades.getString("NOMBRE").trim();
                rfc = rs_drb_entidades.getString("RFC").trim();
                idEntidad = nextID("idEntidad", "cnet_entidades", "CNET_ENTIDADES_GEN");
                sql_frb = (new StringBuilder("INSERT INTO cnet_entidades(nombre,telefono,rfc,idcc,estado,idDomicilio,ccontable) VALUES('")).append(nombre).append("', null ,'").append(rfc).append("' ,").append(1).append(",'A',").append(1).append(" ,null)").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
            sql_drb_2 = (new StringBuilder("SELECT * FROM CNET_EMPRESA WHERE IDEMPRESA = ")).append(rs_drb_entidades.getLong("idempresa")).toString();
            rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
            if(rs_drb_2.next())
            {
                nombre_comercial = rs_drb_2.getString("NOMBRECOMERCIAL");
                ps.SetBorde((new StringBuilder("Migrando Empresa")).append(nombre_comercial).toString());
                sql_frb = (new StringBuilder("SELECT IDEMPRESA, IDENTIDAD, NOMBRECOMERCIAL, SMTPSERVER, SMTPUSERID, SMTPPASSWORD, SMTPPORT, SMTPAUTH, MAILTYPE, NRECORD FROM CNET_EMPRESA WHERE NOMBRECOMERCIAL = '")).append(nombre_comercial).append("'").toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(rs_frb.next())
                {
                    idEmpresa = rs_frb.getLong("idempresa");
                } else
                {
                    idEmpresa = nextID("idempresa", "CNET_EMPRESA", "CNET_EMPRESA_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CNET_EMPRESA(idempresa,identidad, NOMBRECOMERCIAL) VALUES(")).append(idEmpresa).append(",").append(idEntidad).append(",'").append(nombre_comercial).append("')").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                }
            }
            try
            {
                sql_frb = (new StringBuilder("UPDATE cnet_entidades SET idempresa = ")).append(idEmpresa).append(" WHERE IDENTIDAD = ").append(idEntidad).toString();
                rs_frb = cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
            sql_drb_3 = (new StringBuilder("SELECT * FROM CNET_SUCURSAL WHERE IDEMPRESA = ")).append(rs_drb_entidades.getLong("idempresa")).toString();
            rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
            do
            {
                if(!rs_drb_3.next())
                    break;
                idSucursal_drb = rs_drb_3.getLong("idsucursal");
                nombre_sucursal = rs_drb_3.getString("descripcion");
                ps.SetBorde((new StringBuilder("Migrando Sucursal")).append(nombre_sucursal).toString());
                tipo_sucursal = rs_drb_3.getString("tipo");
                sql_frb = (new StringBuilder("SELECT * FROM CNET_SUCURSAL WHERE DESCRIPCION = '")).append(nombre_sucursal).append("' AND IDEMPRESA = ").append(idEmpresa).toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(rs_frb.next())
                {
                    idSucursal = rs_frb.getLong("idsucursal");
                } else
                {
                    idSucursal = nextID("idsucursal", "CNET_SUCURSAL", "CNET_SUCURSAL_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CNET_SUCURSAL(idsucursal,idempresa,identidad,descripcion,estado,tipo) VALUES(")).append(idSucursal).append(",").append(idEmpresa).append(",").append(idEntidad).append(",'").append(nombre_sucursal).append("','A','").append(tipo_sucursal).append("')").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                }
                Domicilios(idEntidad_drb, idEntidad);
                sql_drb_2 = (new StringBuilder("SELECT * FROM cnet_proveedor where identidad = ")).append(idEntidad_drb).toString();
                rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                if(rs_drb_2.next())
                {
                    ps.SetBorde("Migrando Datos de Proveedor");
                    sql_frb_2 = (new StringBuilder("SELECT * FROM cnet_proveedor where identidad = ")).append(idEntidad).toString();
                    rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
                    if(!rs_frb_2.next())
                    {
                        tipo = "P";
                        sql_frb = (new StringBuilder("INSERT INTO cnet_proveedor (identidad, razon_social) values (")).append(idEntidad).append(", '").append(nombre).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    }
                }
                sql_drb_2 = (new StringBuilder("SELECT * FROM cnet_cliente where identidad = ")).append(idEntidad_drb).toString();
                rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                if(rs_drb_2.next())
                {
                    ps.SetBorde("Migrando Datos de Cliente");
                    sql_frb_2 = (new StringBuilder("SELECT * FROM cnet_cliente where identidad = ")).append(idEntidad).toString();
                    rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
                    if(!rs_frb_2.next())
                    {
                        tipo = "R";
                        sql_frb = (new StringBuilder("INSERT INTO cnet_cliente (identidad, razon_social) values (")).append(idEntidad).append(", '").append(nombre).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    }
                }
            } while(true);
        }
        return idEntidad;
    }

    public long FindDomicilio(long entidad_drb, long entidad_frb, long domicilio_drb)
        throws SQLException
    {
        ps.SetBorde("Identificando Domiclio");
        sql_drb_2 = (new StringBuilder("SELECT IDDOMICILIO, IDESTADO, IDCOLONIA, IDENTIDAD, IDDELEGACION, CALLE, N_INTERIOR, N_EXTERIOR  FROM CNET_DOMICILIOS WHERE IDENTIDAD = ")).append(entidad_drb).append("AND IDDOMICILIO = ").append(domicilio_drb).toString();
        rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
        calle = rs_drb_2.getString("calle");
        idEstado = rs_drb_2.getLong("idestado");
        idColonia = rs_drb_2.getLong("idcolonia");
        idDelegacion = rs_drb_2.getLong("idDelegacion");
        noExterior = rs_drb_2.getString("identidad");
        noInterior = rs_drb_2.getString("identidad");
        sql_frb = (new StringBuilder("SELECT * FROM CNET_DOMICILIOS WHERE IDENTIDAD = ")).append(entidad_frb).toString();
        rs_frb = cnx_frb.consulta(sql_frb, true);
        if(rs_frb.next())
            idDomicilio = rs_frb.getLong("idDomicilio");
        ps.SetBorde("Migrando Estados");
        sql_drb_3 = (new StringBuilder("SELECT descripcion, ABREVIACION, PAIS FROM cnet_estados WHERE idestado = ")).append(idEstado).toString();
        rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
        if(rs_drb_3.next())
        {
            estado = rs_drb_3.getString("descripcion");
            abreviado = rs_drb_3.getString("ABREVIACION");
            idpais = rs_drb_3.getLong("PAIS");
            ps.SetBorde((new StringBuilder("Estado [ ")).append(estado).append(" ]").toString());
            sql_frb = (new StringBuilder("SELECT idEstado FROM cnet_estados WHERE descripcion = '")).append(estado).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(rs_frb.next())
            {
                idEstado = rs_frb.getLong("idEstado");
            } else
            {
                idEstado = nextID("idEstado", "cnet_estados", "cnet_estados_gen");
                sql_frb = (new StringBuilder("INSERT INTO cnet_estados(descripcion,abreviacion, pais)  VALUES('")).append(estado).append("', '").append(estado.substring(0, 3)).append("',").append(idpais).append(")").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
        }
        ps.SetBorde("Migrando Delegaciones");
        sql_drb_3 = (new StringBuilder("SELECT nombre FROM cnet_delegacion WHERE idDelegacion = ")).append(idDelegacion).toString();
        rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
        if(rs_drb_3.next())
        {
            municipio = rs_drb_3.getString("nombre");
            ps.SetBorde((new StringBuilder("Delegacion [ ")).append(municipio).append(" ]").toString());
            sql_frb = (new StringBuilder("SELECT idDelegacion FROM cnet_delegacion WHERE nombre = '")).append(municipio).append("' AND idEstado = ").append(idEstado).toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(rs_frb.next())
            {
                idDelegacion = rs_frb.getLong("idDelegacion");
            } else
            {
                idDelegacion = nextID("idDelegacion", "cnet_delegacion", "cnet_delegacion_gen");
                sql_frb = (new StringBuilder("INSERT INTO cnet_delegacion (nombre, idestado) VALUES('")).append(municipio).append("', ").append(idEstado).append(")").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
        }
        ps.SetBorde("Migrando Colonias");
        sql_drb_3 = (new StringBuilder("SELECT * FROM cnet_colonias WHERE idcolonia = ")).append(idColonia).toString();
        rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
        if(rs_drb_3.next())
        {
            codigoPostal = rs_drb_3.getString("codigo_postal");
            colonia = rs_drb_3.getString("descripcion");
            ps.SetBorde((new StringBuilder("Colonias [ ")).append(colonia).append(" ] [").append(codigoPostal).append("]").toString());
            sql_frb = (new StringBuilder("SELECT idColonia FROM cnet_colonias WHERE codigo_postal = '")).append(codigoPostal).append("' AND ").append("descripcion = '").append(colonia).append("' AND ").append("idDelegacion = ").append(idDelegacion).toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(rs_frb.next())
            {
                idColonia = rs_frb.getLong("idColonia");
            } else
            {
                idColonia = nextID("idColonia", "cnet_colonias", "cnet_colonias_gen");
                sql_frb = (new StringBuilder("INSERT INTO cnet_colonias (idcolonia, codigo_postal, descripcion, iddelegacion) VALUES(")).append(idColonia).append(",'").append(codigoPostal).append("', '").append(colonia).append("', ").append(idDelegacion).append(")").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
        }
        ps.SetBorde("Nuevo Domicilio");
        sql_frb = (new StringBuilder("SELECT idDomicilio FROM cnet_domicilios WHERE idEstado = ")).append(idEstado).append(" AND ").append("idColonia = ").append(idColonia).append(" AND ").append("idDelegacion = ").append(idDelegacion).append(" AND ").append("calle = '").append(calle).append("' AND ").append("n_exterior = '").append(noExterior).append("' AND ").append("idEntidad = ").append(idEntidad).toString();
        try
        {
            cnx_frb.consulta(sql_frb, false);
            cnx_frb.commit();
        }
        catch(SQLException e)
        {
            m.GetMensaje((new StringBuilder("Persona duplicada. ")).append(rfc).toString());
        }
        return idDomicilio;
    }

    public void FCards()
        throws SQLException
    {
        ps.SetBorde("Migrando FCards");
        sql_drb = "SELECT FOLIO,NSERIE,CANTIDAD,ESTADO FROM CFD_FCARD";
        rs_drb = cnx_drb.consulta(sql_drb, true);
        do
        {
            if(!rs_drb.next())
                break;
            sql_frb = (new StringBuilder(" SELECT * FROM CFD_FCARD WHERE FOLIO = '")).append(rs_drb.getString("FOLIO")).append("'AND NSERIE = '").append(rs_drb.getString("NSERIE")).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(!rs_frb.next())
            {
                sql_frb = (new StringBuilder("INSERT INTO CFD_FCARD (FOLIO, NSERIE, CANTIDAD, ESTADO)VALUES('")).append(rs_drb.getString("FOLIO")).append("','").append(rs_drb.getString("NSERIE")).append("',").append(rs_drb.getString("CANTIDAD")).append(",'").append(rs_drb.getString("ESTADO")).append("')").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
        } while(true);
    }

    public void Campos()
        throws SQLException
    {
        ps.SetBorde("Migrando Campos");
        sql_drb = " SELECT IDCAMPO, CAMPO, ESTADO, GRUPO FROM CFD_CAMPO";
        rs_drb = cnx_drb.consulta(sql_drb, true);
        do
        {
            if(!rs_drb.next())
                break;
            ps.SetBorde((new StringBuilder("Migrando Campos[")).append(rs_drb.getString("CAMPO")).append("]").toString());
            sql_frb = (new StringBuilder(" SELECT * FROM CFD_CAMPO WHERE IDCAMPO = ")).append(rs_drb.getString("IDCAMPO")).toString();
            rs_frb = cnx_frb.consultaBLOB(sql_frb, true);
            if(!rs_frb.next())
            {
                sql_frb = (new StringBuilder("INSERT INTO CFD_CAMPO (IDCAMPO, CAMPO, ESTADO, GRUPO)VALUES(")).append(rs_drb.getString("IDCAMPO")).append(",'").append(rs_drb.getString("CAMPO")).append("',").append(rs_drb.getString("ESTADO")).append(",").append(rs_drb.getString("GRUPO")).append(")").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
        } while(true);
    }

    public void Configuracion(long idempresa_drb, long sucursal_drb, long idempresa_frb, long sucursal_frb, long idEntidad_drb, long idEntidad_frb)
    {
        long idfolio = 0L;
        long idusuario = 0L;
        byte bytes[] = new byte[8096];
        byte key[] = new byte[8096];
        byte cer[] = new byte[8096];
        byte img[] = new byte[8096];
        ps.SetBorde((new StringBuilder("Configuracion Emp[")).append(idempresa_drb).append("] Suc[ ").append(sucursal_drb).append(" ]").toString());
        try
        {
            sql_drb = (new StringBuilder("SELECT *  FROM cfd_config  WHERE cfd_config.idEmpresa =")).append(idempresa_drb).append(" AND ").append("cfd_config.idSucursal = ").append(sucursal_drb).toString();
            rs_drb = cnx_drb.consultaBLOB(sql_drb, true);
            if(rs_drb.next())
            {
                String direccion = rs_drb.getString("dirContenedor");
                String pass = rs_drb.getString("direccion");
                String pagge = rs_drb.getString("page");
                String serie = rs_drb.getString("seriecertificado");
                String edo = rs_drb.getString("estado");
                String npubkey = rs_drb.getString("npubkey");
                String nprivkey = rs_drb.getString("nprivkey");
                Blob pubkey = rs_drb.getBlob("dirPubKey");
                Blob prikey = rs_drb.getBlob("dirPrivKey");
                Blob im = rs_drb.getBlob("imgFactura");
                sql_frb = (new StringBuilder("SELECT * FROM cfd_config  WHERE cfd_config.idEmpresa =")).append(idempresa_frb).append(" AND ").append("cfd_config.idSucursal = ").append(sucursal_frb).toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(!rs_frb.next())
                    try
                    {
                        sql_frb = "INSERT INTO cfd_config (idempresa, idsucursal, dirContenedor, direccion, dirPubKey, dirPrivKey, imgFactura, pagina, decimales, redondea, seriecertificado, estado, npubkey, nprivkey)  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        cnx_frb.prepara_consulta(sql_frb);
                        int paramindex = 1;
                        cnx_frb.pst.setLong(paramindex++, idempresa_frb);
                        cnx_frb.pst.setLong(paramindex++, sucursal_frb);
                        cnx_frb.pst.setString(paramindex++, direccion);
                        cnx_frb.pst.setString(paramindex++, pass);
                        if(pubkey != null && prikey != null && im != null)
                        {
                            ByteArrayInputStream ub = new ByteArrayInputStream(pubkey.getBytes(1L, (int)pubkey.length()));
                            ByteArrayInputStream ri = new ByteArrayInputStream(prikey.getBytes(1L, (int)prikey.length()));
                            ByteArrayInputStream imga = new ByteArrayInputStream(im.getBytes(1L, (int)im.length()));
                            cnx_frb.pst.setBinaryStream(paramindex++, ub, (int)pubkey.length());
                            cnx_frb.pst.setBinaryStream(paramindex++, ri, (int)prikey.length());
                            cnx_frb.pst.setBinaryStream(paramindex++, imga, (int)im.length());
                        } else
                        {
                            sql_frb = "INSERT INTO cfd_config (idempresa, idsucursal, dirContenedor, direccion, pagina, decimales, redondea, seriecertificado, estado, npubkey, nprivkey)  VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                            cnx_frb.prepara_consulta(sql_frb);
                            paramindex = 1;
                            cnx_frb.pst.setLong(paramindex++, idempresa_frb);
                            cnx_frb.pst.setLong(paramindex++, sucursal_frb);
                            cnx_frb.pst.setString(paramindex++, direccion);
                            cnx_frb.pst.setString(paramindex++, pass);
                            ps.SetBorde("Configuracion Tiene Valores Nulos");
                        }
                        cnx_frb.pst.setString(paramindex++, "LT");
                        cnx_frb.pst.setInt(paramindex++, 2);
                        cnx_frb.pst.setInt(paramindex++, 1);
                        cnx_frb.pst.setString(paramindex++, serie);
                        cnx_frb.pst.setString(paramindex++, edo);
                        cnx_frb.pst.setString(paramindex++, npubkey);
                        cnx_frb.pst.setString(paramindex++, nprivkey);
                        cnx_frb.aplicar_prepara_consulta();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                ps.SetBorde("Configuracion de Folios");
                sql_drb = (new StringBuilder(" SELECT IDFOLIOS, FECHASOLICITUD, FOLIOINICIAL, FOLIOFINAL, FOLIOACTUAL, NOAPROVACION, SERIE, IDEMPRESA, IDTIPODOC, ESTADO, IDSUCURSAL, FOLIO, CANTIDAD, RECORD, STATUS, FOLIOLIMITE  FROM CFD_FOLIOS WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND IDSUCURSAL =").append(sucursal_drb).toString();
                for(rs_drb = cnx_drb.consulta(sql_drb, true); rs_drb.next();)
                {
                    String fserie = rs_drb.getString("SERIE");
                    if(fserie == null)
                        fserie = "";
                    sql_frb = (new StringBuilder(" SELECT * FROM CFD_FOLIOS WHERE SERIE = '")).append(fserie).append("'AND IDEMPRESA = ").append(idempresa_frb).append(" AND IDSUCURSAL =").append(sucursal_frb).toString();
                    rs_frb = cnx_frb.consulta(sql_frb, true);
                    if(!rs_frb.next())
                    {
                        idfolio = nextID("IDFOLIOS", "CFD_FOLIOS", "CFD_FOLIOS_GEN");
                        sql_frb = (new StringBuilder("INSERT INTO CFD_FOLIOS (IDFOLIOS, FECHASOLICITUD, FOLIOINICIAL,FOLIOFINAL, FOLIOACTUAL, NOAPROVACION, SERIE, IDEMPRESA,IDTIPODOC, ESTADO, IDSUCURSAL )VALUES(")).append(idfolio).append(",'").append(rs_drb.getString("FECHASOLICITUD")).append("',").append(rs_drb.getString("FOLIOINICIAL")).append(",").append(rs_drb.getString("FOLIOFINAL")).append(",").append(rs_drb.getString("FOLIOACTUAL")).append(",").append(rs_drb.getString("NOAPROVACION")).append(",'").append(fserie).append("',").append(idempresa_frb).append(",").append(rs_drb.getString("IDTIPODOC")).append(",'").append(rs_drb.getString("ESTADO")).append("',").append(sucursal_frb).append(")").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                        ps.SetBorde("Historial de contrallaves");
                        sql_drb_2 = (new StringBuilder(" SELECT IDFOLIOS, FECHASOLICITUD, NSERIE,CKEY, ESTADO  FROM CFD_HCKEY WHERE IDFOLIOS = ")).append(rs_drb.getString("IDFOLIOS")).toString();
                        rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                        while(rs_drb_2.next()) 
                        {
                            sql_frb = (new StringBuilder("SELECT * FROM CFD_HCKEY WHERE NSERIE = '")).append(rs_drb_2.getString("NSERIE")).append("'AND CKEY = '").append(rs_drb_2.getString("CKEY")).append("'").toString();
                            rs_frb = cnx_frb.consultaBLOB(sql_frb, true);
                            if(!rs_frb.next())
                            {
                                sql_frb = (new StringBuilder("INSERT INTO CFD_HCKEY (IDFOLIOS, FECHASOLICITUD, NSERIE, CKEY, ESTADO)VALUES(")).append(idfolio).append(",'").append(rs_drb_2.getString("FECHASOLICITUD")).append("','").append(rs_drb_2.getString("NSERIE")).append("','").append(rs_drb_2.getString("CKEY")).append("','").append(rs_drb_2.getString("ESTADO")).append("')").toString();
                                cnx_frb.consulta(sql_frb, false);
                                cnx_frb.commit();
                            }
                        }
                    }
                }

                ps.SetBorde("Configuracion Impresion");
                long idimpresion = 0L;
                sql_drb = (new StringBuilder("SELECT IDIMPRESION,SERIE,X,Y,X1,Y1,VISIBLE,BANDA,IDEMPRESA,IDSUCURSAL  FROM CFD_IMPRESION  WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND IDSUCURSAL = ").append(sucursal_drb).toString();
                rs_drb = cnx_drb.consulta(sql_drb, true);
                do
                {
                    if(!rs_drb.next())
                        break;
                    sql_frb = (new StringBuilder(" SELECT * FROM CFD_IMPRESION WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND IDSUCURSAL = ").append(sucursal_frb).append(" AND IDIMPRESION = ").append(rs_drb.getString("IDIMPRESION")).append(" AND SERIE = '").append(rs_drb.getString("SERIE")).append("'").toString();
                    rs_frb = cnx_frb.consulta(sql_frb, true);
                    ps.SetBorde((new StringBuilder("Configuracion Imp[")).append(rs_drb.getString("IDIMPRESION")).append("]").toString());
                    if(!rs_frb.next())
                    {
                        sql_frb = (new StringBuilder("INSERT INTO CFD_IMPRESION (IDIMPRESION,SERIE,X,Y,X1,Y1,VISIBLE,IDEMPRESA,IDSUCURSAL )VALUES(")).append(rs_drb.getString("IDIMPRESION")).append(",'").append(rs_drb.getString("SERIE")).append("',").append(rs_drb.getString("X")).append(",").append(rs_drb.getString("Y")).append(",").append(rs_drb.getString("X1")).append(",").append(rs_drb.getString("Y1")).append(",").append(rs_drb.getString("VISIBLE")).append(",").append(idempresa_frb).append(",").append(sucursal_frb).append(")").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    }
                } while(true);
                ps.SetBorde("Configuracion de usuarios");
                sql_drb = (new StringBuilder("SELECT IDUSUARIO,IDENTIDAD,USR,PASS,TIPO,LSTFACTURA,FECHA,NRECORD FROM CNET_USUARIOS WHERE IDENTIDAD = ")).append(idEntidad_drb).toString();
                rs_drb = cnx_drb.consulta(sql_drb, true);
                if(rs_drb.next())
                {
                    ps.SetBorde((new StringBuilder("Usuario[ ")).append(rs_drb.getString("USR")).append(" ]").toString());
                    sql_frb = (new StringBuilder(" SELECT * FROM CNET_USUARIOS WHERE USR = '")).append(rs_drb.getString("USR")).append("' AND IDENTIDAD = ").append(idEntidad_frb).toString();
                    rs_frb = cnx_frb.consultaBLOB(sql_frb, true);
                    if(!rs_frb.next())
                    {
                        idusuario = nextID("IDUSUARIO", "CNET_USUARIOS", "CNET_USUARIOS_GEN");
                        sql_frb = (new StringBuilder("INSERT INTO CNET_USUARIOS (IDUSUARIO, IDENTIDAD,USR,PASS,TIPO)VALUES(")).append(idusuario).append(",").append(idEntidad_frb).append(",'").append(rs_drb.getString("USR")).append("','").append(rs_drb.getString("PASS")).append("','").append(rs_drb.getString("TIPO")).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                        sql_drb_2 = (new StringBuilder("SELECT IDUSUARIO,IDMODTO,ESTADO FROM CNET_PERMISOS WHERE IDUSUARIO = ")).append(rs_drb.getLong("idusuario")).toString();
                        rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                        do
                        {
                            if(!rs_drb_2.next())
                                break;
                            ps.SetBorde((new StringBuilder("Permisos de Usuario[ ")).append(rs_drb.getString("USR")).append(" ]").toString());
                            sql_frb_2 = (new StringBuilder("SELECT IDUSUARIO,IDMODTO,ESTADO FROM CNET_PERMISOS WHERE IDUSUARIO = ")).append(idusuario).toString();
                            rs_frb_2 = cnx_drb.consulta(sql_frb_2, true);
                            if(!rs_frb_2.next())
                            {
                                sql_frb = (new StringBuilder("INSERT INTO CNET_PERMISOS (IDUSUARIO, IDMODTO,ESTADO)VALUES(")).append(idusuario).append(",").append(rs_drb_2.getLong("IDMODTO")).append(",'").append(rs_drb_2.getString("ESTADO")).append("')").toString();
                                cnx_frb.consulta(sql_frb, false);
                                cnx_frb.commit();
                            }
                        } while(true);
                    }
                }
            }
        }
        catch(SQLException sqlexception) { }
    }

    public void Impuestos(long idempresa_drb, long sucursal_drb, long idempresa_frb, long sucursal_frb)
        throws SQLException
    {
        long idcatimpuesto_drb = 0L;
        long idcatimpuesto_frb = 0L;
        long idcnimpuesto_dbr = 0L;
        long idcnimpuesto_frb = 0L;
        long iddetimpuesto_frb = 0L;
        ps.SetBorde("Impuestos");
        sql_drb = (new StringBuilder("SELECT IDCATIMPUESTO,DESCRIPCION,IDEMPRESA,ESTADO  FROM CFD_CAT_IMPUESTOS  WHERE IDEMPRESA =")).append(idempresa_drb).toString();
        for(rs_drb = cnx_drb.consulta(sql_drb, true); rs_drb.next();)
        {
            ps.SetBorde("Catalogo de impuestos");
            idcatimpuesto_drb = rs_drb.getLong("IDCATIMPUESTO");
            sql_frb = (new StringBuilder("SELECT IDCATIMPUESTO,DESCRIPCION,IDEMPRESA,ESTADO  FROM CFD_CAT_IMPUESTOS  WHERE IDEMPRESA =")).append(idempresa_frb).append(" AND DESCRIPCION = '").append(rs_drb.getString("DESCRIPCION")).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(!rs_frb.next())
            {
                ps.SetBorde((new StringBuilder("Impuesto [ ")).append(rs_drb.getString("DESCRIPCION")).append(" ]").toString());
                idcatimpuesto_frb = nextID("IDCATIMPUESTO", "CFD_CAT_IMPUESTOS", "CFD_CAT_IMPUESTOS_GEN");
                sql_frb = (new StringBuilder("INSERT INTO CFD_CAT_IMPUESTOS(IDCATIMPUESTO, DESCRIPCION, IDEMPRESA, ESTADO)VALUES (")).append(idcatimpuesto_frb).append(",'").append(rs_drb.getString("DESCRIPCION")).append("',").append(idempresa_frb).append(",'").append(rs_drb.getString("ESTADO")).append("')").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
                sql_drb_2 = (new StringBuilder("SELECT DESCRIPCION,IDEMPRESA,IDCNIMPUESTO,ESTADO,TIPO,CCONTABLE  FROM CNET_IMPUESTO WHERE IDEMPRESA = ")).append(idempresa_drb).toString();
                rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
                while(rs_drb_2.next()) 
                {
                    ps.SetBorde((new StringBuilder("Tipo [ ")).append(rs_drb_2.getString("TIPO")).append(" ]").toString());
                    idcnimpuesto_dbr = rs_drb_2.getLong("IDCNIMPUESTO");
                    sql_frb = (new StringBuilder("SELECT DESCRIPCION,IDEMPRESA,IDCNIMPUESTO,ESTADO,TIPO,CCONTABLE FROM CNET_IMPUESTO WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND DESCRIPCION = '").append(rs_drb_2.getString("DESCRIPCION")).append("'").toString();
                    rs_frb = cnx_frb.consulta(sql_frb, true);
                    if(!rs_frb.next())
                    {
                        idcnimpuesto_frb = nextID("IDCNIMPUESTO", "CNET_IMPUESTO", "CNET_IMPUESTO_GEN");
                        sql_frb = (new StringBuilder("INSERT INTO CNET_IMPUESTO(DESCRIPCION, IDEMPRESA, IDCNIMPUESTO, ESTADO, TIPO, CCONTABLE)VALUES ('")).append(rs_drb_2.getString("DESCRIPCION")).append("',").append(idempresa_frb).append(",").append(idcnimpuesto_frb).append(",'").append(rs_drb_2.getString("ESTADO")).append("','").append(rs_drb_2.getString("TIPO")).append("','").append(rs_drb_2.getString("CCONTABLE")).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                        ps.SetBorde("Grupo de impuestos");
                        sql_drb_3 = (new StringBuilder("SELECT IDDETIMPUESTO,IDCNIMPUESTO,IDCATIMPUESTO,IMPUESTO,IDCC,ESTADO,ORDEN FROM CFD_DET_IMPUESTOS WHERE IDCNIMPUESTO = ")).append(idcnimpuesto_dbr).append(" AND IDCATIMPUESTO =").append(idcatimpuesto_drb).toString();
                        rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
                        while(rs_drb_3.next()) 
                        {
                            ps.SetBorde((new StringBuilder("Tasa [ ")).append(rs_drb_3.getDouble("IMPUESTO")).append(" ]").toString());
                            sql_frb = (new StringBuilder("SELECT IDDETIMPUESTO,IDCNIMPUESTO,IDCATIMPUESTO,IMPUESTO,IDCC,ESTADO,ORDEN FROM CFD_DET_IMPUESTOS WHERE IDCNIMPUESTO = ")).append(idcnimpuesto_frb).append(" AND IDCATIMPUESTO =").append(idcatimpuesto_frb).toString();
                            rs_frb = cnx_drb.consulta(sql_frb, true);
                            if(!rs_frb.next())
                            {
                                iddetimpuesto_frb = nextID("IDDETIMPUESTO", "CFD_DET_IMPUESTOS", "CFD_DET_IMPUESTOS_GEN");
                                sql_frb = (new StringBuilder("INSERT INTO CFD_DET_IMPUESTOS(IDDETIMPUESTO, IDCNIMPUESTO, IDCATIMPUESTO, IMPUESTO, IDCC, ESTADO, ORDEN)VALUES (")).append(iddetimpuesto_frb).append(",").append(idcnimpuesto_frb).append(",").append(idcatimpuesto_frb).append(",").append(rs_drb_3.getDouble("IMPUESTO")).append(",").append(rs_drb_3.getLong("IDCC")).append(",'").append(rs_drb_3.getString("ESTADO")).append("',").append(rs_drb_3.getLong("ORDEN")).append(")").toString();
                                cnx_frb.consulta(sql_frb, false);
                                cnx_frb.commit();
                            }
                        }
                    }
                }
            }
        }

    }

    public long Descripcion(long id)
        throws SQLException
    {
        String descripcion_articulo = null;
        sql_drb_2 = (new StringBuilder("SELECT ID_DESCDETALLE, DESCRIPCION FROM ALM_DESC_DETALLE  WHERE ID_DESCDETALLE = ")).append(id).toString();
        rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
        if(rs_drb_2.next())
        {
            descripcion_articulo = rs_drb_2.getString("DESCRIPCION").replaceAll("'", "").trim();
            ps.SetBorde((new StringBuilder("Detalle de Articulos [ ")).append(descripcion_articulo).append("]").toString());
            ps.SetBorde((new StringBuilder("Alta de articulos [")).append(id).append("][").append(rs_drb_2.getString("DESCRIPCION")).append("]").toString());
            sql_frb = (new StringBuilder("SELECT ID_DESCDETALLE, DESCRIPCION FROM ALM_DESC_DETALLE  WHERE DESCRIPCION = '")).append(descripcion_articulo).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(!rs_frb.next())
            {
                id_descdetalle = nextID("ID_DESCDETALLE", "ALM_DESC_DETALLE", "ALM_DESC_DETALLE_GEN");
                sql_frb = (new StringBuilder("INSERT INTO ALM_DESC_DETALLE(ID_DESCDETALLE, DESCRIPCION)VALUES (")).append(id_descdetalle).append(",'").append(descripcion_articulo).append("')").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            } else
            {
                id_descdetalle = rs_frb.getLong("ID_DESCDETALLE");
            }
        }
        return id_descdetalle;
    }

    public long GrupoArticulos(long id)
        throws SQLException
    {
        String descripcion_grupo = null;
        sql_drb_2 = (new StringBuilder("SELECT ID_DESCGRUPO, DESCRIPCION  FROM ALM_DESC_GRUPO  WHERE ID_DESCGRUPO = ")).append(id).toString();
        rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
        if(rs_drb_2.next())
        {
            descripcion_grupo = rs_drb_2.getString("DESCRIPCION").replaceAll("'", "").trim();
            ps.SetBorde((new StringBuilder("Grupo [ ")).append(descripcion_grupo).append("]").toString());
            ps.SetBorde((new StringBuilder("Grupo de articulos [")).append(rs_drb_2.getString("DESCRIPCION")).append("]").toString());
            sql_frb = (new StringBuilder("SELECT ID_DESCGRUPO, DESCRIPCION  FROM ALM_DESC_GRUPO  WHERE DESCRIPCION  = '")).append(descripcion_grupo).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(!rs_frb.next())
            {
                id_descgrupo = nextID("ID_DESCGRUPO", "ALM_DESC_GRUPO", "ALM_DESC_GRUPO_GEN");
                sql_frb = (new StringBuilder("INSERT INTO ALM_DESC_GRUPO(ID_DESCGRUPO, DESCRIPCION)VALUES (")).append(id_descgrupo).append(",'").append(descripcion_grupo).append("')").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            } else
            {
                id_descgrupo = rs_frb.getLong("ID_DESCGRUPO");
            }
        }
        return id_descgrupo;
    }

    public void Articulos(long idempresa_drb, long sucursal_drb, long idempresa_frb, long sucursal_frb)
        throws SQLException
    {
        long DESCDETALLE = 0L;
        long DESCGRUPO = 0L;
        long IDARTICULOS = 0L;
        long IDARTICULOS_drb = 0L;
        ps.SetBorde("Configuracion de Articulos");
        sql_drb = (new StringBuilder(" SELECT IDARTICULO,ID_TIPO_COSTO,CLAVE,ID_DESCDETALLE,ID_DESCGRUPO,TIPO_ARTICULO,PRECIO_VENTA,ESTADO,LAST_ACTION,IDCC  FROM CNET_ARTICULOS  WHERE IDEMPRESA = ")).append(idempresa_drb).toString();
        rs_drb = cnx_drb.consulta(sql_drb, true);
        do
        {
            if(!rs_drb.next())
                break;
            IDARTICULOS_drb = rs_drb.getLong("IDARTICULO");
            DESCGRUPO = GrupoArticulos(rs_drb.getLong("ID_DESCGRUPO"));
            DESCDETALLE = Descripcion(rs_drb.getLong("ID_DESCDETALLE"));
            if(DESCGRUPO > 0L && DESCDETALLE > 0L)
            {
                sql_frb = (new StringBuilder("SELECT  IDARTICULO,CLAVE,ID_DESCDETALLE,ID_DESCGRUPO,TIPO_ARTICULO,PRECIO_VENTA,ESTADO,LAST_ACTION,IDCC FROM CNET_ARTICULOS  WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND ID_DESCDETALLE = ").append(DESCDETALLE).append(" AND ID_DESCDETALLE = ").append(DESCGRUPO).toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(!rs_frb.next())
                {
                    IDARTICULOS = nextID("IDARTICULO", "CNET_ARTICULOS", "CNET_ARTICULOS_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CNET_ARTICULOS(IDEMPRESA, IDARTICULO, CLAVE,ID_DESCDETALLE,ID_DESCGRUPO,TIPO_ARTICULO,PRECIO_VENTA,ESTADO,LAST_ACTION,IDCC)VALUES (")).append(idempresa_frb).append(",").append(IDARTICULOS).append(",'").append(rs_drb.getString("CLAVE")).append("',").append(DESCDETALLE).append(",").append(DESCGRUPO).append(",'").append(rs_drb.getString("TIPO_ARTICULO")).append("',").append(rs_drb.getDouble("PRECIO_VENTA")).append(",'").append(rs_drb.getString("ESTADO")).append("','").append(rs_drb.getString("LAST_ACTION")).append("',").append(rs_drb.getString("IDCC")).append(")").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                    ArtImpuestos(IDARTICULOS_drb, IDARTICULOS, idempresa_drb, idempresa_frb);
                }
            }
        } while(true);
    }

    public void ArtImpuestos(long idarticulo_drb, long idarticulo_frb, long idempresa_drb, long idempresa_frb)
        throws SQLException
    {
        long IDCATIMPUESTO = 0L;
        ps.SetBorde("Asociando Impuestos a Articulos");
        sql_drb = (new StringBuilder(" SELECT IDARTICULO,IDCATIMPUESTO,IDCATIMPUESTOCOMPRA  FROM CFD_ARTIMPUESTO  WHERE IDARTICULO = ")).append(idarticulo_drb).toString();
        rs_drb = cnx_drb.consulta(sql_drb, true);
        do
        {
            if(!rs_drb.next())
                break;
            sql_drb_2 = (new StringBuilder("SELECT IDCATIMPUESTO,DESCRIPCION,IDEMPRESA,ESTADO  FROM CFD_CAT_IMPUESTOS  WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND IDCATIMPUESTO = ").append(rs_drb.getLong("IDCATIMPUESTO")).toString();
            rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
            if(rs_drb_2.next())
            {
                sql_frb_2 = (new StringBuilder("SELECT IDCATIMPUESTO FROM CFD_CAT_IMPUESTOS  WHERE DESCRIPCION = '")).append(rs_drb_2.getString("DESCRIPCION")).append("' AND IDEMPRESA = ").append(idempresa_frb).toString();
                rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
                if(rs_frb_2.next())
                    IDCATIMPUESTO = rs_frb_2.getLong("IDCATIMPUESTO");
            }
            sql_frb = (new StringBuilder(" SELECT IDARTICULO ,IDCATIMPUESTO,IDCATIMPUESTOCOMPRA  FROM CFD_ARTIMPUESTO  WHERE IDARTICULO = ")).append(idarticulo_frb).append(" AND IDCATIMPUESTO = ").append(IDCATIMPUESTO).toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(!rs_frb.next())
            {
                sql_frb = (new StringBuilder("INSERT INTO CFD_ARTIMPUESTO(IDARTICULO,IDCATIMPUESTO)VALUES (")).append(idarticulo_frb).append(",").append(IDCATIMPUESTO).append(")").toString();
                cnx_frb.consulta(sql_frb, false);
                cnx_frb.commit();
            }
        } while(true);
    }

    public void Comprobates(long idempresa_drb, long sucursal_drb, long idempresa_frb, long sucursal_frb)
        throws SQLException
    {
        long folio = 0L;
        long IDCONCEPTO = 0L;
        long IDIMPUESTO = 0L;
        long IDARTICULO = 0L;
        long IDCDECUENTO = 0L;
        long IDDECUENTO = 0L;
        long IDVAL = 0L;
        long IDCAMPO = 0L;
        String serie = null;
        ps.SetBorde("Comprobates");
        sql_drb = (new StringBuilder("SELECT FOLIO,SERIE,TIPO,FECHA,NOAPROBACION,NOCERTIFICADO,SELLO,VERSION,EMISOR,DOM_EMISOR,RECEPTOR,DOM_RECEPTOR,ESTADO ,IDEMPRESA,OBSERVACION,UPWEB,IDTDOCTO,F_PAGO,SUCURSAL,CORIGINAL,MONEDA FROM CFD_COMPROBANTE WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND SUCURSAL = ").append(sucursal_drb).toString();
        for(rs_drb = cnx_drb.consultaBLOB(sql_drb, true); rs_drb.next();)
        {
            folio = rs_drb.getLong("FOLIO");
            serie = rs_drb.getString("serie");
            ps.SetBorde((new StringBuilder("Comprobante [ ")).append(folio).append(" ").append(serie).append(" ]").toString());
            sql_frb = (new StringBuilder("SELECT FOLIO,SERIE,TIPO,FECHA,NOAPROBACION,NOCERTIFICADO,SELLO,VERS,EMISOR,DOM_EMISOR,RECEPTOR,DOM_RECEPTOR,ESTADO ,IDEMPRESA,OBSERVACION,UPWEB,IDTDOCTO,F_PAGO,SUCURSAL,CORIGINAL,MONEDA FROM CFD_COMPROBANTE WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND SUCURSAL = ").append(sucursal_frb).append(" AND FOLIO = '").append(folio).append("' AND SERIE = '").append(serie).append("'").toString();
            rs_frb = cnx_frb.consulta(sql_frb, true);
            if(!rs_frb.next())
            {
                long emisor = FindEntidad(rs_drb.getLong("EMISOR"));
                long domicilio_emisor = FindDomicilio(rs_drb.getLong("EMISOR"), emisor, rs_drb.getLong("DOM_EMISOR"));
                long receptor = FindEntidad(rs_drb.getLong("RECEPTOR"));
                long domicilio_receptor = FindDomicilio(rs_drb.getLong("RECEPTOR"), receptor, rs_drb.getLong("DOM_RECEPTOR"));
                try
                {
                    sql_frb = "INSERT INTO CFD_COMPROBANTE(FOLIO,SERIE,TIPO,FECHA,NOAPROBACION,NOCERTIFICADO,SELLO,VERS,EMISOR,DOM_EMISOR,RECEPTOR,DOM_RECEPTOR,ESTADO ,IDEMPRESA,OBSERVACION,UPWEB,IDTDOCTO,F_PAGO,SUCURSAL,CORIGINAL,MONEDA)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    cnx_frb.prepara_consulta(sql_frb);
                    int paramindex = 1;
                    cnx_frb.pst.setLong(paramindex++, folio);
                    cnx_frb.pst.setString(paramindex++, serie);
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("TIPO"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("FECHA"));
                    cnx_frb.pst.setLong(paramindex++, rs_drb.getLong("NOAPROBACION"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("NOCERTIFICADO"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("SELLO"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("VERSION"));
                    cnx_frb.pst.setLong(paramindex++, emisor);
                    cnx_frb.pst.setLong(paramindex++, domicilio_emisor);
                    cnx_frb.pst.setLong(paramindex++, receptor);
                    cnx_frb.pst.setLong(paramindex++, domicilio_receptor);
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("ESTADO"));
                    cnx_frb.pst.setLong(paramindex++, idempresa_frb);
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("OBSERVACION"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("UPWEB"));
                    cnx_frb.pst.setLong(paramindex++, rs_drb.getLong("IDTDOCTO"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("F_PAGO"));
                    cnx_frb.pst.setLong(paramindex++, sucursal_frb);
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("CORIGINAL"));
                    cnx_frb.pst.setString(paramindex++, rs_drb.getString("MONEDA"));
                    cnx_frb.aplicar_prepara_consulta();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            ps.SetBorde((new StringBuilder("Conceptos [ ")).append(folio).append(" ").append(serie).append(" ]").toString());
            sql_drb_2 = (new StringBuilder("SELECT IDCONCEPTO,FOLIO,CANTIDAD,DESCRIPCION,IMPORTE,VALORUNITARIO,SERIE,IDEMPRESA,CLAVEARTICULO FROM CFD_CONCEPTOS WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("'").toString();
            rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
            do
            {
                if(!rs_drb_2.next())
                    break;
                sql_frb = (new StringBuilder("SELECT IDCONCEPTO,FOLIO,CANTIDAD,DESCRIPCION,IMPORTE,VALORUNITARIO,SERIE,IDEMPRESA,CLAVEARTICULO FROM CFD_CONCEPTOS WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("' AND DESCRIPCION = '").append(rs_drb_2.getString("DESCRIPCION")).append("'").toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(!rs_frb.next())
                {
                    IDCONCEPTO = nextID("IDCONCEPTO", "CFD_CONCEPTOS", "CFD_CONCEPTOS_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CFD_CONCEPTOS(IDCONCEPTO,FOLIO,CANTIDAD,DESCRIPCION,IMPORTE,VALORUNITARIO,SERIE,IDEMPRESA,CLAVEARTICULO)VALUES (")).append(IDCONCEPTO).append(",'").append(rs_drb_2.getLong("FOLIO")).append("',").append(rs_drb_2.getLong("CANTIDAD")).append(",'").append(rs_drb_2.getString("DESCRIPCION")).append("',").append(rs_drb_2.getDouble("IMPORTE")).append(",").append(rs_drb_2.getDouble("VALORUNITARIO")).append(",'").append(rs_drb_2.getString("SERIE")).append("',").append(idempresa_frb).append(",'").append(rs_drb_2.getString("CLAVEARTICULO")).append("')").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                }
            } while(true);
            ps.SetBorde((new StringBuilder("Impuestos [ ")).append(folio).append(" ").append(serie).append(" ]").toString());
            sql_drb_2 = (new StringBuilder("SELECT IDIMPUESTO,IDCNIMPUESTO,IDARTICULO,FOLIO,SERIE,IMPORTE,TIPO,IDEMPRESA,IDCC,IDCATIMPUESTO FROM CFD_IMPUESTOS WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("'").toString();
            rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
            do
            {
                if(!rs_drb_2.next())
                    break;
                sql_frb = (new StringBuilder(" SELECT IDIMPUESTO,IDCNIMPUESTO,IDARTICULO,FOLIO,SERIE,IMPORTE,TIPO,IDEMPRESA,IDCC,IDCATIMPUESTO FROM CFD_IMPUESTOS WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("' AND IDCNIMPUESTO = ").append(rs_drb_2.getLong("IDCNIMPUESTO")).append(" AND IDARTICULO = ").append(rs_drb_2.getLong("IDARTICULO")).toString();
                rs_frb = cnx_frb.consulta(sql_frb, true);
                if(!rs_frb.next())
                {
                    IDIMPUESTO = nextID("IDIMPUESTO", "CFD_IMPUESTOS", "CFD_IMPUESTOS_GEN");
                    sql_frb = (new StringBuilder("INSERT INTO CFD_IMPUESTOS(IDIMPUESTO,IDCNIMPUESTO,IDARTICULO,FOLIO,SERIE,IMPORTE,TIPO,IDEMPRESA,IDCC,IDCATIMPUESTO)VALUES (")).append(IDIMPUESTO).append(",").append(rs_drb_2.getLong("IDCNIMPUESTO")).append(",").append(rs_drb_2.getLong("IDARTICULO")).append(",").append(rs_drb_2.getLong("FOLIO")).append(",'").append(rs_drb_2.getString("SERIE")).append("',").append(rs_drb_2.getDouble("IMPORTE")).append(",'").append(rs_drb_2.getString("TIPO")).append("',").append(rs_drb_2.getLong("IDEMPRESA")).append(",").append(rs_drb_2.getLong("IDCC")).append(",").append(rs_drb_2.getLong("IDCATIMPUESTO")).append(")").toString();
                    cnx_frb.consulta(sql_frb, false);
                    cnx_frb.commit();
                }
            } while(true);
            ps.SetBorde((new StringBuilder("Descuentos [ ")).append(folio).append(" ").append(serie).append(" ]").toString());
            sql_drb_2 = (new StringBuilder("SELECT IDDECUENTO,IDCDECUENTO,IDEMPRESA,FOLIO,SERIE,CVEART,IMPORTE_UNITARIO,IMPORTE_TOTAL,TIPO FROM CFD_DESCUENTOS WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("'").toString();
            rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
            do
            {
                if(!rs_drb_2.next())
                    break;
                sql_drb_3 = (new StringBuilder("SELECT IDCDECUENTO,DESCRIPCION,TASA,IDEMPRESA,EDOS,TIPO FROM CFD_CATDESCUENTOS WHERE IDCDESCUENTO = ")).append(rs_drb_2.getLong("IDCDECUENTO")).append(" AND IDEMPRESA = ").append(idempresa_drb).toString();
                rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
                if(rs_drb_3.next())
                {
                    sql_frb = (new StringBuilder("SELECT IDCDECUENTO,DESCRIPCION,TASA,IDEMPRESA,EDOS,TIPO FROM CFD_CATDESCUENTOS WHERE DESCRIPCION = '")).append(rs_drb_2.getString("DESCRIPCION")).append("' AND IDEMPRESA = ").append(idempresa_frb).append(" AND TASA = ").append(rs_drb_2.getDouble("TASA")).toString();
                    rs_frb = cnx_frb.consulta(sql_frb, true);
                    if(!rs_frb.next())
                    {
                        IDCDECUENTO = nextID("IDCDECUENTO", "CFD_CATDESCUENTOS", "CFD_CATDESCUENTOS_GEN");
                        sql_frb = (new StringBuilder("INSERT INTO CFD_CATDESCUENTOS(IDCDECUENTO,DESCRIPCION,TASA,IDEMPRESA,EDOS,TIPO)VALUES (")).append(IDCDECUENTO).append(",'").append(rs_drb_2.getString("DESCRIPCION")).append("',").append(rs_drb_2.getDouble("TASA")).append(",").append(idempresa_frb).append(",").append(rs_drb_2.getDouble("EDOS")).append(",'").append(rs_drb_2.getDouble("TIPO")).append("')").toString();
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    } else
                    {
                        IDCDECUENTO = rs_frb.getLong("IDCDECUENTO");
                    }
                }
                sql_frb_2 = (new StringBuilder("SELECT IDDECUENTO,IDCDECUENTO,IDEMPRESA,FOLIO,SERIE,CVEART,IMPORTE_UNITARIO,IMPORTE_TOTAL,TIPO FROM CFD_DESCUENTOS WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("' AND IDCDECUENTO = ").append(IDCDECUENTO).toString();
                rs_frb_2 = cnx_frb.consulta(sql_frb_2, true);
                if(!rs_frb_2.next())
                {
                    IDDECUENTO = nextID("IDDECUENTO", "CFD_DESCUENTOS", "CFD_DESCUENTOS_GEN");
                    sql_frb_2 = (new StringBuilder("INSERT INTO CFD_DESCUENTOS(IDDECUENTO,IDCDECUENTO,IDEMPRESA,FOLIO,SERIE,CVEART,IMPORTE_UNITARIO,IMPORTE_TOTAL,TIPO)VALUES (")).append(IDDECUENTO).append(",").append(IDCDECUENTO).append(",").append(idempresa_frb).append(",'").append(folio).append("','").append(serie).append("',").append(rs_frb_2.getDouble("CVEART")).append(",").append(rs_frb_2.getDouble("IMPORTE_UNITARIO")).append(",").append(rs_frb_2.getDouble("IMPORTE_TOTAL")).append(",'").append(rs_drb_2.getString("TIPO")).append("')").toString();
                    cnx_frb.consulta(sql_frb_2, false);
                    cnx_frb.commit();
                }
            } while(true);
            ps.SetBorde((new StringBuilder("Addenda [ ")).append(folio).append(" ").append(serie).append(" ]").toString());
            sql_drb_2 = (new StringBuilder("SELECT IDVAL,IDCAMPO,FOLIO,SERIE,VAL,CVEART,IDEMPRESA FROM CFD_VALADE WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("'").toString();
            rs_drb_2 = cnx_drb.consulta(sql_drb_2, true);
            while(rs_drb_2.next()) 
            {
                sql_drb_3 = (new StringBuilder("SELECT *  FROM CFD_CAMADE  WHERE IDEMPRESA = ")).append(idempresa_drb).append(" AND IDSUCURSAL = ").append(sucursal_drb).append("AND IDCAMPO = ").append(rs_drb_2.getLong("IDCAMPO")).toString();
                rs_drb_3 = cnx_drb.consulta(sql_drb_3, true);
                if(rs_drb_3.next())
                {
                    sql_frb = (new StringBuilder("SELECT IDCAMPO,IDEMPRESA,IDSUCURSAL,NOMBRE,FORMATO,PLANTILLA,COMPANY,ORDEN,IDFORMATO,IDPADRE,IDATRIBUTO,NIVEL,REQUERIDO,DEFT_VAL,IDADD,STATUS,IDASC FROM CFD_CAMADE  WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND NOMBRE = ").append(rs_drb_3.getString("NOMBRE")).toString();
                    rs_frb = cnx_frb.consulta(sql_frb, true);
                    if(!rs_frb.next())
                    {
                        IDCAMPO = nextID("IDCAMPO", "CFD_CAMADE", "CFD_CAMADE_GEN");
                        sql_frb_2 = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO,IDEMPRESA,IDSUCURSAL,NOMBRE,FORMATO,PLANTILLA,COMPANY,ORDEN,IDFORMATO,IDPADRE,IDATRIBUTO,NIVEL,REQUERIDO,DEFT_VAL,IDADD,STATUS,IDASC)VALUES (")).append(IDCAMPO).append(",").append(idempresa_frb).append(",").append(sucursal_frb).append(",").append(rs_frb.getString("NOMBRE")).append(",").append(rs_frb.getString("FORMATO")).append(",").append(rs_frb.getLong("PLANTILLA")).append(",").append(rs_frb.getString("COMPANY")).append(",").append(rs_frb.getLong("ORDEN")).append(",").append(rs_frb.getLong("IDFORMATO")).append(",").append(rs_frb.getLong("IDPADRE")).append(",").append(rs_frb.getLong("IDATRIBUTO")).append(",").append(rs_frb.getLong("NIVEL")).append(",").append(rs_frb.getLong("REQUERIDO")).append(",").append(rs_frb.getString("DEFT_VAL")).append(",").append(rs_frb.getLong("IDADD")).append(",").append(rs_frb.getLong("STATUS")).append(",").append(rs_frb.getLong("IDASC")).append(")").toString();
                        cnx_frb.consulta(sql_frb_2, false);
                        cnx_frb.commit();
                    } else
                    {
                        IDCAMPO = rs_frb.getLong("");
                    }
                    sql_frb = (new StringBuilder("SELECT IDVAL,IDCAMPO,FOLIO,SERIE,VAL,CVEART,IDEMPRESA FROM CFD_VALADE WHERE IDEMPRESA = ")).append(idempresa_frb).append(" AND FOLIO =").append(folio).append(" AND SERIE ='").append(serie).append("' AND IDCAMPO = ").append(IDCAMPO).toString();
                    rs_frb = cnx_frb.consulta(sql_frb, true);
                    if(!rs_frb.next())
                    {
                        IDVAL = nextID("IDVAL", "CFD_VALADE", "CFD_VALADE_GEN");
                        sql_frb = "INSERT INTO CFD_VALADE(IDVAL,IDCAMPO,FOLIO,SERIE,VAL,CVEART,IDEMPRESA)VALUES (IDVAL,IDCAMPO,FOLIO,SERIE,VAL,CVEART,IDEMPRESA)";
                        cnx_frb.consulta(sql_frb, false);
                        cnx_frb.commit();
                    }
                }
            }
        }

    }

    public static void main(String args[])
        throws FileNotFoundException, SQLException, IOException
    {
        MigraV2 mv2 = new MigraV2();
        mv2.FCards();
        mv2.Campos();
        mv2.Entidades();
        mv2.ps.CerrarProgreso();
        mv2.m.GetMensaje("La Migracion V2.0 se ha completado");
    }

    long id_descdetalle;
    long id_descgrupo;
    long idEntidad;
    long idEntidad_drb;
    long idEmpresa_drb;
    long idEmpresa;
    long idEstado;
    long idColonia;
    long idDelegacion;
    long idDomicilio;
    long idSucursal;
    long idSucursal_drb;
    long idpais;
    String nombre;
    String rfc;
    String nombre_comercial;
    String abreviado;
    String tipo_sucursal;
    String estado;
    String municipio;
    String colonia;
    String codigoPostal;
    String calle;
    String noExterior;
    String noInterior;
    String tipo;
    String nombre_sucursal;
    String sql_frb;
    String sql_frb_2;
    String sql_drb;
    String sql_drb_2;
    String sql_drb_3;
    java.sql.ResultSet rs_frb;
    java.sql.ResultSet rs_frb_2;
    java.sql.ResultSet rs_frb_3;
    ResultSet rs_drb;
    ResultSet rs_drb_2;
    ResultSet rs_drb_3;
    ResultSet rs_drb_entidades;
    ConexionFirebird cnx_frb;
    ConexionDerby cnx_drb;
    public ProgressBarSample ps;
    Mensajes m;
}
