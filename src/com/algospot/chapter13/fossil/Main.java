package com.algospot.chapter13.fossil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class LinearFunction{
        double x1, y1;
        double x2, y2;

        public LinearFunction(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public double f(double x){
            double dx = x1 - x2;
            double dy = y1 - y2;

            return dy*(x - x1) / dx + y1;
        }

        public boolean isBetween(double x){
            return (x1 <= x && x <= x2) || (x2 <= x && x <= x1);
        }
    }

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int m;
    private static double[] flowerA;
    private static double[] flowerB;
    private static List<LinearFunction> lower;
    private static List<LinearFunction> upper;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        flowerA = new double[2*n];

        for(int i=0; i<2*n; i++){
            flowerA[i] = Double.parseDouble(tokenizer.nextToken());
        }

        tokenizer = new StringTokenizer(reader.readLine());
        flowerB = new double[2*m];

        for(int i=0; i<2*m; i++){
            flowerB[i] = Double.parseDouble(tokenizer.nextToken());
        }

        lower = new ArrayList<>();
        upper = new ArrayList<>();
        int size = 2*n;

        for(int i=0; i<size; i+=2){
            if(flowerA[i] < flowerA[(i + 2)%size])
                lower.add(new LinearFunction(flowerA[i], flowerA[i + 1],
                                             flowerA[(i + 2) % size], flowerA[(i + 3) % size]));
            else
                upper.add(new LinearFunction(flowerA[i], flowerA[i + 1],
                                             flowerA[(i + 2) % size], flowerA[(i + 3) % size]));
        }

        size = 2*m;

        for(int i=0; i<size; i+=2)
            if(flowerB[i] < flowerB[(i + 2)%size])
                lower.add(new LinearFunction(flowerB[i], flowerB[i + 1],
                                             flowerB[(i + 2) % size], flowerB[(i + 3) % size]));
            else
                upper.add(new LinearFunction(flowerB[i], flowerB[i + 1],
                                             flowerB[(i + 2) % size], flowerB[(i + 3) % size]));
    }

    private static double solve() {
        double lo = getLo();
        double hi = getHi();

        if(lo > hi) return 0.0;

        for(int i=0; i<100; i++){
            double x1 = (2*lo + hi) / 3;
            double x2 = (lo + 2*hi) / 3;

            if(verticalLength(x1) < verticalLength(x2))
                lo = x1;
            else
                hi = x2;
        }

        return Math.max(0.0, verticalLength(hi));
    }

    private static double getLo() {
        double a = 100.0;
        double b = 100.0;

        for(int i=0; i<2*n; i+=2)
            a = Math.min(a, flowerA[i]);

        for(int i=0; i<2*m; i+=2)
            b = Math.min(b, flowerB[i]);

        return Math.max(a, b);
    }

    private static double getHi() {
        double a = 0.0;
        double b = 0.0;

        for(int i=0; i<2*n; i+=2)
            a = Math.max(a, flowerA[i]);

        for(int i=0; i<2*m; i+=2)
            b = Math.max(b, flowerB[i]);

        return Math.min(a, b);
    }

    private static double verticalLength(double x) {
        double u = 100.0;
        double l = 0.0;

        for(LinearFunction f : upper)
            if(f.isBetween(x))
                u = Math.min(u, f.f(x));

        for(LinearFunction f : lower)
            if(f.isBetween(x))
                l = Math.max(l, f.f(x));

        return u - l;
    }




    public static void main(String[] args) throws IOException {
        run();
    }
}
