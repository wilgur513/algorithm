package com.algospot.chapter22.insertion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class NodePair{
    Node left, right;

    public NodePair(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
}

class Node{
    int prior;
    int value;
    int size;
    Node left, right;

    public Node(int value) {
        this.value = value;
        prior = (int)(Math.random()*100000);
        size = 1;
    }

    public void setLeft(Node left){
        this.left = left;
        calSize();
    }

    public void setRight(Node right){
        this.right = right;
        calSize();
    }

    public static Node insert(Node root, Node node){
        if(root == null)
            return node;

        if(root.prior < node.prior){
            NodePair pair = split(root, node);
            node.setLeft(pair.left);
            node.setRight(pair.right);

            return node;
        }
        else if(node.value < root.value)
            root.setLeft(insert(root.left, node));
        else
            root.setRight(insert(root.right, node));

        return root;
    }

    private static NodePair split(Node root, Node node) {
        if(root == null)
            return new NodePair(null, null);

        if(root.value < node.value){
            NodePair pair = split(root.right, node);
            root.setRight(pair.left);

            return new NodePair(root, pair.right);
        }

        NodePair pair = split(root.left, node);
        root.setLeft(pair.right);

        return new NodePair(pair.left, root);
    }

    public static Node erase(Node root, Node node){
        if(root == null)
            return root;

        if(root.value == node.value){
            Node result = merge(root.left, root.right);
            return result;
        }

        if(node.value < root.value){
            root.setLeft(erase(root.left, node));
        }else
            root.setRight(erase(root.right, node));

        return root;
    }

    private static Node merge(Node a, Node b) {
        if(a == null) return b;
        if(b == null) return a;

        if(a.prior < b.prior){
            b.setLeft(merge(a, b.left));
            return b;
        }

        a.setRight(merge(a.right, b));
        return a;
    }

    private void calSize() {
        size = 1;
        if(left != null) size += left.size;
        if(right != null) size += right.size;
    }
}

public class Main {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static int n;
    private static int[] a;

    private static void run() throws IOException {
        int numberOfTestCase = Integer.parseInt(reader.readLine());

        for(int i=0; i<numberOfTestCase; i++)
            runSingleTestCase();
    }

    private static void runSingleTestCase() throws IOException {
        inputData();
        int[] result = solve();

        for(int value : result)
            System.out.print(value + " ");
        System.out.println();
    }

    private static void inputData() throws IOException {
        n = Integer.parseInt(reader.readLine());
        a = new int[n];
        StringTokenizer tokenizer =  new StringTokenizer(reader.readLine());

        for(int i=0; i<n; i++)
            a[i] = Integer.parseInt(tokenizer.nextToken());
    }

    private static int[] solve() {
        int[] result = new int[n];
        Node root = null;

        for(int i=1; i<=n; i++)
            root = Node.insert(root, new Node(i));

        for(int i=n-1; i>=0; i--){
            Node node = findByIndex(root, i + 1 - a[i]);
            root = Node.erase(root, node);
            result[i] = node.value;
        }


        return result;
    }

    private static Node findByIndex(Node root, int index) {
        int leftSize = 0;

        if(root.left != null)
            leftSize = root.left.size;

        if(leftSize >= index)
            return findByIndex(root.left, index);

        if(leftSize + 1 == index)
            return root;

        return findByIndex(root.right, index - leftSize - 1);
    }

    public static void main(String[] args) throws IOException {
        run();
    }
}
