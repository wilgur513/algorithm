package com.boj.unsolved.dijkstra.p1238;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

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

    public Graph(int size){
        this.size = size;
        adj = new List[size];

        for(int i = 0; i < size; i++)
            adj[i] = new ArrayList<>();
    }

    public void addEdge(int from, int to, int cost){
        adj[from - 1].add(new Vertex(to - 1, cost));
    }

    public int dijkstra(int from, int to){
        int[] dist = new int[size];
        Arrays.fill(dist, 987654321);
        dist[from] = 0;

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(from, 0));

        while(!queue.isEmpty()){
            Vertex v = queue.remove();
            int here = v.node;
            int cost = v.w;

            if(dist[here] < cost)
                continue;

            for(Vertex vertex : adj[here]){
                int there = vertex.node;
                int nextCost = cost + vertex.w;

                if(dist[there] > nextCost){
                    dist[there] = nextCost;
                    queue.add(new Vertex(there, nextCost));
                }
            }
        }

        return dist[to];
    }
}

public class Main {
    static int n, x;
    static Graph graph;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.valueOf(tokenizer.nextToken());
        int m = Integer.valueOf(tokenizer.nextToken());
        x = Integer.valueOf(tokenizer.nextToken());
        graph = new Graph(n);

        for(int i = 0; i < m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.valueOf(tokenizer.nextToken());
            int to = Integer.valueOf(tokenizer.nextToken());
            int cost = Integer.valueOf(tokenizer.nextToken());
            graph.addEdge(from, to, cost);
        }
    }

    private static void solve() {
        int result = -1;

        for(int from = 0; from < n; from++){
            result = Math.max(result, graph.dijkstra(from, x - 1) + graph.dijkstra(x - 1, from));
        }

        System.out.println(result);
    }
}
