package com.sds.msp.problems.etc.stream;

import com.sds.msp.problems.utils.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

import static com.sds.msp.problems.utils.RandomUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class PracticeStreamTest {

    private final Instant NOW = Instant.ofEpochMilli(1673483369235L);

    private PracticeStream underTest;

    @BeforeEach
    void setup() {
        this.underTest = new PracticeStream();
    }

    @DisplayName("Assert filter practice")
    @Test
    void testcase1() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.filter(dataList, NOW);

        // then
        int countFilteredData = 0;
        for (final var data : dataList) {
            if (data.startedAt().isAfter(NOW) || data.endedAt().isBefore(NOW)) {
                countFilteredData++;
            }
        }
        assertThat(actual)
                .filteredOn(data -> data.startedAt().isBefore(NOW) && data.endedAt().isAfter(NOW))
                .hasSize(dataList.size() - countFilteredData);
    }

    @DisplayName("Assert map practice")
    @Test
    void testcase2() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.map(dataList);

        // then
        final var ids = new ArrayList<String>(dataList.size());
        for (final var data : dataList) {
            ids.add(data.id());
        }
        assertThat(actual)
                .hasSize(dataList.size())
                .containsExactlyElementsOf(ids);
    }

    @DisplayName("Assert sorted practice")
    @Test
    void testcase3() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.sorted(dataList);

        // then
        assertThat(actual).hasSize(dataList.size());

        Data prev = null;
        for (final var data : actual) {
            if (prev == null ||
                    (prev.basis1() < data.basis1()
                            || (prev.basis1() == data.basis1() && prev.basis2() >= data.basis2()))) {
                prev = data;
                continue;
            }

            fail("not sorted properly");
        }
    }

    @DisplayName("Assert forEach practice")
    @Test
    void testcase4() {
        // given
        final var dataList = randomDataList();

        // when
        final var dataIdConsumer = new TestDataIdConsumer();
        underTest.foreach(dataList, dataIdConsumer);

        // then
        for (int i = 0; i < dataList.size(); i++) {
            if (!dataList.get(i).id().equals(dataIdConsumer.acceptedIds.get(i))) {
                fail("not accepted properly");
            }
        }
    }

    @DisplayName("Assert reduce practice")
    @Test
    void testcase5() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.reduce(dataList);

        // then
        int sum = 0;
        for (final var data : dataList) {
            for (final var number : data.numbers()) {
                sum += number;
            }
        }
        assertThat(actual).isEqualTo(sum);
    }

    @DisplayName("Assert anyMatch practice")
    @Test
    void testcase6() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.anyMatch(dataList);

        // then
        var hasDataMatchedBasis1AndBasis2 = false;
        for (var data : dataList) {
            if (data.basis1() == data.basis2()) {
                hasDataMatchedBasis1AndBasis2 = true;
                break;
            }
        }
        assertThat(actual).isEqualTo(hasDataMatchedBasis1AndBasis2);
    }

    @DisplayName("Assert anyMatch practice")
    @Test
    void testcase7() {
        // given
        final var dataList = generateList(10L, PracticeStreamTest::randomData);

        // when
        final var actual = underTest.groupingBy(dataList);

        // then
        final var actualKeys = actual.keySet();
        final var expectedKeys = new HashSet<Integer>();
        for (final var data : dataList) {
            expectedKeys.add(data.basis1());
        }

        final var hasSameSize = actualKeys.size() == expectedKeys.size();
        assertThat(hasSameSize).isTrue();

        for (final var expectedKey : expectedKeys) {
            if (!actualKeys.contains(expectedKey)) {
                fail("not sorted properly");
            }
        }

        for (final var actualKey : actualKeys) {
            if (!expectedKeys.contains(actualKey)) {
                fail("not sorted properly");
            }
        }
    }

    @DisplayName("Assert complex1 practice")
    @Test
    void testcase8() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.complex1(dataList, NOW);

        // then
        final var numberSumToDataList = new ArrayList<PracticeStream.Pair>();
        for (final var data : dataList) {
            if (data.basis1() + data.basis2() < 6) {
                continue;
            }
            if (data.startedAt().isAfter(NOW)) {
                continue;
            }

            numberSumToDataList.add(new PracticeStream.Pair(data));
        }

        numberSumToDataList.sort(Comparator.comparingInt(PracticeStream.Pair::sumOfNumbers));
        final var limitedList = numberSumToDataList.subList(0, Math.min(3, numberSumToDataList.size()));

        limitedList.sort(Comparator
                .comparingInt(PracticeStream.Pair::basis3)
                .thenComparingInt(PracticeStream.Pair::sumOfNumbers));

        final var solution = new ArrayList<Integer>(limitedList.size());
        for (final var pair : limitedList) {
            solution.add(pair.sumOfNumbers());
        }

        assertThat(actual).containsExactlyElementsOf(solution);
    }

    @DisplayName("Assert complex2 practice")
    @Test
    void testcase9() {
        // given
        final var dataList = randomDataList();

        // when
        final var actual = underTest.complex2(dataList);

        // then
        final var actualKeys = actual.keySet();
        final var expectedKeys = new HashSet<Integer>();
        for (final var data : dataList) {
            expectedKeys.add(data.basis1());
        }

        final var hasSameSize = actualKeys.size() == expectedKeys.size();
        assertThat(hasSameSize).isTrue();

        for (final var expectedKey : expectedKeys) {
            if (!actualKeys.contains(expectedKey)) {
                fail("not sorted properly");
            }
        }

        for (final var actualKey : actualKeys) {
            if (!expectedKeys.contains(actualKey)) {
                fail("not sorted properly");
            }
        }

        var actualTotalCount = 0;
        for (final var key : actualKeys) {
            final var subDataList = actual.get(key);
            actualTotalCount = actualTotalCount + subDataList.size();
            Data prev = null;
            for (final var data : subDataList) {
                if ((prev == null) || (prev.basis2() <= data.basis2() && prev.basis1() == key && data.basis1() == key)) {
                    prev = data;
                    continue;
                }

                fail("not sorted properly");
            }
        }
        assertThat(actualTotalCount).isEqualTo(dataList.size());
    }

    private static List<Data> randomDataList() {
        return generateList(80L, PracticeStreamTest::randomData);
    }

    private static Data randomData() {
        final var instants = generateList(2L, RandomUtils::randomInstant);
        return new Data(
                RandomUtils.randomAlphanumeric(),
                RandomUtils.randomInt(1, 10),
                RandomUtils.randomInt(1, 10),
                RandomUtils.randomInt(),
                instants.get(0),
                instants.get(1),
                generateList(21L, () -> RandomUtils.randomInt(1, 10)));
    }

    private static class TestDataIdConsumer implements Consumer<String> {
        private final List<String> acceptedIds = new ArrayList<>();

        @Override
        public void accept(final String s) {
            acceptedIds.add(s);
        }
    }
}
