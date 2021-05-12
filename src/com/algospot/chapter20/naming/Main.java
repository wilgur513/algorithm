package com.algospot.chapter20.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static String dad, mom;
    private static Scanner scanner = new Scanner(System.in);

    private static void run() {
        dad = scanner.next();
        mom = scanner.next();
        String name = dad + mom;
        List<Integer> list = solve(name);

        for(int i=list.size() - 1; i >=0; i--)
            System.out.print(list.get(i) + " ");
    }

    private static List<Integer> solve(String name) {
        List<Integer> result = new ArrayList<>();

        int[] pi = getPartialMatched(name);
        int k = name.length();

        while(k > 0){
            result.add(k);
            k = pi[k - 1];
        }

        return result;
    }

    private static int[] getPartialMatched(String name) {
        int[] result = new int[name.length()];

        int start = 1;
        int matched = 0;

        while(start + matched < name.length()){
            if(name.charAt(start + matched) == name.charAt(matched)){
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
