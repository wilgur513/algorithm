package com.algospot.chapter11.kakuro2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static class Hint{
        int x;
        int y;
        int value;

        public Hint(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int q;
    private static int[][] board;
    private static List<Hint> horizon;
    private static List<Hint> vertical;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++){
            runSingleTestCase();
        }
    }

    private static void runSingleTestCase() {
        initialize();
        solve(0, 0);
        display();
    }

    private static void initialize() {
        n = scanner.nextInt();
        board = new int[n][n];

        for(int y=0; y<n; y++)
            for(int x=0; x<n; x++)
                board[y][x] = scanner.nextInt();

        q = scanner.nextInt();

        horizon = new ArrayList<>();
        vertical = new ArrayList<>();

        for(int i=0; i<q; i++){
            int y = scanner.nextInt();
            int x = scanner.nextInt();
            int direction = scanner.nextInt();
            int value = scanner.nextInt();

            if(direction == 0)
                horizon.add(new Hint(x, y, value));
            else
                vertical.add(new Hint(x, y, value));
        }
    }

    private static boolean solve(int x, int y) {
        if(y == n)
            return true;
        if(board[y][x] == 0)
            if(x == (n - 1))
                return solve(0, y + 1);
            else
                return solve(x + 1, y);

        Hint h = horizonHint(x, y);
        int prevSum = horizonPrevSum(x, y);
        int min = h.value - prevSum - horizonNextSum(x, y, 9);
        int max = h.value - prevSum - horizonNextSum(x, y, 1);

        Hint v = verticalHint(x, y);
        prevSum = verticalPrevSum(x, y);
        min = Math.max(min, v.value - prevSum - verticalNextSum(x, y, 9));
        max = Math.min(max, v.value - prevSum - verticalNextSum(x, y, 1));

        min = Math.max(min, 1);
        max = Math.min(max, 9);

        if(max <= 0 || min > 9)
            return false;

        for(int value=min; value<=max; value++){
            if(isUsed(x, y, value))
                continue;

            board[y][x] = value;

            if(x == (n - 1) && solve(0, y + 1))
                return true;
            else if(x != (n - 1) && solve(x + 1, y))
                return true;
        }

        return false;
    }

    private static Hint horizonHint(int x, int y) {
        Hint result = null;

        for(Hint h : horizon)
            if((y + 1 == h.y) && (x >= h.x) && (result == null || result.x < h.x))
                result = h;

        return result;
    }

    private static int horizonPrevSum(int x, int y) {
        int sum = 0;

        for(int dx=1; dx<=x; dx++)
            if(board[y][x - dx] > 0)
                sum += board[y][x - dx];
            else
                break;

        return sum;
    }

    private static int horizonNextSum(int x, int y, int value) {
        int sum = 0;

        for(int dx=1; dx<n-x; dx++)
            if(board[y][x + dx] > 0){
                sum += value;

                if(value == 9)
                    value--;
                else
                    value++;
            } else {
                break;
            }

        return sum;
    }

    private static Hint verticalHint(int x, int y) {
        Hint result = null;

        for(Hint h : vertical)
            if((x + 1 == h.x) && (y >= h.y) && (result == null || result.y < h.y))
                result = h;

        return result;
    }

    private static int verticalPrevSum(int x, int y) {
        int sum = 0;

        for(int dy=1; dy<=y; dy++)
            if(board[y - dy][x] > 0)
                sum += board[y - dy][x];
            else
                break;

        return sum;
    }

    private static int verticalNextSum(int x, int y, int value) {
        int sum = 0;

        for(int dy=1; dy<n-y; dy++)
            if(board[y + dy][x] > 0){
                sum += value;

                if(value == 9)
                    value--;
                else
                    value++;
            } else {
                break;
            }

        return sum;
    }

    private static boolean isUsed(int x, int y, int value) {
        for(int dx=1; dx<=x; dx++)
            if(board[y][x - dx] > 0){
                if(board[y][x - dx] == value)
                    return true;
            }else{
                break;
            }

        for(int dy=1; dy<=y; dy++)
            if(board[y - dy][x] > 0){
                if(board[y - dy][x] == value)
                    return true;
            }else{
                break;
            }

        return false;
    }

    public static void display(){
        for(int[] line : board){
            for(int value : line){
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        run();
    }
}
