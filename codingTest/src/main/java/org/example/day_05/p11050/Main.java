package org.example.day_05.p11050;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int ans = (fac(N) / (fac(K) * fac(N-K)));

        bw.write(String.valueOf(ans));
        bw.flush();

    }

    private static int fac(int n) {
        int r = 1;
        for (int i = 1; i < n + 1; i++) {
            r *= i;
        }

        return r;
    }
}
