package com.algospot.chapter28.dictionary;

import java.util.*;

class Graph{
    int size;
    boolean[] visited;
    boolean[][] edge;
    char[] topologicalSort;
    int index;

    public Graph(int size) {
        this.size = size;
        visited = new boolean[size];
        edge = new boolean[size][size];
    }

    public boolean isCanAddEdge(int v1, int v2){
        return !edge[v2][v1];
    }

    public void addEdge(int v1, int v2){
        edge[v1][v2] = true;
    }

    public void topologicalSort(){
        topologicalSort = new char[size];
        index = 0;
        dfsAll();

        for(int i=0; i<index/2; i++){
            char temp = topologicalSort[i];
            topologicalSort[i] = topologicalSort[index - i - 1];
            topologicalSort[index - i - 1] = temp;
        }

        for(int i=0; i<size; i++)
            if(!visited[i])
                topologicalSort[index++] = (char)(i + 'a');

        for(char c : topologicalSort)
            System.out.print(c);
        System.out.println();
    }

    private void dfsAll() {
        for(int i=0; i<size; i++)
            if(isFirstVertex(i))
                dfs(i);
    }

    private boolean isFirstVertex(int v) {
        for(int i=0; i<size; i++)
            if(edge[i][v])
                return false;

        for(int i=0; i<size; i++)
            if(edge[v][i])
                return true;

        return false;
    }

    private void dfs(int v) {
        visited[v] = true;

        for(int i=0; i<size; i++)
            if(edge[v][i] && !visited[i])
                dfs(i);

        topologicalSort[index++] = (char)(v + 'a');
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static String[] words;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        inputData();
        solve();
    }

    private static void inputData() {
        n = scanner.nextInt();
        words = new String[n];

        for(int i=0; i<n; i++)
            words[i] = scanner.next();
    }

    private static void solve() {
        Graph g = makeGraph();

        if(g == null)
            System.out.println("INVALID HYPOTHESIS");
        else
            g.topologicalSort();
    }

    private static Graph makeGraph() {
        Graph g = new Graph(26);

        for(int i=0; i<words.length-1; i++){
            int index = getDifferentIndex(words[i], words[i + 1]);

            if(index == -1)
                continue;

            int a = numberTo(words[i].charAt(index));
            int b = numberTo(words[i + 1].charAt(index));

            if(g.isCanAddEdge(a, b))
                g.addEdge(a, b);
            else
                return null;
        }

        return g;
    }

    private static int getDifferentIndex(String a, String b){
        int len = a.length() < b.length() ? a.length() : b.length();

        for(int i=0; i<len; i++)
            if(a.charAt(i) != b.charAt(i))
                return i;

        return -1;
    }

    private static int numberTo(char c){
        return c - 'a';
    }

    public static void main(String[] args) {
        run();
    }
}
