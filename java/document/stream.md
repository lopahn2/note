# Java Stream API 정리

## 1. Java에서 사용되는 Stream이란?
Java Stream은 컬렉션, 배열, I/O 자원 등 다양한 데이터 소스를 함수형 방식으로 처리할 수 있는 API입니다. 반복, 필터링, 매핑, 정렬, 집계 등을 선언적 방식으로 처리하며, 람다와 함께 사용되어 코드의 가독성과 생산성을 향상시킵니다.

## 2. Collection과의 차이점
| 항목 | Collection | Stream |
|------|------------|--------|
| 데이터 저장 | O | X |
| 사용 방식 | 외부 반복 (for-each 등) | 내부 반복 (함수형 처리) |
| 재사용성 | O | X (1회 사용 후 닫힘) |
| 실행 시점 | 즉시 | 지연(Lazy) |
| 병렬 처리 | 수동 구현 필요 | parallelStream()으로 간단 처리 |

## 3. Stream 객체 생성 방법

### 3.1 Empty Stream
```java
Stream<String> stream = Stream.empty();
```

### 3.2 Stream of Collection
```java
List<String> list = List.of("a", "b", "c");
Stream<String> stream = list.stream();
```

### 3.3 Stream of Array
```java
String[] arr = {"a", "b", "c"};
Stream<String> stream1 = Stream.of(arr);
Stream<String> stream2 = Arrays.stream(arr);
Stream<String> stream3 = Arrays.stream(arr, 1, 3); // 부분 배열
```

## 4. 중간 연산 함수 (Intermediate Operations)

### 4.1 filter()
조건에 맞는 요소만 남김
```java
stream.filter(s -> s.startsWith("a"));
```

### 4.2 map()
요소를 변형
```java
stream.map(String::toUpperCase);
```

### 4.3 limit()
지정된 개수만큼 자름
```java
stream.limit(5);
```

### 4.4 sorted()
기본 또는 사용자 지정 기준으로 정렬
```java
stream.sorted(); // 오름차순
stream.sorted(Comparator.reverseOrder()); // 내림차순
```

### 4.5 distinct()
중복 제거
```java
stream.distinct();
```

## 5. 최종 연산 함수 (Terminal Operations)

### 5.1 forEach()
모든 요소에 작업 수행
```java
stream.forEach(System.out::println);
```

### 5.2 allMatch(), anyMatch()
조건 검사
```java
stream.allMatch(s -> s.length() > 0);
stream.anyMatch(s -> s.contains("a"));
```

### 5.3 reduce()
누적 연산 수행
```java
Optional<Integer> result = Stream.of(1,2,3).reduce((a, b) -> a + b);
```

### 5.4 collect()
수집기(Collector)로 수집
```java
List<String> result = stream.collect(Collectors.toList());
```

### 5.5 toList()
Java 16 이상에서 간단한 리스트 수집
```java
List<String> result = stream.toList();
```

## 6. stream() vs parallelStream()

| 항목 | stream() | parallelStream() |
|------|----------|------------------|
| 처리 방식 | 순차적 | 병렬 (ForkJoinPool) |
| 성능 | 작은 데이터에 적합 | 큰 데이터에 적합 |
| 예외 디버깅 | 쉬움 | 어려움 |
| 순서 보장 | 보장 | 보장되지 않을 수 있음 |

```java
list.stream().forEach(System.out::println);
list.parallelStream().forEach(System.out::println);
```
