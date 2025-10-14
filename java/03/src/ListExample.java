import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ListExample {
    public static void main(String[] args) {
        ArrayList<String> pitches = new ArrayList<>();
        pitches.add("138");
        pitches.add("129");
        pitches.add("142");
        pitches.add(0, "133");    // 첫번째 위치에 133 삽입.
        System.out.println(pitches.get(1)); // 129
        System.out.println(pitches.size()); // 3
        System.out.println(pitches.contains("142")); // true
        System.out.println(pitches.remove("129"));  // 129를 리스트에서 삭제하고, true를 리턴한다.
        System.out.println(pitches.remove(0));  // pitches의 첫 번째 항목이 138이므로, 138을 삭제한 뒤 138을 리턴한다.


        String[] data = {"138", "129", "142"};  // 이미 투구수 데이터 배열이 있다.
        ArrayList<String> pitches2 = new ArrayList<>(Arrays.asList(data));
        System.out.println(pitches2);  // [138, 129, 142] 출력

        ArrayList<String> pitches3 = new ArrayList<>(Arrays.asList("138", "129", "142"));
        System.out.println(pitches3);

        String result = String.join(",", pitches3); // 리스트가 아닌 배열에서도 사용 가능
        System.out.println(result);  // 138,129,142 출력

        ArrayList<String> sortPitches = new ArrayList<>(Arrays.asList("138", "129", "142"));
        sortPitches.sort(Comparator.naturalOrder());  // 오름차순으로 정렬, 내림차순은 reverseOrder()
        System.out.println(sortPitches);  // [129, 138, 142] 출력
    }
}
