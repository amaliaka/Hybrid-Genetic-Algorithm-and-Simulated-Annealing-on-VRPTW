package optimasi_rute_wisata;

import java.util.Arrays;

/**
 *
 * @author amaliaka
 */
public class Chromosom {

    private int[][] chromosom;

    // membuat chromosom
    public void create(int n) {
        this.chromosom = new int[n][20];
    }

    // membangkitkan nilai gen tiap chromosom sebanyak n
    public int[][] inisialisasi(int no_hotel) {
        for (int i = 0; i < chromosom.length; i++) {
            int[] hasil = new int[20];
            int ii = 0, x = 0;
            // membangkitkan segmen pertama secara random
            while (ii < 19) {
                // batasan nilai [1 19]
                double random = Math.random() * (20 - 0) + 0;
                Double r = new Double(random);
                int tanda = 0;
                for (int j = 0; j < hasil.length; j++) {
                    if (r.intValue() == hasil[j]) {
                        tanda++;
                    }
                }
                if (tanda == 0) {
                    hasil[ii++] = r.intValue();
                }
                tanda = 0;
            }
            // membangkitkan segmen kedua secara random
            // inisialisasi nilai no. hotel
            hasil[hasil.length - 1] = no_hotel;
            
            // mengurangi index wisata dan hotel dengan 1
            for (int j = 0; j < hasil.length; j++) {
                if (hasil[j] == 0) {

                } else {
                    hasil[j] -= 1;
                }
            }
            chromosom[i] = hasil;
        }
        return chromosom;
    }
}
