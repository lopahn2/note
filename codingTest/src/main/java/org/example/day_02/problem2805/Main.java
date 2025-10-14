package org.example.day_02.problem2805;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        StringTokenizer st1 = new StringTokenizer(br.readLine());
        List<Integer> trees = new ArrayList<>();

        while (N-- > 0) {
            trees.add(Integer.parseInt(st1.nextToken()));
        }

        trees.sort(Comparator.naturalOrder());

        bw.write(String.valueOf(bs(trees, M)));
        bw.flush();
        bw.close();

    }

    private static long bs(List<Integer> trees, long x) {
        long s = 0;
        long e = trees.get(trees.size() - 1);
        long result = Integer.MIN_VALUE;

        while (true) {
            if (s > e) break;
            long h = (s + e) / 2;

            long target = 0;
            for (int i = 0; i <trees.size() ; i++) {
                if (trees.get(i) > h) {
                    target += trees.get(i) - h;
                }
            }

            if (target >= x) {
                result = Math.max(result, h);
                s = h + 1;
            } else {
                e = h - 1;
            }
        }
        return result;
    }
}
