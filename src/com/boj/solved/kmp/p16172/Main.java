package com.boj.solved.kmp.p16172;

import java.util.*;
import java.io.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static String s, k;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        s = reader.readLine().replaceAll("[0-9]", "");
        k = reader.readLine();
    }

    private static void solve() {
        List<Integer> result = kmp(s, k);
        System.out.println(result.size() > 0 ? 1 : 0);
    }

    private static List<Integer> kmp(String s, String k) {
        int begin = 0;
        int matched = 0;
        List<Integer> result = new ArrayList<>();
        int[] pi = pi(k);

        while(begin <= s.length() - k.length()) {
            if(matched < k.length() && s.charAt(begin + matched) == k.charAt(matched)) {
                matched++;
                if(matched == k.length()) {
                    result.add(begin);
                }
            } else {
                if(matched == 0) {
                    begin++;
                } else {
                    begin += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }
        return result;
    }

    private static int[] pi(String n) {
        int[] pi = new int[n.length()];
        int matched = 0;
        int begin = 1;

        while(begin + matched < n.length()) {
            if(n.charAt(begin + matched) == n.charAt(matched)) {
                matched++;
                pi[begin + matched - 1] = matched;
            } else {
                if(matched == 0) {
                    begin++;
                } else {
                    begin += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }
        return pi;
    }


}
