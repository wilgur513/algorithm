package com.boj.unsolved.dp.p11066;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int INF = 987654321;
    static int[][] cache;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static int[] sum;

    public static void main(String[] args) throws IOException {
        int n = Integer.valueOf(reader.readLine());

        for(int i = 0; i < n; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.valueOf(reader.readLine());
        sum = new int[n + 1];
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        sum[0] = 0;

        for(int i = 1; i <= n; i++)
            sum[i] = sum[i - 1] + Integer.valueOf(tokenizer.nextToken());

        cache = new int[n][n];
        for(int i = 0; i < n; i++)
            Arrays.fill(cache[i], -1);
    }

    private static void solve() {
        System.out.println(cal(0, n - 1));
    }

    private static int cal(int start, int end) {
        if(start >= end)
            return 0;

        if(start + 1 == end)
            return sum[end + 1] - sum[start];

        if(cache[start][end] != -1)
            return cache[start][end];

        int result = INF;

        for(int i = start; i < end; i++)
            result = Math.min(result, cal(start, i) + cal(i + 1, end) + sum[end + 1] - sum[start]);

        cache[start][end] = result;
        return result;
    }
}
