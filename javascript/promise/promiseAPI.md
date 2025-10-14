
# 📘 JavaScript Promise API 요약 및 설명

초보 개발자분들을 위해, 자바스크립트의 `Promise` 클래스에서 제공하는 5가지 정적 메서드를 쉽게 설명해볼게요. 이건 특히 비동기 처리를 효율적으로 다루는 데 필수적인 지식이에요.

---

## 1. Promise.all

### 🔍 어떤 상황에서 사용하나요?
- 여러 개의 비동기 작업을 **동시에 실행**하고, **모두 완료되었을 때** 다음 작업을 하고 싶을 때 사용해요.
- 예: 여러 개의 파일을 동시에 다운로드하고, 다 받아졌을 때 처리할 때.

### 📌 주의할 점
- 전달한 프라미스 중 하나라도 실패하면 전체가 실패로 간주돼요.
- **순서 보장**: 먼저 끝난 프라미스가 있어도, 결과 배열은 입력된 순서와 같아요.

### 📎 코드 예시
```js
Promise.all([
  new Promise(resolve => setTimeout(() => resolve(1), 3000)),
  new Promise(resolve => setTimeout(() => resolve(2), 2000)),
  new Promise(resolve => setTimeout(() => resolve(3), 1000))
]).then(alert); // 결과: [1, 2, 3]
```

---

## 2. Promise.allSettled

### 🔍 어떤 상황에서 사용하나요?
- **모든 프라미스의 상태**가 필요할 때, 실패 여부와 관계없이 각 결과를 보고 싶을 때 사용해요.

### 📌 주의할 점
- 성공/실패 여부를 객체로 구분해서 반환해요.

### 📎 코드 예시
```js
Promise.allSettled([
  fetch('/good-url'),
  fetch('/bad-url')
]).then(results => {
  results.forEach(result => {
    if (result.status === 'fulfilled') {
      console.log("성공:", result.value);
    } else {
      console.log("실패:", result.reason);
    }
  });
});
```

---

## 3. Promise.race

### 🔍 어떤 상황에서 사용하나요?
- 여러 비동기 중 **가장 먼저 완료된 것만** 필요할 때 사용해요.

### 📌 주의할 점
- 가장 먼저 완료된 프라미스 하나만 결과로 삼고, 나머지는 무시돼요.

### 📎 코드 예시
```js
Promise.race([
  new Promise(resolve => setTimeout(() => resolve("첫 번째"), 1000)),
  new Promise(resolve => setTimeout(() => resolve("두 번째"), 2000))
]).then(console.log); // 출력: "첫 번째"
```

---

## 4. Promise.resolve

### 🔍 어떤 상황에서 사용하나요?
- 이미 알고 있는 값을 프라미스로 감싸서 반환하고 싶을 때 사용해요.
- 예: 캐시된 데이터를 프라미스로 감싸 비동기처럼 다룰 때.

### 📎 코드 예시
```js
let cache = new Map();

function loadCached(url) {
  if (cache.has(url)) {
    return Promise.resolve(cache.get(url));
  }
  return fetch(url).then(res => res.text()).then(text => {
    cache.set(url, text);
    return text;
  });
}
```

---

## 5. Promise.reject

### 🔍 어떤 상황에서 사용하나요?
- 강제로 실패 상태의 프라미스를 만들고 싶을 때 사용해요.
- 사용빈도는 낮지만, 테스트나 에러 시뮬레이션 등에 사용돼요.

### 📎 코드 예시
```js
Promise.reject(new Error("문제가 발생했어요!"))
  .catch(err => console.error(err));
```

---

## 💡 초보자가 실수할 수 있는 포인트
- **Promise.all 중 하나라도 실패하면 전체가 실패**해요. `.catch()`는 꼭 써줘야 해요.
- **fetch는 네트워크 성공만으로는 성공으로 간주**, 404 같은 HTTP 에러도 `.then`으로 들어와서 확인 필요해요.
- `Promise.allSettled`는 상태 구분(`fulfilled`, `rejected`)을 꼭 체크해야 해요.

---

## ✅ 결론
| 메서드 | 설명 | 실패 처리 |
|--------|------|------------|
| Promise.all | 모두 성공해야 이행 | 하나라도 실패하면 거부됨 |
| Promise.allSettled | 각각의 상태 반환 | 항상 이행, 실패 포함 |
| Promise.race | 가장 빨리 끝난 것 반환 | 빠른 실패가 전체 실패로 간주될 수 있음 |
| Promise.resolve | 주어진 값을 이행 상태로 반환 | 없음 |
| Promise.reject | 주어진 에러를 거부 상태로 반환 | 없음 |

실무에서는 대부분 `Promise.all`과 `Promise.allSettled`를 많이 써요. 각 상황에 맞게 잘 선택해서 사용하면 돼요!
