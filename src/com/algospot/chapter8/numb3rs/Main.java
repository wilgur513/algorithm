package com.algospot.chapter8.numb3rs;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int numberOfVillage;
    private int remainDays;
    private int prisonVillage;
    private int numberOfWantToKnowVillage;
    private int[][] path;
    private int[] numberOfCanVisit;
    private int[] wantToKnowVillages;
    private double[][] result;

    private void run() {
        int numberOfCase = scanner.nextInt();
        
        for(int i=0; i<numberOfCase; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();

        for(int i=0; i<numberOfWantToKnowVillage; i++)
            System.out.print(percent(wantToKnowVillages[i], remainDays) + " ");
        System.out.println();
    }

    private void initialize() {
        numberOfVillage = scanner.nextInt();
        remainDays = scanner.nextInt();
        prisonVillage = scanner.nextInt();
        path = new int[numberOfVillage][numberOfVillage];
        numberOfCanVisit = new int[numberOfVillage];

        for(int i = 0; i< numberOfVillage; i++)
            for(int j = 0; j< numberOfVillage; j++) {
                path[i][j] = scanner.nextInt();
                numberOfCanVisit[i] += path[i][j];
            }

        numberOfWantToKnowVillage = scanner.nextInt();
        wantToKnowVillages = new int[numberOfWantToKnowVillage];

        for(int i = 0; i< numberOfWantToKnowVillage; i++)
            wantToKnowVillages[i] = scanner.nextInt();

        result = new double[numberOfVillage][remainDays];
    }

    private double percent(int currentVillage, int remainDays) {
        if(remainDays == 0)
            return currentVillage == prisonVillage ? 1 : 0;

        if(result[currentVillage][remainDays - 1] != 0)
            return result[currentVillage][remainDays - 1];

        for(int i=0; i<numberOfVillage; i++){
            if(path[currentVillage][i] == 1){
                result[currentVillage][remainDays - 1] +=  percent(i, remainDays - 1) / numberOfCanVisit[i];
            }
        }

        return result[currentVillage][remainDays - 1];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
