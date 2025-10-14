public class ArrayExample {
    public static void main(String[] args) {
        int[] odds = {1, 3, 5, 7, 9};
        String[] weeks1 = {"월", "화", "수", "목", "금", "토", "일"};
        String[] weeks2 = new String[7];
        weeks2[0] = "월";
        weeks2[1] = "화";
        weeks2[2] = "수";
        weeks2[3] = "목";
        weeks2[4] = "금";
        weeks2[5] = "토";
        weeks2[6] = "일";

//        String[] weeks = new String[];    // 길이에 대한 숫자값이 없으므로 컴파일 오류가 발생한다.
        String[] weeks = {"월", "화", "수", "목", "금", "토", "일"};
        System.out.println(weeks[3]); // 목

        for (int i = 0; i < weeks.length; i++) {
            System.out.println(weeks[i]);
        }


    }
}
