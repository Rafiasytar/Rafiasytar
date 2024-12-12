import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

class Barang {
    private String namaBarang;
    private String kodeBarang;
    private double hargaBarang;
    private int jumlahStock;

    public Barang(String namaBarang, String kodeBarang, double hargaBarang, int jumlahStock) {
        this.namaBarang = namaBarang;
        this.kodeBarang = kodeBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahStock = jumlahStock;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public int getJumlahStock() {
        return jumlahStock;
    }

    public void setJumlahStock(int jumlahStock) {
        this.jumlahStock = jumlahStock;
    }
}

class Transaksi {
    private static int nomorFakturCounter = 1; // Counter untuk nomor faktur
    private String noFaktur;
    private Barang barang;
    private int jumlahBeli;

    public Transaksi(Barang barang, int jumlahBeli) {
        this.noFaktur = "FTR" + nomorFakturCounter++; //  melakukan Generate nomor faktur
        this.barang = barang;
        this.jumlahBeli = jumlahBeli;
    }

    public double getTotal() {
        return barang.getHargaBarang() * jumlahBeli;
    }

    public void displayFaktur() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("  Terima kasih sudah berbelanja di Supermarket BUKIGO   ");
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Tanggal dan Waktu: " + formatter.format(date));
        System.out.println("+-----------------------------------------------------+");
        System.out.println("No. Faktur      : " + noFaktur);
        System.out.println("Kode Barang     : " + barang.getKodeBarang());
        System.out.println("Nama Barang     : " + barang.getNamaBarang());
        System.out.println("Harga Barang    : " + barang.getHargaBarang());
        System.out.println("Jumlah Beli     : " + jumlahBeli);
        System.out.println("TOTAL           : " + getTotal());
        System.out.println("+-----------------------------------------------------+");
        System.out.println("Kasir           : Muhammad Rafi Asytar");
        System.out.println("+-----------------------------------------------------+");
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

class Pembelian {
    private static User admin = new User("admin", "admin123");
    private static User kasir = new User("kasir", "kasir123");
    private static Connection connection;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/supermarket", "postgres", "Tarompa1");
            System.out.println("Koneksi berhasil");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Koneksi gagal");
            return;
        }

