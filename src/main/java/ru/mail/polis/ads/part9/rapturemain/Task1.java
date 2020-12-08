package ru.mail.polis.ads.part9.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Task1 {
    private static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;
    private static int stackEndPointer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        ArrayList<Integer>[] edges = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            edges[i] = new ArrayList<>();
        }
        int[] state = new int[v];
        int[] stack = new int[v];
        stackEndPointer = v - 1;
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            edges[from - 1].add(to - 1);
        }

        for (int i = 0; i < v; i++) {
            if (!goDeeper(edges, state, stack, i)) {
                System.out.println("-1");
                return;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v; i++) {
            sb.append(stack[i] + 1);
            sb.append(' ');
        }
        System.out.println(sb.toString());
    }

    private static boolean goDeeper(ArrayList<Integer>[] edges, int[] state, int[] stack, int index) {
        if (state[index] == GRAY) {
            return false;
        }
        if (state[index] == WHITE) {
            state[index] = GRAY;
            for (int i = 0; i < edges[index].size(); i++) {
                if (!goDeeper(edges, state, stack, edges[index].get(i))) {
                    return false;
                }
            }
            state[index] = BLACK;
            stack[stackEndPointer--] = index;
        }
        return true;
    }
}
