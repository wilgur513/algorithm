package com.algospot.chapter11.boardcover2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Objects;

class Point implements Comparable<Point>{
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point o) {
        if(o.x < x)
            return 1;

        if(o.x > x)
            return -1;

        return o.y == y ? 0 : o.y < y ? 1 : -1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Point){
            Point p = (Point)obj;

            return p.x == x && p.y == y ? true : false;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x)*27 + Objects.hashCode(y);
    }
}


class Board {
    private int[][] board;

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean putBlock(int x, int y, List<Point> block) {
        if(!isCanPutBlock(x, y, block))
            return false;

        for(Point p : block){
            board[y + p.y][x + p.x] = 1;
        }

        return true;
    }

    public void takeBlock(int x, int y, List<Point> block) {
        for(Point p : block){
            board[y + p.y][x + p.x] = 0;
        }
    }

    public void blockOneCell(int x, int y) {
        board[y][x] = 1;
    }

    public void takeOneCell(int x, int y) {
        board[y][x] = 0;
    }

    public Point getFirstBlankCell() {
        for(int y=0; y<board.length; y++)
            for(int x=0; x<board[0].length; x++)
                if(board[y][x] == 0)
                    return new Point(x, y);

        return new Point(-1, -1);
    }

    private boolean isCanPutBlock(int x, int y, List<Point> block) {
        for(Point p : block){
            if(!isValidRange(x, y, p)){
                return false;
            }

            if(board[y + p.y][x + p.x] == 1)
                return false;
        }

        return true;
    }

    private boolean isValidRange(int x, int y, Point p) {
        int h = board.length;
        int w = board[0].length;

        return (x + p.x >= 0 && x + p.x < w) && (y + p.y >= 0 && y + p.y < h);
    }
}

class Blocks {
    private int[][] block;

    public void setBlock(int[][] block) {
        this.block = block;
    }

    public Set<List<Point>> generate() {
        Set<List<Point>> result = new HashSet<>();

        for(int i=0; i<4; i++) {
            result.add(convert());
            block = rotate();
        }

        return result;
    }

    private int[][] rotate() {
        int h = block.length;
        int w = block[0].length;

        int[][] result = new int[w][h];

        for(int y=0; y<h; y++)
            for(int x=0; x<w; x++){
                result[x][h - y - 1] = block[y][x];
            }

        return result;
    }

    private List<Point> convert() {
        List<Point> result = new ArrayList<>();
        int h = block.length;
        int w = block[0].length;

        int originX = -1, originY = -1;

        for(int y=0; y<h; y++)
            for(int x=0; x<w; x++)
                if (block[y][x] == 1) {
                    if (originX == -1 && originY == -1) {
                        originX = x;
                        originY = y;
                    }

                    result.add(new Point(x - originX, y - originY));
                }

        Collections.sort(result);

        return result;
    }
}


public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Blocks blocks;
    private static Board board;
    private static Set<List<Point>> set;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        System.out.println(solve(board));
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int h = Integer.parseInt(tokenizer.nextToken());
        int w = Integer.parseInt(tokenizer.nextToken());
        int r = Integer.parseInt(tokenizer.nextToken());
        int c = Integer.parseInt(tokenizer.nextToken());

        int[][] b = new int[h][w];
        int[][] block = new int[r][c];

        for(int y=0; y<h; y++) {
            String line = reader.readLine();

            for(int x=0; x<w; x++)
                b[y][x] = line.charAt(x) == '#' ? 1 : 0;
        }

        for(int y=0; y<r; y++) {
            String line = reader.readLine();

            for(int x=0; x<c; x++)
                block[y][x] = line.charAt(x) == '#' ? 1 : 0;
        }

        board = new Board();
        blocks = new Blocks();

        board.setBoard(b);
        blocks.setBlock(block);
        set = blocks.generate();
    }

    public static int solve(Board board) {
        int result = 0;

        Point p = board.getFirstBlankCell();

        if (p.x == -1)
            return 0;

        for (List<Point> block : set) {
            if (board.putBlock(p.x, p.y, block)) {
                result = Math.max(result, solve(board) + 1);
                board.takeBlock(p.x, p.y, block);
            }
        }

        board.blockOneCell(p.x, p.y);
        result = Math.max(result, solve(board));
        board.takeOneCell(p.x, p.y);

        return result;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
