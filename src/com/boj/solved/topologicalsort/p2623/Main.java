package com.boj.solved.topologicalsort.p2623;

import java.io.*;
import java.util.*;

public class Main {
    private static int n, m;
    private static Set<Integer>[] adj;
    private static int[] count;
    private static boolean[] isFound;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        adj = new Set[n];
        count = new int[n];
        isFound = new boolean[n];

        for(int i = 0; i < n; i++) {
            adj[i] = new HashSet<>();
        }

        for(int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int cnt = Integer.parseInt(tokenizer.nextToken());
            int prev = Integer.parseInt(tokenizer.nextToken()) - 1;

            for(int j = 1; j < cnt; j++) {
                int current = Integer.parseInt(tokenizer.nextToken()) - 1;

                if(!adj[current].contains(prev)) {
                    count[current]++;
                    adj[current].add(prev);
                }

                prev = current;
            }
        }
    }

    private static void solve() {
        for(int v : findList()) {
            System.out.println(v + 1);
        }
    }

    private static List<Integer> findList() {
        List<Integer> result = new ArrayList<>();

        while(!isAllFound()) {
            int vertex = findNotHasAdj();

            if(findNotHasAdj() == -1) {
                return Arrays.asList(-1);
            }

            result.add(vertex);
            isFound[vertex] = true;
            removeVertex(vertex);
        }

        return result;
    }

    private static void removeVertex(int vertex) {
        for(int i = 0; i < n; i++) {
            if(adj[i].contains(vertex)) {
                adj[i].remove(vertex);
                count[i]--;
            }
        }
    }

    private static int findNotHasAdj() {
        for(int i = 0; i < n; i++) {
            if(count[i] == 0 && !isFound[i]) {
                return i;
            }
        }

        return -1;
    }

    private static boolean isAllFound() {
        for(int i = 0; i < n; i++) {
            if(!isFound[i]) {
                return false;
            }
        }

        return true;
    }
}
