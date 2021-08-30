package com.boj.solved.unionfind.p16724;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n, m;
    static char[][] map;
    static int[][] groupMap;
    static int size;
    static boolean[][] visited;
    static int groupSize;
    static int[] parentGroup;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        visited = new boolean[n][m];
        size = 0;
        initMap();
    }

    private static void initMap() throws IOException {
        map = new char[n][m];

        for(int i = 0; i < n; i++) {
            String line = reader.readLine();
            for(int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j);
            }
        }
    }

    private static void solve() throws IOException {
        groupSize = initGroupMap();
        parentGroup = new int[groupSize];

        for(int i = 0; i < groupSize; i++) {
            parentGroup[i] = i;
        }

        unionGroupMap();
        Set<Integer> set = new HashSet<>();

        for(int i = 0; i < groupSize; i++) {
            set.add(findParentGroup(i));
        }

        System.out.println(set.size());
    }

    private static int initGroupMap() {
        int group = 0;
        groupMap = new int[n][m];

        for(int y = 0; y < n; y++) {
            for(int x = 0; x < m; x++) {
                if(!visited[y][x]) {
                    markGroup(y, x, group);
                    group++;
                }
            }
        }

        return group;
    }

    private static void markGroup(int y, int x, int group) {
        while(isValid(y, x) && !visited[y][x]) {
            groupMap[y][x] = group;
            size++;
            visited[y][x] = true;
            int[] next = move(y, x);
            y = next[0];
            x = next[1];
        }
    }

    private static void unionGroupMap() {
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < m; x++) {
                int[] next = move(y, x);

                if(isValid(next[0], next[1])) {
                    unionGroup(groupMap[y][x], groupMap[next[0]][next[1]]);
                }
            }
        }
    }

    private static void unionGroup(int a, int b) {
        int parentA = findParentGroup(a);
        int parentB = findParentGroup(b);

        if(parentA < parentB) {
            parentGroup[parentB] = parentA;
        } else {
            parentGroup[parentA] = parentB;
        }
    }

    private static int findParentGroup(int group) {
        if(parentGroup[group] == group) {
            return group;
        }

        parentGroup[group] = findParentGroup(parentGroup[group]);
        return parentGroup[group];
    }

    private static int[] move(int y, int x) {
        switch (map[y][x]) {
            case 'L':
                return new int[]{y, x - 1};
            case 'R':
                return new int[]{y, x + 1};
            case 'D':
                return new int[]{y + 1, x};
            case 'U':
                return new int[]{y - 1, x};
        }

        throw new AssertionError();
    }

    private static boolean isValid(int y, int x) {
        return (0 <= y && y < n) && (0 <= x && x < m);
    }
}
