package com.boj.solved.trie.p5052;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

class TrieNode {
    TrieNode[] child;
    boolean isTerminated;

    public TrieNode() {
        child = new TrieNode[10];
        isTerminated = false;
    }

    public boolean insert(String s, int i) {
        if(i == s.length()) {
            isTerminated = true;
            return false;
        }

        if(child[index(s, i)] == null) {
            child[index(s, i)] = new TrieNode();
            return child[index(s, i)].insert(s, i + 1);
        }

        if(child[index(s, i)].isTerminated) {
            return true;
        }

        return child[index(s, i)].insert(s, i + 1);
    }

    private int index(String s, int i) {
        return s.charAt(i) - '0';
    }
}

class Number implements Comparable<Number>{
    String num;

    public Number(String num) {
        this.num = num;
    }

    @Override
    public int compareTo(Number o) {
        return num.length() < o.num.length() ? -1 : (num.length() > o.num.length() ? 1 : 0);
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    static int n;
    static List<Number> numbers;
    static TrieNode tree;

    public static void main(String[] args) throws Exception{
        int t = Integer.parseInt(reader.readLine());
        while(t > 0) {
            inputData();
            solve();
            t--;
        }
    }

    private static void inputData() throws Exception {
        n = Integer.parseInt(reader.readLine());
        numbers = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            numbers.add(new Number(reader.readLine()));
        }
        Collections.sort(numbers);
    }

    private static void solve() {
        tree = new TrieNode();
        for(Number num : numbers) {
            if(tree.insert(num.num, 0)) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }
}
