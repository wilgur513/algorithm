package com.boj.solved.bruteforce.p1079;

import java.util.*;
import java.io.*;

import static java.lang.System.in;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    private static int n;
    private static int[] points;
    private static int[][] r;
    private static int mafia;
    private static boolean[] remains;

    public static void main(String[] args) throws Exception{
        inputData();
        solve();
    }

    private static void inputData() throws Exception {
        n = Integer.parseInt(reader.readLine());
        points = new int[n];
        r = new int[n][n];
        remains = new boolean[n];
        Arrays.fill(remains, true);

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for(int i = 0; i < n; i++) {
            points[i] = Integer.parseInt(tokenizer.nextToken());
        }

        for(int i = 0; i < n; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for(int j = 0; j < n; j++) {
                r[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        mafia = Integer.parseInt(reader.readLine());
    }

    private static void solve() {
        System.out.println(cal());
    }

    private static int cal() {
        if(isMafiaDied() || isRemainOnlyMafia()) {
            return 0;
        }

        if(isMorning()) {
            int victim = findVictim(points, remains);
            remains[victim] = false;
            int result = cal();
            remains[victim] = true;
            return result;
        }

        int result = -1;
        for(int victim = 0; victim < n; victim++) {
            if(remains[victim] && victim != mafia) {
                adjustPoints(r[victim]);
                remains[victim] = false;
                result = Math.max(result, cal() + 1);
                remains[victim] = true;
                undoPoints(r[victim]);
            }
        }
        return result;
    }

    private static void undoPoints(int[] adjust) {
        for(int i = 0; i < n; i++) {
            points[i] -= adjust[i];
        }
    }

    private static void adjustPoints(int[] adjust) {
        for(int i = 0; i < n; i++) {
            points[i] += adjust[i];
        }
    }

    private static boolean isMafiaDied() {
        return !remains[mafia];
    }

    private static boolean isRemainOnlyMafia() {
        for(int i = 0; i < n; i++) {
            if(i != mafia && remains[i]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isMorning() {
        return remainPeople() % 2 != 0;
    }

    private static int remainPeople() {
        int sum = 0;
        for(boolean b : remains) {
            sum += b ? 1 : 0;
        }
        return sum;
    }

    private static int findVictim(int[] points, boolean[] remains) {
        int victim = -1;
        for(int num = 0; num < n; num++) {
            if(remains[num] && (victim == -1 || points[victim] < points[num])) {
                victim = num;
            }
        }
        return victim;
    }
}
