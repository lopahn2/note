package org.example.cthd.basic;
import java.util.*;

public class combination {


    class Solution {
        public static HashMap<Integer, HashMap<String, Integer>> courseMap = new HashMap<>();


        public String[] solution(String[] orders, int[] course) {

            for(int i: course) {
                courseMap.put(i, new HashMap<String, Integer>());
            }

            for(String order: orders) {
                char[] od = order.toCharArray();
                Arrays.sort(od);
                combinations(0, od, "");
            }

            List<String> answer = new ArrayList<>();

            for(HashMap<String, Integer> count : courseMap.values()) {
                count.values()
                        .stream()
                        .max(Integer::compare)
                        .ifPresent(cnt -> count.entrySet()
                                .stream()
                                .filter(entry -> cnt.equals(entry.getValue()) && cnt > 1)
                                .forEach(entry -> {
                                    answer.add(entry.getKey());
                                }));
            }


            return answer.stream().sorted().toArray(String[]::new);
        }

        public static void combinations(int idx, char[] order, String result) {
            if (courseMap.containsKey(result.length())) {
                HashMap<String, Integer> map = courseMap.get(result.length());

                map.put(result, map.getOrDefault(result, 0) + 1);
            }

            for (int i = idx; i < order.length; i++) {
                combinations(i + 1, order, result + order[i]);
            }
        }
    }
}
