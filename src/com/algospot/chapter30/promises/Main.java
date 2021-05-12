package com.algospot.chapter30.promises;

import java.util.*;
import java.io.*;

class Graph{
    private static final long INF = 987654321L;
    int size;
    long[][] adj;

    public Graph(int size) {
        this.size = size;
        adj = new long[size][size];

        for(int i = 0; i < size; i++)
            Arrays.fill(adj[i], INF);
    }

    void addEdge(int a, int b, int c){
        adj[a][b] = Math.min(adj[a][b], c);
        adj[b][a] = Math.min(adj[b][a], c);
    }

    void floyd(){
        for(int i = 0; i < size; i++)
            adj[i][i] = 0;

        for(int k = 0; k < size; k++)
            for(int i = 0; i < size; i++)
                for(int j = 0; j < size; j++)
                    adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
    }

    boolean isUseful(int a, int b, int c){
        if(adj[a][b] <= c)
            return false;

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++){
                adj[i][j] = Math.min(adj[i][j], adj[i][a] + c + adj[b][j]);
                adj[i][j] = Math.min(adj[i][j], adj[i][b] + c + adj[a][j]);
            }

        return true;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int v, m, n;
    private static int[] a, b, c;
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
        v = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        n = Integer.parseInt(tokenizer.nextToken());
        graph = new Graph(v);

        for(int i = 0; i < m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int c = Integer.parseInt(tokenizer.nextToken());

            graph.addEdge(a, b, c);
        }

        a = new int[n];
        b = new int[n];
        c = new int[n];

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            a[i] = Integer.parseInt(tokenizer.nextToken());
            b[i] = Integer.parseInt(tokenizer.nextToken());
            c[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        graph.floyd();
        int result = 0;

        for(int i = 0; i < n; i++)
            if(!graph.isUseful(a[i], b[i], c[i]))
                result++;

        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
