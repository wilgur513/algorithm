package com.boj.solved.implement.p2580;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int[][] board = new int[9][9];

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        for(int y = 0; y < 9; y++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            for(int x = 0; x < 9; x++){
                board[y][x] = Integer.valueOf(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        cal(0, 0);
        display();
    }

    private static boolean cal(int y, int x) {
        if(y == 9)
            return true;

        if(x == 9) {
            return cal(y + 1, 0);
        }

        if(board[y][x] != 0) {
            return cal(y, x + 1);
        }

        for(int value = 1; value <=9; value++){
            if(!isAlreadyUsed(y, x, value)){
                board[y][x] = value;
                if(cal(y, x + 1))
                    return true;
                board[y][x] = 0;
            }
        }

        return false;
    }

    private static boolean isAlreadyUsed(int y, int x, int number){
        return isVerticalUsed(x, number) || isHorizontalUsed(y, number) || isSquareAreaUsed(y, x, number);
    }

    private static boolean isVerticalUsed(int x, int number) {
        for(int y = 0; y < 9; y++){
            if(board[y][x] == number)
                return true;
        }

        return false;
    }

    private static boolean isHorizontalUsed(int y, int number) {
        for(int x = 0; x < 9; x++){
            if(board[y][x] == number)
                return true;
        }

        return false;
    }

    private static boolean isSquareAreaUsed(int y, int x, int number){
        for(int dy = 0; dy < 3; dy++){
            for(int dx = 0; dx < 3; dx++){
                if(board[(y / 3)*3 + dy][(x / 3)*3 + dx] == number)
                    return true;
            }
        }

        return false;
    }

    private static void display() {
        for(int y = 0; y < 9; y++){
            for(int x = 0; x < 9; x++)
                System.out.print(board[y][x] + " ");
            System.out.println();
        }
    }
}
