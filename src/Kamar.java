public class Kamar {
    private int nomorKamar;
    private String tipeKamar;
    private double hargaPerMalam;
    private boolean tersedia;

    public Kamar(int nomorKamar, String tipeKamar, double hargaPerMalam, boolean tersedia) {
        this.nomorKamar = nomorKamar;
        this.tipeKamar = tipeKamar;
        this.hargaPerMalam = hargaPerMalam;
        this.tersedia = tersedia;
    }

    // Getters
    public int getNomorKamar() {
        return nomorKamar;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public double getHargaPerMalam() {
        return hargaPerMalam;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    // Setters
    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    public void printDetails(java.text.NumberFormat rupiahFormat) {
        System.out.println(this.getNomorKamar() + "\t\t" +
                this.getTipeKamar() + "\t\t" +
                rupiahFormat.format(this.getHargaPerMalam()) + "\t" +
                (this.isTersedia() ? "Tersedia" : "Tidak Tersedia"));
    }
}