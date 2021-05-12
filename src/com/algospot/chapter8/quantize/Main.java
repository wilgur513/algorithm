package com.algospot.chapter8.quantize;

import java.util.*;

public class Main {
    private static final int INF = 987654321;
    private Scanner scanner = new Scanner(System.in);
    private int n;
    private int s;
    private List<Integer> numbers;
    private int[][] results;
    private int[][] sums;

    public void run() {
        int c = scanner.nextInt();

        for(int i=0; i<c; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();

        int result = quantize(0, s);
        System.out.println(result);
    }

    private void initialize() {
        n = scanner.nextInt();
        s = scanner.nextInt();
        numbers = new ArrayList<Integer>();
        results = new int[n][s];
        sums = new int[n][n];

        for(int i=0; i<n; i++)
            numbers.add(scanner.nextInt());

        Collections.sort(numbers);
    }

private int quantize(int start, int numberOfGroup){
    if(start == n)
        return INF;

    if(results[start][numberOfGroup - 1] != 0)
        return results[start][numberOfGroup - 1];

    results[start][numberOfGroup - 1] = INF;

    if(numberOfGroup == 1){
        results[start][numberOfGroup - 1] = Math.min(results[start][numberOfGroup - 1], sumOfSquaredError(start, n));
    }else if(numberOfGroup > 1){
        results[start][numberOfGroup - 1] = Math.min(results[start][numberOfGroup - 1], quantize(start, numberOfGroup - 1));

        for(int i=start; i<n; i++){
            results[start][numberOfGroup - 1] = Math.min(results[start][numberOfGroup - 1], sumOfSquaredError(start, i + 1) + quantize(i + 1, numberOfGroup - 1));
        }
    }

    return results[start][numberOfGroup - 1];
}

    private int sumOfSquaredError(int start, int end){
        if(sums[start][end - 1] != 0)
            return sums[start][end - 1];

        int avg = 0;
        int num = end - start;

        for(int i=start; i<end; i++)
            avg += numbers.get(i);

        if(2*(avg % num) > num)
            avg = avg/num + 1;
        else
            avg = avg/num;

        for(int i=start; i<end; i++)
            sums[start][end - 1] += (numbers.get(i) - avg)*(numbers.get(i) - avg);

        return sums[start][end - 1];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
