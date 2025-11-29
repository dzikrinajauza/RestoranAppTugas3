/*
 * Aplikasi Restoran: Kelas Utama (RestoranApp)
 * Dibuat oleh: dzikr
 */
package restoranapp;

import java.util.Scanner;
// Catatan: InputMismatchException tidak diperlukan karena Anda hanya menggunakan nextLine()

//Kelas utama untuk menjalankan aplikasi manajemen dan pemesanan restoran.
//Memuat menu, menangani interaksi pengguna, dan mengarahkan ke mode Admin atau Pelanggan
public class RestoranApp {
    // Diasumsikan kelas Menu, Makanan, Minuman, Diskon, MenuItem, dan Pesanan ada di package restoranapp.
    private static Menu daftarMenu = new Menu();
    private static final Scanner input = new Scanner(System.in);
    // private static boolean isAdminMode = false; // Variabel ini tidak benar-benar digunakan, bisa dipertahankan atau dihapus

    public static void main(String[] args) {
        // Inisiasi data menu saat program pertama kali berjalan
        inisialisasiMenu();

        int pilihan = -1;
        do {
            tampilkanMenuUtama();
            try {
                String inputStr = input.nextLine();
                pilihan = Integer.parseInt(inputStr);

                switch (pilihan) {
                    case 1 -> prosesPemesanan(); // Mode Pelanggan
                    case 2 -> prosesManajemenMenu(); // Mode Admin
                    case 0 -> System.out.println("Terima Kasih Telah Menggunakan Aplikasi Restoran JINHIT.");
                    default -> System.out.println("PERINGATAN: Pilihan tidak valid, silahkan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("PERINGATAN: INPUT HARUS BERUPA ANGKA.");
                pilihan = -1;
            }
            System.out.println();

        } while (pilihan != 0);

        input.close();
    } // public static void main(String[] args)

    // 1. INISIALISASI
    private static void inisialisasiMenu() {
        // MAKANAN NON-VEGETARIAN (Diperbaiki ejaannya)
        daftarMenu.tambahItem(new Makanan("Nasi Goreng Seafood", 30000, "Non-Vegetarian"));
        daftarMenu.tambahItem(new Makanan("Bakso", 20000, "Non-Vegetarian"));
        daftarMenu.tambahItem(new Makanan("Ayam Geprek Sambal Matah", 25000, "Non-Vegetarian"));
        // MAKANAN VEGETARIAN (Diperbaiki ejaannya)
        daftarMenu.tambahItem(new Makanan("Gado-Gado", 20000, "Vegetarian"));
        daftarMenu.tambahItem(new Makanan("Nasi Pecel", 20000, "Vegetarian"));
        daftarMenu.tambahItem(new Makanan("Ketoprak", 20000, "Vegetarian"));
        // MINUMAN KOPI
        daftarMenu.tambahItem(new Minuman("Kopi Espresso", 15000, "Kopi"));
        daftarMenu.tambahItem(new Minuman("Kopi Latte Dingin", 20000, "Kopi"));
        daftarMenu.tambahItem(new Minuman("Cappuccino Panas", 22000, "Kopi"));
        // MINUMAN JUS
        daftarMenu.tambahItem(new Minuman("Jus Mangga", 12000, "Jus"));
        daftarMenu.tambahItem(new Minuman("Jus Alpukat", 18000, "Jus"));
        daftarMenu.tambahItem(new Minuman("Jus Durian", 20000, "Jus"));
        // MINUMAN AIR MINERAL
        daftarMenu.tambahItem(new Minuman("Air Mineral Botol", 5000, "Air Mineral"));
        // DISKON (Harga disetel 0 untuk item Diskon)
        daftarMenu.tambahItem(new Diskon("Diskon Akhir Tahun", 0, 0.10, "Minimum Pembelian Rp 150.000"));
        daftarMenu.tambahItem(new Diskon("Promo Beli 2 Minuman", 0, 0.05, "Beli 2 Minuman apapun"));
    }

    // 2. VIEW
    private static void tampilkanMenuUtama() {
        System.out.println("                     SELAMAT DATANG DI RESTORAN JINHIT                      ");
        System.out.println("ﮩ٨ـﮩﮩ٨ـ♡ﮩ٨ـﮩﮩ٨ ______________________________________________ﮩ٨ـﮩﮩ٨ـ♡ﮩ٨ـﮩﮩ٨ـ");
        System.out.println("\n--- Pilih Mode ---");
        System.out.println("");
        System.out.println("1. Buat Pesanan (Mode Pelanggan)");
        System.out.println("2. Kelola Menu (Mode Admin)");
        System.out.println("0. Keluar dari Aplikasi");
        System.out.print("Masukkan pilihan Anda: ");
    }

    // 3. PROSES PEMESANAN (PELANGGAN)
    private static void prosesPemesanan() {
        // Diperlukan asumsi bahwa kelas Pesanan sudah ada
        Pesanan pesananBaru = new Pesanan();
        System.out.println("\n");
        System.out.println("");
        System.out.println("__________________________________________________________________________________");
        System.out.println("                                 MEMBUAT PESANAN BARU                       ");
        System.out.println("__________________________________________________________________________________");
        System.out.println("");
        

        // Tampilkan Daftar Menu Lengkap
        daftarMenu.tampilkanSemuaMenu();

        // Loop Pemesanan
        while (true) {
            System.out.print("Masukkan nomor menu (atau ketik 'selesai'): ");
            String inputPilihan = input.nextLine();

            if (inputPilihan.equalsIgnoreCase("selesai")) {
                if (pesananBaru.getItemDipesan().isEmpty()) {
                    System.out.println("Anda belum memesan apapun. Kembali ke menu utama.");
                    return;
                }
                break;
            }

            try {
                int noMenu = Integer.parseInt(inputPilihan);
                int index = noMenu - 1;

                if (index < 0 || index >= daftarMenu.getDaftarItem().size()) {
                    System.out.println("PERINGATAN: Nomor menu tidak valid.");
                    continue;
                }

                MenuItem menuPilihan = daftarMenu.getDaftarItem().get(index);

                // Untuk mencegah Diskon dipesan sebagai item
                if (menuPilihan instanceof Diskon) {
                    System.out.println("PERINGATAN: Item ini adalah PROMO, tidak bisa dipesan secara langsung.");
                    continue;
                }

                int qty = 0;
                do {
                    System.out.print("Masukkan jumlah untuk '" + menuPilihan.getNama() + "': ");
                    try {
                        qty = Integer.parseInt(input.nextLine());
                        if (qty <= 0) {
                            System.out.println("Peringatan: Jumlah harus lebih dari 0.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Peringatan: Input jumlah harus berupa angka.");
                        qty = 0;
                    }
                } while (qty <= 0);

                // Tambahkan pesanan ke objek Pesanan
                pesananBaru.tambahItem(menuPilihan, qty);
                System.out.println("...'" + menuPilihan.getNama() + " x" + qty + "' ditambahkan...");


            } catch (NumberFormatException e) {
                System.out.println("Peringatan: Input tidak valid. Masukkan nomor menu atau 'selesai'.");
            }
        } // while (true)

        // Cetak Struk dan selesai
        pesananBaru.cetakStruk(daftarMenu);
        System.out.println("...Tekan Enter untuk kembali ke menu utama...");
        input.nextLine(); // Menjeda layar
    }

    // 4. PROSES MANAJEMEN MENU
    private static void prosesManajemenMenu() {
        int pilihan = -1;
        do {
            System.out.println("");
            System.out.println("__________________________________________________________________________________");
            System.out.println("                         MANAJEMEN MENU RESTORAN JINHIT");
            System.out.println("__________________________________________________________________________________");
            System.out.println("");
            System.out.println("1. Tampilkan Semua Menu");
            System.out.println("2. Tambah Item Menu Baru");
            System.out.println("3. Ubah Harga Item");
            System.out.println("4. Hapus Item Menu");
            System.out.println("0. Kembali ke Menu Utama");
            System.out.print("Masukkan pilihan Anda: ");

            try {
                pilihan = Integer.parseInt(input.nextLine());

                switch (pilihan) {
                    case 1 -> daftarMenu.tampilkanSemuaMenu();
                    case 2 -> tambahItemMenu();
                    case 3 -> ubahHargaMenu();
                    case 4 -> hapusItemMenu();
                    case 0 -> System.out.println("...Kembali ke menu utama...");
                    default -> System.out.println("Peringatan: Pilihan tidak valid.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Peringatan: Input harus berupa angka!");
                pilihan = -1;
            }
        } while (pilihan != 0);
    } // private static void prosesManajemenMenu()

    private static void tambahItemMenu() {
        System.out.println("\n------------------------ TAMBAH ITEM MENU BARU ------------------------");
        System.out.print("Pilih tipe item (1: Makanan, 2: Minuman, 3: Diskon): ");
        String tipeInput = input.nextLine();

        try {
            int tipe = Integer.parseInt(tipeInput);
            System.out.print("Masukkan Nama Item: ");
            String nama = input.nextLine();

            // Menggunakan Switch Expression yang sudah dirapikan
            switch (tipe) {
                case 1, 2 -> {
                    double harga = 0;
                    while (harga <= 0) {
                        System.out.print("Masukkan Harga (angka): ");
                        try {
                            harga = Double.parseDouble(input.nextLine());
                            if (harga <= 0) System.out.println("Harga harus lebih dari 0.");
                        } catch (NumberFormatException e) {
                            System.out.println("Input harga tidak valid.");
                        }
                    }

                    if (tipe == 1) { // Makanan
                        String jenis = "";
                        while (!jenis.equalsIgnoreCase("Vegetarian") && !jenis.equalsIgnoreCase("Non-Vegetarian")) {
                            System.out.print("Jenis Makanan (Vegetarian / Non-Vegetarian): ");
                            jenis = input.nextLine();
                            if (!jenis.equalsIgnoreCase("Vegetarian") && !jenis.equalsIgnoreCase("Non-Vegetarian")) {
                                System.out.println("Peringatan: Jenis harus 'Vegetarian' atau 'Non-Vegetarian'.");
                            }
                        }
                        daftarMenu.tambahItem(new Makanan(nama, harga, jenis));
                        System.out.println("Sukses: Makanan '" + nama + "' ditambahkan.");
                    } else { // Minuman (tipe == 2)
                        String jenis = "";
                        while (!jenis.equalsIgnoreCase("Kopi") && !jenis.equalsIgnoreCase("Jus") && !jenis.equalsIgnoreCase("Air Mineral")) {
                            System.out.print("Jenis Minuman (Kopi / Jus / Air Mineral): ");
                            jenis = input.nextLine();
                            if (!jenis.equalsIgnoreCase("Kopi") && !jenis.equalsIgnoreCase("Jus") && !jenis.equalsIgnoreCase("Air Mineral")) {
                                System.out.println("Peringatan: Jenis harus 'Kopi', 'Jus', atau 'Air Mineral'.");
                            }
                        }
                        daftarMenu.tambahItem(new Minuman(nama, harga, jenis));
                        System.out.println("Sukses: Minuman '" + nama + "' ditambahkan.");
                    }
                } // case 1, 2 ditutup

                case 3 -> {
                    // Diskon
                    double diskon = 0;
                    while (diskon <= 0 || diskon >= 1) {
                        System.out.print("Persentase Diskon (Contoh 0.05 untuk 5%): ");
                        try {
                            diskon = Double.parseDouble(input.nextLine());
                            if (diskon <= 0 || diskon >= 1) System.out.println("Diskon harus antara 0 dan 1 (eksklusif).");
                        } catch (NumberFormatException e) {
                            System.out.println("Input diskon tidak valid.");
                        }
                    }
                    System.out.print("Deskripsi/Syarat Diskon: ");
                    String deskripsi = input.nextLine();
                    daftarMenu.tambahItem(new Diskon(nama, 0, diskon, deskripsi));
                    System.out.println("Sukses: Diskon '" + nama + "' ditambahkan.");
                } // case 3 ditutup

                default -> System.out.println("Peringatan: Pilihan tipe tidak valid.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Peringatan: Pilihan tipe harus berupa angka.");
        }
    } // private static void tambahItemMenu()

    private static void ubahHargaMenu() {
        System.out.println("\n--- UBAH HARGA ITEM MENU ---");
        if (daftarMenu.getDaftarItem().isEmpty()) {
            System.out.println("Tidak ada menu untuk diubah.");
            return;
        }
        daftarMenu.tampilkanSemuaMenu();

        int noMenu = -1;
        int index = -1;

        while (index < 0 || index >= daftarMenu.getDaftarItem().size()) {
            System.out.print("Masukkan nomor menu yang harganya ingin diubah: ");
            try {
                noMenu = Integer.parseInt(input.nextLine());
                index = noMenu - 1;

                if (index < 0 || index >= daftarMenu.getDaftarItem().size()) {
                    System.out.println("Peringatan: Nomor menu tidak valid.");
                    index = -1; // Ulangi loop
                } else if (daftarMenu.getDaftarItem().get(index) instanceof Diskon) {
                    System.out.println("Peringatan: Item Diskon tidak memiliki harga yang bisa diubah.");
                    index = -1; // Ulangi loop
                }
            } catch (NumberFormatException e) {
                System.out.println("Peringatan: Input harus berupa angka.");
                index = -1; // Ulangi loop
            }
        }

        double hargaBaru = 0;
        while (hargaBaru <= 0) {
            System.out.print("Masukkan harga baru (angka): ");
            try {
                hargaBaru = Double.parseDouble(input.nextLine());
                if (hargaBaru <= 0) {
                    System.out.println("Peringatan: Harga harus lebih dari 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Peringatan: Harga harus berupa angka.");
                hargaBaru = 0;
            }
        }

        if (daftarMenu.ubahHargaItem(index, hargaBaru)) {
            // Menggunakan String.format untuk format angka yang lebih baik
            System.out.printf("Sukses: Harga '%s' berhasil diubah menjadi Rp %,.0f.%n", 
                                daftarMenu.getDaftarItem().get(index).getNama(), hargaBaru);
        } else {
            System.out.println("Gagal mengubah harga.");
        }
    } // private static void ubahHargaMenu()

    private static void hapusItemMenu() {
        System.out.println("\n--- HAPUS ITEM MENU ---");
        if (daftarMenu.getDaftarItem().isEmpty()) {
            System.out.println("Tidak ada menu untuk dihapus.");
            return;
        }
        daftarMenu.tampilkanSemuaMenu();

        int noMenu = -1;
        int index = -1;

        while (index < 0 || index >= daftarMenu.getDaftarItem().size()) {
            System.out.print("Masukkan nomor menu yang ingin dihapus: ");
            try {
                noMenu = Integer.parseInt(input.nextLine());
                index = noMenu - 1;

                if (index < 0 || index >= daftarMenu.getDaftarItem().size()) {
                    System.out.println("Peringatan: Nomor menu tidak valid.");
                    index = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Peringatan: Input harus berupa angka.");
                index = -1;
            }
        }

        String namaHapus = daftarMenu.getDaftarItem().get(index).getNama();

        System.out.print("Anda yakin ingin menghapus '" + namaHapus + "'? (Ya/Tidak): ");
        String konfirmasi = input.nextLine();

        if (konfirmasi.equalsIgnoreCase("Ya")) {
            if (daftarMenu.hapusItem(index)) {
                System.out.println("Sukses: Menu '" + namaHapus + "' berhasil dihapus.");
            } else {
                System.out.println("Gagal menghapus menu.");
            }
        } else {
            System.out.println("...Penghapusan menu dibatalkan...");
        }
    } // private static void hapusItemMenu()
} // public class RestoranApp