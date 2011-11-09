// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 05:16:26 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ImportXMLExample.java

package create;

import java.io.IOException;
import java.sql.SQLException;
import org.jdom.JDOMException;

// Referenced classes of package create:
//            ImportXML

public class ImportXMLExample
{

    public ImportXMLExample()
    {
    }

    public static void main(String arg[])
        throws SQLException, NumberFormatException, JDOMException, IOException
    {
        String args[] = new String[4];
        args[0] = "C:\\Documents and Settings\\J.Octavio\\Escritorio\\FACT_5___20070921.xml";
        args[1] = "2";
        args[2] = "RACV731017AI4";
        args[3] = "2";
        ImportXML.main(args);
    }
}
