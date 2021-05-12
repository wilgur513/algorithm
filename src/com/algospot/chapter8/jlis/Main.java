package com.algospot.chapter8.jlis;

import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static int n, m;
    private static int[] a = new int[100];
    private static int[] b = new int[100];
    private static int[][] result = new int[101][101];

    public static void run() {
        int c = sc.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private static void runTestCase() {
        init();

        System.out.println(cal(-1, -1));
    }

    private static void init() {
        n = sc.nextInt();
        m = sc.nextInt();
        a = new int[n];
        b = new int[m];
        result = new int[n + 1][m + 1];

        for(int i=0; i<n; i++)
            a[i] = sc.nextInt();

        for(int i=0; i<m; i++)
            b[i] = sc.nextInt();
    }

    private static int cal(int aIndex, int bIndex) {
       if(result[aIndex + 1][bIndex + 1] != 0)
           return result[aIndex + 1][bIndex + 1];

       long valueA = aIndex == - 1 ? Long.MIN_VALUE : a[aIndex];
       long valueB = bIndex == - 1 ? Long.MIN_VALUE : b[bIndex];
       long max = Math.max(valueA, valueB);

       for(int i=aIndex + 1; i<a.length; i++)
           if(max < a[i]) {
               result[aIndex + 1][bIndex + 1] = Math.max(result[aIndex + 1][bIndex + 1], cal(i, bIndex) + 1);
           }

       for(int i=bIndex + 1; i<b.length; i++){
           if(max < b[i]) {
               result[aIndex + 1][bIndex + 1] = Math.max(result[aIndex + 1][bIndex + 1], cal(aIndex, i) + 1);
           }
       }

       return result[aIndex + 1][bIndex + 1];
    }

    public static void main(String[] args) {
        run();
    }

}
