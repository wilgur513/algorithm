package com.boj.solved.dp.p1516;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

class Building{
    int time;
    List<Integer> needs = new ArrayList<>();

    public Building(int time) {
        this.time = time;
    }

    public void addNeed(int value){
        needs.add(value);
    }
}

public class Main {
    private static final int NEG = -987654321;
    private static int n;
    private static Building[] buildings;
    private static int[] times;


    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.valueOf(reader.readLine());
        buildings = new Building[n];

        for(int i = 0; i < n; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            buildings[i] = new Building(Integer.valueOf(tokenizer.nextToken()));
            int need = Integer.valueOf(tokenizer.nextToken());

            while(need != -1){
                buildings[i].addNeed(need);
                need = Integer.valueOf(tokenizer.nextToken());
            }
        }

        times = new int[n];
        Arrays.fill(times, -1);
    }

    private static void solve() {
        for(int i = 0; i < n; i++)
            System.out.println(time(i));
    }

    private static int time(int num) {
        if(times[num] != -1)
            return times[num];

        int result = buildings[num].time;

        for(int need : buildings[num].needs){
            result = Math.max(result, time(need - 1) + buildings[num].time);
        }
        times[num] = result;

        return result;
    }


}
