package org.example.day_04.problme1644;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());

        List<Integer> primes = new ArrayList<>();
        primes.add(2);
        primes.add(3);
        primes.add(5);
        primes.add(7);
        primes.add(11);
        for (int target = 12; target <= N; target++) {
            boolean isPrime = true;
            for (int div = 2; div <= Math.sqrt(target); div++) {
                if (target % div == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                primes.add(target);
            }
        }

        int l = 0;
        int r = 0;
        int ans = 0;

        while (true) {
            if (l > r) break;
            int tsum = 0;
            if (l == r) tsum = primes.get(l);
            else {
                if (r >= primes.size()) break;
                for (int i = l; i < r + 1; i++) {
                    tsum += primes.get(i);
                }
            }

            if (tsum >= N) {
                if (tsum == N) ans += 1;
                l += 1;
            } else {
                r += 1;
            }
        }

        bw.write(String.valueOf(ans));
        bw.close();

    }
}
