package com.boj.solved.kmp.p1701;

import java.io.*;
import java.util.*;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static String s;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        s = reader.readLine();
    }

    private static void solve() {
        int max = -1;
        for(int i = 0; i < s.length(); i++) {
            String a = s.substring(i);
            int[] pi = getPi(a);
            max = Math.max(Arrays.stream(pi).max().getAsInt(), max);
        }
        System.out.println(max);
    }

    private static int[] getPi(String s) {
        int[] pi = new int[s.length()];
        int begin = 1;
        int matched = 0;

        while(begin + matched < s.length()) {
            if(s.charAt(begin + matched) == s.charAt(matched)) {
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
