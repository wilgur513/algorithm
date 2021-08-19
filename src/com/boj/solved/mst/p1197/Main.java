package com.boj.solved.mst.p1197;

import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge>{
    public final int v1;
    public final int v2;
    public final int w;

    public Edge(int v1, int v2, int w) {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }

    @Override
    public int compareTo(Edge o) {
        return Integer.compare(w, o.w);
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int v, e;
    static List<Edge> edges;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        v = Integer.parseInt(tokenizer.nextToken());
        e = Integer.parseInt(tokenizer.nextToken());
        edges = new ArrayList<>();
        parent = new int[v];

        for(int i = 0; i < v; i++) {
            parent[i] = i;
        }

        for(int i = 0; i < e; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int v1 = Integer.parseInt(tokenizer.nextToken()) - 1;
            int v2 = Integer.parseInt(tokenizer.nextToken()) - 1;
            int w = Integer.parseInt(tokenizer.nextToken());
            edges.add(new Edge(v1, v2, w));
        }

        Collections.sort(edges);
    }

    private static void solve() {
        int result = 0;

        for(Edge e : edges) {
            int parentV1 = parent(e.v1);
            int parentV2 = parent(e.v2);

            if(parentV1 == parentV2) {
                continue;
            }

            union(parentV1, parentV2);
            result += e.w;
        }

        System.out.println(result);
    }

    private static int parent(int node) {
        if(parent[node] != node) {
            parent[node] = parent(parent[node]);
        }

        return parent[node];
    }

    public static void union(int a, int b) {
        int parentA = parent(a);
        int parentB = parent(b);

        if(parentA < parentB) {
            parent[parentB] = parentA;
        } else {
            parent[parentA] = parentB;
        }
    }
}
