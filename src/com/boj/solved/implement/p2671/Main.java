package com.boj.solved.implement.p2671;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String signal;

    public static void main(String[] args) {
        inputData();
        solve();
    }

    private static void inputData() {
        signal = scanner.next();
    }

    private static void solve() {
        System.out.println(identify(signal) ? "SUBMARINE" : "NOISE");
    }

    private static boolean identify(String signal) {
        if(signal.length() == 0)
            return true;

        if(signal.startsWith("01"))
            return identify(signal.substring(2));
        else if(signal.startsWith("100")){
            int first = 3;
            int firstOneAfterSequence = signal.indexOf("1", first);

            if(firstOneAfterSequence == -1)
                return false;

            int firstZeroAfterSequence = signal.indexOf("0", firstOneAfterSequence);

            if(firstZeroAfterSequence == -1)
                return true;

            boolean result = identify(signal.substring(firstZeroAfterSequence));

            if(firstZeroAfterSequence - firstOneAfterSequence >= 2)
                result |= identify(signal.substring(firstZeroAfterSequence - 1));

            return result;
        }

        return false;
    }
}
