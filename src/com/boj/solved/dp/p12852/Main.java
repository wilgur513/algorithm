package com.boj.solved.dp.p12852;

import java.util.*;

class Pair{
    int value;
    int count;
    String path;

    public Pair(int value, int count, String path) {
        this.value = value;
        this.count = count;
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return value == pair.value && count == pair.count && Objects.equals(path, pair.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}

public class Main {
    static int x;

    public static void main(String[] args) {
        inputData();
        solve();
    }

    private static void inputData() {
        x = new Scanner(System.in).nextInt();
    }

    private static void solve() {
        Pair p = min();
        System.out.println(p.count);
        System.out.println(p.path);
    }

    private static Pair min() {
        Queue<Pair> queue = new LinkedList<>();
        boolean[] flag = new boolean[x + 1];
        queue.add(new Pair(x, 0, String.valueOf(x)));
        flag[x] = true;

        while(!queue.isEmpty()) {
            Pair here = queue.remove();
            int value = here.value;
            int count = here.count;
            String path = here.path;

            if(value == 1) {
                return here;
            }

            if(value % 3 == 0) {
                Pair next = new Pair(value / 3, count + 1, path + " " + (value / 3));
                if(!flag[next.value]) {
                    flag[next.value] = true;
                    queue.add(next);
                }
            }

            if(value % 2 == 0) {
                Pair next = new Pair(value / 2, count + 1, path + " " + (value / 2));
                if(!flag[next.value]) {
                    flag[next.value] = true;
                    queue.add(next);
                }
            }

            Pair next = new Pair(value - 1, count + 1, path + " " + (value - 1));
            if(!flag[next.value]) {
                flag[next.value] = true;
                queue.add(next);
            }
        }

        throw new AssertionError();
    }
}
