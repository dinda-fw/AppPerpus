package appperpus;

import java.awt.Color;
import java.awt.event.FocusAdapter; // Ditambahkan untuk FocusListener
import java.awt.event.FocusEvent;   // Ditambahkan untuk FocusListener
import javax.swing.JTextField;
import appperpus.BlueprintAnggota; // Impor kelas Anggota yang baru dibuat
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId; // Untuk konversi Date ke LocalDate
import javax.swing.JOptionPane;
import java.time.LocalDate; // Import ini juga

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();

        setPlaceholder(jTextField1, "Masukkan Username Anda");
        // Menambahkan placeholder untuk jPasswordField1 (Password)
        setPlaceholder(jPasswordField1, "Masukkan Password Anda");

        
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_login.doClick();
            }
        });

        // Enter pada kolom password juga akan memicu login
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_login.doClick();
            }
        });
    }

  private void setPlaceholder(javax.swing.JPasswordField passwordField, String placeholder) {
        passwordField.setText(placeholder);
        passwordField.setForeground(new java.awt.Color(204, 204, 204));
        passwordField.setEchoChar((char) 0); // Nonaktifkan echo char saat placeholder aktif

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                // Hanya hapus placeholder jika teksnya sama dengan placeholder dan warnanya abu-abu
                if (new String(passwordField.getPassword()).equals(placeholder) && passwordField.getForeground().equals(new java.awt.Color(204, 204, 204))) {
                    passwordField.setText("");
                    passwordField.setForeground(new java.awt.Color(0, 0, 0)); // Ubah warna ke hitam
                    passwordField.setEchoChar('\u2022'); // Atur echo char saat fokus didapat (titik hitam)
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText(placeholder);
                    passwordField.setForeground(new java.awt.Color(204, 204, 204));
                    passwordField.setEchoChar((char) 0); // Nonaktifkan echo char jika kosong
                }
            }
        });
    }


    private void setPlaceholder(javax.swing.JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(new java.awt.Color(204, 204, 204)); // Warna abu-abu untuk placeholder
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                if (textField.getText().equals(placeholder) && textField.getForeground().equals(new java.awt.Color(204, 204, 204))) {
                    textField.setText("");
                    textField.setForeground(new java.awt.Color(0, 0, 0)); // Ubah warna ke hitam
                }
                // Jika ini JPasswordField, atur echo char saat fokus
                if (textField instanceof javax.swing.JPasswordField) {
                    ((javax.swing.JPasswordField) textField).setEchoChar('\u2022'); // Titik hitam untuk password
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(new java.awt.Color(204, 204, 204));
                    // Jika ini JPasswordField, kembalikan ke 0 jika kosong
                    if (textField instanceof javax.swing.JPasswordField) {
                        ((javax.swing.JPasswordField) textField).setEchoChar((char)0); // Nonaktifkan echo char
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        btn_signup = new javax.swing.JButton();
        button_login = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Username");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 240, -1, -1));

        jTextField1.setBackground(new java.awt.Color(204, 204, 204));
        jTextField1.setText("Username");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 270, 220, 30));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Password");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, -1, -1));

        jPasswordField1.setBackground(new java.awt.Color(204, 204, 204));
        jPasswordField1.setText("jPasswordField1");
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 350, 220, 30));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Belum punya akun ?");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 390, -1, -1));

        btn_signup.setBackground(new java.awt.Color(0, 0, 0));
        btn_signup.setForeground(new java.awt.Color(255, 204, 0));
        btn_signup.setText("Daftar disini");
        btn_signup.setBorder(null);
        btn_signup.setBorderPainted(false);
        btn_signup.setContentAreaFilled(false);
        btn_signup.setFocusPainted(false);
        btn_signup.setMaximumSize(new java.awt.Dimension(10, 15));
        btn_signup.setPreferredSize(new java.awt.Dimension(10, 5));
        btn_signup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_signupMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_signupMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_signupMouseExited(evt);
            }
        });
        btn_signup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_signupActionPerformed(evt);
            }
        });
        getContentPane().add(btn_signup, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 390, 130, 20));

        button_login.setBackground(new java.awt.Color(0, 0, 0));
        button_login.setForeground(new java.awt.Color(242, 242, 242));
        button_login.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/button login.png"))); // NOI18N
        button_login.setBorder(null);
        button_login.setBorderPainted(false);
        button_login.setContentAreaFilled(false);
        button_login.setFocusPainted(false);
        button_login.setMaximumSize(new java.awt.Dimension(10, 15));
        button_login.setPreferredSize(new java.awt.Dimension(10, 5));
        button_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_loginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button_loginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button_loginMouseExited(evt);
            }
        });
        button_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_loginActionPerformed(evt);
            }
        });
        getContentPane().add(button_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, 220, 50));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/BACKGROUND.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_loginActionPerformed
        String usernameInput = jTextField1.getText();
        String passwordInput = new String(jPasswordField1.getPassword());

    // Membersihkan placeholder jika masih ada
    if (usernameInput.equals("Masukkan Username Anda")) {
        usernameInput = "";
    }
    if (passwordInput.equals("Masukkan Password Anda")) { // Hati-hati jika placeholder jPasswordField diatur tanpa echo char
        passwordInput = "";
    }

    // Validasi input kosong (opsional tapi disarankan)
    if (usernameInput.isEmpty() || passwordInput.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Username dan Password tidak boleh kosong!", "Login Gagal", JOptionPane.WARNING_MESSAGE);
        return; // Hentikan eksekusi jika ada yang kosong
    }

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        conn = koneksi.getKoneksi(); // Pastikan kelas 'koneksi' Anda sudah benar untuk mendapatkan Connection
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Gagal terhubung ke database. Cek koneksi Anda.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query untuk memeriksa username dan password
        // Gunakan parameter (?) untuk mencegah SQL Injection
        String sql = "SELECT Id_Anggota, Nama_Anggota, Kelas, Username, Password, Jenis_Kelamin, " +
                         "Tempat_dan_Tanggal_Lahir, Tahun_Angkatan, Email, Tanggal_Bergabung, No_Telepon " +
                         "FROM anggota WHERE Username = ? AND Password = ?";
        pst = conn.prepareStatement(sql);
        pst.setString(1, usernameInput);
        pst.setString(2, passwordInput); // Catatan: Di aplikasi nyata, Anda harus menghash password!

        rs = pst.executeQuery();

        if (rs.next()) {
            // Jika ada baris yang ditemukan, berarti username dan password cocok
            String userKelas = rs.getString("Kelas");
            String retrievedUsername = rs.getString("Username"); // Ambil username dari database
            
            
            JOptionPane.showMessageDialog(this, "Login Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

                // Buat objek Anggota dan isi dengan data dari ResultSet
                BlueprintAnggota loggedInAnggota = new BlueprintAnggota();
                loggedInAnggota.setIdAnggota(rs.getString("Id_Anggota"));
                loggedInAnggota.setNamaAnggota(rs.getString("Nama_Anggota"));
                loggedInAnggota.setKelas(rs.getString("Kelas"));
                loggedInAnggota.setUsername(rs.getString("Username"));
                //loggedInAnggota.setPassword(rs.getString("Password")); // Tidak perlu menyimpan password di objek setelah login
                loggedInAnggota.setJenisKelamin(rs.getString("Jenis_Kelamin"));
                loggedInAnggota.setTempatTanggalLahir(rs.getString("Tempat_dan_Tanggal_Lahir"));
                loggedInAnggota.setTahunAngkatan(rs.getInt("Tahun_Angkatan"));
                loggedInAnggota.setEmail(rs.getString("Email"));
                
                // Konversi java.sql.Date ke java.time.LocalDate
                java.sql.Date sqlDate = rs.getDate("Tanggal_Bergabung");
                if (sqlDate != null) {
                    loggedInAnggota.setTanggalBergabung(sqlDate.toLocalDate());
                } else {
                    loggedInAnggota.setTanggalBergabung(null); // Atau LocalDate.now() jika ingin default
                }
                
                loggedInAnggota.setNoTelepon(rs.getString("No_Telepon"));

            
            // Anda bisa menggunakan 'Kelas' atau 'Username' untuk menentukan hak akses
            // Misalnya, jika admin punya kelas khusus atau username tertentu
            String userRole = loggedInAnggota.getKelas(); // Contoh: menggunakan kolom 'Kelas' sebagai role
                // Atau jika ada kolom 'Role' di DB: String userRole = rs.getString("Role");

                if (userRole.equalsIgnoreCase("Admin")) { // Contoh: Jika 'Kelas' adalah 'Admin'
                    new Mainmenu_Admin().setVisible(true);
                } else {
                    // Ini yang PENTING: Meneruskan objek loggedInAnggota
                    new Mainmenu_User(loggedInAnggota).setVisible(true); // <--- PASTIKAN INI!
                }
                this.dispose();
            
            
            // Atau, jika Anda ingin lebih fleksibel berdasarkan data dari database:
            // if (userKelas.equalsIgnoreCase("admin")) { // Contoh: admin ditentukan dari kolom Kelas
            //     new Mainmenu_Admin().setVisible(true);
            //     this.dispose();
            // } else {
            //     new Mainmenu_User().setVisible(true);
            //     this.dispose();
            // }

        } else {
            // Username atau Password tidak cocok
            JOptionPane.showMessageDialog(this, "Username atau Password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error saat login: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Cetak stack trace untuk debugging lebih lanjut
    } finally {
        // Pastikan semua resource JDBC ditutup untuk mencegah kebocoran memori
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error menutup koneksi database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_button_loginActionPerformed

    private void button_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_loginMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_button_loginMouseClicked

    private void button_loginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_loginMouseEntered
        button_login.setBackground(Color.lightGray);
    }//GEN-LAST:event_button_loginMouseEntered

    private void button_loginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_loginMouseExited
        button_login.setBackground(Color.lightGray);
    }//GEN-LAST:event_button_loginMouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_addMouseClicked

    private void btn_addMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_addMouseEntered

    private void btn_addMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_addMouseExited

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        
        
    }//GEN-LAST:event_btn_addActionPerformed

    private void btn_signupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_signupMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_signupMouseClicked

    private void btn_signupMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_signupMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_signupMouseEntered

    private void btn_signupMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_signupMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_signupMouseExited

    private void btn_signupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_signupActionPerformed
        SignUP signup = new SignUP(this); 
        signup.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btn_signupActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });

        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_signup;
    private javax.swing.JButton button_login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
