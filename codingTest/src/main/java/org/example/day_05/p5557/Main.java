package org.example.day_05.p5557;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;

    static int N;
    static int result;
    static List<Integer> ops = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        while (st.hasMoreTokens()) {
            ops.add(Integer.parseInt(st.nextToken()));
        }

        result = ops.get(ops.size() - 1);

        long[][] dp = new long[N][21];

        dp[0][ops.get(0)] = 1;

        for (int i = 1; i < N - 1; i++) {
            for (int j = 0; j < 20 + 1; j++) {
                if (dp[i-1][j] > 0) {
                    if (j + ops.get(i) <= 20) {
                        dp[i][j + ops.get(i)] += dp[i - 1][j];
                    }

                    if (j - ops.get(i) >= 0) {
                        dp[i][j - ops.get(i)] += dp[i - 1][j];
                    }

                }
            }
        }

        bw.write(String.valueOf(dp[N - 2][result]));
        bw.flush();
        bw.close();
    }


}
