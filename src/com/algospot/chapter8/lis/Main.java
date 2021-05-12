package com.algospot.chapter8.lis;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private int n;
    private int[] list;
    private int[] result;

    private void run() {
        int c = sc.nextInt();

        for (int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase() {
        init();

        System.out.println(cal(-1));
    }

    private void init() {
        n = sc.nextInt();
        list = new int[n];
        result = new int[n + 1];

        for(int i=0; i<n; i++)
            list[i] = sc.nextInt();
    }

    private int cal(int index) {
        if(index == list.length - 1)
            return 1;

        if(result[index + 1] != 0)
            return result[index + 1];

        result[index + 1] = 1;

        for(int i=index + 1; i<list.length; i++)
            if(index == -1)
                result[index + 1] = Math.max(cal(i), result[index + 1]);
            else if(list[index] < list[i])
                result[index + 1] = Math.max(cal(i) + 1, result[index + 1]);

        return result[index + 1];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
