package ru.mail.polis.ads.part1.rapturemain;

import java.io.*;

public class Task5 {
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

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        String str;
        Queue queue = new Queue(5);
        while ((str = br.readLine()).charAt(1) != 'x') {
            if (str.charAt(1) == 'u') {
                int n = Integer.parseInt(str.substring(5));
                queue.push(n);
                bw.write("ok\n");
                continue;
            }
            if (str.charAt(1) == 'o') {
                bw.write(Integer.toString(queue.pop()));
                bw.newLine();
                continue;
            }
            if (str.charAt(1) == 'r') {
                bw.write(Integer.toString(queue.front()));
                bw.newLine();
                continue;
            }
            if (str.charAt(1) == 'i') {
                bw.write(Integer.toString(queue.size()));
                bw.newLine();
                continue;
            }
            if (str.charAt(1) == 'l') {
                queue.clear();
                bw.write("ok\n");
            }
        }
        bw.write("bye");
        bw.close();
    }
}
