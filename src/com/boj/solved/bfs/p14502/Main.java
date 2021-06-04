package com.boj.solved.bfs.p14502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int[] DELTA_X = {1, -1, 0, 0};
    static final int[] DELTA_Y = {0, 0, 1, -1};
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));;
    static int n, m;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        initMapSize();
        initMap();
    }

    private static void initMapSize() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());
    }

    private static void initMap() throws IOException {
        map = new int[n][m];

        for(int y = 0; y < n; y++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for (int x = 0; x < m; x++){
                map[y][x] = Integer.valueOf(tokenizer.nextToken());
            }
        }
    }

    private static void solve() {
        System.out.println(maxSafetyAreaSize());
    }

    private static int maxSafetyAreaSize() {
        return maxSafetyAreaSize(0, 0, 0);
    }

    private static int maxSafetyAreaSize(int y, int x, int count) {
        if(count == 3)
            return safetyAreaSize();

        if(x == m || y == n){
            return x == m ? maxSafetyAreaSize(y + 1, 0, count) : 0;
        }

        int result = 0;

        if(map[y][x] == 0) {
            map[y][x] = 1;
            result = maxSafetyAreaSize(y, x + 1, count + 1);
            map[y][x] = 0;
        }

        return Math.max(result, maxSafetyAreaSize(y, x + 1, count));
    }

    private static int safetyAreaSize() {
        int[][] beforeSpread = cloneBeforeSpread();
        spreadVirus();
        int result = calSafetyAreaSize();
        map = beforeSpread;

        return result;
    }

    private static int[][] cloneBeforeSpread(){
        int[][] result = new int[n][m];

        for(int y = 0; y < n; y++)
            for(int x = 0; x < m; x++)
                result[y][x] = map[y][x];

        return result;
    }

    private static void spreadVirus(){
        Queue<int[]> queue = firstVirusArea();

        while(!queue.isEmpty()){
            int[] here = queue.remove();

            for(int i = 0; i < 4; i++){
                int nextY = here[0] + DELTA_Y[i];
                int nextX = here[1] + DELTA_X[i];

                if(isValid(nextY, nextX) && map[nextY][nextX] == 0){
                    map[nextY][nextX] = 2;
                    queue.add(new int[]{nextY, nextX});
                }
            }
        }
    }

    private static Queue<int[]> firstVirusArea() {
        Queue<int[]> queue = new LinkedList<>();

        for(int y = 0; y < n; y++)
            for(int x = 0; x < m; x++)
                if(map[y][x] == 2)
                    queue.add(new int[]{y, x});

        return queue;
    }

    private static boolean isValid(int y, int x) {
        return (0 <= y && y < n) && (0 <= x && x < m);
    }

    private static int calSafetyAreaSize() {
        int result = 0;

        for(int y = 0; y < n; y ++){
            for(int x = 0; x < m; x++){
                if(map[y][x] == 0)
                    result++;
            }
        }

        return result;
    }
}
