package com.boj.solved.bruteforce.p17136;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

public class Main {
    private static final int INF = 987654321;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    private static int[][] map = new int[10][10];
    private static int[] count = {0, 0, 0, 0, 0};

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        for(int y = 0; y < 10; y++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for(int x = 0; x < 10; x++) {
                map[y][x] = Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        int result = cal(0, 0);
        System.out.println(result == INF ? -1 : result);
    }

    private static int cal(int y, int x) {
        if(isComplete()) {
            return Arrays.stream(count).sum();
        }

        if(x == 10) {
            return cal(y + 1, 0);
        }

        if(y == 10) {
            return INF;
        }

        if(map[y][x] == 1) {
            int min = INF;
            for(int size = 1; size <= 5; size++) {
                if(isPossible(y, x, size)) {
                    coverPaper(y, x, size);
                    min = Math.min(min, cal(y, x + 1));
                    uncoverPaper(y, x, size);
                }
            }
            return min;
        }

        return cal(y, x + 1);
    }

    private static boolean isComplete() {
        for(int y = 0; y < 10; y++) {
            for(int x = 0; x < 10; x++) {
                if(map[y][x] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isPossible(int y, int x, int size) {
        if(count[size - 1] >= 5) {
            return false;
        }

        for(int dy = 0; dy < size; dy++) {
            for(int dx = 0; dx < size; dx++) {
                if(!isValid(y + dy, x + dx) || map[y + dy][x + dx] != 1) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void coverPaper(int y, int x, int size) {
        count[size - 1]++;
        for(int dy = 0; dy < size; dy++) {
            for(int dx = 0; dx < size; dx++) {
                map[y + dy][x + dx] = 0;
            }
        }
    }

    private static void uncoverPaper(int y, int x, int size) {
        count[size - 1]--;
        for(int dy = 0; dy < size; dy++) {
            for(int dx = 0; dx < size; dx++) {
                map[y + dy][x + dx] = 1;
            }
        }
    }


    private static boolean isValid(int y, int x) {
        return (0 <= y && y < 10) && (0 <= x && x < 10);
    }

}
