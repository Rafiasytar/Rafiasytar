import java.util.Scanner;

// Kelas dasar untuk menyimpan informasi barang
class Barang {
    protected String namaBarang;
    protected String kodeBarang;
    protected double hargaBarang;
    protected int jumlahBeli;

    public Barang(String namaBarang, String kodeBarang, double hargaBarang, int jumlahBeli) {
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBeli = jumlahBeli;
    }
}

// Kelas turunan yang menghitung total harga dan menampilkan faktur
class Transaksi extends Barang {
    private static int nomorFakturCounter = 1; // Counter untuk nomor faktur
    private String noFaktur;

    public Transaksi(String namaBarang, String kodeBarang, double hargaBarang, int jumlahBeli) {
        super(namaBarang, kodeBarang, hargaBarang, jumlahBeli);
        this.noFaktur = "FTR" + nomorFakturCounter++; // Generate nomor faktur
    }

    public double getTotal() {
        return hargaBarang * jumlahBeli;
    }

    public void displayFaktur() {
        System.out.println("\n=== Faktur Transaksi ===");
        System.out.println("No Faktur: " + noFaktur);
        System.out.println("Nama Barang: " + namaBarang);
        System.out.println("Kode Barang: " + kodeBarang);
        System.out.println("Harga Barang: " + hargaBarang);
        System.out.println("Jumlah Beli: " + jumlahBeli);
        System.out.println("Total: " + getTotal());
        System.out.println("========================");
    }
}

// Kelas utama untuk menjalankan program
public class Pembelian {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueTransaction = true;

        while (continueTransaction) {
            try {
                System.out.print("Masukkan Nama Barang: ");
                String namaBarang = scanner.nextLine();

                System.out.print("Masukkan Kode Barang: ");
                String kodeBarang = scanner.nextLine();

                System.out.print("Masukkan Harga Barang: ");
                double hargaBarang = scanner.nextDouble();
                if (hargaBarang < 1) {
                    throw new IllegalArgumentException("Harga barang harus lebih dari 1 rupiah.");
                }

                System.out.print("Masukkan Jumlah Beli: ");
                int jumlahBeli = scanner.nextInt();
                if (jumlahBeli <= 0) {
                    throw new IllegalArgumentException("Jumlah beli harus lebih dari 0.");
                }

                // Membuat objek Transaksi
                Transaksi transaksi = new Transaksi(namaBarang, kodeBarang, hargaBarang, jumlahBeli);
                transaksi.displayFaktur();

            } catch (Exception e) {
                System.out.println("Terjadi kesalahan: " + e.getMessage());
            } finally {
                scanner.nextLine(); // membersihkan buffer
            }

            // Pertanyaan untuk transaksi lain
            System.out.print("Apakah Anda ingin melakukan transaksi lain? (ya/tidak): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("ya")) {
                continueTransaction = false;
            }

            // Menambahkan spasi setelah memilih
            System.out.println(); // Tambahkan baris kosong setelah memilih
        }

        scanner.close();
        System.out.println("Terima kasih telah bertransaksi!");
    }
}