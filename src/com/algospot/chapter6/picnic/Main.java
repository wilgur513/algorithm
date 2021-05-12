package com.algospot.chapter6.picnic;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private int n, m;
    private int[][] list;
    private boolean[] taken;

    public void run(){
        int c = scanner.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase(){
        n = scanner.nextInt();
        m = scanner.nextInt();
        list = new int[m][2];
        taken = new boolean[n];

        for(int i=0; i<m; i++) {
            list[i][0] = scanner.nextInt();
            list[i][1] = scanner.nextInt();
        }

        System.out.println(cal(0, 0));
    }

    private int cal(int index, int count){
        if(count == n) return 1;
        if(index == m) return 0;

        int result = 0;

        result = cal(index + 1, count);
        if(taken[list[index][0]] == false && taken[list[index][1]] == false){
            taken[list[index][0]] = true;
            taken[list[index][1]] = true;
            result += cal(index + 1, count + 2);
            taken[list[index][0]] = false;
            taken[list[index][1]] = false;
        }

        return result;
    }

    public static void main(String[] args) {
        new Main().run();
        scanner.close();
    }
}
