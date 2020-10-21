package ru.mail.polis.ads.part5.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Task3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        System.out.println(findLongestSublistLength(array));
    }

     public static int findLongestSublistLength(int[] array) {
        int[] lengths = new int[array.length];
        int greatestLength = -1;
        Arrays.fill(lengths, 0);
        for (int i = 0; i < array.length; i++) {
            int greatestIndex = i;
            for (int j = i - 1; j >= 0; j--) {
                if (array[j] != 0 && array[i] % array[j] == 0 && lengths[greatestIndex] < lengths[j]) {
                    greatestIndex = j;
                }
            }
            lengths[i] = lengths[greatestIndex] + 1;
            if (lengths[i] > greatestLength) {
                greatestLength = lengths[i];
            }
        }
        return greatestLength;
     }
}
