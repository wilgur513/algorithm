package com.boj.solved.dp.p2342;

import java.util.*;
import java.io.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static List<Integer> values = new ArrayList<>();
    private static int[][][] cache;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        while(true) {
            int value = Integer.parseInt(tokenizer.nextToken());
            values.add(value);
            if(value == 0) {
                break;
            }
        }
        cache = new int[values.size()][5][5];
        for(int i = 0; i < values.size(); i++) {
            for(int j = 0; j < 5; j++) {
                Arrays.fill(cache[i][j], -1);
            }
        }
    }

    private static void solve() {
        System.out.println(cal(0, 0, 0));
    }

    private static int cal(int index, int right, int left) {
        int next = values.get(index);

        if(next == 0) {
            return 0;
        }

        if(cache[index][right][left] == -1) {
            if(right == next) {
                cache[index][right][left] = cal(index + 1, right ,left) + 1;
            } else if(left == next) {
                cache[index][right][left] = cal(index + 1, right, left) + 1;
            } else {
                cache[index][right][left] = Math.min(cal(index + 1, next ,left) + power(right, next),
                        cal(index + 1, right, next) + power(left, next));
            }
        }

        return cache[index][right][left];
    }

    private static int power(int current, int next) {
        if(isCross(current, next)) {
            return 4;
        } else if(isAdj(current, next)) {
            return 3;
        } else if(isFirstMove(current)) {
            return 2;
        } else if(isSame(current, next)) {
            return 1;
        }

        throw new AssertionError();
    }

    private static boolean isCross(int current, int next) {
        if(current == 0) {
            return false;
        }

        return Math.abs(current - next) == 2;
    }

    private static boolean isAdj(int current, int next) {
        if(current == 0) {
            return false;
        }
        return Math.abs(current - next) == 1 || Math.abs(current - next) == 3;
    }

    private static boolean isFirstMove(int current) {
        return current == 0;
    }

    private static boolean isSame(int current, int next) {
        return current == next;
    }
}
