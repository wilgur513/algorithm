package com.boj.unsolved.implement.p1655;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static int[] list;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());
        list = new int[n];
        for(int i = 0; i < n; i++) {
            list[i] = Integer.parseInt(reader.readLine());
        }
    }

    private static void solve() {
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        PriorityQueue<Integer> right = new PriorityQueue<>();

        int first = list[0];
        int second = list[1];

        left.add(Math.min(first, second));
        right.add(Math.max(first, second));

        StringBuilder builder = new StringBuilder(String.format("%d\n%d\n", first, left.peek()));

        for(int i = 2; i< n; i++) {
            int value = list[i];

            if(left.size() == right.size()) {
                if(left.peek() < value && value < right.peek()) {
                    left.add(value);
                } else if(left.peek() < value && right.peek() < value) {
                    right.add(value);
                    left.add(right.remove());
                } else {
                    left.add(value);
                }
            } else {
                if(left.peek() < value && value < right.peek()) {
                    right.add(value);
                } else if(left.peek() < value && right.peek() < value) {
                    right.add(value);
                } else {
                    left.add(value);
                    right.add(left.remove());
                }
            }

            builder.append(left.peek()).append("\n");
        }

        System.out.println(builder);
    }


}
