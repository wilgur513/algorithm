package com.boj.solved.bitmask.p1987;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int r, c;
    static char[][] map;
    static int[][][] cache;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        initSize();
        initMap();
    }

    private static void initSize() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        r = Integer.valueOf(tokenizer.nextToken());
        c = Integer.valueOf(tokenizer.nextToken());
    }

    private static void initMap() throws IOException {
        map = new char[r][c];

        for(int y = 0; y < r; y++) {
            String s = reader.readLine();

            for (int x = 0; x < c; x++) {
                map[y][x] = s.charAt(x);
            }
        }
    }

    private static void solve() {
        System.out.println(max(0, 0, put(0, map[0][0])));
    }

    private static int max(int y, int x, int bit) {
        int result = 1;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for(int i = 0; i < 4; i++){
            int nextY = y + dy[i];
            int nextX = x + dx[i];

            if(isValid(nextY, nextX) && !contains(bit, map[nextY][nextX])){
                result = Math.max(result, max(nextY, nextX, put(bit, map[nextY][nextX])) + 1);
            }
        }

        return result;
    }

    private static boolean contains(int bit, char c){
        return (bit & (1 << index(c))) > 0;
    }

    private static int put(int bit, char c){
        return bit | (1 << index(c));
    }

    private static int index(char c){
        return c - 'A';
    }

    private static boolean isValid(int y, int x){
        return (0 <= y && y < r) && (0 <= x && x < c);
    }
}
