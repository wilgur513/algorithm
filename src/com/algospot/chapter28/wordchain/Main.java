package com.algospot.chapter28.wordchain;

import java.util.*;

class Graph{
    int size;
    int[][] graph;
    int[] in;
    int[] out;
    List<String>[][] edge;
    List<Integer> curcuit;

    public Graph(int size){
        this.size = size;
        graph = new int[size][size];
        in = new int[size];
        out = new int[size];
        edge = (List<String>[][])new List[size][size];

        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                edge[i][j] = new ArrayList<>();
    }

    public void addEdge(int a, int b, String word){
        graph[a][b]++;
        in[b]++;
        out[a]++;
        edge[a][b].add(word);
    }

    public boolean isEulerCurcuitOrTrail(){
        int start = 0, end = 0;

        for(int v=0; v<size; v++){
            int delta = in[v] - out[v];

            if(delta < -1 || delta > 1)
                return false;
            if(delta == -1)
                start++;
            if(delta == 1)
                end++;
        }

        return (start == 1 && end == 1) || (start == 0 && end == 0);
    }

    public List<Integer> makeEulerCurcuitOrTrail(){
        curcuit = new ArrayList<>();

        for(int root=0; root<size; root++){
            if(in[root] + 1 == out[root]){
                makeEulerCurcuit(root, curcuit);
                return curcuit;
            }
        }

        for(int root=0; root<size; root++){
            if(out[root] > 0){
                makeEulerCurcuit(root, curcuit);
                return curcuit;
            }
        }

        return curcuit;
    }

    public String makePath(){
        String result = "";

        for(int i=curcuit.size()-1; i>0; i--){
            int a = curcuit.get(i);
            int b = curcuit.get(i - 1);
            String word = edge[a][b].get(0);
            edge[a][b].remove(0);

            result += " " + word;
        }

        return result;
    }

    private void makeEulerCurcuit(int here, List<Integer> curcuit){
        for(int next=0; next<size; next++){
            while(graph[here][next] > 0){
                graph[here][next]--;
                makeEulerCurcuit(next, curcuit);
            }
        }

        curcuit.add(here);
    }
}

public class Main{
    static Scanner scanner = new Scanner(System.in);
    static int n;
    static String[] words;

    static void run(){
        int number = scanner.nextInt();

        for(int i=0; i<number; i++)
            runSingleTestCase();
    }

    static void runSingleTestCase(){
        inputData();
        solve();
    }

    static void inputData(){
        n = scanner.nextInt();
        words = new String[n];

        for(int i=0; i<n; i++)
            words[i] = scanner.next();
    }

    static void solve(){
        Graph g = makeGraph();

        if(!g.isEulerCurcuitOrTrail()){
            System.out.println("IMPOSSIBLE");
            return;
        }

        List<Integer> curcuit = g.makeEulerCurcuitOrTrail();

        if(curcuit.size() != n + 1){
            System.out.println("IMPOSSIBLE");
            return;
        }

        System.out.println(g.makePath());
    }

    static Graph makeGraph(){
        Graph g = new Graph(26);

        for(int i=0; i<n; i++){
            int a = numberTo(words[i].charAt(0));
            int b = numberTo(words[i].charAt(words[i].length() - 1));
            g.addEdge(a, b, words[i]);
        }

        return g;
    }

    static int numberTo(char ch){
        return ch - 'a';
    }

    public static void main(String[] args){
        run();
    }
}