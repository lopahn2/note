package com.sds.msp.problems.etc.generic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class PracticeGenericTest {

    private PracticeGeneric underTest;

    @BeforeEach
    void setup() {
        this.underTest = new PracticeGeneric();
    }

    @Test
    void printAll() {
        // given
        List<String> strings = Arrays.asList("a", "b", "c");
        List<Integer> integers = Arrays.asList(1, 2, 3);

        // when
        underTest.printAll(strings);
        underTest.printAll(integers);
    }

    @Test
    void create() {
        // given & when
        final var actual = underTest.create();


        // then
        assertThat(actual).isNotNull()
                .returns(1, Something::info);
    }


    @Test
    void max_integer() {
        // given & when
        final var actual = underTest.max(10, 20);

        // then
        assertThat(actual).isEqualTo(20);
    }

    @Test
    void max_string() {
        // given & when
        final var actual = underTest.max("apple", "banana");

        // then
        assertThat(actual).isEqualTo("banana");
    }

    @Test
    void copy() {
        // given
        final var dest = new ArrayList<Object>(Arrays.asList("", "", ""));
        final var src = Arrays.asList("a", "b", "c");

        // when
        underTest.copy(dest, src);

        // then
        assertThat(dest).containsExactly("a", "b", "c");
    }
}
