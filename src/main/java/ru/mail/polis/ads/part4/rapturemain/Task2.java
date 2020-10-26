package ru.mail.polis.ads.part4.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        int[][] seeds = new int[n][m];
        for (int i = n - 1; i >= 0; i--) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                seeds[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = n - 2; i >= 0; i--) {
            seeds[i][m - 1] += seeds[i + 1][m - 1];
        }
        for (int i = m - 2; i >= 0; i--) {
            seeds[n - 1][i] += seeds[n - 1][i + 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = m - 2; j >= 0; j--) {
                seeds[i][j] += Math.max(seeds[i + 1][j], seeds[i][j + 1]);
            }
        }

        int i = 0;
        int j = 0;
        StringBuilder sb = new StringBuilder();
        while (i < n - 1 && j < m - 1) {
            if (seeds[i][j + 1] > seeds[i + 1][j]) {
                sb.append('R');
                j++;
            } else {
                sb.append('F');
                i++;
            }
        }
        for (; i < n - 1; i++) {
            sb.append('F');
        }
        for(; j < m - 1; j++) {
            sb.append('R');
        }
        System.out.println(sb.toString());
    }
}
