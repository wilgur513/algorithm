package com.boj.solved.implement.p14503;

import java.util.*;
import java.io.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n, m, r, c, d;
    static boolean[][] visited;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        r = Integer.valueOf(tokenizer.nextToken());
        c = Integer.valueOf(tokenizer.nextToken());
        d = Integer.valueOf(tokenizer.nextToken());

        map = new int[n][m];
        visited = new boolean[n][m];

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < m; j++){
                map[i][j] = Integer.valueOf(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        System.out.println(cal(r, c, d));
    }

    private static int cal(int r, int c, int d){
        int count = visited[r][c] ? 0 : 1;
        visited[r][c] = true;

        if(isCleanAllAdj(r, c)){
            int[] pos = backward(r, c, d);

            if(isWall(pos[0], pos[1]))
                return count;

            return cal(pos[0], pos[1], d) + count;
        }

        int[] pos = leftPos(r, c, d);
        int leftTurn = turnLeft(d);

        if(!visited[pos[0]][pos[1]] && !isWall(pos[0], pos[1])){
            return cal(pos[0], pos[1], leftTurn) + count;
        }

        return cal(r, c, leftTurn) + count;
    }

    private static boolean isCleanAllAdj(int r, int c){
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for(int i = 0; i < 4; i++){
            int y = r + dy[i];
            int x = c + dx[i];

            if(map[y][x] != 1 && !visited[y][x]){
                return false;
            }
        }

        return true;
    }

    private static int[] backward(int r, int c, int d){
        if(d == 0)
            return new int[]{r + 1, c};
        else if(d == 1)
            return new int[]{r, c - 1};
        else if(d == 2)
            return new int[]{r - 1, c};

        return new int[]{r, c + 1};
    }

    private static int turnLeft(int d){
        if(d == 0)
            return 3;
        else if(d == 1)
            return 0;
        else if(d == 2)
            return 1;

        return 2;
    }

    private static int[] leftPos(int r, int c, int d){
        if(d == 0)
            return new int[]{r, c - 1};
        else if(d == 1)
            return new int[]{r - 1, c};
        else if(d == 2)
            return new int[]{r, c + 1};

        return new int[]{r + 1, c};
    }

    private static boolean isWall(int r, int c){
        return map[r][c] == 1;
    }
}
