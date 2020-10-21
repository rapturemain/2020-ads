package ru.mail.polis.ads.part5.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double c = Double.parseDouble(br.readLine());
        double left = 0;
        double right = Math.sqrt(c);
        double mid;
        double error;
        do {
            mid = left + (right - left) / 2;
            error = mid * mid + Math.sqrt(mid) - c;
            if (error > 0) {
                right = mid;
            } else {
                left = mid;
            }
        } while (Math.abs(error) > 1e-7);
        System.out.println(String.format("%.6f", mid));
    }
}
