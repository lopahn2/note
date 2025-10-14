package org.example.day_02.problem1806;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Integer.parseInt(st.nextToken());
        long S = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            lst.add(Integer.parseInt(st.nextToken()));
        }

        bw.write(String.valueOf(tp(lst, S)));
        bw.flush();
        bw.close();
    }

    private static long tp(List<Integer> lst, long S) {
        int l = 0;
        int r = 1;
        long ts = lst.get(l);
        int result = Integer.MAX_VALUE;

        while (true) {
            if (l >= r) break;

            if (ts >= S) {
                result = Math.min(result, r - l);
                ts -= lst.get(l);
                l += 1;
            } else {
                if ( r  < lst.size()) {
                    r += 1;
                    ts += lst.get(r-1);
                } else {
                    break;
                }
            }
        }
        return result != Integer.MAX_VALUE ? result : 0;
    }

}
