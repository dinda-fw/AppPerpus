/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package appperpus;
import java.awt.CardLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton; // Assuming buttons are JButtons
import javax.swing.JFileChooser; // Untuk JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter; // Untuk filter jenis file di JFileChooser
import java.io.File; // Untuk objek File
import java.io.FileInputStream; // Untuk membaca file
import java.io.ByteArrayOutputStream; // Untuk menulis data file ke array byte
import java.io.IOException; // Untuk menangani exception I/O
import javax.swing.ImageIcon; // Pastikan ini diimpor
import java.awt.Image;       // Pastikan ini diimpor
import java.awt.Color; // Ditambahkan untuk efek placeholder
import java.awt.Component; // Ditambahkan untuk ImageRenderer
import java.awt.event.FocusEvent; // Ditambahkan untuk efek placeholder
import java.awt.event.FocusListener; // Ditambahkan untuk efek placeholder
import javax.swing.event.DocumentEvent; // Ditambahkan untuk DocumentListener
import javax.swing.event.DocumentListener; // Ditambahkan untuk DocumentListener
import javax.swing.JLabel; // Ditambahkan untuk ImageRenderer
import javax.swing.SwingConstants; // Ditambahkan untuk ImageRenderer


public class Panel_Buku extends javax.swing.JPanel {
    
  
   
    private byte[] bookImageBytes; // Variabel untuk menyimpan data gambar biner
    //private JTable tabel_buku; // Tabel untuk menampilkan data buku
    private DefaultTableModel tableModel;
    
    
    // Tombol (jika ada event listener yang perlu ditambahkan di sini)
    private JButton btn_tambah; // Tombol "Tambah" di Panel_view
    private JButton btn_simpan; // Tombol "Simpan" di Panel_add
   
    public Panel_Buku() {
        initComponents();
        
        txt_idBuku.setName("txt_idBuku"); // Tambahkan ini
        txt_judul.setName("txt_judul");   // Tambahkan ini
        txt_penulis.setName("txt_penulis"); // Dst. untuk semua JTextField
        txt_penerbit.setName("txt_penerbit");
        txt_kategori.setName("txt_kategori");
        txt_tahun.setName("txt_tahun");
        txt_rak.setName("txt_rak");
        txt_stok.setName("txt_stok");
        
        initTable(); // Panggil method baru untuk inisialisasi tabel
        setupSearchField(txt_CARI, "Cari");
        
        // Panggil setPlaceholder untuk setiap JTextField
        setPlaceholder(txt_idBuku, "ID Buku"); // Perhatikan: Sebelumnya Anda set "Judul" di bersih()
        setPlaceholder(txt_judul, "Judul Buku"); // Tambahkan ini jika txt_judul ada
        setPlaceholder(txt_penulis, "Penulis");
        setPlaceholder(txt_penerbit, "Penerbit");
        setPlaceholder(txt_kategori, "Kategori");
        setPlaceholder(txt_tahun, "Tahun Terbit"); // Pastikan nama txt_tahun sesuai
        setPlaceholder(txt_rak, "Rak Buku"); // Pastikan txt_rak ada dan ini placeholder-nya
        setPlaceholder(txt_stok, "Stok Buku");
        
        load_table();
             
    }
    
