package com.boj.solved.segment.p11505;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class SegmentTree{
    private static final int MOD = 1_000_000_007;
    private int size;
    private long[] tree;

    public SegmentTree(int size) {
        this.size = size;
        tree = new long[size * 4];
        Arrays.fill(tree, 1);
    }

    public void modify(int index, long value){
        modify(0, size - 1, 1, index, value);
    }

    public long multiply(int left, int right){
        return multiply(0, size - 1, 1, left, right);
    }

    private long modify(int start, int end, int node, int index, long value) {
        if(index < start || end < index)
            return tree[node];

        if(start == end){
            tree[node] = value;
        }else{
            int mid = (start + end) / 2;
            tree[node] = (modify(start, mid, node * 2, index, value) *
                          modify(mid + 1, end, node* 2 + 1, index, value)) % MOD;
        }

        return tree[node];
    }

    private long multiply(int start, int end, int node, int left, int right) {
        if(right < start || end < left)
            return 1;

        if(left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;

        return (multiply(start, mid, node * 2, left, right) *
                multiply(mid + 1, end, node * 2 + 1, left, right)) % MOD;
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m, k;
    private static SegmentTree tree;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());
        k = Integer.valueOf(tokenizer.nextToken());
        tree = new SegmentTree(n);

        for(int i = 0; i < n; i++){
            tree.modify(i, Integer.valueOf(reader.readLine()));
        }
    }

    private static void solve() throws IOException {
        for(int i = 0; i < m + k; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            if(tokenizer.nextToken().equals("1")){
                int b = Integer.valueOf(tokenizer.nextToken());
                long c = Integer.valueOf(tokenizer.nextToken());
                tree.modify(b - 1, c);
            }else{
                int b = Integer.valueOf(tokenizer.nextToken());
                int c = Integer.valueOf(tokenizer.nextToken());
                System.out.println(tree.multiply(b - 1, c - 1));
            }
        }
    }

}
