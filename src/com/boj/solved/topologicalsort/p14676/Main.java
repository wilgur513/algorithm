package com.boj.solved.topologicalsort.p14676;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    static int n, m, k;
    static List<Integer>[] map;
    static int[][] commands;
    static int[] maxSize;
    static int[] createdCount;
    static int[] createdSize;
    static boolean[] isPossibleToCreate;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        k = Integer.parseInt(tokenizer.nextToken());

        map = new List[n];
        maxSize = new int[n];
        for(int i = 0; i < n; i++) {
            map[i] = new ArrayList<>();
        }

        for(int i = 0; i < m; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int from = Integer.parseInt(tokenizer.nextToken()) - 1;
            int to = Integer.parseInt(tokenizer.nextToken()) - 1;
            map[from].add(to);
            maxSize[to]++;
        }

        commands = new int[k][2];
        for(int i = 0; i < k; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            commands[i][0] = Integer.parseInt(tokenizer.nextToken());
            commands[i][1] = Integer.parseInt(tokenizer.nextToken()) - 1;
        }
    }

    private static void solve() {
        createdCount = new int[n];
        createdSize = new int[n];
        isPossibleToCreate = new boolean[n];
        String message = "King-God-Emperor";

        for(int[] command : commands) {
            if(isCreate(command[0]) && isPossibleCreate(command[1])) {
                createBuilding(command);
            } else if(isDestroy(command[0]) && isPossibleDestroy(command[1])) {
                destoryBuilding(command);
            } else {
                message = "Lier!";
                break;
            }
        }
        System.out.println(message);
    }

    private static void createBuilding(int[] command) {
        createdCount[command[1]]++;
        if(createdCount[command[1]] == 1) {
            for(int to : map[command[1]]) {
                createdSize[to]++;
            }
        }
    }

    private static void destoryBuilding(int[] command) {
        createdCount[command[1]]--;
        if(createdCount[command[1]] == 0) {
            for(int to : map[command[1]]) {
                createdSize[to]--;
            }
        }
    }

    private static boolean isCreate(int command) {
        return command == 1;
    }

    private static boolean isDestroy(int command) {
        return command == 2;
    }

    private static boolean isPossibleCreate(int building) {
        return maxSize[building] == createdSize[building];
    }

    private static boolean isPossibleDestroy(int building) {
        return createdCount[building] > 0;
    }
}
