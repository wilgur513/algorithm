package com.algospot.chapter9.numbergame;

import java.util.*;

public class Main {
    private static final int EMPTY = -987654321;
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int[] list;
    private static int[][] result;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        initialize();
        System.out.println(play(0, n - 1));
    }

    private static void initialize() {
        n = scanner.nextInt();
        list = new int[n];
        result = new int[n][n];

        for(int i=0; i<n; i++)
            list[i] = scanner.nextInt();

        for(int i=0; i<n; i++)
            Arrays.fill(result[i], EMPTY);
    }

    private static int play(int left, int right) {
        if(left > right)
            return 0;

        if(result[left][right] != EMPTY)
            return result[left][right];

        result[left][right] = Math.max(list[left] - play(left + 1, right), list[right] - play(left, right - 1));

        if(right - left + 1 >= 2){
            result[left][right] = Math.max(result[left][right], -play(left + 2, right));
            result[left][right] = Math.max(result[left][right], -play(left, right - 2));
        }

        return result[left][right];
    }

    public static void main(String[] args) {
        Main.run();
    }
}
