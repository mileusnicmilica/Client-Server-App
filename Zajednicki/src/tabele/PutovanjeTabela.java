/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Putovanje;

/**
 *
 * @author Milica
 */
public class PutovanjeTabela extends AbstractTableModel{
    List<Putovanje> p = new ArrayList<>();
    String[] columns = {"Zemlja", "Datum_Ulaska", "Datum_Izlaska", "Status"};

    public PutovanjeTabela(List<Putovanje> p) {
        this.p=p;
    }

    public List<Putovanje> getP() {
        return p;
    }

    public void setP(List<Putovanje> p) {
        this.p = p;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }
    
    
    
    @Override
    public int getRowCount() {
        if(p==null){
            return 0;
        }
        return p.size();
        
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Putovanje putovanje = p.get(rowIndex);
        switch(columnIndex){
            case 0:
                return putovanje.getZemlje();
            case 1:
                return putovanje.getDatumUlaska();
            case 2:
                return putovanje.getDatumIzlaska();
            case 3:
                return putovanje.getStatus();
            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }
    
    
    public Putovanje getPutovanje(int row){
        return p.get(row);
    }
    
}
