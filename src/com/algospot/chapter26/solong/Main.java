package com.algospot.chapter26.solong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Data implements Comparable<Data>{
    int value;
    String str;

    public Data(String str, int value) {
        this.str = str;
        this.value = value;
    }

    @Override
    public int compareTo(Data o) {
        if(o.value < value)
            return -1;
        else if(o.value > value)
            return 1;

        return str.compareTo(o.str);
    }
}

class Trie{
    int value;
    Trie[] child;

    public Trie() {
        value = -1;
        child = new Trie[26];
    }

    public void insert(Data data, int index){
        insert(data.str, 0, index);
    }

    private void insert(String str, int index, int value){
        if(this.value == -1)
            this.value = value;

        if(str.length() == index)
            return;

        int next = numberTo(str.charAt(index));

        if(child[next] == null)
            child[next] = new Trie();

        child[next].insert(str, index + 1, value);
    }

    public int search(String str, List<Data> data){
        for(int i=0; i<str.length()-1; i++){
            String prefix = str.substring(0, i + 1);
            Trie trie = find(prefix, 0);

            if(trie == null)
                break;

            Data d = data.get(trie.value);

            if(d.str.equals(str))
                return i + 2;
        }

        return str.length();
    }

    private Trie find(String str, int index) {
        if(str.length() == index)
            return this;

        int next = numberTo(str.charAt(index));

        if(child[next] == null)
            return null;

        return child[next].find(str, index + 1);
    }

    private int numberTo(char ch) {
        return ch - 'A';
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n, m;
    private static List<Data> data;
    private static String[] words;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++){
            runSingleTestCase();
        }
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        n = Integer.parseInt(tokenizer.nextToken());
        m = Integer.parseInt(tokenizer.nextToken());
        words = new String[m];
        data = new ArrayList<>();

        for(int i=0; i<n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            String word = tokenizer.nextToken();
            int value = Integer.parseInt(tokenizer.nextToken());

            data.add(new Data(word, value));
        }

        Collections.sort(data);
        tokenizer = new StringTokenizer(reader.readLine());

        for(int i=0; i<m; i++){
            words[i] = tokenizer.nextToken();
        }
    }

    private static void solve() {
        int result = calculateTypeNumber(makeTrie());
        System.out.println(result);
    }

    private static Trie makeTrie(){
        Trie root = new Trie();

        for(int i=0; i<data.size(); i++)
            root.insert(data.get(i), i);

        return root;
    }

    private static int calculateTypeNumber(Trie root){
        int result = m - 1;

        for(int i=0; i<m; i++)
            result += root.search(words[i], data);

        return result;
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
