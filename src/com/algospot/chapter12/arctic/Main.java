package com.algospot.chapter12.arctic;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static double[][] path;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        initialize();
        double min = getMinDistance();
        System.out.println(String.format("%.2f", min));
    }

    private static void initialize() {
        n = scanner.nextInt();
        double[] x = new double[n];
        double[] y = new double[n];

        for(int i=0; i<n; i++) {
            x[i] = scanner.nextDouble();
            y[i] = scanner.nextDouble();
        }

        path = new double[n][n];

        for(int i=0; i<n; i++)
            for(int j=0; j<n; j++){
                if(i == j)
                    continue;

                path[i][j] = Math.sqrt(Math.pow(x[i] - x[j], 2) + Math.pow(y[i] - y[j], 2));
            }

    }

    private static double getMinDistance() {
        double low = 0.0d;
        double hi = 1416.0d;

        for(int i=0; i<100; i++){
            double mid = (low + hi) / 2;

            if(isPossibleDistance(mid))
                hi = mid;
            else
                low = mid;
        }

        return low;
    }

    private static boolean isPossibleDistance(double d) {
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        int count = 1;

        visited[0] = true;
        stack.push(0);

        while(!stack.isEmpty()){
            int current = stack.pop();

            for(int next=0; next<n; next++){
                if(!visited[next] && path[current][next] <= d){
                    stack.push(next);
                    visited[next] = true;
                    count++;
                }
            }
        }

        return count == n;
    }

    public static void main(String[] args) {
        run();
    }
}
