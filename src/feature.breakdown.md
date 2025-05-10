# Rangkuman Tugas Praktik 1: Aplikasi Reservasi Hotel

## Deskripsi Tugas
Tugas Praktik 1 merupakan implementasi Aplikasi Reservasi Hotel untuk mempraktikkan konsep dasar pemrograman Java dalam konteks aplikasi desktop. Tugas ini menguji pemahaman tentang konsep class, object, method, array, string, serta struktur keputusan dalam Java.

## Tujuan Pembelajaran
1. Mengimplementasikan penggunaan tipe data, variabel, identifier, dan keyword dalam class, object, dan method
2. Mengimplementasikan array dan/atau string dalam method
3. Mengimplementasikan struktur keputusan untuk pemrosesan data

## Fitur Aplikasi
Aplikasi Reservasi Hotel yang telah diimplementasikan memiliki fitur-fitur berikut:

1. **Data Kamar Hotel**
   - Menyimpan data kamar dalam array dengan 8 kamar
   - Setiap kamar memiliki atribut: nomor kamar, tipe kamar, harga per malam, dan status ketersediaan
   - Tipe kamar terdiri dari: Standar, Superior, Deluxe, dan Suite

2. **Pemesanan Kamar**
   - Pelanggan dapat memesan maksimal 3 kamar
   - Untuk setiap kamar, pelanggan dapat menentukan lama menginap
   - Sistem memeriksa ketersediaan kamar sebelum pemesanan

3. **Perhitungan Biaya**
   - Menghitung total biaya berdasarkan tipe kamar dan lama menginap
   - Menambahkan pajak 10% dari total biaya reservasi
   - Menambahkan biaya layanan Rp. 50.000,- per kamar
   - Memberikan diskon 15% jika total biaya melebihi Rp. 500.000,-
   - Memberikan bonus sarapan gratis jika total biaya melebihi Rp. 300.000,-

4. **Struk Reservasi**
   - Menampilkan daftar kamar yang dipesan dengan detailnya
   - Menampilkan rincian biaya termasuk biaya kamar, layanan, pajak, dan diskon
   - Menampilkan informasi bonus jika ada


## Petunjuk Pengerjaan
1. Buatlah kelas Kamar untuk merepresentasikan kamar hotel dengan atribut yang sesuai.
2. Buatlah kelas Main yang memiliki berbagai method untuk:
3. Menampilkan daftar kamar hotel
4. Menerima dan mengolah reservasi
5. Menghitung total biaya reservasi
6. Mencetak struk reservasi
7. Gunakan Array dalam mengelola data kamar.
8. Gunakan Struktur Keputusan dalam mengimplementasikan logika perhitungan total biaya dan mencetak struk reservasi.
9. Jangan menggunakan perulangan dalam tugas ini.
10. Menampilkan skenario-skenario yang berbeda berdasarkan kondisi yang diberikan dalam soal.
11. Buatlah video penjelasan (maksimal 15 menit) tentang cara kerja kode program yang kalian buat.
 

## Implementasi Program
Program diimplementasikan menggunakan dua kelas utama:

1. **Kelas Kamar**
   - Menyimpan properti dasar kamar (nomor, tipe, harga, status)
   - Menyediakan getter dan setter untuk akses dan manipulasi data
   - Memiliki method untuk menampilkan detail kamar

2. **Kelas Main**
   - Berisi logika utama aplikasi
   - Menggunakan array dan matriks untuk menyimpan data kamar dan pemesanan
   - Mengimplementasikan menu interaktif dengan 4 opsi:
     - Lihat Daftar Kamar
     - Pesan Kamar
     - Reset Status Kamar
     - Keluar
   - Menggunakan rekursi sebagai pengganti perulangan
   - Menghitung dan menampilkan struk reservasi dengan rincian biaya


## Konsep yang Diaplikasikan
- Class dan Object
- Array dan Matriks
- Rekursi
- Struktur Keputusan (if-else, switch-case)
- Encapsulation (melalui private fields dan public methods)
- Formatting Output

Implementasi ini telah berhasil memenuhi semua persyaratan tugas dan mendemonstrasikan pemahaman konsep pemrograman Java dalam konteks aplikasi desktop.