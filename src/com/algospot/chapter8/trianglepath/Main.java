package com.algospot.chapter8.trianglepath;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private int n;
    private int[][] path;
    private int[][] result;

    private void run() {
        int c = sc.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase() {
        init();
        System.out.println(cal(0, 0));
    }

    private void init() {
        n = sc.nextInt();
        path = new int[n][n];
        result = new int[n][n];

        for(int i=0; i<n; i++){
            for(int j=0; j<=i; j++){
                path[i][j] = sc.nextInt();
            }
        }
    }

    private int cal(int x, int y) {
        if(y == (path.length - 1))
            return path[y][x];

        if (result[y][x] != 0)
            return result[y][x];

        result[y][x] = Math.max(cal(x, y + 1), cal(x + 1, y + 1)) + path[y][x];

        return result[y][x];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
