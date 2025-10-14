package org.example.day_01.problem3425;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        while (true) {
            List<String> cmds = new ArrayList<>();
            String line;
            while (!(line = br.readLine()).equals("END")) {
                if (line.equals("QUIT")) {
                    bw.flush();
                    bw.close();
                    return;
                }
                cmds.add(line);
            }

            long T = Long.parseLong(br.readLine());
            for (int i = 0; i < T; i++) {
                long x = Long.parseLong(br.readLine());
                long result = execute(cmds, x);
                bw.write(result == Long.MIN_VALUE ? "ERROR" : Long.toString(result));
                bw.newLine();
            }
            bw.newLine();
        }
    }

    private static long execute(List<String> cmds, long x) {
        Deque<Long> stack = new ArrayDeque<>();
        stack.addLast(x);

        for (String cmd : cmds) {
            String query = cmd.split(" ")[0];

            long top;
            long next;
            switch (query) {
                case "NUM":
                    stack.addLast(Long.parseLong(cmd.split(" ")[1]));
                    break;
                case "POP":
                    if (stack.isEmpty()) return Long.MIN_VALUE;
                    stack.removeLast();
                    break;
                case "INV":
                    if (stack.isEmpty()) return Long.MIN_VALUE;
                    stack.addLast(-stack.removeLast());
                    break;
                case "DUP":
                    if (stack.isEmpty()) return Long.MIN_VALUE;
                    stack.addLast(stack.getLast());
                    break;
                case "SWP":
                    if (stack.size() < 2) return Long.MIN_VALUE;
                    top = stack.removeLast();
                    next = stack.removeLast();
                    stack.addLast(top);
                    stack.addLast(next);
                    break;
                case "ADD":
                    if (stack.size() < 2) return Long.MIN_VALUE;
                    top = stack.removeLast();
                    next = stack.removeLast();
                    if (Math.abs(next + top) > 1000000000) return Long.MIN_VALUE;
                    stack.addLast(next + top);
                    break;
                case "SUB":
                    if (stack.size() < 2) return Long.MIN_VALUE;
                    top = stack.removeLast();
                    next = stack.removeLast();
                    if (Math.abs(next - top) > 1000000000) return Long.MIN_VALUE;
                    stack.addLast(next - top);
                    break;
                case "MUL":
                    if (stack.size() < 2) return Long.MIN_VALUE;
                    top = stack.removeLast();
                    next = stack.removeLast();
                    if (Math.abs(next * top) > 1000000000) return Long.MIN_VALUE;
                    stack.addLast(next * top);
                    break;
                case "DIV":
                    if (stack.size() < 2) return Long.MIN_VALUE;
                    top = stack.removeLast();
                    next = stack.removeLast();
                    if (top == 0) return Long.MIN_VALUE;
                    if (top * next < 0) {
                        if (Math.abs(-(Math.abs(next) / Math.abs(top))) > 1000000000) return Long.MIN_VALUE;
                        stack.addLast(-(Math.abs(next) / Math.abs(top)));
                    } else {
                        if (Math.abs(next / top) > 1000000000) return Long.MIN_VALUE;
                        stack.addLast(next / top);
                    }
                    break;
                case "MOD":
                    if (stack.size() < 2) return Long.MIN_VALUE;
                    top = stack.removeLast();
                    next = stack.removeLast();
                    if (top == 0) return Long.MIN_VALUE;
                    if (next < 0) {
                        if (Math.abs(-(Math.abs(next) % Math.abs(top))) > 1000000000) return Long.MIN_VALUE;
                        stack.addLast(-(Math.abs(next) % Math.abs(top)));
                    } else {
                        if (Math.abs(next % Math.abs(top)) > 1000000000) return Long.MIN_VALUE;
                        stack.addLast(next % Math.abs(top));
                    }
            }
        }

        if (stack.size() != 1) return Long.MIN_VALUE;
        long result = stack.removeLast();
        if (result > (1000000000)) return Long.MIN_VALUE;
        return result;
    }
}
