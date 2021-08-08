package com.boj.solved.dfs.p1103;

import java.util.*;

public class Main {
    static final int INF = 1000000;
    private static Scanner scanner = new Scanner(System.in);
    static int n, m, count;
    static char[][] board;
    static int[] order, result;
    static boolean[] isFinished;

    private static void run() {
        inputData();
        solve();
    }

    private static void inputData() {
        n = scanner.nextInt();
        m = scanner.nextInt();
        board = new char[n][m];

        for(int i = 0; i < n; i++){
            String s = scanner.next();

            for(int j = 0; j < m; j++){
                board[i][j] = s.charAt(j);
            }
        }

        order = new int[n*m];
        Arrays.fill(order, -1);
        isFinished = new boolean[n*m];
        result = new int[n*m];
        count = 0;
    }

    private static void solve() {
        int result = dfs(0);

        if(result >= INF)
            System.out.println(-1);
        else
            System.out.println(result + 1);
    }

    private static int dfs(int here){
        order[here] = count++;
        result[here] = 0;

        for(int there : next(here)){
            if(order[there] == -1){
                result[here] = Math.max(result[here], dfs(there) + 1);
            }else if(order[here] > order[there] && !isFinished[there]){
                result[here] = INF;
            }else if(order[here] < order[there]){
                result[here] = Math.max(result[here], result[there]);
            }else{
                result[here] = Math.max(result[here], result[there] + 1);
            }
        }

        isFinished[here] = true;
        return result[here];
    }

    static List<Integer> next(int here){
        List<Integer> result = new ArrayList<>();
        int x = getX(here);
        int y = getY(here);
        int value = board[y][x] - '0';

        if(isValid(x - value, y))
            result.add(getIndex(x - value, y));

        if(isValid(x + value, y))
            result.add(getIndex(x + value, y));

        if(isValid(x, y - value))
            result.add(getIndex(x, y - value));

        if(isValid(x, y + value))
            result.add(getIndex(x, y + value));

        return result;
    }

    static boolean isValid(int x, int y){
        if(x < 0 || x >= m)
            return false;

        if(y < 0 || y >= n)
            return false;

        if(board[y][x] == 'H')
            return false;

        return true;
    }

    static int getX(int index){
        return index % m;
    }

    static int getY(int index){
        return index / m;
    }

    static int getIndex(int x, int y){
        return y*m + x;
    }

    public static void main(String[] args) {
        run();
    }
}
