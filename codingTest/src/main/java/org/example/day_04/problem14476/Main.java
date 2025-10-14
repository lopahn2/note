package org.example.day_04.problem14476;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }


        int[] prefix = new int[N];
        int[] suffix = new int[N];

        prefix[0] = arr[0];
        for (int i = 1; i < N; i++) {
            prefix[i] = gcd(prefix[i - 1], arr[i]);
        }

        suffix[N - 1] = arr[N - 1];
        for (int i = N - 2; i >= 0; i--) {
            suffix[i] = gcd(suffix[i + 1], arr[i]);
        }


        int maxGCD = -1;
        int excluded = -1;

        for (int i = 0; i < N; i++) {
            int g;
            if (i == 0) {
                g = suffix[1];
            } else if (i == N - 1) {
                g = prefix[N - 2];
            } else {
                g = gcd(prefix[i - 1], suffix[i + 1]);
            }

            if (arr[i] % g != 0 && g > maxGCD) {
                maxGCD = g;
                excluded = arr[i];
            }
        }

        if (maxGCD == -1) {
            bw.write("-1\n");
        } else {
            bw.write(maxGCD + " " + excluded + "\n");
        }

        bw.flush();
        bw.close();
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
