package org.example.day_05.p1722;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


public class Main {
    static int N;
    static int type;
    static long k;
    static long[] fac;
    static List<Integer> target;
    static StringTokenizer st;
    static BufferedWriter bw;
    static BufferedReader br;

    public static void main(String[] args) throws Exception {
        br = new BufferedReader(new InputStreamReader(System.in));
        bw = new BufferedWriter(new OutputStreamWriter(System.out));

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        fac = new long[N + 1];

        fac[0] = 1;
        for (int i = 1; i < N + 1; i++) {
            fac[i] = fac[i - 1] * i;
        }

        st = new StringTokenizer(br.readLine());
        type = Integer.parseInt(st.nextToken());

        if (type == 1) {
            k = Long.parseLong(st.nextToken());
            boolean[] visited = new boolean[N + 1];
            List<Integer> ans = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                for (int j = 1; j < N + 1; j++) {
                    if (visited[j]) continue;


                    if (k > fac[N - i - 1]) {
                        k -= fac[N - i - 1];
                    } else {
                        ans.add(j);
                        visited[j] = true;
                        break;
                    }
                }
            }

            String answer = ans.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(" "));
            bw.write(answer);
            bw.flush();

        } else {
            int[] arr = new int[N];
            boolean[] visited = new boolean[N + 1];
            for (int i = 0; i < N; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            long answer = 0;
            for (int i = 0; i < N; i++) {
                int count = 0;
                for (int j = 1; j < arr[i]; j++) {
                    if (!visited[j]) count++;
                }
                answer += (long)count * fac[N - i - 1];
                visited[arr[i]] = true;
            }

            bw.write(String.valueOf(answer + 1));
            bw.flush();

        }
    }


}
