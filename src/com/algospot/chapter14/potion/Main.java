package com.algospot.chapter14.potion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int[] minFactors = new int[1001];
    private static int n;
    private static int[] materials;
    private static int[] usedMaterials;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        initMinFactors();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void initMinFactors() {
        for(int i=0; i<=1000; i++)
            minFactors[i] = i;

        int sqrt = (int)Math.sqrt(1000);

        for(int i=2; i<=sqrt; i++)
            if(minFactors[i] == i)
                for(int j=i*i; j<=1000; j+=i)
                    if(minFactors[j] == j)
                        minFactors[j] = i;
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine().trim());
        materials = new int[n];
        usedMaterials = new int[n];

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++)
            materials[i] = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine());
        for(int i=0; i<n; i++)
            usedMaterials[i] = Integer.parseInt(tokenizer.nextToken());
    }

    private static void solve() {
        divideMaterialsWithFactor();
        int multiple = 0;
        int min = 987654321;

        for(int i=0; i<n; i++){
            int value = usedMaterials[i] / materials[i];

            if((usedMaterials[i] % materials[i]) != 0)
                value++;

            int sum = getSum(value);

            if(sum < min){
                min = sum;
                multiple = value;
            }
        }

        for(int i=0; i<n; i++)
            System.out.print((multiple*materials[i] - usedMaterials[i]) + " ");
        System.out.println();
    }

    private static int getSum(int value) {
        int result = 0;

        for(int i=0; i<n; i++)
            if(value*materials[i] < usedMaterials[i])
                return 987654321;
            else
                result += value*materials[i] - usedMaterials[i];

        return result;
    }

    private static void divideMaterialsWithFactor() {
        while(true){
            if(isFinished())
                break;

            if(isEqualMinFactor())
                for (int i = 0; i < n; i++)
                    materials[i] /= minFactors[materials[i]];
            else
                break;
        }
    }

    private static boolean isFinished() {
        for(int i=0; i<n; i++)
            if(materials[i] == 1)
                return true;

        return false;
    }

    private static boolean isEqualMinFactor() {
        for(int i=0; i<n-1; i++)
            if(minFactors[materials[i]] != minFactors[materials[i + 1]])
                return false;

        return true;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
