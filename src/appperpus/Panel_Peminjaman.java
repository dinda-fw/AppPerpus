/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package appperpus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color; // Import Color class
import java.awt.event.FocusAdapter; // Import FocusAdapter
import java.awt.event.FocusEvent; // Import FocusEvent
import javax.swing.JTextField; // Import JTextField


public class Panel_Peminjaman extends javax.swing.JPanel {

    /**
     * Creates new form Panel_dashboard
     */
    public Panel_Peminjaman() {
        initComponents();
        loadTableData();
        // Anda bisa memanggil clearInputFields() di sini juga jika Anda ingin form tambah bersih setiap kali panel dimuat
        clearInputFields();
         addPlaceholderListeners(); // Panggil metode ini setelah initComponents()
    }
    
    private void addPlaceholderListeners() {
        // Untuk txt_cari (di Panel_view)
        setPlaceholder(txt_Search, "Cari");

        // Untuk JTextFields di Panel_add
        setPlaceholder(txt_id, "No.ID");
        setPlaceholder(txt_nama, "Nama Peminjam");
        setPlaceholder(txt_idAnggota, "id");
        setPlaceholder(txt_telpon, "No. Telpon");
        setPlaceholder(txt_TanggalPinjam, "Tanggal Pinjam (YYYY-MM-DD)");
        setPlaceholder(txt_TanggalKembali, "Tanggal Kembali (YYYY-MM-DD)");
      
        setPlaceholder(txt_judul, "Judul");
        setPlaceholder(txt_penulis, "Penulis");
        setPlaceholder(txt_StatusPeminjaman, "Status");
    }
    
