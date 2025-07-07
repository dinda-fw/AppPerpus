/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package appperpus;

import java.awt.Component; // Diperlukan untuk setPlaceholder agar bisa mendapatkan nama komponen
import java.awt.Color; // Untuk warna placeholder
import java.awt.event.FocusAdapter; // Untuk FocusListener
import java.awt.event.FocusEvent; // Untuk FocusListener
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate; // Untuk mendapatkan tanggal saat ini
import java.time.format.DateTimeFormatter; // Untuk format tanggal
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup; // Untuk JRadioButton
/**
 *
 * @author Acer
 */
public class SignUP extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SignUP.class.getName());
    private ButtonGroup jenisKelaminGroup; // Deklarasikan ButtonGroup
    private Login loginFrame;
    
    /**
     * Creates new form SignUP
     */
    public SignUP(Login loginframe) {
        this.loginFrame = loginFrame;
        initComponents();
        
        
        
        // Inisialisasi ButtonGroup untuk Jenis Kelamin
        jenisKelaminGroup = new ButtonGroup();
        jenisKelaminGroup.add(Lakilaki);
        jenisKelaminGroup.add(Perempuan);

        // Memberi nama pada JTextField untuk debugging placeholder
        txt_id.setName("txt_id");
        txt_nama.setName("txt_nama");
        txt_Kelas.setName("txt_Kelas");
        txt_Tahunangkatan.setName("txt_Tahunangkatan");
        txt_email.setName("txt_email");
        txt_telpon.setName("txt_telpon");
        txt_TTL.setName("txt_TTL"); // Tempat_dan_Tanggal_Lahir
        txt_username.setName("txt_username");
        txt_Password.setName("txt_Password");
        txt_Bergabung.setName("txt_Bergabung");

        // Panggil setPlaceholder untuk setiap JTextField
        setPlaceholder(txt_id, "ID Anggota (contoh: A001)"); // ID Anggota harus unik
        setPlaceholder(txt_nama, "Nama Lengkap");
        setPlaceholder(txt_Kelas, "Kelas (contoh: X-IPA)");
        setPlaceholder(txt_Tahunangkatan, "Tahun Angkatan (contoh: 2023)");
        setPlaceholder(txt_email, "Email");
        setPlaceholder(txt_telpon, "Nomor Telepon (contoh: 08123456789)");
        setPlaceholder(txt_TTL, "Tempat, Tanggal Lahir (contoh: Surabaya, 10-01-2000)");
        setPlaceholder(txt_username, "Username");
        setPlaceholder(txt_Password, "Password");
        
        txt_Bergabung.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        txt_Bergabung.setEditable(false); // Tidak bisa diedit manual
    }
    
    public SignUP() {
        this(null); // Call the main constructor, passing null for loginFrame for initial display
    }

    
     private void setPlaceholder(javax.swing.JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(new java.awt.Color(204, 204, 204)); // Warna abu-abu untuk placeholder
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {
                System.out.println("Focus Gained on: " + textField.getName()); 
                System.out.println("Current text: '" + textField.getText() + "'");
                System.out.println("Placeholder: '" + placeholder + "'");
                System.out.println("Current foreground color: " + textField.getForeground());

                // Periksa warna foreground untuk menentukan apakah itu masih placeholder
                if (textField.getForeground().equals(new java.awt.Color(204, 204, 204))) { 
                    System.out.println("Condition met: Foreground is placeholder color. Changing color to BLACK.");
                    textField.setText(""); // Hapus teks placeholder
                    textField.setForeground(new java.awt.Color(0, 0, 0)); // Ubah warna ke hitam
                } else {
                    System.out.println("Condition NOT met: Foreground is NOT placeholder color. Keeping current text and color.");
                }
            }

            @Override
            public void focusLost(FocusEvent evt) {
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
     
      private void clearFields(boolean isInitialCall) {
        txt_id.setText("");
        txt_nama.setText("");
        txt_Kelas.setText("");
        txt_Tahunangkatan.setText("");
        txt_email.setText("");
        txt_telpon.setText("");
        txt_TTL.setText("");
        txt_username.setText("");
        txt_Password.setText("");
        
        jenisKelaminGroup.clearSelection(); // Menghapus pilihan radio button

        // Hanya panggil setPlaceholder lagi jika bukan panggilan inisial
        // Karena di konstruktor sudah dipanggil
        if (!isInitialCall) {
            setPlaceholder(txt_id, "ID Anggota (contoh: A001)");
            setPlaceholder(txt_nama, "Nama Lengkap");
            setPlaceholder(txt_Kelas, "Kelas (contoh: X-IPA)");
            setPlaceholder(txt_Tahunangkatan, "Tahun Angkatan (contoh: 2023)");
            setPlaceholder(txt_email, "Email");
            setPlaceholder(txt_telpon, "Nomor Telepon (contoh: 08123456789)");
            setPlaceholder(txt_TTL, "Tempat, Tanggal Lahir (contoh: Surabaya, 10-01-2000)");
            setPlaceholder(txt_username, "Username");
            setPlaceholder(txt_Password, "Password");
        }
        
        // Tanggal Bergabung selalu diisi otomatis dengan tanggal saat ini
        txt_Bergabung.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_Kelas = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_Tahunangkatan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_telpon = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        Lakilaki = new javax.swing.JRadioButton();
        Perempuan = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        txt_Bergabung = new javax.swing.JTextField();
        BtnSignup = new javax.swing.JButton();
        BtnBatal = new javax.swing.JButton();
        txt_TTL = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_Password = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("No. ID");

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("DAFTAR ANGGOTA");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setText("ID");

        txt_id.setText("No. ID");
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setText("NAMA");

        txt_nama.setText("Masukan Nama");
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel4.setText("Kelazz");

        txt_Kelas.setText("Kelaz");
        txt_Kelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_KelasActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel5.setText("Tahun Angkatan");

        txt_Tahunangkatan.setText("Tahun");
        txt_Tahunangkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TahunangkatanActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel6.setText("Email");

        txt_email.setText("Email");
        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setText("Telpon");

        txt_telpon.setText("No. Telpon");
        txt_telpon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_telponActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setText("Jenis Kelamin");

        Lakilaki.setText("Laki-Laki");

        Perempuan.setText("Perempuan");
        Perempuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PerempuanActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setText("Tanggal Bergabung");

        txt_Bergabung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_BergabungActionPerformed(evt);
            }
        });

        BtnSignup.setBackground(new java.awt.Color(153, 102, 0));
        BtnSignup.setForeground(new java.awt.Color(255, 255, 255));
        BtnSignup.setText("Sing Up");
        BtnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSignupActionPerformed(evt);
            }
        });

        BtnBatal.setText("Batal");
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });

        txt_TTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TTLActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setText("Tempat, Tanggal Lahir");

        txt_username.setText("Username");
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setText("Username");

        txt_Password.setText("Password");
        txt_Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PasswordActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setText("Password");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_TTL, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_Bergabung, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(BtnBatal)
                        .addGap(18, 18, 18)
                        .addComponent(BtnSignup)
                        .addGap(28, 28, 28))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Lakilaki, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Perempuan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Tahunangkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_telpon, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Tahunangkatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_telpon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Lakilaki)
                    .addComponent(Perempuan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addGap(8, 8, 8)
                .addComponent(txt_TTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_Password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_Bergabung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtnBatal)
                            .addComponent(BtnSignup))
                        .addGap(8, 8, 8))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_KelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_KelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_KelasActionPerformed

    private void txt_TahunangkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TahunangkatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TahunangkatanActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_emailActionPerformed

    private void txt_telponActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_telponActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_telponActionPerformed

    private void PerempuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PerempuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PerempuanActionPerformed

    private void txt_BergabungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_BergabungActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_BergabungActionPerformed

    private void txt_TTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TTLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TTLActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void txt_PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PasswordActionPerformed

    private void BtnSignupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSignupActionPerformed
                                                 
         
        String idAnggota = txt_id.getText().trim();
        String namaAnggota = txt_nama.getText().trim();
        String kelas = txt_Kelas.getText().trim();
        String username = txt_username.getText().trim();
        String password = txt_Password.getText().trim(); // Dalam aplikasi nyata, hash password ini!
        String jenisKelamin = "";
        if (Lakilaki.isSelected()) {
            jenisKelamin = "Laki-laki";
        } else if (Perempuan.isSelected()) {
            jenisKelamin = "Perempuan";
        }
        String tempatTglLahir = txt_TTL.getText().trim();
        String tahunAngkatanStr = txt_Tahunangkatan.getText().trim();
        String email = txt_email.getText().trim();
        String tanggalBergabung = txt_Bergabung.getText().trim(); // Ini sudah diformat YYYY-MM-DD
        String noTelepon = txt_telpon.getText().trim();

        // --- VALIDASI INPUT ---
        // Cek apakah ada field yang masih berisi placeholder atau kosong
        if (idAnggota.equals("ID Anggota (contoh: A001)") || idAnggota.isEmpty() ||
            namaAnggota.equals("Nama Lengkap") || namaAnggota.isEmpty() ||
            kelas.equals("Kelas (contoh: X-IPA)") || kelas.isEmpty() ||
            username.equals("Username") || username.isEmpty() ||
            password.equals("Password") || password.isEmpty() ||
            jenisKelamin.isEmpty() || // Pastikan salah satu jenis kelamin dipilih
            tempatTglLahir.equals("Tempat, Tanggal Lahir (contoh: Surabaya, 10-01-2000)") || tempatTglLahir.isEmpty() ||
            tahunAngkatanStr.equals("Tahun Angkatan (contoh: 2023)") || tahunAngkatanStr.isEmpty() ||
            email.equals("Email") || email.isEmpty() ||
            noTelepon.equals("Nomor Telepon (contoh: 08123456789)") || noTelepon.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Mohon lengkapi semua data dengan benar!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int tahunAngkatan;
        try {
            tahunAngkatan = Integer.parseInt(tahunAngkatanStr);
            // Validasi tahun angkatan (contoh: harus 4 digit)
            if (tahunAngkatanStr.length() != 4) {
                 JOptionPane.showMessageDialog(this, "Tahun Angkatan harus 4 digit angka!", "Error Format", JOptionPane.ERROR_MESSAGE);
                 return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun Angkatan harus berupa angka!", "Error Format", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Cek apakah No_Telepon hanya berisi angka dan tidak terlalu panjang
        if (!noTelepon.matches("\\d+") || noTelepon.length() > 15) { // \d+ artinya satu atau lebih digit
            JOptionPane.showMessageDialog(this, "Nomor Telepon harus berupa angka dan maksimal 15 digit!", "Error Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement checkPst = null;
        ResultSet checkRs = null;

        try {
            conn = koneksi.getKoneksi();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Gagal terhubung ke database. Cek koneksi Anda.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- CEK DUPLIKASI ID_Anggota dan Username ---
            String checkSql = "SELECT COUNT(*) FROM anggota WHERE Id_Anggota = ? OR Username = ?";
            checkPst = conn.prepareStatement(checkSql);
            checkPst.setString(1, idAnggota);
            checkPst.setString(2, username);
            checkRs = checkPst.executeQuery();

            if (checkRs.next() && checkRs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "ID Anggota atau Username sudah terdaftar. Gunakan yang lain.", "Registrasi Gagal", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // --- INSERT DATA ANGGOTA BARU ---
            String sql = "INSERT INTO anggota (id_Anggota, Nama_Anggota, Kelas, Username, Password, Jenis_Kelamin, Tempat_dan_Tanggal_Lahir, Tahun_Angkatan, Email, Tanggal_Bergabung, No_Telepon) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, idAnggota);
            pst.setString(2, namaAnggota);
            pst.setString(3, kelas);
            pst.setString(4, username);
            pst.setString(5, password);
            pst.setString(6, jenisKelamin);
            pst.setString(7, tempatTglLahir);
            pst.setInt(8, tahunAngkatan);
            pst.setString(9, email);
            pst.setString(10, tanggalBergabung); // YYYY-MM-DD
            pst.setString(11, noTelepon);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Pendaftaran Berhasil! Silakan login.", "Registrasi Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearFields(false); // Bersihkan field setelah sukses
                this.dispose(); // Tutup jendela SignUp
                // Anda bisa menambahkan kode untuk membuka kembali jendela login di sini jika perlu
                System.out.println("Memeriksa referensi loginFrame: " + (loginFrame != null ? "TIDAK null" : "NULL"));
                if (loginFrame != null) {
                    System.out.println("Membuat loginFrame terlihat.");
                    loginFrame.setVisible(true); // Buat jendela Login terlihat lagi
                    System.out.println("loginFrame diatur menjadi terlihat.");
                } else {
                    System.out.println("loginFrame adalah NULL. Tidak dapat membuat jendela Login terlihat.");
                }
            }

        } catch (SQLException e) {
            // Penanganan error SQL lebih lanjut (misal, jika ada batasan unik lainnya)
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Pastikan semua resource ditutup
            try {
                if (checkRs != null) checkRs.close();
                if (checkPst != null) checkPst.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                logger.log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_BtnSignupActionPerformed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
         clearFields(false);
        this.dispose();
        // If "Batal" also should show the login frame
        if (loginFrame != null) {
            loginFrame.setVisible(true);
        }
    }//GEN-LAST:event_BtnBatalActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // For initial testing, you might need to create a Login frame first
                // Or remove this main method if SignUp is always called from Login
                //new SignUP(new Login()).setVisible(true); // Pass a new Login instance for testing
            }
        });
    
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnBatal;
    private javax.swing.JButton BtnSignup;
    private javax.swing.JRadioButton Lakilaki;
    private javax.swing.JRadioButton Perempuan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txt_Bergabung;
    private javax.swing.JTextField txt_Kelas;
    private javax.swing.JTextField txt_Password;
    private javax.swing.JTextField txt_TTL;
    private javax.swing.JTextField txt_Tahunangkatan;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_telpon;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
