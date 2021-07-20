package com.boj.unsolved.bfs.p1600;

import java.util.*;
import java.io.*;

public class Main {
    static class Position{
        final int y;
        final int x;
        final int k;

        public Position(int y, int x, int k) {
            this.y = y;
            this.x = x;
            this.k = k;
        }

        public List<Position> next(){
            List<Position> result = new ArrayList<>();

            if(k > 0){
                int[] dy = {-2, -1, -2, -1,  1,  2, 2, 1};
                int[] dx = {-1, -2,  1,  2, -2, -1, 1, 2};

                for(int i = 0; i < 8; i++)
                    if(isValid(y + dy[i], x + dx[i]) && !isObstacle(y + dy[i], x + dx[i]))
                        result.add(new Position(y + dy[i], x + dx[i], k - 1));
            }

            int[] dy = {1, -1, 0, 0};
            int[] dx = {0, 0, 1, -1};

            for(int i = 0; i < 4; i++)
                if(isValid(y + dy[i], x + dx[i]) && !isObstacle(y + dy[i], x + dx[i]))
                    result.add(new Position(y + dy[i], x + dx[i], k));

            return result;
        }

        private boolean isValid(int y, int x){
            return (x >= 0 && x < w) && (y >= 0 && y < h);
        }

        private static boolean isObstacle(int y, int x) {
            return map[y][x] == 1;
        }
    }

    static class Node{
        Position pos;
        int move;

        public Node(Position pos, int move) {
            this.pos = pos;
            this.move = move;
        }
    }

    private static final int INF = 987654321;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int k, w, h;
    static int[][] map;
    static boolean[][][] visited;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        k = Integer.valueOf(reader.readLine());

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        w = Integer.valueOf(tokenizer.nextToken());
        h = Integer.valueOf(tokenizer.nextToken());

        map = new int[h][w];

        for(int y = 0; y < h; y++) {
            tokenizer = new StringTokenizer(reader.readLine());

            for (int x = 0; x < w; x++) {
                map[y][x] = Integer.valueOf(tokenizer.nextToken());
            }
        }

        visited = new boolean[h][w][k + 1];
    }

    private static void solve() {
        System.out.println(cal());
    }

    private static int cal(){
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(new Position(0, 0, k), 0));
        visited[0][0][k] = true;
        int result = INF;

        while(!queue.isEmpty()){
            Node node = queue.remove();
            Position pos = node.pos;
            int move = node.move;

            if(pos.y == h - 1 && pos.x == w - 1) {
                return move;
            }

            for(Position next : pos.next()){
                if(!visited[next.y][next.x][next.k]){
                    queue.add(new Node(next, move + 1));
                    visited[next.y][next.x][next.k] = true;
                }
            }
        }

        return -1;
    }
}
