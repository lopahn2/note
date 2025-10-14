# new Function 문법

자바스크립트에서 `new Function` 문법은 함수 표현식과 함수 선언문 이외에 **동적으로 함수를 생성**할 수 있는 방법입니다.

이 문법은 일반적으로 자주 사용되진 않지만, **런타임에 문자열을 기반으로 함수를 생성해야 할 때** 유용하게 사용됩니다.

---

## 📌 문법

```js
let func = new Function ([arg1, arg2, ...argN], functionBody);
```

- `arg1, arg2, ...argN`: 함수의 매개변수
- `functionBody`: 함수 본문 (문자열)

### 예시

```js
let sum = new Function('a', 'b', 'return a + b');
alert(sum(1, 2)); // 3
```

```js
let sayHi = new Function('alert("Hello")');
sayHi(); // Hello
```

---

## 🔍 특징

### ✅ 문자열로 함수 생성

기존 함수 선언 방식은 스크립트 작성 시점에 정의가 필요하지만, `new Function`은 **문자열 기반으로 런타임 중 동적으로 함수 생성**이 가능합니다.

```js
let code = "alert('동적으로 실행된 코드!')";
let func = new Function(code);
func();
```

---

## 📦 클로저와 new Function

### 일반 함수의 클로저

```js
function getFunc() {
  let value = "test";
  return function() { alert(value); };
}

getFunc()(); // "test"
```

> ✅ 함수는 자신이 만들어진 **렉시컬 환경**을 기억합니다.

---

### new Function의 클로저 동작

```js
function getFunc() {
  let value = "test";
  return new Function('alert(value)');
}

getFunc()(); // ❌ ReferenceError: value is not defined
```

> ⚠️ `new Function`은 외부 렉시컬 환경을 무시하고 **전역 렉시컬 환경**만 참조합니다.

---

## ⚠️ 압축기(Minifier) 문제

```js
function getUserNamePrinter() {
  let userName = "철수";
  return new Function('alert(userName)');
}
```

압축기를 사용하면 `userName`이 `a` 등으로 변경되며, **new Function에서의 참조가 무효화**됩니다.

> ✅ 매개변수를 이용한 값 전달을 권장

```js
let func = new Function('name', 'alert(name)');
func('철수'); // "철수"
```

---

## ✅ 요약

- `new Function`은 런타임에 문자열을 받아 **함수를 생성**할 수 있는 유일한 방법입니다.
- 만들어진 함수는 **전역 스코프**를 기준으로 동작하며 외부 변수(렉시컬 환경)에 접근할 수 없습니다.
- 매개변수로 값을 전달해야 하며, **압축기로 인한 에러 방지**에도 효과적입니다.
- 문자열 기반 코드 실행이 필요하거나, 서버로부터 받은 스크립트를 처리할 때 유용합니다.

---

## 📚 같은 의미의 선언

```js
new Function('a', 'b', 'return a + b');
new Function('a,b', 'return a + b');
new Function('a , b', 'return a + b');
```

---

## 🧠 실전 Tip

- 사용자 입력이나 외부 API에서 받은 문자열을 실행해야 할 때 사용
- 서버에서 템플릿 코드를 받아 뷰를 동적으로 생성할 때 유용
- 보안 이슈에 주의할 것: 외부 입력값을 바로 실행하는 경우 **XSS** 공격 가능성 있음

---
