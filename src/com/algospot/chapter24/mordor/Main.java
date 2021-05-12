package com.algospot.chapter24.mordor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class RMQ{
    private int[] h;
    private int[] min;
    private int[] max;

    public RMQ(int[] h) {
        this.h = h;
        min = new int[h.length*4];
        max = new int[h.length*4];

        init();
    }

    public int min(int node, int nodeLeft, int nodeRight, int left, int right){
        if(right < nodeLeft || left > nodeRight)
            return Integer.MAX_VALUE;
        else if(left <= nodeLeft && right >= nodeRight)
            return min[node];

        int mid = (nodeLeft + nodeRight) / 2;

        return Math.min(min(node*2 + 1, nodeLeft, mid, left, right),
                        min(node*2 + 2, mid + 1, nodeRight, left, right));
    }

    public int max(int node, int nodeLeft, int nodeRight, int left, int right){
        if(right < nodeLeft || left > nodeRight)
            return Integer.MIN_VALUE;
        else if(left <= nodeLeft && right >= nodeRight)
            return max[node];

        int mid = (nodeLeft + nodeRight) / 2;

        return Math.max(max(node*2 + 1, nodeLeft, mid, left, right),
                        max(node*2 + 2, mid + 1, nodeRight, left, right));
    }

    private void init() {
        initMin(0, 0, h.length - 1);
        initMax(0, 0, h.length - 1);
    }

    private int initMin(int node, int left, int right) {
        if(left == right)
            min[node] = h[left];
        else {
            int mid = (left + right) / 2;
            min[node] = Math.min(initMin(node * 2 + 1, left, mid),
                                    initMin(node * 2 + 2, mid + 1, right));
        }

        return min[node];
    }

    private int initMax(int node, int left, int right) {
        if(left == right)
            max[node] = h[left];
        else {
            int mid = (left + right) / 2;
            max[node] = Math.max(initMax(node * 2 + 1, left, mid),
                                    initMax(node * 2 + 2, mid + 1, right));
        }

        return max[node];
    }

}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, q;
    private static int[] h;
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
        q = Integer.parseInt(tokenizer.nextToken());

        h = new int[n];
        a = new int[q];
        b = new int[q];

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++)
            h[i] = Integer.parseInt(tokenizer.nextToken());

        for(int i=0; i<q; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            a[i] = Integer.parseInt(tokenizer.nextToken());
            b[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        RMQ rmq = new RMQ(h);
        for(int i=0; i<q; i++){
            int min = rmq.min(0, 0, h.length - 1, a[i], b[i]);
            int max = rmq.max(0, 0, h.length - 1, a[i], b[i]);
            System.out.println(max - min);
        }
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
