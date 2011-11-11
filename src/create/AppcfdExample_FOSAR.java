// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 04:52:16 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AppcfdExample_FOSAR.java

package create;

import com.codinet.facture.cfdv2.exceptions.CFDException;
import gob.sat.sgi.SgiLibException;
import java.awt.HeadlessException;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.com.codinet.err.ComprobanteArchRefException;
import org.jaxen.JaxenException;
import org.jdom.JDOMException;

// Referenced classes of package create:
//            AppcfdExample

public class AppcfdExample_FOSAR
{

    public AppcfdExample_FOSAR()
    {
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

    public static void main(String arg[])
    {
        String args[] = new String[4];
        args[0] = "1";
        args[1] = "1";
        args[2] = "VF";
        args[3] = "1";
        AppcfdExample app = new AppcfdExample();
        try {
			app.AppcfdExample_main(args);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComprobanteArchRefException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaxenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CFDException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SgiLibException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     }

    public static String args[] = new String[4];

}
