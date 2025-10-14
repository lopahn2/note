package org.example.day_03.problem2014;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int K = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        List<Long> lst = new ArrayList<>();

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < K; i++) {
            lst.add(Long.parseLong(st.nextToken()));
        }

        PriorityQueue<Long> pq = new PriorityQueue<>();
        Set<Long> set = new HashSet<>();
        pq.add(1L);
        set.add(1L);
        long val = 0;


        for (int i = 0; i <= N; i++) {
            val = pq.poll();

            for (Long prime : lst) {

                if (val > Long.MAX_VALUE / prime) break;

                long next = val * prime;
                pq.add(next);

                if (val % prime == 0) break;
            }
        }

        bw.write(String.valueOf(val));
        bw.flush();
        bw.close();
    }
}
