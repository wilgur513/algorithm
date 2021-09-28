package com.boj.unsolved.bfs.p1039;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static int k, result;
    private static boolean[][] visited;
    private static char[] n;

    private static void run() {
        inputData();
        solve();
    }

    private static void inputData() {
        String s = scanner.next();
        n = new char[s.length()];

        for(int i = 0; i < s.length(); i++)
            n[i] = s.charAt(i);

        k = scanner.nextInt();
        result = -1;
        visited = new boolean[1000001][k + 1];
    }

    private static void solve() {
        bfs(n);
    }

    private static void bfs(char[] number){
        Queue<int[]> queue = new LinkedList<>();
        int v = toNumber(number);
        queue.add(new int[]{v, 0});
        visited[v][0] = true;

        while(!queue.isEmpty()){
            int[] here = queue.remove();

            if(here[1] == k){
                result = Math.max(result, here[0]);
                continue;
            }

            char[] ch = toChar(here[0]);

            for(int i = 0; i < ch.length; i++){
                for(int j = i + 1; j < ch.length; j++){
                    if(i == 0 && ch[j] == '0')
                        continue;

                    swap(ch, i, j);
                    if(!visited[toNumber(ch)][here[1] + 1]) {
                        visited[toNumber(ch)][here[1] + 1] = true;
                        queue.add(new int[]{toNumber(ch), here[1] + 1});
                    }
                    swap(ch, i, j);
                }
            }
        }

        System.out.println(result);
    }

    private static char[] toChar(int number){
        return ("" + number).toCharArray();
    }

    private static int toNumber(char[] ch){
        return Integer.parseInt(String.valueOf(ch));
    }

    private static void swap(char[] ch, int i, int j){
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
    }

    public static void main(String[] args) {
        run();
    }
}
