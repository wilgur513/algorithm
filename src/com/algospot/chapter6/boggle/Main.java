package com.algospot.chapter6.boggle;

import java.util.Scanner;

class Test{
    static void run(int c, Main main){
        for(int i=0; i<c; i++)
            main.solve();
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String[] list = new String[5];
    private Boolean result[][][];

    public String hasWord(String str){
        for(int x=0; x<5; x++)
            for(int y=0; y<5; y++)
                if( list[y].charAt(x) == str.charAt(0) && hasWordHelp(str, x, y, 1))
                    return "YES";

        return "NO";
    }

    private boolean hasWordHelp(String str, int x, int y, int index) {
        if(str.length() == index)
            return true;

        if(result[x][y][index] != null)
            return result[x][y][index];

        for(int dx=-1; dx<2; dx++){
            for(int dy=-1; dy<2; dy++){
                if(dx == 0 && dy == 0) continue;
                if(x + dx < 0 || x + dx > 4) continue;
                if(y + dy < 0 || y + dy > 4) continue;

                if(list[y + dy].charAt(x + dx) == str.charAt(index))
                    if(hasWordHelp(str, x + dx, y + dy, index+1)){
                        result[x][y][index] = true;
                        return true;
                    }
            }
        }

        result[x][y][index] = false;
        return false;
    }

    public void solve(){
        for(int i=0; i<5; i++)
            list[i] = scanner.next();

        int n = scanner.nextInt();
        String[] strings = new String[n];

        for(int i=0; i<n; i++)
            strings[i] = scanner.next();

        for(int i=0; i<n; i++) {
            result = new Boolean[5][5][strings[i].length()];

            System.out.println(strings[i] + " " + hasWord(strings[i]));
        }
    }

    public static void main(String[] args) {
        Test.run(scanner.nextInt(), new Main());
    }
}