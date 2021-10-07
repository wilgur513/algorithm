package com.boj.unsolved.binarysearch.p1208;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n, s;
    static int[] values;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        s = Integer.parseInt(tokenizer.nextToken());
        values = new int[n];

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++)
            values[i] = Integer.parseInt(tokenizer.nextToken());
    }

    private static void solve() {
        long result = 0;

        List<Integer> a = makeSumArray(0, n/2);
        List<Integer> b = makeSumArray(n/2, n);

        int aIndex = 0;
        int bIndex = b.size() - 1;

        while(aIndex < a.size() && bIndex >= 0){
            int aValue = a.get(aIndex);
            int bValue = b.get(bIndex);

            if(aValue + bValue == s){
                long aCount = 0;
                long bCount = 0;

                while(aIndex < a.size() && aValue == a.get(aIndex)){
                    aCount++;
                    aIndex++;
                }

                while(bIndex >= 0 && bValue == b.get(bIndex)){
                    bCount++;
                    bIndex--;
                }

                result += aCount * bCount;
            }else if(aValue + bValue > s){
                bIndex--;
            }else {
                aIndex++;
            }
        }

        System.out.println(s == 0 ? result - 1: result);
    }

    private static List<Integer> makeSumArray(int start, int end){
        List<Integer> result = new ArrayList<>();
        make(result, start, end, 0);
        Collections.sort(result);
        return result;
    }

    private static void make(List<Integer> list, int here, int end, int sum){
        if(here == end) {
            list.add(sum);
            return;
        }

        make(list, here + 1, end, sum + values[here]);
        make(list, here + 1, end, sum);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
