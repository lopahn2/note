
# Java Functional Interfaces 정리

## Functional Interface란?

- Java 8에서 도입된 개념으로, **오직 하나의 추상 메서드**만 가지는 인터페이스를 말합니다.  
- 함수형 프로그래밍 스타일을 Java에서 사용할 수 있게 해줍니다 (람다식 사용).
- 인터페이스에 `@FunctionalInterface` 애노테이션을 붙이면, 하나의 추상 메서드만 존재해야 함을 컴파일러가 체크해줍니다.

## @FunctionalInterface 애노테이션

- 컴파일 타임에 해당 인터페이스가 함수형 인터페이스 조건을 만족하는지 확인해줍니다.
- 명시적으로 이 인터페이스가 람다 표현식이나 메서드 참조에 사용될 수 있다는 것을 표현합니다.

---

## Supplier

### 역할

- **입력값 없이** 어떤 값을 제공해주는 함수형 인터페이스.
- 주로 **지연 평가**, **값 생성**, **스트림 값 생성기** 등에 사용됨.

### 메서드

- `T get()` : 값을 반환. 매 호출마다 새 값을 생성하거나 같은 값을 반환할 수 있음.

### 사용 예시

```java
Supplier<Double> lazyValue = () -> Math.random();
Double result = lazyValue.get();
```

---

## Consumer

### 역할

- 입력값을 받아서 **어떤 동작을 수행**하고, 값을 반환하지 않는 함수형 인터페이스.
- 주로 **로깅**, **출력**, **콜백 처리** 등에 사용.

### 메서드

- `void accept(T t)` : 입력값을 받아 동작 수행.
- `Consumer<T> andThen(Consumer<? super T> after)` : Consumer들을 순차적으로 연결 가능.

### 사용 예시

```java
Consumer<String> printer = s -> System.out.println(s);
printer.accept("Hello");

Consumer<String> printerWithPrefix = printer.andThen(s -> System.out.println("Logged: " + s));
printerWithPrefix.accept("World");

// Hello          // 첫 번째 호출
// World          // printerWithPrefix의 첫 번째 Consumer
// Logged: World  // printerWithPrefix의 두 번째 Consumer

```

---

## Function

### 역할

- 하나의 입력값을 받아 **다른 값을 반환**하는 함수형 인터페이스.
- 주로 **데이터 변환**, **매핑**, **계산 로직**에 사용됨.

### 메서드

- `R apply(T t)` : 입력값을 받아 결과 반환.
- `Function<V, R> compose(Function<? super V, ? extends T> before)` : 먼저 before 함수를 적용하고 이후에 현재 함수 적용.
- `Function<T, V> andThen(Function<? super R, ? extends V> after)` : 현재 함수 적용 후 after 함수 적용.

### 사용 예시

```java
Function<Integer, String> intToString = Object::toString;
Function<String, String> quote = s -> "'" + s + "'";
Function<Integer, String> quoteIntToString = quote.compose(intToString);
System.out.println(quoteIntToString.apply(5)); // 출력: '5'
```

---

## Predicate

### 역할

- 하나의 값을 받아 **boolean 결과**를 반환하는 함수형 인터페이스.
- 주로 **조건 검사**, **필터링**, **유효성 검사**에 사용됨.

### 메서드

- `boolean test(T t)` : 입력값이 조건을 만족하는지 확인.
- `Predicate<T> and(Predicate<? super T> other)` : 두 조건을 모두 만족하는지 확인.
- `Predicate<T> or(Predicate<? super T> other)` : 하나라도 만족하면 true.
- `Predicate<T> negate()` : 조건 부정.

### 사용 예시

```java
Predicate<String> isNotEmpty = s -> !s.isEmpty();
Predicate<String> isLongEnough = s -> s.length() > 3;

Predicate<String> validString = isNotEmpty.and(isLongEnough);
System.out.println(validString.test("Hi")); // false
System.out.println(validString.test("Hello")); // true
```

---

## 요약

| 인터페이스 | 입력 | 출력 | 주요 메서드 | 용도 |
|------------|------|------|-------------|------|
| Supplier   | 없음 | 있음 | get()       | 값 생성, 지연 평가 |
| Consumer   | 있음 | 없음 | accept()    | 소비, 출력, 로깅 |
| Function   | 있음 | 있음 | apply()     | 변환, 매핑 |
| Predicate  | 있음 | boolean | test()    | 조건 판별, 필터링 |
