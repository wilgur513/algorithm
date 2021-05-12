package com.algospot.chapter9.genius.slow;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int k;
    private static int m;
    private static int[] times;
    private static double[][] next;
    private static int[] query;
    private static double[][] percents = new double[50][5];

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        initialize();

        for(int i=0; i<m; i++) {
            double result = sumPercents(query[i]);
            System.out.print(result + " ");
        }

        System.out.println();
    }

    private static double sumPercents(int query) {
        double result = 0.0;

        for(int j=0; j<times[query]; j++)
            if(k - j >= 0)
                result += percents[query][(k - j)%5];
        return result;
    }

    private static void initialize() {
        n = scanner.nextInt();
        k = scanner.nextInt();
        m = scanner.nextInt();

        allocateArrayAndInputData();
        calculatePercents();
    }

    private static void allocateArrayAndInputData() {
        times = new int[n];
        next = new double[n][n];
        query = new int[m];

        for(int i=0; i<n; i++)
            times[i] = scanner.nextInt();

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++)
                next[i][j] = scanner.nextDouble();

        for(int i=0; i<m; i++)
            query[i] = scanner.nextInt();
    }

    private static void calculatePercents() {
        for(int i=0; i<n; i++)
            Arrays.fill(percents[i], 0.0);

        for(int i=0; i<=k; i++)
            for(int j=0; j<n; j++)
                percents[j][i%5] = percent(j, i);
    }

    private static double percent(int current, int time) {
        if(time == 0 && current == 0)
            return 1.0;
        else if(time < times[0])
            return 0.0;

        double result = 0.0;

        for(int i=0; i<n; i++) {
            if(time - times[i] >= 0)
                result += next[i][current] * percents[i][(time - times[i] + 5)%5];
        }

        return result;
    }


    public static void main(String[] args) {
        run();
    }
}
