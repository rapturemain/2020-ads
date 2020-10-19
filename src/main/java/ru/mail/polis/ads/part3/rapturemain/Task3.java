package ru.mail.polis.ads.part3.rapturemain;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Task3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        PriorityQueue<Integer> left = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> right = new PriorityQueue<>();
        int next;
        int median = Integer.MAX_VALUE;
        while (true) {
            try {
                next = Integer.parseInt(br.readLine());
            } catch (Exception e) {
                bw.close();
                br.close();
                return;
            }

            add(left, right, median, next);
            balance(left, right);
            median = computeMedian(left, right);
            bw.write(Integer.toString(median));
            bw.newLine();
        }
    }

    public static int computeMedian(PriorityQueue<Integer> left, PriorityQueue<Integer> right) {
        if (left.size() == 0) {
            return Integer.MAX_VALUE;
        } else if (left.size() > right.size()) {
            return left.peek();
        } else if (left.size() < right.size()) {
            return right.peek();
        } else {
            return (left.peek() + right.peek()) / 2;
        }
    }

    public static void balance(PriorityQueue<Integer> left, PriorityQueue<Integer> right) {
        if (left.size() + 1 < right.size()) {
            left.add(right.poll());
        } else if (right.size() + 1 < left.size()) {
            right.add(left.poll());
        }
    }

    public static void add(PriorityQueue<Integer> left, PriorityQueue<Integer> right, int median, int number) {
        if (number > median) {
            right.add(number);
        } else {
            left.add(number);
        }
    }
}
