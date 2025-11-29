/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Aplikasi Restoran: Kelas-kelas Pendukung (MenuItem, Makanan, Minuman, Diskon, Menu)
 * Dibuat oleh: dzikr
 */
package restoranapp;

import java.util.ArrayList;
import java.util.List;
// Import InputMismatchException tidak diperlukan di file ini
// Import Scanner tidak diperlukan di file ini

//Kelas Abstrak Induk untuk semua item dalam menu.
//Menyediakan properti dasar dan metode polimorfik.
abstract class MenuItem {
    protected String nama;
    protected double harga;
    protected String tipe; // Contoh: Makanan, Minuman, Diskon

    public MenuItem(String nama, double harga, String tipe) {
        this.nama = nama;
        this.harga = harga;
        this.tipe = tipe;
    }

    // Getters
    public String getNama() { return nama; }
    public double getHarga() { return harga; }

    // Setter (Digunakan di mode Admin untuk ubah harga)
    public void setHarga(double harga) { this.harga = harga; }
    
    // Metode abstrak untuk Polimorfisme (wajib diimplementasikan oleh turunan)
    public abstract void tampilMenu();
}

// --------------------------------------------------------------------------

//Kelas Makanan - Turunan dari MenuItem.
class Makanan extends MenuItem {
    private String jenisMakanan; // Jenis: Vegetarian atau Non-Vegetarian

    public Makanan(String nama, double harga, String jenisMakanan) {
        // Memanggil konstruktor kelas induk
        super(nama, harga, "Makanan");
        // Diperbaiki ejaannya di RestoranApp, di sini cukup simpan
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        // Menggunakan printf untuk format output yang rapi
        System.out.printf("%-20s (Rp %,.0f) | Jenis: %s\n", nama, harga, jenisMakanan);
    }
    
    // Getter tambahan (PENTING untuk pengelompokan menu)
    public String getJenisMakanan() {
        return jenisMakanan;
    }
}

// --------------------------------------------------------------------------

//Kelas Minuman - Turunan dari MenuItem.
class Minuman extends MenuItem {
    private String jenisMinuman; // Jenis: Kopi, Jus, atau Air Mineral

    public Minuman(String nama, double harga, String jenisMinuman) {
        // Memanggil konstruktor kelas induk
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        // Menggunakan printf untuk format output yang rapi
        System.out.printf("%-20s (Rp %,.0f) | Jenis: %s\n", nama, harga, jenisMinuman);
    }
    
    // Getter tambahan (PENTING untuk pengelompokan menu)
    public String getJenisMinuman() {
        return jenisMinuman;
    }
}

// --------------------------------------------------------------------------

//Kelas Diskon - Turunan dari MenuItem (Harga selalu 0).
class Diskon extends MenuItem {
    private double persentaseDiskon;
    private String deskripsi;

    public Diskon(String nama, double harga, double persentaseDiskon, String deskripsi) {
        // Mengabaikan harga yang dimasukkan, disetel ke 0 karena ini promosi
        super(nama, 0, "Diskon");
        this.persentaseDiskon = persentaseDiskon;
        this.deskripsi = deskripsi;
    }

    @Override
    public void tampilMenu() {
        // Menampilkan persentase diskon dalam format %
        System.out.printf("%-20s | Diskon: %.0f%% | Syarat: %s\n", nama, (persentaseDiskon * 100), deskripsi);
    }

    // Getters
    public double getPersentaseDiskon() {
        return persentaseDiskon;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}

// --------------------------------------------------------------------------

//Kelas Menu - Untuk pengelolaan daftar item dan tampilan menu.
public class Menu {
    private List<MenuItem> daftarItem; // ArrayList untuk menyimpan semua item menu

    public Menu() {
        this.daftarItem = new ArrayList<>();
    }

    // ENKAPSULASI: Metode untuk menambah item
    public void tambahItem(MenuItem item) {
        this.daftarItem.add(item);
    }

    // ENKAPSULASI: Metode untuk mendapatkan daftar item (hanya untuk dibaca/iterasi)
    public List<MenuItem> getDaftarItem() {
        return daftarItem;
    }

