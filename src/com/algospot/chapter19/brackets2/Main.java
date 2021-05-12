package com.algospot.chapter19.brackets2;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String str;
    private static Stack<Character> stack;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        inputData();
        System.out.println(solve());
    }

    private static void inputData() {
        str = scanner.next();
        stack = new Stack<>();
    }

    private static String solve() {
        int index = 0;

        while(true){
            char ch = str.charAt(index++);

            if(isOpen(ch)){
                stack.push(ch);
            }else{
                if(!isPair(ch)){
                    return "NO";
                }

                stack.pop();
            }

            if(index == str.length())
                break;
        }


        return stack.isEmpty() ? "YES" : "NO";
    }

    private static boolean isOpen(char ch) {
        if(ch == '(' || ch == '{' || ch == '[')
            return true;

        return false;
    }

    private static boolean isPair(char ch) {
        if(stack.isEmpty())
            return false;
        else if(ch == '}' && stack.peek() == '{')
            return true;
        else if(ch == ')' && stack.peek() == '(')
            return true;
        else if(ch == ']' && stack.peek() == '[')
            return true;

        return false;
    }


    public static void main(String[] args) {
        run();
    }
}
