package com.boj.solved.segment.p2268;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class SegmentTree{
    private int size;
    private long[] tree;

    public SegmentTree(int size) {
        this.size = size;
        tree = new long[size * 4];
    }

    public void modify(int index, int value){
        modify(0, size - 1, 1, index, value);
    }

    public long sum(int left, int right){
        return sum(0, size - 1, 1, left, right);
    }

    private long modify(int start, int end, int node, int index, int value) {
        if(index < start || end < index)
            return tree[node];

        if(start == end)
            tree[node] = value;
        else{
            int mid = (start + end) / 2;
            tree[node] = modify(start, mid, node * 2, index, value) + modify(mid + 1, end, node * 2 + 1, index, value);
        }

        return tree[node];
    }

    private long sum(int start, int end, int node, int left, int right) {
        if(right < start || end < left)
            return 0;

        if(left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;
        return sum(start, mid, node * 2, left, right) + sum(mid + 1, end, node * 2 + 1, left, right);
    }
}


public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static SegmentTree tree;
    private static int n, m;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());
        tree = new SegmentTree(n);
    }

    private static void solve() throws IOException {
        for(int i = 0; i < m; i++) {
            int[] command = new int[3];
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < 3; j++)
                command[j] = Integer.valueOf(tokenizer.nextToken());

            doCommand(command);
        }
    }

    private static void doCommand(int[] command) {
        if(isModify(command))
            tree.modify(index(command), value(command));
        else
            System.out.println(tree.sum(left(command), right(command)));
    }

    private static boolean isModify(int[] command) {
        return command[0] == 1;
    }

    private static int index(int[] command) {
        return command[1] - 1;
    }

    private static int value(int[] command){
        return command[2];
    }

    private static int left(int[] command) {
        return command[1] - 1 > command[2] - 1 ? command[2] - 1 : command[1] - 1;
    }

    private static int right(int[] command) {
        return command[1] - 1 < command[2] - 1 ? command[2] - 1 : command[1] - 1;
    }
}
