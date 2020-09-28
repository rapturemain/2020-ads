package ru.mail.polis.ads.part1.rapturemain;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;

public class Task2 {
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

        public void incLast() {
            if (tail.isEmpty()) {
                throw new EmptyStackException();
            }
            tail.incLast();
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

            void incLast() {
                arr[size]++;
            }

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

    public static class Queue {
        public Queue(int size) {
            head = new Bucket(size, null);
            tail = head;
        }

        private Bucket head;
        private Bucket tail;
        int total = 0;

        public void push(int n) {
            tail.push(n);
            total++;
            if (tail.isFull()) {
                Bucket buffer = new Bucket(tail.getBucketSize(), null);
                tail.setNext(buffer);
                tail = buffer;
            }
        }

        public int pop() {
            int result = head.pop();
            total--;
            if (head.isEnded()) {
                head = head.getNext();
            }
            return result;
        }

        public int front() {
            return head.front();
        }

        public int size() {
            return total;
        }

        public void clear() {
            head = new Bucket(head.getBucketSize(), null);
            tail = head;
            total = 0;
        }

        private static class Bucket {
            Bucket(int size, Bucket next) {
                arr = new int[size];
                this.next = next;
            }

            Bucket next;
            int index = 0;
            int size = -1;
            int[] arr;

            int getBucketSize() {
                return arr.length;
            }

            void setNext(Bucket next) {
                this.next = next;
            }

            Bucket getNext() {
                return next;
            }

            boolean isEnded() {
                return index == arr.length;
            }

            boolean isFull() {
                return size + 1 == arr.length;
            }

            void push(int n) {
                arr[++size] = n;
            }

            int pop() {
                return arr[index++];
            }

            int front() {
                return arr[index];
            }
        }
    }

    public static ArrayList<Queue> collect(String str) {
        Stack foundOperands = new Stack(10);
        ArrayList<Queue> chars = new ArrayList<>();
        int depth = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            // Provide confidence, that we have bucket in chars list.
            while (chars.size() <= depth)
                chars.add(new Queue(10));
            chars.get(depth).push(str.charAt(i));
            // If we found an operator we need to provide 2 operands to it
            if (Character.isUpperCase(str.charAt(i))) {
                foundOperands.push(0);
                depth++;
            } else {
                foundOperands.incLast();
                // If we found 2 operands for one operator, add operand to next and be sure it needs more
                while (foundOperands.size() > 0 && foundOperands.back() == 2) {
                    foundOperands.pop();
                    if (foundOperands.size() > 0)
                        foundOperands.incLast();
                    depth--;
                }
            }
        }
        return chars;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            ArrayList<Queue> arr = collect(str);
            int s = arr.size() - 1;
            for (int j = s; j >= 0; j--) {
                int sJ = arr.get(j).size();
                for (int k = 0; k < sJ; k++)
                    bw.write((char) arr.get(j).pop());
            }
            bw.newLine();
        }
        bw.close();
    }
}
