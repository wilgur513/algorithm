package com.boj.solved.dp.p12785;

import java.util.*;

public class Main {
    static class Position{
        final int y, x;

        public Position(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public boolean equals(Object obj){
            Position pos = (Position)obj;

            return x == pos.x && y == pos.y;
        }

        public Position left(){
            return new Position(y, x - 1);
        }

        public Position down(){
            return new Position(y - 1, x);
        }
    }

    static final int NUM = 1000007;
    static Scanner scanner = new Scanner(System.in);
    static int w, h, x, y;
    static long[][] count = new long[201][201];

    public static void main(String[] args) {
        inputData();
        solve();
    }

    private static void inputData() {
        w = scanner.nextInt();
        h = scanner.nextInt();
        x = scanner.nextInt();
        y = scanner.nextInt();

        for(int i = 0; i <= 200; i++)
            Arrays.fill(count[i], -1);
    }

    private static void solve() {
        Position home = new Position(0, 0);
        Position toast = new Position(y - 1, x - 1);
        Position school = new Position(h - 1, w - 1);

        long homeTotoast = numberOfRoute(home, toast);
        long toastToSchool = numberOfRoute(toast, school);
        long result = (homeTotoast * toastToSchool) % NUM;

        System.out.println(result);
    }

    private static long numberOfRoute(Position from, Position to) {
        if(!isValidPosition(to, from))
            return 0;

        if(from.equals(to))
            return 1;

        if(count[to.y][to.x] != -1)
            return count[to.y][to.x];

        long result = 0;

        Position left = to.left();
        Position down = to.down();

        result = (result + numberOfRoute(from, left)) % NUM;
        result = (result + numberOfRoute(from, down)) % NUM;
        count[to.y][to.x] = result % NUM;

        return result % NUM;
    }

    static boolean isValidPosition(Position pos, Position basePoint){
        return pos.x >= basePoint.x && pos.y >= basePoint.y;
    }
}
