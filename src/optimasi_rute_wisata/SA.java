/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimasi_rute_wisata;

import java.util.Arrays;

/**
 *
 * @author amaliaka
 */
public class SA {
    private double[][] time_wisata; // titik satu dengan dua [wisata i][wisata j]
    private double[][] time_hotel;  // titik hotel dan wisata [hotel i][hotel j]
    private double[][] time_windows; // waktu buka pariwisata
    private int[][] chromosom;
    private double[][] fitness_chromosom;
    //private final boolean replacement_selection = true; // tanda untuk seleksi replacement
    private int no_hotel;

    // method yang menjalankan proses algoritma genetika
    public SA(double[][] time_wisata, double[][] time_hotel, double[][] time_windows, int no_hotel) {
        this.no_hotel = no_hotel;
        this.time_wisata = time_wisata;
        this.time_hotel = time_hotel;
        this.time_windows = time_windows;
        main(no_hotel);
    }

    // method untuk menjalankan program
    public void main(int no_hotel) {
        int pp = 0;
        SA(pp);
    }

    // method untuk SA
    public void SA(int p) {
//        double start = System.nanoTime();
        // chromosome
        int[] S = {3, 8, 13, 4, 18, 9, 10, 16, 5, 6, 11, 12, 17, 15, 1, 2, 14, 7, 0, 6}; // solusi awal
        int[] Sn = new int[S.length];
        // temperature
        double temp0 = 0.9;
        double temp1 = 0.01;
        double t = temp0;
        double cooling_factor = 0.9;
        double total_time = 0;
        // fitness
        System.out.println("Kromosom Awal");
        System.out.println(Arrays.toString(S));
        System.out.println("Fitness awal = " + fitness(S));
        System.out.println("================================================");
        double d2 = 0; // fitness dari Sn
        // proses SA
//        while (total_time < 60.0) {
        while (t >= temp1) {
            double d1 = fitness(S); // fitness dari S
            Sn = pembangkitan_chromosome_SA(S, p);
            // hitung d2
            d2 = fitness(Sn);
            System.out.println("Suhu = " + t);
            System.out.println("d1 = " + d1 + " d2 = " + d2);
            // kondisi
            if (d2 > d1) {
                S = Sn;

            } else {
                int k = 200;
                double prob = 1 / (Math.exp(((d1 - d2) * k) / (t)));
                // kondisi d2
                double alpa = Math.random();
                System.out.println("Alpha = " + alpa);
                if (alpa < prob) {
                    S = Sn;
                }
            }
            System.out.println(Arrays.toString(S));
            System.out.println("Fitness Sn = " + fitness(S));
            System.out.println("================================================");
//            if  (t > 0.01) {
//                t = t * cooling_factor;
//            }
//            else {
//                t = 0.01;
//            }
//            System.out.println(fitness(S));
//            double finish = System.nanoTime();
//            total_time = (finish-start)* 1.0e-9;
            t = t * cooling_factor;
        }
        System.out.println(fitness(S));
        fitness(S, true);
        System.out.println(Arrays.toString(S));
    }

    // method untuk membangkitkan nilai Sn
    public int[] pembangkitan_chromosome_SA(int[] S, int p) {
        int[] chromosome = new int[S.length];
        int kondisi = 1; // variabel untuk pemilihan operator SA: 1 = scramble mutation
        //int kk = 0;
        //while (kondisi(p) && kk == 0) {
        if (kondisi == 0) {
            chromosome = random_mutation();
        } else if (kondisi == 1) {
            chromosome = scramble_mutation(S);
        }
        return chromosome;
    }

    // membangkitkan nilai gen tiap chromosom sebanyak n
    public int[] random_mutation() {
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
        // mengurangi dengan 1
        for (int j = 0; j < hasil.length; j++) {
            if (hasil[j] == 0) {

            } else {
                hasil[j] -= 1;
            }
        }
        return hasil;
    }

