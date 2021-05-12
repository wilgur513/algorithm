package com.algospot.chapter28.gallery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;

class Graph{
    int size;
    List<Integer>[] adj;
    boolean[] isVisited;
    boolean[] isInstalled;
    boolean[] isObserved;

    public Graph(int size) {
        this.size = size;
        adj = new List[size];
        isVisited = new boolean[size];
        isInstalled = new boolean[size];
        isObserved = new boolean[size];

        for(int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int a, int b){
        adj[a].add(b);
        adj[b].add(a);
    }

    public int cameraCount(){
        for(int i = 0; i < size; i++)
            if(!isVisited[i])
                dfs(i, true);

        int result = 0;

        for(boolean flag : isInstalled)
            if(flag)
                result++;

        return result;
    }

    private void dfs(int here, boolean isRoot) {
        isVisited[here] = true;
        List<Integer> child = new ArrayList<>();

        for(int there : adj[here]){
            if(!isVisited[there]){
                dfs(there, false);
                child.add(there);
            }
        }

        for(int there : child){
            if(!isObserved[there]) {
                isInstalled[here] = true;
                isObserved[here] = true;
                isObserved[there] = true;
            }
        }

        for(int there : child){
            if(isInstalled[there]) {
                isObserved[here] = true;
            }
        }

        if(isRoot){
            if(!hasInstalledChild(child))
                isInstalled[here] = true;
        }
    }

    private boolean hasInstalledChild(List<Integer> child){
        for(int there : child){
            if(isInstalled[there])
                return true;
        }

        return false;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int g, h;
    private static int[] a, b;

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
        g = Integer.parseInt(tokenizer.nextToken());
        h = Integer.parseInt(tokenizer.nextToken());
        a = new int[h];
        b = new int[h];

        for(int i = 0; i < h; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            a[i] = Integer.parseInt(tokenizer.nextToken());
            b[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        Graph graph = makeGraph();
        System.out.println(graph.cameraCount());
    }

    private static Graph makeGraph() {
        Graph graph = new Graph(g);

        for(int i = 0; i < h; i++){
            graph.addEdge(a[i], b[i]);
        }

        return graph;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
