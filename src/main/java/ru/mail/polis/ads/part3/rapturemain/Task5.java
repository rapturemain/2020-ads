package ru.mail.polis.ads.part3.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Task5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split(" ");
        int n = Integer.parseInt(parts[0]), cowCount = Integer.parseInt(parts[1]);
        int[] pos = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            pos[i] = Integer.parseInt(st.nextToken());
        }
        int left = 0;
        int right = pos[n - 1] - pos[0];
        while (right - 1 > left) {
            int minDistance = (right + left) / 2;
            if (check(pos, minDistance, cowCount)) {
                left = minDistance;
            } else {
                right = minDistance;
            }
        }
        System.out.println(left);
    }

    public static boolean check(int[] pos, int minDistance, int cowCount) {
        int collectedDistance = 0;
        cowCount--;
        for (int i = 1; i < pos.length; i++) {
            collectedDistance += pos[i] - pos[i - 1];
            if (collectedDistance >= minDistance) {
                collectedDistance = 0;
                cowCount--;
            }
            if (cowCount == 0) {
                return true;
            }
        }
        return false;
    }
}
