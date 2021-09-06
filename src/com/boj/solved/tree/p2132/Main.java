package com.boj.solved.tree.p2132;

import java.util.*;
import java.io.*;

class Node {
    public int id;
    public int fruit;
    public List<Node> child;

    public Node(int id, int fruit) {
        this.id = id;
        this.fruit = fruit;
        this.child = new ArrayList<>();
    }

    public int maxFruit(boolean[] visited) {
        if(visited[id]) {
            return 0;
        }

        int max = 0;
        visited[id] = true;

        for(Node c : child) {
            max = Math.max(c.maxFruit(visited), max);
        }

        return max + fruit;
    }

    public void addAdj(Node node) {
        child.add(node);
    }

}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static Node[] nodes;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        n = Integer.parseInt(reader.readLine());
        nodes = new Node[n];

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++) {
            nodes[i] = new Node(i, Integer.parseInt(tokenizer.nextToken()));
        }
        for(int i = 0; i < n - 1; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.parseInt(tokenizer.nextToken()) - 1;
            int b = Integer.parseInt(tokenizer.nextToken()) - 1;
            nodes[a].addAdj(nodes[b]);
            nodes[b].addAdj(nodes[a]);
        }
    }

    private static void solve() {
        int max = -1;
        int resultNode = -1;

        for(Node node : nodes) {
            int fruit = node.maxFruit(new boolean[n]);

            if(max < fruit) {
                max = fruit;
                resultNode = node.id + 1;
            }

            if(max == fruit) {
                resultNode = Math.min(node.id + 1, resultNode);
            }
        }

        System.out.println(max + " " + resultNode);
    }
}
