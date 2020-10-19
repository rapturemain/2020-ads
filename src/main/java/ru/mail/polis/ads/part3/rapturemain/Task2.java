package ru.mail.polis.ads.part3.rapturemain;

import java.io.*;
import java.util.ArrayList;

public class Task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        Heap h = new Heap();
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            if (str.charAt(0) == '0') {
                String[] parts = str.split(" ");
                h.insert(Integer.parseInt(parts[1]));
            } else {
                bw.write(Integer.toString(h.extract()));
                bw.newLine();
            }
        }
        bw.close();
    }

    public static class Heap {
        public Heap() {
            array = new ArrayList<>();
        }

        private ArrayList<Integer> array;

        public void insert(int value) {
            array.add(value);
            swim(array.size() - 1);
        }

        public int extract() {
            int buffer = array.get(0);
            array.set(0, array.get(array.size() - 1));
            array.remove(array.size() - 1);
            sink(0);
            return buffer;
        }

        private void swim(int c) {
            int p = (c - 1) / 2;
            while(array.get(c) > array.get(p) && c > 0) {
                int buffer = array.get(c);
                array.set(c, array.get(p));
                array.set(p, buffer);
                c = (c - 1) / 2;
                p = (c - 1) / 2;
            }
        }

        private void sink(int p) {
            while(p * 2 + 1 < array.size()) {
                int c = p * 2 + 1;
                if (c + 1 < array.size() && array.get(c) < array.get(c + 1))
                    c++;
                if (array.get(c) <= array.get(p))
                    break;
                int buffer = array.get(p);
                array.set(p, array.get(c));
                array.set(c, buffer);
                p = c;
            }
        }
    }
}
