// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 03:56:55 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AppcfdComprobanteExecute.java

package comprobante;

import com.codinet.facture.cfdv2.exceptions.CFDException;
import java.io.IOException;
import java.sql.SQLException;
import mx.com.codinet.err.ComprobanteArchRefException;
import org.jdom.JDOMException;

// Referenced classes of package comprobante:
//            AppcfdComprobante

public class AppcfdComprobanteExecute {

    public static void main(String arg[]) throws Exception {
        apc.conected();
        try
        {
            apc.setParams(1L, "39", "_", 1L);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            apc.creaComprobante();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    static AppcfdComprobante apc = new AppcfdComprobante();

}
