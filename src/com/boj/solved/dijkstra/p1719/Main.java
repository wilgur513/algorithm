package com.boj.solved.dijkstra.p1719;

import java.io.*;
import java.util.*;

class Node {
    public final int to;
    public final int w;

    public Node(int to, int w) {
        this.to = to;
        this.w = w;
    }
}

class QueueNode implements Comparable<QueueNode>{
    int to;
    int w;
    int parent;

    public QueueNode(int to, int w, int parent) {
        this.to = to;
        this.w = w;
        this.parent = parent;
    }

    @Override
    public int compareTo(QueueNode o) {
        return Integer.compare(w, o.w);
    }
}

public class Main {
    private static final int INF = 987654321;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static List<Node>[] adj;
    private static String[][] result;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        adj = new List[n];

        for(int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken()) - 1;
            int to = Integer.parseInt(tokenizer.nextToken()) - 1;
            int w = Integer.parseInt(tokenizer.nextToken());

            adj[from].add(new Node(to, w));
            adj[to].add(new Node(from, w));
        }

        result = new String[n][n];
    }

    private static void solve() {
        for(int from = 0; from < n; from++) {
            int[] parent = dijkstra(from);

            for(int to = 0; to < n; to++) {
                if(from == to) {
                    System.out.print("- ");
                    continue;
                }

                System.out.print((parent[to] + 1) + " ");
            }
            System.out.println();
        }
    }

    private static int[] dijkstra(int from) {
        int[] distance = new int[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);
        Arrays.fill(distance, INF);
        distance[from] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));
        queue.add(new int[]{from, 0, -1});

        while(!queue.isEmpty()) {
            int[] node = queue.remove();
            int here = node[0];
            int w = node[1];
            int start = node[2];

            if(distance[here] < w) {
                continue;
            }

            for(Node n : adj[here]) {
                int there = n.to;
                int nextCost = w + n.w;

                if(nextCost < distance[there]) {
                    int nextStart = start == -1 ? there : start;
                    parent[there] = nextStart;
                    distance[there] = nextCost;
                    queue.add(new int[]{there, nextCost, nextStart});
                }
            }
        }

        return parent;
    }
}
