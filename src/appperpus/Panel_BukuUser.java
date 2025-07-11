
package appperpus;

import java.awt.Component;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.SwingConstants;

public class Panel_BukuUser extends javax.swing.JPanel {

     // Deklarasi JTable untuk pengguna
    private DefaultTableModel model; // Model untuk JTable

    public Panel_BukuUser() {
        initComponents(); // Dipanggil oleh NetBeans GUI builder
        setupTable(); // Mempersiapkan tabel
        loadDataBuku(); // Memuat data buku ke tabel
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
        jTableBukuUser = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_last = new javax.swing.JButton();
        btn_next = new javax.swing.JButton();
        cbx_data = new javax.swing.JComboBox<>();
        btn_before = new javax.swing.JButton();
        btn_first = new javax.swing.JButton();
        lb_halaman = new javax.swing.JLabel();

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
        jLabel2.setText(" Pinjam Buku");

        jTableBukuUser.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableBukuUser);

        jTextField1.setForeground(new java.awt.Color(204, 204, 204));
        jTextField1.setText("Cari");

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
                        .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE))
                .addGap(17, 17, 17))
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
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addGroup(Panel_viewLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
            .addGroup(Panel_viewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel_viewLayout.createSequentialGroup()
                    .addContainerGap(523, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        Panel_Main.add(Panel_view, "card2");

        add(Panel_Main, "card2");
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void setupTable() {
        model = new DefaultTableModel() {
            // Override isCellEditable untuk membuat tabel read-only
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Semua sel tidak dapat diedit
            }
        };

        // Menambahkan kolom ke model tabel sesuai dengan skema tb_buku
        model.addColumn("ID Buku");
        model.addColumn("Judul");
        model.addColumn("Pengarang");
        model.addColumn("Penerbit");
        model.addColumn("Kategori");
        model.addColumn("Tahun Terbit");
        model.addColumn("Rak Buku");
        model.addColumn("Stok");
        model.addColumn("Gambar"); // Kolom untuk menampilkan gambar

        jTableBukuUser.setModel(model); // Set model ke JTable

        // Set Cell Renderer untuk kolom "Gambar" (asumsi indeks kolom 8)
        // Pastikan indeks ini sesuai dengan urutan kolom yang ditambahkan di atas
        jTableBukuUser.getColumnModel().getColumn(8).setCellRenderer(new ImageRenderer());
        jTableBukuUser.setRowHeight(80); // Atur tinggi baris agar gambar tidak terpotong, sesuaikan kebutuhan
    }

    /**
     * Memuat data buku dari database tb_buku ke JTable.
     */
    private void loadDataBuku() {
        // Membersihkan data lama di tabel sebelum memuat yang baru
        model.setRowCount(0);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = appperpus.koneksi.getKoneksi(); // Mendapatkan koneksi dari kelas koneksi Anda
            if (conn != null) {
                // Query SQL untuk mengambil semua data dari tb_buku
                String sql = "SELECT Id_Buku, Judul_Buku, Pengarang_Buku, Id_Penerbit, Id_Kategori, Tahun_Terbit, Rak_Buku, Stok_Buku, `Gambar buku` FROM tb_buku";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    // Mengambil data dari setiap kolom
                    String idBuku = rs.getString("Id_Buku");
                    String judul = rs.getString("Judul_Buku");
                    String pengarang = rs.getString("Pengarang_Buku");
                    String tahunTerbit = rs.getString("Tahun_Terbit");
                    String idKategori = rs.getString("Id_Kategori");
                    String idPenerbit = rs.getString("Id_Penerbit");
                    String rakBuku = rs.getString("Rak_Buku");
                    int stokBuku = rs.getInt("Stok_Buku");
                    byte[] gambarBytes = rs.getBytes("Gambar buku"); // Mengambil data gambar dalam bentuk byte array

                    // Menambahkan baris ke model tabel
                    // Data gambar (byte[]) langsung ditambahkan ke model,
                    // ImageRenderer akan menanganinya untuk ditampilkan sebagai ImageIcon
                    model.addRow(new Object[]{
                        idBuku, judul, pengarang, tahunTerbit,
                        idKategori, idPenerbit, rakBuku, stokBuku,
                        gambarBytes // Menambahkan byte array gambar
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this, "Koneksi database tidak tersedia.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error memuat data buku: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Pastikan semua sumber daya ditutup
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close(); // Penting: tutup koneksi setelah selesai
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private class ImageRenderer extends JLabel implements TableCellRenderer {

        public ImageRenderer() {
            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER); // Pusatkan gambar/teks
            setVerticalAlignment(SwingConstants.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            // Atur background dan foreground saat baris dipilih
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
                        // Skalakan gambar agar sesuai dengan ukuran baris tabel
                        Image image = imageIcon.getImage();
                        
                        // Tinggi ideal untuk gambar di dalam sel (sesuai row height)
                        int desiredHeight = table.getRowHeight() - 5; // Beri sedikit padding
                        int desiredWidth = (int) ((double) image.getWidth(null) / image.getHeight(null) * desiredHeight);

                        // Batasi lebar maksimum agar tidak terlalu lebar
                        if (desiredWidth > 150) { // Misalnya, maksimal 150 piksel lebar
                            desiredWidth = 150;
                            desiredHeight = (int) ((double) image.getHeight(null) / image.getWidth(null) * desiredWidth);
                        }


                        Image scaledImage = image.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(scaledImage));
                        setText(""); // Hapus teks jika ada gambar
                    } catch (Exception e) {
                        setIcon(null);
                        setText("Error Gambar"); // Tampilkan teks error jika gambar gagal dimuat
                        e.printStackTrace();
                    }
                } else {
                    setIcon(null);
                    setText("Tidak Ada Gambar"); // Tampilkan teks jika tidak ada data gambar
                }
            } else {
                setIcon(null);
                setText("Tidak Ada Gambar"); // Tampilkan teks jika tipe data bukan byte[]
            }
            return this;
        }
    }



    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel_Main;
    private javax.swing.JPanel Panel_view;
    private javax.swing.JButton btn_before;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_next;
    private javax.swing.JComboBox<String> cbx_data;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBukuUser;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lb_halaman;
    // End of variables declaration//GEN-END:variables
}
