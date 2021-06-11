package com.boj.unsolved.binary_search.p1300;

import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static int k;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        k = scanner.nextInt();
    }

    private static void solve() {
        long low = 1;
        long high = k;
        long result = -1;

        while(low <= high) {
            long mid = (low + high) / 2;

            if(order(mid) < k) {
                low = mid + 1;
            } else {
                result = mid;
                high = mid - 1;
            }
        }

        System.out.println(result);
    }

    static int order(long value) {
        int count = 0;
        for(int i = 1; i <= n; i++) {
            count += Math.min(value / i, n);
        }
        return count;
    }
}
