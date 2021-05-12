package com.algospot.chapter20.palindromize;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String str;

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
        str = scanner.next();
    }

    private static int solve() {
        int len = str.length();
        String inverse = new StringBuilder(str).reverse().toString();
        int[] pi = getPartialMatched(inverse);

        int start = 0;
        int matched = 0;

        while(start + matched < len){
            if(inverse.charAt(matched) == str.charAt(start + matched)){
                matched++;

                if(start + matched == len)
                    break;
            }else{
                if(matched == 0)
                    start++;
                else{
                    start += matched - pi[matched - 1];
                    matched = pi[matched - 1];
                }
            }
        }

        return 2*len - matched;
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
