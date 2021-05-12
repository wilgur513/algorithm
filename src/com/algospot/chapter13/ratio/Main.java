package com.algospot.chapter13.ratio;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int m;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        inputData();
        long games = solve();

        if(games >= 2000000000)
            System.out.println(-1);
        else
            System.out.println(games);
    }

    private static void inputData() {
        n = scanner.nextInt();
        m = scanner.nextInt();
    }

    private static long solve() {
        long lo = 0;
        long hi = 2000000000;

        for(int i=0; i<100; i++){
            long games = (lo + hi) / 2;

            if(isIncreasePoint(games))
                hi = games;
            else
                lo = games;
        }

        return hi;
    }

    private static boolean isIncreasePoint(long games) {
        return ((long)m * 100) / n < (m + games)*100 / (n + games);
    }

    public static void main(String[] args) {
        run();
    }
}
