package org.example.day_02.problem1072;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());

        long X = Long.parseLong(st.nextToken());
        long Y = Long.parseLong(st.nextToken());
        long Z = ((100 * Y) / X);

        bw.write(String.valueOf(bs(X, Y, Z)));
        bw.flush();
        bw.close();

    }

    private static long bs(long X, long Y, long Z) {
        long s = 0;
        long e = 1_000_000_000L;
        long result = Long.MAX_VALUE;

        while (true) {
            if (s > e) break;

            long m = (s + e) / 2;
            long t = (100 * (Y + m)) / (X + m);

            if (t > Z) {
                result = Math.min(result, m);
                e = m - 1;
            } else {
                s = m + 1;
            }
        }
        return result != Long.MAX_VALUE ? result : -1;
    }
}
