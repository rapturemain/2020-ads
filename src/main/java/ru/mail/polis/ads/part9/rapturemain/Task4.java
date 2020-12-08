package ru.mail.polis.ads.part9.rapturemain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task4 {
    private static BufferedReader br;
    private static StringTokenizer st;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine());
        int v = next();
        int e = next();
        int start = next() - 1;
        int end = next() - 1;

        ArrayList<Integer>[] edges = new ArrayList[v];
        ArrayList<Integer>[] edgesW = new ArrayList[v];
        ArrayList<Integer>[] paths = new ArrayList[v];
        int[] minCost = new int[v];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        for (int i = 0; i < v; i++) {
            edges[i] = new ArrayList<>();
            edgesW[i] = new ArrayList<>();
            paths[i] = new ArrayList<>();
        }
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = next();
            int to = next();
            int weight = next();
            edges[from - 1].add(to - 1);
            edges[to - 1].add(from - 1);
            edgesW[from - 1].add(weight);
            edgesW[to - 1].add(weight);
        }

        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(start, 0));

        while (pq.size() > 0) {
            Vertex current = pq.poll();
            int index = current.index;
            int cost = current.cost;
            for (int i = 0; i < edges[index].size(); i++) {
                if (minCost[edges[index].get(i)] < cost + edgesW[index].get(i)) {
                    continue;
                } else {
                    paths[edges[index].get(i)] = new ArrayList<>(paths[index]);
                    paths[edges[index].get(i)].add(index);
                    minCost[edges[index].get(i)] = cost + edgesW[index].get(i);
                }
                pq.add(new Vertex(edges[index].get(i), cost + edgesW[index].get(i)));
            }
        }
        if (paths[end].size() == 0) {
            System.out.println("-1");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(minCost[end]);
            sb.append('\n');
            for (int i = 0; i < paths[end].size(); i++) {
                sb.append(paths[end].get(i) + 1);
                sb.append(' ');
            }
            sb.append(end + 1);
            System.out.println(sb.toString());
        }
    }

    private static int next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(br.readLine());
        }
        return Integer.parseInt(st.nextToken());
    }

    private static class Vertex implements Comparable<Vertex> {
        int index;
        int cost;

        Vertex(int index, int cost) {
            this.index = index;
            this.cost = cost;
        }

        @Override
        public int compareTo(Vertex o) {
            return Integer.compare(cost, o.cost);
        }
    }
}
