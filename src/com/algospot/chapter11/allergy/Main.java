package com.algospot.chapter11.allergy;

import java.util.Scanner;

public class Main {
    class Person{
        String name;
        int[] favorFoods;
        int index;

        public Person(String name) {
            this.name = name;
            favorFoods = new int[50];
            index = 0;
        }

        public void addFavorFood(int food){
            favorFoods[index++] = food;
        }

        @Override
        public boolean equals(Object o){
            return name.equals(o);
        }
    }

    class Food{
        int id;
        int[] people;
        int index;

        public Food(int id, String[] people) {
            this.id = id;
            this.people = new int[50];
            index = 0;

            for(int i=0; i<names.length; i++)
                for(String favor : people)
                    if(favor.equals(names[i]))
                        this.people[index++] = i;
        }
    }

    Scanner scanner = new Scanner(System.in);
    int n;
    int m;
    String[] names;
    Person[] people;
    Food[] foods;
    int best;

    private void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private void runSingleTestCase() {
        initialize();
        solve(0, 0, 0);
        System.out.println(best);
    }

    private void initialize() {
        n = scanner.nextInt();
        m = scanner.nextInt();
        names = new String[n];
        best = 987654321;

        for(int i=0; i<n; i++)
            names[i] = scanner.next();

        people = new Person[n];
        for(int i=0; i<n; i++)
            people[i] = new Person(names[i]);

        String[][] favor = new String[m][];
        for(int food=0; food<m; food++) {
            int num = scanner.nextInt();
            favor[food] = new String[num];

            for (int i=0; i<num; i++)
                favor[food][i] = scanner.next();

            for(String name : favor[food])
                for(Person p : people)
                    if(p.equals(name))
                        p.addFavorFood(food);
        }

        foods = new Food[m];
        for(int food=0; food<m; food++)
            foods[food] = new Food(food, favor[food]);
    }

    private void solve(long peopleBit, long foodBit, int count) {
        if(best <= count)
            return;

        if(peopleBit == ((long)1 << n) - 1) {
            best = Math.min(best, count);
            return;
        }

        if(foodBit == ((long)1 << m) - 1)
            return;

        int p = 0;

        for(int i=0; i<n; i++)
            if((peopleBit & ((long)1 << i)) == 0)
                p = i;

        for(int food : people[p].favorFoods)
            if((foodBit & ((long)1 << food)) == 0){
                long nextPeopleBit = peopleBit;

                for(int i=0; i<foods[food].index; i++)
                    nextPeopleBit |= ((long)1 << foods[food].people[i]);

                solve(nextPeopleBit, foodBit | ((long)1 << food), count + 1);
            }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
