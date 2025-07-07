/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package appperpus;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class Panel_Pengembalian extends javax.swing.JPanel {
    
    private String idPengembalianSaatIni = null;
    /**
     * Creates new form Panel_dashboard
     */
    public Panel_Pengembalian() {
        initComponents();
        initPlaceholder(txt_Pencarian, "Cari");
        initPlaceholder(txt_idpeminjaman, "No.ID");
        initPlaceholder(txt_namapeminjam, "Nama Anggota");
        initPlaceholder(txt_idanggota, "ID Anggota");
        initPlaceholder(txt_telpon, "No. Telpon");
        initPlaceholder(txt_Tanggalpinjam, "YYYY-MM-DD"); // Contoh format tanggal
        initPlaceholder(txt_Tanggakkembali, "YYYY-MM-DD"); // Contoh format tanggal
        initPlaceholder(txt_denda, "Denda");
        initPlaceholder(txt_judul, "Judul Buku");
        initPlaceholder(txt_kategoribuku, "Kategori Buku"); // Perhatikan: Kolom ini tidak ada di tb_pengembalian
        initPlaceholder(txt_statuspeminjam, "Status Peminjaman");

        tampilkanDataPengembalian();
        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                int selectedRow = jTable1.getSelectedRow();
                String idPengembalian = jTable1.getValueAt(selectedRow, 0).toString();
                editDataPengembalian(idPengembalian);
            }
        });
    }
    
     private void initPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(new Color(204, 204, 204));
        textField.setText(placeholder);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(204, 204, 204));
                    textField.setText(placeholder);
                }
            }
        });
    }
     
     
     private void clearForm() {
        initPlaceholder(txt_idpeminjaman, "No.ID");
        initPlaceholder(txt_namapeminjam, "Nama Anggota");
        initPlaceholder(txt_idanggota, "ID Anggota");
        initPlaceholder(txt_telpon, "No. Telpon");
        initPlaceholder(txt_Tanggalpinjam, "YYYY-MM-DD");
        initPlaceholder(txt_Tanggakkembali, "YYYY-MM-DD");
        initPlaceholder(txt_denda, "Denda");
        initPlaceholder(txt_judul, "Judul Buku");
        initPlaceholder(txt_kategoribuku, "Kategori Buku");
        initPlaceholder(txt_statuspeminjam, "Status Peminjaman");
        idPengembalianSaatIni = null;
        Btn_simpan.setText("Simpan");
    }
     
     
     private void tampilkanDataPengembalian() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id_Pengembalian");
        model.addColumn("Nama");
        model.addColumn("No_Telepon");
        model.addColumn("Judul_Buku");
        model.addColumn("Tanggal_Pengembalian");
        model.addColumn("Denda");
        model.addColumn("Id_Peminjaman");
        model.addColumn("Id_Anggota");
        model.addColumn("Tanggal_Peminjaman");
        model.addColumn("Status_Peminjaman");

        try (Connection conn = koneksi.getKoneksi();
             Statement stat = conn.createStatement();
             ResultSet res = stat.executeQuery("SELECT * FROM tb_pengembalian")) {

            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("Id_Pengembalian"),
                    res.getString("Nama"),
                    res.getString("No_Telepon"),
                    res.getString("Judul_Buku"),
                    res.getString("Tanggal_Pengembalian"),
                    res.getDouble("Denda"),
                    res.getString("Id_Peminjaman"),
                    res.getString("Id_Anggota"),
                    res.getString("Tanggal_Peminjaman"),
                    res.getString("Status_Peminjaman")
                });
            }
            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
     
    private void cariDataPengembalian(String keyword) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id_Pengembalian");
        model.addColumn("Nama");
        model.addColumn("No_Telepon");
        model.addColumn("Judul_Buku");
        model.addColumn("Tanggal_Pengembalian");
        model.addColumn("Denda");
        model.addColumn("Id_Peminjaman");
        model.addColumn("Id_Anggota");
        model.addColumn("Tanggal_Peminjaman");
        model.addColumn("Status_Peminjaman");

        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement(
                     "SELECT * FROM tb_pengembalian WHERE " +
                     "Id_Pengembalian LIKE ? OR " +
                     "Nama LIKE ? OR " +
                     "No_Telepon LIKE ? OR " +
                     "Judul_Buku LIKE ? OR " +
                     "Tanggal_Pengembalian LIKE ? OR " +
                     "Denda LIKE ? OR " +
                     "Id_Peminjaman LIKE ? OR " +
                     "Id_Anggota LIKE ? OR " +
                     "Tanggal_Peminjaman LIKE ? OR " +
                     "Status_Peminjaman LIKE ?")) {

            for (int i = 1; i <= 10; i++) { // Ada 10 kolom yang dicari
                pst.setString(i, "%" + keyword + "%");
            }

            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    model.addRow(new Object[]{
                        res.getString("Id_Pengembalian"),
                        res.getString("Nama"),
                        res.getString("No_Telepon"),
                        res.getString("Judul_Buku"),
                        res.getString("Tanggal_Pengembalian"),
                        res.getDouble("Denda"),
                        res.getString("Id_Peminjaman"),
                        res.getString("Id_Anggota"),
                        res.getString("Tanggal_Peminjaman"),
                        res.getString("Status_Peminjaman")
                    });
                }
            }
            jTable1.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void simpanDataPengembalian() {
        String idPengembalian = txt_idpeminjaman.getText(); // Menggunakan Id_Peminjaman sebagai Id_Pengembalian untuk sementara, harusnya ada kolom Id_Pengembalian
        String nama = txt_namapeminjam.getText();
        String noTelpon = txt_telpon.getText();
        String judulBuku = txt_judul.getText();
        String tanggalPengembalian = txt_Tanggakkembali.getText();
        double denda = 0.0;
        try {
            denda = Double.parseDouble(txt_denda.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Denda harus berupa angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String idPeminjaman = txt_idpeminjaman.getText();
        String idAnggota = txt_idanggota.getText();
        String tanggalPeminjaman = txt_Tanggalpinjam.getText();
        String statusPeminjaman = txt_statuspeminjam.getText();

        // Validasi input
        if (idPengembalian.isEmpty() || idPengembalian.equals("No.ID") ||
            nama.isEmpty() || nama.equals("Nama Anggota") ||
            noTelpon.isEmpty() || noTelpon.equals("No. Telpon") ||
            judulBuku.isEmpty() || judulBuku.equals("Judul Buku") ||
            tanggalPengembalian.isEmpty() || tanggalPengembalian.equals("YYYY-MM-DD") ||
            idPeminjaman.isEmpty() || idPeminjaman.equals("No.ID") ||
            idAnggota.isEmpty() || idAnggota.equals("ID Anggota") ||
            tanggalPeminjaman.isEmpty() || tanggalPeminjaman.equals("YYYY-MM-DD") ||
            statusPeminjaman.isEmpty() || statusPeminjaman.equals("Status Peminjaman")) {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi semua data.", "Input Kosong", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validasi format tanggal
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date tglKembali = sdf.parse(tanggalPengembalian);
            Date tglPinjam = sdf.parse(tanggalPeminjaman);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD.", "Format Tanggal Salah", JOptionPane.ERROR_MESSAGE);
            return;
        }


        try (Connection conn = koneksi.getKoneksi()) {
            if (conn == null) {
                return; // Koneksi gagal
            }

            String sql;
            PreparedStatement pst;

            if (idPengembalianSaatIni == null) { // Mode Tambah (INSERT)
                sql = "INSERT INTO tb_pengembalian (Id_Pengembalian, Nama, No_Telepon, Judul_Buku, Tanggal_Pengembalian, Denda, Id_Peminjaman, Id_Anggota, Tanggal_Peminjaman, Status_Peminjaman) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, idPengembalian);
                pst.setString(2, nama);
                pst.setString(3, noTelpon);
                pst.setString(4, judulBuku);
                pst.setString(5, tanggalPengembalian);
                pst.setDouble(6, denda);
                pst.setString(7, idPeminjaman);
                pst.setString(8, idAnggota);
                pst.setString(9, tanggalPeminjaman);
                pst.setString(10, statusPeminjaman);
            } else { // Mode Edit (UPDATE)
                sql = "UPDATE tb_pengembalian SET Nama=?, No_Telepon=?, Judul_Buku=?, Tanggal_Pengembalian=?, Denda=?, Id_Peminjaman=?, Id_Anggota=?, Tanggal_Peminjaman=?, Status_Peminjaman=? WHERE Id_Pengembalian=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, nama);
                pst.setString(2, noTelpon);
                pst.setString(3, judulBuku);
                pst.setString(4, tanggalPengembalian);
                pst.setDouble(5, denda);
                pst.setString(6, idPeminjaman);
                pst.setString(7, idAnggota);
                pst.setString(8, tanggalPeminjaman);
                pst.setString(9, statusPeminjaman);
                pst.setString(10, idPengembalianSaatIni); // Menggunakan ID yang disimpan
            }

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                switchPanel(Panel_view);
                tampilkanDataPengembalian();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void editDataPengembalian(String id) {
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM tb_pengembalian WHERE Id_Pengembalian = ?")) {

            pst.setString(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if (res.next()) {
                    idPengembalianSaatIni = res.getString("Id_Pengembalian"); // Simpan ID untuk UPDATE
                    txt_idpeminjaman.setText(res.getString("Id_Peminjaman"));
                    txt_namapeminjam.setText(res.getString("Nama"));
                    txt_idanggota.setText(res.getString("Id_Anggota"));
                    txt_telpon.setText(res.getString("No_Telepon"));
                    txt_Tanggalpinjam.setText(res.getString("Tanggal_Peminjaman"));
                    txt_Tanggakkembali.setText(res.getString("Tanggal_Pengembalian"));
                    txt_denda.setText(String.valueOf(res.getDouble("Denda")));
                    txt_judul.setText(res.getString("Judul_Buku"));
                    // txt_kategoribuku.setText(res.getString("Kategori_Buku")); // Kolom ini tidak ada
                    txt_statuspeminjam.setText(res.getString("Status_Peminjaman"));

                    // Ubah warna teks menjadi hitam saat diedit
                    txt_idpeminjaman.setForeground(Color.BLACK);
                    txt_namapeminjam.setForeground(Color.BLACK);
                    txt_idanggota.setForeground(Color.BLACK);
                    txt_telpon.setForeground(Color.BLACK);
                    txt_Tanggalpinjam.setForeground(Color.BLACK);
                    txt_Tanggakkembali.setForeground(Color.BLACK);
                    txt_denda.setForeground(Color.BLACK);
                    txt_judul.setForeground(Color.BLACK);
                    txt_kategoribuku.setForeground(Color.BLACK); // Ini akan tetap hitam jika tidak ada placeholder
                    txt_statuspeminjam.setForeground(Color.BLACK);

                    Btn_simpan.setText("Update");
                    switchPanel(Panel_add);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void switchPanel(javax.swing.JPanel panel) {
        Panel_Main.removeAll();
        Panel_Main.add(panel);
        Panel_Main.repaint();
        Panel_Main.revalidate();
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel_Main = new javax.swing.JPanel();
        Panel_view = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Btn_tambah = new javax.swing.JButton();
        txt_Pencarian = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Panel_add = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        txt_idpeminjaman = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_namapeminjam = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_idanggota = new javax.swing.JTextField();
        txt_telpon = new javax.swing.JTextField();
        txt_Tanggakkembali = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_denda = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_judul = new javax.swing.JTextField();
        txt_statuspeminjam = new javax.swing.JTextField();
        txt_kategoribuku = new javax.swing.JTextField();
        txt_Tanggalpinjam = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        Panel_Main.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Main.setPreferredSize(new java.awt.Dimension(70, 50));
        Panel_Main.setLayout(new java.awt.CardLayout());

        Panel_view.setBackground(new java.awt.Color(255, 255, 255));
        Panel_view.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setText("Daftar Status pengembalian Buku Perpustakaan");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabel2.setText("Master Data > Pengembalian");

        Btn_tambah.setBackground(new java.awt.Color(153, 102, 0));
        Btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        Btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add_white.png"))); // NOI18N
        Btn_tambah.setText("Tambah");
        Btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambahActionPerformed(evt);
            }
        });

        txt_Pencarian.setForeground(new java.awt.Color(204, 204, 204));
        txt_Pencarian.setText("Cari");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout Panel_viewLayout = new javax.swing.GroupLayout(Panel_view);
        Panel_view.setLayout(Panel_viewLayout);
        Panel_viewLayout.setHorizontalGroup(
            Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addComponent(Btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_Pencarian, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        Panel_viewLayout.setVerticalGroup(
            Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_viewLayout.createSequentialGroup()
                .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)))
                .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Btn_tambah))
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(txt_Pencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Main.add(Panel_view, "card2");

        Panel_add.setBackground(new java.awt.Color(255, 255, 255));
        Panel_add.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Tambah Data Pengebalian Buku Perpustakaan");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        jLabel4.setText("Master Data > Pengembalian");

        Btn_simpan.setBackground(new java.awt.Color(153, 102, 0));
        Btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        Btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save_white.png"))); // NOI18N
        Btn_simpan.setText("Simpan");
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_simpanActionPerformed(evt);
            }
        });

        btn_batal.setBackground(new java.awt.Color(204, 204, 204));
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel_white.png"))); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        txt_idpeminjaman.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_idpeminjaman.setForeground(new java.awt.Color(204, 204, 204));
        txt_idpeminjaman.setText("No.ID");
        txt_idpeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idpeminjamanActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel7.setText("ID_Peminjaman");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel8.setText("Nama Peminjam");

        txt_namapeminjam.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_namapeminjam.setForeground(new java.awt.Color(204, 204, 204));
        txt_namapeminjam.setText("Nama Anggota");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel9.setText("ID_Anggota");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel10.setText("Telpon");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel11.setText("Tangal Pinjam");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel12.setText("Tanggal Kembali");

        txt_idanggota.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_idanggota.setForeground(new java.awt.Color(204, 204, 204));

        txt_telpon.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_telpon.setForeground(new java.awt.Color(204, 204, 204));
        txt_telpon.setText("No. Telpon");

        txt_Tanggakkembali.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_Tanggakkembali.setForeground(new java.awt.Color(204, 204, 204));
        txt_Tanggakkembali.setText("Tanggal Kembali");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel13.setText("Kategori Buku");

        txt_denda.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_denda.setForeground(new java.awt.Color(204, 204, 204));
        txt_denda.setText("Denda");

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel14.setText("Judul");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel15.setText("Status Peminjaman");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel16.setText("Denda");

        txt_judul.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_judul.setForeground(new java.awt.Color(204, 204, 204));
        txt_judul.setText("Judul");

        txt_statuspeminjam.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_statuspeminjam.setForeground(new java.awt.Color(204, 204, 204));
        txt_statuspeminjam.setText("Status");

        txt_kategoribuku.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_kategoribuku.setForeground(new java.awt.Color(204, 204, 204));
        txt_kategoribuku.setText("Kategori Buku");

        txt_Tanggalpinjam.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_Tanggalpinjam.setForeground(new java.awt.Color(204, 204, 204));
        txt_Tanggalpinjam.setText("Tanggal Pinjam");

        javax.swing.GroupLayout Panel_addLayout = new javax.swing.GroupLayout(Panel_add);
        Panel_add.setLayout(Panel_addLayout);
        Panel_addLayout.setHorizontalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_addLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addComponent(Btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_addLayout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7)
                                    .addComponent(txt_idpeminjaman)
                                    .addComponent(txt_namapeminjam)
                                    .addComponent(txt_idanggota)
                                    .addComponent(txt_telpon)
                                    .addComponent(txt_Tanggalpinjam, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Panel_addLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel12))
                                    .addComponent(jLabel14)
                                    .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_judul, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_denda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_Tanggakkembali, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel16)))
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addContainerGap(431, Short.MAX_VALUE)
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel4)
                                        .addComponent(txt_kategoribuku, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_statuspeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel15))))
                        .addGap(14, 14, 14)))
                .addContainerGap())
        );
        Panel_addLayout.setVerticalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_addLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_simpan)
                    .addComponent(btn_batal))
                .addGap(21, 21, 21)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idpeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_kategoribuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_namapeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_judul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idanggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_statuspeminjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_telpon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_denda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Tanggalpinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Tanggakkembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Main.add(Panel_add, "card2");

        add(Panel_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambahActionPerformed
        clearForm();
        switchPanel(Panel_add);
        
    }//GEN-LAST:event_Btn_tambahActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        switchPanel(Panel_view);
        tampilkanDataPengembalian(); // Muat ulang data saat kembali ke tampilan utama
    }//GEN-LAST:event_btn_batalActionPerformed

    private void txt_idpeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idpeminjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idpeminjamanActionPerformed

    private void Btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_simpanActionPerformed
        simpanDataPengembalian();
    }//GEN-LAST:event_Btn_simpanActionPerformed
    
    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {                                     
        String keyword = txt_Pencarian.getText();
        if (keyword.equals("Cari")) { // Jika placeholder, jangan lakukan pencarian
            tampilkanDataPengembalian();
        } else {
            cariDataPengembalian(keyword);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_simpan;
    private javax.swing.JButton Btn_tambah;
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_add;
    private javax.swing.JPanel Panel_view;
    private javax.swing.JButton btn_batal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txt_Pencarian;
    private javax.swing.JTextField txt_Tanggakkembali;
    private javax.swing.JTextField txt_Tanggalpinjam;
    private javax.swing.JTextField txt_denda;
    private javax.swing.JTextField txt_idanggota;
    private javax.swing.JTextField txt_idpeminjaman;
    private javax.swing.JTextField txt_judul;
    private javax.swing.JTextField txt_kategoribuku;
    private javax.swing.JTextField txt_namapeminjam;
    private javax.swing.JTextField txt_statuspeminjam;
    private javax.swing.JTextField txt_telpon;
    // End of variables declaration//GEN-END:variables
}
