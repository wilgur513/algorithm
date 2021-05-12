package com.algospot.chapter9.packing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int n;
    private int w;
    private String[] names;
    private int[] volume;
    private int[] earnest;
    private int[][] cache;
    private int[][] choices;
    private List<String> selectedStuff;

    private void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();
        int maxEarnest = packing(-1, w);
        reconstruct(-1, w);

        System.out.println(maxEarnest + " " + selectedStuff.size());

        for(String name : selectedStuff)
            System.out.println(name);
    }

    private void initialize() {
        n = scanner.nextInt();
        w = scanner.nextInt();
        names = new String[n];
        volume = new int[n];
        earnest = new int[n];
        cache = new int[n + 1][w + 1];
        choices = new int[n + 1][w + 1];
        selectedStuff = new ArrayList<String>();

        for(int i=0; i<n; i++){
            names[i] = scanner.next();
            volume[i] = scanner.nextInt();
            earnest[i] = scanner.nextInt();
        }

        for(int i=0; i<=n; i++)
            for(int j=0; j<=w; j++)
                choices[i][j] = -1;
    }

    private int packing(int current, int capacity) {
        if(capacity <=0)
            return 0;

        if(cache[current + 1][capacity] != 0)
            return cache[current + 1][capacity];

        int selected = -1;

        for(int i=current + 1; i<n; i++){
            if(capacity - volume[i] >= 0){
                int result = packing(i, capacity - volume[i]) + earnest[i];

                if(cache[current + 1][capacity] < result){
                    cache[current + 1][capacity] = result;
                    selected = i;
                }
            }
        }

        choices[current + 1][capacity] = selected;
        return cache[current + 1][capacity];
    }

    private void reconstruct(int current, int capacity) {
        if(current != -1) selectedStuff.add(names[current]);
        int next = choices[current + 1][capacity];
        if(next != -1) reconstruct(next, capacity - volume[next]);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
