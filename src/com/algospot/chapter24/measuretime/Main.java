package com.algospot.chapter24.measuretime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class FenwickTree{
    int[] tree;

    public FenwickTree(int size) {
        tree = new int[size + 1];
    }

    public int sum(int index){
        index++;
        int result = 0;

        while(index > 0){
            result += tree[index];
            index &= (index - 1);
        }

        return result;
    }

    public void add(int index, int value){
        index++;

        while(index < tree.length){
            tree[index] += value;
            index += (index&-index);
        }
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[] a;

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
        n = Integer.parseInt(reader.readLine());
        a = new int[n];

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++)
            a[i] = Integer.parseInt(tokenizer.nextToken());

    }

    private static void solve() {
        FenwickTree tree = new FenwickTree(1000000);
        int result = 0;

        for(int i=0; i<n; i++){
            result += tree.sum(999999) - tree.sum(a[i]);
            tree.add(a[i], 1);
        }

        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
