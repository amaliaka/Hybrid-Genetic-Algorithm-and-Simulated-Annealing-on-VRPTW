package optimasi_rute_wisata;

/**
 *
 * @author amalia
 */
public class NewClass1 {

    public static void main(String[] args) {
        int[] S = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int[] new_S = new int[S.length];
        // bangkitkan dua titik q dan r
        int pp = 0;
        int q = 0; // batas depan
        int r = 0; // batas belakang
        while (pp != 1) {
            Double qq = new Double(Math.random() * (S.length));
            q = qq.intValue();
            System.out.println("q" +q);
            Double rr = new Double(Math.random() * (S.length));
            r = rr.intValue();
            System.out.println("r" +r);
            if (r > q) {
                pp = 1;
            }
        }
        System.out.println(" Q: " + q + " R: " + r);
        int[] kondisi = new int[1 + r - q];
        for (int i = 0; i < kondisi.length; i++) {
            kondisi[i] = 999;
        }
        int s = 0;
        for (int i = 0; i < new_S.length; i++) {
            if (i >= q && i <= r) {
                // random
                int boll = 0;
                int kk = 0;
                while (boll != 1) {
                    Double k = Math.random() * (1 + r - q) + q;
                    kk = k.intValue();
                    for (int j = 0; j < kondisi.length; j++) {
                        if (kondisi[j] == kk) {
                            break;
                        }
                        if (j == kondisi.length - 1) {
                            boll = 1;
                            kondisi[s++] = kk;
                        }
                    }
                }
                int nilai = S[kk];
                new_S[i] = nilai;
            } else {
                new_S[i] = S[i];
            }
        }
        for (int i = 0; i < S.length; i++) {
            System.out.print(" " + S[i]);
        }
        System.out.println();
        for (int i = 0; i < new_S.length; i++) {
            System.out.print(" " + new_S[i]);
        }
        System.out.println();
        System.out.println(kondisi(new_S));
    }

    public static boolean kondisi(int[] chromosom) {
        boolean kondisi = false;
        for (int i = 0; i < chromosom.length - 2; i++) {
            for (int j = i + 1; j < chromosom.length - 1; j++) {
                if (chromosom[i] == chromosom[j]) {
                    kondisi = true;
                    break;
                }
            }
        }
        return kondisi;
    }
}
