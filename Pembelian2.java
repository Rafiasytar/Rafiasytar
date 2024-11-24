import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

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

class Transaksi extends Barang {
    private static int nomorFakturCounter = 1; // Counter untuk nomor faktur
    private String noFaktur;

    public Transaksi(String namaBarang, String kodeBarang, double hargaBarang, int jumlahBeli) {
        super(namaBarang, kodeBarang, hargaBarang, jumlahBeli);
        this.noFaktur = "FTR" + nomorFakturCounter++; //  melakukan Generate nomor faktur
    }

    public double getTotal() {
        return hargaBarang * jumlahBeli;
    }

    public void displayFaktur() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        
        System.out.println("\nSelamat Datang di Supermarket Bukigo");
        System.out.println("Tanggal dan Waktu: " + formatter.format(date));
        System.out.println("+----------------------------------------------------+");
        System.out.println("No. Faktur      : " + noFaktur);
        System.out.println("Kode Barang     : " + kodeBarang);
        System.out.println("Nama Barang     : " + namaBarang);
        System.out.println("Harga Barang    : " + hargaBarang);
        System.out.println("Jumlah Beli     : " + jumlahBeli);
        System.out.println("TOTAL           : " + getTotal());
        System.out.println("+----------------------------------------------------+");
        System.out.println("Kasir           : Muhammad Rafi Asytar");
        System.out.println("+----------------------------------------------------+");
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

class Pembelian2 {
    private static User user = new User("admin", "123");
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Proses login
            boolean isLoggedIn = false;
            while (!isLoggedIn) {
                String captcha = generateRandomString(5); // kode CAPTCHA akan selalu berubah jika login gagal
                System.out.println("+-----------------------------------------------------+");
                System.out.print("Username : ");
                String username = scanner.nextLine();

                System.out.print("Password : ");
                String password = scanner.nextLine();

                System.out.println("Captcha : " + captcha);
                System.out.print("Masukkan Captcha: ");
                String captchaInput = scanner.nextLine();

                if (user.login(username, password) && captcha.equals(captchaInput)) {
                    System.out.println("Login berhasil!");
                    isLoggedIn = true;
                } else {
                    System.out.println("Login gagal, silakan coba lagi.");
                }
                System.out.println("+-----------------------------------------------------+\n");
            }

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

                    Transaksi transaksi = new Transaksi(namaBarang, kodeBarang, hargaBarang, jumlahBeli);
                    transaksi.displayFaktur();

                } catch (Exception e) {
                    System.out.println("Terjadi kesalahan: " + e.getMessage());
                } finally {
                    scanner.nextLine(); 
                }

                System.out.print("\nApakah ingin melanjutkan transaksi? (y/n): ");
                String choice = scanner.nextLine();
                System.out.println(); 
                if (!choice.equalsIgnoreCase("y")) {
                    continueTransaction = false;
                }
            }
        } 
    }

    // Metode untuk menghasilkan string acak sebagai CAPTCHA
    private static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(characters.charAt(random.nextInt(characters.length())));
        }
        return captcha.toString();
    }
}
