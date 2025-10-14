
# 🌐 CORS (Cross-Origin Resource Sharing) 완벽 가이드

## 1️⃣ CORS란?

- **Cross-Origin Resource Sharing**의 약자
- 다른 오리진(도메인/프로토콜/포트 조합)에서 리소스를 요청할 때 필요한 보안 메커니즘
- 기본적으로 브라우저는 같은 오리진에서만 요청 가능
- 다른 오리진에 요청하려면 **서버가 허용**해야 함

## 2️⃣ 오리진(origin) 개념

- 오리진 = 도메인 + 프로토콜 + 포트
- 예: https://example.com:443
- 다르면 Cross-Origin

예시:
```javascript
try {
  await fetch('http://example.com');
} catch(err) {
  alert(err); // TypeError: Failed to fetch
}
```

## 3️⃣ CORS의 역사

- 예전에는 자바스크립트로 네트워크 요청 자체를 할 수 없었음
- 폼(`<form>`)과 iframe을 사용해 트릭처럼 요청을 보냄

폼 예제:
```html
<iframe name="iframe"></iframe>
<form target="iframe" method="POST" action="http://another.com/…">
  ...
</form>
```

스크립트(JSONP) 예제:
```javascript
function gotWeather({ temperature, humidity }) {
  alert(`temperature: ${temperature}, humidity: ${humidity}`);
}

let script = document.createElement('script');
script.src = `http://another.com/weather.json?callback=gotWeather`;
document.body.append(script);
```

서버 응답 예시:
```javascript
gotWeather({
  temperature: 25,
  humidity: 78
});
```

## 4️⃣ 안전한 요청 vs 안전하지 않은 요청

### 안전한 요청

- 메서드: GET, POST, HEAD
- 헤더: Accept, Accept-Language, Content-Language, Content-Type (특정 값만)
- 별도 preflight 없이 바로 전송

### 안전하지 않은 요청

- 예: PUT, DELETE, PATCH, API-Key 헤더 사용 등
- **preflight 요청(OPTIONS)** 필요

Preflight 요청 예시:
```http
OPTIONS /service.json
Host: site.com
Origin: https://javascript.info
Access-Control-Request-Method: PATCH
Access-Control-Request-Headers: Content-Type,API-Key
```

Preflight 응답 예시:
```http
200 OK
Access-Control-Allow-Origin: https://javascript.info
Access-Control-Allow-Methods: PATCH
Access-Control-Allow-Headers: Content-Type,API-Key
Access-Control-Max-Age: 86400
```

본 요청 예시:
```javascript
let response = await fetch('https://site.com/service.json', {
  method: 'PATCH',
  headers: {
    'Content-Type': 'application/json',
    'API-Key': 'secret'
  }
});
```

## 5️⃣ CORS 응답 헤더 예시

```http
200 OK
Content-Type: application/json
Access-Control-Allow-Origin: https://javascript.info
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: Content-Length,API-Key
```

## 6️⃣ 자격 증명(credentials)

```javascript
fetch('http://another.com', {
  credentials: "include"
});
```

서버 응답:
```http
200 OK
Access-Control-Allow-Origin: https://javascript.info
Access-Control-Allow-Credentials: true
```

✅ `*` 대신에 정확한 Origin 명시 필요

## 7️⃣ Origin vs Referer

요청 헤더 예시:
```http
GET /request
Host: anywhere.com
Origin: https://javascript.info
Referer: https://javascript.info/some/url
```

- Referer: 전체 URL (선택적, 보안 정책 따라 생략될 수 있음)
- Origin: 오리진 정보만 포함 (항상 전송, CORS 핵심)

## 8️⃣ 중요한 포인트

✅ preflight 요청은 JS로 볼 수 없음  
✅ `Access-Control-Expose-Headers` 없으면 Content-Length 등 비표준 헤더 접근 불가  
✅ 오래된 서버는 PUT, DELETE, PATCH 요청에 민감함

## 9️⃣ 정리: CORS 요청 흐름

### 안전한 요청
1. Origin 헤더 포함 요청
2. 서버 응답에 Access-Control-Allow-Origin

### 안전하지 않은 요청
1. preflight(OPTIONS) 요청
2. 서버 응답에 Access-Control-Allow-Methods, Allow-Headers
3. 본 요청 전송

---

🔒 CORS는 보안을 위한 장치, 서버와 협의해 헤더 설정 필요!
