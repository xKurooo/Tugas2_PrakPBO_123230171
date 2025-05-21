/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOdatabuku;

import java.sql.*;
import java.util.*;
import koneksi.connector;
import model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import DAOImplement.databukuimplement;

public class databukuDAO implements databukuimplement {

    Connection connection;

    final String select = "SELECT * FROM db_perpustakaan";
    final String insert = "INSERT INTO db_perpustakaan (judul, genre, penulis, penerbit, lokasi, stock) VALUES (?, ?, ?, ?, ?, ?);";
    final String update = "UPDATE db_perpustakaan set judul=?, genre=?, penulis=?, penerbit=?, lokasi=?, stock=? where id_buku=?";
    final String delete = "DELETE FROM db_perpustakaan where id_buku=?";

    public databukuDAO() {
        connection = connector.connection();
    }

    @Override
    public void insert(databuku p) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, p.getJudul());
            statement.setString(2, p.getGenre());
            statement.setString(3, p.getPenulis());
            statement.setString(4, p.getPenerbit());
            statement.setString(5, p.getLokasi());
            statement.setInt(6, p.getStock());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()) {
                p.setId_buku(rs.getInt(1));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(databuku p) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(update);
            statement.setString(1, p.getJudul());
            statement.setString(2, p.getGenre());
            statement.setString(3, p.getPenulis());
            statement.setString(4, p.getPenerbit());
            statement.setString(5, p.getLokasi());
            statement.setInt(6, p.getStock());
            statement.setInt(7, p.getId_buku());
            statement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public List<databuku> getAll() {
        List<databuku> dp = null;
        try {
            dp = new ArrayList<databuku>();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(select);
            while (rs.next()) {
                databuku buku = new databuku();
                buku.setId_buku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setGenre(rs.getString("genre"));
                buku.setPenulis(rs.getString("penulis"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setLokasi(rs.getString("lokasi"));
                buku.setStock(rs.getInt("stock"));
                dp.add(buku);

            }
        } catch (SQLException ex) {
            Logger.getLogger(databukuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dp;
    }
}
