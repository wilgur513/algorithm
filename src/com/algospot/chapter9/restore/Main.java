package com.algospot.chapter9.restore;

import java.util.*;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int k;
    private List<String> words;
    private int[][] overlap;
    private int[][] maxOverlap;

    private void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();
        System.out.println(restoreString(-1, 0));
    }

    private void initialize() {
        int num = scanner.nextInt();
        String[] list = new String[num];

        for(int i=0; i<num; i++)
            list[i] = scanner.next();

        words = removeDuplicateAndContainWords(list);
        k = words.size();
        overlap = new int[k][k];
        maxOverlap = new int[k + 1][1 << k];

        for(int i=0; i<k; i++)
            Arrays.fill(overlap[i], -1);

        for(int i=0; i<=k; i++)
            Arrays.fill(maxOverlap[i], -1);
    }

    private List<String> removeDuplicateAndContainWords(String[] arg) {
        List<String> list = new ArrayList<>(new HashSet<>(Arrays.asList(arg)));
        List<String> result = new ArrayList<>();

        for(String select : list){
            boolean isContain = false;

            for(String compare : list){
                if(!select.equals(compare) && compare.contains(select)){
                    isContain = true;
                    break;
                }
            }

            if(!isContain){
                result.add(select);
            }
        }

        return result;
    }

    private String restoreString(int prev, int included) {
        for(int i=0; i<k; i++){
            int shift = 1 << i;

            if((included & shift) == 0){
                if(prev == -1 && maxOverlap(prev, included) == maxOverlap(i, included | shift)){
                    return words.get(i) + restoreString(i, included | shift);
                }else if(prev != -1 && maxOverlap(prev, included) == overlap(prev, i) + maxOverlap(i, included | shift))
                    return words.get(i).substring(overlap(prev, i)) + restoreString(i, included | shift);
            }
        }

        return "";
    }

    private int maxOverlap(int prev, int included){
        if(maxOverlap[prev + 1][included] != -1)
            return maxOverlap[prev + 1][included];

        maxOverlap[prev + 1][included] = 0;

        for(int i=0; i<k; i++){
            int shift = 1 << i;

            if((included & shift) == 0){
                if(prev == -1 && maxOverlap[prev + 1][included] < maxOverlap(i, included | shift)){
                    maxOverlap[prev + 1][included] = maxOverlap(i, included | shift);
                }else if(prev != -1 && maxOverlap[prev + 1][included] < overlap(prev, i) + maxOverlap(i, included | shift)) {
                    maxOverlap[prev + 1][included] = overlap(prev, i) + maxOverlap(i, included | shift);
                }
            }
        }

        return maxOverlap[prev + 1][included];
    }

    private int overlap(int prefixIndex, int suffixIndex){
        if(overlap[prefixIndex][suffixIndex] != -1)
            return overlap[prefixIndex][suffixIndex];

        overlap[prefixIndex][suffixIndex] = 0;
        String prefix = words.get(prefixIndex);
        String suffix = words.get(suffixIndex);

        int start = startIndex(prefix, suffix);

        for(int i=start; i<prefix.length(); i++){
            int length = prefix.length() - i;

            if(prefix.substring(i).equals(suffix.substring(0, length))){
                overlap[prefixIndex][suffixIndex] = Math.max(overlap[prefixIndex][suffixIndex], length);
            }
        }

        return overlap[prefixIndex][suffixIndex];
    }

    private int startIndex(String prefix, String suffix) {
        if(prefix.length() > suffix.length())
            return prefix.length() - suffix.length() + 1;
        else
            return 1;
    }


    public static void main(String[] args) {
        new Main().run();
    }
}