        while (true) {
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("         SISTEM INFORMASI SUPERMARKET BUKIGO        ");
            System.out.println("+-----------------------------------------------------+");
            System.out.println("1. Login sebagai Admin");
            System.out.println("2. Login sebagai Kasir");
            System.out.println("3. Keluar Aplikasi");
            System.out.print("Pilih menu login (1/2/3): ");
            int menu = scanner.nextInt();
            scanner.nextLine();

            switch (menu) {
                case 1:
                    loginAdmin(scanner);
                    break;
                case 2:
                    loginKasir(scanner);
                    break;
                case 3:
                    System.out.println("Terima kas ih telah menggunakan aplikasi ini.");
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        System.out.println("Gagal menutup koneksi.");
                    }
                    return;
                default:
                    System.out.println("Menu tidak tersedia. Silakan coba lagi.");
            }
        }
    }
    }

    private static void loginAdmin(Scanner scanner) {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("                   LOGIN ADMIN                        ");
        System.out.println("+-----------------------------------------------------+");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String captcha = generateRandomString(5);
        System.out.println("Captcha : " + captcha);
        System.out.print("Masukkan Captcha: ");
        String captchaInput = scanner.nextLine();

        if (admin.login(username, password) && captcha.equals(captchaInput)) {
            System.out.println("\n");
            System.out.println("Login sebagai Admin berhasil.");
            menuAdmin(scanner);
        } else {
            System.out.println("\n");
            System.out.println("Login gagal. Silakan coba lagi.");
        }
    }

    private static void loginKasir(Scanner scanner) {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("                   LOGIN KASIR                       ");
        System.out.println("+-----------------------------------------------------+");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        String captcha = generateRandomString(5);
        System.out.println("Captcha: " + captcha);
        System.out.print("Masukkan Captcha: ");
        String captchaInput = scanner.nextLine();

        if (kasir.login(username, password) && captcha.equals(captchaInput)) {
            System.out.println("\n");
            System.out.println("Login sebagai Kasir berhasil.");
            menuKasir(scanner);
        } else {
            System.out.println("\n");
            System.out.println("Login gagal. Silakan coba lagi.");
        }
    }

    private static void menuAdmin(Scanner scanner) {
        boolean continueAdmin = true;
        while (continueAdmin) {
            System.out.println("\n+-----------------------------------------------------+");
            System.out.println("                   MENU ADMIN                         ");
            System.out.println("+-----------------------------------------------------+");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Hapus Barang");
            System.out.println("3. Ubah Barang");
            System.out.println("4. Cari Barang");
            System.out.println("5. Tampilkan Semua Barang");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu (1-6): ");
            int menu = scanner.nextInt();
            scanner.nextLine(); 

            switch (menu) {
                case 1:
                    tambahBarang(scanner);
                    break;
                case 2:
                    hapusBarang(scanner);
                    break;
                case 3:
                    ubahBarang(scanner);
                    break;
                case 4:
                    cariBarang(scanner);
                    break;
                case 5:
                    tampilkanSemuaBarang();
                    break;
                case 6:
                    continueAdmin = false;
                    break;
                default:
                    System.out.println("Menu tidak tersedia. Silakan coba lagi.");
            }
        }
    }

    private static void tambahBarang(Scanner scanner) {
        System.out.println("\n");
        System.out.println("Masukan data barang yang ingin di tambahkan:");
        System.out.print("Masukkan Nama Barang: ");
        String namaBarang = scanner.nextLine();
        System.out.print("Masukkan Kode Barang: ");
        String kodeBarang = scanner.nextLine();
        System.out.print("Masukkan Harga Barang: ");
        double hargaBarang = scanner.nextDouble();
        System.out.print("Masukkan Jumlah Stock: ");
        int jumlahStock = scanner.nextInt();
        scanner.nextLine();

        String sql = "INSERT INTO barang (nama_barang, kode_barang, harga_barang, jumlah_stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, namaBarang);
            pstmt.setString(2, kodeBarang);
            pstmt.setDouble(3, hargaBarang);
            pstmt.setInt(4, jumlahStock);
            pstmt.executeUpdate();
            System.out.println("Barang berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan barang: " + e.getMessage());
        }
    }

    private static void hapusBarang(Scanner scanner) {
        System.out.print("Masukkan Kode Barang yang ingin dihapus: ");
        String kodeBarang = scanner.nextLine();

        String sql = "DELETE FROM barang WHERE kode_barang = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kodeBarang);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Barang berhasil dihapus.");
            } else {
                System.out.println("Barang dengan kode " + kodeBarang + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus barang: " + e.getMessage());
        }
    }

    private static void ubahBarang(Scanner scanner) {
        System.out.print("Masukkan Kode Barang yang ingin diubah: ");
        String kodeBarang = scanner.nextLine();
        Barang barangToUpdate = null;

        String sql = "SELECT * FROM barang WHERE kode_barang = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, kodeBarang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                barangToUpdate = new Barang(rs.getString("nama_barang"), rs.getString("kode_barang"), rs.getDouble("harga_barang"), rs.getInt("jumlah_stock"));
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari barang: " + e.getMessage());
        }

        if (barangToUpdate != null) {
            System.out.print("Masukkan Nama Barang baru: ");
            String namaBarang = scanner.nextLine();
            System.out.print("Masukkan Harga Barang baru: ");
            double hargaBarang = scanner.nextDouble();
            System.out.print("Masukkan Jumlah Stock baru: ");
            int jumlahStock = scanner.nextInt();
            scanner.nextLine();

            sql = "UPDATE barang SET nama_barang = ?, harga_barang = ?, jumlah_stock = ? WHERE kode_barang = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, namaBarang);
                pstmt.setDouble(2, hargaBarang);
                pstmt.setInt(3, jumlahStock);
                pstmt.setString(4, kodeBarang);
                pstmt.executeUpdate();
                System.out.println("Barang berhasil diubah.");
            } catch (SQLException e) {
                System.out.println("Gagal mengubah barang: " + e.getMessage());
            }
        } else {
            System.out.println("Barang dengan kode " + kodeBarang + " tidak ditemukan.");
        }
    }

    private static void cariBarang(Scanner scanner) {
        System.out.print("Masukkan Kode Barang yang ingin dicari: ");
        String kodeBarang = scanner.nextLine();
        String query = "SELECT * FROM barang WHERE kode_barang = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, kodeBarang);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Nama Barang: " + rs.getString("nama_barang"));
                System.out.println("Harga Barang: " + rs.getDouble("harga_barang"));
                System.out.println("Jumlah Stock: " + rs.getInt("jumlah_stock"));
            } else {
                System.out.println("Barang dengan kode " + kodeBarang + " tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari barang: " + e.getMessage());
        }
    }

    private static void tampilkanSemuaBarang() {
        String sql = "SELECT * FROM barang";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("Tidak ada barang yang tersedia.");
                return;
            }
            System.out.println("\nDaftar Semua Barang:");
            while (rs.next()) {
                System.out.println("Kode Barang: " + rs.getString("kode_barang") + ", Nama Barang: " + rs.getString("nama_barang") + ", Harga: " + rs.getDouble("harga_barang") + ", Stock: " + rs.getInt("jumlah_stock"));
            }
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan barang: " + e.getMessage());
        }
    }

    private static void menuKasir(Scanner scanner) {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("                    Supermarket Bukigo                  ");
        System.out.println("+-----------------------------------------------------+");
        boolean continueKasir = true;
        while (continueKasir) {
            System.out.print("Masukkan Kode Barang untuk pembelian: ");
            String kodeBarang = scanner.nextLine();
            Barang barang = null;
    
            String query = "SELECT * FROM barang WHERE kode_barang = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, kodeBarang);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    barang = new Barang(rs.getString("nama_barang"), rs.getString("kode_barang"), rs.getDouble("harga_barang"), rs.getInt("jumlah_stock"));
                }
            } catch (SQLException e) {
                System.out.println("Gagal mencari barang: " + e.getMessage());
            }
    
            if (barang == null) {
                System.out.println("Barang dengan kode " + kodeBarang + " tidak ditemukan.");
                continue;
            }
    
            System.out.print("Masukkan Jumlah Beli: ");
            int jumlahBeli = scanner.nextInt();
            scanner.nextLine(); 
    
            if (jumlahBeli > barang.getJumlahStock()) {
                System.out.println("Stock tidak cukup untuk barang " + barang.getNamaBarang() + ".");
                continue;
            }
    
            Transaksi transaksi = new Transaksi(barang, jumlahBeli);
            transaksi.displayFaktur();
    
            query = "UPDATE barang SET jumlah_stock = jumlah_stock - ? WHERE kode_barang = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setInt(1, jumlahBeli);
                pstmt.setString(2, barang.getKodeBarang());
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Gagal mengupdate stock barang: " + e.getMessage());
            }
    
            System.out.print("\nApakah ingin melanjutkan transaksi? (y/n): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("y")) {
                continueKasir = false;
            }
        }
    }

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