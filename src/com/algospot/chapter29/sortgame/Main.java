package com.algospot.chapter29.sortgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Graph{
    Map<List<Integer>, Integer>[] map;

    public Graph() {
        map = new Map[8];

        for(int i = 0; i < 8; i++) {
            map[i] = new HashMap<>();
            initMap(i + 1);
        }
    }

    public int cal(int n, List<Integer> list) {
        return map[n - 1].get(list);
    }

    private void initMap(int n){
        List<Integer> first = new ArrayList<>();

        for(int i = 0; i < n; i++)
            first.add(i);

        Queue<List<Integer>> queue = new LinkedList<>();
        map[n - 1].put(first, 0);
        queue.add(first);

        while(!queue.isEmpty()){
            List<Integer> list = queue.remove();
            int distance = map[n - 1].get(list);

            for(int start = 0; start < n; start++) {
                for (int end = start; end < n; end++) {
                    List<Integer> reverse = reverse(list, start, end);

                    if (map[n - 1].get(reverse) == null) {
                        queue.add(reverse);
                        map[n - 1].put(reverse, distance + 1);
                    }
                }
            }
        }
    }

    private List<Integer> reverse(List<Integer> list, int start, int end){
        int[] values = new int[list.size()];

        for(int i = 0; i < list.size(); i++)
            values[i] = list.get(i);

        for(int i = 0; i <= (end - start) / 2; i++){
            int temp = values[start + i];
            values[start + i] = values[end - i];
            values[end - i] = temp;
        }

        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < values.length; i++)
            result.add(values[i]);

        return result;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[] values;
    private static Graph graph;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());
        graph = new Graph();

        for(int i = 0; i < numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        values = new int[n];

        for(int i = 0; i < n; i++)
            values[i] = Integer.parseInt(tokenizer.nextToken());
    }

    private static void solve() {
        List<Integer> list = makeList();
        System.out.println(graph.cal(n, list));
    }

    private static List<Integer> makeList() {
        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < n; i++){
            int index = 0;

            for(int j = 0; j < n; j++){
                if(values[j] < values[i])
                    index++;
            }

            result.add(index);
        }

        return result;
    }


    public static void main(String[] args) throws IOException {
        run();
    }
}