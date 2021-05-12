package com.algospot.chapter8.poly;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int n;
    private int[][] result;

    private void run() {
        int caseNumber = scanner.nextInt();

        for(int i=0; i<caseNumber; i++)
            runSingleCase();
    }

    private void runSingleCase() {
        initialize();

        System.out.println(numberOfAllCase());
    }

    private int numberOfAllCase() {
        int result = 0;

        for(int i=1; i<=n; i++)
            result = (result + numberOfAllCaseHelp(n - i, i)) % 10000000;

        return result;
    }

    private void initialize() {
        n = scanner.nextInt();
        result = new int[n + 1][n + 1];
    }

    private int numberOfAllCaseHelp(int remain, int previous) {
        if(remain == 0)
            return 1;
        else if(remain < 0)
            return 0;

        if(result[remain][previous] != 0)
            return result[remain][previous];

        for(int current = 1; current <= remain; current++){
            result[remain][previous] = (result[remain][previous] +
                                        (previous + current - 1)*numberOfAllCaseHelp(remain - current, current)) % 10000000;
        }

        return result[remain][previous];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
