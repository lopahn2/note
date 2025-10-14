

# 자바스크립트 Promise 심화 학습

## 🎤 실생활 비유로 이해하는 Promise

**가수 = 비동기 작업 수행자 (제작 코드)**  
**팬 = 결과를 기다리는 코드 (소비 코드)**  
**구독 리스트 = Promise 객체**

앨범이 언제 나올지 팬들이 묻는 대신, 가수가 리스트에 등록된 팬들에게 자동으로 알림을 보냄 → 이게 Promise입니다.

---

## 🔧 Promise 기본 문법

```javascript
let promise = new Promise(function(resolve, reject) {
  // 비동기 작업
});
```

- **resolve(value)**: 성공 결과 전달
- **reject(error)**: 에러 전달

---

## 🔄 상태 관리

Promise 객체는 `state`와 `result`라는 내부 상태를 가집니다.

- `pending` → `fulfilled` (resolve 호출 시)
- `pending` → `rejected` (reject 호출 시)

```javascript
let promise = new Promise((resolve, reject) => {
  resolve("완료");
  reject(new Error("무시됨")); // 무시됨
});
```

---

## ⌛ 예시: setTimeout과 함께 사용

```javascript
let promise = new Promise(function(resolve, reject) {
  setTimeout(() => resolve("완료"), 1000);
});
```

---

## ⚠️ 주의사항

- resolve/reject는 **단 한 번만** 호출 가능
- 이후 호출은 모두 무시됨

---

## ✅ 즉시 처리도 가능

```javascript
let promise = new Promise(resolve => resolve(123));
```

이미 데이터가 있다면, 즉시 resolve로 처리할 수 있습니다.

---

## 🔁 소비자 함수

### .then

```javascript
promise.then(
  result => alert(result),
  error => alert(error)
);
```

### .catch

```javascript
promise.catch(error => alert(error));
```

### .finally

```javascript
promise
  .finally(() => alert("프라미스가 준비되었습니다."))
  .then(result => alert(result))
  .catch(error => alert(error));
```

- `finally`는 결과나 에러를 전달받지 않음
- 대신 다음 핸들러로 값 또는 에러를 그대로 전달함

---

## 📦 활용 예제: loadScript

콜백 방식:

```javascript
function loadScript(src, callback) {
  let script = document.createElement('script');
  script.src = src;
  script.onload = () => callback(null, script);
  script.onerror = () => callback(new Error(`${src} 로딩 실패`));
  document.head.append(script);
}
```

프라미스 방식:

```javascript
function loadScript(src) {
  return new Promise((resolve, reject) => {
    let script = document.createElement('script');
    script.src = src;
    script.onload = () => resolve(script);
    script.onerror = () => reject(new Error(`${src} 로딩 실패`));
    document.head.append(script);
  });
}
```

---

## 🔄 두 번 resolve?

```javascript
let promise = new Promise(function(resolve, reject) {
  resolve(1);
  setTimeout(() => resolve(2), 1000);
});

promise.then(alert); // 출력: 1
```

---

## ⏱️ delay 함수 만들기

```javascript
function delay(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

delay(3000).then(() => alert('3초 후 실행'));
```

---

Promise는 **비동기 흐름을 자연스럽고 유연하게 관리**할 수 있게 해주는 강력한 도구입니다.
