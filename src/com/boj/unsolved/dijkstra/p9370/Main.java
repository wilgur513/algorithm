package com.boj.unsolved.dijkstra.p9370;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node implements Comparable<Node>{
    int to;
    long weight;

    public Node(int to, long weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return weight < o.weight ? -1 : (weight > o.weight ? 1 : 0);
    }
}

class Graph {
    final static int INF = 987654321;
    int size;
    List<Node>[] adj;

    public Graph(int size) {
        this.size = size;
        adj = new List[size];

        for(int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int v1, int v2, int w) {
        adj[v1].add(new Node(v2, w));
        adj[v2].add(new Node(v1, w));
    }

    public long edge(int v1, int v2) {
        for(Node node : adj[v1]) {
            if(node.to == v2) {
                return node.weight;
            }
        }

        throw new AssertionError();
    }

    public long dijk(int start, int end) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        long[] dist = new long[size];
        Arrays.fill(dist, INF);

        dist[start] = 0;
        queue.add(new Node(start, 0));

        while(!queue.isEmpty()) {
            Node node = queue.remove();
            int here = node.to;
            long weight = node.weight;

            if(dist[here] < weight)
                continue;

            for(Node n : adj[here]) {
                int there = n.to;
                long cost = weight + n.weight;

                if(cost < dist[there]) {
                    dist[there] = cost;
                    queue.add(new Node(there, cost));
                }
            }
        }

        return dist[end];
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n, m, t, s, g, h;
    static int[] subs;
    static Graph graph;

    public static void main(String[] args) throws IOException {
        int testCase = Integer.parseInt(reader.readLine());

        for(int i = 0; i < testCase; i++) {
            runSingleTestCase();
        }
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        t = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        s = Integer.parseInt(tokenizer.nextToken());
        g = Integer.parseInt(tokenizer.nextToken());
        h = Integer.parseInt(tokenizer.nextToken());

        graph = new Graph(n);

        for(int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int v1 = Integer.parseInt(tokenizer.nextToken());
            int v2 = Integer.parseInt(tokenizer.nextToken());
            int w = Integer.parseInt(tokenizer.nextToken());

            graph.addEdge(v1 - 1, v2 - 1, w);
        }

        subs = new int[t];

        for(int i = 0; i < t; i++) {
            subs[i] = Integer.parseInt(reader.readLine());
        }
    }

    private static void solve() {
        List<Integer> result = new ArrayList<>();
        for(int sub :subs) {
            if(graph.dijk(s - 1, sub - 1) == distance(s - 1, g - 1, h - 1, sub - 1) ||
            graph.dijk(s - 1, sub - 1) == distance(s - 1, h - 1, g - 1, sub - 1)){
                result.add(sub);
            }
        }
        result.stream().sorted().map(v -> v + " ").forEach(System.out::print);
        System.out.println();
    }

    private static long distance(int s, int g, int h, int sub) {
        return graph.dijk(s, g) + graph.edge(g, h) + graph.dijk(h, sub);
    }
}
