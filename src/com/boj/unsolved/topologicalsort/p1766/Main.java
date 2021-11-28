package com.boj.unsolved.topologicalsort.p1766;

import java.io.*;
import java.util.*;

public class Main {
    static int n, m;
    static int[] count;
    static boolean[] visited;
    static List<Integer>[] adj;
    static PriorityQueue<Integer> queue;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        adj = new List[n + 1];
        count = new int[n + 1];
        visited = new boolean[n + 1];

        for(int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken());
            int to = Integer.parseInt(tokenizer.nextToken());

            adj[from].add(to);
            count[to]++;
        }
    }

    private static void solve() {
        queue = notHasInputNode();

        while(!queue.isEmpty()) {
            int node = queue.remove();
            removeNode(node);
            System.out.print(node + " ");
        }
    }

    private static PriorityQueue<Integer> notHasInputNode() {
        PriorityQueue queue = new PriorityQueue<>();

        for(int node = 1; node <= n; node++){
            if(count[node] == 0 && !visited[node]){
                queue.add(node);
                visited[node] = true;
            }
        }

        return queue;
    }

    private static void removeNode(int node) {
        for(int to : adj[node]) {
            count[to]--;

            if(count[to] == 0){
                queue.add(to);
            }
        }

        visited[node] = true;
    }
}
