package com.algospot.chapter7.fence;

import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private int n;
    private int[] fences = new int[20000];
    private int result;

    public void run(){
        int c = scanner.nextInt();

        for(int i=0; i<c; i++)
            runTestCase();
    }

    private void runTestCase() {
        dataInit();
        solve(0, n - 1);

        System.out.println(result);
    }

    private void dataInit() {
        n = scanner.nextInt();
        result = -1;

        for(int i=0; i<n; i++)
            fences[i] = scanner.nextInt();
    }

    private void solve(int start, int end) {
        if(start == end){
            result = Math.max(result, fences[start]);
            return;
        }

        int mid = (start + end) / 2;

        solve(start, mid);
        solve(mid + 1, end);
        merge(start, mid, end);
    }

    private void merge(int start, int mid, int end){
        int left = 0, right = 1;
        int h = Math.min(fences[mid - left], fences[mid + right]);
        int w = 2;

        result = Math.max(result, h*w);

        left++;
        right++;

        while(mid + right <= end && mid - left >= start){
            if(fences[mid + right] < fences[mid - left]) {
                right--;
                h = Math.min(fences[mid - left], h);
            }
            else {
                left--;
                h = Math.min(fences[mid + right], h);
            }

            left++;
            right++;
            w++;
            result = Math.max(result, h*w);
        }

        if(mid + right > end){
            for(int i = mid-left; i>=start; i--){
                h = Math.min(fences[i], h);
                w++;
                result = Math.max(result, h*w);
            }
        }else {
            for(int i = mid+right; i <= end; i++){
                h = Math.min(fences[i], h);
                w++;
                result = Math.max(result, h*w);
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
