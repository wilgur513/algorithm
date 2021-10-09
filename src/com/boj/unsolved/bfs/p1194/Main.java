package com.boj.unsolved.bfs.p1194;

import java.util.*;
import java.io.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static char[][] map;
    private static boolean[][][] visited;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        inputSize();
        inputMap();
    }

    private static void inputSize() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
    }

    private static void inputMap() throws IOException {
        map = new char[50][50];
        visited = new boolean[50][50][64];

        for(int i = 0; i < n; i++) {
            String s = reader.readLine();

            for(int j = 0; j < m; j++){
                map[i][j] = s.charAt(j);
            }
        }
    }

    private static void solve() {
        int[] start = start();
        int result = cal(start[0], start[1]);
        System.out.println(result);
    }

    private static int[] start(){
        int y = 0, x;

        for(; y < n; y++) {
            for (x = 0; x < m; x++) {
                if(map[y][x] == '0')
                    return new int[]{y, x};
            }
        }

        throw new AssertionError();
    }

    private static int cal(int startY, int startX) {
        int[] dx = {0 ,0 ,1 , -1};
        int[] dy = {1, -1, 0, 0};

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX, 0, 0});
        visited[startY][startX][0] = true;

        while(!queue.isEmpty()){
            int[] here = queue.remove();
            int y = here[0];
            int x = here[1];
            int key = here[2];
            int cost = here[3];
            int nextKey = key;

            if(isKey(y, x)){
                int index = map[y][x] - 'a';
                nextKey |= 1 << index;
            }

            for(int i = 0; i < 4; i++){
                int nextX = x + dx[i];
                int nextY = y + dy[i];

                if(isValid(nextY, nextX)){
                    if(isExit(nextY, nextX)){
                        return cost + 1;
                    }

                    if(!visited[nextY][nextX][nextKey] && !isWall(nextY, nextX)){
                        if(!isDoor(nextY, nextX) || isDoor(nextY, nextX) && hasKey(map[nextY][nextX], nextKey)){
                            visited[nextY][nextX][nextKey] = true;
                            queue.add(new int[]{nextY, nextX, nextKey, cost + 1});
                        }
                    }
                }
            }
        }

        return -1;
    }

    private static boolean isDoor(int y, int x){
        return map[y][x] >= 'A' && map[y][x] <= 'F';
    }

    private static boolean hasKey(char door, int key){
        int index = door - 'A';
        return (key & (1 << index)) > 0;
    }

    private static boolean isExit(int y, int x){
        return map[y][x] == '1';
    }

    private static boolean isWall(int y, int x){
        return map[y][x] == '#';
    }

    private static boolean isValid(int y, int x){
        return (y >= 0 && y < n) && (x >= 0 && x < m);
    }

    private static boolean isKey(int y, int x){
        return map[y][x] >= 'a' && map[y][x] <= 'f';
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