    // =========================================================================
    // METODE PERBAIKAN: tampilkanSemuaMenu() (Mengelompokkan menu)
    // =========================================================================
    public void tampilkanSemuaMenu() {
        // Mulai nomor urut global dari 1
        int nomorUrutGlobal = 1;

        // Tampilkan dan perbarui nomor urut untuk setiap kategori
        nomorUrutGlobal = tampilkanKategori(nomorUrutGlobal, "MAKANAN NON-VEGETARIAN", "Non-Vegetarian");
        nomorUrutGlobal = tampilkanKategori(nomorUrutGlobal, "MAKANAN VEGETARIAN", "Vegetarian");
        nomorUrutGlobal = tampilkanKategori(nomorUrutGlobal, "MINUMAN KOPI", "Kopi");
        nomorUrutGlobal = tampilkanKategori(nomorUrutGlobal, "MINUMAN JUS", "Jus");
        nomorUrutGlobal = tampilkanKategori(nomorUrutGlobal, "MINUMAN AIR MINERAL", "Air Mineral");
        
        // Tampilkan Promosi (Diskon)
        tampilkanPromosi(nomorUrutGlobal);
        System.out.println("");
    }
    
    // Mengambil nomor awal (startNum) dan mengembalikan nomor akhir
    private int tampilkanKategori(int nomorAwal, String judul, String jenis) {
        int nomorUrut = nomorAwal;
        
        // Header kategori
        System.out.println("\n----------------------------------------------------------------------------------");
        System.out.printf("%s                    %n", judul);
        System.out.println("");

        for (int i = 0; i < daftarItem.size(); i++) {
            MenuItem item = daftarItem.get(i);
            
            // Logika filtering untuk Makanan atau Minuman
            if ((item instanceof Makanan && ((Makanan)item).getJenisMakanan().equalsIgnoreCase(jenis)) || 
                (item instanceof Minuman && ((Minuman)item).getJenisMinuman().equalsIgnoreCase(jenis))) {
                
                // Menggunakan nomor urut global dan menambahkan
                System.out.printf("%2d. ", nomorUrut++);
                item.tampilMenu(); 
            }
        }
        if (nomorUrut == nomorAwal) { // Jika nomorUrut tidak bertambah
            System.out.println("                   (Tidak ada item dalam kategori ini)                     ");
        }
        
        // Mengembalikan nomor urut setelah item terakhir
        return nomorUrut;
    }
        
    
    // METODE BANTUAN 2: Menampilkan Diskon (Hanya perlu menyesuaikan header)
    private void tampilkanPromosi(int nomorAwal) {
        int nomorUrut = nomorAwal; // Melanjutkan dari nomor terakhir
        System.out.println("");
        System.out.println("");
        System.out.printf("PROMOSI & DISKON:");
        System.out.println("");
        for (int i = 0; i < daftarItem.size(); i++) {
            MenuItem item = daftarItem.get(i);
            
            if (item instanceof Diskon) {
                System.out.printf("", nomorUrut++);
                item.tampilMenu(); 
            }
        }
        
        if (nomorUrut == nomorAwal) {
            System.out.println("                   (Tidak ada promosi aktif saat ini)                     ");
        }
    }
    
    // Jika ingin mengubah harga berdasarkan index
    public boolean ubahHargaItem(int index, double hargaBaru) {
        if (index >= 0 && index < daftarItem.size()) {
            MenuItem item = daftarItem.get(index);
            // Hanya makanan dan minuman yang diizinkan di ubah harganya
            if (item instanceof Makanan || item instanceof Minuman) {
                item.setHarga(hargaBaru);
                return true;
            } else {
                // Pesan peringatan ini sebenarnya sudah ditangani di RestoranApp.java
                return false; 
            }
        }
        return false;
    }

    // Jika ingin menghapus item berdasarkan index
    public boolean hapusItem(int index) {
        if (index >= 0 && index < daftarItem.size()) {
            daftarItem.remove(index);
            return true;
        }
        return false;
    }
}