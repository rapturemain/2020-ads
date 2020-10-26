package ru.mail.polis.ads.part4.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Task5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        System.out.println(mergeSort(arr));
    }

    public static int mergeSort(int[] arr) {
        if (arr.length < 2)
            return 0;
        int m = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, m);
        int[] right = Arrays.copyOfRange(arr, m, arr.length);
        return mergeSort(left) + mergeSort(right) + merge(arr, left, right);
    }

    public static int merge(int[] arr, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int counter = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[i + j] = left[i++];
            } else {
                arr[i + j] = right[j++];
                counter += left.length - i;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i++];
        }
        while (j < right.length) {
            arr[i + j] = right[j++];
        }
        return counter;
    }
}
