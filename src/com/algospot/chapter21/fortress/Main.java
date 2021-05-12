package com.algospot.chapter21.fortress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class Fortress{
        int x, y, r;
        List<Fortress> child;

        public Fortress(int x, int y, int r) {
            this.x = x;
            this.y = y;
            this.r = r;
            child = new ArrayList<>();
        }

        public void addChild(Fortress c){
            child.add(c);
        }

        public boolean isChild(Fortress f){
            int distance = (x - f.x)*(x - f.x) + (y - f.y)*(y - f.y);

            if(distance <= r*r)
                return f.r < r;

            return false;
        }
    }

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static Fortress root = null;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        System.out.println(solve(root));
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine().trim());
        int[][] values = new int[n][3];

        for(int i=0; i<n; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

            values[i][0] = Integer.parseInt(tokenizer.nextToken());
            values[i][1] = Integer.parseInt(tokenizer.nextToken());
            values[i][2] = Integer.parseInt(tokenizer.nextToken());
        }

        root = makeTree(values);
    }

    private static int solve(Fortress fortress) {
        if(fortress.child.isEmpty())
            return 0;

        int size = fortress.child.size();
        int[] levels = getLevels(fortress);
        int first = getFirstMax(levels);
        int result = levels[first];

        if(size > 1){
            int second = getSecondMax(first, levels);
            result = levels[first] + levels[second];
        }

        for(Fortress child : fortress.child){
            result = Math.max(result, solve(child));
        }

        return result;
    }

    private static Fortress makeTree(int[][] values) {
        Fortress root = new Fortress(values[0][0], values[0][1], values[0][2]);

        for(int i=1; i<n; i++){
            Fortress fortress = new Fortress(values[i][0], values[i][1], values[i][2]);
            Fortress parent = getParent(root, fortress);
            parent.addChild(fortress);

            for(Fortress child : parent.child){
                if(fortress.isChild(child)){
                    fortress.addChild(child);
                    parent.child.remove(child);
                    break;
                }
            }

        }

        return root;
    }

    private static Fortress getParent(Fortress parent, Fortress fortress) {
        if(parent.isChild(fortress)){
            if(parent.child.isEmpty()) {
                return parent;
            }else {
                Fortress result = null;

                for(Fortress child : parent.child){
                    result = getParent(child, fortress);

                    if(result != null)
                        return result;
                }

                if(result == null)
                    return parent;
            }
        }

        return null;
    }

    private static int[] getLevels(Fortress fortress) {
        int[] levels = new int[fortress.child.size()];

        for(int i=0; i<fortress.child.size(); i++)
            levels[i] = getMaxLevel(fortress.child.get(i));

        return levels;
    }

    private static int getFirstMax(int[] levels) {
        int index = 0;

        for(int i=1; i<levels.length; i++)
            if(levels[index] < levels[i])
                index = i;

        return index;
    }

    private static int getSecondMax(int first, int[] levels) {
        int index = first == 0 ? 1 : 0;

        for(int i=0; i<levels.length; i++)
            if(i != first && levels[i] > levels[index])
                index = i;

        return index;
    }

    private static int getMaxLevel(Fortress fortress) {
        int result = 1;

        for(Fortress child : fortress.child)
            result = Math.max(result, getMaxLevel(child) + 1);

        return result;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
