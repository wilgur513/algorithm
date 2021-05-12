package com.algospot.chapter20.habit;

import java.util.*;

public class Main {
    static class SuffixComparator implements Comparator<Integer>{
        private static int t;
        private static int[] group;

        public static void setT(int t) {
            SuffixComparator.t = t;
        }

        public static void setGroup(int[] group) {
            SuffixComparator.group = group;
        }

        @Override
        public int compare(Integer o1, Integer o2) {
            if(group[o1] != group[o2])
                return group[o1] == group[o2] ? 0 : (group[o1] > group[o2] ? 1 : -1);

            return group[o1 + t] == group[o2 + t] ? 0 : (group[o1 + t] > group[o2 + t] ? 1 : -1);
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static int k;
    private static String str;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++){
            runSingleTestCase();
        }
    }

    private static void runSingleTestCase() {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() {
        k = scanner.nextInt();
        str = scanner.next();
    }

    private static int solve() {
        Integer[] suffixArray = getSuffixArray();
        int result = 0;

        for(int i=0; i + k <= str.length(); i++)
            result = Math.max(result, commonPrefix(suffixArray[i], suffixArray[i + k - 1]));
        return result;
    }

    private static Integer[] getSuffixArray() {
        int n = str.length();
        int t = 1;
        int[] group = new int[n + 1];
        Integer[] index = new Integer[n];

        for(int i=0; i<n; i++){
            group[i] = str.charAt(i);
            index[i] = i;
        }
        group[n] = -1;
        SuffixComparator comparator = new SuffixComparator();

        while(t < n){
            comparator.setT(t);
            comparator.setGroup(group);
            Arrays.sort(index, comparator);
            t *= 2;

            if(t >= n)
                break;

            int[] newGroup = new int[n + 1];
            newGroup[index[0]] = 0;
            newGroup[n] = -1;

            for(int i=1; i<n; i++){
                if(comparator.compare(index[i], index[i - 1]) == 0)
                    newGroup[index[i]] = newGroup[index[i - 1]];
                else
                    newGroup[index[i]] = newGroup[index[i - 1]] + 1;
            }

            group = newGroup;
        }


        return index;
    }

    private static int commonPrefix(int i, int j) {
        int result = 0;

        while(i < str.length() && j < str.length()){
            if(str.charAt(i) == str.charAt(j)){
                result++;
                i++;
                j++;
            }else
                break;
        }

        return result;
    }

    public static void main(String[] args) {
        run();
    }
}
