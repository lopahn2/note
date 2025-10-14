
# 🛑 Fetch 요청 중단(Abort) 이해하기

## 1️⃣ 왜 요청을 중단해야 할까?

- 어떤 작업을 하다가 사용자가 **"뒤로 가기"** 또는 **"취소"** 버튼을 눌렀다면?
- 이미 보낸 `fetch` 요청을 취소하고 싶을 때가 생길 거야.

하지만 **JavaScript의 Promise에는 중단(Abort) 개념이 없음**.  
그래서 특별한 도구가 필요해!  
그게 바로 **AbortController**!

---

## 2️⃣ AbortController란?

- **AbortController**는 **취소(Abort) 이벤트**를 관리하는 객체야.
- 두 가지 역할:
    1. **abort() 메서드**: 중단 시 호출 (누르는 쪽)
    2. **signal 객체**: 중단 이벤트를 받을 쪽

✅ **signal.aborted**: 요청이 중단되었는지 여부를 알려주는 불리언 값

### 기본 예제 (fetch 없이)

```javascript
let controller = new AbortController();
let signal = controller.signal;

signal.addEventListener('abort', () => alert("Abort 발생!"));

controller.abort(); // abort() 호출 → 이벤트 발생
alert(signal.aborted); // true
```

---

## 3️⃣ fetch와 함께 사용하기

```javascript
let controller = new AbortController();

let response = await fetch(url, {
  signal: controller.signal
});
```

- `fetch`는 `AbortController`의 `signal`을 받아서 **중단 가능(fetch + abort 연동)** 하게 됨.
- 중단하려면 `controller.abort()` 호출!

---

## 4️⃣ 중단 시 처리

- 중단되면 fetch는 **Rejected 상태의 Promise**를 반환해.
- 에러 타입: `AbortError`
- 따라서 **try-catch**로 처리해야 함!

```javascript
let controller = new AbortController();
setTimeout(() => controller.abort(), 1000); // 1초 후 중단

try {
  let response = await fetch('/article/fetch-abort/demo/hang', {
    signal: controller.signal
  });
} catch(err) {
  if (err.name == 'AbortError') {
    alert("요청 중단됨!");
  } else {
    throw err;
  }
}
```

---

## 5️⃣ 여러 fetch 동시에 중단하기

```javascript
let urls = [/* 여러 URL */];
let controller = new AbortController();

let fetchJobs = urls.map(url =>
  fetch(url, { signal: controller.signal })
);

// fetch 여러 개 동시에 실행 중
// controller.abort()를 호출하면 fetchJobs 전부 중단!
```

---

## 6️⃣ fetch 외 다른 작업도 중단하기

```javascript
let controller = new AbortController();

let ourJob = new Promise((resolve, reject) => {
  controller.signal.addEventListener('abort', reject);
});

let fetchJobs = urls.map(url => fetch(url, {
  signal: controller.signal
}));

let results = await Promise.all([...fetchJobs, ourJob]);
```

✅ **AbortController는 fetch 외 다른 비동기 작업에도 적용 가능!**

---

## 7️⃣ 초보자가 헷갈리기 쉬운 포인트

✅ **Abort는 직접 `abort()`를 호출해야 함!**
- `signal`만 있다고 중단되는 게 아님! 반드시 `controller.abort()` 필요!

✅ **AbortError 처리 필수!**
- try-catch로 `AbortError` 잡아주자!

✅ **Abort는 fetch 외에도 가능!**
- `AbortController`는 단순한 이벤트 시스템, fetch뿐 아니라 다른 작업도 중단 가능!

✅ **fetch 요청 중단은 브라우저마다 동작이 다를 수 있음**
- 특히 오래된 브라우저는 AbortController를 지원하지 않을 수도 있음.
