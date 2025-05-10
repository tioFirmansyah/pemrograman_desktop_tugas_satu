import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    // Components
    private JTable tableKamar;
    private DefaultTableModel tableModel;
    private JButton btnLihatKamar, btnPesanKamar, btnResetStatus, btnKeluar;
    private JTextArea textAreaStruk;
    private JPanel mainPanel, buttonPanel, centerPanel, rightPanel;

    // Data dan format
    private Kamar[] kamarHotel;
    private List<Kamar> kamarDipesan = new ArrayList<>();
    private List<Integer> lamaMenginap = new ArrayList<>();
    private NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public MainFrame() {
        setTitle("Aplikasi Reservasi Hotel");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initKamarHotel();

        createUI();
    }

    private void initKamarHotel() {
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

    private void createUI() {
        mainPanel = new JPanel(new BorderLayout());

        // Header
        JLabel headerLabel = new JLabel("APLIKASI RESERVASI HOTEL", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Center panel with split content
        centerPanel = new JPanel(new BorderLayout());

        // Table for room list
        String[] columns = { "No. Kamar", "Tipe Kamar", "Harga/Malam", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        tableKamar = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableKamar);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Right panel for receipt
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Struk Reservasi"));

        textAreaStruk = new JTextArea();
        textAreaStruk.setEditable(false);
        JScrollPane scrollStruk = new JScrollPane(textAreaStruk);
        rightPanel.add(scrollStruk, BorderLayout.CENTER);

        // Add center and right panels to split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerPanel, rightPanel);
        splitPane.setResizeWeight(0.6);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        btnLihatKamar = new JButton("Lihat Daftar Kamar");
        btnPesanKamar = new JButton("Pesan Kamar");
        btnResetStatus = new JButton("Reset Status Kamar");
        btnKeluar = new JButton("Keluar");

        buttonPanel.add(btnLihatKamar);
        buttonPanel.add(btnPesanKamar);
        buttonPanel.add(btnResetStatus);
        buttonPanel.add(btnKeluar);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Setup action listeners
        setupActionListeners();

        // Initial display of rooms
        tampilkanDaftarKamar();
    }

    private void setupActionListeners() {
        btnLihatKamar.addActionListener(e -> tampilkanDaftarKamar());

        btnPesanKamar.addActionListener(e -> prosesReservasi());

        btnResetStatus.addActionListener(e -> {
            resetStatusKamar();
            tampilkanDaftarKamar();
            JOptionPane.showMessageDialog(this, "Status semua kamar telah direset menjadi tersedia.");
        });

        btnKeluar.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                    "Apakah anda yakin ingin keluar?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void tampilkanDaftarKamar() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Add each room individually instead of using a loop
        addRoomToTable(0);
        addRoomToTable(1);
        addRoomToTable(2);
        addRoomToTable(3);
        addRoomToTable(4);
        addRoomToTable(5);
        addRoomToTable(6);
        addRoomToTable(7);
    }

    // Helper method to add a specific room to the table
    private void addRoomToTable(int index) {
        Kamar kamar = kamarHotel[index];
        tableModel.addRow(new Object[] {
                kamar.getNomorKamar(),
                kamar.getTipeKamar(),
                rupiahFormat.format(kamar.getHargaPerMalam()),
                kamar.isTersedia() ? "Tersedia" : "Tidak Tersedia"
        });
    }

    private void prosesReservasi() {
        // Reset daftar kamar yang dipesan untuk pemesanan baru
        kamarDipesan.clear();
        lamaMenginap.clear();

        // Pesan kamar pertama (wajib)
        boolean success = pesanKamar();

        // Jika kamar pertama berhasil dipesan, tanyakan untuk kamar kedua
        if (success) {
            int lanjutPesan = JOptionPane.showConfirmDialog(this,
                    "Apakah ingin memesan kamar lagi?",
                    "Pemesanan Kamar",
                    JOptionPane.YES_NO_OPTION);

            if (lanjutPesan == JOptionPane.YES_OPTION) {
                boolean success2 = pesanKamar();

                // Jika kamar kedua berhasil dipesan, tanyakan untuk kamar ketiga
                if (success2) {
                    lanjutPesan = JOptionPane.showConfirmDialog(this,
                            "Apakah ingin memesan kamar lagi?",
                            "Pemesanan Kamar",
                            JOptionPane.YES_NO_OPTION);

                    if (lanjutPesan == JOptionPane.YES_OPTION) {
                        pesanKamar();
                        JOptionPane.showMessageDialog(this,
                                "Anda telah mencapai batas maksimum pemesanan (3 kamar).");
                    }
                }
            }
        }

        // Jika ada kamar yang dipesan, tampilkan struk
        if (!kamarDipesan.isEmpty()) {
            hitungDanTampilkanStruk();
        }

        // Refresh tampilan daftar kamar
        tampilkanDaftarKamar();
    }

    private boolean pesanKamar() {
        // Dapatkan nomor kamar yang dipilih
        String nomorKamarStr = JOptionPane.showInputDialog(this,
                "Masukkan nomor kamar yang ingin dipesan:",
                "Pemesanan Kamar",
                JOptionPane.QUESTION_MESSAGE);

        // Check if cancelled
        if (nomorKamarStr == null)
            return false;

        int nomorKamar;
        try {
            nomorKamar = Integer.parseInt(nomorKamarStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nomor kamar harus berupa angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Cari kamar dengan nomor yang diminta dengan cara direct access
        Kamar kamarDitemukan = cariKamarByNomor(nomorKamar);

        if (kamarDitemukan == null) {
            JOptionPane.showMessageDialog(this,
                    "Kamar dengan nomor " + nomorKamar + " tidak ditemukan.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!kamarDitemukan.isTersedia()) {
            JOptionPane.showMessageDialog(this,
                    "Maaf, kamar dengan nomor " + nomorKamar + " sudah dipesan.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Dapatkan lama menginap
        String lamaMenginapStr = JOptionPane.showInputDialog(this,
                "Masukkan lama menginap (malam):",
                "Pemesanan Kamar",
                JOptionPane.QUESTION_MESSAGE);

        // Check if cancelled
        if (lamaMenginapStr == null)
            return false;

        int lama;
        try {
            lama = Integer.parseInt(lamaMenginapStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lama menginap harus berupa angka.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (lama <= 0) {
            JOptionPane.showMessageDialog(this, "Lama menginap harus lebih dari 0 malam.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Tambahkan ke daftar pesanan
        kamarDipesan.add(kamarDitemukan);
        lamaMenginap.add(lama);

        // Update status kamar menjadi tidak tersedia
        kamarDitemukan.setTersedia(false);

        JOptionPane.showMessageDialog(this,
                "Kamar " + nomorKamar + " berhasil dipesan untuk " + lama + " malam.");
        return true;
    }

    // Helper method to find a room by number without looping
    private Kamar cariKamarByNomor(int nomorKamar) {
        // Check each room individually instead of using a loop
        if (kamarHotel[0].getNomorKamar() == nomorKamar)
            return kamarHotel[0];
        if (kamarHotel[1].getNomorKamar() == nomorKamar)
            return kamarHotel[1];
        if (kamarHotel[2].getNomorKamar() == nomorKamar)
            return kamarHotel[2];
        if (kamarHotel[3].getNomorKamar() == nomorKamar)
            return kamarHotel[3];
        if (kamarHotel[4].getNomorKamar() == nomorKamar)
            return kamarHotel[4];
        if (kamarHotel[5].getNomorKamar() == nomorKamar)
            return kamarHotel[5];
        if (kamarHotel[6].getNomorKamar() == nomorKamar)
            return kamarHotel[6];
        if (kamarHotel[7].getNomorKamar() == nomorKamar)
            return kamarHotel[7];
        return null;
    }

    private void hitungDanTampilkanStruk() {
        if (kamarDipesan.isEmpty())
            return;

        double totalBiaya = 0;
        StringBuilder struk = new StringBuilder();

        // Header struk
        struk.append("===== STRUK RESERVASI HOTEL =====\n\n");
        struk.append("Daftar Kamar yang Dipesan:\n");
        struk.append("No. Kamar\tTipe Kamar\tLama\tHarga/Malam\tTotal\n");
        struk.append("------------------------------------------------------------\n");

        // Daftar kamar yang dipesan - Tanpa menggunakan loop
        // Kamar 1 (selalu ada jika method ini dipanggil)
        totalBiaya += tambahkanKamarKeStruk(struk, 0);

        // Kamar 2 (jika ada)
        if (kamarDipesan.size() > 1) {
            totalBiaya += tambahkanKamarKeStruk(struk, 1);
        }

        // Kamar 3 (jika ada)
        if (kamarDipesan.size() > 2) {
            totalBiaya += tambahkanKamarKeStruk(struk, 2);
        }

        // Rincian biaya
        struk.append("\nRincian Biaya:\n");
        struk.append("Total Biaya Kamar: ").append(rupiahFormat.format(totalBiaya)).append("\n");

        // Hitung biaya layanan (Rp. 50.000,- per kamar)
        double biayaLayanan = 50000 * kamarDipesan.size();
        struk.append("Biaya Layanan (")
                .append(rupiahFormat.format(50000))
                .append(" x ")
                .append(kamarDipesan.size())
                .append(" kamar): ")
                .append(rupiahFormat.format(biayaLayanan))
                .append("\n");

        // Hitung pajak (10% dari total biaya reservasi)
        double biayaPajak = totalBiaya * 0.1;
        struk.append("Pajak (10%): ").append(rupiahFormat.format(biayaPajak)).append("\n");

        double totalSebelumDiskon = totalBiaya + biayaLayanan + biayaPajak;
        double totalAkhir = totalSebelumDiskon;

        // Cek apakah mendapatkan diskon (15% jika total biaya melebihi Rp. 500.000,-)
        if (totalBiaya > 500000) {
            double diskon = totalBiaya * 0.15;
            struk.append("Diskon (15%): ").append(rupiahFormat.format(diskon)).append("\n");
            totalAkhir -= diskon;
        }

        // Cek apakah mendapatkan sarapan gratis (jika total biaya melebihi Rp.
        // 300.000,-)
        if (totalBiaya > 300000) {
            struk.append("Bonus: Sarapan Gratis untuk semua tamu\n");
        }

        struk.append("\nTotal Pembayaran: ").append(rupiahFormat.format(totalAkhir)).append("\n");
        struk.append("===== TERIMA KASIH TELAH MEMESAN =====");

        // Tampilkan struk di text area
        textAreaStruk.setText(struk.toString());
    }

    // Helper method untuk menambahkan detail kamar ke struk tanpa menggunakan loop
    private double tambahkanKamarKeStruk(StringBuilder struk, int index) {
        Kamar kamar = kamarDipesan.get(index);
        int lama = lamaMenginap.get(index);
        double totalKamar = kamar.getHargaPerMalam() * lama;

        struk.append(kamar.getNomorKamar())
                .append("\t\t")
                .append(kamar.getTipeKamar())
                .append("\t")
                .append(lama)
                .append(" malam\t")
                .append(rupiahFormat.format(kamar.getHargaPerMalam()))
                .append("\t")
                .append(rupiahFormat.format(totalKamar))
                .append("\n");

        return totalKamar;
    }

    private void resetStatusKamar() {
        // Set each room individually to available instead of using a loop
        kamarHotel[0].setTersedia(true);
        kamarHotel[1].setTersedia(true);
        kamarHotel[2].setTersedia(true);
        kamarHotel[3].setTersedia(true);
        kamarHotel[4].setTersedia(true);
        kamarHotel[5].setTersedia(true);
        kamarHotel[6].setTersedia(true);
        kamarHotel[7].setTersedia(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
