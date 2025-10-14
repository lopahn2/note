package org.example.cthd.basic;

import java.util.*;
import java.util.stream.Collectors;

public class Snipet {
    public static void main(String[] args) {
        int[] scores = new int[100];
        int maxScore = Arrays.stream(scores).max().getAsInt(); // 배열에서 가장 큰 값 가져오기

        List<Integer> answer = new ArrayList<>();
        answer.add(1);
        answer.stream().mapToInt(Integer::intValue).toArray(); // List를 배열로 만들기;


        Map<Integer, Float> map = new HashMap<>();

        map.put(1, 1.0f);
        map.put(2, 3.0f);
        map.put(3, 2.0f);
        map.put(4, 4.0f);

        map.entrySet().stream()
                .sorted((o1, o2) -> o1.getValue().equals(o2.getValue())
                        ? Integer.compare(o1.getKey(), o2.getKey())
                        : Double.compare(o2.getValue(), o1.getValue()))
                .mapToInt(HashMap.Entry::getKey)
                .toArray();

        map.containsKey(1);
        map.containsValue(1.0f);

        String myString = "Hello Java";

        // Convert the String to a char array
        char[] charArray = myString.toCharArray();

        for (char c : charArray) {
            System.out.println(c);
        }

        // 상, 하, 좌, 우 방향 정의 (U, D, L, R)
        Map<Character, int[]> directionMap = Map.of(
                'U', new int[]{0, -1},
                'D', new int[]{0, 1},
                'L', new int[]{-1, 0},
                'R', new int[]{1, 0}
        );

        Stack<Integer> stack = new Stack<>();

        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        sb.toString();

        String[] arr = {"a","b","c"};
        Arrays.asList(arr).lastIndexOf("b"); // 1
        Arrays.asList(arr).indexOf("b"); // 1

        HashMap<String, Integer> part = new HashMap<>();

        Map<String, Integer> filteredMap =
                part.entrySet().stream()
                        .filter(p -> p.getValue() != 0)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));

        part.getOrDefault("이 키가 없으면 0이 나와요", 0);

        HashMap<String, Integer> wmap = new HashMap<>();
        wmap.put("1", 1);
        wmap.put("2", 2);
        wmap.put("3", 3);

        HashMap<String, Integer> rmap = new HashMap<>();
        rmap.put("1", 1);
        rmap.put("3", 3);
        rmap.put("2", 2);

        wmap.equals(rmap); // true


        class Solution {
            class Data {
                Integer plays;
                Integer index;

                public Data(Integer plays, Integer index) {
                    this.plays = plays;
                    this.index = index;
                }
            }
            public int[] solution(String[] genres, int[] plays) {
                Map<String, Integer> map = new HashMap<>();
                Map<String, List<Data>> target = new HashMap<>();
                List<Integer> answer = new ArrayList<>();


                for(int i = 0; i < genres.length; i++) {
                    map.put(genres[i], map.getOrDefault(genres[i], 0) + plays[i]);
                    List<Data> list = target.getOrDefault(genres[i], new ArrayList<>());
                    list.add(new Data(plays[i], i));
                    target.put(genres[i], list);
                }

                map.entrySet().stream()
                        .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                        .forEach(m -> {
                            List<Data> targetData = target.get(m.getKey());
                            targetData.stream().sorted((d1, d2) ->
                                            d1.plays == d2.plays ? Integer.compare(d1.index, d2.index) : Integer.compare(d2.plays, d1.plays)
                                    ).limit(2)
                                    .forEach(d -> {
                                        answer.add(d.index);
                                    });
                        });


                return answer.stream().mapToInt(Integer::intValue).toArray();
                // answer.stream().toArray(String[]::new);
            }
        }



    }
}
