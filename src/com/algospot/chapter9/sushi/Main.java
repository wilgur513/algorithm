package com.algospot.chapter9.sushi;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int m;
    private static int[] prices;
    private static int[] favors;
    private static int[] result = new int[10000001];

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++){
            runSingleTestCase();
        }
    }

    private static void runSingleTestCase() {
        initialize();
        System.out.println(result[m]);
    }

    private static void initialize() {
        n = scanner.nextInt();
        m = scanner.nextInt() / 100;
        prices = new int[n];
        favors = new int[n];

        for(int i=0; i<n; i++){
            prices[i] = scanner.nextInt() / 100;
            favors[i] = scanner.nextInt();
        }

        Arrays.fill(result, 0);

        for(int i = 0; i<=m; i++)
            result[i] = favor(i);
    }

    public static int favor(int price){
        result[price] = 0;

        for(int i=0; i<n; i++){
            if(price < prices[i])
                continue;
            else if(price == prices[i])
                result[price] = Math.max(result[price], favors[i]);
            else {
                result[price] = Math.max(result[price], favors[i] + result[price - prices[i]]);
            }
        }

        return result[price];
    }

    public static void main(String[] args){
        run();
    }
}