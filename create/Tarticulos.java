// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:27:33 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Tarticulos.java

package create;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

// Referenced classes of package create:
//            ConexionFirebird

public class Tarticulos
{

    public Tarticulos()
        throws SQLException, FileNotFoundException, IOException
    {
        sql = null;
        cnx = new ConexionFirebird();
        try
        {
            ConexionFirebird.conectarFirebird();
        }
        catch(ClassNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }

    public void ActulalizaEdo()
        throws SQLException
    {
        try
        {
            sql = "UPDATE CNET_ARTICULOS SET TIPO_ARTICULO = 'V'";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
    }

    public void ComprobanteAnio()
        throws SQLException
    {
        try
        {
            sql = "ALTER TABLE CFD_COMPROBANTE ADD ANIOAPROB INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
    }

    public void CatEdos()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_EDOS (ESTADO VARCHAR(28),COLOR INTEGER,IDEDOS INTEGER NOT NULL,EDOS VARCHAR(1)) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
        try
        {
            sql = " CREATE UNIQUE INDEX SQL070911053112600 ON CFD_EDOS (IDEDOS ASC) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
        try
        {
            sql = " ALTER TABLE CFD_EDOS ADD CONSTRAINT SQL070911053112600 PRIMARY KEY (IDEDOS) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
        try
        {
            sql = " INSERT INTO CFD_EDOS(ESTADO,COLOR,IDEDOS,EDOS) VALUES('Vigente',0,1,'T') ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
        try
        {
            sql = " INSERT INTO CFD_EDOS(ESTADO,COLOR,IDEDOS,EDOS) VALUES('Cancelado',255,2,'X') ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
        try
        {
            sql = "INSERT INTO CFD_EDOS (ESTADO, COLOR, IDEDOS, EDOS) VALUES ('Remision', 15993634, 3, 'R')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Estados)");
        }
    }

    public void CamMoneda()
        throws SQLException
    {
        try
        {
            sql = " ALTER TABLE CFD_COMPROBANTE ADD COLUMN TIPO_CAMBIO DECIMAL(10,2)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            sql = " ALTER TABLE CFD_COMPROBANTE ADD COLUMN MONEDA VARCHAR(3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes(Cambio de moneda ya realizado...");
        }
        try
        {
            sql = "UPDATE CFD_COMPROBANTE SET TIPO_CAMBIO = 0.0, MONEDA = 'PSM' WHERE moneda is null  and  tipo_cambio is null";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Valores de tipo de cambio actualizados a pesos...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes(Tipo de cambio no se encontraron valores nulos...)");
        }
    }

    public void CatAddenda()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_CAMADE (IDCAMPO INTEGER NOT NULL,IDEMPRESA INTEGER,IDSUCURSAL INTEGER,NOMBRE VARCHAR(255),FORMATO VARCHAR(25),PLANTILLA INTEGER)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            sql = " CREATE UNIQUE INDEX SQL070918110145790 ON CFD_CAMADE (IDCAMPO ASC)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            sql = " ALTER TABLE CFD_CAMADE ADD CONSTRAINT SQL070918110145790 PRIMARY KEY (IDCAMPO)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Addenda)");
        }
        try
        {
            sql = "ALTER TABLE CFD_ADDENDA ADD TIPO INTEGER DEFAULT 1 NOT NULL";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Tipo Addenda)");
        }
    }

    public void Cam_Imp()
        throws SQLException
    {
        try
        {
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'calle_receptor'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'calle_receptor',2,2)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'noexterior_receptor'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'noexterior_receptor',2,2)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'nointerior_receptor'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'nointerior_receptor',2,2)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'colonia_receptor'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'colonia_receptor',2,2)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'calle_sucursal'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'calle_sucursal',2,1)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'noexterior_sucursal'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'noexterior_sucursal',2,1)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'nointerior_sucursal'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'nointerior_sucursal',2,1)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'colonia_sucursal'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'colonia_sucursal',2,1)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'desglose_impuestos'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'desglose_impuestos',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'retencion_iva'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'retencion_iva',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'retencion_isr'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'retencion_isr',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'traslado_iva'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'traslado_iva',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'traslado_ieps'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'traslado_ieps',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'subtotal_honorarios'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'subtotal_honorarios',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'anio_de_aprobacion'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'anio_de_aprobacion',1,4)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'subtotal_iva_tra'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'subtotal_iva_tra',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'subtotal_isr_ret'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'subtotal_isr_ret',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'subtotal_ieps_tra'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'subtotal_ieps_tra',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMPO WHERE campo = 'subtotal_iva_ret'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMPO(IDCAMPO, CAMPO, ESTADO, GRUPO) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMPO")).append(",'subtotal_iva_ret',1,3)").toString();
                rs = cnx.consulta(sql, false);
            }
            System.out.println("Valores creados (Campos Impresion)");
            cnx.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya existentes (Campos Impresion)");
        }
    }

    public void ValAddenda()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_VALADE (IDVAL INTEGER,IDCAMPO INTEGER,FOLIO INTEGER,SERIE VARCHAR(5),VAL VARCHAR(800),CVEART VARCHAR(30),IDEMPRESA INTEGER )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Valores Adenda)");
        }
        try
        {
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noVersAdd' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noVersAdd','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'claseDoc' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'claseDoc','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noSociedad' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noSociedad','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noProveedor' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noProveedor','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noPedido' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noPedido','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'moneda' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'moneda','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noEntrada' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noEntrada','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noRemision' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noRemision','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'noSocio' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'noSocio','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'centro' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'centro','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'iniPerLiq' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'iniPerLiq','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'finPerLiq' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'finPerLiq','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'retencion1' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'retencion1','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'retencion2' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'retencion2','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'email' and FORMATO = 'FacturaFemsa' AND COMPANY = 'Documento'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE(IDCAMPO, NOMBRE, FORMATO, PLANTILLA, COMPANY) VALUES (")).append(nextID("IDCAMPO", "CFD_CAMADE")).append(",'email','FacturaFemsa',1,'Documento')").toString();
                rs = cnx.consulta(sql, false);
            }
            cnx.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya existentes (Addenda Femsa)");
        }
    }

    public void Descuentos()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_DESCUENTOS (IDDECUENTO INTEGER NOT NULL,IDCDECUENTO INTEGER,IDEMPRESA INTEGER,FOLIO INTEGER,SERIE VARCHAR(5),CVEART VARCHAR(30),IMPORTE_UNITARIO DECIMAL(10,2),IMPORTE_TOTAL DECIMAL(10,2))";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Descuentos)");
        }
    }

    public void CatDescuentos()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_CATDESCUENTOS ( IDCDECUENTO INTEGER NOT NULL,DESCRIPCION VARCHAR(250),TASA DOUBLE,IDEMPRESA INTEGER) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            sql = " CREATE UNIQUE INDEX SQL071002115450450 ON CFD_CATDESCUENTOS (IDCDECUENTO ASC)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            sql = " ALTER TABLE CFD_CATDESCUENTOS ADD CONSTRAINT SQL071002115450450 PRIMARY KEY (IDCDECUENTO)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Catalogo de descuentos)");
        }
        try
        {
            sql = "ALTER TABLE CFD_CATDESCUENTOS ADD COLUMN TIPO VARCHAR(2)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            try
            {
                sql = "UPDATE CFD_CATDESCUENTOS SET TIPO = 'D'";
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Valores ya existentes");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Campo tipo en catdescuentos)");
        }
        try
        {
            sql = "ALTER TABLE CFD_DESCUENTOS ADD COLUMN TIPO VARCHAR(2)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            try
            {
                sql = "UPDATE CFD_DESCUENTOS SET TIPO = 'D'";
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Valores ya existentes");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Campo tipo en descuentos)");
        }
    }

    public void CampoISH()
        throws SQLException
    {
        try
        {
            sql = "INSERT INTO cfd_campo VALUES (60, 'traslado_ish', 1, 3 ) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Campo traslado_ish en catalogo de campos dise\361o)");
        }
    }

    public void ArticulosFactura()
        throws SQLException
    {
        try
        {
            sql = "INSERT INTO cfd_campo VALUES (38, 'num_art', 1, 4 ) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Campo clave en catalogo de campos dise\361o)");
        }
        try
        {
            sql = "INSERT INTO CFD_IMPRESION VALUES(38,'',15,15,15,15,0,'',-1,null)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Campo clave de articulo)");
        }
        try
        {
            sql = "ALTER TABLE CFD_CATDESCUENTOS ADD COLUMN EDOS int DEFAULT 1";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Campo estados en descuentos)");
        }
        try
        {
            sql = "INSERT INTO cfd_campo VALUES (39, 'cveart', 1, 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "CREATE TABLE CNET_MEMPRESA (IDMULTIE INTEGER NOT NULL,IDEMPRESA INTEGER,IDENTIDAD INTEGER) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "CREATE UNIQUE INDEX SQL071024062044310 ON CNET_MEMPRESA (IDMULTIE ASC)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Multiples empresas)");
        }
        try
        {
            sql = "ALTER TABLE CNET_MEMPRESA ADD CONSTRAINT SQL071024062044310 PRIMARY KEY (IDMULTIE)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "ALTER TABLE CFD_CAMADE ADD COLUMN COMPANY VARCHAR(25)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "ALTER TABLE ALM_DESC_DETALLE ALTER COLUMN DESCRIPCION SET DATA TYPE VARCHAR(800)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya modificados (Se amplia descripcion a 800 en articulos)");
        }
        try
        {
            sql = "ALTER TABLE cfd_conceptos ALTER COLUMN DESCRIPCION SET DATA TYPE VARCHAR(800)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya modificado (Se amplia descripcion a 800 en conceptos)");
        }
    }

    private long nextID(String campoID, String tabla)
        throws SQLException
    {
        String sql = (new StringBuilder("SELECT MAX(")).append(campoID).append(") as m_id FROM ").append(tabla).toString();
        ResultSet rs = cnx.consulta(sql, true);
        rs.next();
        long id = rs.getLong("m_id");
        if(id == 0L)
            id = 1L;
        else
            id++;
        return id;
    }

    public boolean ControlV(String ver1, String ver2, String ver3)
        throws SQLException
    {
        try
        {
            sql = "ALTER TABLE CNET_MODULOS ADD UP INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna de registro de actualizacion ya exite..");
        }
        sql = (new StringBuilder("SELECT * FROM CNET_MODULOS WHERE IDMODULO = 1 and X = ")).append(ver1).append(" and Y = ").append(ver2).append(" and Z = ").append(ver3).toString();
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            try
            {
                sql = (new StringBuilder("UPDATE CNET_MODULOS SET X = ")).append(ver1).append(", Y = ").append(ver2).append(", Z = ").append(ver3).append(", UP = 1 WHERE IDMODULO = 1").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
                System.out.println("Se actualiza la version");
            }
            catch(SQLException e)
            {
                System.out.println("No se realizo actualizacion de version");
            }
            return true;
        } else
        {
            System.out.println((new StringBuilder("La version: ")).append(ver1).append(".").append(ver2).append(".").append(ver3).append(" ya ha sido actualizada").toString());
            return false;
        }
    }

    public void Precision()
        throws SQLException
    {
        try
        {
            sql = "ALTER TABLE CFD_CONFIG ADD REDONDEA INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza precision ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna de precision de actualizacion ya exite..");
        }
    }

    public void BackUpMode()
        throws SQLException
    {
        try
        {
            sql = "SELECT * FROM CNET_CAT_MOVTOS WHERE DESCRIPCION = 'BACKUP'";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                String imagen = ".\\resources\\icons\\backup.bmp";
                sql = (new StringBuilder("INSERT INTO CNET_CAT_MOVTOS(IDCATMOVTO,DESCRIPCION,NIVEL,SUBNIVEL,X,Y,Z,VENTANA,SMALL_PICTURE,LARGE_PICTURE) VALUES (")).append(nextID("IDCATMOVTO", "CNET_CAT_MOVTOS")).append(",'BACKUP',4,8,0,0,1,'w_backup','").append(imagen).append("','").append(imagen).append("')").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            sql = "SELECT * FROM CNET_MODTOS WHERE IDMODULO = 1 and IDCATMOVTO = 33";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CNET_MODTOS(IDMODTO,IDMODULO,IDCATMOVTO) VALUES (")).append(nextID("IDMODTO", "CNET_MODTOS")).append(",1,33)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            sql = "SELECT * FROM CNET_PERMISOS WHERE IDUSUARIO = 1 AND IDMODTO = 15";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = "INSERT INTO CNET_PERMISOS(IDUSUARIO,IDMODTO,ESTADO) VALUES (1,15,'A')";
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            System.out.println("Se han actualizado (BackUpMode)");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (BackUpMode)");
        }
    }

    public void ActulalizaTDocto()
        throws SQLException
    {
        try
        {
            sql = "ALTER TABLE CFD_TDOCTOS ALTER COLUMN LDESC TYPE VARCHAR(80)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "ALTER TABLE CFD_TDOCTOS ALTER COLUMN SDESC TYPE VARCHAR(80)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "UPDATE CFD_TDOCTOS SET SDESC = 'RECIBO DE HONORARIOS' WHERE IDTDOCTO = 2";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes");
        }
        try
        {
            sql = "SELECT * FROM CFD_TDOCTOS WHERE IDTDOCTO = 5";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_TDOCTOS(IDTDOCTO, LDESC, SDESC, ESTADO) VALUES (")).append(nextID("IDTDOCTO", "CFD_TDOCTOS")).append(",'RECIBO DE PAGO','RECIBO DE PAGO','A')").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Recibo de pago)");
        }
        try
        {
            sql = "SELECT * FROM CFD_TDOCTOS WHERE IDTDOCTO = 6";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = (new StringBuilder("INSERT INTO CFD_TDOCTOS(IDTDOCTO, LDESC, SDESC, ESTADO) VALUES (")).append(nextID("IDTDOCTO", "CFD_TDOCTOS")).append(",'RECIBO DE ARRENDAMIENTO','RECIBO DE ARRENDAMIENTO','A')").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Recibo de arrendamiento)");
        }
    }

    public void RazonSocial()
        throws SQLException
    {
        System.out.println("Actualizando Razon Social");
        try
        {
            sql = "ALTER TABLE CNET_ENTIDADES ALTER COLUMN NOMBRE TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CNET_ENTIDADES)");
        }
        try
        {
            sql = "ALTER TABLE CNET_PROVEEDOR ALTER COLUMN RAZON_SOCIAL TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CNET_PROVEEDOR)");
        }
        try
        {
            sql = "ALTER TABLE CNET_CLIENTE ALTER COLUMN RAZON_SOCIAL TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CNET_CLIENTE)");
        }
        try
        {
            sql = "ALTER TABLE CNET_EMPRESAALTER COLUMN NOMBRECOMERCIAL TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CNET_EMPRESAALTER)");
        }
        try
        {
            sql = "ALTER TABLE CNET_SUCURSAL ALTER COLUMN DESCRIPCION TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CNET_SUCURSAL)");
        }
    }

    public void Vacios_Addenda()
    {
        try
        {
            sql = "ALTER TABLE CFD_ADDENDA ADD VACIO INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Vacios_Addenda)");
        }
    }

    public void Cfg_Rango_Fechas()
    {
        try
        {
            sql = "ALTER TABLE CFD_CONFIG ADD RDATE INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza rango de fechas ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna rango de fechas ya exite..");
        }
    }

    public void FOLIOS_APROB()
    {
        try
        {
            sql = "ALTER TABLE CFD_FOLIOS ADD AAPROB INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza anio de aprobacion ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna anio de aprobacion ya exite..");
        }
    }

    public void Desc_Entidad()
    {
        try
        {
            sql = "ALTER TABLE CNET_CLIENTE ALTER COLUMN RAZON_SOCIAL TYPE VARCHAR(800)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza RAZON_SOCIAL ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna RAZON_SOCIAL ya exite..");
        }
        try
        {
            sql = "ALTER TABLE CNET_EMPRESA ALTER COLUMN NOMBRECOMERCIAL TYPE VARCHAR(800)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza NOMBRECOMERCIAL ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna NOMBRECOMERCIAL ya exite..");
        }
        try
        {
            sql = "ALTER TABLE CNET_ENTIDADES ALTER COLUMN NOMBRE TYPE VARCHAR(800)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza NOMBRE ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna NOMBRE ya exite..");
        }
        try
        {
            sql = "ALTER TABLE CNET_PROVEEDOR ALTER COLUMN RAZON_SOCIAL TYPE VARCHAR(800)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza RAZON_SOCIAL ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna RAZON_SOCIAL ya exite..");
        }
    }

    public void DetallistaCom()
        throws SQLException
    {
        long ll_idadd = 0L;
        long ll_iddetallista = 0L;
        long ll_idRQFP = 0L;
        long ll_idSI = 0L;
        long ll_idORI = 0L;
        long ll_idADI = 0L;
        long ll_idDVN = 0L;
        long ll_idBYR = 0L;
        long ll_idSLR = 0L;
        long ll_idSHT = 0L;
        long ll_idINC = 0L;
        long ll_idCTM = 0L;
        long ll_idCRC = 0L;
        long ll_idPTM = 0L;
        long ll_idSHD = 0L;
        long ll_idACH = 0L;
        long ll_idLIT = 0L;
        long ll_idTAM = 0L;
        long ll_idTAC = 0L;
        long ll_idcampo = 0L;
        sql = "SELECT * FROM CFD_ADDENDA WHERE DESCRIPCION = 'Detallista'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idadd = nextID("IDADD", "CFD_ADDENDA");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_ADDENDA (IDADD, DESCRIPCION, VACIO, TIPO) VALUES (")).append(ll_idadd).append(", 'Detallista', 0,2)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna Detallista ya exite..");
            }
        } else
        {
            ll_idadd = rs.getLong("IDADD");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:detallista'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_iddetallista = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_iddetallista).append(", NULL, NULL, 'detallista:detallista', NULL, 2, NULL, 1, 0, 0, 0, 1, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:detallista ya exite..");
            }
        } else
        {
            ll_iddetallista = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'type'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idcampo = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idcampo).append(", NULL, NULL, 'type', '', 2, 'Detallista', 1, 52, 0, ").append(ll_iddetallista).append(", 2, 0, 'SimpleInvoiceType', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna type ya exite..");
            }
        } else
        {
            ll_idcampo = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'contentVersion'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idcampo = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idcampo).append(", NULL, NULL, 'contentVersion', '', 2, 'Detallista', 2, 97, 0, ").append(ll_iddetallista).append(", 2, 0, '1.3.1',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna contentVersion ya exite..");
            }
        } else
        {
            ll_idcampo = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'documentStructureVersion'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idcampo = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idcampo).append(", NULL, NULL, 'documentStructureVersion', '', 2, 'Detallista', 3, 97, 0, ").append(ll_iddetallista).append(", 2, 1, 'AMC8.1',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna documentStructureVersion ya exite..");
            }
        } else
        {
            ll_idcampo = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'documentStatus'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idcampo = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idcampo).append(", NULL, NULL, 'documentStatus', '', 2, 'Detallista', 4, 108, 0, ").append(ll_iddetallista).append(", 2, 1, 'ORIGINAL',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna documentStatus ya exite..");
            }
        } else
        {
            ll_idcampo = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:requestForPaymentIdentification'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idRQFP = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idRQFP).append(", NULL, NULL, 'detallista:requestForPaymentIdentification', '', 2, 'Detallista', 1, 0, ").append(ll_iddetallista).append(", 0, 2, 1, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:requestForPaymentIdentification ya exite..");
            }
        } else
        {
            ll_idRQFP = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:specialInstruction'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idSI = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idSI).append(", NULL, NULL, 'detallista:specialInstruction', '', 2, 'Detallista', 2, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:specialInstruction ya exite..");
            }
        } else
        {
            ll_idSI = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:orderIdentification'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idORI = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idORI).append(", NULL, NULL, 'detallista:orderIdentification', '', 2, 'Detallista', 3, 0, ").append(ll_iddetallista).append(", 0, 2, 1, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:orderIdentification ya exite..");
            }
        } else
        {
            ll_idORI = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:AdditionalInformation'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idADI = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idADI).append(", NULL, NULL, 'detallista:AdditionalInformation', '', 2, 'Detallista', 4, 0, ").append(ll_iddetallista).append(", 0, 2, 1, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:AdditionalInformation ya exite..");
            }
        } else
        {
            ll_idADI = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:DeliveryNote'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idDVN = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idDVN).append(", NULL, NULL, 'detallista:DeliveryNote', '', 2, 'Detallista', 5, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:DeliveryNote ya exite..");
            }
        } else
        {
            ll_idDVN = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:buyer'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idBYR = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idBYR).append(", NULL, NULL, 'detallista:buyer', '', 2, 'Detallista', 6, 0, ").append(ll_iddetallista).append(", 0, 2, 1, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:buyer ya exite..");
            }
        } else
        {
            ll_idBYR = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:seller'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idSLR = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idSLR).append(", NULL, NULL, 'detallista:seller', '', 2, 'Detallista', 7, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:seller ya exite..");
            }
        } else
        {
            ll_idSLR = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:shipTo'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idSHT = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idSHT).append(", NULL, NULL, 'detallista:shipTo', '', 2, 'Detallista', 8, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:shipTo ya exite..");
            }
        } else
        {
            ll_idSHT = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:InvoiceCreator'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idINC = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idINC).append(", NULL, NULL, 'detallista:InvoiceCreator', '', 2, 'Detallista', 9, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:InvoiceCreator ya exite..");
            }
        } else
        {
            ll_idINC = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:Customs'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idCTM = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idCTM).append(", NULL, NULL, 'detallista:Customs', '', 2, 'Detallista', 10, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:Customs ya exite..");
            }
        } else
        {
            ll_idCTM = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:currency'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idCRC = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idCRC).append(", NULL, NULL, 'detallista:currency', '', 2, 'Detallista', 11, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:currency ya exite..");
            }
        } else
        {
            ll_idCRC = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:paymentTerms'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idPTM = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idPTM).append(", NULL, NULL, 'detallista:paymentTerms', '', 2, 'Detallista', 12, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '',  ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna Detallista ya exite..");
            }
        } else
        {
            ll_idPTM = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:shipmentDetail'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idSHD = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idSHD).append(", NULL, NULL, 'detallista:shipmentDetail', '', 2, 'Detallista', 13, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:shipmentDetail ya exite..");
            }
        } else
        {
            ll_idSHD = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:allowanceCharge'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idACH = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idACH).append(", NULL, NULL, 'detallista:allowanceCharge', '', 2, 'Detallista', 14, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:allowanceCharge ya exite..");
            }
        } else
        {
            ll_idACH = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:lineItem'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idLIT = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idLIT).append(", NULL, NULL, 'detallista:lineItem', '', 2, 'Detallista', 15, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:lineItem ya exite..");
            }
        } else
        {
            ll_idLIT = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:totalAmount'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idTAM = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idTAM).append(", NULL, NULL, 'detallista:totalAmount', '', 2, 'Detallista', 16, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:totalAmount ya exite..");
            }
        } else
        {
            ll_idTAM = rs.getLong("IDCAMPO");
        }
        sql = "SELECT * FROM CFD_CAMADE WHERE NOMBRE = 'detallista:TotalAllowanceCharge'";
        rs = cnx.consulta(sql, true);
        if(!rs.next())
        {
            ll_idTAC = nextID("IDCAMPO", "CFD_CAMADE");
            try
            {
                sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (")).append(ll_idTAC).append(", NULL, NULL, 'detallista:TotalAllowanceCharge', '', 2, 'Detallista', 17, NULL, ").append(ll_iddetallista).append(", 0, 2, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
                rs = cnx.consulta(sql, false);
                cnx.commit();
            }
            catch(SQLException e)
            {
                System.out.println("Columna detallista:TotalAllowanceCharge ya exite..");
            }
        } else
        {
            ll_idTAC = rs.getLong("IDCAMPO");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (251, NULL, NULL, 'detallista:entityType', '', 2, 'Detallista', 1, 110, ")).append(ll_idRQFP).append(", 0, 3, 1, 'INVOICE', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (252, NULL, NULL, 'detallista:text', '', 2, 'Detallista', 1, 43, ")).append(ll_idSI).append(", 0, 3, 1, 'HOLA MUNDO', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (253, NULL, NULL, 'code', '', 2, 'Detallista', 1, 111, 0, ")).append(ll_idSI).append(", 3, 1, 'PUR', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (254, NULL, NULL, 'detallista:referenceIdentification', '', 2, 'Detallista', 1, 52, ")).append(ll_idORI).append(", 0, 3, 1, '125546', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (255, NULL, NULL, 'detallista:ReferenceDate', '', 2, 'Detallista', 2, 106, ")).append(ll_idORI).append(", 0, 3, 0, '2009-10-22', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (256, NULL, NULL, 'type', '', 2, 'Detallista', 1, 65, 0, 254, 4, 1, 'ON', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (257, NULL, NULL, 'detallista:referenceIdentification', '', 2, 'Detallista', 1, 52, ")).append(ll_idADI).append(", 0, 3, 1, '6545465', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (258, NULL, NULL, 'type', '', 2, 'Detallista', 1, 113, 0, 257, 4, 1, 'ACE', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (259, NULL, NULL, 'detallista:referenceIdentification', '', 2, 'Detallista', 1, 52, ")).append(ll_idDVN).append(", 0, 3, 1, '5454545', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (260, NULL, NULL, 'detallista:ReferenceDate', '', 2, 'Detallista', 2, 106, ")).append(ll_idDVN).append(", 0, 3, 0, '2009-10-23', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (261, NULL, NULL, 'detallista:gln', '', 2, 'Detallista', 1, 68, ")).append(ll_idBYR).append(", 0, 3, 1, '54546544578', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (262, NULL, NULL, 'detallista:contactInformation', '', 2, 'Detallista', 2, 0, ")).append(ll_idBYR).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (263, NULL, NULL, 'detallista:personOrDepartmentName', '', 2, 'Detallista', 1, 0, 262, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (264, NULL, NULL, 'detallista:text', '', 2, 'Detallista', 1, 52, 263, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (265, NULL, NULL, 'detallista:gln', '', 2, 'Detallista', 1, 68, ")).append(ll_idSLR).append(", 0, 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (266, NULL, NULL, 'detallista:alternatePartyIdentification', '', 2, 'Detallista', 2, 52, ")).append(ll_idSLR).append(", 0, 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (267, NULL, NULL, 'type', '', 2, 'Detallista', 1, 114, 0, 266, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (268, NULL, NULL, 'detallista:gln', '', 2, 'Detallista', 1, 68, ")).append(ll_idSHT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (269, NULL, NULL, 'detallista:nameAndAddress', '', 2, 'Detallista', 2, NULL, ")).append(ll_idSHT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (270, NULL, NULL, 'detallista:name', '', 2, 'Detallista', 1, 52, 269, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (271, NULL, NULL, 'detallista:streetAddressOne', '', 2, 'Detallista', 2, 52, 269, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (272, NULL, NULL, 'detallista:city', '', 2, 'Detallista', 3, 52, 269, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (273, NULL, NULL, 'detallista:postalCode', '', 2, 'Detallista', 4, 115, 269, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (274, NULL, NULL, 'detallista:gln', '', 2, 'Detallista', 1, 68, ")).append(ll_idINC).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (275, NULL, NULL, 'detallista:alternatePartyIdentification', '', 2, 'Detallista', 2, 52, ")).append(ll_idINC).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (276, NULL, NULL, 'detallista:nameAndAddress', '', 2, 'Detallista', 3, NULL, ")).append(ll_idINC).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (277, NULL, NULL, 'type', '', 2, 'Detallista', 1, 116, 0, 275, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (278, NULL, NULL, 'detallista:name', '', 2, 'Detallista', 1, 52, 276, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (279, NULL, NULL, 'detallista:streetAddressOne', '', 2, 'Detallista', 2, 52, 276, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (280, NULL, NULL, 'detallista:city', '', 2, 'Detallista', 3, 52, 276, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (281, NULL, NULL, 'detallista:postalCode', '', 2, 'Detallista', 4, 115, 276, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (282, NULL, NULL, 'detallista:gln', '', 2, 'Detallista', 1, 68, ")).append(ll_idCTM).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (283, NULL, NULL, 'currencyISOCode', '', 2, 'Detallista', 1, 117, 0, ")).append(ll_idCRC).append(", 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (284, NULL, NULL, 'detallista:currencyFunction', '', 2, 'Detallista', 1, 73, ")).append(ll_idCRC).append(", 0, 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (285, NULL, NULL, 'detallista:rateOfChange', '', 2, 'Detallista', 2, 118, ")).append(ll_idCRC).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (286, NULL, NULL, 'paymentTermsEvent', '', 2, 'Detallista', 1, 74, 0, ")).append(ll_idPTM).append(", 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (287, NULL, NULL, 'PaymentTermsRelationTime', '', 2, 'Detallista', 2, 75, 0, ")).append(ll_idPTM).append(", 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (288, NULL, NULL, 'detallista:netPayment', '', 2, 'Detallista', 1, NULL, ")).append(ll_idPTM).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (289, NULL, NULL, 'detallista:discountPayment', '', 2, 'Detallista', 2, NULL, ")).append(ll_idPTM).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (290, NULL, NULL, 'netPaymentTermsType', '', 2, 'Detallista', 1, 76, 0, 288, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (291, NULL, NULL, 'detallista:paymentTimePeriod', '', 2, 'Detallista', 1, NULL, 288, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (292, NULL, NULL, 'detallista:timePeriodDue', '', 2, 'Detallista', 1, 76, 291, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (293, NULL, NULL, 'timePeriod', '', 2, 'Detallista', 1, 77, 0, 292, 6, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (294, NULL, NULL, 'detallista:value', '', 2, 'Detallista', 1, 107, 292, 0, 6, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (295, NULL, NULL, 'discountType', '', 2, 'Detallista', 1, 119, 0, 289, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (296, NULL, NULL, 'detallista:percentage', '', 2, 'Detallista', 1, 107, 289, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (297, NULL, NULL, 'allowanceChargeType', '', 2, 'Detallista', 1, 120, 0, ")).append(ll_idACH).append(", 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (298, NULL, NULL, 'settlementType', '', 2, 'Detallista', 2, 121, 0, ")).append(ll_idACH).append(", 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (299, NULL, NULL, 'sequenceNumber', '', 2, 'Detallista', 3, 81, 0, ")).append(ll_idACH).append(", 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (300, NULL, NULL, 'detallista:specialServicesType', '', 2, 'Detallista', 1, 82, ")).append(ll_idACH).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (301, NULL, NULL, 'detallista:monetaryAmountOrPercentage', '', 2, 'Detallista', 2, NULL, ")).append(ll_idACH).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (302, NULL, NULL, 'detallista:rate', '', 2, 'Detallista', 1, NULL, 301, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (303, NULL, NULL, 'base', '', 2, 'Detallista', 1, 122, 0, 302, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (304, NULL, NULL, 'detallista:percentage', '', 2, 'Detallista', 1, 123, 302, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (305, NULL, NULL, 'type', '', 2, 'Detallista', 1, 84, 0, ")).append(ll_idLIT).append(", 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (306, NULL, NULL, 'number', '', 2, 'Detallista', 2, 124, 0, ")).append(ll_idLIT).append(", 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (307, NULL, NULL, 'detallista:tradeItemIdentification', '', 2, 'Detallista', 1, 124, ")).append(ll_idLIT).append(", 0, 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (308, NULL, NULL, 'detallista:alternateTradeItemIdentification', '', 2, 'Detallista', 2, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (309, NULL, NULL, 'detallista:tradeItemDescriptionInformation', '', 2, 'Detallista', 3, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (310, NULL, NULL, 'detallista:invoicedQuantity', '', 2, 'Detallista', 4, 125, ")).append(ll_idLIT).append(", 0, 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (311, NULL, NULL, 'detallista:aditionalQuantity', '', 2, 'Detallista', 5, 125, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (312, NULL, NULL, 'detallista:grossPrice', '', 2, 'Detallista', 6, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (313, NULL, NULL, 'detallista:netPrice', '', 2, 'Detallista', 7, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (314, NULL, NULL, 'detallista:AdditionalInformation', '', 2, 'Detallista', 8, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (315, NULL, NULL, 'detallista:Customs', '', 2, 'Detallista', 9, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (316, NULL, NULL, 'detallista:LogisticUnits', '', 2, 'Detallista', 10, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (317, NULL, NULL, 'detallista:palletInformation', '', 2, 'Detallista', 11, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (318, NULL, NULL, 'detallista:extendedAttributes', '', 2, 'Detallista', 12, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (319, NULL, NULL, 'detallista:allowanceCharge', '', 2, 'Detallista', 13, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (320, NULL, NULL, 'detallista:tradeItemTaxInformation', '', 2, 'Detallista', 14, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (321, NULL, NULL, 'detallista:totalLineAmount', '', 2, 'Detallista', 15, NULL, ")).append(ll_idLIT).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (322, NULL, NULL, 'detallista:gtin', '', 2, 'Detallista', 1, 70, 307, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (323, NULL, NULL, 'type', '', 2, 'Detallista', 1, 86, 0, 308, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (324, NULL, NULL, 'language', '', 2, 'Detallista', 1, 87, 0, 309, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (325, NULL, NULL, 'detallista:longText', '', 2, 'Detallista', 1, 52, 309, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (326, NULL, NULL, 'unitOfMeasure', '', 2, 'Detallista', 1, 126, 0, 310, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (327, NULL, NULL, 'QuantityType', '', 2, 'Detallista', 1, 127, 0, 311, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (328, NULL, NULL, 'detallista:Amount', '', 2, 'Detallista', 1, 125, 312, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (329, NULL, NULL, 'detallista:Amount', '', 2, 'Detallista', 1, 125, 313, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (330, NULL, NULL, 'detallista:referenceIdentification', '', 2, 'Detallista', 1, NULL, 314, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (331, NULL, NULL, 'type', '', 2, 'Detallista', 1, 65, 0, 330, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (332, NULL, NULL, 'detallista:gln', '', 2, 'Detallista', 1, 70, 315, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (333, NULL, NULL, 'detallista:alternatePartyIdentification', '', 2, 'Detallista', 2, 52, 315, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (334, NULL, NULL, 'detallista:ReferenceDate', '', 2, 'Detallista', 3, 112, 315, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (335, NULL, NULL, 'detallista:nameAndAddress', '', 2, 'Detallista', 4, 112, 315, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (336, NULL, NULL, 'type', '', 2, 'Detallista', 1, 71, 0, 333, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (337, NULL, NULL, 'detallista:name', '', 2, 'Detallista', 1, 52, 335, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (338, NULL, NULL, 'detallista:serialShippingContainerCode', '', 2, 'Detallista', 1, 128, 316, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (339, NULL, NULL, 'type', '', 2, 'Detallista', 1, 129, 0, 338, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (340, NULL, NULL, 'detallista:palletQuantity', '', 2, 'Detallista', 1, 81, 317, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (341, NULL, NULL, 'detallista:description', '', 2, 'Detallista', 2, 81, 317, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (342, NULL, NULL, 'detallista:transport', '', 2, 'Detallista', 3, 130, 317, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (343, NULL, NULL, 'type', '', 2, 'Detallista', 1, 130, 0, 341, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (344, NULL, NULL, 'detallista:methodOfPayment', '', 2, 'Detallista', 1, 131, 342, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (345, NULL, NULL, 'detallista:lotNumber', '', 2, 'Detallista', 1, 52, 318, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (346, NULL, NULL, 'productionDate', '', 2, 'Detallista', 1, 132, 0, 345, 5, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (347, NULL, NULL, 'allowanceChargeType', '', 2, 'Detallista', 1, 133, 0, 319, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (348, NULL, NULL, 'settlementType', '', 2, 'Detallista', 2, 134, 0, 319, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (349, NULL, NULL, 'sequenceNumber', '', 2, 'Detallista', 3, 81, 0, 319, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (350, NULL, NULL, 'detallista:specialServicesType', '', 2, 'Detallista', 1, 135, 319, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (351, NULL, NULL, 'detallista:monetaryAmountOrPercentage', '', 2, 'Detallista', 2, 135, 319, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (352, NULL, NULL, 'detallista:percentagePerUnit', '', 2, 'Detallista', 1, 107, 351, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (353, NULL, NULL, 'detallista:ratePerUnit', '', 2, 'Detallista', 2, NULL, 351, 0, 5, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (354, NULL, NULL, 'detallista:amountPerUnit', '', 2, 'Detallista', 1, 52, 353, 0, 6, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (355, NULL, NULL, 'detallista:taxTypeDescription', '', 2, 'Detallista', 1, 136, 320, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (356, NULL, NULL, 'detallista:referenceNumber', '', 2, 'Detallista', 2, 101, 320, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (357, NULL, NULL, 'detallista:tradeItemTaxAmount', '', 2, 'Detallista', 3, NULL, 320, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (358, NULL, NULL, 'detallista:taxCategory', '', 2, 'Detallista', 4, 137, 320, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (359, NULL, NULL, 'detallista:taxPercentage', '', 2, 'Detallista', 1, 125, 357, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (360, NULL, NULL, 'detallista:taxAmount', '', 2, 'Detallista', 2, 125, 357, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (361, NULL, NULL, 'detallista:grossAmount', '', 2, 'Detallista', 1, NULL, 321, 0, 4, 0, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (362, NULL, NULL, 'detallista:netAmount', '', 2, 'Detallista', 2, 125, 321, 0, 4, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (363, NULL, NULL, 'detallista:Amount', '', 2, 'Detallista', 1, 125, 361, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (364, NULL, NULL, 'detallista:Amount', '', 2, 'Detallista', 1, 125, 362, 0, 5, 1, '', ")).append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (365, NULL, NULL, 'detallista:Amount', '', 2, 'Detallista', 1, 125, ")).append(ll_idTAM).append(", 0, 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (366, NULL, NULL, 'allowanceOrChargeType', '', 2, 'Detallista', 1, 138, 0, ")).append(ll_idTAC).append(", 3, 1, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (367, NULL, NULL, 'detallista:specialServicesType', '', 2, 'Detallista', 1, 135, ")).append(ll_idTAC).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
        try
        {
            sql = (new StringBuilder("INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (368, NULL, NULL, 'detallista:Amount', '', 2, 'Detallista', 2, 125, ")).append(ll_idTAC).append(", 0, 3, 0, '', ").append(ll_idadd).append(", 1, 0)").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Detallista ya exite..");
        }
    }

    public void HomeDepotADD()
        throws SQLException
    {
        try
        {
            sql = "INSERT INTO CFD_ADDENDA (IDADD, DESCRIPCION, VACIO) VALUES (4, 'Home Depot', 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (209, NULL, NULL, 'Encabezado', NULL, 1, '', 1, NULL, 0, 0, 1, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (210, NULL, NULL, 'fecha', '', 1, 'Home Depot', 1, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (211, NULL, NULL, 'total', '', 1, 'Home Depot', 2, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (212, NULL, NULL, 'iva', '', 1, 'Home Depot', 3, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (213, NULL, NULL, 'divisa', '', 1, 'Home Depot', 4, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (214, NULL, NULL, 'proveedor', '', 1, 'Home Depot', 5, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (215, NULL, NULL, 'folio', '', 1, 'Home Depot', 6, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (216, NULL, NULL, 'subtotal', '', 1, 'Home Depot', 7, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (217, NULL, NULL, 'tipoCambio', '', 1, 'Home Depot', 8, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (218, NULL, NULL, 'serie', '', 1, 'Home Depot', 9, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (219, NULL, NULL, 'oc', '', 1, 'Home Depot', 10, NULL, 0, 209, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (220, NULL, NULL, 'INILISTAPROD', NULL, 1, '', 2, NULL, 0, 0, 1, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (221, NULL, NULL, 'Detalle', '', 1, 'Home Depot', 1, NULL, 220, 0, 2, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (222, NULL, NULL, 'unidad', '', 1, 'Home Depot', 1, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (223, NULL, NULL, 'valor', '', 1, 'Home Depot', 2, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (224, NULL, NULL, 'IVA', '', 1, 'Home Depot', 3, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (225, NULL, NULL, 'vendorPack', '', 1, 'Home Depot', 4, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (226, NULL, NULL, 'cantidad', '', 1, 'Home Depot', 5, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (227, NULL, NULL, 'descripcion', '', 1, 'Home Depot', 6, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
        try
        {
            sql = "INSERT INTO CFD_CAMADE (IDCAMPO, IDEMPRESA, IDSUCURSAL, NOMBRE, FORMATO, PLANTILLA, COMPANY, ORDEN, IDFORMATO, IDPADRE, IDATRIBUTO, NIVEL, REQUERIDO, DEFT_VAL, IDADD, STATUS, IDASC) VALUES (228, NULL, NULL, 'SKU', '', 1, 'Home Depot', 7, NULL, 0, 221, 3, 0, NULL, 4, 1, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Columna Home Depot ya exite..");
        }
    }

    public void Formatos()
    {
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (7, 'DTM', 'yyyy', 4)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (5, 'DTM', 'mm/dd/yyyy', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (106, 'DTM', 'yyyy-mm-dd', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (8, 'DTM', 'yyyy', 4)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (9, 'DTM', 'mm/dd/yy', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (4, 'DTM', 'mm/dd/yyyy', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (12, 'DCM', '#.#', 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (74, 'TKN', 'DATE_OF_INVOICE,EFFECTIVE_DATE', 30)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (14, 'STM', '^', 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (13, 'NUM', '#', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (55, 'NUM', '#', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (16, 'DCM', '#', 9)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (45, 'TKN', 'COPIAR,PEGAR,CORTAR', 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (56, 'DCM', '####.###', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (58, 'STM', 'a', 12)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (75, 'TKN', 'REFERENCE_AFTER,', 16)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (67, 'STM', 'a', 17)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (76, 'TKN', 'BASIC_NET,END_OF_MONTH,BASIC_DISCOUNT_OFFERED', 45)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (22, 'NUM', '#', 4)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (59, 'TKN', 'ORIGINAL,COPY,DELETE,REMPLAZA', 29)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (60, 'DTM', 'yyyy/mm/dd', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (61, 'TKN', 'INVOICE,DEBIT_NOTE,CREDIT_NOTE,LEASE_RECEIPT,HONORARY_RECEIPT,AUTO_INVOICE,PARTIAL_INVOICE,TRANSPORT_DOCUMENT', 113)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (77, 'TKN', 'DAYS,', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (46, 'DCM', '####.##', 7)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (47, 'TKN', 'INICIO,FIN', 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (62, 'TKN', 'AAB,DUT,PUR,ZZZ', 15)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (68, 'STM', 'a', 13)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (32, 'TMM', 'hh:mm:ss', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (78, 'TKN', 'ALLOWANCE_BY_PAYMENT_ON_TIME,SANCTION', 37)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (64, 'TKN', 'ON', 2)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (79, 'TKN', 'ALLOWANCE_GLOBAL,CHARGE_GLOBAL', 30)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (65, 'TKN', 'ON,', 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (80, 'TKN', 'BILL_BACK,OFF_INVOICE', 21)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (66, 'TKN', 'AEE,CK,ACE,ATZ,AWR,ON,DQ,IV', 28)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (69, 'TKN', 'SELLER_ASSIGNED_IDENTIFIER_FOR_A_PARTY,IEPS_REFERENCE', 54)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (81, 'STM', 'a', 15)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (82, 'TKN', 'AA,ABZ,ADS,ADT,ADO,AJ,CAC,COD,DA,DI,EAA,EAB,FA,FC,FG,FI,HD,QD,PAD,PI,QD,RAA,SAB,TAE,TD,TS,TX,TZ,UM,VAB,ZZZ', 106)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (83, 'TKN', 'INVOICE_VALUE', 13)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (70, 'STM', 'a', 14)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (84, 'STM', 'a', 32)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (71, 'TKN', 'TN,', 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (72, 'TKN', 'MXN,USD,XEU', 11)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (73, 'TKN', 'BILLING_CURRENCY,PRICE_CURRENCY,PAYMENT_CURRENCY', 48)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (86, 'TKN', 'BUYER_ASSIGNED,SUPPLIER_ASSIGNED,GLOBAL_TRADE_ITEM_IDENTIFICATION,SERIAL_NUMBER', 79)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (87, 'TKN', 'ES,EN', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (48, 'DCM', '####.####', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (88, 'TKN', '001,002,003,004,ACR,AMT,APX,ASM,ASV,BTU,CA,C0,CEL,CMK,CMT,DMT,EA,FAH,FOT,GJO,GLI,GRM,GWH,HLT,INH,JOU,KBA,KEL,KGM,KHZ,KJO,KVT,KWH,KWT,LNE,LTR,MAL,MAW,MGM,MHZ,MIN,MLT,MMT,MTK,MTQ,MTR,MWH,NAR,NRL,ONZ,OZA,OZI,PA,P1,PCE,PGE,PND,PPM,PTI,PTN,QTI,RTO,SEC,ST,TNE,', 273)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (49, 'DCM', '############', 12)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (89, 'TKN', 'NUM_CONSUMER_UNITS,FREE_GOODS', 29)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (50, 'DCM', '###', 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (51, 'DCM', '###.###', 7)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (52, 'STM', 'a', 35)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (53, 'STM', '#', 15)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (54, 'STM', '#', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (17, '', 'a', 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (42, 'TMM', 'hh:mm', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (92, 'STM', 'a', 83)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (93, 'STM', 'a', 1)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (43, 'STM', 'a', 200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (94, 'TKN', 'BJ,SRV', 6)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (95, 'TKN', 'EXCHANGE_PALLETS,RETURN_PALLETS,PALLET_ 80x100,CASE,BOX', 55)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (96, 'TKN', 'PREPAID_BY_SELLER,PAID_BY_BUYER', 31)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (97, 'STM', 'a', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (98, 'DTM', 'yy/mm/dd', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (99, 'TKN', 'OFF_INVOICE,CHARGE_TO_BE_PAID_BY_VENDOR,CHARGE_TO_BE_PAID_BY_CUSTOMER', 69)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (100, 'TKN', 'LAC,VAT,GST,AAA,AAD,FRE,LOC,STT,OTH', 35)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (101, 'STM', 'a', 20)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (102, 'TKN', 'TRANSFERIDO,RETENIDO', 20)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (103, 'TKN', 'ALLOWANCE,CHARGE', 16)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (104, 'TKN', 'GST,VAT,LAC', 11)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (105, 'TKN', 'AAB,DUT,PUR,ZZZ,', 16)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (107, 'STM', 'a', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (108, 'TKN', 'ORIGINAL,COPY,DELETE,REMPLAZA,', 30)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (109, 'TKN', 'INVOICE,DEBIT_NOTE', 18)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (110, 'TKN', 'INVOICE,DEBIT_NOTE,CREDIT_NOTE,LEASE_RECEIPT,HONORARY_RECEIPT,AUTO_INVOICE,PARTIAL_INVOICE,TRANSPORT_DOCUMENT', 109)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (111, 'TKN', 'DUT,PUR,ZZZ,', 12)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (112, 'DTM', 'yyyymmdd', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (113, 'TKN', 'ACE,ATZ,AWR,ON,DQ,', 18)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (114, 'TKN', 'SELLER_ASSIGNED_IDENTIFIER_FOR_A_PARTY,IEPS_REFERENCE', 53)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (115, 'STM', 'a', 9)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (116, 'TKN', 'IA,', 3)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (117, 'TKN', 'MXN,USD,XEU,', 12)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (118, 'DCM', '###.##', 6)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (119, 'TKN', 'ALLOWANCE_BY_PAYMENT_ON_TIME,SANCTION,', 38)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (120, 'TKN', 'ALLOWANCE,CHARGE_GLOBAL', 23)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (121, 'TKN', 'BILL_BACK,OFF_INVOICE,', 22)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (122, 'TKN', 'INVOICE_VALUE,', 14)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (123, 'DCM', '#######.##', 10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (124, 'NUM', '#', 5)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (125, 'DCM', '##########.##', 13)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (126, 'TKN', 'EDIFACT,', 8)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (127, 'TKN', 'NUM_CONSUMER_UNITS,FREE_GOODS,', 30)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (128, 'TKN', 'RANGOS,', 7)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (129, 'TKN', 'BJ,SRV,', 7)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (130, 'TKN', 'EXCHANGE_PALLETS,RETURN_PALLETS,PALLET_ 80x100,CASE,BOX,', 56)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (131, 'TKN', 'PREPAID_BY_SELLER,PAID_BY_BUYER,', 32)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (132, 'DTM', 'yymmdd', 6)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (133, 'TKN', 'ALLOWANCE,CHARGE_GLOBAL,', 24)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (134, 'TKN', 'OFF_INVOICE,CHARGE_TO_BE_PAID_BY_VENDOR,CHARGE_TO_BE_PAID_BY_CUSTOMER,', 70)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (135, 'TKN', 'AA,ABZ,ADS,ADT,ADO,AJ,CAC,COD,DA,DI,EAA,EAB,FA,FC,FG,FI,HD,QD,PAD,PI,QD,RAA,SAB,TAE,TD,TS,TX,TZ,UM,VAB,ZZZ,', 107)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (136, 'TKN', 'LAC,VAT,GST,AAA,AAD,FRE,LOC,STT,OTH,', 36)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (137, 'TKN', 'TRANSFERIDO,RETENIDO,', 21)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_DATAFORMAT (IDFORMAT, FORMATO, MASCARA, LONGITUD) VALUES (138, 'TKN', 'ALLOWANCE,CHARGE,', 17)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Formato ya existe...");
        }
    }

    public void CLAVE_ENTIDAD()
    {
        try
        {
            sql = "ALTER TABLE CNET_ENTIDADES ADD CLAVE VARCHAR(10)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza CLAVE ENTIDAD ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna CLAVE ENTIDAD ya exite..");
        }
        try
        {
            sql = "ALTER TABLE CNET_DOMICILIOS ALTER COLUMN CALLE TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se amplia CALLE a 200 en articulos");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya modificados (Se amplia CALLE a 200 en articulos)");
        }
        try
        {
            sql = "ALTER TABLE CNET_DOMICILIOS ALTER COLUMN N_EXTERIOR TYPE VARCHAR(100)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se amplia EXTERIOR a 100 en articulos");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya modificados (Se amplia EXTERIOR a 100 en articulos)");
        }
        try
        {
            sql = "ALTER TABLE CNET_DOMICILIOS ALTER COLUMN N_INTERIOR TYPE VARCHAR(100)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se amplia INTERIOR a 100 en articulos");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya modificados (Se amplia INTERIOR a 100 en articulos)");
        }
        try
        {
            sql = "ALTER TABLE CNET_COLONIAS ALTER DESCRIPCION TYPE VARCHAR(200)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se amplia DESCRIPCION a 200 en articulos");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("Valores ya modificados (Se amplia DESCRIPCION a 200 en articulos)");
        }
    }

    public void Activa_Nemonico()
    {
        try
        {
            sql = "UPDATE CNET_MODULOS SET NEMONICO = 'PRISE' WHERE IDMODULO = 1";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("NEMONICO YA ACTIVO...");
        }
    }

    public void AddendaICON()
    {
        try
        {
            sql = "INSERT INTO CNET_CAT_MOVTOS (IDCATMOVTO, DESCRIPCION, NIVEL, SUBNIVEL, X, Y, Z, VENTANA, SMALL_PICTURE, LARGE_PICTURE) VALUES (35, 'ADDENDAS', 1, 9, 0, 0, 1, 'w_cat_camade', '.\\resources\\icons\\addenda.bmp', '.\\resources\\icons\\addenda.bmp')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("AddendaICON YA ACTIVO...");
        }
        try
        {
            sql = "INSERT INTO CNET_MODTOS (IDMODTO, IDMODULO, IDCATMOVTO, UP) VALUES (17, 1, 35, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("AddendaICON YA ACTIVO...");
        }
        try
        {
            sql = "SELECT * FROM CNET_PERMISOS WHERE IDUSUARIO = 1 AND IDMODTO = 17";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = "INSERT INTO CNET_PERMISOS (IDUSUARIO, IDMODTO, ESTADO) VALUES (1, 17, 'A')";
                rs = cnx.consulta(sql, false);
                cnx.commit();
            } else
            {
                System.out.println("AddendaICON YA ACTIVO...");
            }
        }
        catch(SQLException e)
        {
            System.out.println("AddendaICON YA ACTIVO...");
        }
    }

    public void LiverpoolICON()
    {
        try
        {
            sql = "INSERT INTO CNET_CAT_MOVTOS (IDCATMOVTO, DESCRIPCION, NIVEL, SUBNIVEL, X, Y, Z, VENTANA, SMALL_PICTURE, LARGE_PICTURE) VALUES (36, 'REMISIONES LIVERPOOL', 3, 1, 0, 0, 1, 'w_imp_fact_v1', '.\\resources\\icons\\liverpool.bmp', '.\\resources\\icons\\liverpool.bmp')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando LiverpoolICON ...");
        }
        catch(SQLException e)
        {
            System.out.println("LiverpoolICON YA ACTIVO...");
        }
        try
        {
            sql = "INSERT INTO CNET_MODTOS (IDMODTO, IDMODULO, IDCATMOVTO, UP) VALUES (18, 1, 36, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando LiverpoolICON ...");
        }
        catch(SQLException e)
        {
            System.out.println("LiverpoolICON YA ACTIVO...");
        }
        try
        {
            sql = "SELECT * FROM CNET_PERMISOS WHERE IDUSUARIO = 1 AND IDMODTO = 18";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = "INSERT INTO CNET_PERMISOS (IDUSUARIO, IDMODTO, ESTADO) VALUES (1, 18, 'A')";
                rs = cnx.consulta(sql, false);
                cnx.commit();
                System.out.println("Agregando LiverpoolICON ...");
            } else
            {
                System.out.println("LiverpoolICON YA ACTIVO...");
            }
        }
        catch(SQLException e)
        {
            System.out.println("LiverpoolICON YA ACTIVO...");
        }
    }

    public void HFolios()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_HFOLIOS ( IDHF            INTEGER NOT NULL, IDFOLIOS        INTEGER NOT NULL, FINI            INTEGER NOT NULL, FFIN            INTEGER NOT NULL, NOAPROBACION    VARCHAR(17) NOT NULL, ANIOAPROBACION  CHAR(4) NOT NULL, CONSTRAINT PK_CFD_HFOLIOS PRIMARY KEY (IDHF)\t)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Tabla HFolios ya existe...");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CFD_HFOLIOS_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador HFolios ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CFD_HFOLIOS_BI FOR CFD_HFOLIOS BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDHF is null) or (new.IDHF = 0) ) THEN NEW.IDHF = GEN_ID(GEN_CFD_HFOLIOS_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("Trigger HFolios ya existe...")).append(e.getMessage()).toString());
        }
    }

    public void CFDLiverpool()
        throws SQLException
    {
        try
        {
            sql = "CREATE TABLE CFD_LIVERPOOL ( IDLIVERPOOL   INTEGER NOT NULL, CONFIRMACION  INTEGER, NPEDIDO       INTEGER, DEPARTAMENTO  INTEGER, FEMBARQUE     DATE, FCANCELACION  DATE, TDA           VARCHAR(100), MODELO        VARCHAR(100), MARCA         VARCHAR(100), UPCEAN        INTEGER, REN\t         INTEGER, TALLA         CHAR(20) DEFAULT 'V', COLOR         VARCHAR(20) DEFAULT 'V', CANTIDAD      DECIMAL(10,2), COSTO         DECIMAL(10,2), PREGULAR      DECIMAL(10,2), PPUBLICO      DECIMAL(10,2), CTOTAL        DECIMAL(10,2), STATUS \t\t INTEGER DEFAULT 0,  UNIQUE (FEMBARQUE),  PRIMARY KEY (IDLIVERPOOL) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Tabla CFD_LIVERPOOL ya existe...");
        }
        try
        {
            sql = "ALTER TABLE CFD_LIVERPOOL ADD REN INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Campo REN...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (REN)");
        }
        try
        {
            sql = "ALTER TABLE CFD_LIVERPOOL ADD STATUS INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Campo STATUS...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (STATUS)");
        }
        try
        {
            sql = "ALTER TABLE CFD_LIVERPOOL ADD MARCA VARCHAR(100) ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Campo MARCA...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (MARCA)");
        }
        try
        {
            sql = "CREATE SEQUENCE GEN_CFD_LIVERPOOL_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador GEN_CFD_LIVERPOOL_ID ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CFD_LIVERPOOL_BI  FOR CFD_LIVERPOOL BEFORE INSERT POSITION 0 AS BEGIN if ((new.IDLIVERPOOL  is null) or (new.IDLIVERPOOL  = 0) ) THEN NEW.IDLIVERPOOL = GEN_ID(GEN_CFD_LIVERPOOL_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("Trigger CFD_LIVERPOOL_BI ya existe...")).append(e.getMessage()).toString());
        }
    }

    public void RestableceFolio()
    {
        try
        {
            System.out.println("RESTABLECER FOLIO...");
            sql = "UPDATE CFD_FOLIOS SET FOLIOACTUAL = 1 WHERE FOLIOACTUAL = 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("FOLIOS YA ACTIVOS...")).append(e.getMessage()).toString());
        }
    }

    public void BackupFields()
    {
        try
        {
            sql = "ALTER TABLE CFD_CONFIG ADD BPERIODO INTEGER DEFAULT 8 NOT NULL";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza BPERIODO...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna BPERIODO ya exite..");
        }
        try
        {
            sql = "ALTER TABLE CFD_CONFIG ADD BPROGRAMA TIMESTAMP";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza BPROGRAMA...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna BPROGRAMA ya exite..");
        }
        try
        {
            sql = "ALTER TABLE CFD_CONFIG ADD BFLG INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza BFLG...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna BFLG ya exite..");
        }
    }

    public void Version(String name)
    {
        try
        {
            sql = (new StringBuilder("UPDATE CNET_MODULOS SET NEMONICO = '")).append(name).append("'").toString();
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println((new StringBuilder("Cambio de version a: ")).append(name).toString());
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("Cambio de version a: ")).append(name).append(" ya existente...").toString());
        }
    }

    public void Folmula_Column()
        throws SQLException
    {
        try
        {
            sql = "ALTER TABLE CFD_DET_IMPUESTOS ADD FORMULA CHAR(3) DEFAULT 'NCA'";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Se actualiza columna Formula Impuesto ...");
        }
        catch(SQLException e)
        {
            System.out.println("Columna Formula Impuesto ya existe ...");
        }
    }

    public void Modulo_Cobranza()
    {
        try
        {
            sql = "INSERT INTO CNET_CAT_MOVTOS (IDCATMOVTO, DESCRIPCION, NIVEL, SUBNIVEL, X, Y, Z, VENTANA, SMALL_PICTURE, LARGE_PICTURE) VALUES (38, 'COBRANZA', 3, 1, 0, 0, 1, 'w_cobranza', '.\\resources\\icons\\cobranza02.bmp', '.\\resources\\icons\\cobranza02.bmp')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Cobranza ...");
        }
        catch(SQLException e)
        {
            System.out.println("Cobranza YA ACTIVO...");
        }
        try
        {
            sql = "INSERT INTO CNET_MODTOS (IDMODTO, IDMODULO, IDCATMOVTO, UP) VALUES (20, 1, 38, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Cobranza ...");
        }
        catch(SQLException e)
        {
            System.out.println("Cobranza YA ACTIVO...");
        }
        try
        {
            sql = "SELECT * FROM CNET_PERMISOS WHERE IDUSUARIO = 1 AND IDMODTO = 20";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = "INSERT INTO CNET_PERMISOS (IDUSUARIO, IDMODTO, ESTADO) VALUES (1, 20, 'A')";
                rs = cnx.consulta(sql, false);
                cnx.commit();
                System.out.println("Agregando Cobranza ...");
            } else
            {
                System.out.println("Cobranza YA ACTIVO...");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Cobranza YA ACTIVO...");
        }
        try
        {
            sql = "INSERT INTO CNET_CAT_MOVTOS (IDCATMOVTO, DESCRIPCION, NIVEL, SUBNIVEL, X, Y, Z, VENTANA, SMALL_PICTURE, LARGE_PICTURE) VALUES (39, 'REPORTE COBRANZA', 2, 5, 0, 0, 1, 'w_cobranza_rep', '.\\resources\\icons\\reporte_cobranza.bmp', '.\\resources\\icons\\reporte_cobranza.bmp')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Reporte Cobranza ...");
        }
        catch(SQLException e)
        {
            System.out.println("Reporte Cobranza  YA ACTIVO...");
        }
        try
        {
            sql = "INSERT INTO CNET_MODTOS (IDMODTO, IDMODULO, IDCATMOVTO, UP) VALUES (21, 1, 39, 0)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Reporte Cobranza  ...");
        }
        catch(SQLException e)
        {
            System.out.println("Reporte Cobranza  YA ACTIVO...");
        }
        try
        {
            sql = "SELECT * FROM CNET_PERMISOS WHERE IDUSUARIO = 1 AND IDMODTO = 21";
            rs = cnx.consulta(sql, true);
            if(!rs.next())
            {
                sql = "INSERT INTO CNET_PERMISOS (IDUSUARIO, IDMODTO, ESTADO) VALUES (1, 21, 'A')";
                rs = cnx.consulta(sql, false);
                cnx.commit();
                System.out.println("Agregando Reporte Cobranza  ...");
            } else
            {
                System.out.println("Reporte Cobranza  YA ACTIVO...");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Reporte Cobranza  YA ACTIVO...");
        }
        try
        {
            sql = "ALTER TABLE CFD_COMPROBANTE ADD IDCBRZ INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Campo IDCBRZ...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (IDCBRZ)");
        }
        try
        {
            sql = " CREATE TABLE CFD_COBRANZA (  IDCBRZ      INTEGER NOT NULL, IDENTIDAD   BIGINT, FOLIO       INTEGER, SERIE       VARCHAR(5), STATUS      CHAR(3) DEFAULT 'PDT', IMPORTE     DECIMAL(10,2), SALDO       DECIMAL(10,2), PRIMARY KEY (IDCBRZ) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (Cobranza)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CFD_COBRANZA_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CFD_COBRANZA ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CFD_COBRANZA_BI FOR CFD_COBRANZA BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDCBRZ is null) or (new.IDCBRZ = 0) ) THEN NEW.IDCBRZ = GEN_ID(GEN_CFD_COBRANZA_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_COBRANZA ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_CTABANCO (  IDCTA   INTEGER NOT NULL, NOMBRE  VARCHAR(100), NCTA    VARCHAR(20), CLABE   VARCHAR(20), SALDO   DECIMAL(10,2), PRIMARY KEY (IDCTA) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CTABANCO)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_CTABANCO_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CTABANCO ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_CTABANCO_BI FOR CBRZ_CTABANCO BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDCTA is null) or (new.IDCTA = 0) ) THEN NEW.IDCTA = GEN_ID(GEN_CBRZ_CTABANCO_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CTABANCO ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_HCTABANCO ( IDHCTA       INTEGER NOT NULL, IDCTA       INTEGER, IDCHEQUES   INTEGER, IDDEPOSITO  INTEGER, IDCREDITO   INTEGER, IDEFECTIVO  INTEGER, FECHA       TIMESTAMP, MONTO       DECIMAL(10,2), PRIMARY KEY (IDHCTA) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_HCTABANCO)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_HCTABANCO_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_HCTABANCO ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_HCTABANCO_BI FOR CBRZ_HCTABANCO BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDHCTA is null) or (new.IDHCTA = 0) ) THEN new.IDHCTA = GEN_ID(GEN_CBRZ_HCTABANCO_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_HCTABANCO ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_EFECTIVO ( IDEFECT  INTEGER NOT NULL, IDCBRZ   INTEGER, FECHA    TIMESTAMP, MONTO    DECIMAL(10,2), IDCTA    INTEGER, PRIMARY KEY (IDEFECT) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_EFECTIVO)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_EFECTIVO_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_EFECTIVO ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_EFECTIVO_BI FOR CBRZ_EFECTIVO BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDEFECT is null) or (new.IDEFECT = 0) ) THEN new.IDEFECT = GEN_ID(GEN_CBRZ_EFECTIVO_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_EFECTIVO ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_DEPOSITO (  IDDEPOSITO    INTEGER NOT NULL, IDCBRZ        INTEGER, NREF          INTEGER, FECHA         TIMESTAMP, MONTO         DECIMAL(10,2), IDDELEGACION  INTEGER, IDCTA         INTEGER, PRIMARY KEY (IDDEPOSITO) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_DEPOSITO)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_DEPOSITO_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_DEPOSITO ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_DEPOSITO_BI FOR CBRZ_DEPOSITO BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDDEPOSITO is null) or (new.IDDEPOSITO = 0) ) THEN new.IDDEPOSITO = GEN_ID(GEN_CBRZ_DEPOSITO_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_DEPOSITO ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_CHEQUES ( IDCHEQUES  INTEGER NOT NULL, IDCBRZ     INTEGER, NCHEQUE    INTEGER, SCHEQUE    CHAR(3) DEFAULT 'PGD', FCHQUE     TIMESTAMP, BANCO      VARCHAR(100), MONTO      DECIMAL(10,2), IDCTA      INTEGER, PRIMARY KEY (IDCHEQUES) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_CHEQUES)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_CHEQUES_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_CHEQUES ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_CHEQUES_BI FOR CBRZ_CHEQUES BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDCHEQUES is null) or (new.IDCHEQUES = 0) ) THEN new.IDCHEQUES = GEN_ID(GEN_CBRZ_CHEQUES_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_CHEQUES ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_CREDITO ( IDCREDITO  INTEGER NOT NULL, IDCBRZ     INTEGER, FECHA      TIMESTAMP, IMPORTE    DECIMAL(10,2), ABONO      DECIMAL(10,2), SALDO      DECIMAL(10,2), IDCTA      INTEGER, PRIMARY KEY (IDCREDITO) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_CREDITO)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_CREDITO_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_CREDITO ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_CREDITO_BI FOR CBRZ_CREDITO BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDCREDITO is null) or (new.IDCREDITO = 0) ) THEN new.IDCREDITO = GEN_ID(GEN_CBRZ_CREDITO_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_CREDITO ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_PTOVTA ( IDPTAVTA  INTEGER NOT NULL, IDCBRZ    INTEGER, MONTO     DECIMAL(10,2), FECHA     TIMESTAMP, BANCO     VARCHAR(100), PRIMARY KEY (IDPTAVTA) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_CREDITO)");
        }
        try
        {
            sql = "ALTER TABLE CBRZ_PTOVTA ADD TIPO VARCHAR(20)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Campo TIPO...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (TIPO)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_PTOVTA_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_PTOVTA ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_PTOVTA_BI FOR CBRZ_PTOVTA BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDPTAVTA is null) or (new.IDPTAVTA = 0) ) THEN new.IDPTAVTA = GEN_ID(GEN_CBRZ_PTOVTA_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_PTOVTA ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CBRZ_TRANS ( IDTRANS  INTEGER NOT NULL, IDCBRZ   INTEGER, TIPO     VARCHAR(20), BANCO    VARCHAR(100), MONTO     DECIMAL(10,2), FECHA    TIMESTAMP, NOAPROB  INTEGER, PRIMARY KEY (IDTRANS) )";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CBRZ_TRANS)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CBRZ_TRANS_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CBRZ_TRANS ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CBRZ_TRANS_BI FOR CBRZ_TRANS BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDTRANS is null) or (new.IDTRANS = 0) ) THEN new.IDTRANS = GEN_ID(GEN_CBRZ_TRANS_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CBRZ_TRANS ya existe...");
        }
        try
        {
            sql = " CREATE TABLE CFD_HCBRZ (  IDHCBRZ   INTEGER NOT NULL, IDCBRZ    INTEGER, FECHA     TIMESTAMP, CHEQUE    INTEGER, CREDITO   INTEGER, DEPOSITO  INTEGER, EFECTIVO  INTEGER, PTOVTA    INTEGER, TRANS     INTEGER, PRIMARY KEY (IDHCBRZ))";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (CFD_HCBRZ)");
        }
        try
        {
            sql = "CREATE GENERATOR GEN_CFD_HCBRZ_ID";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Generador CFD_HCBRZ ya existe...");
        }
        try
        {
            sql = "CREATE TRIGGER CFD_HCBRZ_BI FOR CFD_HCBRZ BEFORE INSERT POSITION 0 AS BEGIN if ( (new.IDHCBRZ is null) or (new.IDHCBRZ = 0) ) THEN new.IDHCBRZ = GEN_ID(GEN_CFD_HCBRZ_ID,1); END;";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
    }

    public void Modulo_HCobranza()
    {
        try
        {
            sql = "INSERT INTO CFD_HCBRZ SELECT NEXT VALUE FOR GEN_CFD_HCBRZ_ID, CBRZ_EFECTIVO.IDCBRZ, CBRZ_EFECTIVO.FECHA, 0, 0, 0, CBRZ_EFECTIVO.IDEFECT, 0, 0 FROM CBRZ_EFECTIVO LEFT OUTER JOIN  CFD_HCBRZ ON CFD_HCBRZ.EFECTIVO = CBRZ_EFECTIVO.IDEFECT WHERE CBRZ_EFECTIVO.IDEFECT NOT IN (SELECT CFD_HCBRZ.EFECTIVO FROM CFD_HCBRZ)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_HCBRZ SELECT NEXT VALUE FOR GEN_CFD_HCBRZ_ID, CBRZ_CHEQUES.IDCBRZ, CBRZ_CHEQUES.FCHQUE, CBRZ_CHEQUES.IDCHEQUES, 0, 0, 0, 0, 0 FROM CBRZ_CHEQUES LEFT OUTER JOIN  CFD_HCBRZ ON CFD_HCBRZ.CHEQUE = CBRZ_CHEQUES.IDCHEQUES WHERE CBRZ_CHEQUES.IDCHEQUES NOT IN (SELECT CFD_HCBRZ.CHEQUE FROM CFD_HCBRZ)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_HCBRZ SELECT NEXT VALUE FOR GEN_CFD_HCBRZ_ID, CBRZ_CREDITO.IDCBRZ, CBRZ_CREDITO.FECHA, 0, CBRZ_CREDITO.IDCREDITO, 0, 0, 0, 0  FROM CBRZ_CREDITO LEFT OUTER JOIN  CFD_HCBRZ ON CFD_HCBRZ.CREDITO = CBRZ_CREDITO.IDCREDITO WHERE CBRZ_CREDITO.IDCREDITO NOT IN (SELECT CFD_HCBRZ.CREDITO FROM CFD_HCBRZ)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_HCBRZ SELECT NEXT VALUE FOR GEN_CFD_HCBRZ_ID, CBRZ_DEPOSITO.IDCBRZ, CBRZ_DEPOSITO.FECHA, 0, 0, CBRZ_DEPOSITO.IDDEPOSITO, 0, 0, 0 FROM CBRZ_DEPOSITO LEFT OUTER JOIN  CFD_HCBRZ ON CFD_HCBRZ.DEPOSITO = CBRZ_DEPOSITO.IDDEPOSITO WHERE CBRZ_DEPOSITO.IDDEPOSITO NOT IN (SELECT CFD_HCBRZ.DEPOSITO FROM CFD_HCBRZ)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_HCBRZ SELECT NEXT VALUE FOR GEN_CFD_HCBRZ_ID, CBRZ_PTOVTA.IDCBRZ, CBRZ_PTOVTA.FECHA, 0, 0, 0, 0, CBRZ_PTOVTA.IDPTAVTA, 0 FROM CBRZ_PTOVTA LEFT OUTER JOIN  CFD_HCBRZ ON CFD_HCBRZ.PTOVTA = CBRZ_PTOVTA.IDPTAVTA WHERE CBRZ_PTOVTA.IDPTAVTA NOT IN (SELECT CFD_HCBRZ.PTOVTA FROM CFD_HCBRZ)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_HCBRZ SELECT NEXT VALUE FOR GEN_CFD_HCBRZ_ID, CBRZ_TRANSLEFT.IDCBRZ, CBRZ_TRANSLEFT.FECHA, 0, 0, 0, 0, 0, CBRZ_TRANSLEFT.IDTRANS FROM CBRZ_TRANSLEFT LEFT OUTER JOIN  CFD_HCBRZ ON CFD_HCBRZ.TRANS = CBRZ_TRANSLEFT.IDTRANS WHERE CBRZ_TRANSLEFT.IDTRANS NOT IN (SELECT CFD_HCBRZ.TRANS FROM CFD_HCBRZ)";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Trigger CFD_HCBRZ ya existe...");
        }
    }

    void EImpuestos()
    {
        try
        {
            sql = " delete  from cfd_impuestos where IDARTICULO  IS NULL ";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Eliminando Impuestos Duplicados...");
        }
        catch(SQLException e)
        {
            System.out.println("No se eliminando Impuestos Duplicados...");
        }
    }

    public void AddValCoppel()
        throws SQLException
    {
        try
        {
            sql = "ALTER TABLE CFD_VALADE ADD IDVALPADRE INTEGER DEFAULT 0";
            rs = cnx.consulta(sql, false);
            cnx.commit();
            System.out.println("Agregando Campo IDVALPADRE...");
        }
        catch(SQLException e)
        {
            System.out.println("Valores ya existentes (IDVALPADRE)");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (22, 'LISTADO COPPEL', 'LIST_ACOPPEL')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (23, 'CADENA ORIGINAL', 'CO')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (24, 'PEDIDO COPPEL', 'PCOPPEL')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (25, 'BODEGA RES', 'BRES')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (26, 'BODEGA DES', 'BDES')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (27, 'FECHAENTC', 'FEC')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (28, 'ARTICULO COPPEL', 'ARTC')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (29, 'MODELO COPPEL', 'MOC')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (30, 'PRECIO COPPEL', 'PRECIO')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (31, 'SECUENCIA_LISTA', 'SLIST')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (32, 'IDSERIECOPPEL', 'ICOPPEL')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (33, 'CANTIDADART', 'CANART')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (34, 'PRECIOBRUTO', 'PB')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (35, 'PRECIONETO', 'PN')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (36, 'IMPORTEBRUTO', 'IB')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
        try
        {
            sql = "INSERT INTO CFD_ASC_ADD (IDASC, DESCRIPCION, REFERENCIA) VALUES (37, 'IMPORTENETO', 'IN')";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println("Valorcoppel ya existe...");
        }
    }

    public void RestableceEgreso()
    {
        try
        {
            System.out.println("RESTABLECER EGRESO...");
            sql = "UPDATE CFD_COMPROBANTE SET TIPO = 'E' WHERE IDTDOCTO = 4";
            rs = cnx.consulta(sql, false);
            cnx.commit();
        }
        catch(SQLException e)
        {
            System.out.println((new StringBuilder("EGRESO YA ACTIVO...")).append(e.getMessage()).toString());
        }
    }

    public static void main(String args[])
        throws FileNotFoundException, SQLException, IOException
    {
        Tarticulos ta = new Tarticulos();
        ta.ControlV("3", "5", "1");
        ta.BackUpMode();
        ta.Cam_Imp();
        ta.Precision();
        ta.ActulalizaTDocto();
        ta.Vacios_Addenda();
        ta.Cfg_Rango_Fechas();
        ta.FOLIOS_APROB();
        ta.Desc_Entidad();
        ta.DetallistaCom();
        ta.CLAVE_ENTIDAD();
        ta.CatEdos();
        ta.CatAddenda();
        ta.ComprobanteAnio();
        ta.HFolios();
        ta.Formatos();
        ta.AddendaICON();
        ta.RestableceFolio();
        ta.BackupFields();
        ta.CampoISH();
        ta.CFDLiverpool();
        ta.Folmula_Column();
        ta.LiverpoolICON();
        ta.Modulo_Cobranza();
        ta.Modulo_HCobranza();
        ta.EImpuestos();
        ta.AddValCoppel();
        ta.RestableceEgreso();
    }

    String sql;
    ResultSet rs;
    ConexionFirebird cnx;
}
