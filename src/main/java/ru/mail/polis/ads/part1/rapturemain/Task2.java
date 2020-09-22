package ru.mail.polis.ads.part1.rapturemain;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

// Recursive solution is not optimal - stackoverflow error possible
public class Task2 {
    public static void collect(String str, ArrayList<ArrayList<Character>> chars) {
        LinkedList<Integer> foundOperands = new LinkedList<>();
        int depth = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            // Provide confidence, that we have bucket in chars list.
            while (chars.size() <= depth)
                chars.add(new ArrayList<>());
            chars.get(depth).add(str.charAt(i));
            // If we found an operator we need to provide 2 operands to it
            if (Character.isUpperCase(str.charAt(i))) {
                foundOperands.add(0);
                depth++;
            } else {
                foundOperands.set(foundOperands.size() - 1, foundOperands.getLast() + 1);
                // If we found 2 operands for one operator, add operand to next and be sure it needs more
                while (foundOperands.size() > 0 && foundOperands.getLast() == 2) {
                    foundOperands.removeLast();
                    if (foundOperands.size() > 0)
                        foundOperands.set(foundOperands.size() - 1, foundOperands.getLast() + 1);;
                    depth--;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            ArrayList<ArrayList<Character>> arr = new ArrayList<>();
            collect(str, arr);
            for (int j = arr.size() - 1; j >= 0; j--)
                for (int k = 0; k < arr.get(j).size(); k++)
                    bw.write(arr.get(j).get(k));
            bw.newLine();
        }
        bw.close();
    }
}
