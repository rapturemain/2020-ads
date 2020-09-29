package ru.mail.polis.ads.part1.rapturemain;

import java.io.*;
import java.util.ArrayList;

public class Task2 {
    public static ArrayList<StringBuilder> collect(String str) {
        int[] foundOperands = new int[str.length() / 2];
        ArrayList<StringBuilder> chars = new ArrayList<>(str.length() / 2);
        int index = -1;
        int i = str.length() - 1;
        for (; i >= 0; i--) {
            int depth = index + 1;
            char c = str.charAt(i);
            if (chars.size() <= depth)
                chars.add(new StringBuilder());
            chars.get(depth).append(c);
            if (c > 'Z') {
                OutOfBorders:
                {
                    while (foundOperands[index] == 1) {
                        foundOperands[index--] = 0;
                        if (index < 0) {
                            break OutOfBorders;
                        }
                    }
                    foundOperands[index] = 1;
                }
            } else {
                index++;
            }
        }
        return chars;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            ArrayList<StringBuilder> arr = collect(str);
            int s = arr.size() - 1;
            for (int j = s; j >= 0; j--) {
                StringBuilder sb = arr.get(j);
                char[] chars = new char[sb.length()];
                sb.getChars(0, sb.length(), chars, 0);
                bw.write(chars);
            }
            bw.newLine();
        }
        bw.close();
    }
}
