/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Kelas Pesanan.java
 * Versi Final dengan Pemisahan Output Diskon.
 */
package restoranapp;

import java.util.LinkedHashMap;
import java.util.Map;

// KELAS [PESANAN] = UNTUK MENCATAT ITEM DAN JUMLAH YANG DIPESAN.
public class Pesanan {
    // LinkedHashMap untuk menjaga urutan penambahan item.
    private Map<MenuItem, Integer> itemDipesan;

    public Pesanan(){
        this.itemDipesan = new LinkedHashMap<>();
    }

    
    //Menambahkan item menu ke dalam pesanan.
    //Menggunakan Item Name sebagai kunci unik untuk mengupdate kuantitas.
    
    public void tambahItem(MenuItem item, int jumlah){
        // Mencari item yang sudah ada berdasarkan NAMA item (kunci unik)
        MenuItem existingItem = itemDipesan.keySet().stream()
            .filter(i -> i.getNama().equalsIgnoreCase(item.getNama()))
            .findFirst()
            .orElse(null);

        if (existingItem != null) {
            // Jika sudah ada, update jumlahnya
            itemDipesan.put(existingItem, itemDipesan.get(existingItem) + jumlah);
        } else {
            // Jika belum ada, tambahkan item baru
            itemDipesan.put(item, jumlah);
        }
    }

    
    //Menghitung total biaya sebelum pajak dan diskon.
    public double hitungSubTotal() {
        double subTotal = 0;
        for (Map.Entry<MenuItem, Integer> entry : itemDipesan.entrySet()){
            if (!(entry.getKey() instanceof Diskon)) {
                subTotal += entry.getKey().getHarga() * entry.getValue();
            }
        }
        return subTotal;
    } 

    
    //Mencetak struk pembayaran, termasuk perhitungan pajak, biaya layanan, dan diskon.
    
    public void cetakStruk(Menu daftarMenu) {
        double subTotal = hitungSubTotal();
        double pajak = subTotal * 0.10; // 10%
        int biayaPelayanan = 20000;

        // --- 1. PENGUMPULAN DATA UNTUK DISKON ---
        int jumlahMinuman = 0;
        double totalHargaMinuman = 0;

        for (Map.Entry<MenuItem, Integer> entry : itemDipesan.entrySet()) {
            MenuItem item = entry.getKey();
            int qty = entry.getValue();

            if (item instanceof Minuman) {
                jumlahMinuman += qty;
                totalHargaMinuman += item.getHarga() * qty;
            }
        }

        // --- 2. PENERAPAN LOGIKA DISKON ---
        double diskonAkhirTahunPotongan = 0;
        double diskonMinumanPotongan = 0;

        for (MenuItem item : daftarMenu.getDaftarItem()) {
            if (item instanceof Diskon) {
                Diskon d = (Diskon) item;

                // A. Diskon Akhir Tahun (Minimum Pembelian Rp 150.000)
                if (subTotal >= 150000 && d.getDeskripsi().contains("150.000")) {
                    diskonAkhirTahunPotongan = subTotal * d.getPersentaseDiskon();
                }

                // B. Promo Beli 2 Minuman (Kuantitas >= 2)
                if (jumlahMinuman >= 2 && d.getDeskripsi().contains("Beli 2 Minuman apapun")) {
                    // Diskon hanya berlaku untuk total harga MINUMAN
                    diskonMinumanPotongan = totalHargaMinuman * d.getPersentaseDiskon();
                }
            }
        }

        double totalDiskon = diskonAkhirTahunPotongan + diskonMinumanPotongan; 
        double totalAkhir = subTotal + pajak + biayaPelayanan - totalDiskon;

        // --- 3. MENCETAK STRUK ---
        
        System.out.println("\n=============================================");
        System.out.println("            STRUK PEMBAYARAN - JIN HIT           ");
        System.out.println("=============================================");
        System.out.println("\nRINCIAN PESANAN :");
        
        for (Map.Entry<MenuItem, Integer> entry : itemDipesan.entrySet()) {
            MenuItem item = entry.getKey();
            int qty = entry.getValue();
            double itemTotal = item.getHarga() * qty;
            System.out.printf("%-20s x%d \t = Rp %,.0f\n", item.getNama(), qty, itemTotal);
        }
        
        System.out.println("---------------------------------------------");
        System.out.printf("SUBTOTAL (Bruto)   \t = Rp %,.0f\n", subTotal);
        System.out.printf("Pajak (10%%)        \t = +Rp %,.0f\n", pajak);
        System.out.printf("Biaya Pelayanan    \t = +Rp %,d\n", biayaPelayanan);

        // Mencetak Diskon Akhir Tahun (Jika ada)
        if (diskonAkhirTahunPotongan > 0) {
             System.out.printf("Diskon Akhir Tahun (10%%)\t = -Rp %,.0f\n", diskonAkhirTahunPotongan);
        }

        // Mencetak Diskon Minuman (Jika ada)
        if (diskonMinumanPotongan > 0) {
             System.out.printf("Promo Beli 2 Minuman (5%%)\t = -Rp %,.0f\n", diskonMinumanPotongan);
        }

        // Mencetak Total Diskon Kumulatif
        if (totalDiskon > 0) {
            System.out.println("---------------------------------------------");
            System.out.printf("TOTAL DISKON PROMO \t = -Rp %,.0f\n", totalDiskon);
        }

        System.out.println("---------------------------------------------");
        System.out.printf("TOTAL PEMBAYARAN   \t = Rp %,.0f\n", totalAkhir);
        System.out.println("=============================================");
    }

    // Getter untuk item yang dipesan (Untuk prosesPemesanan di RestoranApp)
    public Map<MenuItem, Integer> getItemDipesan() {
        return itemDipesan;
    }
}