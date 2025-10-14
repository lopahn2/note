package org.example.day_05.p11051;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[][] dp = new int[N+1][K+1];

        for (int n = 0; n <= N; n++) {
            for (int k = 0; k <= Math.min(n, K); k++) {
                if (k == 0 || k == n) dp[n][k] = 1;
                else dp[n][k] = (dp[n-1][k] + dp[n-1][k-1]) % 10007;
            }
        }



        bw.write(String.valueOf(dp[N][K] % 10007));
        bw.flush();

    }


}
