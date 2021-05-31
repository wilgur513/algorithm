package com.boj.unsolved.dp.p12865;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, k;
    private static int[][] object;
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
        n = Integer.valueOf(tokenizer.nextToken());
        k = Integer.valueOf(tokenizer.nextToken());

        object = new int[n][2];

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < 2; j++){
                object[i][j] = Integer.valueOf(tokenizer.nextToken());
            }
        }

        cache = new int[n][k + 1];
        for(int i = 0; i < n; i++)
            Arrays.fill(cache[i], -1);

    }

    private static void solve() {
        System.out.println(cal(0, k));
    }

    private static int cal(int current, int weight){
        if(current == n)
            return 0;

        if(cache[current][weight] != -1)
            return cache[current][weight];

        int result = cal(current + 1, weight);

        if(weight >= object[current][0]){
            result = Math.max(result, cal(current + 1, weight - object[current][0]) + object[current][1]);
        }

        cache[current][weight] = result;
        return result;
    }
}

