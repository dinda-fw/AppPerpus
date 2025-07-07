package appperpus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class koneksi {

    // Hapus: private static Connection koneksi; // <--- Hapus ini

    public static Connection getKoneksi() {
        Connection conn = null; // Deklarasikan di sini
        try {
            String url = "jdbc:mysql://localhost:3306/db_perpustakaan_fixx";
            String user = "root";
            String pass = "";

            conn = DriverManager.getConnection(url, user, pass); // Buat koneksi baru
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + ex.getMessage(), "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
        return conn; // Kembalikan koneksi baru
    }
}