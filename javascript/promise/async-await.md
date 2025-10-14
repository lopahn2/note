
# ⏳ JavaScript async/await 완전 정복

`async`와 `await` 키워드는 비동기 코드를 작성할 때 **더 깔끔하고 직관적으로** 만들어 줍니다. 아래에서 초보자도 쉽게 이해할 수 있도록 하나하나 짚어볼게요.

---

## 1. async 함수란?

```js
async function f() {
  return 1;
}
```

- `async`가 붙은 함수는 항상 **프라미스를 반환**해요.
- `return 1` 처럼 일반 값을 반환해도, 자동으로 `Promise.resolve(1)`으로 바뀝니다.

---

## 2. await 키워드

```js
async function f() {
  let result = await somePromise;
}
```

- `await`는 **프라미스가 처리될 때까지 기다림**
- **async 함수 안에서만 사용 가능**
- 코드가 마치 동기처럼 읽히게 해줘요!

예시:
```js
async function f() {
  let promise = new Promise(resolve => setTimeout(() => resolve("완료!"), 1000));
  let result = await promise;
  alert(result); // 1초 후 "완료!"
}
```

---

## 3. 최상위 await 금지

```js
let response = await fetch(url); // ❌ Error
```

- `await`는 **async 함수 안**이 아니면 쓸 수 없어요.
- 해결 방법: **익명 async 함수로 감싸기**
```js
(async () => {
  let response = await fetch(url);
})();
```

---

## 4. await는 thenable도 기다려요

```js
class Thenable {
  constructor(num) { this.num = num; }
  then(resolve, reject) {
    setTimeout(() => resolve(this.num * 2), 1000);
  }
}

async function f() {
  let result = await new Thenable(1);
  alert(result); // 2
}
```

---

## 5. async 클래스 메서드

```js
class Waiter {
  async wait() {
    return await Promise.resolve(1);
  }
}
new Waiter().wait().then(alert); // 1
```

---

## 6. 에러 핸들링

- `await`에서 프라미스가 **거부되면** 에러가 **throw** 된 것처럼 동작함
- `try..catch`로 처리 가능

```js
async function f() {
  try {
    let response = await fetch("잘못된주소");
  } catch(err) {
    alert(err);
  }
}
```

---

## 7. .catch()와 async 함수

```js
async function f() {
  let response = await fetch("잘못된주소");
}

f().catch(alert); // 외부에서 에러 처리
```

---

## 8. async/await + Promise.all

```js
let results = await Promise.all([
  fetch(url1),
  fetch(url2)
]);
```

- `Promise.all`을 await와 함께 쓰면, **여러 작업을 병렬로 처리**하고 **결과를 기다릴 수 있어요**

---

## 9. 예제: loadJson

```js
async function loadJson(url) {
  let response = await fetch(url);
  if (response.status == 200) {
    return await response.json();
  }
  throw new Error(response.status);
}
```

---

## 10. 예제: 재귀 대신 반복

```js
async function demoGithubUser() {
  while (true) {
    let name = prompt("GitHub username을 입력하세요.", "iliakan");

    try {
      let user = await loadJson(`https://api.github.com/users/${name}`);
      alert(`이름: ${user.name}`);
      return user;
    } catch (err) {
      if (err instanceof HttpError && err.response.status == 404) {
        alert("일치하는 사용자가 없습니다. 다시 입력해주세요.");
      } else {
        throw err;
      }
    }
  }
}
```

---

## 11. 일반 함수에서 async 함수 호출하기

```js
async function wait() {
  await new Promise(resolve => setTimeout(resolve, 1000));
  return 10;
}

function f() {
  wait().then(result => alert(result)); // 1초 후 10 출력
}
```

---

## ✅ 요약

| 개념 | 설명 |
|------|------|
| `async` | 항상 프라미스를 반환 |
| `await` | 프라미스가 처리될 때까지 기다림 |
| `try..catch` | await 에러 핸들링 가능 |
| `Promise.all + await` | 병렬 처리 후 한번에 기다리기 |
| 일반 함수에서 async 호출 | `.then()` 사용 |

async/await는 **가독성 좋고 실수 줄일 수 있는 강력한 도구**입니다. 익숙해지면 프라미스를 다루는 게 훨씬 쉬워질 거예요!
