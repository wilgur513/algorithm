package com.algospot.chapter9.tictactoe;

import java.util.Scanner;

public class Main {
    enum Result{
        LOSE(0), DRAW(1), WIN(2);

        private int value;

        private Result(int value){
            this.value = value;
        }

        public Result reversal(){
            if(value == 0)
                return WIN;
            else if(value == 2)
                return LOSE;
            else
                return DRAW;
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static char[][] board = new char[3][3];
    private static Result[] result = new Result[100000];

    public static void run(){
        int numberOfCase = scanner.nextInt();

        for(int i=0; i<numberOfCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        initialize();
        System.out.println(winner(board));
    }

    private static void initialize() {
        for(int y=0; y<3; y++) {
            String string = scanner.next();

            for (int x = 0; x < 3; x++) {
                board[y][x] = string.charAt(x);
            }
        }
    }

    private static String winner(char[][] board){
        char startPlayer = findPlayer(board);

        if(turn(board, startPlayer) == Result.WIN)
            return startPlayer == 'x' ? "x" : "o";
        else if(turn(board, startPlayer) == Result.LOSE)
            return startPlayer == 'x' ? "o" : "x";
        else
            return "TIE";
    }

    private static Result turn(char[][] board, char player) {
        int hash = boardHash(board);

        if(isLose(board, player))
            return Result.LOSE;

        if(isDraw(board))
            return Result.DRAW;

        if(result[hash] != null)
            return result[hash];

        result[hash] = Result.LOSE;

        for(int y=0; y<3; y++)
            for(int x=0; x<3; x++)
                if(board[y][x] == '.'){
                    board[y][x] = player;
                    char nextPlayer = player == 'x' ? 'o' : 'x';

                    if(result[hash].value < turn(board, nextPlayer).reversal().value)
                        result[hash] = turn(board, nextPlayer).reversal();

                    board[y][x] = '.';
                }


        return result[hash];
    }

    private static int boardHash(char[][] board){
        int result = 0;

        for(int y=0; y<3; y++)
            for(int x=0; x<3; x++){
                if(board[y][x] == 'x')
                    result += 1;
                else if(board[y][x] == 'o')
                    result += 2;

                result *= 3;
            }

        return result;
    }

    private static char findPlayer(char[][] board) {
        int numberOfX = 0;
        int numberOfO = 0;

        for(int y=0; y<3; y++)
            for(int x=0; x<3; x++)
                if(board[y][x] == 'x')
                    numberOfX++;
                else if(board[y][x] == 'o')
                    numberOfO++;

        return numberOfX == numberOfO ? 'x' : 'o';
    }

    private static boolean isLose(char[][] board, char player){
        String line = player == 'x' ? "ooo" : "xxx";

        if (isHorizontal(board, line) || isVertical(board, line) || isDiagonal(board, line))
            return true;

        return false;
    }

    private static boolean isDraw(char[][] board) {
        for(int y=0; y<3; y++)
            for (int x = 0; x < 3; x++)
                if(board[y][x] == '.')
                    return false;

        return true;
    }

    private static boolean isHorizontal(char[][] board, String line) {
        for(int y=0; y<3; y++)
            if(String.valueOf(board[y]).equals(line))
                return true;

        return false;
    }

    private static boolean isVertical(char[][] board, String line) {
        for(int x=0; x<3; x++) {
            String verticalLine = String.valueOf(new char[]{board[0][x], board[1][x], board[2][x]});

            if (verticalLine.equals(line))
                return true;
        }

        return false;
    }

    private static boolean isDiagonal(char[][] board, String line) {
        String diagonal1 = String.valueOf(new char[]{board[0][0], board[1][1], board[2][2]});
        String diagonal2 = String.valueOf(new char[]{board[0][2], board[1][1], board[2][0]});

        return diagonal1.equals(line) || diagonal2.equals(line);
    }


    public static void main(String[] args) {
        Main.run();
    }
}
