package com.boj.unsolved.dp.p11054;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[] values;
    private static int[][][] result;

    private static void run() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
        values = new int[n];
        result = new int[1002][1002][2];

        for(int i = 0; i < n; i++)
            values[i] = Integer.parseInt(tokenizer.nextToken());
    }

    private static void solve(){
        int result = Math.max(cal(-1, 0, 1), cal(-1, 1001, 0));
        System.out.println(result);

    }

    private static int cal(int current, int prevValue, int dir){
        if(result[current + 1][prevValue][dir] != 0)
            return result[current + 1][prevValue][dir];

        int r = 0;

        for(int i = current + 1; i < n; i++){
            if(prevValue == values[i])
                continue;

            if(dir == 1){
                int d = values[i] > prevValue ? 1 : 0;
                r = Math.max(r, cal(i, values[i], d) + 1);
            }else if(dir == 0) {
                if (values[i] > prevValue) {
                    continue;
                } else {
                    r = Math.max(r, cal(i, values[i], 0) + 1);
                }
            }
        }

        result[current + 1][prevValue][dir] = r;
        return r;
    }


    public static void main(String[] args) throws IOException {
        run();
    }
}
