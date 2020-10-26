package ru.mail.polis.ads.part4.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        int[][][] d = new int[s.length()][s.length()][5]; // cost | parent1 i | parent1 j | parent2 i | parent2 j

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < s.length(); j++) {
                if (i == j) {
                    d[i][j][0] = 1;
                    d[i][j][1] = i;
                    d[i][j][2] = j;
                    d[i][j][3] = -1;
                } else if (i < j) {
                    d[i][j][0] = Integer.MAX_VALUE;
                }
            }
        }

        for (int j = 1; j < s.length(); j++) {
            for (int i = j - 1; i >= 0; i--) {
                if (isPair(s, i, j)) {
                    d[i][j][0] = d[i + 1][j - 1][0];
                    d[i][j][1] = i + 1;
                    d[i][j][2] = j - 1;
                    d[i][j][3] = i + 1;
                    d[i][j][4] = j - 1;
                }
                countMin(d, i, j);
            }
        }

        System.out.println(collect(s, d));
    }

    public static void countMin(int[][][] d, int ii, int jj) {
        int j = ii + 1;
        for (int i = ii; i < jj; i++) {
            int sum = Math.abs(d[ii][i][0] + d[j][jj][0]);
            if (sum < d[ii][jj][0]) {
                d[ii][jj][0] = sum;
                d[ii][jj][1] = ii;
                d[ii][jj][2] = i;
                d[ii][jj][3] = j;
                d[ii][jj][4] = jj;
            }
            j++;
        }
    }

    private static String collect(String s, int[][][] d) {
        if (s.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        collectRecursive(sb, s, d, 0, s.length() - 1);
        return sb.toString();
    }

    private static void collectRecursive(StringBuilder sb, String s, int[][][] d, int start, int end) {
        if (start > end || start < 0) {
            return;
        }
        int[] current = d[start][end];
        if (current[1] == current[3] && current[2] == current[4]) { // if [] or ()
            sb.append(s.charAt(start));
            if (current[1] == current[2]) {
                collectSingle(sb, s, current[1]);
            } else {
                collectRecursive(sb, s, d, current[1], current[2]);
            }
            sb.append(s.charAt(end));
        } else {
            if (current[1] == current[2]) {
                collectSingle(sb, s, current[1]);
            } else {
                collectRecursive(sb, s, d, current[1], current[2]);
            }
            if (current[3] == current[4]) {
                collectSingle(sb, s, current[3]);
            } else {
                collectRecursive(sb, s, d, current[3], current[4]);
            }
        }
    }

    private static void collectSingle(StringBuilder sb, String s, int index) {
        char c = s.charAt(index);
        if (c == '(' || c == '[') {
            sb.append(c);
            sb.append(getPairRight(c));
        } else {
            sb.append(getPairWrong(c));
            sb.append(c);
        }
    }

    private static char getPairRight(char c) {
        if (c == '(')
            return ')';
        else
            return ']';
    }

    private static char getPairWrong(char c) {
        if (c == ')')
            return '(';
        else
            return '[';
    }

    public static boolean isPair(String s, int i, int j) {
        return s.charAt(i) == '(' && s.charAt(j) == ')' || s.charAt(i) == '[' && s.charAt(j) == ']';
    }
}
