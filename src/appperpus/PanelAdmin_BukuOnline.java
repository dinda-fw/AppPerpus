/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package appperpus;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Image; // Diperlukan untuk ImageIcon
import javax.swing.ImageIcon; // Diperlukan untuk menampilkan gambar

/**
 *
 * @author mac
 */
public class PanelAdmin_BukuOnline extends javax.swing.JPanel {

    private DefaultTableModel tableModel;
    private boolean isEditMode = false;
    private String selectedJudulBukuForEdit = ""; // Menyimpan judul buku yang dipilih untuk diedit
//  private String uploadedFilePath = null;
    private byte[] uploadedFileBytes = null;
    private byte[] uploadedCoverImageBytes = null;

    public PanelAdmin_BukuOnline() {
        initComponents();
        setupTable();
        loadDataToTable();
        setupPlaceholder(txt_id, "Judul");
        setupPlaceholder(txt_namapenulis, "Pengarang");
        setupPlaceholder(txt_penerbit, "Penerbit");
        setupPlaceholder(txt_tahun, "Tahun Terbit");
        setupSearchField(jTextField1, "Cari");
        
        Btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_tambahActionPerformed(evt);
            }
        });
        
        
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        
        // Tambahkan action listener untuk tombol hapus
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        
        // Tambahkan action listener untuk tombol simpan
        Btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanActionPerformed(evt);
            }
        });
        
        jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterTable(jTextField1.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterTable(jTextField1.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterTable(jTextField1.getText());
            }
        });
        
        btn_uploadfoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btn_uploadfotoActionPerformed(e);
            }
        });

       
    }
    
    private void setupTable() {
        tableModel = new DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Judul Buku", "Pengarang Buku", "Penerbit Buku", "Tahun Terbit", "File PDF", "Gambar Cover"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hanya kolom "File PDF" (indeks 4) yang bisa diedit/diklik
                return column == 4; 
            }
            
             @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Memberi tahu tabel bahwa kolom 4 (File PDF) dan 5 (Gambar Cover) berisi byte[]
                if (columnIndex == 4 || columnIndex == 5) {
                    return byte[].class; 
                }
                return super.getColumnClass(columnIndex);
            }
            
        };
        jTable1.setModel(tableModel);

        // Mengatur Cell Renderer dan Cell Editor untuk kolom "File PDF" (indeks 4)
        jTable1.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("Lihat File"));
        // Meneruskan instance PanelAdmin_BukuOnline (this) agar ButtonEditor dapat memanggil openPdfFromBytes
        jTable1.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField(), "Lihat File", this)); 
        
        // Mengatur Cell Renderer untuk kolom "Gambar Cover" (indeks 5)
        // Pastikan menggunakan ImageRenderer kustom dari file terpisah jika ada
        jTable1.getColumnModel().getColumn(5).setCellRenderer(new appperpus.ImageRenderer()); // Menggunakan ImageRenderer eksternal

        jTable1.setRowHeight(80); 
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(120); // Atur lebar preferensi kolom PDF
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(120);
    }
    
    private void loadDataToTable() {
        tableModel.setRowCount(0); // Kosongkan tabel sebelum memuat data baru
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getKoneksi();
            if (conn != null) {
                // Mengambil kolom file_path (BLOB) dan gambar (BLOB)
                String sql = "SELECT Judul_Buku, Pengarang_Buku, Penerbit_buku, Tahun_Terbit, file_path, gambar FROM tb_buku_bacaan_online";
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                
                while (rs.next()) {
                    String judul = rs.getString("Judul_Buku");
                    String pengarang = rs.getString("Pengarang_Buku");
                    String penerbit = rs.getString("Penerbit_buku");
                    String tahunTerbit = rs.getString("Tahun_Terbit");
                    byte[] fileData = rs.getBytes("file_path"); // Data biner file PDF
                    byte[] imageData = rs.getBytes("gambar"); // Data biner gambar cover

                    // Tambahkan baris dengan data biner file dan gambar
                    tableModel.addRow(new Object[] {
                        judul, pengarang, penerbit, tahunTerbit, fileData, imageData
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
        // Perbarui tampilan tabel setelah data dimuat untuk memastikan renderer bekerja
        jTable1.repaint();
        jTable1.revalidate();
    }
    
    private void clearForm() {
        txt_id.setText("");
        txt_namapenulis.setText("");
        txt_penerbit.setText("");
        txt_tahun.setText("");
        lbl_filepath.setText("Tidak ada file terpilih."); // Reset teks JLabel
        uploadedFileBytes = null; // Reset data file PDF
        
        frame_gambar.setIcon(null); // Hapus gambar dari JLabel
        frame_gambar.setText("Preview Gambar"); // Kembalikan teks placeholder awal
        uploadedCoverImageBytes = null;
        
        setupPlaceholder(txt_id, "Judul");
        setupPlaceholder(txt_namapenulis, "Pengarang");
        setupPlaceholder(txt_penerbit, "Penerbit");
        setupPlaceholder(txt_tahun, "Tahun Terbit");
    }

   private void setupPlaceholder(javax.swing.JTextField textField, String placeholder) {
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
                // Gunakan SwingUtilities.invokeLater untuk menunda pemanggilan setText
                javax.swing.SwingUtilities.invokeLater(() -> {
                    textField.setText(placeholder);
                    textField.setForeground(new Color(204, 204, 204));
                    textField.setFont(textField.getFont().deriveFont(java.awt.Font.ITALIC));
                });
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
    
    private void filterTable(String keyword) {
        tableModel.setRowCount(0); // Kosongkan tabel
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = koneksi.getKoneksi();
            if (conn != null) {
                // Memfilter berdasarkan judul, pengarang, penerbit, atau tahun terbit
                String sql = "SELECT Judul_Buku, Pengarang_Buku, Penerbit_buku, Tahun_Terbit, file_path, gambar FROM tb_buku_bacaan_online " +
                             "WHERE Judul_Buku LIKE ? OR Pengarang_Buku LIKE ? OR Penerbit_buku LIKE ? OR Tahun_Terbit LIKE ?";
                pst = conn.prepareStatement(sql);
                for (int i = 1; i <= 4; i++) {
                    pst.setString(i, "%" + keyword + "%");
                }
                rs = pst.executeQuery();
                
                while (rs.next()) {
                    byte[] fileData = rs.getBytes("file_path");
                    byte[] imageData = rs.getBytes("gambar"); // Ambil BLOB gambar
                    tableModel.addRow(new Object[] {
                        rs.getString("Judul_Buku"),
                        rs.getString("Pengarang_Buku"),
                        rs.getString("Penerbit_buku"),
                        rs.getString("Tahun_Terbit"),
                        fileData,
                        imageData // Tambahkan BLOB gambar
                    });
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mencari data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
        // Perbarui tampilan tabel setelah data difilter untuk memastikan renderer bekerja
        jTable1.repaint();
        jTable1.revalidate();
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
        jTextField1 = new javax.swing.JTextField();
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
        Btn_Simpan = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_namapenulis = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_penerbit = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_tahun = new javax.swing.JTextField();
        lbl_filepath = new javax.swing.JLabel();
        btn_filepath = new javax.swing.JButton();
        frame_gambar = new javax.swing.JLabel();
        btn_uploadfoto = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        Panel_Main.setBackground(new java.awt.Color(255, 255, 255));
        Panel_Main.setPreferredSize(new java.awt.Dimension(70, 50));
        Panel_Main.setLayout(new java.awt.CardLayout());

        Panel_view.setBackground(new java.awt.Color(255, 255, 255));
        Panel_view.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Daftar Data Buku Bacaan Online");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel2.setText("Master Data > Buku Online");

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

        jTextField1.setForeground(new java.awt.Color(204, 204, 204));
        jTextField1.setText("Cari");

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
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
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
                        .addComponent(btn_edit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(90, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(89, Short.MAX_VALUE)))
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
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(329, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        Panel_Main.add(Panel_view, "viewCard");

        Panel_add.setBackground(new java.awt.Color(255, 255, 255));
        Panel_add.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setText("Tambah Daftar Buku");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        jLabel4.setText("Master Data > Tambah Buku Online");

        Btn_Simpan.setBackground(new java.awt.Color(153, 102, 0));
        Btn_Simpan.setForeground(new java.awt.Color(255, 255, 255));
        Btn_Simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save_white.png"))); // NOI18N
        Btn_Simpan.setText("Simpan");
        Btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SimpanActionPerformed(evt);
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

        txt_id.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_id.setForeground(new java.awt.Color(204, 204, 204));
        txt_id.setText("Judul");
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel7.setText("Judul");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel8.setText("Penulis");

        txt_namapenulis.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_namapenulis.setForeground(new java.awt.Color(204, 204, 204));
        txt_namapenulis.setText("Penulis");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel9.setText("Penerbit");

        txt_penerbit.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_penerbit.setForeground(new java.awt.Color(204, 204, 204));
        txt_penerbit.setText("Penerbit");

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel13.setText("Tahun");

        txt_tahun.setFont(new java.awt.Font("SansSerif", 2, 13)); // NOI18N
        txt_tahun.setForeground(new java.awt.Color(204, 204, 204));
        txt_tahun.setText("Tahun");

        lbl_filepath.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        lbl_filepath.setText("Upload File");

        btn_filepath.setBackground(new java.awt.Color(204, 204, 204));
        btn_filepath.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add_white.png"))); // NOI18N
        btn_filepath.setText("Upload File");
        btn_filepath.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_filepathActionPerformed(evt);
            }
        });

        frame_gambar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        frame_gambar.setFocusable(false);

        btn_uploadfoto.setBackground(new java.awt.Color(204, 204, 204));
        btn_uploadfoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add_white.png"))); // NOI18N
        btn_uploadfoto.setText("Upload foto");
        btn_uploadfoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_uploadfotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel_addLayout = new javax.swing.GroupLayout(Panel_add);
        Panel_add.setLayout(Panel_addLayout);
        Panel_addLayout.setHorizontalGroup(
            Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_addLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(11, 11, 11))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_penerbit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                            .addComponent(txt_namapenulis, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_id, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_tahun, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                            .addComponent(lbl_filepath, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel_addLayout.createSequentialGroup()
                                .addComponent(Btn_Simpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal))
                            .addComponent(btn_filepath, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(115, 115, 115)
                        .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(frame_gambar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_uploadfoto, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                        .addGap(72, 72, 72)))
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
                    .addComponent(Btn_Simpan)
                    .addComponent(btn_batal))
                .addGap(45, 45, 45)
                .addGroup(Panel_addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(frame_gambar, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(btn_uploadfoto))
                    .addGroup(Panel_addLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_namapenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_penerbit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tahun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_filepath)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_filepath, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Panel_Main.add(Panel_add, "addCard\n");

        add(Panel_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        CardLayout cardLayout = (CardLayout) Panel_Main.getLayout();
        cardLayout.show(Panel_Main, "viewCard"); // Mengarah ke Panel_view // Kembali ke Panel_view (asumsi "card1" adalah nama kartu untuk Panel_view)
        clearForm(); // Bersihkan form
        Btn_Simpan.setText("Simpan"); // Kembalikan teks tombol ke "Simpan"
        jLabel3.setText("Tambah Daftar Buku"); // Kembalikan judul panel form
        isEditMode = false; // Reset status edit
        loadDataToTable(); // Muat ulang data ke tabel
    }//GEN-LAST:event_btn_batalActionPerformed

    private void Btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_tambahActionPerformed
        isEditMode = false;
        clearForm();
        Btn_Simpan.setText("Simpan");
        jLabel3.setText("Tambah Daftar Buku");
        CardLayout cardLayout = (CardLayout) Panel_Main.getLayout();
        cardLayout.show(Panel_Main, "addCard\n"); // Mengarah ke Panel_add // Menampilkan Panel_add

    }//GEN-LAST:event_Btn_tambahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String judulBuku = tableModel.getValueAt(selectedRow, 0).toString(); // Ambil judul buku dari kolom pertama

                Connection conn = null;
                PreparedStatement pst = null;
                try {
                    conn = koneksi.getKoneksi();
                    if (conn != null) {
                        String sql = "DELETE FROM tb_buku_bacaan_online WHERE Judul_Buku = ?";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, judulBuku);

                        int rowsAffected = pst.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                            loadDataToTable(); // Muat ulang data setelah penghapusan
                        } else {
                            JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error menghapus data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                } finally {
                    try {
                        if (pst != null) pst.close();
                        if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            isEditMode = true;
            Btn_Simpan.setText("Update");
            jLabel3.setText("Edit Data Buku");

            // Ambil data dari baris yang dipilih
            selectedJudulBukuForEdit = tableModel.getValueAt(selectedRow, 0).toString(); // Judul buku saat ini
            String pengarang = tableModel.getValueAt(selectedRow, 1).toString();
            String penerbit = tableModel.getValueAt(selectedRow, 2).toString();
            String tahun = tableModel.getValueAt(selectedRow, 3).toString();
            uploadedFileBytes = (byte[]) tableModel.getValueAt(selectedRow, 4); // Ambil data biner file
            uploadedCoverImageBytes = (byte[]) tableModel.getValueAt(selectedRow, 5);
            
            // Isi form dengan data yang dipilih
            txt_id.setText(selectedJudulBukuForEdit);
            txt_id.setForeground(Color.BLACK);
            txt_id.setFont(txt_id.getFont().deriveFont(java.awt.Font.PLAIN));

            txt_namapenulis.setText(pengarang);
            txt_namapenulis.setForeground(Color.BLACK);
            txt_namapenulis.setFont(txt_namapenulis.getFont().deriveFont(java.awt.Font.PLAIN));

            txt_penerbit.setText(penerbit);
            txt_penerbit.setForeground(Color.BLACK);
            txt_penerbit.setFont(txt_penerbit.getFont().deriveFont(java.awt.Font.PLAIN));

            txt_tahun.setText(tahun);
            txt_tahun.setForeground(Color.BLACK);
            txt_tahun.setFont(txt_tahun.getFont().deriveFont(java.awt.Font.PLAIN));
            
            // Perbarui label status file path jika file ada
            if (uploadedFileBytes != null && uploadedFileBytes.length > 0) {
                lbl_filepath.setText("File PDF tersimpan.");
            } else {
                lbl_filepath.setText("Tidak ada file tersimpan.");
            }
            
            if (uploadedCoverImageBytes != null && uploadedCoverImageBytes.length > 0) {
                ImageIcon imageIcon = new ImageIcon(uploadedCoverImageBytes);
                Image image = imageIcon.getImage();
                // Skala gambar agar pas di JLabel
                int labelWidth = frame_gambar.getWidth();
                int labelHeight = frame_gambar.getHeight();

                if (labelWidth <= 0 || labelHeight <= 0) {
                    labelWidth = 200; // Ukuran default jika belum di-render
                    labelHeight = 250; 
                }
                Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                frame_gambar.setIcon(new ImageIcon(scaledImage));
                frame_gambar.setText(""); // Hapus teks "Preview Gambar"
            } else {
                frame_gambar.setIcon(null);
                frame_gambar.setText("Preview Gambar");
            }
            
            
            CardLayout cardLayout = (CardLayout) Panel_Main.getLayout();
            cardLayout.show(Panel_Main, "\n"); // Mengarah ke Panel_add // Menampilkan Panel_add
        } else {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btn_editActionPerformed

    private void Btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SimpanActionPerformed
        String judul = txt_id.getText().trim();
        String pengarang = txt_namapenulis.getText().trim();
        String penerbit = txt_penerbit.getText().trim();
        String tahun = txt_tahun.getText().trim();
        
        // Validasi input: pastikan kolom teks tidak kosong dan bukan placeholder
        if (judul.isEmpty() || pengarang.isEmpty() || penerbit.isEmpty() || tahun.isEmpty() ||
            judul.equals("Judul") || pengarang.equals("Pengarang") || penerbit.equals("Penerbit") || tahun.equals("Tahun Terbit")) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi dengan data valid!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Validasi file PDF: untuk operasi TAMBAH, file PDF wajib ada.
        // Untuk operasi UPDATE, file PDF boleh kosong jika tidak ada perubahan file.
        if (!isEditMode && (uploadedFileBytes == null || uploadedFileBytes.length == 0)) {
            JOptionPane.showMessageDialog(this, "Mohon pilih file buku untuk diunggah!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!isEditMode && (uploadedCoverImageBytes == null || uploadedCoverImageBytes.length == 0)) {
            JOptionPane.showMessageDialog(this, "Mohon pilih gambar cover buku untuk diunggah!", "Validasi Input", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        Connection conn = null;
        PreparedStatement pst = null;
        try {
            conn = koneksi.getKoneksi();
            if (conn != null) {
                String sql;
                if (isEditMode) {
                    // Mode Update
                    sql = "UPDATE tb_buku_bacaan_online SET Judul_Buku = ?, Pengarang_Buku = ?, Penerbit_buku = ?, Tahun_Terbit = ?";
                    if (uploadedFileBytes != null && uploadedFileBytes.length > 0) { // Hanya update file_path jika ada file baru
                        sql += ", file_path = ?";
                    }
                    
                    if (uploadedCoverImageBytes != null && uploadedCoverImageBytes.length > 0) { // Hanya update gambar jika ada gambar baru
                        sql += ", gambar = ?";
                    }
                    
                    sql += " WHERE Judul_Buku = ?"; // Kondisi WHERE berdasarkan judul buku yang asli
                    
                    pst = conn.prepareStatement(sql);
                    int paramIndex = 1;
                    pst.setString(paramIndex++, judul);
                    pst.setString(paramIndex++, pengarang);
                    pst.setString(paramIndex++, penerbit);
                    pst.setString(paramIndex++, tahun);
                    if (uploadedFileBytes != null && uploadedFileBytes.length > 0) {
                        pst.setBytes(paramIndex++, uploadedFileBytes); // Set data biner file
                    }
                    
                    if (uploadedCoverImageBytes != null && uploadedCoverImageBytes.length > 0) {
                        pst.setBytes(paramIndex++, uploadedCoverImageBytes); // Set data biner gambar cover
                    }
                    
                    pst.setString(paramIndex++, selectedJudulBukuForEdit); // Judul asli untuk WHERE
                    
                } else {
                    // Mode Insert
                    sql = "INSERT INTO tb_buku_bacaan_online (Judul_Buku, Pengarang_Buku, Penerbit_buku, Tahun_Terbit, file_path, gambar) VALUES (?, ?, ?, ?, ?, ?)";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, judul);
                    pst.setString(2, pengarang);
                    pst.setString(3, penerbit);
                    pst.setString(4, tahun);
                    pst.setBytes(5, uploadedFileBytes); // Set data biner file
                    pst.setBytes(6, uploadedCoverImageBytes);
                }

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    btn_batalActionPerformed(evt); // Kembali ke tampilan utama dan muat ulang data
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menyimpan data.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error menyimpan data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_Btn_SimpanActionPerformed

    private void btn_filepathActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_filepathActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Pilih File Buku (PDF)");

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File selectedFile = fileChooser.getSelectedFile();
            if (!selectedFile.getName().toLowerCase().endsWith(".pdf")) {
                JOptionPane.showMessageDialog(this, "Pilih hanya file PDF (.pdf).", "Jenis File Tidak Valid", JOptionPane.WARNING_MESSAGE);
                uploadedFileBytes = null; // Clear bytes if invalid
                lbl_filepath.setText("Jenis file tidak valid.");
                return;
            }

            try (FileInputStream fis = new FileInputStream(selectedFile);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                uploadedFileBytes = bos.toByteArray(); // Simpan data biner file
                lbl_filepath.setText("File terpilih: " + selectedFile.getName()); // Tampilkan NAMA FILE di JLabel
                // Dihapus: JOptionPane.showMessageDialog(this, "File PDF '" + selectedFile.getName() + "' berhasil dipilih.", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                lbl_filepath.setText("Error membaca file.");
                uploadedFileBytes = null;
                JOptionPane.showMessageDialog(this, "Gagal membaca file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            // Jika pengguna membatalkan, hanya reset jika TIDAK dalam mode edit DAN data sebelumnya kosong
            if (!isEditMode && (uploadedFileBytes == null || uploadedFileBytes.length == 0)) { 
                 uploadedFileBytes = null;
                 lbl_filepath.setText("Tidak ada file terpilih.");
            }
        }
    }//GEN-LAST:event_btn_filepathActionPerformed

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void btn_uploadfotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_uploadfotoActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Gambar Cover Buku");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(selectedFile);
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                
                byte[] buf = new byte[1024];
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
                uploadedCoverImageBytes = bos.toByteArray();
                
                ImageIcon imageIcon = new ImageIcon(uploadedCoverImageBytes);
                Image image = imageIcon.getImage();
                
                int labelWidth = frame_gambar.getWidth();
                int labelHeight = frame_gambar.getHeight();

                if (labelWidth <= 0 || labelHeight <= 0) {
                    labelWidth = 200; // Ukuran default jika belum di-render
                    labelHeight = 250; 
                }
                
                Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
                frame_gambar.setIcon(new ImageIcon(scaledImage));
                frame_gambar.setText(""); // Hapus teks "Preview Gambar"
                // Dihapus: JOptionPane.showMessageDialog(this, "Gambar cover '" + selectedFile.getName() + "' berhasil dipilih.", "Info", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException ex) {
                uploadedCoverImageBytes = null;
                frame_gambar.setIcon(null);
                frame_gambar.setText("Error Memuat Gambar");
                JOptionPane.showMessageDialog(this, "Gagal membaca file gambar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            // Jika pengguna membatalkan, hanya reset gambar jika TIDAK dalam mode edit DAN data sebelumnya kosong
            if (!isEditMode && (uploadedCoverImageBytes == null || uploadedCoverImageBytes.length == 0)) {
                uploadedCoverImageBytes = null;
                frame_gambar.setIcon(null);
                frame_gambar.setText("Preview Gambar");
            }
        }
    }//GEN-LAST:event_btn_uploadfotoActionPerformed
    
    private void openPdfFromBytes(byte[] fileBytes, String judulBuku) {
        if (fileBytes == null || fileBytes.length == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada file PDF yang tersimpan untuk buku ini.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Buat direktori sementara untuk menyimpan file PDF
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "PerpustakaanOnlineAdminTemp");
        if (!tempDir.exists()) {
            tempDir.mkdirs();
        }
        
        // Buat nama file yang aman dan unik
        String safeFileName = judulBuku.replaceAll("[^a-zA-Z0-9.-]", "_"); 
        File tempFile = new File(tempDir, safeFileName + "_" + UUID.randomUUID().toString().substring(0, 8) + ".pdf"); 

        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(fileBytes);
            fos.flush();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(tempFile);
                JOptionPane.showMessageDialog(this, "Membuka file: " + tempFile.getName(), "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Desktop API tidak didukung. Tidak dapat membuka file PDF secara otomatis.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuka file PDF: " + e.getMessage(), "Error I/O", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    
    private class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() {
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);
            // Teks "Tidak Ada Gambar" akan diatur di getTableCellRendererComponent
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }

            if (value instanceof byte[]) {
                byte[] imageData = (byte[]) value;
                if (imageData != null && imageData.length > 0) {
                    try {
                        ImageIcon imageIcon = new ImageIcon(imageData);
                        Image image = imageIcon.getImage();
                        
                        // Perhitungan skala yang lebih baik
                        int originalWidth = image.getWidth(null);
                        int originalHeight = image.getHeight(null);
                        
                        int desiredHeight = table.getRowHeight() - 5; // Sedikit lebih kecil dari tinggi baris tabel
                        int desiredWidth = (int) ((double) originalWidth / originalHeight * desiredHeight);

                        // Batasi lebar maksimum agar gambar tidak terlalu lebar
                        int maxWidth = table.getColumnModel().getColumn(column).getWidth();
                        if (desiredWidth > maxWidth) { // Jika lebar hasil skala melebihi lebar kolom
                            desiredWidth = maxWidth; // Sesuaikan lebar agar pas di kolom
                            desiredHeight = (int) ((double) originalHeight / originalWidth * desiredWidth); // Sesuaikan tinggi
                        }

                        // Pastikan tinggi juga tidak melebihi batas baris tabel
                        if (desiredHeight > table.getRowHeight()) {
                             desiredHeight = table.getRowHeight() - 5; // Re-adjust if it's still too tall
                             desiredWidth = (int) ((double) originalWidth / originalHeight * desiredHeight);
                        }

                        Image scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(scaledImage));
                        setText(""); // Pastikan teks dihilangkan saat gambar berhasil ditampilkan
                    } catch (Exception e) {
                        setIcon(null);
                        setText("Error Gambar"); 
                        e.printStackTrace();
                    }
                } else {
                    setIcon(null);
                    setText("Tidak Ada Gambar"); // Jika data gambar kosong/null
                }
            } else {
                setIcon(null);
                setText("Tidak Ada Gambar"); // Jika value bukan byte[]
            }
            return this;
        }
    }
    
    
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setOpaque(true);
            setText(text);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setText("Lihat File"); 
            return this;
        }
    }
    
    private class ButtonEditor extends JPanel implements TableCellEditor {
        private JButton button;
        private byte[] currentFileBytes;
        private String currentJudulBuku;
        private PanelAdmin_BukuOnline parentPanel; // Referensi ke panel admin induk

        public ButtonEditor(JTextField tf, String buttonText, PanelAdmin_BukuOnline parent) {
            super(new java.awt.BorderLayout());
            this.parentPanel = parent;

            button = new JButton(buttonText);
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped(); 
                    // Panggil metode openPdfFromBytes dari panel induk
                    parentPanel.openPdfFromBytes(currentFileBytes, currentJudulBuku);
                }
            });
            add(button);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentJudulBuku = (String) table.getModel().getValueAt(row, 0); // Asumsi Judul Buku di kolom indeks 0
            currentFileBytes = (byte[]) value; // Value dari sel ini adalah data biner file_path

            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            return this;
        }

        @Override
        public Object getCellEditorValue() {
            return currentFileBytes;
        }

        @Override
        public boolean isCellEditable(java.util.EventObject anEvent) {
            return true;
        }

        @Override
        public boolean shouldSelectCell(java.util.EventObject anEvent) {
            return true;
        }

        @Override
        public boolean stopCellEditing() {
            fireEditingStopped();
            return true;
        }

        @Override
        public void cancelCellEditing() {
            fireEditingCanceled();
        }

        private java.util.Vector<javax.swing.event.CellEditorListener> listeners = new java.util.Vector<>();

        @Override
        public void addCellEditorListener(javax.swing.event.CellEditorListener l) {
            listeners.addElement(l);
        }

        @Override
        public void removeCellEditorListener(javax.swing.event.CellEditorListener l) {
            listeners.removeElement(l);
        }

        protected void fireEditingStopped() {
            for (int i = listeners.size() - 1; i >= 0; i--) {
                listeners.elementAt(i).editingStopped(new javax.swing.event.ChangeEvent(this));
            }
        }

        protected void fireEditingCanceled() {
            for (int i = listeners.size() - 1; i >= 0; i--) {
                listeners.elementAt(i).editingCanceled(new javax.swing.event.ChangeEvent(this));
            }
        }
    }


    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Simpan;
    private javax.swing.JButton Btn_tambah;
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_add;
    private javax.swing.JPanel Panel_view;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_before;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_filepath;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_uploadfoto;
    private javax.swing.JComboBox<String> cbx_data;
    private javax.swing.JLabel frame_gambar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lb_halaman;
    private javax.swing.JLabel lbl_filepath;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_namapenulis;
    private javax.swing.JTextField txt_penerbit;
    private javax.swing.JTextField txt_tahun;
    // End of variables declaration//GEN-END:variables
}
