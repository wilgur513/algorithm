package com.algospot.chapter14.pass486;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int lo;
    private static int hi;
    private static int[] minFactor = new int[10000001];

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        initMinFactor();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void initMinFactor() {
        for(int i=0; i<= 10000000; i++)
            minFactor[i] = i;

        int sqrt = (int)Math.sqrt(10000000);
        minFactor[0] = -1;
        minFactor[1] = -1;

        for(int i=0; i<=sqrt; i++){
            if(minFactor[i] == i){
                for(int j=i*i; j<=10000000; j+=i){
                    if(minFactor[j] == j)
                        minFactor[j] = i;
                }
            }
        }
    }

    private static void runSingleTestCase() {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() {
        n = scanner.nextInt();
        lo = scanner.nextInt();
        hi = scanner.nextInt();
    }

    private static int solve() {
        int result = 0;

        for(int value=lo; value <=hi; value++)
            if(numberOfFactors(value) == n)
                result++;

        return result;
    }

    private static int numberOfFactors(int value) {
        int result = 1;
        int prev = -1;
        int count = 0;

        while(value > 1){
            if(prev == -1 || prev == minFactor[value])
                count++;
            else{
                result *= (count + 1);
                count = 1;
            }

            prev = minFactor[value];
            value /= minFactor[value];
        }

        return result * (count + 1);
    }

    public static void main(String[] args) {
        run();
    }
}
