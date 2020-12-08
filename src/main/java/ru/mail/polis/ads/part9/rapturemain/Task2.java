package ru.mail.polis.ads.part9.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Task2 {
    private static final int WHITE = 0;
    private static final int GRAY = 1;
    private static final int BLACK = 2;

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
        boolean[] partOfCycle = new boolean[v];
        Stack stack = new Stack(v);
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            edges[from - 1].add(to - 1);
            edges[to - 1].add(from - 1);
        }

        for (int i = 0; i < v; i++) {
            goDeeper(edges, state, partOfCycle, i, stack);
        }
        for (int i = 0; i < v; i++) {
            if (partOfCycle[i]) {
                System.out.println("Yes\n" + (i + 1));
                return;
            }
        }
        System.out.println("No");
    }

    private static void goDeeper(ArrayList<Integer>[] edges, int[] state, boolean[] partOfCycle, int index, Stack stack) {
        if (state[index] == GRAY) {
            for (int i = stack.size() - 1; i >= 0; i--) {
                partOfCycle[stack.get(i)] = true;
                if (stack.get(i) == index) {
                    break;
                }
            }
        }
        if (state[index] == WHITE) {
            state[index] = GRAY;
            stack.put(index);
            for (int i = 0; i < edges[index].size(); i++) {
                if (stack.size() < 2 || edges[index].get(i) != stack.peek(1)) {
                    goDeeper(edges, state, partOfCycle, edges[index].get(i), stack);
                }
            }
            state[index] = BLACK;
            stack.pop();
        }
    }

    private static class Stack {
        int[] stack;
        int pointer = 0;

        Stack(int size) {
            this.stack = new int[size];
        }

        void put(int val) {
            stack[pointer++] = val;
        }

        int pop() {
            return stack[--pointer];
        }

        int peek() {
            return stack[pointer - 1];
        }

        int peek(int back) {
            return stack[pointer - back - 1];
        }

        int size() {
            return pointer;
        }

        int maxSize() {
            return stack.length;
        }

        int get(int index) {
            return stack[index];
        }
    }
}
