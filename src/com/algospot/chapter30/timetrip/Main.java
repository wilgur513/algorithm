package com.algospot.chapter30.timetrip;

import java.util.*;
import java.io.*;

class Vertex{
    int node;
    int w;

    public Vertex(int node, int w) {
        this.node = node;
        this.w = w;
    }
}

class Graph{
    final int INF = 987654321;
    int size;
    List<Vertex>[] adj;
    List<Vertex>[] max;
    boolean[][] isReachable;

    public Graph(int size) {
        this.size = size;
        adj = new List[size];
        max = new List[size];
        isReachable = new boolean[size][size];

        for(int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
            max[i] = new ArrayList<>();
        }
    }

    void addEdge(int a, int b, int d){
        adj[a].add(new Vertex(b, d));
    }

    void dfs(){
        for(int src = 0; src < size; src++){
            dfs(src);
        }
    }

    void dfs(int src){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src);

        while(!queue.isEmpty()){
            int here = queue.remove();
            isReachable[src][here] = true;

            for(Vertex v : adj[here]){
                int there = v.node;

                if(!isReachable[src][there]){
                    queue.add(there);
                }
            }
        }
    }

    int bellman(){
        int[] upper = new int[size];
        Arrays.fill(upper, INF);
        upper[0] = 0;

        for(int i = 0; i < size - 1; i++){
            for(int here = 0; here < size; here++){
                for(Vertex v : adj[here]){
                    int there = v.node;
                    int cost = v.w;
                    upper[there] = Math.min(upper[there], upper[here] + cost);
                }
            }
        }

        for(int here = 0; here < size; here++){
            for(Vertex v : adj[here]){
                int there = v.node;
                int cost = v.w;

                if(upper[here] + cost < upper[there])
                    if(isReachable[0][there] && isReachable[there][1])
                        return -INF;
            }
        }

        return upper[1];
    }

    boolean isReachable(){
        return isReachable[0][1];
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int v, w;
    private static Graph past, future;

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
        w = Integer.parseInt(tokenizer.nextToken());
        past = new Graph(v);
        future = new Graph(v);

        for(int i = 0; i < w; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken());
            int b = Integer.parseInt(tokenizer.nextToken());
            int d = Integer.parseInt(tokenizer.nextToken());

            past.addEdge(a, b, d);
            future.addEdge(a, b, -d);
        }
    }

    private static void solve() {
        past.dfs();
        future.dfs();
        int past = Main.past.bellman();
        int future = Main.future.bellman();

        String result1 = Math.abs(past) < 100001 ? String.valueOf(past) : "INFINITY";
        String result2 = Math.abs(future) < 100001 ? String.valueOf(-future) : "INFINITY";

        if(!Main.past.isReachable())
            System.out.println("UNREACHABLE");
        else
            System.out.println(result1 + " " + result2);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