    // Metode helper untuk mengatur placeholder
    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(new Color(204, 204, 204));

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(204, 204, 204));
                }
            }
        });
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
        txt_Search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Panel_add = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Btn_simpan = new javax.swing.JButton();
        btn_hapus1 = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_idAnggota = new javax.swing.JTextField();
        txt_telpon = new javax.swing.JTextField();
        txt_TanggalKembali = new javax.swing.JTextField();
        txt_StatusPeminjaman = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_judul = new javax.swing.JTextField();
        txt_penulis = new javax.swing.JTextField();
        txt_TanggalPinjam = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        Panel_Main.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Main.setPreferredSize(new java.awt.Dimension(70, 50));
        Panel_Main.setLayout(new java.awt.CardLayout());

        Panel_view.setBackground(new java.awt.Color(255, 255, 255));
        Panel_view.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setText("Daftar Peminjam Buku Perpustakaan");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel2.setText("Master Data > Peminjaman");

        Btn_tambah.setBackground(new java.awt.Color(153, 102, 0));
        Btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        Btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add_white.png"))); // NOI18N
        Btn_tambah.setText("Tambah");
        Btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambahActionPerformed(evt);
            }
        });

        txt_Search.setForeground(new java.awt.Color(204, 204, 204));
        txt_Search.setText("Cari");

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addComponent(Btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(txt_Search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Main.add(Panel_view, "card2");

        Panel_add.setBackground(new java.awt.Color(255, 255, 255));
        Panel_add.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Tambah Data Peminjam Buku Perpustakaan");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel4.setText("Master Data > Peminjaman");

        Btn_simpan.setBackground(new java.awt.Color(153, 102, 0));
        Btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        Btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save_white.png"))); // NOI18N
        Btn_simpan.setText("Simpan");
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_simpanActionPerformed(evt);
            }
        });

        btn_hapus1.setBackground(new java.awt.Color(204, 204, 204));
        btn_hapus1.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel_white.png"))); // NOI18N
        btn_hapus1.setText("Batal");
        btn_hapus1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus1ActionPerformed(evt);
            }
        });

        txt_id.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_id.setForeground(new java.awt.Color(204, 204, 204));
        txt_id.setText("No.ID");
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel7.setText("ID_ Peminjaman");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel8.setText("Nama");

        txt_nama.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(204, 204, 204));
        txt_nama.setText("Nama Peminjam");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel9.setText("ID_Anggota");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel10.setText("Telpon");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel11.setText("Tangal Pinjam");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel12.setText("Tanggal Kembali");

        txt_idAnggota.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_idAnggota.setForeground(new java.awt.Color(204, 204, 204));
        txt_idAnggota.setText("id");

        txt_telpon.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_telpon.setForeground(new java.awt.Color(204, 204, 204));
        txt_telpon.setText("No. Telpon");

        txt_TanggalKembali.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_TanggalKembali.setForeground(new java.awt.Color(204, 204, 204));
        txt_TanggalKembali.setText("Tanggal Kembali");

        txt_StatusPeminjaman.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_StatusPeminjaman.setForeground(new java.awt.Color(204, 204, 204));
        txt_StatusPeminjaman.setText("Status");
        txt_StatusPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_StatusPeminjamanActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel14.setText("Judul");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel15.setText("Penulis");

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel16.setText("Status Peminjaman");

        txt_judul.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_judul.setForeground(new java.awt.Color(204, 204, 204));
        txt_judul.setText("Judul");

        txt_penulis.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_penulis.setForeground(new java.awt.Color(204, 204, 204));
        txt_penulis.setText("Penulis");

        txt_TanggalPinjam.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_TanggalPinjam.setForeground(new java.awt.Color(204, 204, 204));
        txt_TanggalPinjam.setText("Tanggal Pinjam");
        txt_TanggalPinjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TanggalPinjamActionPerformed(evt);
            }
        });

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
                                .addComponent(btn_hapus1)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                                    .addComponent(txt_id))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_telpon)
                                        .addComponent(txt_TanggalPinjam, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_idAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Panel_addLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4))
                                    .addGroup(Panel_addLayout.createSequentialGroup()
                                        .addGap(86, 86, 86)
                                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_judul, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel15)
                                            .addComponent(txt_penulis, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16)
                                            .addComponent(txt_StatusPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12)
                                            .addComponent(txt_TanggalKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
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
                    .addComponent(btn_hapus1))
                .addGap(21, 21, 21)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_judul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_penulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_StatusPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_telpon)
                    .addComponent(txt_TanggalKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_TanggalPinjam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(230, 230, 230))
        );

        Panel_Main.add(Panel_add, "card2");

        add(Panel_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambahActionPerformed
        Panel_Main.removeAll();
        Panel_Main.add(Panel_add);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        
    }//GEN-LAST:event_Btn_tambahActionPerformed

    private void btn_hapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus1ActionPerformed
        Panel_Main.removeAll();
        Panel_Main.add(Panel_view);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        
       
    }//GEN-LAST:event_btn_hapus1ActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_TanggalPinjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TanggalPinjamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TanggalPinjamActionPerformed

    private void txt_StatusPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_StatusPeminjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_StatusPeminjamanActionPerformed

    private void Btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_simpanActionPerformed
        // Aksi ketika tombol "Simpan" diklik untuk menambahkan data baru
        String id_peminjaman = txt_id.getText();
        String nama_peminjam = txt_nama.getText();
        String no_telepon = txt_telpon.getText();
        String judul_buku = txt_judul.getText();
        String tanggal_peminjaman = txt_TanggalPinjam.getText();
        String tanggal_pengembalian = txt_TanggalKembali.getText();
        String status_peminjaman = txt_StatusPeminjaman.getText();
        String id_anggota = txt_idAnggota.getText();

        // Validasi dasar (Anda bisa menambahkan validasi yang lebih kuat)
        if (id_peminjaman.isEmpty() || nama_peminjam.isEmpty() || no_telepon.isEmpty() ||
            judul_buku.isEmpty() || tanggal_peminjaman.isEmpty() || tanggal_pengembalian.isEmpty() ||
            status_peminjaman.isEmpty() || id_anggota.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", "Kesalahan Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = koneksi.getKoneksi()) {
            String sql = "INSERT INTO tb_peminjaman (Id_Peminjaman, Nama_Peminjam, No_Telepon, Judul_Buku, Tanggal_Peminjaman, Tanggal_Pengembalian, Status_Peminjaman, Id_Anggota) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, id_peminjaman);
            pst.setString(2, nama_peminjam);
            pst.setString(3, no_telepon);
            pst.setString(4, judul_buku);
            pst.setString(5, tanggal_peminjaman);
            pst.setString(6, tanggal_pengembalian);
            pst.setString(7, status_peminjaman);
            pst.setString(8, id_anggota);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearInputFields();
                // Beralih kembali ke panel tampilan dan segarkan tabel
                Panel_Main.removeAll();
                Panel_Main.add(Panel_view);
                Panel_Main.repaint();
                Panel_Main.revalidate();
                loadTableData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data peminjaman.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Kesalahan Basis Data", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_Btn_simpanActionPerformed
    
    
        
    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {                                     
        // Filter data tabel saat pengguna mengetik di kolom pencarian
        filterTableData(txt_Search.getText());
    } 
    
    private void loadTableData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Peminjaman");
        model.addColumn("Nama Peminjam");
        model.addColumn("No. Telepon");
        model.addColumn("Judul Buku");
        model.addColumn("Tanggal Peminjaman");
        model.addColumn("Tanggal Pengembalian");
        model.addColumn("Status Peminjaman");
        model.addColumn("ID Anggota");

        try (Connection conn = koneksi.getKoneksi()) {
            String sql = "SELECT * FROM tb_peminjaman";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("Id_Peminjaman"),
                    rs.getString("Nama_Peminjam"),
                    rs.getString("No_Telepon"),
                    rs.getString("Judul_Buku"),
                    rs.getDate("Tanggal_Peminjaman"),
                    rs.getDate("Tanggal_Pengembalian"),
                    rs.getString("Status_Peminjaman"),
                    rs.getString("Id_Anggota")
                });
            }
            jTable1.setModel(model);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error memuat data: " + ex.getMessage(), "Kesalahan Basis Data", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    
    private void clearInputFields() {
        txt_id.setText("No.ID");
        txt_id.setForeground(new java.awt.Color(204, 204, 204));
        txt_nama.setText("Nama Peminjam");
        txt_nama.setForeground(new java.awt.Color(204, 204, 204));
        txt_telpon.setText("No. Telpon");
        txt_telpon.setForeground(new java.awt.Color(204, 204, 204));
        txt_judul.setText("Judul");
        txt_judul.setForeground(new java.awt.Color(204, 204, 204));
        txt_TanggalPinjam.setText("YYYY-MM-DD");
        txt_TanggalPinjam.setForeground(new java.awt.Color(204, 204, 204));
        txt_TanggalKembali.setText("YYYY-MM-DD");
        txt_TanggalKembali.setForeground(new java.awt.Color(204, 204, 204));
        txt_StatusPeminjaman.setText("Status");
        txt_StatusPeminjaman.setForeground(new java.awt.Color(204, 204, 204));
        txt_idAnggota.setText("id");
        txt_idAnggota.setForeground(new java.awt.Color(204, 204, 204));
    }
    
    private void filterTableData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Hapus data yang ada

        try (Connection conn = koneksi.getKoneksi()) {
            String sql = "SELECT * FROM tb_peminjaman WHERE " +
                         "Id_Peminjaman LIKE ? OR " +
                         "Nama_Peminjam LIKE ? OR " +
                         "No_Telepon LIKE ? OR " +
                         "Judul_Buku LIKE ? OR " +
                         "Tanggal_Peminjaman LIKE ? OR " +
                         "Tanggal_Pengembalian LIKE ? OR " +
                         "Status_Peminjaman LIKE ? OR " +
                         "Id_Anggota LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            for (int i = 1; i <= 9; i++) {
                pst.setString(i, "%" + keyword + "%");
            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("Id_Peminjaman"),
                    rs.getString("Nama_Peminjam"),
                    rs.getString("No_Telepon"),
                    rs.getString("Judul_Buku"),
                    rs.getDate("Tanggal_Peminjaman"),
                    rs.getDate("Tanggal_Pengembalian"),
                    rs.getString("Status_Peminjaman"),
                    rs.getString("Id_Anggota")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error memfilter data: " + ex.getMessage(), "Kesalahan Basis Data", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    
    
    
 
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_simpan;
    private javax.swing.JButton Btn_tambah;
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_add;
    private javax.swing.JPanel Panel_view;
    private javax.swing.JButton btn_hapus1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JTextField txt_Search;
    private javax.swing.JTextField txt_StatusPeminjaman;
    private javax.swing.JTextField txt_TanggalKembali;
    private javax.swing.JTextField txt_TanggalPinjam;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_idAnggota;
    private javax.swing.JTextField txt_judul;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_penulis;
    private javax.swing.JTextField txt_telpon;
    // End of variables declaration//GEN-END:variables
}
