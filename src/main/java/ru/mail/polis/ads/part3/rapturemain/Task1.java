package ru.mail.polis.ads.part3.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        String[] parts = br.readLine().split(" ");
        int[] numbers = new int[parts.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(parts[i]);
        }
        for (int i = numbers.length - 1; i > 0; i--) {
            if (numbers[(i - 1) / 2] > numbers[i]) {
                System.out.println("NO");
                return;
            }
        }
        System.out.println("YES");
    }
}
