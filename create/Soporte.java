// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:27:26 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Soporte.java

package create;

import java.sql.*;

public class Soporte
{

    public Soporte()
    {
    }

    public void getLastID(String as_schema, String as_table, String as_column, ResultSet rs[])
        throws SQLException
    {
        Connection conn = DriverManager.getConnection("jdbc:default:connection");
        PreparedStatement ps = conn.prepareStatement("select sys.syscolumns.autoincrementvalue  from sys.syscolumns, sys.systables, sys.sysschemas  where (sys.systables.tableid = sys.syscolumns.referenceid) and sys.sysschemas.schemaid = sys.systables.schemaid  and sys.sysschemas.schemaname = upper(?)  and sys.systables.tablename= upper(?)  and sys.syscolumns.columnname = upper(?) ");
        ps.setString(1, as_schema.toString());
        ps.setString(2, as_table.toString());
        ps.setString(3, as_column.toString());
        rs[0] = ps.executeQuery();
        conn.close();
    }
}
