package org.example.day_05.p1256;

import java.io.*;
import java.util.*;

public class Main {
    static BufferedReader br;
    static BufferedWriter bw;
    static StringTokenizer st;

    static int N;
    static int M;
    static long K;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Long.parseLong(st.nextToken());

        long[][] dp = new long[202][202];

        for (int i = 0; i < 201; i++) {
            dp[i][1] = i; // iC1
            dp[i][0] = 1; // iC0
            dp[i][i] = 1; // iCi
        }

        for (int i = 1; i < 201; i++) {
            for (int j = 1; j < i + 1; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1];

                if (dp[i][j] > 1000000000) dp[i][j] = 1000000001;
            }
        }

        if (dp[N+M][M] < K) {
            bw.write("-1");
            bw.flush();
            return;
        }

        while(!(N==0&&M==0)) {
            if (dp[N+M-1][M] >= K) {
                bw.write("a");
                N--;
            } else {
                bw.write("z");
                K -= dp[N + M - 1][M];
                M--;
            }
        }

        bw.flush();
        bw.close();
    }
}
