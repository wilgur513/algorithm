package com.boj.solved.dp.p1937;

import java.util.*;
import java.io.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[][] map;
    private static int[][] cache;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.valueOf(reader.readLine());
        map = new int[n][n];
        cache = new int[n][n];

        for(int y = 0; y < n; y++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            for(int x = 0; x < n; x++){
                map[y][x] = Integer.valueOf(tokenizer.nextToken());
            }
        }

        for(int i = 0; i < n; i++)
            Arrays.fill(cache[i], -1);
    }

    private static void solve() {
        int result = 0;

        for(int y = 0; y < n; y++)
            for(int x = 0; x < n; x++)
                result = Math.max(result, maxDurationOfLife(y, x));

        System.out.println(result);
    }

    private static int maxDurationOfLife(int y, int x){
        List<int[]> adj = adjacent(y, x);

        if(adj.size() == 0)
            return 1;

        if(cache[y][x] != -1)
            return cache[y][x];

        int result = 0;

        for(int[] next : adj){
            result = Math.max(result, maxDurationOfLife(next[0], next[1]) + 1);
        }

        cache[y][x] = result;
        return result;
    }

    private static List<int[]> adjacent(int y, int x){
        List<int[]> result = new ArrayList<>();
        int[] dy = {0, 0, 1, -1};
        int[] dx = {1, -1, 0, 0};

        for(int i = 0; i < 4; i++){
            if(isValid(y + dy[i], x + dx[i]) && map[y][x] < map[y + dy[i]][x + dx[i]])
                result.add(new int[]{y + dy[i], x + dx[i]});
        }

        return result;
    }

    private static boolean isValid(int y, int x){
        return (y >= 0 && y < n) && (x >= 0 && x < n);
    }
}
