package org.example.day_01.problem1713;

import java.io.*;
import java.util.*;

public class Main {

    static class Candidate {
        int student;
        int point;
        int time;

        public Candidate(int student, int point, int time) {
            this.student = student;
            this.point = point;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int R = Integer.parseInt(br.readLine());
        List<Integer> recommends = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        while (st.hasMoreTokens()) {
            recommends.add(Integer.parseInt(st.nextToken()));
        }

        HashMap<Integer, Candidate> frames = new HashMap<>();

        int time = 0;
        for (int student : recommends) {
            if (frames.containsKey(student)) {
                frames.get(student).point++;
            } else {
                if (frames.size() >= N) {
                    int removeIdx = frames.values().stream()
                            .min(Comparator.comparingInt((Candidate c) -> c.point)
                                    .thenComparing((Candidate c) -> c.time))
                            .get().student;
                    frames.remove(removeIdx);
                }
                frames.put(student, new Candidate(student, 1, time));
            }
            time++;
        }

        List<Integer> answer = new ArrayList<>(frames.keySet());
        Collections.sort(answer);
        for (int student : answer) {
            bw.write(student + " ");
        }
        bw.flush();
        bw.close();

    }
}
