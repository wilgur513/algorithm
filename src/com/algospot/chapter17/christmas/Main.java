package com.algospot.chapter17.christmas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int k;
    private static int[] partialSum;
    private static int[] cache;
    private static int[] next;

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

        tokenizer = new StringTokenizer(reader.readLine());
        partialSum = new int[n + 1];

        for(int i=1; i<=n; i++){
            partialSum[i] = (partialSum[i - 1] + Integer.parseInt(tokenizer.nextToken())) % k;
        }

        cache = new int[n + 1];
        Arrays.fill(cache, -1);

        next = new int[n + 1];
    }

    private static void solve() {
        long single = singlePick();
        int several = severalPick();

        System.out.println(single + " " + several);
    }

    private static long singlePick() {
        long result = 0;

        long[] count = new long[k];

        for(int i=0; i<=n; i++)
            count[partialSum[i]]++;

        for(int i=0; i<k; i++)
            if(count[i] >= 2)
                result = (result + ((count[i] * (count[i] - 1)) / 2)) % 20091101;

        return result;
    }

    private static int severalPick() {
        initNext();

        for(int i=0; i<=n; i++)
           severalPickHelp(i);

        return severalPickHelp(n);
    }

    private static void initNext() {
        int[] prev = new int[k];
        Arrays.fill(prev, -1);

        for(int i=0; i<=n; i++){
            if(prev[partialSum[i]] == -1){
                next[i] = -1;
            }else{
                next[i] = prev[partialSum[i]];
            }

            prev[partialSum[i]] = i;
        }
    }

    private static int severalPickHelp(int index) {
        if(index == 0)
            return 0;

        if(cache[index] != -1)
            return cache[index];

        if(next[index] != -1)
            cache[index] = Math.max(cache[index], severalPickHelp(next[index]) + 1);

        cache[index] = Math.max(cache[index], severalPickHelp(index - 1));

        return cache[index];
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
