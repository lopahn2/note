package com.sds.msp.problems.etc.generic;

import java.util.List;

public class PracticeGeneric {

    public <T> void printAll(final List<T> list) {
        // 제네릭을 사용해 메소드 시그니처를 수정하여,
        // 다음 코드가 컴파일되고, 정상 실행되도록 하세요
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    Something<Data, Integer> create() {
        // 올바른 데이터 타입을 사용해 메소드 시그니처를 수정하여,
        // 다음 코드가 컴파일되고, 정상 실행되도록 하세요
        return new Something<>(new Data(1), 1);
    }

    <T extends Comparable<T>> T max(final T a, final T b) {
        // Bounded Type을 사용해 메소드 시그니처를 수정하여,
        // 다음 코드가 컴파일되고, 정상 실행되도록 하세요.
        // java.lang.Comparable을 참고하세요.
        return a.compareTo(b) >= 0 ? a : b;
    }

    <T> void copy(final List<? super T> dest, final List<? extends T> src) {
        // 올바른 제네릭 와일드카드 문법을 사용해 메소드 시그니처를 수정하여,
        // 다음 코드가 컴파일되고, 정상 실행되도록 하세요
        for (int i = 0; i < src.size(); i++) {
            dest.set(i, src.get(i));
        }
    }

    record Data(
            int value
    ) {
    }
}
