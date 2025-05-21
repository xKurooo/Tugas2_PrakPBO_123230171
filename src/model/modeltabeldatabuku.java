/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class modeltabeldatabuku extends AbstractTableModel{

    List<databuku> dp;
    public modeltabeldatabuku(List<databuku>dp){
        this.dp = dp;
    }
    
    @Override
    public int getRowCount() {
        return dp.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }
    
    @Override
    public String getColumnName(int column){
        switch (column){
            case 0:
                return "ID";
            case 1:
                return "Judul";
            case 2:
                return "Genre";
            case 3:
                return "Penulis";
            case 4:
                return "Penerbit";
            case 5:
                return "Lokasi";
            case 6:
                return "Stock";
                
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch (column){
            case 0:
                return dp.get(row).getId_buku();
            case 1:
                return dp.get(row).getJudul();
            case 2:
                return dp.get(row).getGenre();
            case 3:
                return dp.get(row).getPenulis();
            case 4:
                return dp.get(row).getPenerbit();
            case 5:
                return dp.get(row).getLokasi();
            case 6:
                return dp.get(row).getStock();
                
            default:
                return null;
        }
    }
    
}
