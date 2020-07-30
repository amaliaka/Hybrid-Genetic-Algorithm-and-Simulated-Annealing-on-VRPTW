/*
 * this is a program optimation touring in Banyuwangi
 */
package optimasi_rute_wisata;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author amaliaka
 */
public class Optimasi_Rute_Wisata {

    private double[][] waktu_tempuh_wisata = new double[19][19]; // dalam satuan jam
    private double[][] waktu_tempuh_hotel = new double[14][19]; // dalam satuan jam
    private double[][] time_windows = new double[19][2]; // dalam satuan pukul

    public static void main(String[] args) throws IOException, BiffException {
        double start = System.nanoTime();
        // memasukan data dari xls ke array
        Optimasi_Rute_Wisata opw = new Optimasi_Rute_Wisata();
        opw.masukan();
        //inisialisasi indeks hotel 7 = urutan hotel 8
        int no_hotel = 7;
        // memanggil kelas
        int pilih_run = 1;
        if (pilih_run == 0){
            for (int i = 0; i < 10; i++) {
                Algoritma_Genetika ag = new Algoritma_Genetika(opw.waktu_tempuh_wisata, opw.waktu_tempuh_hotel, opw.time_windows, no_hotel);
            }
        }
        else if(pilih_run == 1){
            Algoritma_Genetika ag = new Algoritma_Genetika(opw.waktu_tempuh_wisata, opw.waktu_tempuh_hotel, opw.time_windows, no_hotel);
        }
        else if(pilih_run == 3){
            GA ga = new GA(opw.waktu_tempuh_wisata, opw.waktu_tempuh_hotel, opw.time_windows, no_hotel);
        }
        else{
            SA sa = new SA(opw.waktu_tempuh_wisata, opw.waktu_tempuh_hotel, opw.time_windows, no_hotel);
        }
        double finish = System.nanoTime();
        System.out.println("Time: "+(finish-start)* 1.0e-9);
    }

    // masakukan nilai-nilai dari rute
    public void masukan() throws IOException, BiffException {
        File f = new File("C:\\Users\\amaliaka\\Dropbox\\ALEV + SKRIPSI\\ALEV\\data wisata.xls");
        // membaca workbooknya
        Workbook w = Workbook.getWorkbook(f);
        /*
         * Memasukan nilai waktu tempuh wisata
         * pada sheet = 0
         */
        Sheet sheet = w.getSheet(0);
        int k = 0, t = 0;
        // merujuk pada kolom [3 21]
        for (int i = 3; i <= 21; i++) {
            // merujuk pada baris [23 - 41]
            for (int j = 23; j <= 41; j++) {
                Cell cell = sheet.getCell(i, j);
                waktu_tempuh_wisata[k][t++] = Double.valueOf(cell.getContents());
            }
            k++;
            t = 0;
        }
        /*
         * Memasukan nilai waktu tempuh hotel ke wisata
         * pada sheet = 1
         */
        k = 0;
        t = 0;
        Sheet sheet1 = w.getSheet(1);
        // merujuk pada baris [19 - 32]
        for (int j = 19; j <= 32; j++) {
            // merujuk pada kolom [3 21]
            for (int i = 3; i <= 21; i++) {
                Cell cell1 = sheet1.getCell(i, j);
                waktu_tempuh_hotel[k][t++] = Double.valueOf(cell1.getContents());
            }
            k++;
            t = 0;
        }
        /*
         * Memasukan nilai time windows
         */
        k = 0;
        t = 0;
        // merujuk pada baris [1 19]
        for (int j = 1; j <= 19; j++) {
            // merujuk pada column [2 3]
            for (int i = 2; i <= 3; i++) {
                Sheet sheet2 = w.getSheet(2);
                Cell cell2 = sheet2.getCell(i, j);
                time_windows[k][t++] = Double.valueOf(cell2.getContents());
            }
            k++;
            t = 0;
        }
    }
}