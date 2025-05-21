/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.List;
import koneksi.connector;
import DAOdatabuku.databukuDAO;
import DAOImplement.databukuimplement;
import model.*;
import view.MainView;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class databukucontroller {
    MainView frame;
    databukuimplement impldatabuku;
    List<databuku> dp;
    
    public databukucontroller(MainView frame){
        this.frame = frame;
        impldatabuku = new databukuDAO();
        dp = impldatabuku.getAll();
    }
    
    public void isitabel(){
        dp = impldatabuku.getAll();
        modeltabeldatabuku mp = new modeltabeldatabuku(dp);
        frame.getTabelDatabuku().setModel(mp);
    }
    
    public void insert(){
        try {
        String judul = frame.getJTxtjudul().getText().trim();
        String genre = frame.getJTxtgenre().getText().trim();
        String penulis = frame.getJTxtpenulis().getText().trim();
        String penerbit = frame.getJTxtpenerbit().getText().trim();
        String lokasi = frame.getJTxtlokasi().getText().trim();
        String stockText = frame.getJTxtstock().getText().trim();

        if (judul.isEmpty() || genre.isEmpty() || penulis.isEmpty() ||  penerbit.isEmpty() || lokasi.isEmpty() || stockText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Stok harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        databuku db = new databuku();
        db.setJudul(judul);
        db.setGenre(genre);
        db.setPenulis(penulis);
        db.setPenerbit(penerbit);
        db.setLokasi(lokasi);
        db.setStock(stock);

        impldatabuku.insert(db);
        JOptionPane.showMessageDialog(null, "Data berhasil disimpan.");
        } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void update(){
        databuku dp = new databuku();
        dp.setJudul(frame.getJTxtjudul().getText());
        dp.setGenre(frame.getJTxtgenre().getText());
        dp.setPenulis(frame.getJTxtpenulis().getText());
        dp.setPenerbit(frame.getJTxtpenerbit().getText());
        dp.setLokasi(frame.getJTxtlokasi().getText());
        dp.setStock(Integer.parseInt(frame.getJTxtstock().getText()));
        dp.setId_buku(Integer.parseInt(frame.getJTxtid().getText()));
        impldatabuku.update(dp);
        
    }
    
    public void delete(){
        int id_buku = Integer.parseInt(frame.getJTxtid().getText());
        impldatabuku.delete(id_buku);
    }
    
    public void cari(String column, String keyword){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("id_buku");
        model.addColumn("judul");
        model.addColumn("genre");
        model.addColumn("penulis");
        model.addColumn("penerbit");
        model.addColumn("lokasi");
        model.addColumn("stock");
        try {
            Connection konek = connector.connection();
            String sql = "SELECT * FROM db_perpustakaan WHERE " + column + " LIKE ?";
            PreparedStatement pr = konek.prepareStatement(sql);
            pr.setString(1, "%" + keyword + "%");
            ResultSet satset = pr.executeQuery();
            while (satset.next()){
                model.addRow(new Object[]{
                    satset.getInt("id_buku"),
                    satset.getString("judul"),
                    satset.getString("genre"),
                    satset.getString("penulis"),    
                    satset.getString("penerbit"),
                    satset.getString("lokasi"),
                    satset.getInt("stock")
                });
            }
            
            frame.getTabelDatabuku().setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ALERTTTT" + e.getMessage());
        }
    }
        
}

