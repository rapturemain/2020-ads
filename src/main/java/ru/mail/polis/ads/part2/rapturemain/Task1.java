package ru.mail.polis.ads.part2.rapturemain;

import java.io.*;
import java.util.StringTokenizer;

public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }
        // Radix sort
        int[][] buckets = new int[4][n];
        int[] size = new int[4];
        int mask = 0x00000001;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < n; j++) {
                int digit;
                int shift;
                if (numbers[j] < 0) {
                    digit = ~numbers[j] & mask;
                    shift = 0;
                } else {
                    digit = numbers[j] & mask;
                    shift = 2;
                }
                int index = (digit == 0 ? 0 : 1) + shift;
                buckets[index][size[index]++] = numbers[j];
            }
            int c = 0;
            for (int j = 0; j < 4; j++) {
                System.arraycopy(buckets[j], 0, numbers, c, size[j]);
                c += size[j];
                size[j] = 0;
            }
            mask <<= 1;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(numbers[i]);
            sb.append(" ");
        }
        bw.write(sb.toString());
        bw.close();
    }
}