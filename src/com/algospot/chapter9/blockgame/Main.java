package com.algospot.chapter9.blockgame;

import java.util.*;

public class Main {
    private static List<Integer> blocks;
    private static Scanner scanner = new Scanner(System.in);
    private static byte[] result = new byte[1 << 25];
    private static int board;

    private static void run() {
        predefineBlocks();
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void predefineBlocks() {
        blocks = new ArrayList<>();

        for(int x=0; x<4; x++)
            for(int y=0; y<4; y++){
                blocks.add(blockOneByOne(x, y) + blockOneByOne(x + 1, y) + blockOneByOne(x, y + 1));
                blocks.add(blockOneByOne(x, y) + blockOneByOne(x + 1, y) + blockOneByOne(x + 1, y + 1));
                blocks.add(blockOneByOne(x, y) + blockOneByOne(x, y + 1) + blockOneByOne(x + 1, y + 1));
                blocks.add(blockOneByOne(x + 1, y) + blockOneByOne(x, y + 1) + blockOneByOne(x + 1, y + 1));
            }

        for(int x=0; x<5; x++)
            for(int y=0; y<4; y++)
                blocks.add(blockOneByOne(x, y) + blockOneByOne(x, y + 1));

        for(int x=0; x<4; x++)
            for(int y=0; y<5; y++)
                blocks.add(blockOneByOne(x, y) + blockOneByOne(x + 1, y));
    }

    private static void runSingleTestCase() {
        inputData();
        solve();
    }

    private static void inputData() {
        board = 0;

        for(int y=0; y<5; y++){
            String str = scanner.next();

            for(int x=0; x<5; x++){
                if(str.charAt(x) == '#')
                    board |= 1 << (5*y + x);
            }
        }
    }

    private static void solve() {
        if(play(board) == -1)
            System.out.println("LOSING");
        else
            System.out.println("WINNING");
    }

    private static byte play(int board) {
        if(result[board] != 0)
            return result[board];

        result[board] = -1;

        for(int block : blocks){
            if((block & board) == 0)
                if(play(board | block) == -1) {
                    result[board] = 1;
                    break;
                }
        }

        return result[board];
    }

    private static int blockOneByOne(int x, int y){
        return 1 << 5*y + x;
    }

    public static void main(String[] args) {
        run();
    }
}
