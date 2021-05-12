package com.algospot.chapter23.runningmedian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int a;
    private static int b;
    private static int[] values;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        a = Integer.parseInt(tokenizer.nextToken());
        b = Integer.parseInt(tokenizer.nextToken());
        values = new int[n];
        values[0] = 1983;

        for(int i=1; i<n; i++)
            values[i] = (int)(((long)values[i - 1]*a + b) % 20090711);
    }

    private static int solve() {
        PriorityQueue<Integer> low = new PriorityQueue<>(n, (a, b) -> (Integer.compare(b, a)));
        PriorityQueue<Integer> high = new PriorityQueue<>();
        int result = 0;

        for(int i=0; i<n; i++){
            if(low.size() == high.size())
                low.add(values[i]);
            else
                high.add(values[i]);

            if(!low.isEmpty() && !high.isEmpty() && high.peek() < low.peek()){
                int l = low.poll();
                int h = high.poll();

                low.add(h);
                high.add(l);
            }

            result = (result + low.peek()) % 20090711;
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