        private void initTable() {
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Membuat sel tidak bisa diedit langsung di tabel
            }
        };
        // Tambahkan kolom ke model tabel
        tableModel.addColumn("ID Buku");
        tableModel.addColumn("Judul");
        tableModel.addColumn("Penulis");
        tableModel.addColumn("Penerbit");
        tableModel.addColumn("Kategori");
        tableModel.addColumn("Tahun");
        tableModel.addColumn("Rak");
        tableModel.addColumn("Stok");
        tableModel.addColumn("Gambar"); // Kolom Gambar

        jTable1.setModel(tableModel); // Set model ke jTable1 Anda

        // Set Cell Renderer untuk kolom "Gambar" (asumsi indeks 8)
        jTable1.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());
        jTable1.setRowHeight(80); // Atur tinggi baris agar gambar tidak terpotong
    }
    
    
    
    private void setPlaceholder(javax.swing.JTextField textField, String placeholder) {
    textField.setText(placeholder);
    textField.setForeground(new java.awt.Color(204, 204, 204)); // Warna abu-abu untuk placeholder
    textField.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            System.out.println("Focus Gained on: " + textField.getName()); // Nama komponen jika diset
            System.out.println("Current text: '" + textField.getText() + "'");
            System.out.println("Placeholder: '" + placeholder + "'");

            if (textField.getText().equals(placeholder)) {
                System.out.println("Condition met: Text equals placeholder. Changing color to BLACK.");
                textField.setText("");
                textField.setForeground(new java.awt.Color(0, 0, 0)); // Warna teks normal (hitam)
            } else {
                System.out.println("Condition NOT met: Text does NOT equal placeholder. Keeping current color.");
                // Jika ini yang sering muncul, berarti ada sesuatu yang mengisi teks SEBELUM focusGained
                // atau teks placeholder tidak sama persis.
            }
        }
        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            System.out.println("Focus Lost on: " + textField.getName());
            System.out.println("Current text (on focusLost): '" + textField.getText() + "'");
            if (textField.getText().isEmpty()) {
                System.out.println("Text is empty. Setting placeholder.");
                textField.setText(placeholder);
                textField.setForeground(new java.awt.Color(204, 204, 204)); // Warna placeholder
            } else {
                System.out.println("Text is NOT empty. Keeping current text and color.");
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

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(textField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(textField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(textField.getText());
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Btn_tambah = new javax.swing.JButton();
        txt_CARI = new javax.swing.JTextField();
        btn_hapus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btn_last = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        cbx_data = new javax.swing.JComboBox<>();
        btn_before = new javax.swing.JButton();
        btn_first = new javax.swing.JButton();
        lb_halaman = new javax.swing.JLabel();
        btn_edit = new javax.swing.JButton();
        Panel_add = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Btn_simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        txt_idBuku = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_penulis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_penerbit = new javax.swing.JTextField();
        txt_kategori = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_tahun = new javax.swing.JTextField();
        framegambarbuku = new javax.swing.JLabel();
        imagepath = new javax.swing.JButton();
        tambahimage = new javax.swing.JButton();
        txt_stok = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_judul = new javax.swing.JTextField();
        txt_rak = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        Panel_Main.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Main.setPreferredSize(new java.awt.Dimension(70, 50));
        Panel_Main.setLayout(new java.awt.CardLayout());

        Panel_view.setBackground(new java.awt.Color(255, 255, 255));
        Panel_view.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Daftar Data Buku");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel2.setText("Master Data > Buku");

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

        txt_CARI.setForeground(new java.awt.Color(204, 204, 204));
        txt_CARI.setText("Cari");

        btn_hapus.setBackground(new java.awt.Color(153, 0, 0));
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete_white.png"))); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));

        btn_last.setText("Last Page");

        btn_next.setText(">");

        cbx_data.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "14", "28", "54", "108" }));

        btn_before.setText("<");

        btn_first.setText("First Page");

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
                        .addComponent(btn_first)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_before, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_data, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_last)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_halaman, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_first, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_before, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_data, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_last, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btn_edit.setBackground(new java.awt.Color(204, 204, 204));
        btn_edit.setForeground(new java.awt.Color(0, 153, 153));
        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/change_password.png"))); // NOI18N
        btn_edit.setText("Edit");

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
                        .addComponent(btn_edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_CARI, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(235, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(236, Short.MAX_VALUE)))
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
                        .addComponent(btn_edit))
                    .addComponent(txt_CARI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(523, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        Panel_Main.add(Panel_view, "card2");

        Panel_add.setBackground(new java.awt.Color(255, 255, 255));
        Panel_add.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Tambah Daftar Buku");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel4.setText("Master Data > Tambah Buku");

        Btn_simpan.setBackground(new java.awt.Color(153, 102, 0));
        Btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        Btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save_white.png"))); // NOI18N
        Btn_simpan.setText("Simpan");

        btn_batal.setBackground(new java.awt.Color(204, 204, 204));
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/cancel_white.png"))); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        txt_idBuku.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_idBuku.setForeground(new java.awt.Color(204, 204, 204));
        txt_idBuku.setText("id");
        txt_idBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idBukuActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel7.setText("Judul");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel8.setText("Penulis");

        txt_penulis.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_penulis.setForeground(new java.awt.Color(204, 204, 204));
        txt_penulis.setText("Penulis");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel9.setText("Penerbit");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel10.setText("Kategori");

        txt_penerbit.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_penerbit.setForeground(new java.awt.Color(204, 204, 204));
        txt_penerbit.setText("Penerbit");

        txt_kategori.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_kategori.setForeground(new java.awt.Color(204, 204, 204));
        txt_kategori.setText("Kategori");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel13.setText("Tahun");

        txt_tahun.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_tahun.setForeground(new java.awt.Color(204, 204, 204));
        txt_tahun.setText("Tahun");

        framegambarbuku.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/book_abu.png"))); // NOI18N
        framegambarbuku.setText("gambar buku");
        framegambarbuku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        imagepath.setText("Image path");
        imagepath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagepathActionPerformed(evt);
            }
        });

        tambahimage.setText("+");
        tambahimage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahimageActionPerformed(evt);
            }
        });

        txt_stok.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_stok.setForeground(new java.awt.Color(204, 204, 204));
        txt_stok.setText("stok");

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel14.setText("Stok");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel11.setText("ID_Buku");

        txt_judul.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_judul.setForeground(new java.awt.Color(204, 204, 204));
        txt_judul.setText("Judul");
        txt_judul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_judulActionPerformed(evt);
            }
        });

        txt_rak.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_rak.setForeground(new java.awt.Color(204, 204, 204));
        txt_rak.setText("Rak");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel15.setText("Rak");

        javax.swing.GroupLayout Panel_addLayout = new javax.swing.GroupLayout(Panel_add);
        Panel_add.setLayout(Panel_addLayout);
        Panel_addLayout.setHorizontalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_addLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txt_tahun, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_penulis, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_penerbit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_kategori, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                        .addComponent(txt_rak, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addComponent(txt_judul)
                                .addGap(6, 6, 6)))
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(framegambarbuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addComponent(imagepath, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tambahimage)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_idBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel14)
                            .addGroup(Panel_addLayout.createSequentialGroup()
                                .addComponent(Btn_simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn_batal))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 224, Short.MAX_VALUE)))
                .addContainerGap())
        );
        Panel_addLayout.setVerticalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel_addLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Btn_simpan)
                    .addComponent(btn_batal))
                .addGap(9, 9, 9)
                .addComponent(jLabel11)
                .addGap(2, 2, 2)
                .addComponent(txt_idBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(txt_judul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(7, 7, 7)
                        .addComponent(txt_penulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addGap(9, 9, 9)
                        .addComponent(txt_penerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addComponent(framegambarbuku, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(txt_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_rak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_stok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(imagepath)
                        .addComponent(tambahimage)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Main.add(Panel_add, "card2");

        add(Panel_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        Panel_Main.removeAll();
        Panel_Main.add(Panel_view);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        clearFields(); // Bersihkan field saat kembali ke tampilan view
        load_table(); // Muat ulang tabel setelah batal
        // Reset action listener for Btn_tambah1 if it was set to update
        for (java.awt.event.ActionListener al : Btn_simpan.getActionListeners()) {
            Btn_simpan.removeActionListener(al);
        }
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambah1ActionPerformed(evt);
            }
        });
        Btn_simpan.setText("Simpan"); // Reset button text to "Simpan"
    }//GEN-LAST:event_btn_batalActionPerformed

    private void Btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambahActionPerformed
        Panel_Main.removeAll();
        Panel_Main.add(Panel_add);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        clearFields(); // Bersihkan field saat masuk ke tampilan tambah
        // Ensure Btn_tambah1's action listener is set to save
        for (java.awt.event.ActionListener al : Btn_simpan.getActionListeners()) {
            Btn_simpan.removeActionListener(al);
        }
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambah1ActionPerformed(evt);
            }
        });
        Btn_simpan.setText("Simpan"); // Reset button text to "Simpan"
    }//GEN-LAST:event_Btn_tambahActionPerformed

    private void txt_idBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idBukuActionPerformed

    private void txt_judulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_judulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_judulActionPerformed

    private void imagepathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagepathActionPerformed
        // Panggil langsung kode JFileChooser di sini, atau panggil method terpisah jika ingin lebih rapi.
    
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home"))); // Atur direktori awal
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        fileChooser.addChoosableFileFilter(filter);

        int result = fileChooser.showOpenDialog(this); // Gunakan showOpenDialog untuk memilih file yang sudah ada
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Opsional: Tampilkan path file di JTextField jika Anda punya txt_imagePath
                // txt_imagePath.setText(selectedFile.getAbsolutePath());

            // Baca file gambar ke dalam byte array
                FileInputStream fis = new FileInputStream(selectedFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                }
                bookImageBytes = bos.toByteArray(); // Simpan data gambar ke variabel anggota
                
                        if (bookImageBytes != null) { // Pastikan ada data gambar
            ImageIcon imageIcon = new ImageIcon(bookImageBytes);
            // Penting: Skalakan gambar agar sesuai dengan ukuran JLabel Anda
            // Asumsi framegambarbuku adalah JLabel Anda
            Image image = imageIcon.getImage();

            // Pastikan lbl_bookImage (atau framegambarbuku) memiliki ukuran yang ditentukan
            // atau set preferred size untuk JLabel di GUI builder.
            // Jika tidak, gambar bisa terlalu besar/kecil.
            int labelWidth = framegambarbuku.getWidth();
            int labelHeight = framegambarbuku.getHeight();

            // Hindari pembagian nol jika lebar/tinggi label belum diinisialisasi
            if (labelWidth == 0 || labelHeight == 0) {
                // Setel ukuran default jika label belum dirender atau ukurannya 0
                labelWidth = 150; // Contoh lebar default
                labelHeight = 200; // Contoh tinggi default
                // Atau gunakan ukuran preferred size JLabel Anda
            }

            Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
            framegambarbuku.setIcon(new ImageIcon(scaledImage));
            framegambarbuku.setText(""); // Hapus teks "gambar buku" setelah gambar ditampilkan
        }
                
                fis.close();
                bos.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error membaca file gambar: " + e.getMessage(), "Error Gambar", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            bookImageBytes = null; // Reset bookImageBytes jika ada error
            // if (txt_imagePath != null) txt_imagePath.setText(""); // Bersihkan text field jika ada error
            }
        } else {
        // Jika pengguna membatalkan pemilihan file
        bookImageBytes = null; // Pastikan data gambar direset
        // if (txt_imagePath != null) txt_imagePath.setText(""); // Bersihkan text field jika ada
        }
    }//GEN-LAST:event_imagepathActionPerformed

    private void tambahimageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahimageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tambahimageActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
    }

    // Ambil Id_Buku dari kolom pertama (index 0)
    String idBukuToDelete = jTable1.getValueAt(selectedRow, 0).toString().trim(); // Tambahkan .trim() untuk keamanan
    // Ambil Judul_Buku dari kolom kedua (index 1) untuk pesan konfirmasi
    String judulBukuToConfirm = jTable1.getValueAt(selectedRow, 1).toString();

    int confirm = JOptionPane.showConfirmDialog(this,
                                                "Anda yakin ingin menghapus buku dengan ID: " + idBukuToDelete + " (" + judulBukuToConfirm + ")?",
                                                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try (Connection conn = koneksi.getKoneksi();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM tb_buku WHERE Id_Buku = ?")) { // Gunakan Id_Buku di WHERE clause

            pst.setString(1, idBukuToDelete);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil dihapus!");
                load_table(); // Muat ulang tabel setelah penghapusan
            } else {
                // Pesan ini bisa muncul jika buku sudah terhapus atau ID tidak ditemukan (misalnya, jika data di tabel tidak sinkron)
                JOptionPane.showMessageDialog(this, "Gagal menghapus data buku. Buku mungkin tidak ditemukan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            // Penanganan error foreign key constraint yang lebih spesifik
            if (e.getSQLState() != null && e.getSQLState().startsWith("23")) { // SQLSTATE '23' adalah Integrity Constraint Violation
                JOptionPane.showMessageDialog(this, "Gagal menghapus buku: Buku ini masih terkait dengan data lain (misal: di tabel peminjaman). Hapus data terkait terlebih dahulu.", "Database Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error saat menghapus data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_btn_hapusActionPerformed
    
    private void Btn_tambah1ActionPerformed(java.awt.event.ActionEvent evt) {
        String idBuku = txt_idBuku.getText(); 
        String judul = txt_judul.getText();
        String penulis = txt_penulis.getText();
        String penerbit = txt_penerbit.getText();
        String kategori = txt_kategori.getText();
        String tahunTerbitStr = txt_tahun.getText(); // Ganti nama variabel agar lebih jelas
        String rakBuku = txt_rak.getText(); // <-- TAMBAHKAN BARIS INI
        String stokBukuStr = txt_stok.getText();


        // Validasi input
    // Validasi placeholder. Jika masih teks placeholder, anggap kosong.
    if (idBuku.equals("ID Buku") || idBuku.isEmpty() ||
        judul.equals("Judul Buku") || judul.isEmpty() || // Sesuaikan dengan placeholder Anda
        penulis.equals("Penulis") || penulis.isEmpty() ||
        penerbit.equals("Penerbit") || penerbit.isEmpty() ||
        kategori.equals("Kategori") || kategori.isEmpty() ||
        tahunTerbitStr.equals("Tahun Terbit") || tahunTerbitStr.isEmpty() ||
        rakBuku.equals("Rak Buku") || rakBuku.isEmpty() || // Sesuaikan dengan placeholder Anda
        stokBukuStr.equals("Stok Buku") || stokBukuStr.isEmpty() || bookImageBytes == null) {
        JOptionPane.showMessageDialog(this, "Semua kolom harus diisi dengan data yang valid, termasuk gambar buku!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int tahun;
    int stokBuku;
    
    try {
        tahun = Integer.parseInt(tahunTerbitStr);
        stokBuku = Integer.parseInt(stokBukuStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Tahun dan Stok Buku harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Connection conn = null; // Menghapus karakter '/' yang membuat baris ini komentar
    PreparedStatement pst = null; // Untuk operasi INSERT
    PreparedStatement checkPst = null; // Untuk operasi SELECT (cek duplikasi)
    ResultSet checkRs = null; // Untuk hasil SELECT (cek duplikasi)

    try {
        conn = koneksi.getKoneksi(); // Mendapatkan koneksi baru

        // Cek Duplikasi
        // Sesuaikan nama kolom di sini agar sesuai dengan skema database Anda
        String checkSql = "SELECT COUNT(*) FROM tb_buku WHERE Judul_Buku = ? AND Pengarang_Buku = ? AND Id_Penerbit = ?";
        checkPst = conn.prepareStatement(checkSql);
        checkPst.setString(1, judul);
        checkPst.setString(2, penulis);
        checkPst.setString(3, penerbit);
        checkRs = checkPst.executeQuery();

        if (checkRs.next() && checkRs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "Buku dengan Judul, Penulis, dan Penerbit yang sama sudah ada.", "Input Duplikat", JOptionPane.WARNING_MESSAGE);
            // Penting: Tutup sumber daya yang sudah dibuka sebelum return
            if (checkRs != null) checkRs.close();
            if (checkPst != null) checkPst.close();
             // Tutup koneksi di sini juga
            return;
        }
        // checkRs dan checkPst tidak perlu ditutup di sini karena akan ditutup di finally
        

        // Insert Data Baru
        // Sesuaikan nama kolom di sini agar sesuai dengan skema database Anda
        String sql = "INSERT INTO tb_buku (Id_Buku, Judul_Buku, Pengarang_Buku, Id_Penerbit, Id_Kategori, Tahun_Terbit, Rak_Buku, Stok_Buku, `Gambar buku`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, idBuku);
        pst.setString(2, judul);
        pst.setString(3, penulis);
        pst.setString(4, penerbit);
        pst.setString(5, kategori);
        pst.setInt(6, tahun); // Gunakan variabel 'tahun' yang sudah di-parse
        pst.setString(7, rakBuku); 
        pst.setInt(8, stokBuku); // Gunakan variabel 'stokBuku' yang sudah di-parse
        pst.setBytes(9, bookImageBytes);

        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Data buku berhasil ditambahkan!");
            // Asumsi Anda memiliki method untuk update UI, contoh:
            // cardLayout.show(Panel_Main, "viewPanel"); // Jika Anda menggunakan CardLayout
            clearFields(); // Panggil method untuk membersihkan input fields
            load_table(); // Muat ulang data di tabel setelah penambahan
            
            Panel_Main.removeAll();
            Panel_Main.add(Panel_view);
            Panel_Main.repaint();
            Panel_Main.revalidate();

            // Reset action listener for Btn_simpan if it was set to update
            // (Ini sudah ada di btn_batal dan Btn_tambah, tapi pastikan juga di sini setelah sukses tambah)
             for (java.awt.event.ActionListener al : Btn_simpan.getActionListeners()) {
                Btn_simpan.removeActionListener(al);
            }
            Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    Btn_tambah1ActionPerformed(evt);
                }
            });
            Btn_simpan.setText("Simpan"); // Reset button text to "Simpan"
            
            
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data buku.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error saat menyimpan data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try {
            // Pastikan semua ResultSet dan PreparedStatement ditutup
            if (checkRs != null) checkRs.close();
            if (checkPst != null) checkPst.close();
            // pst akan null jika ada exception sebelum diinisialisasi, jadi cek null
            if (pst != null) pst.close();
            if (conn != null) conn.close();
            // Penting: Tutup koneksi terakhir
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

    private void load_table() {
        tableModel.setRowCount(0);
        try (Connection conn = koneksi.getKoneksi()) {
            if (conn != null) {
                String sql = "SELECT Id_Buku, Judul_Buku, Pengarang_Buku, Id_Penerbit, Id_Kategori, Tahun_Terbit, Rak_Buku, Stok_Buku, `Gambar buku` FROM tb_buku";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                     byte[] gambarBytes = rs.getBytes("Gambar buku");
                    
                    tableModel.addRow(new Object[]{
                        rs.getString("Id_Buku"),
                        rs.getString("Judul_Buku"),
                        rs.getString("Pengarang_Buku"),
                        rs.getString("Id_Penerbit"),
                        rs.getString("Id_Kategori"),
                        rs.getString("Tahun_Terbit"),
                        rs.getString("Rak_Buku"),
                        rs.getInt("Stok_Buku"),
                        gambarBytes
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memuat data tabel: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void clearFields() {
        txt_idBuku.setText("");
        txt_judul.setText("");
        txt_penulis.setText("");
        txt_penerbit.setText("");
        txt_kategori.setText("");
        txt_tahun.setText("");
        txt_rak.setText("");
        txt_stok.setText("");
        bookImageBytes = null;
        
        
        if (framegambarbuku != null) {
        framegambarbuku.setIcon(null); // Hapus gambar dari JLabel
        framegambarbuku.setText("gambar buku"); // Kembalikan teks placeholder awal
    }
        
        
        setPlaceholder(txt_idBuku, "ID Buku"); 
        setPlaceholder(txt_judul, "Judul Buku");
        setPlaceholder(txt_penulis, "Penulis");
        setPlaceholder(txt_penerbit, "Penerbit");
        setPlaceholder(txt_kategori, "Kategori");
        setPlaceholder(txt_tahun, "Tahun Terbit");
        setPlaceholder(txt_rak, "Rak Buku");
        setPlaceholder(txt_stok, "Stok Buku");
        // lbl_gambarBuku.setIcon(null);        
    }
    
    private void filterTable(String keyword) {
        tableModel.setRowCount(0); // Kosongkan tabel sebelum memuat data baru
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getKoneksi();
            if (conn != null) {
                // Query untuk mencari buku berdasarkan beberapa kolom string
                // Pencarian dilakukan pada Id_Buku, Judul_Buku, Pengarang_Buku, Id_Penerbit,
                // Id_Kategori, Tahun_Terbit, dan Rak_Buku.
                String sql = "SELECT Id_Buku, Judul_Buku, Pengarang_Buku, Id_Penerbit, Id_Kategori, Tahun_Terbit, Rak_Buku, Stok_Buku, `Gambar buku` FROM tb_buku " +
                             "WHERE Id_Buku LIKE ? OR Judul_Buku LIKE ? OR Pengarang_Buku LIKE ? OR Id_Penerbit LIKE ? OR Id_Kategori LIKE ? OR Tahun_Terbit LIKE ? OR Rak_Buku LIKE ?";
                pst = conn.prepareStatement(sql);
                String searchKeyword = "%" + keyword + "%";
                // Set parameter untuk setiap LIKE clause (ada 7 kolom)
                pst.setString(1, searchKeyword); // Id_Buku
                pst.setString(2, searchKeyword); // Judul_Buku
                pst.setString(3, searchKeyword); // Pengarang_Buku
                pst.setString(4, searchKeyword); // Id_Penerbit
                pst.setString(5, searchKeyword); // Id_Kategori
                pst.setString(6, searchKeyword); // Tahun_Terbit
                pst.setString(7, searchKeyword); // Rak_Buku
                
                ResultSet rs_filtered = pst.executeQuery(); 

                while (rs_filtered.next()) {
                    byte[] gambarBytes = rs_filtered.getBytes("Gambar buku"); // Ambil data gambar
                    tableModel.addRow(new Object[]{
                        rs_filtered.getString("Id_Buku"),
                        rs_filtered.getString("Judul_Buku"),
                        rs_filtered.getString("Pengarang_Buku"),
                        rs_filtered.getString("Id_Penerbit"),
                        rs_filtered.getString("Id_Kategori"),
                        rs_filtered.getString("Tahun_Terbit"),
                        rs_filtered.getString("Rak_Buku"),
                        rs_filtered.getInt("Stok_Buku"),
                        gambarBytes
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat mencari data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close(); 
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
                                        

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ambil data dari baris yang dipilih
        String judul = jTable1.getValueAt(selectedRow, 0).toString();
        String penulis = jTable1.getValueAt(selectedRow, 1).toString();
        String penerbit = jTable1.getValueAt(selectedRow, 2).toString();
        String kategori = jTable1.getValueAt(selectedRow, 3).toString();
        String tahunTerbit = jTable1.getValueAt(selectedRow, 4).toString();
        String rakBuku = jTable1.getValueAt(selectedRow, 5).toString(); // Ambil Rak_Buku
        String stokBuku = jTable1.getValueAt(selectedRow, 6).toString(); // Pastikan indeks kolom sesuai

        // Tampilkan data di Panel_add untuk diedit
      
        txt_judul.setText(judul);
        txt_penulis.setText(penulis);
        txt_penerbit.setText(penerbit);
        txt_kategori.setText(kategori);
        txt_tahun.setText(tahunTerbit);
        txt_stok.setText(stokBuku);
        
        // Hapus placeholder text
        txt_judul.setForeground(new java.awt.Color(0, 0, 0));
        txt_penulis.setForeground(new java.awt.Color(0, 0, 0));
        txt_penerbit.setForeground(new java.awt.Color(0, 0, 0));
        txt_kategori.setForeground(new java.awt.Color(0, 0, 0));
        txt_tahun.setForeground(new java.awt.Color(0, 0, 0));
        txt_rak.setForeground(new java.awt.Color(0, 0, 0)); // Set warna rak
        txt_stok.setForeground(new java.awt.Color(0, 0, 0));


        // Pindah ke Panel_add
        Panel_Main.removeAll();
        Panel_Main.add(Panel_add);
        Panel_Main.repaint();
        Panel_Main.revalidate();
        
        // Ubah aksi tombol simpan untuk melakukan update
        // Hapus listener yang lama untuk menghindari duplikasi aksi
        for (java.awt.event.ActionListener al : Btn_simpan.getActionListeners()) {
            Btn_simpan.removeActionListener(al);
        }
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt, judul); // Pass original judul for WHERE clause
            }
        });
        Btn_simpan.setText("Update"); // Ubah teks tombol menjadi "Update"
    }                                        

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt, String originalJudul) {
        String idBukuBaru = txt_idBuku.getText(); // Jika Id Buku bisa diedit, ambil nilainya
        String judulBaru = txt_judul.getText(); // Gunakan txt_judul
        String penulisBaru = txt_penulis.getText();
        String penerbitBaru = txt_penerbit.getText();
        String kategoriBaru = txt_kategori.getText();
        String tahunTerbitBaru = txt_tahun.getText();
        String rakBukuBaru = txt_rak.getText(); // Ambil nilai Rak_Buku
        String stokBukuStrBaru = txt_stok.getText();

        if (idBukuBaru.equals("ID Buku") || idBukuBaru.isEmpty() ||
        judulBaru.equals("Judul Buku") || judulBaru.isEmpty() ||
        penulisBaru.equals("Penulis") || penulisBaru.isEmpty() ||
        penerbitBaru.equals("Penerbit") || penerbitBaru.isEmpty() ||
        kategoriBaru.equals("Kategori") || kategoriBaru.isEmpty() ||
        tahunTerbitBaru.equals("Tahun Terbit") || tahunTerbitBaru.isEmpty() ||
        rakBukuBaru.equals("Rak Buku") || rakBukuBaru.isEmpty() ||
        stokBukuStrBaru.equals("Stok Buku") || stokBukuStrBaru.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua kolom harus diisi dengan data yang valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }

        int stokBukuBaru;
        try {
            stokBukuBaru = Integer.parseInt(stokBukuStrBaru);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok Buku harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = koneksi.getKoneksi()) {
            if (conn != null) {
                String sql = "UPDATE tb_buku SET Judul_Buku = ?, Pengarang_Buku = ?, Id_Penerbit = ?, Id_Kategori = ?, Tahun_Terbit = ?, Rak_Buku = ?, Stok_Buku = ? WHERE Judul_Buku = ?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, judulBaru);
                pst.setString(2, penulisBaru);
                pst.setString(3, penerbitBaru);
                pst.setString(4, kategoriBaru);
                pst.setString(5, tahunTerbitBaru);
                pst.setInt(6, stokBukuBaru);
                pst.setString(7, originalJudul); // Menggunakan judul asli untuk klausa WHERE

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data buku berhasil diperbarui!");
                    clearFields();
                    load_table();
                    // Kembali ke Panel_view setelah update
                    Panel_Main.removeAll();
                    Panel_Main.add(Panel_view);
                    Panel_Main.repaint();
                    Panel_Main.revalidate();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal memperbarui data buku.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat memperbarui data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        // Reset action listener for Btn_tambah1 to its original save function
        // Hapus listener yang lama untuk menghindari duplikasi aksi
        for (java.awt.event.ActionListener al : Btn_simpan.getActionListeners()) {
            Btn_simpan.removeActionListener(al);
        }
        Btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambah1ActionPerformed(evt);
            }
        });
        Btn_simpan.setText("Simpan"); // Reset button text to "Simpan"
    

    }                                          
    
    
    
        
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_simpan;
    private javax.swing.JButton Btn_tambah;
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_add;
    private javax.swing.JPanel Panel_view;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_before;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_next;
    private javax.swing.JComboBox<String> cbx_data;
    private javax.swing.JLabel framegambarbuku;
    private javax.swing.JButton imagepath;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JButton tambahimage;
    private javax.swing.JTextField txt_CARI;
    private javax.swing.JTextField txt_idBuku;
    private javax.swing.JTextField txt_judul;
    private javax.swing.JTextField txt_kategori;
    private javax.swing.JTextField txt_penerbit;
    private javax.swing.JTextField txt_penulis;
    private javax.swing.JTextField txt_rak;
    private javax.swing.JTextField txt_stok;
    private javax.swing.JTextField txt_tahun;
    // End of variables declaration//GEN-END:variables
}
