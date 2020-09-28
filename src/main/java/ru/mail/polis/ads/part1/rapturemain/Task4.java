package ru.mail.polis.ads.part1.rapturemain;

import java.io.*;
import java.util.EmptyStackException;

public class Task4 {
    public static class Stack {
        public Stack(int size) {
            tail = new Bucket(size, null);
        }

        private Bucket tail;
        int total = 0;

        public void clear() {
            total = 0;
            tail = new Bucket(tail.getBucketSize(), null);
        }

        public void push(int n) {
            total++;
            if (tail.isFull()) {
                tail = new Bucket(tail.getBucketSize(), tail);
            }
            tail.push(n);
        }

        public int pop() throws EmptyStackException {
            if (tail.isEmpty()) {
                throw new EmptyStackException();
            }
            int val = tail.pop();
            if (tail.isEmpty() && tail.previous() != null) {
                tail = tail.previous();
            }
            total--;
            return val;
        }

        public int back() throws EmptyStackException {
            if (tail.isEmpty()) {
                throw new EmptyStackException();
            }
            return tail.back();
        }

        public int size() {
            return total;
        }

        private class Bucket {
            Bucket(int size, Bucket previous) {
                arr = new int[size];
                prev = previous;
            }

            Bucket prev;
            int size = -1;
            int[] arr;

            Bucket previous() {
                return prev;
            }

            boolean isFull() {
                return arr.length == size + 1;
            }

            boolean isEmpty() {
                return size < 0;
            }

            int getBucketSize() {
                return arr.length;
            }

            int pop() {
                return arr[size--];
            }

            void push(int n) {
                arr[++size] = n;
            }

            int back() {
                return arr[size];
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String str;
        Stack stack = new Stack(5);
        while ((str = br.readLine()).charAt(1) != 'x') {
            try {
                if (str.charAt(1) == 'u') {
                    int n = Integer.parseInt(str.substring(5));
                    stack.push(n);
                    bw.write("ok\n");
                    continue;
                }
                if (str.charAt(1) == 'o') {
                    bw.write(Integer.toString(stack.pop()));
                    bw.newLine();
                    continue;
                }
                if (str.charAt(1) == 'a') {
                    bw.write(Integer.toString(stack.back()));
                    bw.newLine();
                    continue;
                }
                if (str.charAt(1) == 'i') {
                    bw.write(Integer.toString(stack.size()));
                    bw.newLine();
                    continue;
                }
                if (str.charAt(1) == 'l') {
                    stack.clear();
                    bw.write("ok\n");
                }
            } catch (EmptyStackException e) {
                bw.write("error\n");
            }
        }
        bw.write("bye");
        bw.close();
    }
}
