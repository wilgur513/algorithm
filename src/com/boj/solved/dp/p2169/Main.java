package com.boj.solved.dp.p2169;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int N_INF = -987654321;
    static final int LEFT = 0, RIGHT = 1, DOWN = 2;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n, m;
    static int[][] values;
    static int[][][] cache;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        values = new int[n][m];
        cache = new int[n][m][3];

        for(int i = 0; i < n; i++)
            for(int j = 0; j < m; j++)
                Arrays.fill(cache[i][j], N_INF);

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < m; j++){
                values[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        int result = Math.max(cal(0, 1, RIGHT), cal(1, 0, DOWN));
        System.out.println(n == 1 && m == 1 ? values[0][0] : result + values[0][0]);
    }

    private static int cal(int y, int x, int dir) {
        if(y >= n || x >= m || x < 0)
            return N_INF;

        if(y == n - 1 && x == m - 1)
            return values[y][x];

        if(cache[y][x][dir] != N_INF)
            return cache[y][x][dir];

        int result = N_INF;

        if(dir == LEFT) {
            result = Math.max(result, cal(y, x - 1, LEFT));
            result = Math.max(result, cal(y + 1, x, DOWN));
        }else if(dir == RIGHT){
            result = Math.max(result, cal(y, x + 1, RIGHT));
            result = Math.max(result, cal(y + 1, x, DOWN));
        }else if(dir == DOWN){
            result = Math.max(result, cal(y, x - 1, LEFT));
            result = Math.max(result, cal(y, x + 1, RIGHT));
            result = Math.max(result, cal(y + 1, x, DOWN));
        }

        cache[y][x][dir] = result + values[y][x];
        return cache[y][x][dir];
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
