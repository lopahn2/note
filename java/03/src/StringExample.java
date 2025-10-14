public class StringExample {
    public static void main(String[] args) {

        /*
        * a와 b는 값은 같지만 주소값이 다르다.
         "" 방식을 literal 표기라 하는데 고정된 값을 그대로 대입한다.
         new 방식은 항상 새로운 String 객체를 만든다.
        * */
        String a = "Happy Java";
        String b = new String("Happy Java");

        // TIP : 멀티 스레드 환경에서 동기화를 지원하려면 원시 자료형 대신 Wrapper Class를 사용해야 한다.

        System.out.println(a.equals(b)); // true 출력
        System.out.println(a.indexOf("Java"));  // 6 출력
        System.out.println(a.contains("Java"));  // true 출력
        System.out.println(a.charAt(6));  // "J" 출력
        System.out.println(a.replaceAll("Java", "World"));  // Hello World 출력
        System.out.println(a.substring(0, 4));  // Hell 출력
        System.out.println(a.toUpperCase());  // HELLO JAVA 출력

        String c = "a:b:c:d";
        String[] result = c.split(":");  // result는 {"a", "b", "c", "d"}

        int number = 10;
        String day = "three";
        System.out.println(String.format("I ate %d apples. so I was sick for %s days.", number, day));
        // 여기에서 재미있는 것은 %s 포맷 코드인데, 이 코드는 어떤 형태의 값이든 변환해 넣을 수 있다.
        System.out.println(String.format("I have %s apples",  3));  // "I have 3 apples" 출력
        System.out.println(String.format("rate is %s", 3.234));  // "rate is 3.234" 출력

        System.out.println(String.format("%10s", "hi"));  // "        hi" 출력
        System.out.println(String.format("%-10sjane.", "hi"));  // "hi        jane." 출력
        System.out.println(String.format("%.4f", 3.42134234));  // 3.4213 출력
        System.out.println(String.format("%10.4f", 3.42134234));  // '    3.4213' 출력
        System.out.printf("I eat %d apples.", 3);  // "I eat 3 apples." 출력
    }
}
