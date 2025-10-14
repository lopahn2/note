# Java 이모저모

## IS-A ( 상속 )

```java
class Animal {
    String name;

    void setName(String name) {
        this.name = name;
    }
}

class Dog extends Animal {
    void sleep() {
        System.out.println(this.name+" zzz");
    }
}

public class Sample {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.setName("poppy");
        System.out.println(dog.name);
        dog.sleep();
    }
}
```

Dog 클래스는 Animal 클래스를 상속했습니다.  
Dog는 Animal의 하위 개념, 즉 Dog `is-a` Animal 이라 표현할 수 있어 상속 관계를 표현할 때 자주 쓰는 표현입니다.  

이러한 경우, 자식 클래스의 객체는 부모 클래스의 객체처럼 사용할 수 있습니다.  

```java
Animal dog = new Dog();
dog.setName("바둑이"); // OK
dog.sleep(); // compile exception
```

단, 부모의 객체로서 자식 클래스를 사용하는 경우 부모 객체에 정의된 클래스만 사용할 수 있습니다.  

### 다중 상속

Java는 기본적으로 Class의 다중 상속을 지원하지 않습니다.  

```java
class A {
    public void msg() {
        System.out.println("A message");
    }
}

class B {
    public void msg() {
        System.out.println("B message");
    }
}

class C extends A, B { // X
    public void static main(String[] args) {
        C test = new C();
        test.msg();
    }
}
```
이 경우 `test.msg();`는 어떤 클래스의 메서드를 실행해야 할까요?  
모호하기 때문에 언어 측면에서 이를 허용하지 않습니다.  

하지만 인터페이스의 경우 다릅니다.  
메서드를 구현하지 않았기 때문에 선택할 수 있습니다.  

다중 상속을 하는 이유는 구현 클래스가 여러 Type을 가질 수 있게 하기 위함(다형성)입니다.  

```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// Bird 클래스는 Flyable, Swimmable 인터페이스 둘 다 구현
class Duck implements Flyable, Swimmable {
    @Override
    public void fly() {
        System.out.println("Duck is flying!");
    }

    @Override
    public void swim() {
        System.out.println("Duck is swimming!");
    }
}

public class Main {
    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.fly();   // Duck is flying!
        duck.swim();  // Duck is swimming!

        // 다형성: Flyable 타입 변수로 Duck 객체 참조
        Flyable flyer = duck;
        flyer.fly();

        // 다형성: Swimmable 타입 변수로 Duck 객체 참조
        Swimmable swimmer = duck;
        swimmer.swim();
    }
}
```

## Interface

Interface가 없다면, 클래스의 종류만큼 메서드가 필요했을 것입니다.  
ex) 하늘을 날 줄 아는 오리, 수영을 할 줄 아는 오리, ...

Interface를 사용함으로써 단 한 개의 메서드로 구현이 가능했고 다형성도 가질 수 있게 됐습니다.  

여기서 중요한 점은 메서드의 개수가 준 것이 아니라, **클래스가 다른 클래스에 의존적인 상태에서, 독립적인 클래스가 됐다는 점** 입니다.

> 상속은요?  
> 
> 상속으로도, 동일한 효과를 얻을 수 있습니다.  
하지만 자식 클래스가 부모 클래스의 메서드를 Override하도록 강제하지 않기 때문에,  
해당 메서드를 반드시 구현해야 한다는 강제성을 갖지 못한다는 차이점이 있습니다. 

### Default Method

Interface는 기본적으로는 구현 메서드를 가질 수 없지만, 자바 8 버전 이후로 `default method` 를 사용할 수 있습니다.  

```java
interface Predator {
    String getFood();

    default void printFood() {
        System.out.printf("my food is %s\n", getFood());
    }
}
```

이후 인터페이스를 구현하는 클래스에서, printFood 메서드를 재정의하지 않고 사용할 수 있습니다.  

다만, 다중 상속에서 발생하는 문제와 동일하게, 공통 메서드가 중복되는 경우가 발생할 수 있기 때문에  
공통 로직을 넣을 필요가 없다면, `abstract class`를 사용하는 것이 더 적절할 수 있습니다.  

하지만, 공통적으로 사용되는 유틸성 기능을 인터페이스에서 제공할 때 유용하게 사용될 수 있습니다.  
ex) `List.sort(Comparator)`  

## Abstract Class

추상 클래스는 최소 하나의 추상 메서드를 가지는 클래스입니다.  
추상 클래스의 abstract method는 반드시 상속받는 클래스에서 구현해야 하며,  
추상 클래스를 단독으로 new 키워드로 생성해 사용할 수 없으며 이를 상속한 실제 클래스를 통해서만 객체를 생성할 수 있습니다.

```java
// 추상 클래스
abstract class Animal {
    // 추상 메서드 (몸체 없음)
    public abstract void makeSound();

    // 일반 메서드 (공통 구현 제공)
    public void sleep() {
        System.out.println("Sleeping...");
    }
}

// Dog 클래스는 Animal을 상속하여 makeSound를 구현해야 함
class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark!");
    }
}

// Cat 클래스도 마찬가지
class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal dog = new Dog();
        dog.makeSound(); // Bark!
        dog.sleep();     // Sleeping...

        Animal cat = new Cat();
        cat.makeSound(); // Meow!
        cat.sleep();     // Sleeping...
    }
}
```

## 접근 제어자

### 자바 접근 제어자(Access Modifiers) 비교표

| 접근 제어자 | 같은 클래스 | 같은 패키지 | 자식 클래스 | 전체 |
|------------|------------|------------|------------|------|
| public     | O          | O          | O          | O    |
| protected  | O          | O          | O          | X    |
| default    | O          | O          | X          | X    |
| private    | O          | X          | X          | X    |

## Exception

Exception과 RuntimeException이 있습니다.  

Exception: Compile 과정에서 발생하는 예외
RuntimeException: 실행 시 발생하는 예외

그렇기 때문에 Exception을 Checked Exception, RuntimeException을 Unchecked Exception 이라고도 표현합니다.  

### Checked Exception

컴파일 오류를 막기 위해선 try~catch 구문으로 감싸서 예외 핸들링을 하거나,  
호출한 곳에서 예외를 처리하도록 `throws` 할 수 있습니다.  

따라서 무분별하게 에러를 상위 호출 스택으로 떠넘기는 것은 지양해야 하며 최대한 예외가 발생할 수 있는 지점에서 처리해주는 것이 좋습니다.  
