package com.boj.solved.bfs.p4991;

import java.io.*;
import java.util.*;

class Point{
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj){
        Point p = (Point)obj;
        return x == p.x && y == p.y;
    }

    public List<Point> nextPoints(){
        List<Point> result = new ArrayList<>();
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for(int i = 0; i < 4; i++)
            result.add(new Point(x + dx[i], y + dy[i]));

        return result;
    }
}

class Graph{
    int size;
    int[][] graph;
    int[][] cache;

    public Graph(int size) {
        this.size = size;
        graph = new int[size][size];
        cache = new int[size][1 << 11];
    }

    public void addEdge(int v1, int v2, int edge){
        graph[v1][v2] = edge;
        graph[v2][v1] = edge;
    }

    public int minDistance(){
        return cal(0, 1);
    }

    private int cal(int current, int bitMask){
        if(bitMask == (1 << size) - 1)
            return 0;

        if(cache[current][bitMask] != 0)
            return cache[current][bitMask];

        int result = Main.INF;

        for(int to = 1; to < size; to++){
            if(current != to && !isVisited(bitMask, to)){
                result = Math.min(result, cal(to, nextBitMask(bitMask, to)) + graph[current][to]);
            }
        }

        cache[current][bitMask] = result;
        return result;
    }

    private boolean isVisited(int bitMask, int point){
        return ((1 << (point)) & bitMask) > 0;
    }

    private int nextBitMask(int bitMask, int point){
        return bitMask | (1 << (point));
    }
}

public class Main {
    public static final int INF = 987654321;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int w = -1, h = -1;
    private static char[][] map;

    public static void main(String[] args) throws IOException {
        while(true){
            inputSize();
            if(w == 0 && h == 0)
                break;
            inputMap();
            solve();
        }
    }

    private static void inputSize() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        w = Integer.valueOf(tokenizer.nextToken());
        h = Integer.valueOf(tokenizer.nextToken());
    }

    private static void inputMap() throws IOException {
        map = new char[h][w];

        for(int y = 0; y < h; y++){
            String s = reader.readLine();

            for(int x = 0; x < w; x++){
                map[y][x] = s.charAt(x);
            }
        }
    }

    private static void solve() {
        Graph graph = initGraph();
        int result = graph.minDistance();
        System.out.println(result == INF ? -1 : result);
    }

    private static Graph initGraph() {
        List<Point> points = findRobotAndDirtyPoint();
        Graph graph = new Graph(points.size());

        for(int vertex = 0; vertex < points.size(); vertex++){
            for(int adj = vertex + 1; adj < points.size(); adj++){
                int edge = calEdge(points.get(vertex), points.get(adj));
                graph.addEdge(vertex, adj, edge);
            }
        }

        return graph;
    }

    private static List<Point> findRobotAndDirtyPoint() {
        List<Point> result = new ArrayList<>();
        result.add(findRobot());

        for(int y = 0; y < h; y++){
            for(int x = 0; x < w; x++){
                if(map[y][x] == '*'){
                   result.add(new Point(x, y));
                }
            }
        }

        return result;
    }

    private static Point findRobot() {
        for(int y = 0; y < h; y++)
            for(int x = 0; x < w; x++)
                if(map[y][x] == 'o')
                    return new Point(x, y);

        throw new AssertionError();
    }

    private static int calEdge(Point from, Point to) {
        Queue<Point> queue = new LinkedList<>();
        int[][] distance = new int[h][w];
        boolean[][] visited = new boolean[h][w];

        for(int y = 0; y < h; y++)
            Arrays.fill(distance[y], INF);
        distance[from.y][from.x] = 0;
        queue.add(from);

        while(!queue.isEmpty()){
            Point p = queue.remove();

            for(Point next : p.nextPoints()){
                if(next.equals(to)){
                    return distance[p.y][p.x] + 1;
                }

                if(isValidRange(next) && isNotFurniture(next) && !visited[next.y][next.x]){
                    distance[next.y][next.x] = distance[p.y][p.x] + 1;
                    queue.add(next);
                    visited[next.y][next.x] = true;
                }
            }
        }

        return INF;
    }

    private static boolean isValidRange(Point p){
        return (0 <= p.x && p.x < w) && (0 <= p.y && p.y < h);
    }

    private static boolean isNotFurniture(Point p){
        return map[p.y][p.x] != 'x';
    }
}
