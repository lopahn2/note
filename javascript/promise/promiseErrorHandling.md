
# 자바스크립트 Promise와 에러 핸들링

## 🛑 프라미스 에러 처리

프라미스는 체인을 통해 에러를 쉽게 처리할 수 있습니다. `.catch()`는 어디에 위치해도 작동합니다.

```javascript
fetch('https://no-such-server.blabla')
  .then(response => response.json())
  .catch(err => alert(err));
```

---

## 🧠 JSON 파싱 에러 처리

```javascript
fetch('/user.json')
  .then(response => response.json())
  .catch(error => alert(error.message));
```

모든 에러를 마지막 `.catch()` 하나로 처리 가능합니다.

---

## 🔄 암시적 try...catch

프라미스 executor와 핸들러 안의 에러는 자동으로 catch에서 처리됩니다.

```javascript
new Promise((resolve, reject) => {
  throw new Error("에러 발생!");
}).catch(alert);
```

---

## 📛 핸들러 내 에러 처리

```javascript
new Promise(resolve => resolve("OK"))
  .then(() => {
    throw new Error("에러 발생!");
  })
  .catch(alert);
```

모든 예외는 자동으로 rejection으로 처리됨.

---

## 🔁 에러 다시 던지기

```javascript
new Promise(() => {
  throw new Error("에러 발생!");
})
.catch(error => {
  if (!(error instanceof URIError)) {
    alert("처리할 수 없는 에러");
    throw error;
  }
})
.catch(error => alert(`알 수 없는 에러 발생: ${error}`));
```

에러를 분석하고 필요 시 다시 던질 수 있습니다.

---

## ❌ 처리되지 않은 에러

```javascript
new Promise(() => {
  noSuchFunction(); // 존재하지 않음
});
```

.catch가 없으면 에러는 갇힘 → 실무에서 치명적

---

## 🌐 브라우저에서의 처리: unhandledrejection

```javascript
window.addEventListener('unhandledrejection', function(event) {
  alert(event.promise);
  alert(event.reason);
});

new Promise(() => {
  throw new Error("에러 발생!");
});
```

---

## ❓ 과제: setTimeout 내 에러

```javascript
new Promise(function(resolve, reject) {
  setTimeout(() => {
    throw new Error("에러 발생!");
  }, 1000);
}).catch(alert);
```

**답**: `.catch()`는 트리거되지 않습니다.  
setTimeout 내 코드는 프라미스 실행 시점 밖에서 실행되므로 프라미스는 이를 감지하지 못합니다.

# JavaScript Promise 내부 setTimeout에서 발생한 에러가 catch로 넘어가지 않는 이유

## 🔍 문제 코드

```js
new Promise(function(resolve, reject) {
  setTimeout(() => {
    throw new Error("에러 발생!");  // 이 에러는 catch로 가지 않음
  }, 1000);
}).catch(alert);
```

---

## 🤔 왜 `catch`로 넘어가지 않을까?

### ✅ 핵심 원인
`throw new Error("에러 발생!")`는 **`Promise` 실행 컨텍스트 바깥에서 발생**한 예외이기 때문입니다.

### 💡 상세 설명

1. `new Promise(function(resolve, reject) { ... })` 내의 함수는 **동기적으로 실행**됩니다.  
   즉, 이 콜백 함수 내부에서 발생하는 에러는 `Promise` 객체가 **reject 상태**로 전환될 수 있어요.

   예:
   ```js
   new Promise((resolve, reject) => {
     throw new Error("즉시 에러"); // -> catch로 넘어감
   }).catch(console.log);
   ```

2. 그러나 `setTimeout()` 안의 콜백은 **비동기적으로 실행**되며,  
   이 콜백은 **Promise 생성자 함수와는 다른 컨텍스트**에서 동작해요.

   그래서 이 콜백에서 `throw` 한 에러는 **해당 Promise의 reject로 전달되지 않고**,  
   **전역 에러(unhandled exception)**가 발생하게 됩니다.

---

## ✅ 해결 방법

### 1. `reject`로 명시적으로 에러 전달하기

```js
new Promise(function(resolve, reject) {
  setTimeout(() => {
    reject(new Error("에러 발생!"));  // 이제 catch로 넘어감
  }, 1000);
}).catch(alert);
```

### 2. 또는 try-catch로 감싸고 reject 호출

```js
new Promise(function(resolve, reject) {
  setTimeout(() => {
    try {
      throw new Error("에러 발생!");
    } catch (err) {
      reject(err);  // 명시적 reject
    }
  }, 1000);
}).catch(alert);
```

---

#### 🧠 정리

| 발생 위치 | `throw` 효과 | `catch()`로 감지됨? |
|-----------|--------------|----------------------|
| Promise 생성자 내부 (동기) | Promise를 reject로 만듦 | ✅ YES |
| `setTimeout` 등 비동기 콜백 내부 | 전역 에러 발생 | ❌ NO (직접 reject 해야 함) |

---

## 📝 요약

- `.catch()`는 모든 프라미스 에러를 잡음
- throw는 자동으로 rejection으로 변환
- `.catch()` 안에서 다시 throw로 전파 가능
- setTimeout 등 비동기 함수 내 에러는 프라미스가 잡지 못함
- 브라우저 환경에선 `unhandledrejection`으로 미처리 에러 감지 가능
