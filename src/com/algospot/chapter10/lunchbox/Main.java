package com.algospot.chapter10.lunchbox;

import java.util.*;

public class Main {
    private static class LunchBox implements Comparable<LunchBox>{
        final int warm;
        final int eat;

        public LunchBox(int warm, int eat) {
            this.warm = warm;
            this.eat = eat;
        }


        @Override
        public int compareTo(LunchBox o) {
            if(eat < o.eat)
                return 1;
            else if(eat > o.eat)
                return -1;

            if(warm < o.warm)
                return -1;
            else if(warm > o.warm)
                return 1;

            return 0;
        }
    }
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static List<LunchBox> lunchBox;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        initialize();
        System.out.println(solve());
    }

    private static void initialize() {
        n = scanner.nextInt();
        int[] warms = new int[n];
        int[] eats = new int[n];

        for(int i=0; i<n; i++)
            warms[i] = scanner.nextInt();

        for(int i=0; i<n; i++)
            eats[i] = scanner.nextInt();

        lunchBox = new ArrayList<LunchBox>();

        for(int i=0; i<n; i++)
            lunchBox.add(new LunchBox(warms[i], eats[i]));

        Collections.sort(lunchBox);
    }

    private static int solve() {
        int result[][] = new int[n][2];
        result[0][0] = lunchBox.get(0).warm;
        result[0][1] = result[0][0] + lunchBox.get(0).eat;

        for(int i=1; i<n; i++){
            int w = lunchBox.get(i).warm;
            int e = lunchBox.get(i).eat;

            result[i][0] = result[i -1][0] + w;
            result[i][1] = result[i][0] + e;
        }

        int max = result[0][1];

        for(int i=1; i<n; i++)
            max = Math.max(max, result[i][1]);

        return max;
    }

    public static void main(String[] args) {
        run();
    }
}
