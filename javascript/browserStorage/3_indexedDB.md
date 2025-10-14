
# 📦 IndexedDB 쉽게 배우기

## 🌐 IndexedDB란?

IndexedDB는 브라우저에 내장된 **로컬 데이터베이스**예요.  
`localStorage`보다 훨씬 강력하고, **많은 양의 데이터**를 저장할 수 있어요.

주요 특징:
- 거의 모든 자료형 저장 가능 (키-값 형태)
- 트랜잭션 지원 → 데이터 신뢰성 보장
- **키 범위 검색** 및 인덱스 지원
- 로컬에서 큰 데이터 저장 가능 → 오프라인 앱과 궁합 👍

하지만 전통적인 클라이언트-서버 앱에는 **과할 수도 있음**. 오프라인 앱 개발, ServiceWorker와 함께 쓸 때 주로 사용해요.

## 📥 데이터베이스 열기

데이터베이스를 열려면:

```javascript
let openRequest = indexedDB.open(name, version);
```

- `name`: 데이터베이스 이름
- `version`: 버전 (기본 1)

반환되는 `openRequest` 객체에 이벤트 핸들러를 등록해요.

```javascript
openRequest.onupgradeneeded = function(event) {
  let db = openRequest.result;
  // 초기화 코드 작성 (DB 처음 열거나 버전 업그레이드 시)
};

openRequest.onsuccess = function() {
  let db = openRequest.result;
  // db를 사용해 작업
};

openRequest.onerror = function() {
  console.error("DB 열기 실패", openRequest.error);
};
```

📌 **업그레이드 이벤트 (`upgradeneeded`)**
- 새 버전의 DB를 열 때 발생 (처음 열 때도)
- 데이터 구조(스키마) 설정은 **여기서만 가능**

## 🏗️ 객체 저장소 (Object Store)

데이터 저장은 **객체 저장소** 단위로 이루어져요. → SQL의 테이블 개념과 비슷!

```javascript
db.createObjectStore('books', {keyPath: 'id'});
```

- `keyPath`: 객체의 어떤 속성을 키로 삼을지 지정
- `autoIncrement`: 자동 키 생성 여부 (옵션)

**중요:** `createObjectStore`는 **upgradeneeded 이벤트** 안에서만 가능!

## 💾 데이터 추가하기

데이터 작업은 **트랜잭션** 안에서 해야 해요.

```javascript
let transaction = db.transaction('books', 'readwrite');
let store = transaction.objectStore('books');

let book = { id: 'js', price: 10 };

let request = store.add(book);

request.onsuccess = function() {
  console.log("저장 성공!");
};
request.onerror = function() {
  console.error("저장 실패", request.error);
};
```

📌 `add()`와 `put()`의 차이
- `add()`: **키 중복 시 에러**
- `put()`: 키 중복이면 **기존 데이터 덮어씀**

## 🔄 트랜잭션 주의사항

- 트랜잭션은 **짧게**! (오래 걸리면 브라우저가 자동으로 종료해버려요)
- `fetch`나 `setTimeout` 같은 비동기 작업은 트랜잭션 내에서 못 씀!  
  → 작업 끝나고 트랜잭션 시작하기

```javascript
let tx = db.transaction("books", "readwrite");
let store = tx.objectStore("books");

store.add(...);

tx.oncomplete = function() {
  console.log("트랜잭션 완료");
};
```

## 🔍 검색 (Query)

- 키 기반 검색: `get`, `getAll`, `getKey`, `getAllKeys`, `count`
- **키 범위 검색**: `IDBKeyRange` 사용

```javascript
store.getAll(IDBKeyRange.bound('css', 'html'));
```

- 인덱스 검색: 인덱스 생성 후 사용

```javascript
let index = store.createIndex('price_idx', 'price');
index.getAll(10);
```

## 🧭 커서 (Cursor)

대용량 데이터 검색은 커서 사용!

```javascript
let request = store.openCursor();

request.onsuccess = function() {
  let cursor = request.result;
  if (cursor) {
    console.log(cursor.key, cursor.value);
    cursor.continue();
  } else {
    console.log("끝!");
  }
};
```

## 🗑️ 데이터 삭제

```javascript
store.delete(key); // 특정 데이터 삭제
store.clear(); // 전체 삭제
```

## 💥 오류 처리

- `request.onerror`에서 처리 가능
- **event.preventDefault()** → 트랜잭션 중단 방지
- 에러 처리 안 하면 → 트랜잭션 전체 롤백

```javascript
request.onerror = function(event) {
  if (request.error.name == "ConstraintError") {
    event.preventDefault();
  }
};
```

## 📦 Promise Wrapper (idb 라이브러리)

콜백 대신 `async/await`로 쉽게 쓸 수 있는 라이브러리:

```html
<script src="https://cdn.jsdelivr.net/npm/idb@3.0.2/build/idb.min.js"></script>
```

```javascript
let db = await idb.openDB('store', 1, db => {
  db.createObjectStore('books', {keyPath: 'id'});
});

let tx = db.transaction('books', 'readwrite');
let store = tx.objectStore('books');

await store.add(...);
await tx.done;
```

## ⚠️ 주의!

- 트랜잭션은 **짧고 빠르게 끝내야 함**
- `fetch` → DB → `fetch` 순서로 작성할 것!
- 같은 DB 버전을 동시에 열면 → `onversionchange`/`onblocked` 이벤트 처리 필요

```javascript
db.onversionchange = function() {
  db.close();
  alert("DB 업데이트됨. 새로고침 해주세요.");
};
```

## 🎯 마무리

IndexedDB는 **로컬에서 큰 데이터를 저장**하는 강력한 도구입니다.  
복잡해 보이지만, 차근차근 따라 하면 쓸 수 있어요!  
필요할 때마다 이 문서를 참고해보세요! 🚀
