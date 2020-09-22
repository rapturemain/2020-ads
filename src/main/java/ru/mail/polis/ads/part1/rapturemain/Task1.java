package ru.mail.polis.ads.part1.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task1 {
    public static void main(String[] arg) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String buffer = br.readLine();
        System.out.println(buffer.charAt(0) + " " + buffer.charAt(1));
    }
}
