package com.boj.solved.implement.p14499;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

class Dice {
    int top, bottom, up, down, left, right;

    public Dice(int top, int bottom, int up, int down, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public Dice turnLeft() {
        return new Dice(right, left, up, down, top, bottom);
    }

    public Dice turnRight() {
        return new Dice(left, right, up, down, bottom, top);
    }

    public Dice turnUp() {
        return new Dice(down, up, top, bottom, left, right);
    }

    public Dice turnDown() {
        return new Dice(up, down, bottom, top, left, right);
    }

    public Dice setBottom(int value) {
        return new Dice(top, value, up, down, left, right);
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    static int n, m, x, y, k;
    static int[][] map;
    static int[] commands;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        y = Integer.parseInt(tokenizer.nextToken());
        x = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        map = new int[n][m];
        for(int y = 0; y < n; y++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for(int x = 0; x < m; x++) {
                map[y][x] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        commands = new int[k];
        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < k; i++) {
            commands[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        Dice dice = new Dice(0, 0, 0, 0, 0, 0);

        for(int command : commands) {
            if(isTurnLeft(command) && isValid(y, x - 1)) {
                x--;
                dice = copyMapValue(y, x, dice.turnLeft());
                System.out.println(dice.top);
            } else if(isTurnRight(command) && isValid(y, x + 1)) {
                x++;
                dice = copyMapValue(y, x, dice.turnRight());
                System.out.println(dice.top);
            } else if(isTurnDown(command) && isValid(y + 1, x)) {
                y++;
                dice = copyMapValue(y, x, dice.turnDown());
                System.out.println(dice.top);
            } else if(isTurnUp(command) && isValid(y - 1, x)) {
                y--;
                dice = copyMapValue(y, x, dice.turnUp());
                System.out.println(dice.top);
            }
        }
    }

    private static Dice copyMapValue(int y, int x, Dice dice) {
        if(map[y][x] == 0) {
            map[y][x] = dice.bottom;
            return dice;
        }
        Dice result = dice.setBottom(map[y][x]);
        map[y][x] = 0;
        return result;
    }

    private static boolean isTurnRight(int command) {
        return command == 1;
    }

    private static boolean isTurnLeft(int command) {
        return command == 2;
    }

    private static boolean isTurnUp(int command) {
        return command == 3;
    }

    private static boolean isTurnDown(int command) {
        return command == 4;
    }

    private static boolean isValid(int y, int x) {
        return (0 <= y && y < n) && (0 <= x && x < m);
    }
}
