package com.algospot.chapter31.lan;

import java.util.*;
import java.io.*;

class Vertex{
    int x;
    int y;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Edge implements Comparable<Edge>{
    int v1, v2;
    double w;

    public Edge(int v1, int v2, double w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }

    @Override
    public int compareTo(Edge o) {
        return w < o.w ? -1 : (w > o.w ? 1 : 0);
    }
}

class Graph{
    int size;
    Vertex[] v;
    double[][] adj;

    public Graph(int size) {
        this.size = size;
        v = new Vertex[size];
        adj = new double[size][size];
    }

    void addVertex(int i, int x, int y){
        v[i] = new Vertex(x, y);
    }

    void initEdge(){
        for(int i = 0; i < size; i++)
            for(int j = i + 1; j < size; j++){
                if(i == j)
                    adj[i][j] = 0.0;
                else {
                    adj[i][j] = distance(v[i], v[j]);
                    adj[j][i] = distance(v[i], v[j]);
                }
            }
    }

    void setZeroEdge(int a, int b){
        adj[a][b] = 0.0;
        adj[b][a] = 0.0;
    }

    double kruskal(){
        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < size; i++)
            for(int j = i + 1; j < size; j++)
                edges.add(new Edge(i, j, adj[i][j]));
        Collections.sort(edges);

        Union sets = new Union(size);
        double result = 0.0;

        for(Edge e : edges){
            int v1 = e.v1;
            int v2 = e.v2;
            double w = e.w;

            if(sets.find(v1) == sets.find(v2))
                continue;

            sets.merge(v1, v2);
            result += w;

            if(isEnd(sets))
                break;
        }

        return result;
    }

    boolean isEnd(Union sets){
        int p = sets.find(0);
        for(int i = 1; i < size; i++)
            if(p != sets.find(i))
                return false;
        return true;
    }

    private double distance(Vertex v1, Vertex v2) {
        int dx = v1.x - v2.x;
        int dy = v1.y - v2.y;

        return Math.sqrt(dx*dx + dy*dy);
    }
}

class Union{
    int size;
    int[] p;
    int[] c;

    public Union(int size) {
        this.size = size;
        p = new int[size];
        c = new int[size];

        for(int i = 0; i < size; i++)
            p[i] = i;
    }

    int find(int a){
        if(p[a] == a)
            return a;

        p[a] = find(p[a]);
        return p[a];
    }

    void merge(int a, int b){
        int pa = find(a);
        int pb = find(b);

        if(c[pa] <= c[pb]){
            c[pb] += c[pa];
            p[pa] = pb;
        }else{
            c[pa] += c[pb];
            p[pb] = pa;
        }
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static Graph graph;

    private static void run() throws IOException {
        int number = Integer.parseInt(reader.readLine());

        for(int i = 0; i < number; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        int[] x = new int[n];
        int[] y = new int[n];

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++)
            x[i] = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++)
            y[i] = Integer.parseInt(tokenizer.nextToken());

        graph = new Graph(n);

        for(int i = 0; i < n; i++)
            graph.addVertex(i, x[i], y[i]);

        graph.initEdge();

        for(int i = 0; i < m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());

            graph.setZeroEdge(a, b);
        }
    }

    private static void solve() {
        System.out.println(graph.kruskal());
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
