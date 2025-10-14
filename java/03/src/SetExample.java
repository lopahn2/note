import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SetExample {
    public static void main(String[] args) {
        HashSet<Integer> s1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        HashSet<Integer> s2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8, 9));

        s1.add(1);
        s1.remove(1);

        HashSet<Integer> intersection = new HashSet<>(s1);  // s1으로 intersection 생성
        intersection.retainAll(s2);  // 교집합 수행
        System.out.println(intersection);  // [4, 5, 6] 출력

        HashSet<Integer> union = new HashSet<>(s1);  // s1으로 union 생성
        union.addAll(s2); // 합집합 수행
        System.out.println(union);  // [1, 2, 3, 4, 5, 6, 7, 8, 9] 출력

        HashSet<Integer> substract = new HashSet<>(s1);  // s1으로 substract 생성
        substract.removeAll(s2); // 차집합 수행
        System.out.println(substract);  // [1, 2, 3] 출력

        /**
         * TreeSet과 LinkedHashSet
         * 집합 자료형의 특징은 순서가 없다는 것이다. 그런데 집합에 입력한 순서대로 데이터를 가져오거나 오름차순으로 정렬된 데이터를 가져오고 싶을 수 있다. 이럴 때는 TreeSet과 LinkedHashSet을 사용하자.
         *
         * TreeSet : 값을 오름차순으로 정렬해 저장한다.
         * LinkedHashSet : 값을 입력한 순서대로 정렬한다.
         * */

        // LinkedHashSet 예제 - 입력 순서 유지
        LinkedHashSet<String> linkedSet = new LinkedHashSet<>();
        linkedSet.add("banana");
        linkedSet.add("apple");
        linkedSet.add("cherry");

        System.out.println("LinkedHashSet (입력 순서 유지):");
        for (String item : linkedSet) {
            System.out.println(item);
        }

        // TreeSet 예제 - 오름차순 정렬
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("banana");
        treeSet.add("apple");
        treeSet.add("cherry");

        System.out.println("\nTreeSet (오름차순 정렬):");
        for (String item : treeSet) {
            System.out.println(item);
        }
    }
}
