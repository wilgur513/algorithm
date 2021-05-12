package com.algospot.chapter22.nerd2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class Person implements Comparable<Person>{
        int p;
        int q;

        public Person(int p, int q) {
            this.p = p;
            this.q = q;
        }

        @Override
        public int compareTo(Person o) {
            return p < o.p ? -1 : (p == o.p ? 0 : 1);
        }

        public String toString(){
            return "{" + p +", " + q + "}";
        }
    }

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static Person[] people;
    private static TreeSet<Person> set;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();

        int result = 0;

        for(int i=0; i<n; i++)
            result += cal(i);

        System.out.println(result);
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        set = new TreeSet<>();
        people = new Person[n];
        StringTokenizer tokenizer = null;

        for(int i=0; i<n; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int p = Integer.parseInt(tokenizer.nextToken());
            int q = Integer.parseInt(tokenizer.nextToken());
            people[i] = new Person(p, q);
        }
    }

    private static int cal(int index) {
        Person person = people[index];

        if(isDominated(person))
            return set.size();

        removeDominatedPerson(person);

        return set.size();
    }

    private static boolean isDominated(Person person) {
        Person higher = set.higher(person);

        if(higher == null)
            return false;

        return higher.q > person.q;
    }

    private static void removeDominatedPerson(Person person){
        List<Person> list = new ArrayList<>();
        NavigableSet<Person> headSet = set.headSet(person, false);
        Iterator<Person> iterator = headSet.descendingIterator();

        while(iterator.hasNext()){
            Person p = iterator.next();

            if(p.q < person.q)
                list.add(p);
            else
                break;
        }

        for(Person p : list)
            set.remove(p);

        set.add(person);

    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
