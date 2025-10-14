package org.example.day_05.p1010;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for (int t = 0; t < T; t++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            int[][] dp = new int[M + 1][N + 1];

            for (int m = 0; m < M + 1; m++) {
                for (int n = 0; n < Math.min(m, N) + 1; n++) {
                    if (n == 0 || n == m) dp[m][n] = 1;
                    else dp[m][n] = dp[m - 1][n - 1] + dp[m - 1][n];
                }
            }

            bw.write(String.valueOf(dp[M][N]));
            bw.newLine();
        }

        bw.flush();

    }
}
