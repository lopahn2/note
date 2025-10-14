package org.example.cthd.basic;

import java.util.*;

public class Tree {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 한 줄 전체를 입력받아 공백 기준으로 split
        String[] input = sc.nextLine().split("\\s+");
        int[] nodes = Arrays.stream(input)
                .mapToInt(Integer::parseInt)
                .toArray();

        System.out.println(preorder(nodes, 0).trim());
        System.out.println(inOrder(nodes, 0).trim());
        System.out.println(postOrder(nodes, 0).trim());
    }

    public static String preorder(int[] nodes, int idx) {
        if (idx >= nodes.length) return "";

        return nodes[idx] + " " + preorder(nodes,idx * 2 + 1)  + preorder(nodes, idx * 2 + 2);
    }

    public static String inOrder(int[] nodes, int idx) {
        if (idx >= nodes.length) return "";

        return inOrder(nodes,idx * 2 + 1) + nodes[idx] + " " + inOrder(nodes, idx * 2 + 2);
    }

    public static String postOrder(int[] nodes, int idx) {
        if (idx >= nodes.length) return "";

        return postOrder(nodes,idx * 2 + 1) + postOrder(nodes, idx * 2 + 2) + nodes[idx] + " ";
    }
}
