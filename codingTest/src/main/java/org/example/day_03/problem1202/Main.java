package org.example.day_03.problem1202;

import java.util.*;
import java.io.*;

public class Main {

    static class Jewel {
        long M;
        long V;

        public Jewel(long m, long v) {
            M = m;
            V = v;
        }
    }

    static class Bag {
        long C;

        public Bag(long c) {
            C = c;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        PriorityQueue<Jewel> jewels = new PriorityQueue<>((j1, j2) -> {
            return (int) (j1.M - j2.M);
        });
        List<Bag> bags = new ArrayList<>();

        while (N-- > 0) {
            st = new StringTokenizer(br.readLine());
            jewels.add(new Jewel(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken())));
        }

        while (K-- > 0) {
            st = new StringTokenizer(br.readLine());
            bags.add(new Bag(Long.parseLong(st.nextToken())));
        }

        bags.sort((b1, b2) -> (int) (b1.C - b2.C));

        PriorityQueue<Jewel> maxValue = new PriorityQueue<>((j1, j2) -> {
            return (int) (j2.V - j1.V);
        });

        long ans = 0;

        for (Bag bag : bags) {
            while (!jewels.isEmpty() && jewels.peek().M <= bag.C) {
                maxValue.add(jewels.poll());
            }

            if (!maxValue.isEmpty()) {
                ans += maxValue.poll().V;
            }
        }

        bw.write(String.valueOf(ans));
        bw.flush();
        bw.close();

    }
}
