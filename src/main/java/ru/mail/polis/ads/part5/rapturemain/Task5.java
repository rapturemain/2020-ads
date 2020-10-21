package ru.mail.polis.ads.part5.rapturemain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Task5 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(br.readLine());
        ArrayList<Integer> arrayList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            arrayList.add(i + 1);
        }

        print(arrayList);
        int factCount = fact(n);
        for (int i = 0; i < factCount - 1; i++) {
            next(arrayList);
            print(arrayList);
        }

        bw.close();
    }

    public static BufferedWriter bw; // for secondary use in print() function

    public static void print(List<Integer> list) throws IOException {
        for (int i = 0; i < list.size(); i++) {
            bw.write(Integer.toString(list.get(i)));
            bw.write(" ");
        }
        bw.newLine();
    }

    public static int fact(int val) {
        if (val == 1) {
            return 1;
        }
        return val * fact(val - 1);
    }

    public static void next(List<Integer> integers) {
        int i = integers.size() - 2;
        while (i >= 0 && integers.get(i) > integers.get(i + 1)) // find tail
            i--;
        if (i == -1)
            return;
        int toSwap = i;
        int toSwapVal = integers.get(i);
        i++;
        while (i < integers.size() && integers.get(i) > toSwapVal) // find least greater number
            i++;
        i--;
        integers.set(toSwap, integers.get(i));
        integers.set(i, toSwapVal);
        toSwap++;
        i = integers.size() - 1;
        while (toSwap < i) { // rotate tail
            toSwapVal = integers.get(toSwap);
            integers.set(toSwap, integers.get(i));
            integers.set(i, toSwapVal);
            toSwap++;
            i--;
        }
    }
}
