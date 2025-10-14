package com.sds.msp.problems.etc.optional;



import java.util.Optional;

class PracticeOptional {
    static final Something DEFAULT_SOMETHING = new Something("DEFAULT", 1, "DEFAULT", "DEFAULT");
    static final String TEXT_1_DEFAULT = "NONE";
    static final String TEXT_2_SMALLER = "SMALLER";

    Optional<Something> createOptional(final Something something) {
        // ToDo: Java Optional을 활용하여 다음 조건에 해당하는 코드를 작성하세요. 스스로 연습해보는 practice이니 마개조하지 맙시다^^
        //       하나의 statement로 작성하면 교육 효과가 더욱 좋습니다.
        // Practice #1. 주어진 객체를 담는 Optional 객체를 생성하여 반환하세요.
        //              something 파라미터는 null일 수 있다는 사실에 유의하세요.
        return null == something ? Optional.empty() : Optional.of(something);
    }

    Something getSomethingOrDefault(final Something something) {
        final var optionalSomething = Optional.ofNullable(something);

        // ToDo: Java Optional을 활용하여 다음 조건에 해당하는 코드를 작성하세요. 스스로 연습해보는 practice이니 마개조하지 맙시다^^
        //       하나의 statement로 작성하면 교육 효과가 더욱 좋습니다.
        // Practice #2. optionalSomething에 들어있는 객체를 반환하세요.
        //              만약 들어있는 값이 null이라면 DEFAULT_SOMETHING을 반환하세요.
        return optionalSomething.orElse(DEFAULT_SOMETHING);
    }

    Something getSomethingOrException(final Something something) throws Exception {
        final var optionalSomething = Optional.ofNullable(something);

        // ToDo: Java Optional을 활용하여 다음 조건에 해당하는 코드를 작성하세요. 스스로 연습해보는 practice이니 마개조하지 맙시다^^
        //       하나의 statement로 작성하면 교육 효과가 더욱 좋습니다.
        // Practice #3. optionalSomething에 들어있는 객체를 반환하세요.
        //              만약 들어있는 값이 null이라면 Exception을 발생시키세요.
        return optionalSomething.orElseThrow(Exception::new);
    }

    String getText1OrDefault(final Something something) {
        final var optionalSomething = Optional.ofNullable(something);

        // ToDo: Java Optional을 활용하여 다음 조건에 해당하는 코드를 작성하세요. 스스로 연습해보는 practice이니 마개조하지 맙시다^^
        //       하나의 statement로 작성하면 교육 효과가 더욱 좋습니다.
        // Practice #4. optionalSomething에 들어있는 text1 문자열을 반환하세요.
        //              optionalSomething에 null이 들어있거나 text1의 값이 null이면 TEXT_1_DEFAULT으로 변환하여 반환하세요.
        return optionalSomething
                .map(Something::text1)
                .orElse(TEXT_1_DEFAULT);
    }

    String getText2AfterFilter(final Something something) {
        final var optionalSomething = Optional.ofNullable(something);

        // ToDo: Java Optional을 활용하여 다음 조건에 해당하는 코드를 작성하세요. 스스로 연습해보는 practice이니 마개조하지 맙시다^^
        //       하나의 statement로 작성하면 교육 효과가 더욱 좋습니다.
        // Practice #5. optionalSomething에 들어있는 text2 문자열을 반환하세요. 다만 number1의 상태에 따라 다음과 같은 조건을 만족해야 합니다.
        //              조건1. number1이 5보다 큰 경우, text2의 문자열을 그대로 반환하세요.
        //              조건2. number1이 5 이하인 경우, TEXT_2_SMALLER를 반환하세요.
        //              * something이 null인 경우는 없습니다.
        return optionalSomething
                .filter(smt -> smt.number1() > 5)
                .map(Something::text2)
                .orElse(TEXT_2_SMALLER);
    }
}
