package com.algospot.chapter29.hanoi4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int start;
    private static int end;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());
        for(int i = 0; i < numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        start = 0;
        end = 0;

        for(int i = 0; i < 4; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int size = Integer.parseInt(tokenizer.nextToken());
            int[] values = new int[size];

            for(int j = 0; j < size; j++){
                values[j] = Integer.parseInt(tokenizer.nextToken());
            }

            start |= set(values, i);
        }


        for(int i = 0; i < 3; i++)
            end |= set(new int[]{}, i);

        int[] values = new int[n];
        for(int i = 0; i < n; i++)
            values[i] = i + 1;

        end |= set(values, 3);
    }

    private static void solve() {
        if(start == end){
            System.out.println("0");
            return;
        }

        Queue<Integer> q = new LinkedList<>();
        int[] c = new int[1 << 24];

        q.add(start);
        q.add(end);
        c[start] = 1;
        c[end] = -1;

        while(!q.isEmpty()){
            int here = q.remove();

            int[] small = {-1, -1, -1, -1};

            for(int i = 12; i > 0; i--)
                small[get(here, i)] = i;

            for(int i = 0; i < 4; i++){
                if(small[i] != -1) {
                    for (int j = 0; j < 4; j++) {
                        if (i != j && (small[j] == -1 | small[j] > small[i])) {
                            int there = set(here, small[i], j);

                            if (c[there] == 0) {
                                c[there] = incr(c[here]);
                                q.add(there);
                            } else if (sgn(c[there]) != sgn(c[here])) {
                                System.out.println(Math.abs(c[there]) + Math.abs(c[here]) - 1);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static int get(int state, int value){
        int bit = (state >> ((value - 1)*2));
        return bit&3;
    }

    private static int set(int[] values, int line){
        int result = 0;

        for(int v : values)
            result |= set(result, v, line);

        return result;
    }

    private static int set(int state, int value, int line){
        return (state & ~(3 << ((value - 1)*2))) | (line << ((value - 1)*2));
    }

    private static int incr(int value){
        return value < 0 ? value - 1 : value + 1;
    }

    private static int sgn(int value){
        return value > 0 ? 1 : -1;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}