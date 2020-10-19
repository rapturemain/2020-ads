package ru.mail.polis.ads.part3.rapturemain;

import java.io.*;
import java.util.StringTokenizer;

public class Task4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String[] parts = br.readLine().split(" ");
        int n = Integer.parseInt(parts[0]);
        int q = Integer.parseInt(parts[1]);
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < q; i++) {
            bw.write(binarySearch(array, Integer.parseInt(br.readLine())) ? "YES" : "NO");
            bw.newLine();
        }
        bw.close();
    }

    public static boolean binarySearch(int[] array, int value) {
        int left = 0;
        int right = array.length - 1;
        int index = (left + right) / 2;
        while (left < right) {
            if (array[index] == value)
                break;
            if (array[index] < value) {
                left = index + 1;
            } else {
                right = index - 1;
            }
            index = (left + right) / 2;
        }
        return array[index] == value;
    }
}
