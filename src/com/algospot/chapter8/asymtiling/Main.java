package com.algospot.chapter8.asymtiling;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private int n;
    private int[] all;
    private int[][] duplication;

    private void run() {
        int caseNum = sc.nextInt();

        for(int i=0; i<caseNum; i++)
            runSingleCase();
    }

    private void runSingleCase() {
        initialize();

        int result = numOfAllCase(0) - numOfDuplicationCase(0, n);
        System.out.println(round(result));
    }

    private void initialize() {
        n = sc.nextInt();
        all = new int[n];
        duplication = new int[n][n + 1];
    }

    private int numOfAllCase(int start) {
        if(start == n)
            return 1;
        else if(start > n)
            return 0;

        if(all[start] != 0)
            return all[start];

        all[start] = (numOfAllCase(start + 2) + numOfAllCase(start + 1)) % 1000000007;

        return all[start];
    }

    private int numOfDuplicationCase(int start, int end){
        if(end - start == 2)
            return 2;
        else if(end - start == 1 || end == start)
            return 1;
        if(start > end)
            return 0;

        if(duplication[start][end] != 0)
            return duplication[start][end];

        duplication[start][end] = (numOfDuplicationCase(start + 2, end - 2) + numOfDuplicationCase(start + 1, end - 1)) % 1000000007;

        return duplication[start][end];
    }


    private int round(int value){
        if(value < 0)
            value += 1000000007;

        return value;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
