package com.boj.unsolved.dp.p1727;

import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m, smallSize, largeSize;
    private static int[] man, woman, largeSizeValues, smallSizeValues;
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
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        man = new int[n];
        woman = new int[m];
        cache = new int[1000][1000];

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++)
            man[i] = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < m; i++)
            woman[i] = Integer.parseInt(tokenizer.nextToken());

        Arrays.sort(man);
        Arrays.sort(woman);

        smallSizeValues = n > m ? woman : man;
        largeSizeValues = n > m ? man : woman;

        for(int i = 0; i < 1000; i++)
            Arrays.fill(cache[i], - 1);
    }

    private static void solve() {
        int result = n > m ? cal(m - 1, n - 1) : cal(n - 1, m - 1);
        System.out.println(result);
    }

    private static int cal(int a, int b) {
        if(a < 0)
            return 0;
        if(b < 0)
            return 987654321;

        if(cache[a][b] != -1)
            return cache[a][b];

        int result = cal(a - 1, b - 1) + Math.abs(smallSizeValues[a] - largeSizeValues[b]);
        result = Math.min(result, cal(a, b - 1));

        cache[a][b] = result;
        return result;
    }
}
