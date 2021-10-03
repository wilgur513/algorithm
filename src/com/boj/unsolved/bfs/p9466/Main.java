package com.boj.unsolved.bfs.p9466;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[] want;
    private static int result;
    private static Set<Integer> checkStudent;
    private static Set<Integer> visited;

    public static void main(String[] args) throws IOException {
        int t = Integer.parseInt(reader.readLine());

        for(int i = 0; i < t; i++) {
            inputData();
            solve();
        }
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        result = 0;
        want = new int[n];
        visited = new HashSet<>();
        checkStudent = new HashSet<>();

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++) {
            want[i] = Integer.parseInt(tokenizer.nextToken()) - 1;
        }
    }

    private static void solve() {
        for(int student = 0; student < n; student++) {
            if(visited.contains(student)) {
                continue;
            }

            makeTeam(student);
        }

        System.out.println(n - result);
    }

    private static void makeTeam(int student) {
        visited.add(student);

        if(!visited.contains(want[student])) {
            makeTeam(want[student]);
        }

        if(!checkStudent.contains(want[student])) {
            for(int i = want[student]; i != student; i = want[i]) {
                result++;
            }

            result++;
        }

        checkStudent.add(student);
    }
}
