package com.boj.solved.dp.p17404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int R = 0;
    static final int G = 1;
    static final int B = 2;
    static final int INF = Integer.MAX_VALUE;
    static int n;
    static int[][] cost;
    static char firstType;
    static int[][][] cache;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());
        cost = new int[n][3];
        cache = new int[n][3][3];

        for(int i = 0; i < n; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            cost[i][0] = Integer.parseInt(tokenizer.nextToken());
            cost[i][1] = Integer.parseInt(tokenizer.nextToken());
            cost[i][2] = Integer.parseInt(tokenizer.nextToken());

            for(int j = 0; j < 3; j++) {
                Arrays.fill(cache[i][j], -1);
            }
        }
    }

    private static void solve() {
        int result = INF;
        result = Math.min(result, minCost(1, R, R) + cost[0][0]);
        result = Math.min(result, minCost(1, G, G) + cost[0][1]);
        result = Math.min(result, minCost(1, B, B) + cost[0][2]);
        System.out.println(result);
    }

    private static int minCost(int index, int prev, int first) {
        if(index == n - 1) {
            if(first == R) {
                if(prev == R)
                    return Math.min(cost[n - 1][1], cost[n - 1][2]);
                else if(prev == G)
                    return cost[n - 1][2];
                return cost[n - 1][1];
            } else if(first == G) {
                if(prev == R)
                    return cost[n - 1][2];
                else if(prev == G)
                    return Math.min(cost[n - 1][0], cost[n - 1][2]);
                return cost[n - 1][0];
            }

            if(prev == R)
                return cost[n - 1][1];
            else if(prev == G)
                return cost[n - 1][0];
            return Math.min(cost[n - 1][0], cost[n - 1][1]);
        }

        if(cache[index][prev][first] != -1)
            return cache[index][prev][first];


        if(prev == R) {
            return Math.min(minCost(index + 1, G, first) + cost[index][1],
                    minCost(index + 1, B, first) + cost[index][2]);
        } else if(prev == G) {
            return Math.min(minCost(index + 1, R, first) + cost[index][0],
                    minCost(index + 1, B, first) + cost[index][2]);
        }

        cache[index][prev][first] = Math.min(minCost(index + 1, R, first) + cost[index][0],
                minCost(index + 1, G, first) + cost[index][1]);

        return cache[index][prev][first];
    }
}
