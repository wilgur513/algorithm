package com.algospot.chapter25.editwars;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static String[] message;
    private static int[] a, b;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        message = new String[m];
        a = new int[m];
        b = new int[m];

        for(int i=0; i<m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            message[i] = tokenizer.nextToken();
            a[i] = Integer.parseInt(tokenizer.nextToken());
            b[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        Counter counter = new Counter(n);

        for(int i=0; i<m; i++){
            if(message[i].equals("ACK") && !counter.ack(a[i], b[i])){
                System.out.println("CONTRADICTION AT " + (i + 1));
                return;
            }else if(message[i].equals("DIS") && !counter.dis(a[i], b[i])){
                System.out.println("CONTRADICTION AT " + (i + 1));
                return;
            }
        }

        System.out.println("MAX PARTY SIZE IS " + counter.count());
    }

    public static void main(String[] args) throws IOException {
        run();
    }

    private static class Counter {
        private int size;
        private int[] rank;
        private int[] parent;
        private int[] count;
        private int[] enemy;

        public Counter(int n) {
            size = n;
            parent = new int[size];
            rank = new int[size];
            count = new int[size];
            enemy = new int[size];

            for(int i=0; i<size; i++){
                parent[i] = i;
                rank[i] = 0;
                count[i] = 1;
                enemy[i] = -1;
            }
        }

        public boolean ack(int u, int v){
            u = find(u);
            v = find(v);

            if(u == enemy[v])
                return false;

            int a = union(u, v);
            int b = union(enemy[u], enemy[v]);

            enemy[a] = b;

            if(b != -1)
                enemy[b] = a;

            return true;
        }

        public boolean dis(int u, int v){
            u = find(u);
            v = find(v);

            if(u == v)
                return false;

            int a = union(u, enemy[v]);
            int b = union(v, enemy[u]);

            enemy[a] = b;
            enemy[b] = a;

            return true;
        }

        public int count() {
            int result = 0;

            for(int i=0; i<n; i++){
                if(find(i) == i){
                    int e = enemy[i];

                    if(e > i)
                        continue;

                    int enemySize = e == -1 ? 0 : count[e];
                    result += Math.max(count[i], enemySize);
                }
            }

            return result;
        }

        private int find(int arg) {
            if(parent[arg] != arg)
                parent[arg] = find(parent[arg]);

            return parent[arg];
        }

        private int union(int u, int v) {
            if(u == -1 | v == -1)
                return Math.max(u, v);

            u = find(u);
            v = find(v);

            if(u == v)
                return u;

            if(rank[u] < rank[v])
                return union(v, u);
            else if(rank[u] == rank[v])
                rank[v]++;

            parent[u] = v;
            count[v] += count[u];

            return v;
        }
    }
}
