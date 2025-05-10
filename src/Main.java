import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    // Array untuk menyimpan kamar hotel
    private static Kamar[] kamarHotel;
    // Matriks 2D untuk menyimpan pemesanan (baris: nomor pemesanan, kolom: kamar
    // dalam satu pemesanan)
    private static Kamar[][] kamarDipesan = new Kamar[50][3]; // Maksimal 50 pemesanan, masing-masing maksimal 3 kamar
    // Matriks 2D untuk menyimpan lama menginap (baris: nomor pemesanan, kolom: lama
    // menginap untuk kamar)
    private static int[][] lamaMenginap = new int[50][3];
    // Jumlah pemesanan yang sudah dilakukan
    private static int jumlahPemesanan = 0;
    // Jumlah kamar yang dipesan dalam pemesanan saat ini
    private static int jumlahKamarDipesan = 0;
    // Scanner untuk input
    private static Scanner scanner = new Scanner(System.in);
    // Format mata uang rupiah
    private static NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void main(String[] args) {
        // Inisialisasi data kamar hotel
        initKamarHotel();

        // Gunakan fungsi rekursif untuk menjalankan program
        jalankanProgram();

        System.out.println("\nTerima kasih telah menggunakan aplikasi Reservasi Hotel!");
        scanner.close();
    }

    // Method rekursif untuk menjalankan program
    private static void jalankanProgram() {
        boolean lanjut = tampilkanMenu();
        if (lanjut) {
            jalankanProgram(); // Rekursif untuk melanjutkan program
        }
    }

    // Method untuk menampilkan menu utama
    private static boolean tampilkanMenu() {
        System.out.println("\n=== APLIKASI RESERVASI HOTEL ===");
        System.out.println("1. Lihat Daftar Kamar");
        System.out.println("2. Pesan Kamar");
        System.out.println("3. Reset Status Kamar");
        System.out.println("4. Keluar");
        System.out.print("Pilih menu (1-4): ");

        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Membersihkan buffer

        switch (pilihan) {
            case 1:
                tampilkanDaftarKamar();
                return true;
            case 2:
                jumlahKamarDipesan = 0; // Reset jumlah kamar untuk pemesanan baru
                tampilkanDaftarKamar();
                prosesReservasi();
                hitungDanTampilkanStruk();
                jumlahPemesanan++; // Tambahkan jumlah pemesanan
                return true;
            case 3:
                resetStatusKamar();
                System.out.println("Status semua kamar telah direset menjadi tersedia.");
                return true;
            case 4:
                return false;
            default:
                System.out.println("Pilihan tidak valid. Silakan pilih menu 1-4.");
                return true;
        }
    }

    // Method untuk mereset status semua kamar menjadi tersedia
    private static void resetStatusKamar() {
        // Reset status setiap kamar secara individual
        kamarHotel[0].setTersedia(true);
        kamarHotel[1].setTersedia(true);
        kamarHotel[2].setTersedia(true);
        kamarHotel[3].setTersedia(true);
        kamarHotel[4].setTersedia(true);
        kamarHotel[5].setTersedia(true);
        kamarHotel[6].setTersedia(true);
        kamarHotel[7].setTersedia(true);
    }

    // Method untuk inisialisasi data kamar
    private static void initKamarHotel() {
        kamarHotel = new Kamar[8];

        // Inisialisasi kamar dengan berbagai tipe
        kamarHotel[0] = new Kamar(101, "Standar", 250000, true);
        kamarHotel[1] = new Kamar(102, "Standar", 250000, true);
        kamarHotel[2] = new Kamar(201, "Superior", 350000, true);
        kamarHotel[3] = new Kamar(202, "Superior", 350000, true);
        kamarHotel[4] = new Kamar(301, "Deluxe", 450000, true);
        kamarHotel[5] = new Kamar(302, "Deluxe", 450000, true);
        kamarHotel[6] = new Kamar(401, "Suite", 800000, true);
        kamarHotel[7] = new Kamar(402, "Suite", 800000, true);
    }

    // Method untuk menampilkan daftar kamar hotel yang tersedia
    private static void tampilkanDaftarKamar() {
        System.out.println("\n=== DAFTAR KAMAR HOTEL ===");
        System.out.println("No. Kamar\tTipe Kamar\tHarga/Malam\tStatus");

        // Gunakan rekursif untuk menampilkan info kamar
        tampilkanInfoKamarRekursif(0);
    }

    // Method rekursif untuk menampilkan info kamar
    private static void tampilkanInfoKamarRekursif(int index) {
        if (index >= kamarHotel.length) {
            return; // Basis rekursi: sudah melebihi jumlah kamar
        }

        // Tampilkan info kamar saat ini
        kamarHotel[index].printDetails(rupiahFormat);

        // Rekursif untuk kamar berikutnya
        tampilkanInfoKamarRekursif(index + 1);
    }

    // Method untuk memproses reservasi
    private static void prosesReservasi() {
        System.out.println("\n=== PEMESANAN KAMAR ===");
        System.out.println("Anda dapat memesan maksimal 3 kamar.");

        // Pemesanan kamar pertama (wajib)
        pesanKamar();

        // Tanya apakah ingin pesan kamar kedua
        System.out.print("\nApakah ingin memesan kamar lagi? (y/n): ");
        String jawaban = scanner.next();

        if (jawaban.equalsIgnoreCase("y")) {
            pesanKamar();

            // Tanya apakah ingin pesan kamar ketiga
            System.out.print("\nApakah ingin memesan kamar lagi? (y/n): ");
            jawaban = scanner.next();

            if (jawaban.equalsIgnoreCase("y")) {
                pesanKamar();
                System.out.println("Anda telah mencapai batas maksimum pemesanan (3 kamar).");
            }
        }
    }

    // Method untuk memesan satu kamar
    private static void pesanKamar() {
        if (jumlahKamarDipesan >= 3) {
            System.out.println("Maaf, Anda sudah mencapai batas maksimal pemesanan kamar (3 kamar).");
            return;
        }

        System.out.print("\nMasukkan nomor kamar yang ingin dipesan: ");
        int nomorKamar = scanner.nextInt();

        // Menggunakan fungsi rekursif untuk mencari kamar
        int indexKamar = cariKamarRekursif(nomorKamar, 0);
        Kamar kamarDitemukan = (indexKamar != -1) ? kamarHotel[indexKamar] : null;

        if (kamarDitemukan == null) {
            System.out.println("Kamar dengan nomor " + nomorKamar + " tidak ditemukan.");
            return;
        }

        if (!kamarDitemukan.isTersedia()) {
            System.out.println("Maaf, kamar dengan nomor " + nomorKamar + " sudah dipesan.");
            return;
        }

        System.out.print("Masukkan lama menginap (malam): ");
        int lama = scanner.nextInt();

        if (lama <= 0) {
            System.out.println("Lama menginap harus lebih dari 0 malam.");
            return;
        }

        // Simpan kamar yang dipesan dalam matriks 2D
        kamarDipesan[jumlahPemesanan][jumlahKamarDipesan] = kamarDitemukan;
        lamaMenginap[jumlahPemesanan][jumlahKamarDipesan] = lama;
        jumlahKamarDipesan++;

        // Update status kamar menjadi tidak tersedia
        kamarHotel[indexKamar].setTersedia(false);

        System.out.println("Kamar " + nomorKamar + " berhasil dipesan untuk " + lama + " malam.");
    }

    // Method untuk menghitung dan menampilkan struk
    private static void hitungDanTampilkanStruk() {
        if (jumlahKamarDipesan == 0) {
            System.out.println("\nTidak ada kamar yang dipesan.");
            return;
        }

        double totalBiaya = 0;

        System.out.println("\n===== STRUK RESERVASI HOTEL =====");
        System.out.println("No. Pemesanan: " + (jumlahPemesanan + 1));
        System.out.println("Daftar Kamar yang Dipesan:");
        System.out.println("No. Kamar\tTipe Kamar\tLama Menginap\tHarga/Malam\t\tTotal Harga");

        // Tampilkan semua kamar yang dipesan tanpa menggunakan perulangan
        totalBiaya += tampilkanStrukKamar(0);
        if (jumlahKamarDipesan > 1) {
            totalBiaya += tampilkanStrukKamar(1);
        }
        if (jumlahKamarDipesan > 2) {
            totalBiaya += tampilkanStrukKamar(2);
        }

        System.out.println("\nRincian Biaya:");
        System.out.println("Total Biaya Kamar: " + rupiahFormat.format(totalBiaya));

        // Hitung biaya layanan (Rp. 50.000,- per kamar)
        double biayaLayanan = 50000 * jumlahKamarDipesan;
        System.out.println("Biaya Layanan (" + rupiahFormat.format(50000) + " x " + jumlahKamarDipesan + " kamar): "
                + rupiahFormat.format(biayaLayanan));

        // Hitung pajak (10% dari total biaya reservasi)
        double biayaPajak = totalBiaya * 0.1;
        System.out.println("Pajak (10%): " + rupiahFormat.format(biayaPajak));

        double totalSebelumDiskon = totalBiaya + biayaLayanan + biayaPajak;
        double totalAkhir = totalSebelumDiskon;

        // Cek apakah mendapatkan diskon (15% jika total biaya melebihi Rp. 500.000,-)
        if (totalBiaya > 500000) {
            double diskon = totalBiaya * 0.15;
            System.out.println("Diskon (15%): " + rupiahFormat.format(diskon));
            totalAkhir -= diskon;
        }

        // Cek apakah mendapatkan sarapan gratis (jika total biaya melebihi Rp.
        // 300.000,-)
        if (totalBiaya > 300000) {
            System.out.println("Bonus: Sarapan Gratis untuk semua tamu");
        }

        System.out.println("\nTotal Pembayaran: " + rupiahFormat.format(totalAkhir));
        System.out.println("===== TERIMA KASIH TELAH MEMESAN =====");
    }

    // Method helper untuk menampilkan informasi kamar dalam struk
    private static double tampilkanStrukKamar(int index) {
        if (index >= jumlahKamarDipesan) {
            return 0;
        }

        double totalKamar = kamarDipesan[jumlahPemesanan][index].getHargaPerMalam()
                * lamaMenginap[jumlahPemesanan][index];

        System.out.println(kamarDipesan[jumlahPemesanan][index].getNomorKamar() + "\t\t" +
                kamarDipesan[jumlahPemesanan][index].getTipeKamar() + "\t\t" +
                lamaMenginap[jumlahPemesanan][index] + " malam\t" +
                rupiahFormat.format(kamarDipesan[jumlahPemesanan][index].getHargaPerMalam()) + "\t" +
                rupiahFormat.format(totalKamar));

        return totalKamar;
    }

    // Method rekursif untuk mencari kamar berdasarkan nomor
    private static int cariKamarRekursif(int nomorKamar, int index) {
        // Basis rekursi: sudah melebihi jumlah kamar
        if (index >= kamarHotel.length) {
            return -1;
        }

        // Jika kamar ditemukan, kembalikan indeksnya
        if (kamarHotel[index].getNomorKamar() == nomorKamar) {
            return index;
        }

        // Rekursif untuk kamar berikutnya
        return cariKamarRekursif(nomorKamar, index + 1);
    }
}