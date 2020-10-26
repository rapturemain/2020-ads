package ru.mail.polis.ads.part4.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Task3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] first = new int[n];
        for (int i = 0; i < n; i++) {
            first[i] = Integer.parseInt(st.nextToken());
        }
        int m = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        int[] second = new int[m];
        for (int i = 0; i < m; i++) {
            second[i] = Integer.parseInt(st.nextToken());
        }
        int[][] d = new int[n + 1][m + 1];
        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                d[i][j] = first[i - 1] == second[j - 1] ? d[i - 1][j - 1] + 1 : Math.max(d[i - 1][j], d[i][j - 1]);
            }
        }
        System.out.println(d[n][m]);
    }
}
