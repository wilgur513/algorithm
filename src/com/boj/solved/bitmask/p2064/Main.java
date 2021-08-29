package com.boj.solved.bitmask.p2064;

import java.io.*;
import java.util.*;

public class Main {
    private static final int INF = 987654321;
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[][] ips;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        ips = new int[n][4];

        for(int i = 0; i < n; i++) {
            String ip = reader.readLine();
            StringTokenizer tokenizer = new StringTokenizer(ip, ".");
            ips[i][0] = Integer.parseInt(tokenizer.nextToken());
            ips[i][1] = Integer.parseInt(tokenizer.nextToken());
            ips[i][2] = Integer.parseInt(tokenizer.nextToken());
            ips[i][3] = Integer.parseInt(tokenizer.nextToken());
        }
    }

    private static void solve() {
        int[] minIp = new int[4];
        int[] subnet = new int[4];
        boolean found = false;

        for(int i = 0; i < 4; i++) {
            if(found) {
                minIp[i] = 0;
                subnet[i] = 0;
            } else {
                minIp[i] = minIp(i);
                subnet[i] = subnet(i);
                found = !isAllSame(i);
            }
        }
        System.out.println(toIp(minIp));
        System.out.println(toIp(subnet));
    }

    private static int minIp(int pos) {
        if(isAllSame(pos)) {
            return ips[0][pos];
        }
        return ips[0][pos] & bitmask(pos);
    }

    private static int subnet(int pos) {
        if(isAllSame(pos)) {
            return 255;
        }
        return bitmask(pos);
    }

    private static boolean isAllSame(int pos) {
        int ip = ips[0][pos];
        for(int i = 1; i < n; i++) {
            if((ip ^ ips[i][pos]) != 0) {
                return false;
            }
        }
        return true;
    }

    private static int bitmask(int pos) {
        int m = findM(pos);
        int bitmask = (1 << 8) - 1;
        for(int i = 0; i <=m; i++) {
            bitmask &= ~(1 << i);
        }
        return bitmask;
    }

    private static int findM(int pos) {
        int xor = xor(pos);
        for(int i = 7; i >=0; i--) {
            if((xor & (1 << i)) > 0) {
                return i;
            }
        }
        throw new AssertionError();
    }

    private static int xor(int pos) {
        return minValue(pos) ^ maxValue(pos);
    }

    private static int minValue(int pos) {
        int ip = ips[0][pos];
        for(int i = 1; i < n; i++) {
            if(ip > ips[i][pos]) {
                ip = ips[i][pos];
            }
        }
        return ip;
    }

    private static int maxValue(int pos) {
        int ip = ips[0][pos];
        for(int i = 1; i < n; i++) {
            if(ip < ips[i][pos]) {
                ip = ips[i][pos];
            }
        }
        return ip;
    }

    private static String toIp(int[] values) {
        return values[0] + "." + values[1] + "." + values[2] + "." + values[3];
    }
}
