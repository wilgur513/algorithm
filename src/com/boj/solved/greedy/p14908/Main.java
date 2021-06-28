package com.boj.solved.greedy.p14908;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Task{
    int id;
    int time;
    int cost;

    public Task(int id, int time, int cost) {
        this.id = id;
        this.time = time;
        this.cost = cost;
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static Task[] tasks;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.valueOf(reader.readLine());
        tasks = new Task[n];

        for(int i = 0; i < n; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            tasks[i] = new Task(i + 1,
                                Integer.valueOf(tokenizer.nextToken()),
                                Integer.valueOf(tokenizer.nextToken()));
        }
    }

    private static void solve() {
        Arrays.sort(tasks, (a, b) ->
           Integer.compare(a.time * b.cost, b.time * a.cost) == 0 ?
                   Integer.compare(a.id, b.id) : Integer.compare(a.time * b.cost, b.time * a.cost)
        );

        Arrays.stream(tasks).forEach(a -> System.out.println(a.id));
    }
}
