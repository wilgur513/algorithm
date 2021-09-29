package com.boj.solved.segment.p2357;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

class Node {
    int max;
    int min;

    public Node(int max, int min) {
        this.max = max;
        this.min = min;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    private static int n, m;
    private static int[] values;
    private static int[][] range;
    private static Node[] tree;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        values = new int[n];
        range = new int[m][2];

        for(int i = 0; i  < n; i++) {
            values[i] = Integer.parseInt(reader.readLine());
        }

        for(int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            range[i][0] = Integer.parseInt(tokenizer.nextToken());
            range[i][1] = Integer.parseInt(tokenizer.nextToken());
        }

        tree = new Node[4 * n];
    }

    private static void solve() {
        init(values, 1, 0, n - 1);
        for(int i = 0; i < m; i++) {
            Node node = query(range[i][0] - 1, range[i][1] - 1, 1, 0, n - 1);
            System.out.println(node.min + " " + node.max);
        }
    }

    private static Node init(int[] array, int node, int nodeLeft, int nodeRight) {
        if(nodeLeft == nodeRight) {
            tree[node] = new Node(array[nodeLeft], array[nodeLeft]);
            return tree[node];
        }

        int mid = (nodeLeft + nodeRight) / 2;
        Node l = init(array, node * 2, nodeLeft, mid);
        Node r = init(array, node * 2 + 1, mid + 1, nodeRight);
        tree[node] = new Node(Math.max(l.max, r.max), Math.min(l.min, r.min));
        return tree[node];
    }

    private static Node query(int left, int right, int node, int nodeLeft, int nodeRight) {
        if(right < nodeLeft || nodeRight < left) {
            return new Node(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        if(left <= nodeLeft && nodeRight <= right) {
            return tree[node];
        }

        int mid = (nodeLeft + nodeRight) / 2;
        Node l = query(left, right, node * 2, nodeLeft, mid);
        Node r = query(left, right, node * 2 + 1, mid + 1, nodeRight);
        return new Node(Math.max(l.max, r.max), Math.min(l.min, r.min));
    }
}
