package com.boj.unsolved.segment.p1275;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

class SegmentTree{
    private int size;
    private long[] tree;

    public SegmentTree(int size) {
        this.size = size;
        tree = new long[size * 4];
        Arrays.fill(tree, 0);
    }

    public void modify(int index, long diff){
        modify(0, size - 1, 1, index, diff);
    }

    public long sum(int left, int right){
        return sum(0, size - 1, 1, left, right);
    }

    private void modify(int start, int end, int node, int index, long diff){
        if(index < start || end < index)
            return;

        this.tree[node] += diff;

        if(start == end)
            return;

        int mid = (start + end) / 2;
        modify(start, mid, node * 2, index, diff);
        modify(mid + 1, end, node * 2 + 1, index, diff);
    }

    private long sum(int start, int end, int node, int left, int right){
        if(left > end || right < start)
            return 0;

        if(left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;
        return sum(start, mid, node * 2, left, right) + sum(mid + 1, end, node * 2 + 1, left, right);
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, q;
    private static SegmentTree tree;
    private static long[] list;
    private static int[][] question;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.valueOf(tokenizer.nextToken());
        q = Integer.valueOf(tokenizer.nextToken());
        tree = new SegmentTree(n);

        list = new long[n];
        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++)
            list[i] = Long.valueOf(tokenizer.nextToken());

        question = new int[q][4];
        for(int i = 0; i < q; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for(int j = 0; j < 4; j++)
                question[i][j] = Integer.valueOf(tokenizer.nextToken());
        }
    }

    private static void solve() {
        IntStream.range(0, n).forEach(a -> tree.modify(a, list[a]));
        IntStream.range(0, q).forEach(Main::answerQuestion);
    }

    private static void answerQuestion(int i){
        System.out.println(tree.sum(left(i), right(i)));
        tree.modify(index(i), diff(i));
        list[index(i)] += diff(i);
    }

    private static int left(int i){
        return question[i][0] - 1 > question[i][1] - 1 ? question[i][1] - 1 : question[i][0] - 1;
    }

    private static int right(int i){
        return question[i][0] - 1 > question[i][1] - 1 ? question[i][0] - 1 : question[i][1] - 1;
    }

    private static int index(int i){
        return question[i][2] - 1;
    }

    private static long diff(int i){
        return question[i][3] - list[index(i)];
    }
}
