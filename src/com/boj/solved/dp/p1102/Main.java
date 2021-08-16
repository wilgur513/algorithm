package com.boj.solved.dp.p1102;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static int[][] cost;
    static int isOn;
    static int p, count;
    static int[] cache;

    private static void run() throws IOException {
        inputData();
        int result = solve(isOn, count);
        System.out.println(result == 987654321 ? -1 : result);
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        cost = new int[n][n];
        cache = new int[1 << n];

        Arrays.fill(cache, -1);

        for(int i = 0; i < n; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            for(int j = 0; j < n; j++)
                cost[i][j] = Integer.parseInt(tokenizer.nextToken());
        }

        isOn = 0;
        String s = reader.readLine();
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'Y'){
                isOn |= 1 << i;
                count++;
            }
        }

        p = Integer.parseInt(reader.readLine());
    }

    private static int solve(int bit, int normal) {
        if(normal >= p)
            return 0;

        if(cache[bit] != -1)
            return cache[bit];

        int result = 987654321;

        for(int here = 0; here < n; here++){
            if(isNormal(bit, here)){
                for(int there = 0; there < n; there++){
                    if(here == there)
                        continue;

                    if(!isNormal(bit, there))
                        result = Math.min(result, solve(bit | (1 << there), normal + 1) + cost[here][there]);
                }
            }
        }

        cache[bit] = result;
        return result;
    }

    static boolean isNormal(int bit, int device){
        return (bit & (1 << device)) > 0;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
