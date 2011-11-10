// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MyTableModel_FOSAR.java

package create;

import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class MyTableModel_FOSAR implements TableModel {

    MyTableModel_FOSAR() {
        data = new ArrayList();
        listener = new ArrayList();
    }

    public void Actualizar_Datos(ArrayList datos) {
        data = datos;
    }

    MyTableModel_FOSAR(ArrayList datos) {
        data = new ArrayList();
        listener = new ArrayList();
        data = datos;
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
        case 0:
            return java/lang/String;
        case 1:
            return java/lang/String;
        case 2:
            return java/lang/Integer;
        case 3:
            return java/lang/String;
        case 4:
            return java/lang/String;
        case 5:
            return java/lang/String;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
            return java/lang/Double;
        }
        return null;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((ArrayList)data.get(rowIndex)).get(columnIndex);
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addTableModelListener(TableModelListener l) {
        listener.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listener.remove(l);
    }

    private ArrayList data;
    private String columnNames[] = {
        "FECHA", "ESTADO", "FOLIO", "SERIE", "RFC", "RAZON SOCIAL", "SUBTOTAL", "DESCUENTOS", "SUBTOTAL", "IMPUESTOS", 
        "TOTAL"
    };
    private ArrayList listener;
}
