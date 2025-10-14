import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapExample {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("people", "사람");
        map.put("baseball", "야구");
        System.out.println(map.get("people")); // "사람" 출력
        System.out.println(map.get("java"));  // null 출력
        System.out.println(map.getOrDefault("java", "자바"));  // "자바" 출력
        System.out.println(map.containsKey("people"));  // true 출력
        System.out.println(map.keySet());  // [baseball, people] 출력 -> 집합 자료형
        System.out.println(map.remove("people"));  // "사람" 출력 후 삭제
        System.out.println(map.size());

        /**
         * LinkedHashMap과 TreeMap
         * 맵의 가장 큰 특징은 순서에 의존하지 않고 key로 value를 가져오는 것이다.
         * 그런데 가끔 Map에 입력된 순서대로 데이터를 가져오거나 입력한 key에 의해 정렬(sort)하도록 저장하고 싶을 수 있다.
         * 이럴때는 LinkedHashMap과 TreeMap을 사용하면 된다.
         *
         * LinkedHashMap : 입력된 순서대로 데이터를 저장한다.
         * TreeMap : 입력된 key의 오름차순으로 데이터를 저장한다.
         * */

        // LinkedHashMap 예제 - 입력 순서 유지
        Map<String, Integer> linkedMap = new LinkedHashMap<>();
        linkedMap.put("banana", 3);
        linkedMap.put("apple", 5);
        linkedMap.put("cherry", 2);

        System.out.println("LinkedHashMap (입력 순서 유지):");
        for (String key : linkedMap.keySet()) {
            System.out.println(key + " => " + linkedMap.get(key));
        }

        // TreeMap 예제 - key 기준 오름차순 정렬
        Map<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("banana", 3);
        treeMap.put("apple", 5);
        treeMap.put("cherry", 2);

        System.out.println("\nTreeMap (key 기준 정렬):");
        for (String key : treeMap.keySet()) {
            System.out.println(key + " => " + treeMap.get(key));
        }

    }
}
