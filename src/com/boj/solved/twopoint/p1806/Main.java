package com.boj.solved.twopoint.p1806;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int n, s;
    static int[] list;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        s = Integer.parseInt(tokenizer.nextToken());
        list = new int[n];

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++) {
            list[i] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        int start = 0;
        int end = 1;
        int value = list[0];
        int result = Integer.MAX_VALUE;

        while(true) {
            if(value < s) {
                if(end < n)
                    value += list[end++];
                else
                    break;
            } else if(value >= s) {
                result = Math.min(result, end - start);
                value -= list[start++];
            }
        }

        System.out.println(result == Integer.MAX_VALUE ? 0 : result);
    }
}
