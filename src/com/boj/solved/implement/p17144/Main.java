package com.boj.solved.implement.p17144;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    static int r, c, t;
    static int[][] map;
    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;
    static int[] dy = {0, 0, -1, 1};
    static int[] dx = {1, -1, 0, 0};

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        r = Integer.parseInt(tokenizer.nextToken());
        c = Integer.parseInt(tokenizer.nextToken());
        t = Integer.parseInt(tokenizer.nextToken());
        map = new int[r][c];

        for(int y = 0; y < r; y++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for(int x = 0; x < c; x++) {
                map[y][x] = Integer.parseInt(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        for(int i = 0; i < t; i++) {
            spread();
            move();
        }
        System.out.println(count());
    }

    private static void spread() {
        int[][] newMap = newMap();

        for(int y = 0; y < r; y++) {
            for(int x = 0; x < c; x++) {
                if(map[y][x] > 0) {
                    int spreadAmount = newMap[y][x] / 5;
                    int decrementAmount = (spreadAmount * adjCount(y, x));
                    for(int i = 0; i < 4; i++) {
                        if(isValid(y + dy[i], x + dx[i])) {
                            map[y + dy[i]][x + dx[i]] += spreadAmount;
                        }
                    }
                    map[y][x] -= decrementAmount;
                }
            }
        }
    }

    static int[][] newMap() {
        int[][] newMap = new int[r][c];

        for(int y = 0; y < r; y++) {
            newMap[y] = map[y].clone();
        }
        return newMap;
    }

    private static int count() {
        int result = 0;
        for(int[] row : map) {
            for(int col : row) {
                result += col;
            }
        }
        return result + 2;
    }

    private static void move() {
        int[] pos = purifierPos();
        moveVertical(pos[0], 0, DOWN);
        moveHorizontal(0, LEFT);
        moveVertical(pos[0], c - 1, UP);
        moveHorizontal(pos[0],  RIGHT);

        moveVertical(pos[1], 0, UP);
        moveHorizontal(r - 1, LEFT);
        moveVertical(pos[1], c - 1, DOWN);
        moveHorizontal(pos[1],  RIGHT);

    }

    private static void moveVertical(int y, int x, int dir) {
        switch (dir) {
            case UP:
                moveUp(y, x);
                return;
            case DOWN :
                moveDown(y, x);
                return;
        }
        throw new AssertionError();
    }

    private static void moveUp(int y, int x) {
        int start = x == 0 ? y : 0;
        int end = x == 0 ? r - 1 : y;
        for(int i = start; i < end; i++) {
            if(map[i][x] == -1) {
                continue;
            }
            map[i][x] = map[i + 1][x];
        }
    }

    private static void moveDown(int y, int x) {
        int start = x == 0 ? y : r - 1;
        int end = x == 0 ? 0 : y;
        for(int i = start; i > end; i--) {
            if(map[i][x] == -1) {
                continue;
            }
            map[i][x] = map[i - 1][x];
        }
    }

    private static void moveHorizontal(int y, int dir) {
        int start = dir == LEFT ? 0 : c - 1;
        int end = dir == LEFT ? c - 2 : 1;

        if(dir == LEFT) {
            for(int x = start; x <= end; x++) {
                map[y][x] = map[y][x + 1];
            }
        } else {
            for(int x = start; x >= end; x--) {
                if(map[y][x - 1] == -1) {
                    map[y][x] = 0;
                    continue;
                }

                map[y][x] = map[y][x - 1];
            }
        }
    }

    private static int[] purifierPos() {
        int[] pos = new int[2];
        int index = 0;
        for(int y = 0; y < r; y++) {
            if(map[y][0] == -1) {
                pos[index++] = y;
            }
        }
        return pos;
    }

    private static int adjCount(int y, int x) {
        int result = 0;
        for(int i = 0; i < 4; i++) {
            if(isValid(y + dy[i], x + dx[i])) {
                result++;
            }
        }
        return result;
    }

    private static boolean isValid(int y, int x) {
        return (0 <= y && y < r) && (0 <= x && x < c) && (map[y][x] != -1);
    }
}
