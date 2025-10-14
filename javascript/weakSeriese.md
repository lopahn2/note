# 🔒 자바스크립트 WeakMap & WeakSet 정리

## 🧠 가비지 컬렉션과 도달 가능성
- JS 엔진은 **더 이상 참조되지 않는 객체**를 메모리에서 제거함.
```js
let john = { name: "John" };
john = null; // → 객체는 메모리에서 제거됨
```

- 객체가 배열, 맵 등 자료구조에 속해 있다면 해당 자료구조가 살아있는 한 메모리에서 제거되지 않음.

---

## 🗺️ WeakMap

### ✅ 특징
- **키는 객체만 가능** (원시 값 불가)
- 객체가 참조되지 않으면 자동으로 제거됨
- 반복 불가: `keys()`, `values()`, `entries()` 없음
- 사용 가능한 메서드:
    - `weakMap.get(key)`
    - `weakMap.set(key, value)`
    - `weakMap.delete(key)`
    - `weakMap.has(key)`

### ✅ 예시
```js
let john = { name: "John" };
let weakMap = new WeakMap();

weakMap.set(john, "비밀문서");
john = null; // 객체와 함께 value도 GC 대상
```

---

## 📌 WeakMap 사용 예시

### 📁 사용자 방문 수 기록
```js
let visitsCountMap = new WeakMap();

function countUser(user) {
  let count = visitsCountMap.get(user) || 0;
  visitsCountMap.set(user, count + 1);
}
```

### 📁 캐시 활용
```js
let cache = new WeakMap();

function process(obj) {
  if (!cache.has(obj)) {
    let result = obj; // 연산 생략
    cache.set(obj, result);
  }
  return cache.get(obj);
}
```

---

## 📦 WeakSet

### ✅ 특징
- 객체만 저장 가능
- 값이 참조되지 않으면 자동 삭제
- 메서드:
    - `add(value)`
    - `has(value)`
    - `delete(value)`
- 반복 불가

### ✅ 예시
```js
let visitedSet = new WeakSet();

let john = { name: "John" };
visitedSet.add(john);

visitedSet.has(john); // true

john = null;
// john은 GC 대상, WeakSet에서도 자동 제거됨
```

---

## ✅ 용도 요약

| 목적 | 사용 도구 | 설명 |
|------|-----------|------|
| 추가 데이터 저장 | `WeakMap` | 객체에 부가 정보 저장 |
| 캐시 | `WeakMap` | 객체를 키로 캐시 저장 |
| 방문/처리 여부 | `WeakSet` | 객체가 처리되었는지 체크 |

---

## ❗ 주의
- **반복 불가**: size 확인, 모든 요소 순회 불가
- 가비지 컬렉션 시점은 명확히 알 수 없음 → 요소 수 추정 불가능
