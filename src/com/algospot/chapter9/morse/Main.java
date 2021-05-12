package com.algospot.chapter9.morse;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int n;
    private int m;
    private long k;
    private long[][] bino = new long[201][201];

    private void run() {
        int numberOfTestCase = scanner.nextInt();

        initializeBinomical();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private void initializeBinomical() {
        for(int i=0; i<201; i++) {
            bino[i][0] = 1;
            bino[i][i] = 1;
        }
    }

    private void runSingleTestCase() {
        initialize();
        System.out.println(findMorse(n, m, k));
    }

    private void initialize() {
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
    }

    private String findMorse(int n, int m, long skip) {
        if(n == 0){
            String result = "";

            for(int i=0; i<m; i++)
                result += "o";

            return result;
        }

        if(skip <= binomical(n + m - 1, n - 1)){
            return "-" + findMorse(n - 1, m, skip);
        }else
            return "o" + findMorse(n, m - 1, skip - binomical(n + m - 1, n - 1));
    }

    private long binomical(int a, int b){
        if(bino[a][b] != 0)
            return bino[a][b];

        bino[a][b] = Math.min(1000000000, binomical(a - 1, b) + binomical(a - 1, b - 1));

        return bino[a][b];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
