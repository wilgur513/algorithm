package com.boj.unsolved.segment.p5676;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

class SegmentTree{
    private int size;
    private int[] tree;

    public SegmentTree(int size) {
        this.size = size;
        tree = new int[size * 4];
        Arrays.fill(tree, 1);
    }

    public void modify(int index, int value){
        modify(0, size - 1, 1, index, value);
    }

    public int multiply(int left, int right){
        return multiply(0, size - 1, 1, left, right);
    }

    private int modify(int start, int end, int node, int index, int diff) {
        if(index < start || end < index)
            return tree[node];

        if(start == end)
            tree[node] = diff;
        else {
            int mid = (start + end) / 2;
            tree[node] = modify(start, mid, node * 2, index, diff) * modify(mid + 1, end, node * 2 + 1, index, diff);
        }

        return tree[node];
    }

    private int multiply(int start, int end, int node, int left, int right) {
        if(right < start || end < left)
            return 1;

        if(left <= start && end <= right)
            return tree[node];

        int mid = (start + end) / 2;
        return multiply(start, mid, node * 2, left, right)* multiply(mid + 1, end, node * 2 + 1, left, right);
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, k;
    private static int[] values;
    private static int[][] command;
    private static SegmentTree tree;

    public static void main(String[] args) throws IOException {
        String input = null;

        while((input = reader.readLine()) != null){
            runSingleTestCase(input);
        }
    }

    private static void runSingleTestCase(String input) throws IOException {
        inputData(input);
        solve();
    }

    private static void inputData(String input) throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(input);
        n = Integer.valueOf(tokenizer.nextToken());
        k = Integer.valueOf(tokenizer.nextToken());

        values = new int[n];
        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++)
            values[i] = Integer.valueOf(tokenizer.nextToken());

        command = new int[k][3];
        for(int i = 0; i < k; i++){
            tokenizer = new StringTokenizer(reader.readLine());

            command[i][0] = tokenizer.nextToken().equals("C") ? 0 : 1;

            for(int j = 1; j < 3; j++)
                command[i][j] = Integer.valueOf(tokenizer.nextToken());
        }

        tree = new SegmentTree(n);
    }

    private static void solve() {
        IntStream.range(0, n).forEach(Main::initTrees);
        String result = IntStream.range(0, k).mapToObj(Main::doCommand).reduce("", (a, b) -> a + b);
        System.out.println(result);
    }

    private static void initTrees(int i){
        int value = isPositive(values[i]) ? 1 : (isNegative(values[i]) ? -1 : 0);
        tree.modify(i, value);
    }

    private static boolean isPositive(int value){
        return value > 0;
    }

    private static boolean isNegative(int value){
        return value < 0;
    }

    private static String doCommand(int i){
        if(isModifyCommand(i)){
            tree.modify(index(i), value(i));
            return "";
        }

        if(tree.multiply(left(i), right(i)) > 0)
            return "+";
        else if(tree.multiply(left(i), right(i)) < 0)
            return "-";
        return "0";
    }

    private static boolean isModifyCommand(int i){
        return command[i][0] == 0;
    }

    private static int index(int i){
        return command[i][1] - 1;
    }

    private static int value(int i){
        return isPositive(command[i][2]) ? 1 : (isNegative(command[i][2]) ? -1 : 0);
    }

    private static int left(int i){
        return command[i][1] - 1;
    }

    private static int right(int i){
        return command[i][2] - 1;
    }
}
