package com.boj.solved.unionfind.p17471;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int INF = Integer.MAX_VALUE;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static boolean[][] adj;
    static int n;
    static int[] people;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        initPeople();
        initBridge();
    }

    private static void initPeople() throws IOException {
        people = new int[n];
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++){
            people[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void initBridge() throws IOException {
        adj = new boolean[n][n];

        for(int from = 0; from < n; from++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            adj[from][from] = true;
            int num = Integer.valueOf(tokenizer.nextToken());

            for(int j = 0; j < num; j++){
                int to = Integer.valueOf(tokenizer.nextToken()) - 1;
                adj[from][to] = true;
                adj[to][from] = true;
            }
        }
    }

    private static void solve() {
        List<List<Integer>> graphList = makeGraphs();
        int result = INF;
        for(List<Integer> graph : graphList) {
            result = Math.min(result, calculateDiffBetweenTwoSection(graph));
        }

        System.out.println(result == INF ? -1 : result);
    }

    private static List<List<Integer>> makeGraphs() {
        List<List<Integer>> result = new ArrayList<>();
        makeGraphs(0, new boolean[n], result);
        result.remove(new ArrayList<>()); // 빈 배열 제거

        // 모든 지역구 포함한 배열 제거
        List<Integer> fullList = new ArrayList<>();
        for(int i = 0; i < n; i++){
            fullList.add(i);
        }
        result.remove(fullList);

        return result;
    }

    private static void makeGraphs(int index, boolean[] visited, List<List<Integer>> result) {
        if(index == n){
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i < n; i++){
                if(visited[i]){
                    list.add(i);
                }
            }
            result.add(list);
            return;
        }

        makeGraphs(index + 1, visited, result);
        visited[index] = true;
        makeGraphs(index + 1, visited, result);
        visited[index] = false;
    }

    private static int calculateDiffBetweenTwoSection(List<Integer> graph) {
        List<Integer> other = getOtherSideGraph(graph);
        if(onlyHasTwoSection(graph, other)){
            return diffPeople(graph, other);
        }

        return INF;
    }

    private static List<Integer> getOtherSideGraph(List<Integer> graph) {
        List<Integer> other = new ArrayList<>();
        for(int i = 0; i < n; i++){
            if(!graph.contains(i)){
                other.add(i);
            }
        }
        return other;
    }

    private static boolean onlyHasTwoSection(List<Integer> graph, List<Integer> other) {
        return isUnion(graph) && isUnion(other);
    }

    private static boolean isUnion(List<Integer> graph) {
        int[] root = new int[n];
        Arrays.fill(root, -1);

        for(int node : graph){
            root[node] = node;
        }

        for(int from : graph) {
            for(int to : graph){
                if(adj[from][to]){
                    union(from, to, root);
                }
            }
        }

        int parent = parent(graph.get(0), root);
        for(int node : graph) {
            if(parent != parent(node, root))
                return false;
        }

        return true;
    }

    private static void union(int from, int to, int[] root) {
        int fromParent = parent(from, root);
        int toParent = parent(to, root);

        if(fromParent < toParent) {
            root[toParent] = fromParent;
        } else{
            root[fromParent] = toParent;
        }
    }

    private static int parent(int node, int[] root) {
        if(root[node] == node){
            return node;
        }
        root[node] = parent(root[node], root);
        return root[node];
    }

    private static int diffPeople(List<Integer> graph, List<Integer> other) {
        return Math.abs(sumOfPeople(graph) - sumOfPeople(other));
    }

    private static int sumOfPeople(List<Integer> graph) {
        int sum = 0;
        for(int index : graph){
            sum += people[index];
        }
        return sum;
    }
}
