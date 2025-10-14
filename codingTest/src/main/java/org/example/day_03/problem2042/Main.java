package org.example.day_03.problem2042;

import java.util.*;
import java.io.*;

public class Main {
    static class SegmentTree {
        long[] arr;
        long[] tree;
        int n;

        public SegmentTree(long[] input) {
            this.arr = input.clone();
            this.n = input.length;
            this.tree = new long[this.n * 4];
            build(1, 0, n - 1);
        }

        private void build(int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(node * 2, start, mid);
                build(node * 2 + 1, mid + 1, end);
                tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }
        }

        public long query(int left, int right) {
            return query(1, 0, n - 1, left, right);
        }

        private long query(int node, int start, int end, int left, int right) {
            if (right < start || end < left) return 0;
            if (left <= start && end <= right) return tree[node];
            int mid = (start + end) / 2;
            return query(node * 2, start, mid, left, right) + query(node * 2 + 1, mid + 1, end, left, right);
        }

        public void update(int index, long newValue) {
            update(1, 0, n - 1, index, newValue);
        }

        private void update(int node, int start, int end, int index, long newValue) {
            if (index < start || index > end) return;
            if (start == end) {
                arr[index]  = newValue;
                tree[node] = newValue;
            } else {
                int mid = (start + end) / 2;
                update(node * 2, start, mid, index, newValue);
                update(node * 2 + 1, mid + 1, end, index, newValue);
                tree[node] = tree[node * 2] + tree[node * 2 + 1];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] arr = new long[N];

        for (int i = 0; i <N ; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        SegmentTree tree = new SegmentTree(arr);

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if (a == 1) {
                tree.update(b-1, c);
            } else {
                bw.write(String.valueOf(tree.query(b-1, (int) c-1)));
                bw.newLine();
            }
        }

        bw.flush();
        bw.close();
    }
}
