
# 🌐 Fetch API 전체 옵션 정리

## 1️⃣ fetch 기본 문법

```javascript
let promise = fetch(url, {
  method: "GET", // POST, PUT, DELETE, etc.
  headers: {
    "Content-Type": "text/plain;charset=UTF-8"
  },
  body: undefined, // string, FormData, Blob, BufferSource, or URLSearchParams
  referrer: "about:client", // 또는 "", 다른 same-origin URL
  referrerPolicy: "no-referrer-when-downgrade",
  mode: "cors", // same-origin, no-cors
  credentials: "same-origin", // omit, include
  cache: "default", // no-store, reload, no-cache, force-cache, only-if-cached
  redirect: "follow", // manual, error
  integrity: "", // sha256-abc... 형태의 해시
  keepalive: false, // true
  signal: undefined, // AbortController
  window: window // null
});
```

---

## 2️⃣ referrer / referrerPolicy

- `referrer`: 요청 헤더의 Referer 값 직접 설정
- `referrerPolicy`: 요청마다 참조 헤더 처리 방식

예시:
```javascript
fetch('/page', { referrer: "" }); // Referer 헤더 제거
fetch('/page', { referrer: "https://javascript.info/anotherpage" }); // 같은 오리진 내에서만 가능
```

referrerPolicy 예시:
```javascript
fetch('/page', { referrerPolicy: "origin-when-cross-origin" });
```

referrerPolicy 값 설명:

| 값 | same-origin | cross-origin | HTTPS→HTTP |
|----|-------------|--------------|------------|
| no-referrer | X | X | X |
| no-referrer-when-downgrade | 전체 | 전체 | X |
| origin | origin | origin | origin |
| origin-when-cross-origin | 전체 | origin | origin |
| same-origin | 전체 | X | X |
| strict-origin | origin | origin | X |
| strict-origin-when-cross-origin | 전체 | origin | X |
| unsafe-url | 전체 | 전체 | 전체 |

---

## 3️⃣ mode

```javascript
mode: "cors" // 기본값, 크로스 오리진 허용
mode: "same-origin" // 크로스 오리진 금지
mode: "no-cors" // 단순 요청만 허용
```

---

## 4️⃣ credentials

```javascript
credentials: "same-origin" // 기본값, 같은 오리진만 쿠키/인증 포함
credentials: "include" // 모든 요청에 쿠키/인증 포함 (서버 CORS 설정 필요)
credentials: "omit" // 항상 쿠키/인증 제거
```

---

## 5️⃣ cache

```javascript
cache: "default" // HTTP 캐시 규칙 따름
cache: "no-store" // 캐시 무시
cache: "reload" // 캐시 무시, 응답은 캐시에 저장
cache: "no-cache" // 조건부 요청 후 캐시 저장
cache: "force-cache" // 캐시만 사용
cache: "only-if-cached" // 캐시만 사용, 없으면 에러 (same-origin만)
```

---

## 6️⃣ redirect

```javascript
redirect: "follow" // 기본값, 301/302 리다이렉트 자동 따름
redirect: "error" // 리다이렉트 시 에러 발생
redirect: "manual" // 리다이렉트 안함, response.redirected 확인 필요
```

---

## 7️⃣ integrity

```javascript
fetch('http://site.com/file', {
  integrity: 'sha256-abcdef...'
});
```

- 응답의 해시값과 일치하지 않으면 오류 발생

---

## 8️⃣ keepalive

```javascript
window.onunload = function() {
  fetch('/analytics', {
    method: 'POST',
    body: "statistics",
    keepalive: true
  });
};
```

- 페이지 언로드 후에도 요청 유지
- **제한**: 전체 64KB 이하, 응답 처리 불가

---

## 9️⃣ signal

- `AbortController`로 요청 중단
```javascript
let controller = new AbortController();
fetch(url, { signal: controller.signal });
controller.abort(); // 요청 중단
```

---

## 10️⃣ 예제

```javascript
let response = await fetch('https://example.com/api', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer token'
  },
  body: JSON.stringify({ key: 'value' }),
  credentials: 'include',
  mode: 'cors',
  cache: 'no-cache',
  referrerPolicy: 'strict-origin-when-cross-origin'
});
```

---

✅ **주의사항**
- 대부분 옵션은 잘 안쓰임, 필요할 때만 사용
- 기본값으로도 fetch 대부분 잘 동작함
- 옵션 필요하면 이 정리 참고!
