package com.algospot.chapter20.jaehasafe;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static String[] str;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() {
        n = scanner.nextInt();
        str = new String[n + 1];

        for(int i=0; i<=n; i++)
            str[i] = scanner.next();
    }

    private static int solve() {
        int result = 0;

        for(int i=0; i<n; i++)
            if(i % 2 == 0)
                result += count(str[i], str[i + 1]);
            else
                result += count(str[i + 1], str[i]);

        return result;
    }

    private static int count(String state, String want) {
        int pi[] = getPartialMatched(state);
        want += want;
        int start = 1;
        int matched = 0;

        while(matched < state.length()){
            if(state.charAt(matched) == want.charAt(start + matched)){
                matched++;

                if(matched == state.length())
                    return start;
            }else{
                if(matched == 0)
                    start++;
                else{
                    start += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }

        throw new AssertionError();
    }

    private static int[] getPartialMatched(String str) {
        int[] result = new int[str.length()];

        int start = 1;
        int matched = 0;

        while(start + matched < str.length()){
            if(str.charAt(start + matched) == str.charAt(matched)){
                matched++;
                result[start + matched - 1] = matched;
            }else{
                if(matched == 0)
                    start++;
                else{
                    start += matched - result[matched - 1];
                    matched = result[matched - 1];
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        run();
    }
}
