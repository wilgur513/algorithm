package com.algospot.chapter7.quadtree;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private String image;
    private int[] starts;
    private int index;

    public void run(){
        int c = scanner.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();

        scanner.close();
    }

    private void runTestCase() {
        init();
        System.out.println(solve());
    }

    private void init() {
        image = scanner.next();
        starts = new int[4];
        index = -1;
    }

    private String solve() {
        index++;

        if(image.charAt(index) != 'x') {
            return String.valueOf(image.charAt(index));
        }

        String result = "x";

        String a1 = solve();
        String a2 = solve();
        String a3 = solve();
        String a4 = solve();

        return result + a3 + a4 + a1 + a2;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
