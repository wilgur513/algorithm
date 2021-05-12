package com.algospot.chapter28.meetingroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.StringTokenizer;

class Pair implements Comparable<Pair>{
    int scc;
    int node;

    public Pair(int scc, int node) {
        this.scc = scc;
        this.node = node;
    }


    @Override
    public int compareTo(Pair p) {
        if(p.scc < scc)
            return -1;
        else if(p.scc > scc)
            return 1;

        return 0;
    }
}

class Graph{
    int size;
    List<Integer>[] adj;
    int[] discoverd;
    int[] sccId;
    int count;
    int sccCount;
    Stack<Integer> stack;

    public Graph(int size) {
        this.size = size;
        adj = new List[size];
        discoverd = new int[size];
        sccId = new int[size];

        for(int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int a, int b) {
        adj[a].add(b);
    }

    public int[] scc(){
        count = 0;
        sccCount = 0;
        stack = new Stack<>();
        Arrays.fill(discoverd, -1);
        Arrays.fill(sccId, -1);

        for(int i = 0; i < size; i++)
            if(discoverd[i] == -1)
                scc(i);

        return sccId;
    }

    private int scc(int here) {
        discoverd[here] = count++;
        int vertex = discoverd[here];
        stack.push(here);

        for(int there : adj[here]){
            if(discoverd[there] == -1){
                vertex = Math.min(vertex, scc(there));
            }else if(sccId[there] == -1){
                vertex = Math.min(vertex, discoverd[there]);
            }
        }

        if(vertex == discoverd[here]){
            while(true){
                int v = stack.pop();
                sccId[v] = sccCount;

                if(v == here)
                    break;
            }

            sccCount++;
        }

        return vertex;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[] a, b, c, d;

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
        n = Integer.parseInt(tokenizer.nextToken());
        a = new int[n];
        b = new int[n];
        c = new int[n];
        d = new int[n];

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            a[i] = Integer.parseInt(tokenizer.nextToken());
            b[i] = Integer.parseInt(tokenizer.nextToken());
            c[i] = Integer.parseInt(tokenizer.nextToken());
            d[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        Graph graph = makeGraph();
        int[] sccId = graph.scc();

        for(int i = 0; i < 4*n ; i += 2)
            if(sccId[i] == sccId[i + 1]){
                System.out.println("IMPOSSIBLE");
                return;
            }

        int[] value = new int[2*n];
        Arrays.fill(value, -1);

        List<Pair> list = new ArrayList<>();
        for(int i = 0; i < 4*n; i++)
            list.add(new Pair(-sccId[i], i));
        Collections.sort(list);

        for(int i = 0; i < 4*n; i++){
            Pair p = list.get(i);
            int vertex = p.node;
            int isTrue = (vertex % 2) == 0 ? 1 : 0;
            int index = vertex / 2;

            if(value[index] != -1)
                continue;

            value[index] = isTrue;
        }

        System.out.println("POSSIBLE");
        for(int i = 0; i < 2*n; i++){
            if(value[i] == 1){
                int index = i / 2;

                if(i % 2 == 0){
                    System.out.println(a[index] + " " + b[index]);
                    i++;
                }else{
                    System.out.println(c[index] + " " + d[index]);
                }
            }
        }
    }

    private static Graph makeGraph() {
        Graph graph = new Graph(4*n);

        for(int i = 0; i < 4*n; i += 4){
            graph.addEdge(i + 1, i + 2);
            graph.addEdge(i + 3, i);
        }

        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                if(!isDisjoint(a[i], b[i], a[j], b[j])){
                    graph.addEdge(4*i, 4*j + 1);
                    graph.addEdge(4*j, 4*i + 1);
                }

                if(!isDisjoint(a[i], b[i], c[j], d[j])){
                    graph.addEdge(4*i, 4*j + 3);
                    graph.addEdge(4*j + 2, 4*i + 1);
                }

                if(!isDisjoint(c[i], d[i], a[j], b[j])){
                    graph.addEdge(4*i + 2, 4*j + 1);
                    graph.addEdge(4*j, 4*i + 3);
                }

                if(!isDisjoint(c[i], d[i], c[j], d[j])){
                    graph.addEdge(4*i + 2, 4*j + 3);
                    graph.addEdge(4*j + 2, 4*i + 3);
                }
            }
        }

        return graph;
    }

    private static boolean isDisjoint(int a, int b, int c, int d){
        return b <= c || d <= a;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
