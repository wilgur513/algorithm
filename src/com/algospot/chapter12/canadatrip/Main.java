package com.algospot.chapter12.canadatrip;

import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int k;
    private static int[][] city;

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
        city = new int[n][3];

        for(int i=0; i<n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            city[i][0] = Integer.parseInt(tokenizer.nextToken());
            city[i][1] = Integer.parseInt(tokenizer.nextToken());
            city[i][2] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static int solve() {
        int low = 0;
        int hi = 8030000;

        for(int i=0; i<100; i++){
            int distance = (low + hi) / 2;
            long underAndSame = numberOfUnderAndSameBoard(distance);
            long same = numberOfSameBoard(distance);

            if(underAndSame < k)
                low = distance;
            else if(underAndSame - same < k && underAndSame >= k)
                return distance;
            else
                hi = distance;
        }

        throw new AssertionError();
    }

    private static long numberOfUnderAndSameBoard(int distance) {
        int result = 0;

        for(int i=0; i<n; i++){
            int diff = distance > city[i][0] ? city[i][1] : distance - (city[i][0] - city[i][1]);

            if(diff >= 0)
                result += (diff / city[i][2]) + 1;
        }

        return result;
    }

    private static long numberOfSameBoard(int distance) {
        int result = 0;

        for(int i=0; i<n; i++){
            if(distance <= city[i][0] && distance >= (city[i][0] - city[i][1]))
                if((distance - (city[i][0] - city[i][1])) % city[i][2] == 0)
                    result++;
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
