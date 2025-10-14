package org.example.day_01.problem1920;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        List<Integer> nList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            nList.add(Integer.parseInt(st.nextToken()));
        }

        int M = Integer.parseInt(br.readLine());
        StringTokenizer st1 = new StringTokenizer(br.readLine());
        List<Integer> mList = new ArrayList<>();
        while (st1.hasMoreTokens()) {
            mList.add(Integer.parseInt(st1.nextToken()));
        }

        nList.sort(Comparator.naturalOrder());

        for (int i = 0; i < M; i++) {
            bw.write(bs(nList, mList.get(i)) ? String.valueOf(1) : String.valueOf(0));
            bw.newLine();
        }

        bw.flush();
        bw.close();
    }

    private static boolean bs(List<Integer> lst, int x) {
        int s = 0;
        int e = lst.size() - 1;

        while (true) {
            int m = (s + e) / 2;
            if (s > e) break;
            if (lst.get(m) == x) {
                return true;
            } else if (lst.get(m) < x) {
                s = m + 1;
            } else if (lst.get(m) > x) {
                e = m - 1;
            }
        }
        return false;
    }
}
