package com.boj.unsolved.math.p7453;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n;
    static long[] a, b, c, d;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(reader.readLine());
        a = new long[n];
        b = new long[n];
        c = new long[n];
        d = new long[n];

        for(int i = 0; i < n; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            a[i] = Long.parseLong(tokenizer.nextToken());
            b[i] = Long.parseLong(tokenizer.nextToken());
            c[i] = Long.parseLong(tokenizer.nextToken());
            d[i] = Long.parseLong(tokenizer.nextToken());
        }
    }

    private static void solve() {
        long[] sumOfAB = sum(a, b);
        long[] sumOfCD = sum(c, d);

        long result = 0;

        for(long ab : sumOfAB) {
            result += upper(sumOfCD, -ab) - lower(sumOfCD, -ab);
        }

        System.out.println(result);
    }

    private static long[] sum(long[] list1, long[] list2) {
        long[] sum = new long[n * n];
        int index = 0;

        for(long value1 : list1) {
            for(long value2 : list2) {
                sum[index++] = (value1 + value2);
            }
        }

        Arrays.sort(sum);
        return sum;
    }

    private static long upper(long[] sumOfCD, long value) {
        int left = 0;
        int right = sumOfCD.length;
        while(left < right) {
            int mid = (left + right) / 2;
            if(sumOfCD[mid] <= value) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return right;
    }

    private static long lower(long[] sumOfCD, long value) {
        int left = 0;
        int right = sumOfCD.length;
        while(left < right) {
            int mid = (left + right) / 2;
            if(sumOfCD[mid] < value) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return right;
    }
}