    // method untuk melakukan scramble mutation
    public int[] scramble_mutation(int[] S) {
        int[] new_S = new int[S.length];
        // bangkitkan dua titik q dan r
        int pp = 0;
        int q = 0; // batas depan
        int r = 0; // batas belakang
        while (pp != 1) {
            Double qq = new Double(Math.random() * (S.length - 1));
            q = qq.intValue();
            Double rr = new Double(Math.random() * (S.length - 1));
            r = rr.intValue();
            if (r > q) {
                pp = 1;
            }
        }
//        System.out.println("Batas Awal = " + q);
//        System.out.println("Batas Akhir = " + r);
        int[] kondisi = new int[1 + r - q];// array baru dengan batas q dan r
        for (int i = 0; i < kondisi.length; i++) {
            kondisi[i] = 999;//inisialisasi biasa
        }
        int s = 0;
        for (int i = 0; i < new_S.length; i++) {
            if (i >= q && i <= r) {
                // random
                int boll = 0;
                int kk = 0;
                while (boll != 1) {
                    Double k = Math.random() * (1 + r - q) + q; //random index gen dalam range
                    kk = k.intValue();
                    for (int j = 0; j < kondisi.length; j++) {
                        if (kondisi[j] == kk) { // melihat angka yang sama dlm kromosom
                            break;
                        }
                        if (j == kondisi.length - 1) {
                            boll = 1;//kondisi selesai
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
        return new_S;
    }
   
    // menghitung nilai fitness
    public double fitness(int[] gen) {
        double hasil = 0;
        double time = 0; // variabel lama tempuh
        double penalty = 0;
        // menghitung nilai time tiap chromosom
        // menghitung waktu tempuh 
        int tt = 0;
        // looping wisata
        double timer = 5;
        int indek_wisata = 0;
        // looping keseluruhan chromosome hari pertama
        // untuk hari pertama hotel ke wisata ke-0
        //System.out.println("    HARI PERTAMA");
        String S_time = String.valueOf(time);
        String S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[0]]);
        time = tambah(S_time, S_time_hotel);
        //System.out.println("time awal = " + time);
        String S_timer = String.valueOf(timer);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("timer awal = " + timer);
        if (gen[0] == 12) {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "5.00");
        } else {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "3.00");
        }
        //System.out.println("timer kedua = " + timer);
        indek_wisata++;
        for (int i = 0; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i]][gen[i + 1]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            indek_wisata++;
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            // lihat waktu
            if (timer > 14.45) {
                break;
            }
        }
        //System.out.println("timer hari pertama 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("Time hotel: " + S_time_hotel);
        //System.out.println("Timer: " + timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println("timer hari pertama = " + timer);
        // untuk hari kedua hotel ke wisata ke-1
        timer = 1;
        //System.out.println("    HARI KEDUA");
        //System.out.println("time awal = " + timer);
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            //System.out.println("Timer awal: " + timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            //System.out.println(S_time_wisata);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("-penalty = " + penalty);
            }
            indek_wisata++;
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            if (timer > 14.45) {
                break;
            }
        }
        // System.out.println("timer hari kedua 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        // penalty
        //System.out.println("timer hari kedua = " + timer);
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println(indek_wisata + " " + gen[indek_wisata]);
        // untuk hari ketiga hotel ke wisata ke-
        //System.out.println("    HARI KETIGA");
        timer = 5;
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("waktu awal: " + timer);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        //System.out.println("waktu akhir: " + timer);
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println(S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][0]);
                timer = tambah(S_timer, String.valueOf(kurang(S_time_windows, S_timer)));
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            indek_wisata++;
            if (timer > 14.45) {
                break;
            }
        }
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println(gen[indek_wisata-1]+" "+(indek_wisata-1)+" "+S_time_hotel);
        //System.out.println("Timer Hotel: "+timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
        }
        double aa = indek_wisata;
        hasil = (1 / (1 + time)) + (penalty * (-1)) + (aa / 10);
        return hasil;
    }

    // menghitung nilai fitness
    public double fitness(int[] gen, boolean tanda) {
        double hasil = 0;
        double time = 0; // variabel lama tempuh
        double penalty = 0;
        // menghitung nilai time tiap chromosom
        // menghitung waktu tempuh 
        int tt = 0;
        // looping wisata
        double timer = 5;
        int indek_wisata = 0;
        // looping keseluruhan chromosome hari pertama
        // untuk hari pertama hotel ke wisata ke-0
        //System.out.println("    HARI PERTAMA");
        String S_time = String.valueOf(time);
        String S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[0]]);
        time = tambah(S_time, S_time_hotel);
        //System.out.println("time awal = " + time);
        String S_timer = String.valueOf(timer);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("timer awal = " + timer);
        if (gen[0] == 12) {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "5.00");
        } else {
            S_timer = String.valueOf(timer);
            timer = tambah(S_timer, "3.00");
        }
        //System.out.println("timer kedua = " + timer);
        indek_wisata++;
        for (int i = 0; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i]][gen[i + 1]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            indek_wisata++;
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            // lihat waktu
            if (timer > 14.45) {
                break;
            }
        }
        //System.out.println("timer hari pertama 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("Time hotel: " + S_time_hotel);
        //System.out.println("Timer: " + timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println("timer hari pertama = " + timer);
        // untuk hari kedua hotel ke wisata ke-1
        timer = 1;
        //System.out.println("    HARI KEDUA");
        //System.out.println("time awal = " + timer);
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            //System.out.println("Timer awal: " + timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            //System.out.println(S_time_wisata);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                timer = (time_windows[gen[i]][0]);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("-penalty = " + penalty);
            }
            indek_wisata++;
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            if (timer > 14.45) {
                break;
            }
        }
        // System.out.println("timer hari kedua 1 = " + timer);
        // kembali ke hotel
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        // penalty
        //System.out.println("timer hari kedua = " + timer);
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
            //System.out.println("penalty = " + penalty);
        }
        //System.out.println(indek_wisata + " " + gen[indek_wisata]);
        // untuk hari ketiga hotel ke wisata ke-
        //System.out.println("    HARI KETIGA");
        timer = 5;
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println("waktu awal: " + timer);
        if (timer < time_windows[gen[indek_wisata]][0]) {
            timer = (time_windows[gen[indek_wisata]][0]);
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        } else {
            if (gen[indek_wisata] == 12) {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "5.00");
            } else {
                S_timer = String.valueOf(timer);
                timer = tambah(S_timer, "3.00");
            }
        }
        //System.out.println("waktu akhir: " + timer);
        indek_wisata++;
        for (int i = indek_wisata; i < gen.length; i++) {
            // untuk waktu selanjutkanya
            S_time = String.valueOf(time);
            S_timer = String.valueOf(timer);
            String S_time_wisata = String.valueOf(time_wisata[gen[i - 1]][gen[i]]);
            time = tambah(S_time, S_time_wisata);
            timer = tambah(S_timer, S_time_wisata);
            //System.out.println(S_time_wisata);
            //System.out.println("Timer 1" + " " + i + ": " + timer);
            // lama berwisata 3 jam
            boolean kondisi = false;
            if (timer > time_windows[gen[i]][1]) {
                kondisi = true;
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
                //System.out.println("penalty = " + penalty);
            } else if (timer < time_windows[gen[i]][0]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][0]);
                timer = tambah(S_timer, String.valueOf(kurang(S_time_windows, S_timer)));
                if (gen[i + 1] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            } else {
                if (gen[i] == 12) {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "5.00");
                } else {
                    S_timer = String.valueOf(timer);
                    timer = tambah(S_timer, "3.00");
                }
            }
            if (kondisi == false && timer > time_windows[gen[i]][1]) {
                S_timer = String.valueOf(timer);
                String S_time_windows = String.valueOf(time_windows[gen[i]][1]);
                String S_penalty = String.valueOf(penalty);
                penalty = tambah(String.valueOf(kurang(S_timer, S_time_windows)), S_penalty);
                //System.out.println("penalty = " + penalty);
            }
            //System.out.println("Timer 2" + " " + i + ": " + timer);
            indek_wisata++;
            if (timer > 14.45) {
                break;
            }
        }
        S_time = String.valueOf(time);
        S_timer = String.valueOf(timer);
        S_time_hotel = String.valueOf(time_hotel[no_hotel][gen[indek_wisata - 1]]);
        time = tambah(S_time, S_time_hotel);
        timer = tambah(S_timer, S_time_hotel);
        //System.out.println(gen[indek_wisata-1]+" "+(indek_wisata-1)+" "+S_time_hotel);
        //System.out.println("Timer Hotel: "+timer);
        // penalty
        if (timer > 19) {
            S_timer = String.valueOf(timer);
            String S_penalty = String.valueOf(penalty);
            penalty = tambah(String.valueOf(kurang(S_timer, "19.00")), S_penalty);
        }
        System.out.println("=>> " + time + " " + penalty + " " + indek_wisata);
        double aa = indek_wisata;
        hasil = (1 / (1 + time)) + (penalty * (-1)) + (aa / 10);
        return hasil;
    }

    // operasi perjumlahan waktu
    public double tambah(String a, String b) {
        double hasil = 0;
        if (Double.valueOf(b) >= 0 && Double.valueOf(a) >= 0) {
            String string_a = (a);
            double[] array_a = rubah(string_a);
            String string_b = (b);
            double[] array_b = new double[2];
            array_b = rubah(string_b);
            double menit = array_a[1] + array_b[1];
//            System.out.println(array_a[1]+" + "+array_b[1]+"-> "+menit);
            // hitung menit dan sisanya
            double jam_menit = 0, sisa_menit = 0;
            sisa_menit = (menit % 60);
            jam_menit = (menit - sisa_menit) / 60;
            // hitung jam
            hasil = (array_a[0] + array_b[0]) + jam_menit;
            double menit_jam = sisa_menit / 100;
            hasil += menit_jam;
        } else if (Double.valueOf(a) >= 0 && Double.valueOf(b) < 0) {
            String s_a = (a);
            String s_b = String.valueOf(Math.abs(Double.valueOf(b)));
            hasil = kurang(s_a, s_b);
        } else if (Double.valueOf(a) < 0 && Double.valueOf(b) >= 0) {
            String s_a = String.valueOf(a);
            String s_b = String.valueOf(Math.abs(Double.valueOf(b)));
            hasil = kurang(s_b, s_a);
            hasil *= -1;
        } else {
            hasil = tambah(String.valueOf(Math.abs(Double.valueOf(a))), String.valueOf(Math.abs(Double.valueOf(b))));
            hasil *= -1;
        }
        return hasil;
    }

    // operasi pengurangan waktu
    public double kurang(String a, String b) {
        double hasil = 0;
        String string_a = (a);
        double[] array_a = rubah(string_a);
        String string_b = (b);
        double[] array_b = rubah(string_b);
        double menit = array_a[1] - array_b[1];
        double jam_menit = 0, sisa_menit = 0;
        if (menit < 0) {
            jam_menit = menit * -1;
        }
        jam_menit = 60 - jam_menit;
        jam_menit /= 100;
        // hitung jam
        hasil = (array_a[0] - array_b[0]);
        if (menit < 0) {
            hasil = hasil - 1;
            hasil += jam_menit;
        } else {
            menit /= 100;
            hasil += menit;
        }
        return hasil;
    }

    public double[] rubah(String string_a) {
        //System.out.println(string_a);
        char[] pp = new char[string_a.length()];
        for (int i = 0; i < string_a.length(); i++) {
            pp[i] = string_a.charAt(i);
        }
        for (int i = 0; i < pp.length; i++) {
            // kondisi perkalian 10
            if (pp[i] == '.') {
                if (i == 2) {
                    String a = String.valueOf(pp[i + 1]);
                    if (Double.valueOf(a) > 0) {
                        if (pp.length == 5) {
                        } else if (pp.length == 4) {
                            char[] new_pp = pp;
                            pp = new char[pp.length + 1];
                            for (int j = 0; j < new_pp.length; j++) {
                                pp[j] = new_pp[j];
                            }
                            pp[i + 2] = '0';
                        }
                    }
                } else if (i == 1) {
                    String a = String.valueOf(pp[i + 1]);
                    if (Double.valueOf(a) > 0) {
                        if (pp.length == 4) {
                        } else if (pp.length == 3) {
                            char[] new_pp = pp;
                            pp = new char[pp.length + 1];
                            for (int j = 0; j < new_pp.length; j++) {
                                pp[j] = new_pp[j];
                            }
                            pp[i + 2] = '0';
                        }
                    }
                }
            }
        }
        for (int i = 0; i < pp.length; i++) {
            if (pp[i] == '.') {
                pp[i] = '-';
            }
        }
        string_a = "";
        for (int i = 0; i < pp.length; i++) {
            string_a += pp[i];
        }
        String[] array_aa = string_a.split("-");
        double[] array_double = new double[array_aa.length];
        for (int i = 0; i < array_aa.length; i++) {
            array_double[i] = Double.valueOf(array_aa[i]);
        }
        //System.out.println(Arrays.toString(array_double));
        return array_double;
    }
}