package com.algospot.chapter8.tiling2;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private int n;
    private int[] result;

    private void run() {
        int caseNumber = sc.nextInt();

        for(int i=0; i<caseNumber; i++)
            runSingleCase();

    }

    private void runSingleCase() {
        initialize();
        System.out.println(numberOfAllCase(0));
    }

    private void initialize() {
        n = sc.nextInt();
        result = new int[n];
    }

    private int numberOfAllCase(int start) {
        if(start == n)
            return 1;
        else if(start > n)
            return 0;

        if(result[start] != 0)
            return result[start];

        result[start] = (numberOfAllCase(start + 2) + numberOfAllCase(start + 1)) % 1000000007;

        return result[start];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
