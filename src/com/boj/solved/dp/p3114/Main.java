package com.boj.solved.dp.p3114;

import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int r, c;
    static String[][] map;
    static long[][] apple, banana;
    static long[][] cache;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        r = Integer.parseInt(tokenizer.nextToken());
        c = Integer.parseInt(tokenizer.nextToken());
        map = new String[r][c];
        apple = new long[r][c];
        banana = new long[r][c];

        for(int i = 0; i < r; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < c; j++)
                map[i][j] = tokenizer.nextToken();
        }

        initApple();
        initBanana();

        cache = new long[r][c];
        for(int i = 0; i < r; i++)
            Arrays.fill(cache[i], -1);
    }

    private static void initApple(){
        for(int y = 0; y < r; y++){
            if(map[y][0].startsWith("A"))
                apple[y][0] = Integer.valueOf(map[y][0].substring(1));

            for(int x = 1; x < c; x++){
                if(map[y][x].startsWith("A")){
                    apple[y][x] = apple[y][x - 1] + Integer.valueOf(map[y][x].substring(1));
                }else{
                    apple[y][x] = apple[y][x - 1];
                }
            }
        }
    }

    private static void initBanana(){
        for(int x = 0; x < c; x++){
            if(map[0][x].startsWith("B"))
                banana[0][x] = Integer.valueOf(map[0][x].substring(1));

            for(int y = 1; y < r; y++){
                if(map[y][x].startsWith("B")){
                    banana[y][x] = banana[y - 1][x] + Integer.valueOf(map[y][x].substring(1));
                }else{
                    banana[y][x] = banana[y - 1][x];
                }
            }
        }
    }

    private static void solve() {
        long result = cal(0, 0);
        System.out.println(result);
    }

    private static long cal(int y, int x) {
        if (x == c - 1 && y == r - 1)
            return 0;

        if (cache[y][x] != -1)
            return cache[y][x];

        cache[y][x] = 0;

        if(isValid(y, x + 1)){
            cache[y][x] = Math.max(cache[y][x],
                    cal(y, x + 1) + banana(y - 1, x + 1));
        }

        if(isValid(y + 1, x + 1)){
            cache[y][x] = Math.max(cache[y][x],
                    cal(y + 1, x + 1) + apple(y + 1, x) + banana(y, x + 1));
        }

        if(isValid(y + 1, x)){
            cache[y][x] = Math.max(cache[y][x],
                    cal(y + 1, x) + apple(y + 1, x - 1));
        }

        return cache[y][x];
    }

    private static long banana(int y, int x){
        return y >= 0 ? banana[y][x] : 0;
    }

    private static long apple(int y, int x){
        return x >= 0 ? apple[y][x] : 0;
    }

    private static boolean isValid(int y, int x){
        if(x < 0 || x >= c)
            return false;

        if(y < 0 || y >= r)
            return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
