
# 🧠 자바스크립트 마이크로태스크와 프라미스 핸들러

프라미스 `.then()`, `.catch()`, `.finally()`는 **언제나 비동기적으로 실행**됩니다. 이건 **프라미스가 이미 준비되어 있어도 마찬가지**예요.

---

## 1. 프라미스 핸들러는 왜 나중에 실행될까요?

프라미스가 성공해도, 그 핸들러는 바로 실행되지 않고 **'마이크로태스크 큐'에 등록**됩니다.

```js
let promise = Promise.resolve();

promise.then(() => alert("프라미스 성공!"));

alert("코드 종료"); // 이게 먼저 실행됨
```

출력 순서:
```
코드 종료
프라미스 성공!
```

---

## 2. 마이크로태스크 큐란?

- 내부적으로 프라미스 핸들러는 **PromiseJobs** 또는 **마이크로태스크 큐**에 들어감
- 현재 실행 중인 코드가 **완전히 종료된 후**에 마이크로태스크가 처리됨
- FIFO(선입선출) 방식으로 실행됨

---

## 3. 순서 보장이 필요한 경우

프라미스 체인으로 순서를 명확히 해야 할 땐 `.then()`을 연속으로 작성하면 돼요:

```js
Promise.resolve()
  .then(() => alert("프라미스 성공!"))
  .then(() => alert("코드 종료"));
```

---

## 4. 처리되지 않은 거부 (Unhandled Rejection)

프라미스 에러가 `.catch()` 없이 끝나면, 엔진은 `unhandledrejection` 이벤트를 발생시켜요.

```js
let promise = Promise.reject(new Error("프라미스 실패!"));

window.addEventListener('unhandledrejection', event => alert(event.reason));
```

출력:
```
프라미스 실패!
```

.catch를 나중에 달면?

```js
let promise = Promise.reject(new Error("프라미스 실패!"));
setTimeout(() => promise.catch(err => alert("잡았다!")), 1000);
```

출력:
```
프라미스 실패!
잡았다!
```

❗ `unhandledrejection`은 **마이크로태스크 큐가 끝났을 때** 판단되므로, `setTimeout`으로 나중에 .catch를 달아도 소용 없어요.

---

## ✅ 요약

| 내용 | 설명 |
|------|------|
| 마이크로태스크 큐 | 프라미스 핸들러가 대기하는 큐, 현재 코드 종료 후 실행 |
| 실행 순서 | 프라미스 핸들러는 항상 나중에 실행됨 |
| unhandledrejection | 에러를 .catch로 잡지 않으면 마이크로태스크 큐 끝에서 실행됨 |

📌 프라미스 코드는 항상 비동기적으로 작동하므로, 순서를 의도한 대로 컨트롤하려면 `.then()` 체인 사용이 중요해요!
