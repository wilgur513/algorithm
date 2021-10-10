package com.boj.unsolved.divideconquer.p2099;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Matrix{
    private int size;
    private boolean[][] matrix;

    public Matrix(int size) {
        this.size = size;
        matrix = new boolean[size][size];
    }

    public void setValue(int row, int col, boolean value){
        matrix[row][col] = value;
    }

    public boolean getValue(int row, int col){
        return matrix[row][col];
    }

    public Matrix power(long arg){
        if(arg == 1)
            return this;

        Matrix result = power(arg / 2);
        return arg % 2 == 1 ? result.multiply(result).multiply(this) : result.multiply(result);
    }

    private Matrix multiply(Matrix arg){
        Matrix result = new Matrix(size);

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                for(int i = 0; i < size; i++) {
                    result.matrix[row][col] |= (matrix[row][i] && arg.matrix[i][col]);
                }
            }
        }

        return result;
    }
}

public class Main {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static Matrix matrix;
    static int n, k, m;

    public static void main(String[] args) throws IOException {
        inputData();
        solve();
    }

    private static void inputData() throws IOException {
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());

        n = Integer.valueOf(tokenizer.nextToken());
        k = Integer.valueOf(tokenizer.nextToken());
        m = Integer.valueOf(tokenizer.nextToken());
        matrix = new Matrix(n);

        for(int i = 0; i < n; i++){
            tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.valueOf(tokenizer.nextToken()) - 1;
            int b = Integer.valueOf(tokenizer.nextToken()) - 1;

            matrix.setValue(i, a, true);
            matrix.setValue(i, b, true);
        }
    }

    private static void solve() throws IOException {
        matrix = matrix.power(k);

        for(int i = 0; i < m; i++){
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int a = Integer.valueOf(tokenizer.nextToken()) - 1;
            int b = Integer.valueOf(tokenizer.nextToken()) - 1;
            System.out.println(matrix.getValue(a, b) ? "death" : "life");
        }
    }
}
