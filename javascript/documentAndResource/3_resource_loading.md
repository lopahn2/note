
# Resource loading: onload와 onerror

## 🌐 외부 리소스 로딩 감지

브라우저는 외부 리소스(스크립트, 이미지, iframe 등) 로딩을 감지할 수 있는 **이벤트**를 제공합니다.

| 이벤트     | 설명                             |
| ---------- | -------------------------------- |
| onload     | 리소스 로딩 성공 시 발생            |
| onerror    | 리소스 로딩 실패 시 발생 (404, 네트워크 에러 등) |

---

## 📦 스크립트 로딩 예시

### 외부 스크립트 로드 후 함수 호출

```js
let script = document.createElement('script');
script.src = "https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.3.0/lodash.js";
document.head.append(script);

script.onload = function() {
  alert(_.VERSION);
};
```

### 로드 실패 처리

```js
let script = document.createElement('script');
script.src = "https://example.com/404.js";
document.head.append(script);

script.onerror = function() {
  alert("Error loading " + this.src);
};
```

**주의**: `onload`는 스크립트 내부 오류까지는 감지 못함. 스크립트 로드는 성공했지만 내부 코드 오류는 `window.onerror`로만 감지 가능.

---

## 🖼️ 이미지 로딩

```js
let img = document.createElement('img');
img.src = "https://js.cx/clipart/train.gif";

img.onload = function() {
  alert(`Image loaded: ${img.width}x${img.height}`);
};

img.onerror = function() {
  alert("Error occurred while loading image");
};
```

- 대부분 리소스는 추가되자마자 로드 시작
- **이미지**는 `src`를 설정해야 로드 시작
- **iframe**은 로드 완료 후 `iframe.onload` 발생 (성공/실패 상관없음)

---

## 🛡️ Cross-Origin 정책

- 다른 도메인의 스크립트는 에러 세부정보(라인, 메시지 등) 접근 불가

```js
<script src="https://cors.javascript.info/error.js"></script>
<script>
window.onerror = function(message, url, line, col, errorObj) {
  alert(`${message}
${url}, ${line}:${col}`);
};
</script>
```

- **Cross-Origin** 정책 우회: `crossorigin` 속성 + 서버 CORS 헤더 필요

| 속성 | 동작 |
| ---- | ---- |
| (없음) | 접근 금지 |
| crossorigin="anonymous" | 인증 정보 없이 접근 가능 (서버에 `Access-Control-Allow-Origin` 필요) |
| crossorigin="use-credentials" | 쿠키 포함 접근 가능 (서버에 `Access-Control-Allow-Credentials: true` 필요) |

---

## 🎯 이미지 로드 함수 예제

모든 이미지 로드 후 콜백 실행하기:

```js
function preloadImages(sources, callback) {
  let count = 0;
  let total = sources.length;

  function check() {
    count++;
    if (count === total) callback();
  }

  for (let src of sources) {
    let img = document.createElement('img');
    img.src = src;
    img.onload = check;
    img.onerror = check;
  }
}

// 사용 예시
preloadImages(["1.jpg", "2.jpg", "3.jpg"], function() {
  alert("Images loaded");
});
```

---

✅ **정리**

- **onload**: 로드 성공 후 실행
- **onerror**: 로드 실패 후 실행
- **iframe**: 성공/실패 구분 없이 항상 load 이벤트 발생
- **Cross-Origin**: 다른 도메인 리소스는 오류 상세 정보 차단됨
- **crossorigin** 속성 + 서버 CORS 헤더 필요
