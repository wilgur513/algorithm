package com.algospot.chapter16.graduation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int k;
    private static int m;
    private static int l;
    private static int[] prior;
    private static int[] canTake;
    private static int[][][] cache;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();

        int result = solve(0, 0, 0);

        if(result == 987654321)
            System.out.println("IMPOSSIBLE");
        else
            System.out.println(result);
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        l = Integer.parseInt(tokenizer.nextToken());

        int[][] r = new int[n][];

        for(int i=0; i<n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int size = Integer.parseInt(tokenizer.nextToken());

            r[i] = new int[size + 1];
            r[i][0] = size;

            for(int j=1; j<=size; j++){
                r[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        int[][] c = new int[m][];

        for(int i=0; i<m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int size = Integer.parseInt(tokenizer.nextToken());

            c[i] = new int[size + 1];
            c[i][0] = size;

            for(int j=1; j<=size; j++){
                c[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        prior = new int[n];

        for(int i=0; i<n; i++){
            for(int j=1; j<=r[i][0]; j++)
                prior[i] |= (1 << r[i][j]);
        }

        canTake = new int[m];

        for(int i=0; i<m; i++){
            for(int j=1; j<=c[i][0]; j++)
                canTake[i] |= (1 << c[i][j]);
        }

        cache = new int[1 << n][m][11];
    }

    private static int solve(int take, int term, int count) {
        if(Integer.bitCount(take) == k)
            return count;

        if(term == m)
            return 987654321;

        if(cache[take][term][count] != 0)
            return cache[take][term][count];

        cache[take][term][count] = solve(take, term + 1, count);

        for(int lecture = 1; lecture <(1 << n); lecture++){
            if(Integer.bitCount(lecture) > l)
                continue;

            if(isSatisfy(take, term, lecture)){
                cache[take][term][count] = Math.min(cache[take][term][count], solve(take | lecture, term + 1, count + 1));
            }
        }

        return cache[take][term][count];
    }

    private static boolean isSatisfy(int take, int term, int lecture) {
        while(lecture != 0){
            int index = Integer.numberOfTrailingZeros(lecture);
            lecture &= (lecture - 1);

            if((canTake[term] & (1 << index)) == 0)
                return false;

            if((take & prior[index]) != prior[index])
                return false;
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
