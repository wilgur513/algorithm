package com.boj.solved.implement.p17143;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Shark{
        int y;
        int x;
        int speed;
        int dir;
        int size;

        public Shark(int y, int x, int speed, int dir, int size) {
            this.y = y;
            this.x = x;
            this.speed = speed;
            this.dir = dir;
            this.size = size;
        }

        public void move(){
            y = newY(y, speed);
            x = newX(x, speed);
        }

        private int newY(int y, int speed){
            int newY = (dir == 1) ? y - speed : ((dir == 2) ? y + speed : y);

            if(newY < 0 || newY >= r){
                changeDir();
                return newY < 0 ? newY(0, speed - y) : newY(r - 1,speed - (r - 1 - y));
            }

            return newY;
        }

        private int newX(int x, int speed){
            int newX = dir == 3 ? x + speed : (dir == 4) ? x - speed : x;

            if(newX < 0 || newX >= c) {
                changeDir();
                return newX < 0 ? newX(0,speed - x) : newX(c - 1, speed - (c - 1 - x));
            }

            return newX;
        }

        private void changeDir(){
            if(dir == 1 || dir == 3)
                dir++;
            else
                dir--;
        }
    }

    static class Sharks{
        Shark[][] map = new Shark[r][c];

        public void addShark(int y, int x, int speed, int dir, int size){
            map[y][x] = new Shark(y, x, speed, dir, size);
        }

        public int fishingShark(int x) {
            if(hasShark(x)) {
                Shark shark = findNearbyShark(x);
                map[shark.y][shark.x] = null;

                return shark.size;
            }

            return 0;
        }

        public void move(){
            Shark[][] newMap = new Shark[r][c];

            List<Shark> sharks = sharks();

            for(Shark shark : sharks){
                shark.move();
                Shark other = newMap[shark.y][shark.x];
                newMap[shark.y][shark.x] = other == null ? shark : (other.size < shark.size) ? shark : other;
            }

            map = newMap;
        }

        private boolean hasShark(int x){
            return findNearbyShark(x) == null ? false : true;
        }

        private Shark findNearbyShark(int x){
            for(int y = 0; y < r; y++){
                if(map[y][x] != null)
                    return map[y][x];
            }

            return null;
        }

        private List<Shark> sharks(){
            List<Shark> result = new ArrayList<>();

            for(int y = 0; y < r; y++){
                for(int x = 0; x < c; x++){
                    if(map[y][x] != null){
                        result.add(map[y][x]);
                    }
                }
            }

            return result;
        }
    }

    private static int r, c, m;
    private static Sharks sharks;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        r = Integer.valueOf(tokenizer.nextToken());
        c = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());

        sharks = new Sharks();

        for(int i = 0; i < m; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int y = Integer.valueOf(tokenizer.nextToken()) - 1;
            int x = Integer.valueOf(tokenizer.nextToken()) - 1;
            int speed = Integer.valueOf(tokenizer.nextToken());
            int dir = Integer.valueOf(tokenizer.nextToken());
            int size = Integer.valueOf(tokenizer.nextToken());

            sharks.addShark(y, x, speed, dir, size);
        }
    }

    private static void solve() {
        int result = 0;

        for(int x = 0; x < c; x++) {
            result += sharks.fishingShark(x);
            sharks.move();
        }

        System.out.println(result);
    }
}
