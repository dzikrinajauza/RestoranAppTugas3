/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restoranapp;;

/**
 *
 * @author dzikr
 */
// Buat kelas abstrak untuk menjadi vondasi bagi semua item menu(makanan, minuman, diskon)
public abstract class MenuItem {
    protected String nama; //pakai protected supaya bisa diakses langsung oleh subkelas
    protected double harga;
    protected String kategori;
    
    //kontruktor utam
    public MenuItem(String nama, double harga, String kategori ){
        this.nama = nama; 
        this.harga = harga;
        this.kategori = kategori;
    } //public MenuItem
        
    //Metode absract harus di implementasikan oleh semua subkelas
    public abstract void tampilMenu();
    
    // ENCAPSULASI (Gettet) untuk nama, harga, kategori
    public String getNama(){
        return nama;
    } //public string getNama
    
    public double getHarga(){
        return harga;
    } //public double getHarga
    
    public String getKategori(){
        return kategori;
    } //public String getKategori   
    
    // ENCAPSULASI (Setter) hanya untuk harga
    public void setHarga (double harga){
        this.harga = harga;
    } //public void setHarga
    
}//class menuItem
