package ru.mail.polis.ads.part5.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = br.readLine().split(" ");
        long w = Integer.parseInt(strings[0]);
        long h = Integer.parseInt(strings[1]);
        long n = Integer.parseInt(strings[2]);

        long left = Math.max(w, h);
        long right = left * n;

        while (left < right) {
            long mid = (left + right) / 2;
            long have = (mid / h) * (mid / w); // overflow without brackets?? that's sick
            if (have < n) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        System.out.println(left);
    }
}
