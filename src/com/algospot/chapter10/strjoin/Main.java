package com.algospot.chapter10.strjoin;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static List<Integer> list;

    private static void run() {
        int num = scanner.nextInt();

        for(int i=0; i<num; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        initialize();
        System.out.println(solve(list));
    }

    private static void initialize() {
        n = scanner.nextInt();
        list = new ArrayList<Integer>();

        for(int i=0; i<n; i++)
            list.add(scanner.nextInt());
    }

    private static int solve(List<Integer> list) {
        if(list.size() == 1)
            return 0;

        Collections.sort(list);
        int result = 0;

        for(int i=0; i<2; i++){
            result += list.get(0);
            list.remove(0);
        }

        list.add(result);

        return result + solve(list);
    }

    public static void main(String[] args) {
        run();
    }
}
