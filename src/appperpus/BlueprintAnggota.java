/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appperpus;

import java.time.LocalDate; // Untuk Tanggal_Bergabung

/**
 *
 * @author Acer
 */
public class BlueprintAnggota {
    private String idAnggota;
    private String namaAnggota;
    private String kelas;
    private String username;
    private String password; // Sebaiknya tidak disimpan dalam objek model setelah login
    private String jenisKelamin;
    private String tempatTanggalLahir;
    private int tahunAngkatan;
    private String email;
    private LocalDate tanggalBergabung; // Menggunakan LocalDate untuk tipe DATE
    private String noTelepon;

    // Konstruktor kosong (opsional, tapi berguna)
    public BlueprintAnggota() {}

    // Konstruktor dengan semua field
    public BlueprintAnggota(String idAnggota, String namaAnggota, String kelas, String username,
                   String password, String jenisKelamin, String tempatTanggalLahir,
                   int tahunAngkatan, String email, LocalDate tanggalBergabung, String noTelepon) {
        this.idAnggota = idAnggota;
        this.namaAnggota = namaAnggota;
        this.kelas = kelas;
        this.username = username;
        this.password = password;
        this.jenisKelamin = jenisKelamin;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.tahunAngkatan = tahunAngkatan;
        this.email = email;
        this.tanggalBergabung = tanggalBergabung;
        this.noTelepon = noTelepon;
    }

    // --- Getter dan Setter untuk setiap field ---
    public String getIdAnggota() { return idAnggota; }
    public void setIdAnggota(String idAnggota) { this.idAnggota = idAnggota; }

    public String getNamaAnggota() { return namaAnggota; }
    public void setNamaAnggota(String namaAnggota) { this.namaAnggota = namaAnggota; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getTempatTanggalLahir() { return tempatTanggalLahir; }
    public void setTempatTanggalLahir(String tempatTanggalLahir) { this.tempatTanggalLahir = tempatTanggalLahir; }

    public int getTahunAngkatan() { return tahunAngkatan; }
    public void setTahunAngkatan(int tahunAngkatan) { this.tahunAngkatan = tahunAngkatan; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getTanggalBergabung() { return tanggalBergabung; }
    public void setTanggalBergabung(LocalDate tanggalBergabung) { this.tanggalBergabung = tanggalBergabung; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

}
