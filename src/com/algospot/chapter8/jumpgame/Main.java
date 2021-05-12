package com.algospot.chapter8.jumpgame;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private int n;
    private int map[][];
    private long result[][];

    private void run(){
        int c = sc.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase(){
        init();

        System.out.println();
        System.out.println(solve(0, 0));
        if(solve(0, 0) > 0)
            System.out.println("YES");
        else
            System.out.println("NO");
    }

    private void init(){
        n = sc.nextInt();
        map = new int[n][n];
        result = new long[n][n];

        for(int y=0; y<n; y++) {
            for (int x = 0; x<n; x++) {
                map[y][x] = sc.nextInt();
                result[y][x] = -1;
            }
        }
    }

    private long solve(int x, int y) {
        if((x >= n) || (y >= n))
            return 0;

        if((x == (n - 1)) && (y == (n - 1)))
            return 1;

        if(result[y][x] == -1) {
            result[y][x] = solve(x + map[y][x], y) + solve(x, y + map[y][x]);
        }

        return result[y][x];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
