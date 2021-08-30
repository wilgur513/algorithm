package com.boj.solved.prefixsum.p2313;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Node implements Comparable<Node>{
    int start;
    int end;
    long sum;

    public Node(int start, int end, long sum) {
        this.start = start;
        this.end = end;
        this.sum = sum;
    }

    @Override
    public int compareTo(Node o) {
        if(sum < o.sum) {
            return 1;
        } else if(sum > o.sum) {
            return -1;
        }

        if(count() < o.count()) {
            return -1;
        } else if(count() > o.count()) {
            return 1;
        }

        return (start + " " + end).compareTo(o.start + " " + o.end);
    }

    private int count() {
        return end - start + 1;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    static int[] num;
    static int[][] values;
    static long[][] psum;
    static Node[][] max;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        num = new int[n];
        values = new int[n][];
        psum = new long[n][];
        max = new Node[n][];

        for(int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(reader.readLine());
            values[i] = new int[num[i]];
            max[i] = new Node[num[i]];
            psum[i] = new long[num[i] + 1];
            psum[i][0] = 0;

            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            for(int j = 0; j < num[i]; j++) {
                values[i][j] = Integer.parseInt(tokenizer.nextToken());
                psum[i][j + 1] = psum[i][j] + values[i][j];
            }
        }
    }

    private static void solve() {
        long result = 0;

        for(int i = 0; i < n; i++) {
            max[i][0] = new Node(0, 0, values[i][0]);
            for(int j = 1; j < num[i]; j++) {
                if(max[i][j -1].sum + values[i][j] > values[i][j]) {
                    max[i][j] = new Node(max[i][j - 1].start, j, max[i][j - 1].sum + values[i][j]);
                } else {
                    max[i][j] = new Node(j, j, values[i][j]);
                }
            }
            Arrays.sort(max[i]);
            result += max[i][0].sum;
        }

        System.out.println(result);
        for(Node[] nodes : max) {
            System.out.println((nodes[0].start + 1) + " " + (nodes[0].end + 1));
        }
    }
}
