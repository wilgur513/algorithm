package com.algospot.chapter30.routing;

import java.util.*;
import java.io.*;

class Vertex implements Comparable<Vertex>{
    double w;
    int number;

    public Vertex(int number, double w) {
        this.number = number;
        this.w = w;
    }

    @Override
    public int compareTo(Vertex o) {
        return w > o.w ? 1 : (w < o.w ? -1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        Vertex v = (Vertex)obj;

        return w == v.w && number == v.number;
    }
}

class Graph{
    int size;
    List<Vertex>[] adj;
    double dist[];

    public Graph(int size) {
        this.size = size;
        adj = new List[size];
        dist = new double[size];

        for(int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
            dist[i] = Double.MAX_VALUE;
        }
    }

    public void addEdge(int v1, int v2, double w){
        adj[v1].add(new Vertex(v2, Math.log(w)));
    }

    public double[] dijkstra(){
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(0, 0.0));
        dist[0] = 0.0;

        while(!queue.isEmpty()){
            Vertex v = queue.remove();
            int here = v.number;
            double cost = v.w;

            if(dist[here] < cost)
                continue;

            for(int i = 0; i < adj[here].size(); i++){
                v = adj[here].get(i);
                int there = v.number;
                double nextCost = cost + v.w;

                if(nextCost < dist[there]){
                    dist[there] = nextCost;
                    queue.add(new Vertex(there, nextCost));
                }
            }
        }

        return dist;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static Graph graph;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i = 0; i < numberOfTestCase; i++)
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
        graph = new Graph(n);

        for(int i = 0; i < m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int v1 = Integer.parseInt(tokenizer.nextToken());
            int v2 = Integer.parseInt(tokenizer.nextToken());
            double w = Double.parseDouble(tokenizer.nextToken());

            graph.addEdge(v1, v2, w);
        }
    }

    private static void solve() {
        double[] dist = graph.dijkstra();

        System.out.printf("%.10f\n", Math.exp(dist[n-1]));
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
