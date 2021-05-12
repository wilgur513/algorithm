package com.algospot.chapter9.zimbabwe.refactor;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for (int i = 0; i < numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        String e = scanner.next();
        int m = scanner.nextInt();

        System.out.println(PossiblePriceCounter.count(e, m));
    }

    public static void main (String[]args){
        new Main().run();
    }
}

