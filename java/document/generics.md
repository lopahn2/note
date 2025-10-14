# Generics Type

제너릭 타입은 타입에 대한 매개변수화를 지원하는 클래스나 인터페이스를 의미합니다.  

## Simple Box Class

```java
public class Box {
    private Object object;

    public void set(Object object) { this.object = object; }
    public Object get() { return object; }
}
```

위 소스 코드의 문제점은, 컴파일 시 타입 검사가 불가능 한 점이 문제입니다.  

### Generic Version

```java
/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 */
public class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}
```

`T` 는 사용자가 지정하는 임의의 참조형 타입이며 컴파일 시 타입 검사가 가능해집니다.  

#### Type Parameter Naming Convention

`E` - Element (주로 Java 컬렉션 프레임워크에서 사용)

`K` - Key

`N` - Number

`T` - Type

`V` - Value

`S`, `U`, `V`, ... - 2번째, 3번째, 4번째 타입

### Usage

```java
Box<Integer> box = new Box<>();
```

## Multiple Type Parameters

```java
public interface Pair<K, V> {
    public K getKey();
    public V getValue();
}

public class OrderedPair<K, V> implements Pair<K, V> {
    private K key;
    private V value;

    public OrderedPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey()   { return key; }
    public V getValue() { return value; }
}
```

### Usage

```java
OrderedPair<String, Box<Integer>> p = new OrderedPair<>("primes", new Box<Integer>(...));
```

## ArrayList 예시

```java
ArrayList pitches = new ArrayList();
pitches.add("138");
pitches.add("129");

String one = (String) pitches.get(0);
String two = (String) pitches.get(1);
```

제너릭스를 사용하지 않으면, ArrayList에 추가하는 객체는 모두 Object로 인식됩니다.  
그렇기 때문에 get을 해올 때 계속 Casting을 해주어야 합니다.  

만약 String 이외의 다른 객체로 ArrayList가 변경된 경우에 Casting Exception이 발생합니다.  
제너릭스는 이러한 형 변환 오류를 방지하기 위해 탄생했습니다.  

```java
ArrayList<String> pitches = new ArrayList<>();
pitches.add("138");
pitches.add("129");

String one = pitches.get(0);  // 형 변환이 필요없다.
String two = pitches.get(1);  // 형 변환이 필요없다.

```

### 출처

[oracle java tutorial](https://docs.oracle.com/javase/tutorial/java/generics/)
