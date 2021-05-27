package com.boj.solved.bfs.p6593;

import java.io.*;
import java.util.*;

public class Main {
    static class Position{
        final int layer;
        final int y;
        final int x;

        public Position(int layer, int y, int x) {
            this.layer = layer;
            this.y = y;
            this.x = x;
        }

        public List<Position> adjacentPositions(){
            List<Position> result = new ArrayList<>();

            int[] dl = {0, 0, 0, 0, 1, -1};
            int[] dy = {1, -1, 0, 0, 0, 0};
            int[] dx = {0, 0, 1, -1, 0, 0};

            for(int i = 0; i < 6; i++)
                if(isValid(layer + dl[i], y + dy[i], x + dx[i]) && !isGold(layer + dl[i], y + dy[i], x + dx[i]))
                    result.add(new Position(layer + dl[i], y + dy[i], x + dx[i]));

            return result;
        }

        private boolean isValid(int layer, int y, int x){
            return (layer >= 0 && layer < l) && (y >= 0 && y < r) && (x >= 0 && x < c);
        }

        private boolean isGold(int layer, int y, int x){
            return map[layer][y][x] == '#';
        }
    }

    static class Node{
        Position pos;
        int minute;

        public Node(Position pos, int minute) {
            this.pos = pos;
            this.minute = minute;
        }
    }

    private static final int INF = 987654321;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int l, r, c;
    private static char[][][] map;
    private static boolean[][][] visited;
    private static Position start;

    public static void main(String[] args) throws IOException {
        runTestCases();
    }

    private static void runTestCases() throws IOException {
        while(true){
            inputMapSize();

            if(isEndTest())
                break;

            runSingleTestCase();
        }
    }

    private static void inputMapSize() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        l = Integer.valueOf(tokenizer.nextToken());
        r = Integer.valueOf(tokenizer.nextToken());
        c = Integer.valueOf(tokenizer.nextToken());
    }

    private static boolean isEndTest(){
        return l == 0 && r == 0 && c == 0;
    }

    private static void runSingleTestCase() throws IOException {
        initializeData();
        solve();
    }

    private static void initializeData() throws IOException {
        map = new char[l][r][c];
        visited = new boolean[l][r][c];

        for(int layer = 0; layer < l; layer++){
            for(int y = 0; y < r; y++){
                String s = reader.readLine();

                for(int x = 0; x < c; x++){
                    map[layer][y][x] = s.charAt(x);

                    if(map[layer][y][x] == 'S')
                        start = new Position(layer, y, x);
                }
            }
            reader.readLine();
        }
    }

    private static void solve() {
        int result = minMoveToEscape();

        if(result == INF)
            System.out.println("Trapped!");
        else
            System.out.println("Escaped in " + result + " minute(s).");
    }

    private static int minMoveToEscape() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start, 0));

        while(!queue.isEmpty()){
            Node node = queue.remove();
            Position pos = node.pos;
            int minute = node.minute;

            if(isEscapeDoor(pos.layer, pos.y, pos.x))
                return minute;

            for(Position adj : pos.adjacentPositions()){
                if(!visited[adj.layer][adj.y][adj.x]) {
                    queue.add(new Node(adj, minute + 1));
                    visited[adj.layer][adj.y][adj.x] = true;
                }
            }
        }

        return INF;
    }

    private static boolean isEscapeDoor(int l, int y, int x){
        return map[l][y][x] == 'E';
    }
}
