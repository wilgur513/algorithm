package com.boj.solved.bruteforce.p2239;

import java.util.*;
import java.io.*;
import java.util.stream.IntStream;

public class Main {
    static int[][] map = new int[9][9];

    public static void main(String[] args) {
        inputData();
        solve(0, 0);

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    private static void inputData() {
        Scanner scanner = new Scanner(System.in);

        for(int y = 0; y < 9; y++) {
            String s = scanner.next();

            for(int x = 0; x < 9; x++) {
                map[y][x] = Integer.parseInt(String.valueOf(s.charAt(x)));
            }
        }
    }

    private static boolean solve(int y, int x) {
        if(x == 9) {
            return solve(y + 1, 0);
        }

        if(y == 9) {
            return true;
        }

        if(map[y][x] != 0) {
            return solve(y, x + 1);
        }

        for(int num = 1; num <= 9; num++) {
            if(isValid(num, y, x)) {
                map[y][x] = num;
                if(solve(y, x + 1)) {
                    return true;
                }
                map[y][x] = 0;
            }
        }

        return false;
    }

    private static boolean isValid(int num, int y, int x) {
        return isNotHasInLine(num, y, x) && isNotHasInBox(num, y, x);
    }

    private static boolean isNotHasInLine(int num, int y, int x) {
        return isNotHasVertically(num, x) && isNotHasHorizontally(num, y);
    }

    private static boolean isNotHasInBox(int num, int y, int x) {
        for(int dy = 0; dy < 3; dy++) {
            for(int dx = 0; dx < 3; dx++) {
                if(map[(y / 3) * 3 + dy][(x / 3) * 3 + dx] == num)
                    return false;
            }
        }

        return true;
    }

    private static boolean isNotHasVertically(int num, int x) {
        return IntStream.range(0, 9).map(y -> map[y][x]).allMatch(value -> value != num);
    }

    private static boolean isNotHasHorizontally(int num, int y) {
        return IntStream.range(0, 9).map(x -> map[y][x]).allMatch(value -> value != num);
    }

}
