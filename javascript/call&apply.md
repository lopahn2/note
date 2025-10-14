# call/apply와 데코레이터, 포워딩

## 1. 개요
자바스크립트는 함수를 유연하게 다룰 수 있어, 전달, 호출, 데코레이팅이 가능합니다.

## 2. 캐싱 데코레이터 (cachingDecorator)

### 기본 캐싱 함수 예시
```js
function slow(x) {
  alert(`slow(${x}) 호출됨`);
  return x;
}

function cachingDecorator(func) {
  let cache = new Map();
  return function(x) {
    if (cache.has(x)) return cache.get(x);
    let result = func(x);
    cache.set(x, result);
    return result;
  };
}

slow = cachingDecorator(slow);
```

### 장점
- 코드 재사용
- 코드 분리
- 다른 데코레이터와 조합 가능

## 3. call을 이용한 컨텍스트 지정

```js
func.call(context, arg1, arg2, ...)
```

### 예시
```js
function sayHi() { alert(this.name); }

let user = { name: "John" };
sayHi.call(user); // John
```

## 4. 객체 메서드와 this 유지
```js
function cachingDecorator(func) {
  let cache = new Map();
  return function(x) {
    if (cache.has(x)) return cache.get(x);
    let result = func.call(this, x);
    cache.set(x, result);
    return result;
  };
}
```

## 5. 여러 인수 처리

```js
function cachingDecorator(func, hash) {
  let cache = new Map();
  return function() {
    let key = hash(arguments);
    if (cache.has(key)) return cache.get(key);
    let result = func.call(this, ...arguments);
    cache.set(key, result);
    return result;
  };
}

function hash(args) {
  return [].join.call(args);
}
```

## 6. apply를 이용한 콜 포워딩
```js
function wrapper() {
  return original.apply(this, arguments);
}
```

## 7. 메서드 빌리기
```js
[].join.call(arguments);
```

## 8. 데코레이터와 함수 프로퍼티
- `func.calledCount`와 같은 프로퍼티는 데코레이터 후 래퍼에선 사라질 수 있음.
- 해결 방법: Proxy 사용

---

# 과제 요약

## Spy decorator
```js
function spy(func) {
  function wrapper(...args) {
    wrapper.calls.push(args);
    return func.apply(this, args);
  }
  wrapper.calls = [];
  return wrapper;
}
```

## Delay decorator
```js
function delay(f, ms) {
  return function(...args) {
    setTimeout(() => f.apply(this, args), ms);
  };
}
```

## Debounce decorator
```js
function debounce(func, ms) {
  let timeout;
  return function(...args) {
    clearTimeout(timeout);
    timeout = setTimeout(() => func.apply(this, args), ms);
  };
}
```

## Throttle decorator
```js
function throttle(func, ms) {
  let isThrottled = false, savedArgs, savedThis;

  function wrapper(...args) {
    if (isThrottled) {
      savedArgs = args;
      savedThis = this;
      return;
    }

    func.apply(this, args);
    isThrottled = true;

    setTimeout(() => {
      isThrottled = false;
      if (savedArgs) {
        wrapper.apply(savedThis, savedArgs);
        savedArgs = savedThis = null;
      }
    }, ms);
  }

  return wrapper;
}
```
