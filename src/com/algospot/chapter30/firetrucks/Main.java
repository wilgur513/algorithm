package com.algospot.chapter30.firetrucks;

import java.util.*;
import java.io.*;

class Vertex implements Comparable<Vertex>{
    int node;
    int w;

    public Vertex(int node, int w) {
        this.node = node;
        this.w = w;
    }


    @Override
    public int compareTo(Vertex o) {
        return w < o.w ? -1 : (w > o.w ? 1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        Vertex v = (Vertex) obj;

        return w == v.w && node == v.node;
    }
}

class Graph{
    int size;
    List<Vertex>[] adj;

    public Graph(int size) {
        this.size = size;
        adj = new List[size];

        for(int i = 0; i < size; i++)
            adj[i]= new ArrayList<>();
    }

    void addEdge(int v1, int v2, int w){
        adj[v1].add(new Vertex(v2, w));
    }

    int[] dijkstra(){
        int[] dist = new int[size];
        Arrays.fill(dist, 987654321);
        dist[0] = 0;

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(0, 0));

        while(!queue.isEmpty()){
            Vertex v = queue.remove();
            int here = v.node;
            int cost = v.w;

            if(dist[here] < cost)
                continue;

            for(Vertex vertex : adj[here]){
                int there = vertex.node;
                int nextCost = cost + vertex.w;

                if(dist[there] >= nextCost){
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
    private static int v, e, n ,m;
    private static Graph graph;
    private static int[] fire;
    private static int[] station;

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
        v = Integer.parseInt(tokenizer.nextToken());
        e = Integer.parseInt(tokenizer.nextToken());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        graph = new Graph(v + 1);

        for(int i = 0; i < e; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int v1 = Integer.parseInt(tokenizer.nextToken());
            int v2 = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());
            graph.addEdge(v1, v2, w);
            graph.addEdge(v2, v1, w);
        }

        fire = new int[n];
        tokenizer = new StringTokenizer(reader.readLine());

        for(int i = 0; i < n; i++)
            fire[i] = Integer.parseInt(tokenizer.nextToken());

        station = new int[m];
        tokenizer = new StringTokenizer(reader.readLine());

        for(int i = 0; i < m; i++){
            station[i] = Integer.parseInt(tokenizer.nextToken());
            graph.addEdge(0, station[i], 0);
        }
    }

    private static void solve() {
        int[] dist = graph.dijkstra();
        int result = 0;

        for(int v : fire)
            result += dist[v];

        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
