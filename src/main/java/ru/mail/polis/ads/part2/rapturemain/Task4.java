package ru.mail.polis.ads.part2.rapturemain;

import java.io.*;
import java.util.Random;

public class Task4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int k = Integer.parseInt(br.readLine());
        String[] strings = br.readLine().split(" ");
        int n = strings.length;
        long[][] numbers = new long[n][2];
        for (int i = 0; i < n; i++) {
            String str = strings[i];
            if (str.length() > 32) {
                throw new IllegalArgumentException();
            }
            numbers[i] = parse(str);
        }
        long[] result = directPartition(numbers, n - k);
        bw.write(toString(result));
        bw.close();
    }

    public static long[] directPartition(long[][] arr, int k) {
        Random rng = new Random();
        int l = 0;
        int r = arr.length - 1;
        while (l < r) {
            if (r - l == 1) {
                if (compare(arr[l], arr[r]) > 0)
                    swap(arr, l, r);
                return arr[k];
            }
            int i = l;
            int j = r;
            int rIndex = l + rng.nextInt(r - l + 1);
            swap(arr, i, rIndex);
            long[] pivot = arr[i++];
            while (i < j) {
                while (compare(arr[i], pivot) < 0 && i < j)
                    i++;
                while (compare(arr[j], pivot) >= 0 && j > i)
                    j--;
                if (compare(arr[j], arr[i]) < 0)
                    swap(arr, i, j);
            }
            if (compare(arr[i], pivot) < 0)
                swap(arr, l, i);
            else
                swap(arr, l, i - 1);
            if (k >= i)
                l = i;
            else
                r = i - 1;
        }
        return arr[l];
    }

    public static void swap(long[][] arr, int first, int second) {
        long[] temp = arr[second];
        arr[second] = arr[first];
        arr[first] = temp;
    }

    /**
     * Decodes BCD representation array to string
     * @param val - representation to decode
     * @return decoded number
     */
    private static String toString(long[] val) {
        if (val[1] == 0)
            return toString(val[0], length(val[0]));
        else
            return toString(val[1], length(val[1])) + toString(val[0], 15);
    }

    /**
     * Finds last significand digit
     * @param val - BCD to search in
     * @return index of last significand digit
     */
    private static int length(long val) {
        long mask = 0b1111L << 60;
        int i = 15;
        for (; i >= 0; i--) {
            if ((val & mask) != 0)
                break;
            mask >>>= 4;
        }
        return i;
    }

    /**
     * Decodes digits of BCD starting from most significand (at index {@code len}) to least at index {@code 0}
     * @param val - BCD to decode
     * @param len - index of most significand
     * @return decoded BCD
     */
    private static String toString(long val, int len) {
        StringBuilder sb = new StringBuilder();
        long mask = 0b1111L << (len * 4);
        while (len >= 0) {
            sb.append((char) (((val & mask) >>> (len * 4)) + '0'));
            mask >>>= 4;
            len--;
        }
        return sb.toString();
    }

    /**
     * Parses number {@code str} to BCD representation stored in long
     * @param str - string to parse
     * @return long[] - BCD representation. Higher index - higher digits of number
     */
    private static long[] parse(String str) {
        long[] l = new long[2];
        if (str.length() < 16) {
            l[0] = parse(str, 0, str.length());
        } else {
            l[0] = parse(str, str.length() - 16, str.length());
            l[1] = parse(str, 0, str.length() - 16);
        }
        return l;
    }

    /**
     * Parses number {@code str} to BCD representation stored in long
     * starting from index {@code start} to index {@code end}
     * @param str - string to parse
     * @param start - start index
     * @param end - end index
     * @return BCD representation of segment stored in long
     */
    private static long parse(String str, int start, int end) {
        long result = 0;
        for (int i = start; i < end; i++) {
            result <<= 4;
            result += (str.charAt(i) - '0');
        }
        return result;
    }

    /**
     *
     * @param first first value to compare
     * @param second second value to compare
     * @return 1 if first greater, 0 if they are equal and -1 if second greater
     */
    public static int compare(long[] first, long[] second) {
        int compare1 = Long.compareUnsigned(first[1], second[1]);
        int compare0 = Long.compareUnsigned(first[0], second[0]);
        return compare1 != 0 ? compare1 : compare0;
    }
}