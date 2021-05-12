package com.algospot.chapter29.childrenday;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static char[] d;
    private static int n, m;

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
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        String str = tokenizer.nextToken();
        d = new char[str.length()];

        for(int i = 0; i < str.length(); i++)
            d[i] = str.charAt(i);
        Arrays.sort(d);

        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
    }

    private static void solve() {
        int[] parent = new int[2*n];
        int[] choice = new int[2*n];
        Arrays.fill(parent, -1);
        Arrays.fill(choice, -1);

        Queue<Integer> queue = new LinkedList<>();
        parent[0] = 0;
        queue.add(0);

        while(!queue.isEmpty()){
            int here = queue.remove();

            for(int v : d){
                int there = append(here , v - '0');

                if(parent[there] == -1){
                    parent[there] = here;
                    choice[there] = v - '0';
                    queue.add(there);
                }
            }
        }

        if(parent[n + m] == -1){
            System.out.println("IMPOSSIBLE");
            return;
        }

        int here = n + m;
        String result = "";

        while(parent[here] != here){
            result = (char)(choice[here] + '0') + result;
            here= parent[here];
        }

        System.out.println(result);
    }

    private static int append(int here, int edge){
        int there = here * 10 + edge;

        return there >= n ? n + there % n : there;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}