/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package appperpus;
import appperpus.BlueprintAnggota; // <--- Import kelas BlueprintAnggota
import javax.swing.JOptionPane;
import java.time.format.DateTimeFormatter; // Untuk memformat tanggal
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;

/**
 *
 * @author mac
 */
public class Panel_profil extends javax.swing.JPanel {
     private BlueprintAnggota anggotaYangLogin; // <--- Variabel untuk menyimpan data anggota
     private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Format tanggal untuk tampilan

    /**
     * Creates new form Panel_dashboard
     */
    public Panel_profil(BlueprintAnggota anggota) {
       
        this.anggotaYangLogin = anggota; // Simpan objek yang diterima
        initComponents(); // Inisialisasi komponen UI Anda
        displayAnggotaData(); // Panggil metode untuk mengisi field
        setFieldsEditable(false); // Secara default, field tidak bisa diedit
        
    }
    
    
//    public Panel_profil() {
//        initComponents();
//        JOptionPane.showMessageDialog(this, "Panel Profil dibuka tanpa data anggota. Silakan login terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
//        // Mungkin nonaktifkan field atau tampilkan pesan error
//    }
    
    private void displayAnggotaData() {
        if (anggotaYangLogin != null) {
            txtID.setText(anggotaYangLogin.getIdAnggota()); // Sesuaikan nama JTextField Anda
            txtNama.setText(anggotaYangLogin.getNamaAnggota());
            txtEmail.setText(anggotaYangLogin.getEmail());
            txtNoTelp.setText(anggotaYangLogin.getNoTelepon());
            txtJenisKelamin.setText(anggotaYangLogin.getJenisKelamin());
            
            // Tampilkan Tanggal Bergabung
            if (anggotaYangLogin.getTanggalBergabung() != null) {
                txtTanggalBergabung.setText(anggotaYangLogin.getTanggalBergabung().format(dateFormatter));
            } else {
                txtTanggalBergabung.setText("-"); // Atau biarkan kosong jika null
            }
            
            txtUsername.setText(anggotaYangLogin.getUsername());
            txtPassword.setText("********"); // <--- PENTING: JANGAN TAMPILKAN PASSWORD ASLI!
            
        } else {
            // Jika objek anggotaYangLogin null, kosongkan field atau tampilkan error
            clearFields();
            JOptionPane.showMessageDialog(this, "Gagal memuat data profil. Objek anggota null.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Metode untuk mengosongkan semua field
    private void clearFields() {
        txtID.setText("");
        txtNama.setText("");
        txtEmail.setText("");
        txtNoTelp.setText("");
        txtJenisKelamin.setText("");
        txtTanggalBergabung.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }
    
    // Metode untuk mengatur apakah field bisa diedit atau tidak
    private void setFieldsEditable(boolean editable) {
        txtID.setEditable(editable);
        txtNama.setEditable(editable);
        txtEmail.setEditable(editable);
        txtNoTelp.setEditable(editable);
        txtJenisKelamin.setEditable(editable);
        txtTanggalBergabung.setEditable(editable);
        txtUsername.setEditable(editable);
        txtPassword.setEditable(editable);
        // Jika ada tombol edit/simpan, atur visibilitasnya di sini
        // btnEdit.setVisible(!editable);
        // btnSimpan.setVisible(editable);
    }
    
    // Pastikan Anda memiliki deklarasi JTextField di Panel_profil.java (dibuat oleh NetBeans GUI Builder)
    // Contoh:
    // private javax.swing.JTextField txtID;
    // private javax.swing.JTextField txtNama;
    // ... dst
    
    // Implementasi placeholder jika Anda menggunakannya (sesuaikan dengan kode Anda)
    private void setPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }

    // ... (metode initComponents() dan main method jika ada) ...
    // Pastikan main method di Panel_profil.java dikomentari atau dihapus saat testing dari Mainmenu_User
    /*
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Panel_profil().setVisible(true);
            }
        });
    }
    */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paneldasar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNoTelp = new javax.swing.JTextField();
        txtJenisKelamin = new javax.swing.JTextField();
        txtTanggalBergabung = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.CardLayout());

        paneldasar.setBackground(new java.awt.Color(255, 255, 255));
        paneldasar.setPreferredSize(new java.awt.Dimension(70, 50));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Profil Akun");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        jLabel2.setText("ID");

        jLabel3.setText("Nama");

        jLabel4.setText("Email");

        jLabel5.setText("No.Telp");

        jLabel6.setText("Jenis Kelamin");

        jLabel7.setText("Tanggal Bergabung");

        txtTanggalBergabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalBergabungActionPerformed(evt);
            }
        });

        jLabel9.setText("Username");

        jLabel10.setText("Password");

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setText("Edit");

        javax.swing.GroupLayout paneldasarLayout = new javax.swing.GroupLayout(paneldasar);
        paneldasar.setLayout(paneldasarLayout);
        paneldasarLayout.setHorizontalGroup(
            paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldasarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldasarLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(paneldasarLayout.createSequentialGroup()
                        .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(paneldasarLayout.createSequentialGroup()
                                .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                    .addComponent(txtID)
                                    .addComponent(txtEmail)
                                    .addComponent(txtNoTelp)
                                    .addComponent(txtJenisKelamin)
                                    .addComponent(txtTanggalBergabung)
                                    .addComponent(txtUsername)
                                    .addComponent(txtPassword)))
                            .addComponent(jLabel1)
                            .addComponent(jLabel10))
                        .addGap(181, 181, 181)
                        .addComponent(jButton1)
                        .addGap(31, 31, 31))))
        );
        paneldasarLayout.setVerticalGroup(
            paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldasarLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(65, 65, 65)
                .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(paneldasarLayout.createSequentialGroup()
                        .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTanggalBergabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(paneldasarLayout.createSequentialGroup()
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNoTelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtJenisKelamin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addGap(41, 41, 41)
                .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paneldasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        add(paneldasar, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txtTanggalBergabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalBergabungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalBergabungActionPerformed

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel paneldasar;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtJenisKelamin;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNoTelp;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtTanggalBergabung;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
