
# 📦 localStorage와 sessionStorage 쉽게 이해하기

## 🌐 웹 스토리지(Web Storage)란?

브라우저에 데이터를 저장할 때 주로 쿠키를 떠올리지만, **`localStorage`** 와 **`sessionStorage`** 도 있어요!  
이 둘은 **브라우저 내에 키-값 쌍을 저장**할 수 있는 저장소입니다.

- **localStorage**: 브라우저를 껐다 켜도 데이터가 유지됩니다.
- **sessionStorage**: 탭을 새로 고침해도 유지되지만, **탭을 닫으면 데이터가 사라집니다**.

👉 **쿠키와 다른 점**:
- 쿠키는 매 요청마다 서버에 전송되지만, localStorage/sessionStorage는 **서버로 전송되지 않음**.
- 저장 용량도 훨씬 큼 (2MB~5MB 이상).

## ✍️ 기본 메서드와 프로퍼티

| 메서드/프로퍼티       | 설명                              |
|---------------------|---------------------------------|
| setItem(key, value) | 키-값 저장                         |
| getItem(key)        | 키에 해당하는 값 가져오기               |
| removeItem(key)     | 키-값 삭제                           |
| clear()             | 모든 데이터 삭제                      |
| key(index)          | index에 해당하는 키 반환               |
| length              | 저장된 항목의 개수 반환                 |

## 📂 localStorage의 특징

- **오리진(도메인/포트/프로토콜)** 이 같으면 **모든 탭과 창에서 공유**.
- 브라우저를 껐다 켜도 데이터 유지.
- 예제:

```javascript
localStorage.setItem('test', 1);
alert(localStorage.getItem('test')); // 1
```

### 객체처럼 사용 가능하지만 주의!

```javascript
localStorage.test = 2;
alert(localStorage.test); // 2
delete localStorage.test;
```

> ❗ 하지만 이런 방식은 **추천하지 않음**: `length`나 `toString` 같은 내장 메서드를 키로 설정하면 에러 발생 가능!  
> 
> 👉 무조건 `getItem`/`setItem` 사용하기!

### 키 순회 방법

```javascript
for (let i = 0; i < localStorage.length; i++) {
  let key = localStorage.key(i);
  alert(`${key}: ${localStorage.getItem(key)}`);
}

// 권장하지 않는 방법 (내장 메서드까지 출력됨)
for (let key in localStorage) {
  if (!localStorage.hasOwnProperty(key)) continue;
  alert(`${key}: ${localStorage.getItem(key)}`);
}

// 안전한 방법
let keys = Object.keys(localStorage);
for (let key of keys) {
  alert(`${key}: ${localStorage.getItem(key)}`);
}
```

### 문자열만 저장 가능!

```javascript
localStorage.user = {name: "John"};
alert(localStorage.user); // [object Object]

// 객체 저장은 JSON.stringify 사용
localStorage.user = JSON.stringify({name: "John"});
let user = JSON.parse(localStorage.user);
alert(user.name); // John
```

## 📂 sessionStorage의 특징

- 같은 **탭** 내에서만 데이터 공유 (다른 탭/창에서는 안 보임).
- **새로 고침**해도 유지, 하지만 **탭을 닫으면 사라짐**.

```javascript
sessionStorage.setItem('test', 1);
alert(sessionStorage.getItem('test')); // 새로고침 후: 1
```

## 🔔 storage 이벤트

- `setItem`, `removeItem`, `clear` 호출 시 발생.
- 다른 창에서도 이벤트 감지 가능 (localStorage는 모든 창에서, sessionStorage는 같은 탭 내에서).

```javascript
window.onstorage = event => {
  if (event.key != 'now') return;
  alert(event.key + ':' + event.newValue + " at " + event.url);
};

localStorage.setItem('now', Date.now());
```

## 📊 localStorage vs sessionStorage

| 구분             | localStorage                           | sessionStorage                     |
|----------------|---------------------------------------|-----------------------------------|
| 공유 범위         | 오리진이 같은 모든 탭/창에서 공유              | 오리진이 같은 **탭/iframe** 내에서만 공유 |
| 브라우저 종료 시   | 데이터 유지                              | 데이터 삭제                            |
| 새로 고침 시      | 데이터 유지                              | 데이터 유지                            |
| 용량 제한         | 5MB 이상 (브라우저마다 다름)                  | 5MB 이상 (브라우저마다 다름)                |

## ⚠️ 초보 개발자가 자주 하는 실수

- localStorage에 **객체를 저장할 때 JSON.stringify 안 함** → [object Object]가 저장됨!
- localStorage를 객체처럼 사용 (`localStorage.key = value`) → **storage 이벤트 발생 안 함**, 예기치 못한 동작 발생.
- 다른 탭/창과 데이터 공유 여부 헷갈림 → localStorage는 공유, sessionStorage는 공유 안 함!

## 📌 정리

- `localStorage`: 여러 창에서 공유, 브라우저 꺼도 유지.
- `sessionStorage`: 같은 탭 내에서만 유지, 탭 닫으면 삭제.
- 항상 문자열로 저장, 객체는 JSON.stringify 사용!
- 키 순회는 `for`보다 `Object.keys()` 사용!
- storage 이벤트 잘 활용하면 창 간 통신도 가능!

---

이제 자신 있게 localStorage와 sessionStorage를 사용할 수 있겠죠? 🚀
