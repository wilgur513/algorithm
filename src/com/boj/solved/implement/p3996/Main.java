package com.boj.solved.implement.p3996;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static long n;
    private static int k;

    private static void run() {
        inputData();
        solve();
    }

    private static void inputData() {
        n = scanner.nextLong();
        k = scanner.nextInt();
    }

    private static void solve() {
        int[] kNotation = convertNotation(n);
        long result = cal(kNotation, 0);
        System.out.println(isKNumber(kNotation) ? result + 1 : result);
    }

    private static int[] convertNotation(long number) {
        long value = number;
        int index = 0;

        while(value > 0){
            value /= k;
            index++;
        }

        int[] notation = new int[index];
        value = number;

        while(value > 0){
            notation[--index] = (int)(value % k);
            value /= k;
        }

        return notation;
    }

    private static long cal(int[] notation, int index){
        long result;

        if(notation.length % 2 == 0){
            result = index % 2 == 0 ? calEvenLength(notation, index) : calOddLength(notation, index);
        }else{
            result = index % 2 == 0 ? calOddLength(notation, index) : calEvenLength(notation, index);
        }

        return result;
    }

    private static long calOddLength(int[] notation, int index){
        long result = notation[index];

        for(int i = index + 2; i < notation.length; i += 2) {
            result *= k;
        }

        long remain = 0;

        for(int i = index + 1; i < notation.length; i++){
            if(notation[i] != 0) {
                remain = cal(notation, i);
                break;
            }
        }

        return result + remain;
    }

    private static long calEvenLength(int[] notation, int index){
        long result = 1;

        for(int i = index + 1; i < notation.length; i += 2)
            result *= k;

        return result;
    }

    private static boolean isKNumber(int[] notation){
        if(notation.length % 2 == 0){
            for(int i = 0; i < notation.length; i++)
                if(notation[i] != 0 && i % 2 == 0)
                    return false;
        }else{
            for(int i = 0; i < notation.length; i++)
                if(notation[i] != 0 && i % 2 != 0)
                    return false;
        }

        return true;
    }

    public static void main(String[] args) {
        run();
    }
}
