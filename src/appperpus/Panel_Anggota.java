/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package appperpus;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.ButtonGroup;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent; // Ditambahkan untuk DocumentListener
import javax.swing.event.DocumentListener;

import java.io.File; // Untuk File
import java.io.FileOutputStream; // Untuk menulis file
import java.io.IOException;
import javax.swing.JFileChooser; // Untuk dialog pilih lokasi simpan file
import javax.swing.filechooser.FileNameExtensionFilter;

// Import library Apache POI untuk Excel
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // Untuk format .xlsx


public class Panel_Anggota extends javax.swing.JPanel {

    private DefaultTableModel model;
    private ButtonGroup jenisKelaminButtonGroup; // Deklarasi ButtonGroup
    
    private int currentPage = 1;
    private int rowsPerPage = 14; // Default rows per page, sesuai cbx_data
    private int totalRows = 0;
    private int totalPages = 0;
    
    // Variabel untuk mode edit
    private boolean isEditMode = false;
    private String idAnggotaToEdit = "";

    
    public Panel_Anggota() {
        initComponents();
        setupTable();
        
        // Inisialisasi ButtonGroup untuk jenis kelamin
        jenisKelaminButtonGroup = new ButtonGroup();
        jenisKelaminButtonGroup.add(btn_laki);
        jenisKelaminButtonGroup.add(btn_perempuan);
        
        // Atur placeholder awal untuk text fields
        setPlaceholder(txt_id, "No.ID");
        setPlaceholder(txt_nama, "Nama Anggota");
        setPlaceholder(txt_Email, "Email");
        setPlaceholder(txt_telpon, "No. Telpon");
        setPlaceholder(txt_TglBergabung, "YYYY-MM-DD");
        setPlaceholder(txt_kelazz, "Kelas");
        setPlaceholder(txt_Username, "Username");
        setPlaceholder(txt_Password, "Password");
        setPlaceholder(txt_TTL, "Tempat, YYYY-MM-DD"); // Contoh placeholder yang lebih jelas
        setPlaceholder(txt_Angkatan, "YYYY");
        
        // Atur txt_Cari (pencarian)
        setupSearchField(txt_Cari, "Cari");
        
        // Menambahkan action listeners untuk tombol navigasi halaman
        btn_Firstpage.addActionListener(e -> navigatePage(1));
        btn_before.addActionListener(e -> navigatePage(currentPage - 1));
        btn_next.addActionListener(e -> navigatePage(currentPage + 1));
        btn_Lastpage.addActionListener(e -> navigatePage(totalPages));
        
        cbx_data.addActionListener(e -> {
            rowsPerPage = Integer.parseInt(cbx_data.getSelectedItem().toString());
            currentPage = 1; // Reset ke halaman pertama saat mengganti jumlah baris per halaman
            loadDataAnggota();
        });

        // Pastikan Btn_tambah1 memiliki action listener untuk simpan/update
        Btn_tambah1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Btn_tambah1ActionPerformed(evt); // Ini adalah method yang akan menangani simpan/update
            }
        });

        // Menambahkan action listener untuk tombol "Print" (Export Excel)
        btn_Print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exportToExcel(); // Panggil metode ekspor Excel
            }
        });

        loadDataAnggota(); // Panggil metode untuk memuat data awal
        updatePaginationInfo();
    }
    
    private void setupTable() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat sel tidak bisa diedit langsung di tabel
            }
        };
        model.addColumn("ID Anggota");
        model.addColumn("Nama Anggota");
        model.addColumn("Kelas");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Email");
        model.addColumn("No. Telepon");
        model.addColumn("Jenis Kelamin");
        model.addColumn("Tempat & Tanggal Lahir");
        model.addColumn("Tahun Angkatan");
        model.addColumn("Tanggal Bergabung");
        jTable1.setModel(model);
    }

    // Metode untuk memuat data anggota dari database
    private void loadDataAnggota() {
        model.setRowCount(0); // Bersihkan data tabel yang ada
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet res = null;
        
        try {
            conn = koneksi.getKoneksi();
            // Query untuk mendapatkan total baris
            String countSql = "SELECT COUNT(*) FROM Anggota";
            Statement countStat = conn.createStatement();
            ResultSet countRes = countStat.executeQuery(countSql);
            if (countRes.next()) {
                totalRows = countRes.getInt(1);
            }
            countRes.close();
            countStat.close();
            
            totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            } else if (totalPages == 0) {
                currentPage = 0; // Jika tidak ada data, set halaman ke 0
            } else if (currentPage == 0 && totalRows > 0) {
                currentPage = 1; // Jika ada data tapi halaman 0, set ke 1
            }


            // Query untuk memuat data dengan LIMIT dan OFFSET untuk paginasi
            String sql = "SELECT Id_Anggota, Nama_Anggota, Kelas, Username, Password, Email, No_Telepon, Jenis_Kelamin, Tempat_dan_Tanggal_Lahir, Tahun_Angkatan, Tanggal_Bergabung " +
                         "FROM Anggota LIMIT ? OFFSET ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, rowsPerPage);
            pst.setInt(2, (currentPage - 1) * rowsPerPage);
            res = pst.executeQuery();

            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("Id_Anggota"),
                    res.getString("Nama_Anggota"),
                    res.getString("Kelas"),
                    res.getString("Username"),
                    res.getString("Password"),
                    res.getString("Email"),
                    res.getString("No_Telepon"),
                    res.getString("Jenis_Kelamin"),
                    res.getString("Tempat_dan_Tanggal_Lahir"),
                    res.getString("Tahun_Angkatan"),
                    res.getDate("Tanggal_Bergabung")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat data anggota: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (res != null) res.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        updatePaginationInfo(); // Perbarui info halaman setelah data dimuat
    }
    
    private void filterDataAnggota(String keyword) {
        model.setRowCount(0); // Bersihkan tabel
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet res = null;

        try {
            conn = koneksi.getKoneksi();
            // Query yang lebih komprehensif untuk pencarian
            String sql = "SELECT Id_Anggota, Nama_Anggota, Kelas, Username, Password, Email, No_Telepon, Jenis_Kelamin, Tempat_dan_Tanggal_Lahir, Tahun_Angkatan, Tanggal_Bergabung " +
                         "FROM Anggota WHERE Id_Anggota LIKE ? OR Nama_Anggota LIKE ? OR Kelas LIKE ? OR Username LIKE ? OR Email LIKE ? OR No_Telepon LIKE ? OR Jenis_Kelamin LIKE ? OR Tempat_dan_Tanggal_Lahir LIKE ? OR Tahun_Angkatan LIKE ? OR Tanggal_Bergabung LIKE ?";
            pst = conn.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 10; i++) { // Ada 10 kolom yang dicari
                pst.setString(i, searchKeyword);
            }
            res = pst.executeQuery();

            while (res.next()) {
                model.addRow(new Object[]{
                    res.getString("Id_Anggota"),
                    res.getString("Nama_Anggota"),
                    res.getString("Kelas"),
                    res.getString("Username"),
                    res.getString("Password"),
                    res.getString("Email"),
                    res.getString("No_Telepon"),
                    res.getString("Jenis_Kelamin"),
                    res.getString("Tempat_dan_Tanggal_Lahir"),
                    res.getString("Tahun_Angkatan"),
                    res.getDate("Tanggal_Bergabung")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memfilter data anggota: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (res != null) res.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // Tidak perlu updatePaginationInfo() karena ini mode filter, bukan paginasi murni
    }
    
    
    private void setPlaceholder(javax.swing.JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(new java.awt.Color(204, 204, 204));
    textField.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(new java.awt.Color(0, 0, 0)); // Warna teks normal
            }
        }
        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(new java.awt.Color(204, 204, 204)); // Warna placeholder
            }
        }
    });
 }
    
        private void setupSearchField(javax.swing.JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(new Color(204, 204, 204));
        textField.setFont(textField.getFont().deriveFont(java.awt.Font.ITALIC));

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                    textField.setFont(textField.getFont().deriveFont(java.awt.Font.PLAIN));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(204, 204, 204));
                    textField.setFont(textField.getFont().deriveFont(java.awt.Font.ITALIC));
                }
            }
        });

        // Menambahkan DocumentListener untuk melakukan filter secara real-time
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textField.getText();
                if (text.isEmpty()) { // Jika teks kosong setelah perubahan, muat ulang semua data
                    loadDataAnggota();
                } else {
                    filterDataAnggota(text);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textField.getText();
                if (text.isEmpty()) { // Jika teks kosong setelah perubahan, muat ulang semua data
                    loadDataAnggota();
                } else {
                    filterDataAnggota(text);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Tidak perlu implementasi di sini untuk PlainDocument
            }
        });
    }

    /**
     * Memperbarui informasi paginasi di label lb_halaman dan mengaktifkan/menonaktifkan tombol.
     */
    private void updatePaginationInfo() {
        lb_halaman.setText("Halaman " + currentPage + " dari " + totalPages + " (" + totalRows + " data)");
        
        btn_Firstpage.setEnabled(currentPage > 1);
        btn_before.setEnabled(currentPage > 1);
        btn_next.setEnabled(currentPage < totalPages);
        btn_Lastpage.setEnabled(currentPage < totalPages);
        
        // Handle case where there's no data
        if (totalRows == 0) {
            btn_Firstpage.setEnabled(false);
            btn_before.setEnabled(false);
            btn_next.setEnabled(false);
            btn_Lastpage.setEnabled(false);
            lb_halaman.setText("Tidak ada data");
        }
    }

 
    private void navigatePage(int targetPage) {
        if (targetPage >= 1 && targetPage <= totalPages) {
            currentPage = targetPage;
            loadDataAnggota();
        } else if (totalPages == 0) {
             // Do nothing if no data
        } else if (targetPage < 1) {
            currentPage = 1;
            loadDataAnggota();
        } else if (targetPage > totalPages) {
            currentPage = totalPages;
            loadDataAnggota();
        }
    }
    
    private void clearForm() {
        txt_id.setText("");
        txt_nama.setText("");
        txt_Email.setText("");
        txt_telpon.setText("");
        txt_TglBergabung.setText("");
        jenisKelaminButtonGroup.clearSelection();
        txt_kelazz.setText("");
        txt_Username.setText("");
        txt_Password.setText("");
        txt_TTL.setText("");
        txt_Angkatan.setText("");
        
        setPlaceholder(txt_id, "No.ID");
        setPlaceholder(txt_nama, "Nama Anggota");
        setPlaceholder(txt_Email, "Email");
        setPlaceholder(txt_telpon, "No. Telpon");
        setPlaceholder(txt_TglBergabung, "YYYY-MM-DD");
        setPlaceholder(txt_kelazz, "Kelas");
        setPlaceholder(txt_Username, "Username");
        setPlaceholder(txt_Password, "Password");
        setPlaceholder(txt_TTL, "Tempat, YYYY-MM-DD");
        setPlaceholder(txt_Angkatan, "YYYY");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Btn_tambah = new javax.swing.JButton();
        txt_Cari = new javax.swing.JTextField();
        btn_hapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_Lastpage = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        cbx_data = new javax.swing.JComboBox<>();
        btn_before = new javax.swing.JButton();
        btn_Firstpage = new javax.swing.JButton();
        lb_halaman = new javax.swing.JLabel();
        btn_Print = new javax.swing.JButton();
        Panel_add = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Btn_tambah1 = new javax.swing.JButton();
        btn_hapus1 = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_kelazz = new javax.swing.JTextField();
        txt_Angkatan = new javax.swing.JTextField();
        txt_TglBergabung = new javax.swing.JTextField();
        btn_laki = new javax.swing.JRadioButton();
        btn_perempuan = new javax.swing.JRadioButton();
        txt_Email = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_telpon = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_Username = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txt_Password = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txt_TTL = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        Panel_Main.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Main.setPreferredSize(new java.awt.Dimension(70, 50));
        Panel_Main.setLayout(new java.awt.CardLayout());

        Panel_view.setBackground(new java.awt.Color(255, 255, 255));
        Panel_view.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Daftar Anggota");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel2.setText("Master Data > Anggota");

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

        Btn_tambah.setBackground(new java.awt.Color(153, 102, 0));
        Btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        Btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add_white.png"))); // NOI18N
        Btn_tambah.setText("Tambah");
        Btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambahActionPerformed(evt);
            }
        });

        txt_Cari.setForeground(new java.awt.Color(204, 204, 204));
        txt_Cari.setText("Cari");

        btn_hapus.setBackground(new java.awt.Color(204, 0, 0));
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete_white.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        btn_Lastpage.setText("Last Page");
        btn_Lastpage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LastpageActionPerformed(evt);
            }
        });

        btn_next.setText(">");

        cbx_data.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "14", "28", "54", "108" }));

        btn_before.setText("<");

        btn_Firstpage.setText("First Page");

        lb_halaman.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_halaman.setText("Halaman of Total Halaman");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_halaman, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_Firstpage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_before, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Lastpage)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_halaman, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Firstpage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_before, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Lastpage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_Print.setBackground(new java.awt.Color(102, 102, 102));
        btn_Print.setForeground(new java.awt.Color(255, 255, 255));
        btn_Print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/print_white.png"))); // NOI18N
        btn_Print.setText("Print");
        btn_Print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_PrintActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_viewLayout = new javax.swing.GroupLayout(Panel_view);
        Panel_view.setLayout(Panel_viewLayout);
        Panel_viewLayout.setHorizontalGroup(
            Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_viewLayout.createSequentialGroup()
                        .addComponent(Btn_tambah)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Print)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(143, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(143, Short.MAX_VALUE)))
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
                .addGap(18, 18, 18)
                .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Btn_tambah)
                        .addComponent(btn_hapus)
                        .addComponent(btn_Print))
                    .addComponent(txt_Cari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(408, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        Panel_Main.add(Panel_view, "card2");

        Panel_add.setBackground(new java.awt.Color(255, 255, 255));
        Panel_add.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Tambah Daftar Anggota");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel4.setText("Master Data > Tambah Anggota");

        Btn_tambah1.setBackground(new java.awt.Color(153, 102, 0));
        Btn_tambah1.setForeground(new java.awt.Color(255, 255, 255));
        Btn_tambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save_white.png"))); // NOI18N
        Btn_tambah1.setText("simpan");
        Btn_tambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambah1ActionPerformed(evt);
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

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel7.setText("ID");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel8.setText("Nama");

        txt_nama.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(204, 204, 204));
        txt_nama.setText("Nama Anggota");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel9.setText("Kelazz");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel10.setText("Email");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel11.setText("Jenis Kelamin");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel12.setText("Tanggal Bergabung");

        txt_kelazz.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_kelazz.setForeground(new java.awt.Color(204, 204, 204));
        txt_kelazz.setText("Kelaz");

        txt_Angkatan.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_Angkatan.setForeground(new java.awt.Color(204, 204, 204));
        txt_Angkatan.setText("Tahun");
        txt_Angkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AngkatanActionPerformed(evt);
            }
        });

        txt_TglBergabung.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_TglBergabung.setForeground(new java.awt.Color(204, 204, 204));

        btn_laki.setText("Laki-laki");

        btn_perempuan.setText("Perempuan");

        txt_Email.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_Email.setForeground(new java.awt.Color(204, 204, 204));
        txt_Email.setText("Email");
        txt_Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_EmailActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel13.setText("Tahun Angkatan");

        txt_telpon.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_telpon.setForeground(new java.awt.Color(204, 204, 204));
        txt_telpon.setText("No Telp");
        txt_telpon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telponActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel14.setText("No. Telpon");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel15.setText("Username");

        txt_Username.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_Username.setForeground(new java.awt.Color(204, 204, 204));

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel16.setText("Password");

        txt_Password.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_Password.setForeground(new java.awt.Color(204, 204, 204));

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel17.setText("Tempat, Tanggal Lahir");

        txt_TTL.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_TTL.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout Panel_addLayout = new javax.swing.GroupLayout(Panel_add);
        Panel_add.setLayout(Panel_addLayout);
        Panel_addLayout.setHorizontalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_addLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(Btn_tambah1)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
            .addGroup(Panel_addLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel13)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_TTL, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_Angkatan, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_kelazz, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_nama, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                                .addComponent(txt_id, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_TglBergabung, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(txt_telpon, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(btn_laki)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_perempuan)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        Panel_addLayout.setVerticalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_addLayout.createSequentialGroup()
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_tambah1)
                    .addComponent(btn_hapus1))
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel15))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_kelazz, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_Angkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_telpon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TglBergabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_laki)
                    .addComponent(btn_perempuan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Main.add(Panel_add, "card2");

        add(Panel_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void btn_LastpageActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Tombol Last Page diklik!");
    
}

    
    
    private void Btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambahActionPerformed
    
        isEditMode = false; // Set mode ke tambah
        clearForm(); // Bersihkan semua field
        
        // Atur kembali teks tombol simpan menjadi "simpan"
        Btn_tambah1.setText("simpan"); 
        
        Panel_Main.removeAll();
        Panel_Main.add(Panel_add);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        
        txt_id.setText("No.ID");
        txt_nama.setText("Nama Anggota");
        txt_Email.setText("Email");
        txt_telpon.setText("No. Telpon");
        txt_TglBergabung.setText("YYYY-MM-DD");
        jenisKelaminButtonGroup.clearSelection(); // Clear radio button selection
        
        txt_kelazz.setText("Kelas");
        txt_Username.setText("Username");
        txt_Password.setText("Password");
        txt_TTL.setText("Tempat, YYYY-MM-DD");
        txt_Angkatan.setText("YYYY");
        
        // Reset warna placeholder
        setPlaceholder(txt_id, "No.ID");
        setPlaceholder(txt_nama, "Nama Anggota");
        setPlaceholder(txt_Email, "Email");
        setPlaceholder(txt_telpon, "No. Telpon");
        setPlaceholder(txt_TglBergabung, "YYYY-MM-DD");
        
        setPlaceholder(txt_kelazz, "Kelas");
        setPlaceholder(txt_Username, "Username");
        setPlaceholder(txt_Password, "Password");
        setPlaceholder(txt_TTL, "Tempat, YYYY-MM-DD");
        setPlaceholder(txt_Angkatan, "YYYY");
        
    }//GEN-LAST:event_Btn_tambahActionPerformed

    private void btn_hapus1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus1ActionPerformed
        //ini adalah tombol "Batal" di Panel_add untuk batal
        Panel_Main.removeAll();
        Panel_Main.add(Panel_view);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        loadDataAnggota();
        clearForm(); // Bersihkan form
        isEditMode = false; // Reset mode
        Btn_tambah1.setText("simpan"); // Reset teks tombol
    }//GEN-LAST:event_btn_hapus1ActionPerformed


    
    private void Btn_tambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambah1ActionPerformed
        // Saat tombol simpan di Panel_add diklik
        String idAnggota = txt_id.getText().trim();
        String namaAnggota = txt_nama.getText().trim();
        String email = txt_Email.getText().trim();
        String noTelponStr = txt_telpon.getText().trim();
        String jenisKelamin = "";
        String tanggalBergabungStr = txt_TglBergabung.getText().trim();
        
        String kelas = txt_kelazz.getText().trim();
        String username = txt_Username.getText().trim();
        String password = txt_Password.getText().trim();
        String tempatTanggalLahir = txt_TTL.getText().trim();
        String tahunAngkatanStr = txt_Angkatan.getText().trim();
        
        
        // Validasi input
        if (idAnggota.isEmpty() || idAnggota.equals("No.ID") ||
            namaAnggota.isEmpty() || namaAnggota.equals("Nama Anggota") ||
            email.isEmpty() || email.equals("Email") ||
            noTelponStr.isEmpty() || noTelponStr.equals("No. Telpon") ||
            tanggalBergabungStr.isEmpty() || tanggalBergabungStr.equals("YYYY-MM-DD") ||
            kelas.isEmpty() || kelas.equals("Kelas") ||
            username.isEmpty() || username.equals("Username") ||
            password.isEmpty() || password.equals("Password") ||
            tempatTanggalLahir.isEmpty() || tempatTanggalLahir.equals("Tempat, YYYY-MM-DD") ||
            tahunAngkatanStr.isEmpty() || tahunAngkatanStr.equals("YYYY")) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi dengan data yang valid!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (btn_laki.isSelected()) {
            jenisKelamin = "Laki-laki";
        } else if (btn_perempuan.isSelected()) {
            jenisKelamin = "Perempuan";
        } else {
            JOptionPane.showMessageDialog(this, "Pilih jenis kelamin!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        long noTelpon;
        try {
            noTelpon = Long.parseLong(noTelponStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "No. Telepon harus berupa angka!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int tahunAngkatan;
        try {
            tahunAngkatan = Integer.parseInt(tahunAngkatanStr);
            if (tahunAngkatan < 1900 || tahunAngkatan > 2100) { // Example range
                JOptionPane.showMessageDialog(this, "Tahun Angkatan tidak valid!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun Angkatan harus berupa angka!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validasi format tanggal
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalBergabung;
        try {
            tanggalBergabung = dateFormat.parse(tanggalBergabungStr);
        } catch (java.text.ParseException e) {
            JOptionPane.showMessageDialog(this, "Format Tanggal Bergabung salah. Gunakan YYYY-MM-DD.", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = koneksi.getKoneksi();
            // Periksa apakah ID Anggota sudah ada
            String checkSql = "SELECT COUNT(*) FROM Anggota WHERE id_Anggota = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkSql);
            checkPst.setString(1, idAnggota);
            ResultSet checkRs = checkPst.executeQuery();
            if (checkRs.next() && checkRs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "ID Anggota sudah ada. Gunakan ID lain.", "Input Duplikat", JOptionPane.WARNING_MESSAGE);
                return;
            }
            checkRs.close();
            checkPst.close();
            
            // Masukkan data baru
            String sql = "INSERT INTO Anggota (id_Anggota, Nama_Anggota, Kelas, Username, Password, Jenis_Kelamin, Tempat_dan_Tanggal_Lahir, Tahun_Angkatan, Email, Tanggal_Bergabung, No_Telepon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, idAnggota);
            pst.setString(2, namaAnggota);
            pst.setString(3, kelas); // NEW
            pst.setString(4, username); // NEW
            pst.setString(5, password); // NEW
            pst.setString(6, jenisKelamin);
            pst.setString(7, tempatTanggalLahir); // NEW
            pst.setInt(8, tahunAngkatan); // NEW
            pst.setString(9, email);
            pst.setDate(10, new java.sql.Date(tanggalBergabung.getTime())); // Konversi java.util.Date ke java.sql.Date
            pst.setString(11, noTelponStr); // No_Telepon is varchar now
            
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data anggota berhasil ditambahkan!");
                // Kembali ke Panel_view setelah berhasil menyimpan
                Panel_Main.removeAll();
                Panel_Main.add(Panel_view);
                Panel_Main.repaint();
                Panel_Main.revalidate();
                loadDataAnggota(); // Muat ulang data di tabel
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data anggota.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            pst.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error saat menyimpan data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
        }            
    }//GEN-LAST:event_Btn_tambah1ActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
                // 1. Mendapatkan baris yang dipilih
        int[] selectedRows = jTable1.getSelectedRows(); // Mendapatkan indeks baris yang dipilih

    // Cek apakah ada baris yang dipilih
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data anggota yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return; // Hentikan proses jika tidak ada baris yang dipilih
        }

    // 2. Konfirmasi Hapus
        int confirmResult = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin ingin menghapus " + selectedRows.length + " data anggota terpilih?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        // Jika pengguna memilih "Tidak" atau menutup dialog, batalkan penghapusan
         if (confirmResult != JOptionPane.YES_OPTION) {
            return;
         }
         
        

        Connection conn = null; // Deklarasikan di luar try untuk finally block
        PreparedStatement pst = null; // Deklarasikan di luar try untuk finally block

        try {
            conn = koneksi.getKoneksi();
            // Mulai transaksi untuk memastikan semua hapus berhasil atau tidak sama sekali
            conn.setAutoCommit(false); // Matikan auto-commit

            String sql = "DELETE FROM Anggota WHERE id_Anggota = ?";
            pst = conn.prepareStatement(sql);

            // Iterasi dari belakang agar indeks baris tidak berubah saat menghapus dari model
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int modelRowIndex = jTable1.convertRowIndexToModel(selectedRows[i]); // Konversi ke indeks model

                // 3. Mengambil ID Anggota dari model tabel
                // ID Anggota ada di kolom pertama (indeks 0) dari model
                String idAnggotaToDelete = model.getValueAt(modelRowIndex, 0).toString();

                // 4. Menghapus dari Database
                pst.setString(1, idAnggotaToDelete);
                pst.addBatch(); // Tambahkan ke batch untuk eksekusi yang lebih efisien
        }

        int[] deleteCounts = pst.executeBatch(); // Jalankan semua perintah DELETE
        conn.commit(); // Komit transaksi jika semua berhasil

        JOptionPane.showMessageDialog(this, selectedRows.length + " data anggota berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

        // 5. Memperbarui Tampilan (memuat ulang data dari database)
        loadDataAnggota();

    } catch (SQLException e) {
        try {
            if (conn != null) {
                conn.rollback(); // Rollback transaksi jika ada error
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        JOptionPane.showMessageDialog(this, "Error saat menghapus data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        // Tutup PreparedStatement dan Connection di finally block
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.setAutoCommit(true); // Aktifkan kembali auto-commit
            // koneksi.getKoneksi() mungkin mengembalikan koneksi yang sama, jadi hati-hati menutupnya.
            // Jika koneksi dikelola secara global, pastikan tidak ditutup di sini jika masih dibutuhkan.
            // Namun, untuk PreparedStatement, selalu tutup.
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }            // TODO add your handling code here:
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void txt_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_EmailActionPerformed

    private void txt_telponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telponActionPerformed

    private void txt_AngkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AngkatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AngkatanActionPerformed

    private void btn_PrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_PrintActionPerformed
        exportToExcel();
    }//GEN-LAST:event_btn_PrintActionPerformed

    private void exportToExcel() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data di tabel untuk diekspor.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Data Anggota ke Excel");
        // Filter untuk hanya menampilkan file Excel
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));
        fileChooser.setSelectedFile(new File("Data_Anggota_Perpustakaan.xlsx")); // Nama file default

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            // Pastikan ekstensi .xlsx ada
            if (!fileToSave.getName().toLowerCase().endsWith(".xlsx")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".xlsx");
            }

            try (Workbook workbook = new XSSFWorkbook(); // Untuk format .xlsx
                 FileOutputStream out = new FileOutputStream(fileToSave)) {

                Sheet sheet = workbook.createSheet("Data Anggota");

                // Header kolom
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                }

                // Data baris
                for (int r = 0; r < model.getRowCount(); r++) {
                    Row dataRow = sheet.createRow(r + 1); // Baris data dimulai dari baris ke-1 (setelah header)
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        Cell cell = dataRow.createCell(c);
                        Object value = model.getValueAt(r, c);
                        if (value != null) {
                            cell.setCellValue(value.toString()); // Konversi semua nilai ke String
                        } else {
                            cell.setCellValue(""); // Jika null, set string kosong
                        }
                    }
                }

                workbook.write(out);
                JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke:\n" + fileToSave.getAbsolutePath(), "Ekspor Sukses", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saat mengekspor data: " + e.getMessage(), "Error Ekspor", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_tambah;
    private javax.swing.JButton Btn_tambah1;
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_add;
    private javax.swing.JPanel Panel_view;
    private javax.swing.JButton btn_Firstpage;
    private javax.swing.JButton btn_Lastpage;
    private javax.swing.JButton btn_Print;
    private javax.swing.JButton btn_before;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_hapus1;
    private javax.swing.JRadioButton btn_laki;
    private javax.swing.JButton btn_next;
    private javax.swing.JRadioButton btn_perempuan;
    private javax.swing.JComboBox<String> cbx_data;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lb_halaman;
    private javax.swing.JTextField txt_Angkatan;
    private javax.swing.JTextField txt_Cari;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_Password;
    private javax.swing.JTextField txt_TTL;
    private javax.swing.JTextField txt_TglBergabung;
    private javax.swing.JTextField txt_Username;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_kelazz;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_telpon;
    // End of variables declaration//GEN-END:variables

    
}
