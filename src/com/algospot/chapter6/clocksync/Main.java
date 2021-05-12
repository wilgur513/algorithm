package com.algospot.chapter6.clocksync;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int[] clocks = new int[16];
    private int[][] switches = {{0, 1, 2}, {3, 7, 9, 11}, {4, 10, 14, 15}, {0, 4, 5, 6, 7},
                                {6, 7, 8, 10, 12}, {0, 2, 14, 15}, {3, 14, 15}, {4, 5, 7, 14, 15},
                                {1, 2, 3, 4, 5}, {3, 4, 5, 9, 13}};
    private int[] count = new int[10];
    private int result = 987654321;

    public void run(){
        int c = scanner.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase() {
        init();
        cal(0);

        if(result == 987654321)
            System.out.println("-1");
        else
            System.out.println(result);
    }

    private void init() {
        for(int i=0; i<16; i++)
            clocks[i] = scanner.nextInt();

        for(int i=0; i<10; i++)
            count[i] = 0;

        result = 987654321;
    }

    private void cal(int s){
        if(isAllTwelve()) {
            result = Math.min(result, count());
        }

        if(s == 10)
            return;

        for(int i=0; i<4; i++){
            pressSwitch(s);
            cal(s + 1);
        }
    }

    private boolean isAllTwelve() {
        for(int clock : clocks)
            if(clock != 12) return false;

        return true;
    }

    private void pressSwitch(int index){
        for(int clock : switches[index]){
            clocks[clock] += 3;
            clocks[clock] %= 12;
            clocks[clock] = clocks[clock] == 0 ? 12 : clocks[clock];
        }

        count[index]++;
    }

    private int count() {
        int result = 0;

        for(int c : count) {
            int temp = c%4;
            result += temp;
        }
        return result;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
