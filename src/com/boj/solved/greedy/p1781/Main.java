package com.boj.solved.greedy.p1781;

import java.util.*;
import java.io.*;

public class Main {
    static class Problem implements Comparable<Problem>{
        int time;
        int number;

        public Problem(int time, int number) {
            this.time = time;
            this.number = number;
        }

        @Override
        public int compareTo(Problem o) {
            if(time > o.time)
                return 1;
            else if(time < o.time)
                return -1;

            return 0;
        }

        public String toString(){
            return time + " " + number;
        }
    }

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static int n;
    static List<Problem> list;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        list = new ArrayList<>();

        StringTokenizer tokenizer;
        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int time = Integer.parseInt(tokenizer.nextToken());
            int number = Integer.parseInt(tokenizer.nextToken());
            list.add(new Problem(time - 1, number));
        }
        Collections.sort(list);
    }

    private static void solve() {
        int result = 0;

        int[] end = new int[n];
        int[] start = new int[n];
        Arrays.fill(end, -1);
        Arrays.fill(start, -1);

        for(int i = list.size() - 1; i >= 0; i--){
            Problem p = list.get(i);
            if(end[p.time] == -1)
                end[p.time] = i;
        }

        for(int i = 0; i < list.size(); i++){
            Problem p = list.get(i);
            if(start[p.time] == -1)
                start[p.time] = i;
        }

        PriorityQueue<Integer> queue = new PriorityQueue<Integer>((a, b) -> (b.compareTo(a)));

        for(int i = n - 1; i >= 0; i--){
            if(end[i] != -1 && start[i] != -1) {
                for (int j = start[i]; j <= end[i]; j++) {
                    Problem p = list.get(j);
                    queue.add(p.number);
                }
            }

            result += queue.peek() == null ? 0 : queue.poll();
        }

        System.out.println(result);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
