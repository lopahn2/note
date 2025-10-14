package org.example.day_03.problem1991;

import java.util.*;
import java.io.*;

public class Main {
    static class Node {
        String name;
        String left;
        String right;

        public Node(String name, String left, String right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());

        HashMap<String, Node> tree = new HashMap<>();

        while (N-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String name = st.nextToken();
            String left = st.nextToken();
            String right = st.nextToken();

            Node node = new Node(name, !left.equals(".")  ? left : null, !right.equals(".") ? right : null);
            tree.put(name, node);
        }
        pre(tree, tree.get("A"), bw);
        bw.newLine();
        in(tree, tree.get("A"), bw);
        bw.newLine();
        post(tree, tree.get("A"), bw);
        bw.flush();
        bw.close();

    }

    private static void pre(HashMap<String, Node> tree, Node node, BufferedWriter bw) throws IOException {
        bw.write(node.name);
        if (node.left != null) pre(tree, tree.get(node.left), bw);
        if (node.right != null) pre(tree, tree.get(node.right), bw);
    }

    private static void in(HashMap<String, Node> tree, Node node, BufferedWriter bw) throws IOException {
        if (node.left != null) in(tree, tree.get(node.left), bw);
        bw.write(node.name);
        if (node.right != null) in(tree, tree.get(node.right), bw);
    }
    private static void post(HashMap<String, Node> tree, Node node, BufferedWriter bw) throws IOException {
        if (node.left != null) post(tree, tree.get(node.left), bw);
        if (node.right != null) post(tree, tree.get(node.right), bw);
        bw.write(node.name);
    }
}
