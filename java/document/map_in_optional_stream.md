# ✅ 1. Optional.map() vs Optional.flatMap()

## 🔹 공통점

둘 다 Optional<T>에 담긴 값을 가공할 때 사용합니다.

함수형 스타일로 null-safe하게 데이터를 다룰 수 있게 합니다.

## 🔹 차이점 요약

| 기준          | `map`                             | `flatMap`                    |
| ----------- | --------------------------------- | ---------------------------- |
| 함수 리턴 타입    | `T → U`                           | `T → Optional<U>`            |
| 최종 결과       | `Optional<U>`                     | `Optional<U>`                |
| 중첩 Optional | 생길 수 있음 (`Optional<Optional<U>>`) | 중첩을 제거해서 `Optional<U>`로 평탄화함 |


### 🔸 예시로 비교

📌 map을 쓸 때
```java
Optional<Optional<String>> result = Optional.of("hello")
                                            .map(s -> Optional.of(s.toUpperCase()));
// 결과: Optional[Optional["HELLO"]]
```

📌 flatMap을 쓸 때

```java
Optional<String> result = Optional.of("hello")
                                  .flatMap(s -> Optional.of(s.toUpperCase()));
// 결과: Optional["HELLO"]
```

🔑 요약 문장

> map은 일반적인 변환,  
> flatMap은 Optional을 반환하는 함수를 쓸 때 중첩을 제거해주는 역할

# ✅ 2. Optional.map() vs Stream.map()

## 🔹 공통점

둘 다 내부에 있는 값을 함수로 변환합니다.

둘 다 map(Function<T, R>) 형태입니다.

### 🔹 차이점 요약

| 기준           | `Optional.map()`      | `Stream.map()`                |
| ------------ | --------------------- | ----------------------------- |
| 대상 컬렉션       | 0개 또는 1개 (Optional 값) | 0개 이상 (컬렉션/배열 등)              |
| 반환값          | `Optional<R>`         | `Stream<R>`                   |
| 사용 목적        | **null-safe 처리** 중심   | **데이터 흐름/변환 처리** 중심           |
| flatMap의 사용처 | 중첩된 Optional 평탄화      | 다수의 원소를 펼칠 때 (List to Stream) |


📌 Stream.map() 예시

```java
List<String> names = List.of("a", "bb", "ccc");
List<Integer> lengths = names.stream()
                             .map(String::length)
                             .collect(Collectors.toList());
// 결과: [1, 2, 3]
```

📌 Stream.flatMap() 예시
```java
List<List<String>> nested = List.of(List.of("a", "b"), List.of("c"));

List<String> flat = nested.stream()
                          .flatMap(List::stream)
                          .collect(Collectors.toList());
// 결과: ["a", "b", "c"]
```

### 🔚 요약 정리

| 함수 이름              | 대상 구조       | 입력 함수 형태          | 결과 구조                | 사용 예                                     |
| ------------------ | ----------- | ----------------- | -------------------- | ---------------------------------------- |
| `Optional.map`     | Optional<T> | `T → U`           | Optional<U>          | `user.map(User::getName)`                |
| `Optional.flatMap` | Optional<T> | `T → Optional<U>` | Optional<U>          | `user.flatMap(User::getOptionalProfile)` |
| `Stream.map`       | Stream<T>   | `T → U`           | Stream<U>            | `stream.map(String::length)`             |
| `Stream.flatMap`   | Stream<T>   | `T → Stream<U>`   | Stream<U> (평탄화된 스트림) | `stream.flatMap(List::stream)`           |
