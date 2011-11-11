// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:10:19 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   ExcelTableExporter.java

package excelexporter;

import java.io.*;
import java.util.List;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.*;
import jxl.write.Number;

public class ExcelTableExporter
{

    public ExcelTableExporter(List tables, File file, List nombreTabs)
        throws Exception
    {
        this.file = file;
        this.tables = tables;
        this.nombreTabs = nombreTabs;
        if(nombreTabs.size() != tables.size())
            throw new Exception("Cantidad de tablas debe coincidir con el nombre de tabs");
        else
            return;
    }

    public boolean export()
    {
        try
        {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            for(int index = 0; index < tables.size(); index++)
            {
                JTable table = (JTable)tables.get(index);
                WritableSheet s = w.createSheet((String)nombreTabs.get(index), 0);
                for(int i = 0; i < table.getColumnCount(); i++)
                {
                    s.addCell(new Label(i, 0, table.getColumnName(i)));
                    for(int j = 0; j < table.getRowCount(); j++)
                    {
                        Object objeto = table.getValueAt(j, i);
                        if(i < 6)
                        {
                            s.addCell(new Label(i, j + 1, String.valueOf(objeto)));
                        } else
                        {
                            Number numero = new Number(i, j + 1, Double.parseDouble(objeto.toString()));
                            s.addCell(numero);
                        }
                    }

                }

            }

            w.write();
            w.close();
            out.close();
            return true;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        catch(WriteException ex)
        {
            ex.printStackTrace();
        }
        return false;
    }

    private File file;
    private List tables;
    private List nombreTabs;
}
