package com.algospot.chapter18.josephus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int k;
    private static LinkedList<Integer> list;

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
        k = Integer.parseInt(tokenizer.nextToken());

        list = new LinkedList<>();

        for(int i=0; i<n; i++)
            list.add(i);
    }

    private static void solve() {
        int index = 0;

        while(n > 2){
            list.remove(index);
            index += (k - 1);
            n--;
            index %= n;
        }

        System.out.println((list.get(0) + 1) + " " + (list.get(1) + 1));
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
