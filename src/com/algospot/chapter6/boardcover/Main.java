package com.algospot.chapter6.boardcover;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private int w, h;
    private char[][] board;
    private int[][] offset = {{0, 0}, {0, 0}, {0, 0}, {-1, 1}};
    private char[][][] block = {{{'#', '#'}, {'#', '.'}}, {{'#', '#'}, {'.', '#'}},
                                {{'#', '.'}, {'#', '#'}}, {{'.', '#'}, {'#', '#'}}};

    public void run(){
        int c = sc.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase() {
        input();
        System.out.println(cal());
    }

    private void input() {
        h = sc.nextInt();
        w = sc.nextInt();
        board = new char[h][w];

        for(int y=0; y<h; y++) {
            String str = sc.next();
            for (int x = 0; x < w; x++)
                board[y][x] = str.charAt(x);
        }
    }

    private int cal() {
        int a = -1;
        int b = -1;

        for(int y=0; y<h; y++) {
            for (int x = 0; x < w; x++)
                if (board[y][x] == '.') {
                    a = x;
                    b = y;
                    break;
                }
            if(a != -1) break;
        }

        if(a == -1) return 1;

        int result = 0;

        for(int type=0; type<4; type++)
            if(isDrawable(type, a, b)){
                draw(type, a, b);
                result += cal();
                undo(type, a, b);
            }

        return result;
    }

    private boolean isDrawable(int type, int x, int y) {
        for (int dy = 0; dy < 2; dy++)
                for (int dx = offset[type][0]; dx<(offset[type][0] + 2); dx++) {
                    if (x + dx < 0 || x + dx >= w || y + dy < 0 || y + dy >= h)
                        return false;

                    if (board[y + dy][x + dx] == '#' && block[type][dy][dx + offset[type][1]] == '#')
                        return false;
                }

        return true;
    }

    private void draw(int type, int x, int y) {
        for(int dy=0; dy<2; dy++)
            for(int dx=offset[type][0]; dx<(offset[type][0] + 2); dx++) {
                if (block[type][dy][dx + offset[type][1]] == '#') {
                    board[y + dy][x + dx] = block[type][dy][dx + offset[type][1]];
                }
            }
    }

    private void undo(int type, int x, int y) {
        for(int dy=0; dy<2; dy++)
            for(int dx=offset[type][0]; dx<(offset[type][0] + 2); dx++) {
                if (block[type][dy][dx + offset[type][1]] == '#') {
                    board[y + dy][x + dx] = '.';
                }
            }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
