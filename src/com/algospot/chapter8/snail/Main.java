package com.algospot.chapter8.snail;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int n, m;
    private double[][] result = new double[1001][1001];

    private void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();
        System.out.println(percent(0, 0));
    }

    private void initialize() {
        n = scanner.nextInt();
        m = scanner.nextInt();
        result = new double[n + 1][m + 1];

        for(int i=0; i<=n; i++)
            Arrays.fill(result[i], -1.0);
    }

    private double percent(int climbed, int days) {
        if(climbed >= n)
            return 1;
        else if(days == m || m - climbed > n - days)
            return 0;

        if(result[climbed][days] != -1)
            return result[climbed][days];

        result[climbed][days] = 0.75 * percent(climbed + 2, days + 1) +
                                0.25 * percent(climbed + 1, days + 1);


        return result[climbed][days];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
