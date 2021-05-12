package com.algospot.chapter9.zimbabwe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static int DIVISOR = 1000000007;
    private Scanner scanner = new Scanner(System.in);
    private String e;
    private int m;
    private List<Integer> list;
    private int[][] result;

    private void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();
        System.out.println(numberOfPossiblePrice(0, 0, 0));
    }

    private void initialize() {
        e = scanner.next();
        m = scanner.nextInt();
        list = new ArrayList<>();
        int size = 1 << e.length();
        result = new int[m][size];

        for(int i=0; i<m; i++)
            for(int j = 0; j< size; j++)
                result[i][j] = -1;

        for(int i=0; i<e.length(); i++){
            int value = Integer.valueOf(String.valueOf(e.charAt(i)));
            list.add(value);
        }

        Collections.sort(list);
    }

    private int numberOfPossiblePrice(int index, long number, int visit) {
        if(index == e.length() - 1){
            long value = number;

            for(int i=0; i<e.length(); i++){
                int shift = 1 << i;

                if((visit & shift) == 0)
                    value += list.get(i);
            }

            if((value < Long.valueOf(e)) && (value % m == 0))
                return 1;
            else
                return 0;
        }

        if((result[(int)(number % m)][visit] != -1) && (Long.valueOf(e) / (long)Math.pow(10, e.length() - index) != number/ (long)Math.pow(10, e.length() - index)))
            return result[(int)(number % m)][visit];

        result[(int)(number % m)][visit] = 0;
        int prev = -1;

        for(int i=0; i<e.length(); i++){
            int shift = 1 << i;

            if((visit & shift) == 0){
                if(prev == -1 || prev != list.get(i)){
                    long value = number + list.get(i)*(long)Math.pow(10, e.length() - index - 1);

                    if(value < Long.valueOf(e)){
                        result[(int)(number % m)][visit] = (result[(int)(number % m)][visit] +
                                numberOfPossiblePrice(index + 1, value, visit|shift))%DIVISOR;
                        prev = list.get(i);
                    }
                }
            }
        }

        return result[(int)(number % m)][visit];
    }



    public static void main(String[] args) {
        new Main().run();
    }
}