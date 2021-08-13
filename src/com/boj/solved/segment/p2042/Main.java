package com.boj.solved.segment.p2042;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

class SegmentTree{
    private int size;
    private BigInteger[] tree;

    public SegmentTree(int size) {
        this.size = size;
        tree = new BigInteger[size * 4];
        Arrays.fill(tree, BigInteger.ZERO);
    }

    public void modify(int index, BigInteger diff){
        modify(0, size - 1, 1, index, diff);
    }

    public BigInteger sum(int left, int right){
        return sum(0, size - 1, 1, left, right);
    }

    private void modify(int start, int end, int node, int index, BigInteger diff){
        if(index < start || end < index)
            return;

        tree[node] = tree[node].add(diff);

        if(start != end) {
            int mid = (start + end) / 2;
            modify(start, mid, node * 2, index, diff);
            modify(mid + 1, end, node * 2 + 1, index, diff);
        }
    }

    private BigInteger sum(int start, int end, int node, int left, int right) {
        if(right < start || end < left)
            return BigInteger.ZERO;

        if(left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;
        BigInteger result = sum(start, mid, node * 2, left, right);
        result = result.add(sum(mid + 1, end, node * 2 + 1, left, right));

        return result;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m, k;
    private static BigInteger[] list;
    private static long[][] command;
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
        list = new BigInteger[n];
        tree = new SegmentTree(n);

        for(int i = 0; i < n; i++) {
            list[i] = new BigInteger(reader.readLine());
        }

        command = new long[m + k][3];
        for(int i = 0; i < m + k; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < 3; j++)
                command[i][j] = Long.valueOf(tokenizer.nextToken());
        }

        tree = new SegmentTree(n);
    }

    private static void solve() {
        IntStream.range(0, n).forEach(a -> tree.modify(a, list[a]));
        IntStream.range(0, m + k).forEach(Main::doCommand);
    }

    private static void doCommand(int i){
        if(isModify(i)){
            tree.modify(index(i), diff(i));
            list[index(i)] = list[index(i)].add(diff(i));
        }else{
            System.out.println(tree.sum(left(i), right(i)));
        }
    }

    private static boolean isModify(int i) {
        return command[i][0] == 1;
    }

    private static int index(int i){
        return (int)command[i][1] - 1;
    }

    private static BigInteger diff(int i){
        BigInteger newValue = BigInteger.valueOf(command[i][2]);
        return newValue.subtract(list[index(i)]);
    }

    private static int left(int i){
        return (int)command[i][1] - 1;
    }

    private static int right(int i){
        return (int)command[i][2] - 1;
    }
}
