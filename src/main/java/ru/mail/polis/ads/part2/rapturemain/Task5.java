package ru.mail.polis.ads.part2.rapturemain;

import java.io.*;

public class Task5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        int[][] numbers = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] nums = br.readLine().split(" ");
            numbers[i][0] = Integer.parseInt(nums[0]);
            numbers[i][1] = Integer.parseInt(nums[1]);
        }
        mergeSort(numbers);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(numbers[i][0]).append(' ').append(numbers[i][1]).append('\n');
        }
        bw.write(sb.toString());
        bw.close();
    }

    public static void mergeSort(int[][] arr) {
        mergeSortInternal(arr, 0, arr.length - 1);
    }

    private static void mergeSortInternal(int[][] arr, int left, int right) {
        if (right != left) {
            if (right - left == 1) {
                if (arr[right][0] < arr[left][0]) {
                    int temp = arr[right][0];
                    arr[right][0] = arr[left][0];
                    arr[left][0] = temp;
                    temp = arr[right][1];
                    arr[right][1] = arr[left][1];
                    arr[left][1] = temp;

                }
            } else {
                int middle = left + (right - left) / 2;
                int l = left;
                int r = middle + 1;
                mergeSortInternal(arr, left, middle);
                mergeSortInternal(arr, r, right);
                if (arr[middle][0] <= arr[r][0]) {
                    return;
                }
                int[][] buffer = new int[right - left + 1][2];
                int c = 0;
                while (l <= middle && r <= right) {
                    if (arr[l][0] <= arr[r][0]) {
                        buffer[c][0] = arr[l][0];
                        buffer[c++][1] = arr[l++][1];
                    } else {
                        buffer[c][0] = arr[r][0];
                        buffer[c++][1] = arr[r++][1];
                    }
                }
                while (l <= middle) {
                    buffer[c][0] = arr[l][0];
                    buffer[c++][1] = arr[l++][1];
                }
                while (r <= right) {
                    buffer[c][0] = arr[r][0];
                    buffer[c++][1] = arr[r++][1];
                }
                System.arraycopy(buffer, 0, arr, left, right - left + 1);
            }
        }
    }
}