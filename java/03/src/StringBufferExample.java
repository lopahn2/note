public class StringBufferExample {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();  // StringBuffer 객체 sb 생성
        sb.append("hello");
        sb.append(" ");
        sb.append("jump to java");
        String result1 = sb.toString();
        System.out.println(result1);  // "hello jump to java" 출력

        String result2 = "";
        result2 += "hello";
        result2 += " ";
        result2 += "jump to java";
        System.out.println(result2);  // "hello jump to java" 출력

        StringBuilder sbu = new StringBuilder();
        sbu.append("hello");
        sbu.append(" ");
        sbu.append("jump to java");
        String result3 = sbu.toString();
        System.out.println(result3);

        // StringBuffer는 객체를 한 번만 만들고, String은 매 번 객체를 만든다.
        // toUpperCase() 같은 경우에도 새로운 String 객체를 만드는 것이다.
        // StringBuffer = mutable & String = immutable

        /*
         * StringBuffer는 대신 객체가 무거워 생성 코스트가 높다.
         * 따라서 수정이 많은 경우와 멀티 스레드가 고려되는 환경에서는 StringBuffer
         * 수정이 많고 싱글 스레드가 고려되는 환경에서는 StringBuilder
         * 수정이 적으면 String 을 사용하자.
         * */

        StringBuffer sb3 = new StringBuffer();
        sb3.append("jump to java");
        sb3.insert(0, "hello ");
        System.out.println(sb3.toString());
        System.out.println(sb3.substring(0, 4)); //hell

    }
}
