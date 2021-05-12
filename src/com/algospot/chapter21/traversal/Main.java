package com.algospot.chapter21.traversal;

import java.util.*;

class Node{
    int value;
    Node left;
    Node right;
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static int n;
    private static int[] prefix;
    private static int[] infix;

    private static void run() {
        int numberOfTestCase = scanner.nextInt();

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() {
        inputData();
        Node root = makeTree();
        postfix(root);
        System.out.println();
    }

    private static void inputData() {
        n = scanner.nextInt();
        prefix = new int[n];
        infix = new int[n];

        for(int i=0; i<n; i++)
            prefix[i] = scanner.nextInt();

        for(int i=0; i<n; i++)
            infix[i] = scanner.nextInt();
    }

    private static Node makeTree() {
        Node root = new Node();
        root.value = prefix[0];

        for(int i=1; i<n; i++){
            Node parent = parentNode(root, indexOf(prefix[i]));
            int pos = position(root, indexOf(prefix[i]));

            Node node = new Node();
            node.value = prefix[i];

            if(pos == -1)
                parent.left = node;
            else if(pos == 1)
                parent.right = node;
        }

        return root;
    }

    private static Node parentNode(Node node, int index){
        int nodeIndex = indexOf(node.value);

        if(nodeIndex > index)
            return node.left == null ? node : parentNode(node.left, index);

        return node.right == null ? node : parentNode(node.right, index);
    }

    private static int position(Node node, int index){
        int nodeIndex = indexOf(node.value);

        if(nodeIndex > index)
            return node.left == null ? -1 : position(node.left, index);

        return node.right == null ? 1 : position(node.right, index);
    }

    private static int indexOf(int value) {
        for(int i=0; i<n; i++)
            if(infix[i] == value)
                return i;

        throw new AssertionError();
    }

    private static void postfix(Node node){
        if(node == null)
            return;

        postfix(node.left);
        postfix(node.right);
        System.out.print(node.value + " ");
    }

    public static void main(String[] args) {
        run();
    }
}
