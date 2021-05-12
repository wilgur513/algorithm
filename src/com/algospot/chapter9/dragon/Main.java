package com.algospot.chapter9.dragon;

import java.util.Scanner;

public class Main {
    private static final int CAN_CAL_GENERATION = 20;
    private Scanner scanner = new Scanner(System.in);
    private int n;
    private long p;
    private int l;
    private String[] curves = new String[CAN_CAL_GENERATION + 1];
    private long[] lengths = new long[51];

    public void run() {
        int numberOfCase = scanner.nextInt();

        for(int i=0; i<numberOfCase; i++)
            runSingleTestCase();
    }


    private void runSingleTestCase() {
        initialize();
        System.out.println(findPartialCurve(n, p - 1, p + l - 1));
    }

    private void initialize() {
        n = scanner.nextInt();
        p = scanner.nextLong();
        l = scanner.nextInt();

        curves[0] = "FX";
        curves[1] = "FX+YF";

        lengths[0] = 2;
    }

    private String findPartialCurve(int generation, long start, long end) {
        if(start > end)
            return "";

        if(generation <= CAN_CAL_GENERATION)
            return curve(generation).substring((int)start, (int)end);

        if(length(generation) <= start - end + 1)
            return findPartialCurve(generation, start, length(generation) - 1);

        long[] partialLength = {length(generation - 1), length(generation - 2)};
        long partialStart = start;
        long partialEnd = end;

        if(partialStart < partialLength[0]){
            String result = findPartialCurve(generation - 1, partialStart, partialEnd);

            if(partialLength[0] <= partialEnd)
                result +=  "+" + findPartialCurve(generation - 2, 0, partialEnd - partialLength[0] - 1);

            return result;
        }

        partialStart -= partialLength[0];
        partialEnd -= partialLength[0];

        if(partialStart == 0){
            return "+" + findPartialCurve(generation - 2, 0, partialEnd - 1);
        }

        partialStart -= 1;
        partialEnd -= 1;

        if(partialStart < partialLength[1]){
            String result = findPartialCurve(generation - 2, partialStart, partialEnd);

            if(partialLength[1] <= partialEnd)
                result += "-" + findPartialCurve(generation - 1, 0, partialEnd - partialLength[1] - 1);

            return result;
        }

        partialStart -= partialLength[1];
        partialEnd -= partialLength[1];

        if(partialStart == 0){
            return "-" + findPartialCurve(generation - 1, 0, partialEnd - 1);
        }

        partialStart -= 1;
        partialEnd -= 1;

        return findPartialCurve(generation - 1, partialStart, partialEnd);
    }

    private String curve(int generation){
        if(curves[generation] != null)
            return curves[generation];

        StringBuilder builder = new StringBuilder();
        builder.append(curve(generation - 1))
                .append("+")
                .append(curve(generation - 2))
                .append("-")
                .append(curve(generation - 1).substring(curve(generation - 1).length()/2 + 2));
        curves[generation] = builder.toString();

        return curves[generation];
    }

    private long length(int generation){
        if(lengths[generation] != 0)
            return lengths[generation];

        lengths[generation] = length(generation - 1)*2 + 1;

        return lengths[generation];
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
