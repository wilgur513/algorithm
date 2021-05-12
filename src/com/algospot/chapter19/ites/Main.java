package com.algospot.chapter19.ites;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int k;
    private static long divisor = ((1L << 32) - 1);

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

        k = Integer.parseInt(tokenizer.nextToken());
        n = Integer.parseInt(tokenizer.nextToken());
    }

    private static int solve() {
        Deque<Integer> deque = new LinkedList<>();
        int result = 0;
        int sum  = 0;
        long a = 1983L;

        for(int i=0; i<n; i++){
            sum += sign(a);
            deque.addLast(sign(a));
            a = nextA(a);

            while(sum > k){
                sum -= deque.pollFirst();
            }

            if(sum == k)
                result++;
        }

        return result;
    }

    public static long nextA(long a){
        return (a*214013L + 2531011L) & divisor;
    }

    public static int sign(long a){
        return (int)(a % 10000) + 1;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
