package com.boj.solved.implement.p2056;

import java.util.*;
import java.io.*;

public class Main {
    static int n;
    static List<Integer>[] graph;
    static int[] cost;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());

        graph = new List[n];
        cost = new int[n];

        for(int from = 0; from < n; from++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            cost[from] = Integer.parseInt(tokenizer.nextToken());
            graph[from] = new ArrayList<>();
            int num = Integer.parseInt(tokenizer.nextToken());

            for(int j = 0; j < num; j++) {
                int to = Integer.parseInt(tokenizer.nextToken()) - 1;
                graph[from].add(to);
            }
        }
    }

    private static void solve() {
        int[] result = new int[n];

        for(int node = 0; node < n; node++) {
            result[node] = cost[node];
        }

        for(int node = 0; node < n; node++) {
            result[node] += graph[node].stream().map(n -> result[n]).max(Integer::compareTo).orElse(0);
        }

        System.out.println(Arrays.stream(result).max().getAsInt());
    }
}
