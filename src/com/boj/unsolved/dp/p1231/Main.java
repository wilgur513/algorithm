package com.boj.unsolved.dp.p1231;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int c, d, m;
    private static int[][] stockPrice;
    private static int[][] cache;

    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        c = Integer.valueOf(tokenizer.nextToken());
        d = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());

        stockPrice = new int[c][d];

        for(int i = 0; i < c; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < d; j++){
                stockPrice[i][j] = Integer.valueOf(tokenizer.nextToken());
            }
        }

        cache = new int[c][500005];
    }

    private static void solve() {
        System.out.println(cal());
    }

    private static int cal(){
        int result = m;

        for(int day = 0; day < d - 1; day++){
            for(int i = 0; i < c; i++)
                Arrays.fill(cache[i], -987654321);

            result = result + maxProfit(0, day, result);
        }

        return result;
    }

    private static int maxProfit(int stock, int day, int money){
        if(stock == c)
            return 0;

        if(cache[stock][money] != -987654321)
            return cache[stock][money];

        int result = maxProfit(stock + 1, day, money);

        if(money >= stockPrice[stock][day]) {
            int profit = stockPrice[stock][day + 1] - stockPrice[stock][day];
            result = Math.max(result, maxProfit(stock, day, money - stockPrice[stock][day]) + profit);
            result = Math.max(result, maxProfit(stock + 1, day, money - stockPrice[stock][day]) + profit);
        }

        cache[stock][money] = result;
        return result;
    }
}

