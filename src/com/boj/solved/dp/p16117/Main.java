package com.boj.solved.dp.p16117;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static int[][] map;
    private static int[][] cache;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        map = new int[n][m + 3];
        cache = new int[2*n + 1][m + 3];

        for(int i = 0; i <= 2*n; i++)
            Arrays.fill(cache[i], -1);

        for(int i = 0; i < n; i++){
            map[i][0] = 0;
            map[i][m + 1] = 0;
            map[i][m + 2] = 0;
        }

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            for(int j = 1; j <= m; j++){
                map[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        int result = 0;

        for(int y = 0; y <= 2*n; y++){
            result = Math.max(result, cal(0, y));
        }

        System.out.println(result);
    }

    private static int cal(int x, int y){
        if(cache[y][x] != -1)
            return cache[y][x];

        int result = 0;

        // 앞으로 이동
        if(isValid(x + 2, y)) {
            int value1 = y % 2 == 0 ? 0 : map[y / 2][x + 1];
            int value2 = y % 2 == 0 ? 0 : map[y / 2][x + 2];
            result = Math.max(result, cal(x + 2, y) + value1 + value2);
        }

        // 아래로 이동
        if(isValid(x + 1, y + 2)) {
            int value = y % 2 == 0 ? map[y / 2][x + 1] : map[(y + 2) / 2][x + 1];
            result = Math.max(result, cal(x + 1, y + 2) + value);
        }

        // 위로 이동
        if(isValid(x + 1, y - 2)) {
            int value = map[(y - 2) / 2][x + 1];
            result = Math.max(result, cal(x + 1, y - 2) + value);
        }

        cache[y][x] = result;
        return result;
    }

    private static boolean isValid(int x, int y){
        if(x < 0 || x >= m + 3)
            return false;

        if(y < 0 || y > 2*n)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
