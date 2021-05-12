package com.algospot.chapter30.nthlon;

import java.util.*;
import java.io.*;

class Kind{
    int minute1;
    int minute2;

    public Kind(int minute1, int minute2) {
        this.minute1 = minute1;
        this.minute2 = minute2;
    }

    public int number(){
        return minute2 - minute1;
    }

    public int weight(){
        return minute1;
    }
}

class Vertex implements Comparable<Vertex>{
    int number;
    int weight;

    public Vertex(int number, int weight) {
        this.number = number;
        this.weight = weight;
    }

    @Override
    public int compareTo(Vertex o) {
        return weight > o.weight ? 1 : (weight < o.weight ? -1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        Vertex v = (Vertex)obj;

        return weight == v.weight && number == v.number;
    }

    public String toString(){
        return number + " " + weight;
    }
}

class Graph{
    int size;
    List<Vertex>[] adj;
    List<Kind> kinds;

    public Graph(int size) {
        this.size = size;
        adj = new List[size];
        kinds = new ArrayList<>();

        for(int i = 0; i < size; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addKind(Kind k){
        kinds.add(k);
    }

    public void initEdge(){
        boolean[] visited = new boolean[size];
        Queue<Integer> queue = new LinkedList<>();

        for(Kind k : kinds){
            int hereNumber = k.number();
            int hereIndex = indexTo(hereNumber);

            Vertex v = new Vertex(hereIndex, k.weight());
            adj[0].add(v);
            queue.add(hereNumber);
        }

        while(!queue.isEmpty()){
            int hereNumber = queue.remove();
            int hereIndex = indexTo(hereNumber);

            if(visited[hereIndex])
                continue;

            for(Kind k : kinds){
                if(!isValid(hereNumber, k.number()))
                    continue;

                int thereNumber = hereNumber +  k.number();
                int thereIndex = indexTo(thereNumber);

                adj[hereIndex].add(new Vertex(thereIndex, k.weight()));
                visited[hereIndex] = true;
                queue.add(thereNumber);
            }
        }
    }

    public int indexTo(int value){
        return value < 0 ? -value : value + 200;
    }

    public boolean isValid(int number1, int number2){
        return (number1 < 0 && number2 > 0) || (number1 > 0 && number2 < 0);
    }

    public int[] dijkstra(){
        int dist[] = new int[size];
        Arrays.fill(dist, 987654321);
        dist[0] = 0;

        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        queue.add(new Vertex(0, 0));

        while(!queue.isEmpty()){
            Vertex v = queue.remove();
            int here = v.number;
            int cost = v.weight;

            if(dist[here] < cost)
                continue;

            for(int i = 0; i < adj[here].size(); i++){
                v = adj[here].get(i);
                int there = v.number;
                int nextCost = cost + v.weight;

                if(nextCost < dist[there]){
                    dist[there] = nextCost;
                    queue.add(new Vertex(there, nextCost));
                }
            }
        }

        return dist;
    }

    void display(){
        for(int i = 0; i < size; i++){
            if(adj[i].size() > 0){
                System.out.println(valueTo(i) + " vertex");
                for(Vertex v : adj[i]){
                    System.out.println(valueTo(v.number) + " " + v.weight);
                }
                System.out.println("---------");
            }
        }
    }

    int valueTo(int index){
        if(index <= 199)
            return -index;

        return index - 200;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int m;
    private static Graph graph;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i = 0; i < numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        m = Integer.parseInt(reader.readLine());
        graph = new Graph(400);

        for(int i = 0; i < m; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int value1 = Integer.parseInt(tokenizer.nextToken());
            int value2 = Integer.parseInt(tokenizer.nextToken());

            graph.addKind(new Kind(value1, value2));
        }

        graph.initEdge();
    }

    private static void solve() {
        int[] dist = graph.dijkstra();

        if(dist[200] == 987654321)
            System.out.println("IMPOSSIBLE");
        else
            System.out.println(dist[200]);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
