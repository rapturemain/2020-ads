package ru.mail.polis.ads.part5.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String pattern = br.readLine();
        String word = br.readLine();

        // ?? why should i even check this ??
        if (word.contains("?") || word.contains("*")) {
            String buffer = word;
            word = pattern;
            pattern = buffer;
        }

        boolean[][] good = new boolean[pattern.length() + 1][word.length() + 1];
        good[0][0] = true;
        /* Base:
           1 1 1 1 1 1 1 1 1 word
           1 0 0 0 0 0 0 0 0
           1 0 0 0 0 0 0 0 0
           1 0 0 0 0 0 0 0 0
           1 0 0 0 0 0 0 0 0
           1 0 0 0 0 0 0 0 0
           1 0 0 0 0 0 0 0 0
           pattern

           is redundant because of OR in case of '*' in the pattern
           - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
           Base:
           1 0 0 0 0 0 0 0 0 word
           0 0 0 0 0 0 0 0 0
           0 0 0 0 0 0 0 0 0
           0 0 0 0 0 0 0 0 0
           0 0 0 0 0 0 0 0 0
           0 0 0 0 0 0 0 0 0
           0 0 0 0 0 0 0 0 0
           pattern
         */
        for (int i = 1; i <= pattern.length(); i++) {
            char c = pattern.charAt(i - 1);
            for (int j = 1; j <= word.length(); j++) {
                int cw = word.charAt(j - 1);
                if (c == '?' || c == cw) {
                    good[i][j] = good[i - 1][j - 1];
                } else if (c == '*') {
                    good[i][j] = good[i - 1][j] ||  // don't take this character
                                 good[i][j - 1] ||  // take this (and look for more)
                                 good[i - 1][j - 1];// take only 1 character
                } else {
                    good[i][j] = false;
                }
            }
        }
        System.out.println(good[pattern.length()][word.length()] ? "YES" : "NO");
    }
}
