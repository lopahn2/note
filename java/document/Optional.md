# Java Optional

## Optional이 필요한 이유와 역할

- Java에서 `null`을 반환하면 이후 NPE(NullPointerException)의 위험이 있다.
- `Optional`은 **메서드의 반환값이 없을 수도 있음을 명시적으로 표현**할 수 있는 방법이다.

> “Optional is primarily intended for use as a method return type where there is a clear need to represent ‘no result,’ and where using null is likely to cause errors.”

## Optional의 기본 사용법

### 생성

```java
Optional<String> opt1 = Optional.of("value");         // null 허용 안함
Optional<String> opt2 = Optional.ofNullable(null);    // null 허용
```

### 값 꺼내기

```java
String val = opt1.get(); // 비어있으면 NoSuchElementException 발생
```

### 안전한 값 처리

```java
opt1.ifPresent(val -> System.out.println(val));              // 값이 있을 경우만 실행
String val = opt1.orElse("default");                         // 값이 없으면 기본값
String val = opt1.orElseGet(() -> "generatedDefault");       // 지연 생성
String val = opt1.orElseThrow(() -> new RuntimeException()); // 예외 던지기
```

### 값 존재 여부 확인

```java
opt1.isPresent(); // true면 값 있음
opt1.isEmpty();   // Java 11부터 지원
```

## Optional 바르게 사용하기

### 1. isPresent() + get() 대신 orElse(), orElseGet(), orElseThrow() 사용

```java
// Bad
if (opt.isPresent()) return opt.get();
else return null;

// Good
return opt.orElse(null);
```

### 2. orElse(new ...) 대신 orElseGet(() -> new ...)

```java
// Bad: new Member()는 무조건 실행됨
return opt.orElse(new Member());

// Good: 값이 없을 때만 실행됨
return opt.orElseGet(Member::new);
```

### 3. 단순 null 대체 목적이면 Optional 대신 삼항 연산자 사용

```java
// Bad
return Optional.ofNullable(status).orElse(READY);

// Good
return status != null ? status : READY;
```

### 4. Optional로 컬렉션 감싸지 말고 비어있는 컬렉션 반환

```java
// Bad
return Optional.ofNullable(members);

// Good
return members != null ? members : Collections.emptyList();
```

### 5. Optional을 필드로 사용 금지

```java
// Bad
private Optional<String> email = Optional.empty();

// Good
private String email;
```

### 6. Optional을 생성자/메서드 인자로 사용 금지

```java
// Bad
public void setInfo(Optional<Member> member)

// Good
public void setInfo(Member member)
```

### 7. Optional을 컬렉션의 원소로 사용 금지

> Optional은 비싸고 불필요한 오버헤드를 유발할 수 있다. Optional 대신 null을 처리하는 방식이 더 적절하다.
