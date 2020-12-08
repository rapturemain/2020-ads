package ru.mail.polis.ads.part9.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Task3 {
    private static final int MAX_LEN = 30000;
    private static BufferedReader br;
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int v = next();
        int e = next();

        int[][] edges = new int[e][3];
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = next();
            int to = next();
            int weight = next();
            edges[i][0] = from - 1;
            edges[i][1] = to - 1;
            edges[i][2] = weight;
        }
        int[] d = new int[v];
        Arrays.fill(d, MAX_LEN);
        d[0] = 0;
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < e; j++) {
                if (d[edges[j][0]] != MAX_LEN) {
                    d[edges[j][1]] = Math.min(d[edges[j][1]], d[edges[j][0]] + edges[j][2]);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v; i++) {
            sb.append(d[i]);
            sb.append(' ');
        }
        System.out.println(sb.toString());
    }

    private static int next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return Integer.parseInt(st.nextToken());
    }
}
