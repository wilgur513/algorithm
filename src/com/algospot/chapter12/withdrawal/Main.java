package com.algospot.chapter12.withdrawal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int k;
    private static int[] ranks;
    private static int[] attendees;

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
        k = Integer.parseInt(tokenizer.nextToken());

        ranks = new int[n];
        attendees = new int[n];
        tokenizer = new StringTokenizer(reader.readLine());

        for(int i=0; i<n; i++){
            ranks[i] = Integer.parseInt(tokenizer.nextToken());
            attendees[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static double solve() {
        double lo = 0.0;
        double hi = 1.0;

        for(int i=0; i<100; i++){
            double avg = (lo + hi) / 2;

            if(isLessThan(avg))
                hi = avg;
            else
                lo = avg;
        }

        return hi;
    }

    private static boolean isLessThan(double avg) {
        double[] list = new double[n];

        for(int i=0; i<n; i++)
            list[i] = avg * attendees[i] - ranks[i];

        Arrays.sort(list);
        double sum = 0.0;

        for(int i=n-k; i<n; i++)
            sum += list[i];

        return sum >= 0.0;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
