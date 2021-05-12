package com.algospot.chapter8.wildcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private String wild;
    private int n;
    private String[] files;
    private List<String> result;
    private int[][] cache;

    public void run(){
        int c = sc.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase() {
        init();

        for(String file : files){
            cache = new int[file.length()][wild.length()];

            for(int i=0; i<file.length(); i++)
                for(int j=0; j<wild.length(); j++)
                    cache[i][j] = -1;

            if(solve(file, 0, 0, false) == 1)
                result.add(file);
        }

        Collections.sort(result);

        for(String s : result)
            System.out.println(s);
    }

    private void init() {
        wild = sc.next();
        n = sc.nextInt();
        files = new String[n];
        result = new ArrayList<>();

        for(int i=0; i<n; i++)
            files[i] = sc.next();
    }

    private int solve(String file, int f, int w, boolean isStar){
        if(w == wild.length())
            if(isStar || f == file.length())
                return 1;
            else
                return 0;

        if(f == file.length()){
            for(int i=w; i<wild.length(); i++)
                if(wild.charAt(i) != '*')
                    return 0;

            return 1;
        }

        if(cache[f][w] != -1)
            return cache[f][w];

        cache[f][w] = 0;

        if(wild.charAt(w) == '*')
            cache[f][w] |= solve(file, f, w + 1, true);

        if(isStar){
            for(int i=f; i<file.length(); i++)
                if((wild.charAt(w) == '?') || (wild.charAt(w) == file.charAt(i)))
                    cache[f][w] |= solve(file, i + 1, w + 1, false);
        }else{
            if((wild.charAt(w) == '?') || (wild.charAt(w) == file.charAt(f)))
                cache[f][w] |= solve(file, f + 1, w + 1, false);
        }

        return cache[f][w];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
