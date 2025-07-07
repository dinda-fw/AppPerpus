# AppPerpus - Aplikasi Sistem Informasi Perpustakaan Desktop

![Java Logo](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL Logo](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Apache NetBeans Logo](https://img.shields.io/badge/NetBeans-182B49?style=for-the-badge&logo=apache-netbeans&logoColor=white)
![GitHub Logo](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)

**Aplikasi Perpustakaan Aksara Maya** ini merupakan sebuah sistem informasi berbasis desktop yang dikembangkan untuk membantu pengelolaan administrasi dan layanan di perpustakaan secara digital dan efisien. Aplikasi ini dirancang menggunakan bahasa pemrograman **Java** dengan antarmuka grafis (GUI) berbasis **Java Swing**, serta didukung oleh **database MySQL** untuk menyimpan dan mengelola data secara terstruktur.

Aplikasi ini memiliki **dua jenis pengguna**, yaitu **Admin** dan **User (Anggota)**. Setiap pengguna memiliki akses dan hak sesuai peran masing-masing. Admin bertugas mengelola data buku, anggota, serta melakukan proses peminjaman dan pengembalian buku fisik. Sementara itu, User dapat melihat riwayat pinjam dan membaca buku digital secara online.

## Fitur Utama

Aplikasi ini dilengkapi dengan berbagai fitur untuk memudahkan manajemen perpustakaan:

* **Login Multi-Role (Admin & User):**
    Sistem login yang membedakan hak akses antara admin dan user, memastikan keamanan dan pengelolaan sesuai peran.

* **Manajemen Buku Fisik:**
    Admin dapat dengan mudah menambahkan, mengedit, dan menghapus data buku fisik yang tersedia di perpustakaan, menjaga inventaris tetap akurat.

* **Manajemen Data Anggota:**
    Admin dapat mengelola data anggota perpustakaan, termasuk kemampuan untuk mencetak kartu anggota dalam format siap cetak.

* **Peminjaman dan Pengembalian Buku Fisik:**
    Admin dapat mencatat transaksi peminjaman dan pengembalian buku, termasuk penghitungan otomatis untuk keterlambatan dan denda.

* **Cetak Kartu Anggota:**
    Aplikasi dapat mencetak kartu anggota dalam format siap cetak berdasarkan data anggota yang telah diinputkan.

* **Baca Buku Online:**
    Fitur inovatif yang memungkinkan User untuk mengakses koleksi buku digital langsung melalui aplikasi, baik melalui fitur WebView maupun tampilan Flipbook HTML.

* **Riwayat Pinjam Anggota:**
    User dapat melihat riwayat peminjaman buku fisik mereka secara langsung melalui antarmuka aplikasi.

Aplikasi ini diharapkan mampu meningkatkan efisiensi layanan perpustakaan, meminimalisir kesalahan pencatatan manual, serta memberikan pengalaman digital yang lebih baik bagi pengguna dalam mengakses informasi dan bahan bacaan.

## Teknologi yang Digunakan

* **Bahasa Pemrograman:** Java
* **Antarmuka Pengguna (GUI):** Java Swing
* **Database:** MySQL (hubungi lebih lanjut untuk file database)
* **Lingkungan Pengembangan (IDE):** Apache NetBeans IDE23

## Instalasi dan Cara Menjalankan

Untuk menjalankan aplikasi ini di lingkungan lokal Anda, ikuti langkah-langkah berikut:

1.  **Persiapan Database MySQL:**
    * Pastikan Anda telah menginstal MySQL Server di komputer Anda.
    * Buat database baru dengan nama `db_perpustakaan`
    * Impor skema database (file `.sql`)
    * Pastikan kredensial koneksi database (username, password, host) di aplikasi Java Anda (`src/nama/paket/anda/KoneksiDatabase.java` atau serupa) sesuai dengan konfigurasi MySQL Anda.

2.  **Clone Repositori:**
    Buka Terminal atau Command Prompt Anda dan jalankan perintah berikut:
    ```bash
    git clone [https://github.com/dinda-fw/AppPerpus.git](https://github.com/dinda-fw/AppPerpus.git)
    cd AppPerpus
    ```

3.  **Buka Proyek di NetBeans:**
    * Buka Apache NetBeans IDE.
    * Pilih `File` > `Open Project...`
    * Navigasi ke folder `AppPerpus` yang baru saja Anda clone dan klik `Open Project`.

4.  **Atur Dependensi (jika ada):**
    * Pastikan semua library eksternal yang dibutuhkan (misalnya, MySQL JDBC Driver) sudah ditambahkan ke `Libraries` proyek Anda di NetBeans. Biasanya Anda bisa klik kanan pada folder `Libraries` di panel "Projects" > `Add JAR/Folder...` atau `Add Library...`.

5.  **Jalankan Aplikasi:**
    * Setelah proyek terbuka, database terkoneksi, dan semua dependensi teratasi, klik tombol `Run Project` (ikon panah hijau) di toolbar NetBeans.
    * Atau, klik kanan pada `AppPerpus` di panel "Projects" dan pilih `Run`.

## Kontribusi

Kontribusi dalam bentuk apapun sangat dihargai! Jika Anda memiliki saran perbaikan, fitur baru, atau menemukan bug, jangan ragu untuk:
* Membuka sebuah **Issue** di repositori GitHub ini.
* Mengirimkan **Pull Request** dengan perubahan yang Anda usulkan.

## Lisensi

Ini Project Mata Kuliah Pemrograman Berbasis Objek semester 2 
Proyek ini dilisensikan di bawah [Universitas Negeri Surabaya, MIT License, Apache 2.0 License, GPLv3].

---

