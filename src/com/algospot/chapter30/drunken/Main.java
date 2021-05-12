package com.algospot.chapter30.drunken;

import java.util.*;
import java.io.*;

class Vertex implements Comparable<Vertex>{
    int delay;
    int node;

    public Vertex(int delay, int node) {
        this.delay = delay;
        this.node = node;
    }


    @Override
    public int compareTo(Vertex o) {
        return delay > o.delay ? 1 : (delay < o.delay ? -1 : 0);
    }
}

class Graph{
    private static final int INF = 987654321;
    int size;
    int[] times;
    int[][] adj;
    int[][] max;

    public Graph(int size) {
        this.size = size;
        times = new int[size];
        adj = new int[size][size];
        max = new int[size][size];

        for(int i = 0; i < size; i++)
            Arrays.fill(adj[i], INF);
    }

    void addTime(int v, int t){
        times[v] = t;
    }

    void addEdge(int a, int b, int c){
        adj[a][b] = Math.min(adj[a][b], c);
        adj[b][a] = Math.min(adj[b][a], c);
    }

    void floyd(){
        List<Vertex> list = new ArrayList<>();
        for(int i = 0; i < size; i++)
            list.add(new Vertex(times[i], i));
        Collections.sort(list);

        for(int i = 0; i < size; i++)
            for(int j = 0; j< size; j++)
                if(i == j)
                    max[i][j] = 0;
                else
                    max[i][j] = adj[i][j];

        for(int k = 0; k < size; k++) {
            int w = list.get(k).node;

            for (int i = 0; i < size; i++)
                for (int j = 0; j < size; j++) {
                    adj[i][j] = Math.min(adj[i][j], adj[i][w] + adj[w][j]);
                    max[i][j] = Math.min(adj[i][w] + times[w] + adj[w][j], max[i][j]);
                }
        }
    }

    int path(int start, int end){
        return max[start][end];
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int v, e;
    private static int[] start, end;
    private static Graph graph;

    private static void run() throws IOException {
        inputData();
        runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        v = Integer.parseInt(tokenizer.nextToken());
        e = Integer.parseInt(tokenizer.nextToken());
        graph = new Graph(v);

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < v; i++)
            graph.addTime(i, Integer.parseInt(tokenizer.nextToken()));

        for(int i = 0; i < e; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            graph.addEdge(a - 1, b - 1, c);
        }

        int size = Integer.parseInt(reader.readLine());
        start = new int[size];
        end = new int[size];

        for(int i = 0; i < size; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            start[i] = Integer.parseInt(tokenizer.nextToken());
            end[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        graph.floyd();

        for(int i = 0; i < start.length; i++)
            System.out.println(graph.path(start[i] - 1, end[i] - 1));
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
