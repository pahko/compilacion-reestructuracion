// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 07/11/2011 06:10:59 p.m.
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   TableFrame.java

package excelexporter;

import javax.swing.table.DefaultTableModel;

// Referenced classes of package excelexporter:
//            TableFrame

class TableFrame$1 extends DefaultTableModel
{

    public Class getColumnClass(int columnIndex)
    {
        return types[columnIndex];
    }

    Class types[] = {
        java/lang/String, java/lang/String
    };
    final TableFrame this$0;

    TableFrame$1(Object x0[][], Object x1[])
    {
        this$0 = TableFrame.this;
        super(x0, x1);
    }
}
