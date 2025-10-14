
# 🔁 JavaScript 제너레이터 완전 정리

제너레이터는 **값을 하나씩 생성하며 실행을 잠시 멈췄다 다시 시작할 수 있는 함수**입니다. 이터러블 객체를 쉽게 만들 수 있는 강력한 기능이에요.

---

## 1. 제너레이터 함수

```js
function* generateSequence() {
  yield 1;
  yield 2;
  return 3;
}
```

- `function*` 키워드를 사용해 제너레이터 함수 생성
- `yield`를 사용해 값을 하나씩 반환
- `.next()`로 실행 제어

---

## 2. next()의 반환값

```js
let generator = generateSequence();
console.log(generator.next()); // { value: 1, done: false }
console.log(generator.next()); // { value: 2, done: false }
console.log(generator.next()); // { value: 3, done: true }
```

---

## 3. 제너레이터는 이터러블

```js
for (let value of generateSequence()) {
  console.log(value); // 1, 2만 출력됨 (return 값은 무시됨)
}
```

- `return` 값은 for..of에서 무시됨

---

## 4. 전개 구문

```js
let sequence = [0, ...generateSequence()];
console.log(sequence); // [0, 1, 2, 3]
```

---

## 5. 이터러블 객체를 제너레이터로 간단히 구현

```js
let range = {
  from: 1,
  to: 5,
  *[Symbol.iterator]() {
    for (let i = this.from; i <= this.to; i++) yield i;
  }
};
console.log([...range]); // [1, 2, 3, 4, 5]
```

---

## 6. 무한 제너레이터

```js
function* infinite() {
  let i = 0;
  while (true) yield i++;
}
```

- `for..of` 사용할 땐 `break` 필수!

---

## 7. 제너레이터 컴포지션

```js
function* generateSequence(start, end) {
  for (let i = start; i <= end; i++) yield i;
}

function* generatePasswordCodes() {
  yield* generateSequence(48, 57);
  yield* generateSequence(65, 90);
  yield* generateSequence(97, 122);
}

let str = '';
for (let code of generatePasswordCodes()) {
  str += String.fromCharCode(code);
}
console.log(str); // "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
```

---

## 8. yield를 통한 양방향 통신

```js
function* gen() {
  let result = yield "2 + 2 = ?";
  console.log(result); // 4
}

let generator = gen();
console.log(generator.next().value); // "2 + 2 = ?"
generator.next(4); // yield의 결과로 4 전달됨
```

---

## 9. generator.throw

```js
function* gen() {
  try {
    let result = yield "2 + 2 = ?";
  } catch (e) {
    console.log("에러:", e);
  }
}

let generator = gen();
generator.next();
generator.throw(new Error("답을 찾지 못했습니다"));
```

---

## ✅ 요약

| 기능 | 설명 |
|------|------|
| `function*` | 제너레이터 함수 선언 |
| `yield` | 값을 외부로 산출하고 중단 |
| `next()` | 실행 재개 |
| `yield*` | 다른 제너레이터 위임 |
| `.throw()` | 에러 전달 |
| `for..of` | 이터러블처럼 반복 가능 |

---

## 🔄 과제: 의사 난수 생성기

```js
function* pseudoRandom(seed) {
  let value = seed;
  while (true) {
    value = value * 16807 % 2147483647;
    yield value;
  }
}

let generator = pseudoRandom(1);
console.log(generator.next().value); // 16807
console.log(generator.next().value); // 282475249
console.log(generator.next().value); // 1622650073
```

제너레이터는 실제 개발에서 자주 쓰이진 않지만, **이터러블 처리나 비동기 스트림 처리에 매우 유용한 도구**예요!
