package ru.mail.polis.ads.part1.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        int bracketCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(')
                bracketCounter++;
            else {
                bracketCounter--;
                if (bracketCounter < 0)
                    break;
            }
        }
        System.out.println(bracketCounter == 0 ? "YES" : "NO");
    }
}
