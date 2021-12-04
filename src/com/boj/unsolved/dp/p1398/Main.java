package com.boj.unsolved.dp.p1398;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int INF = 987654321;
    private static int[] cache = new int[100];
    private static Scanner scanner = new Scanner(System.in);
    private static long price;

    public static void main(String[] args) {
        initCache();

        int n = scanner.nextInt();
        for(int i = 0; i < n; i++)
            runSingleTestCase();
    }

    private static void initCache() {
        Arrays.fill(cache, -1);
    }

    private static void runSingleTestCase() {
        inputData();
        solve();
    }

    private static void inputData() {
        price = scanner.nextLong();
    }

    private static void solve() {
        long result = 0;

        while(price > 0){
            result += numberOfCoins((int)(price % 100));
            price /= 100;
        }

        System.out.println(result);
    }

    private static int numberOfCoins(int price){
        if(price <= 0)
            return price < 0 ? INF : 0;

        if(cache[price] != -1)
            return cache[price];

        int result = Math.min(numberOfCoins(price - 1) + 1, numberOfCoins(price - 10) + 1);
        result = Math.min(result, numberOfCoins(price - 25) + 1);
        cache[price] = result;

        return result;
    }
}
