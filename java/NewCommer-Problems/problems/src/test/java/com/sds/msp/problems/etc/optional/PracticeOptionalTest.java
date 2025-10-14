package com.sds.msp.problems.etc.optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.sds.msp.problems.etc.optional.PracticeOptional.*;
import static com.sds.msp.problems.utils.RandomUtils.randomAlphanumeric;
import static com.sds.msp.problems.utils.RandomUtils.randomInt;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class PracticeOptionalTest {

    private PracticeOptional underTest;

    @BeforeEach
    void setup() {
        this.underTest = new PracticeOptional();
    }

    @DisplayName("Assert createOptional practice given something")
    @Test
    void testcase1() {
        // given
        final var something = randomSomething();

        // when
        final var actual = underTest.createOptional(something);

        // then
        assertThat(actual).contains(something);
    }

    @DisplayName("Assert createOptional practice given null")
    @Test
    void testcase2() {
        // when
        final var actual = underTest.createOptional(null);

        // then
        assertThat(actual).isEmpty();
    }

    @DisplayName("Assert getSomethingOrDefault practice given something")
    @Test
    void testcase3() {
        // given
        final var something = randomSomething();

        // when
        final var actual = underTest.getSomethingOrDefault(something);

        // then
        assertThat(actual).isSameAs(something);
    }

    @DisplayName("Assert getSomethingOrDefault practice given null")
    @Test
    void testcase4() {
        // when
        final var actual = underTest.getSomethingOrDefault(null);

        // then
        assertThat(actual).isSameAs(DEFAULT_SOMETHING);
    }

    @DisplayName("Assert getSomethingOrException practice given something")
    @Test
    void testcase5() throws Exception {
        // given
        final var something = randomSomething();

        // when
        final var actual = underTest.getSomethingOrException(something);

        // then
        assertThat(actual).isSameAs(something);
    }

    @DisplayName("Assert getSomethingOrException practice null")
    @Test
    void testcase6() {
        assertThatThrownBy(() -> underTest.getSomethingOrException(null));
    }

    @DisplayName("Assert getText1OrDefault practice given something")
    @Test
    void testcase7() {
        // given
        final var something = randomSomething();

        // when
        final var actual = underTest.getText1OrDefault(something);

        // then
        assertThat(actual).isEqualTo(something.text1());
    }

    @DisplayName("Assert getText1OrDefault practice given null")
    @Test
    void testcase8() {
        // when
        final var result = underTest.getText1OrDefault(null);

        // then
        assertThat(result).isEqualTo(TEXT_1_DEFAULT);
    }

    @DisplayName("Assert getText1OrDefault practice given something")
    @Test
    void testcase9() {
        // given
        final var somethingWithText1Null = randomSomethingWithText1Null();

        // when
        final var result = underTest.getText1OrDefault(somethingWithText1Null);

        // then
        assertThat(result).isSameAs(TEXT_1_DEFAULT);
    }

    @DisplayName("Assert getText1AfterFilter practice given something with number which is under 6")
    @Test
    void testcase10() {
        // given
        final var somethingWithNumber1Under6 = randomSomethingWithNumber1(randomInt(0, 6));

        // when
        final var actual = underTest.getText2AfterFilter(somethingWithNumber1Under6);

        // then
        assertThat(actual).isEqualTo(TEXT_2_SMALLER);
    }

    @DisplayName("Assert getText1AfterFilter practice given something with number which is upper 5")
    @Test
    void testcase11() {
        // given
        final var somethingWithNumber1Over5 = randomSomethingWithNumber1(randomInt(6, 1000));

        // when
        final var actual = underTest.getText2AfterFilter(somethingWithNumber1Over5);

        // then
        assertThat(actual).isSameAs(somethingWithNumber1Over5.text2());
    }

    private static Something randomSomething() {
        return new Something(
                randomAlphanumeric(),
                randomInt(),
                randomAlphanumeric(),
                randomAlphanumeric()
        );
    }

    private static Something randomSomethingWithText1Null() {
        return new Something(
                randomAlphanumeric(),
                randomInt(),
                null,
                randomAlphanumeric()
        );
    }

    private static Something randomSomethingWithNumber1(final int number1) {
        return new Something(
                randomAlphanumeric(),
                number1,
                randomAlphanumeric(),
                randomAlphanumeric()
        );
    }
}
