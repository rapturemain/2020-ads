package ru.mail.polis.ads.part4.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Task4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stt = new StringTokenizer(br.readLine());
        // Why should i even check correctness of the input data????
        // empty strings at the beginning of the input????????????????????????????????????
        while (!stt.hasMoreTokens()) {
            stt = new StringTokenizer(br.readLine());
        }
        int n = Integer.parseInt(stt.nextToken());
        int[] stairs = new int[n + 2];
        stairs[0] = 0;
        stairs[n + 1] = 0;
        if (n > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 1; i < n + 1; i++) {
                if (!st.hasMoreTokens()) {
                    st = new StringTokenizer(br.readLine());
                }
                stairs[i] = Integer.parseInt(st.nextToken());
            }
        }
        int k = Integer.parseInt(br.readLine());
        int[] sums = new int[n + 2];
        sums[0] = 0;
        sums[n + 1] = 0;
        for (int i = 1; i < n + 2; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i - 1; j >= 0; j--) {
                if (i - j > k) {
                    break;
                }
                if (sums[j] > max) {
                    max = sums[j];
                }
            }
            sums[i] = max + stairs[i];
        }
        System.out.println(sums[n + 1]);
    }
}
