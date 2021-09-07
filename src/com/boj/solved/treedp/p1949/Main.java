package com.boj.solved.treedp.p1949;

import java.util.*;
import java.io.*;

class Node{
    int id;
    int value;
    List<Node> child;

    public Node(int id) {
        this.id = id;
        child = new ArrayList<>();
    }

    public void setValue(int value){
        this.value = value;
    }

    public void addChild(Node n){
        child.add(n);
    }

    public List<Node> getChild(){
        return child;
    }
}

class Tree{
    int size;
    Node[] nodes;
    boolean[] isVisited;
    List<Integer>[] adj;

    public Tree(int size) {
        this.size = size;
        nodes = new Node[size];
        isVisited = new boolean[size];
        adj = new List[size];

        for(int i = 0; i < size; i++) {
            nodes[i] = new Node(i);
            adj[i] = new ArrayList<>();
        }
    }

    public void setValues(int[] values){
        for(int i = 0; i < size; i++)
            nodes[i].setValue(values[i]);
    }

    public void relate(int v1, int v2){
        adj[v1].add(v2);
        adj[v2].add(v1);
    }
    
    public Node getRoot(){
        makeTree(0);
        return nodes[0];
    }

    private void makeTree(int current) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(nodes[current]);
        isVisited[current] = true;

        while(!queue.isEmpty()){
            Node here = queue.remove();

            for(int there : adj[here.id]){
                if(!isVisited[there]){
                    queue.add(nodes[there]);
                    isVisited[there] = true;
                    here.addChild(nodes[there]);
                }
            }
        }
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static Tree tree;
    static int[][] cache;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        int size = Integer.valueOf(reader.readLine());
        int[] values = new int[size];

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        for(int i = 0; i < size; i++)
            values[i] = Integer.valueOf(tokenizer.nextToken());

        tree = new Tree(size);
        tree.setValues(values);

        for(int i = 0; i < size - 1; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            tree.relate(Integer.valueOf(tokenizer.nextToken()) - 1, Integer.valueOf(tokenizer.nextToken()) - 1);
        }

        cache = new int[size][2];
        for(int i = 0; i < size; i++)
            Arrays.fill(cache[i], -1);
    }

    private static void solve() {
        Node root = tree.getRoot();
        System.out.println(Math.max(cal(root, true), cal(root, false)));
    }

    private static int cal(Node current, boolean isPerfect) {
        if(cache[current.id][index(isPerfect)] != -1)
            return cache[current.id][index(isPerfect)];

        if(current.getChild().size() == 0){
            return isPerfect ? current.value : 0;
        }

        int result = isPerfect ? childMostNotPerfect(current) + current.value : childCanPerfectOrNotPerfect(current);
        cache[current.id][index(isPerfect)] = result;

        return result;
    }

    private static int childMostNotPerfect(Node current){
        int result = 0;

        for(Node child : current.getChild()){
            result += cal(child, false);
        }

        return result;
    }

    private static int childCanPerfectOrNotPerfect(Node current){
        int result = 0;

        for(Node child : current.getChild()){
            result += Math.max(cal(child, false), cal(child, true));
        }

        return result;
    }

    private static int index(boolean flag){
        return flag ? 1 : 0;
    }
}
