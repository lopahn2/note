package com.sds.msp.problems.etc.stream;

import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class PracticeStream {
    // Java Stream을 활용하여 다음 조건에 해당하는 코드를 작성하세요. 스스로 연습해보는 practice이니 마개조하지 맙시다^^
    // 하나의 stream pipeline으로 결과를 얻어야 교육 효과가 좋습니다.

    List<Data> filter(
            final List<Data> dataList,
            final Instant now) {
        // Practice #1. dataList를 정렬하세요. 정렬 기준은 다음과 같습니다.
        //              현재 시각이 Data 클래스 내 startedAt과 endedAt 사이에 포함되는 경우만 포함한다.
        //              현재 시각은 전달된 now 파라미터를 사용해주세요.
        return dataList.stream()
                .filter(data -> data.startedAt().isAfter(now) && data.endedAt().isBefore(now))
                .collect(Collectors.toList());
    }

    List<String> map(
            final List<Data> dataList) {
        // Practice #2. dataList를 ID만 추려 새로운 List 객체를 만드세요.
        return dataList.stream()
                .map(Data::id)
                .collect(Collectors.toList());
    }

    List<Data> sorted(
            final List<Data> dataList) {
        // Practice #3. dataList를 일자 기준으로 필터링하세요.
        //              Data 클래스 내 basis1로 오름차순 정렬한다.
        //              basis1가 같은 경우 basis2로 내림차순 정렬합니다.
        //              basis1, basis2가 같은 경우는 정렬이 되었다고 간주합니다.
        return dataList.stream()
                .sorted(Comparator.comparing(Data::basis1)
                                  .thenComparing(Comparator.comparing(Data::basis2).reversed()))
                .collect(Collectors.toList());
    }

    void foreach(
            final List<Data> dataList,
            final Consumer<String> dataIdConsumer) {
        // Practice #4. forEach를 사용하여 모든 Data의 id를 dataIdConsumer의 인자로 넣어 실행해 봅니다.
        try {
            dataList.forEach(data -> dataIdConsumer.accept(data.id()));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    int reduce(
            final List<Data> dataList) {
        // Practice #5. reduce를 사용하여 모든 Data의 numbers 리스트 내 숫자들의 총합을 계산하세요.
        //              reduce만으로 해결할 수 없을 때 다른 stream 연산을 활용하여 총합을 계산하세요.
        return dataList.stream()
                .flatMap(data -> data.numbers().stream())
                .reduce(0, Integer::sum);
    }

    boolean anyMatch(
            final List<Data> dataList) {
        // Practice #6. anyMatch를 사용하여 dataList 내 basis1과 basis2가 같은 Data 객체가 하나라도 있는지 그 여부를 확인하세요.
        return dataList.stream()
                .anyMatch(data -> data.basis1() == data.basis2());
    }

    Map<Integer, List<Data>> groupingBy(
            final List<Data> dataList) {
        // Practice #7. groupingBy를 사용하여 basis1로 Grouping된 Data Map을 반환하세요.
        return dataList.stream()
                .collect(Collectors.groupingBy(Data::basis1));
    }

    List<Integer> complex1(
            final List<Data> dataList,
            final Instant now) {
        // Practice #8. 다음 조건을 만족하는 Data별 numbers 총합 리스트를 반환하세요.
        //              조건1. 최종 결과는 basis3로 오름차순 정렬되어야 합니다. basis3가 같은 경우 numbers 총합으로 오름차순 정렬한다.
        //              조건2. numbers 총합이 낮은 3개 데이터만 반환합니다.
        //              조건3. basis1과 basis2의 합이 6 이상인 Data만 포함합니다.
        //              조건4. startedAt가 오늘 일시 이후인 Data는 포함하지 않습니다. 오늘 일시는 now 파라미터를 사용해주세요.
        //              * 연산량을 최적화할 수 있는 방안도 함깨 고민해보세요.
        //              * Data 클래스 멤버변수 구성을 변경하지 말아주세요.
        //              * Data 객체는 불변성을 가집니다.
        record Cache(Data data, Integer sumNumbers) {
        }
        ;

        return dataList.stream()
                .filter(data -> data.startedAt().isBefore(now))
                .filter(data -> data.basis1() + data.basis2() >= 6)
                .map(data -> new Cache(data, data.numbers().stream().reduce(0, Integer::sum)))
                .sorted(Comparator.comparingInt((Cache c) -> c.sumNumbers)) // Step 1: 총합 오름차순
                .limit(3) // Step 2: 총합이 낮은 3개만 선택
                .sorted(Comparator.comparing((Cache c) -> c.data.basis3()) // Step 3: 결과 정렬
                        .thenComparing(c -> c.sumNumbers))
                .map(c -> c.sumNumbers)
                .collect(Collectors.toList());
    }
    Map<Integer, List<Data>> complex2(
            final List<Data> dataList) {
        // Practice #9. 다음 조건을 만족하는 basis1별 정렬된 Data 목록를 반환하세요.
        //              조건1. basis1을 key로, Data 목록을 value로 합니다.
        //              조건2. 반환되는 Data 목록은 basis2로 오름차순 정렬합니다.
        Map<Integer, List<Data>> result = this.groupingBy(dataList);
        for (Integer key : result.keySet()) {
            result.get(key).sort(Comparator.comparing(Data::basis2));
        }
        return result;
    }


    record Pair(
            int basis3,
            int sumOfNumbers
    ) {

        Pair(final Data data) {
            this(data.basis3(),
                    data.numbers().stream().mapToInt(i -> i).sum());
        }
    }
}
