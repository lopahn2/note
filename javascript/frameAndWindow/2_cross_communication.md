
# 📚 자바스크립트 Cross-window Communication 쉽게 이해하기

## 🌐 Same Origin Policy (같은 출처 정책)
- 다른 사이트끼리 JS로 서로 접근 못 하게 막는 보안 규칙
- **같은 출처**란? 아래 3개가 모두 같아야 해요:
    1. 프로토콜 (`http`, `https`)
    2. 도메인 (`site.com`, `www.site.com` 다르면 안 됨)
    3. 포트 (`:80`, `:8080` 다르면 안 됨)

예시:
- 같은 출처:
    - http://site.com
    - http://site.com/page.html
- 다른 출처:
    - https://site.com (https라서 다름)
    - http://www.site.com (www.이 붙어서 다름)
    - http://site.org (다른 도메인)

## 🪟 창 간 접근 (팝업/iframe)
- 같은 출처라면 팝업/iframe의 모든 것에 접근 가능 (변수, 문서, 등등)
- 다른 출처라면:
    - `location` 변경만 가능 (강제 리다이렉트)
    - 그 외 접근 ❌ (보안상 제한)

## 🖼 iframe과 접근
```html
<iframe src="https://example.com" id="iframe"></iframe>
```
- 접근 시도:
    - `iframe.contentWindow` (OK, 윈도우 객체 접근 가능)
    - `iframe.contentDocument` (❌ 다른 출처면 불가)
    - `iframe.contentWindow.location` 읽기 (❌ 불가, 변경만 가능)

## 🏠 서브도메인끼리 접근 (document.domain)
- `john.site.com`, `peter.site.com`, `site.com` 은 다르지만...
- 아래처럼 하면 같은 출처로 간주!
```javascript
document.domain = 'site.com';
```
- **단, 2차 도메인이 같아야 함!**

## ⚠️ iframe 문서 주의 (wrong document pitfall)
- iframe은 생성되자마자 초기 문서가 있지만, 실제 문서가 로드되면 바뀜
- 따라서 iframe에 접근은 `iframe.onload` 이후에 해야 안전

## 🧭 window.frames 컬렉션
```javascript
window.frames[0]        // 첫 번째 iframe window 객체
window.frames.iframeName // name 속성으로도 접근 가능
```

## 🌳 계층 관계 접근
| 속성          | 설명                         |
|---------------|----------------------------|
| window.frames | 자식 iframe들 (배열/객체)     |
| window.parent | 부모 window                 |
| window.top    | 최상위 window                |

예시:
```javascript
if (window == top) {
  alert('최상위 window!');
} else {
  alert('iframe 안입니다!');
}
```

## 🏖 iframe sandbox 속성
```html
<iframe sandbox src="..."></iframe>
```
- 기본적으로 **매우 제한적** (스크립트, 폼 제출, 팝업, 위치 변경 다 ❌)
- 제한을 풀고 싶으면:
```html
<iframe sandbox="allow-scripts allow-forms allow-popups allow-same-origin"></iframe>
```

| 옵션               | 설명                                |
|--------------------|-----------------------------------|
| allow-same-origin  | 원래 같은 출처라면 JS 접근 허용          |
| allow-top-navigation | 부모창의 location 변경 허용            |
| allow-forms        | 폼 제출 허용                          |
| allow-scripts      | 스크립트 실행 허용                      |
| allow-popups       | 팝업 열기 허용                          |

## 📬 Cross-window Messaging (postMessage)

### 💬 postMessage로 메시지 보내기
```javascript
targetWindow.postMessage(data, targetOrigin);
```
- **data**: 보낼 데이터 (객체/문자열)
- **targetOrigin**: 받을 창의 출처 (보안상 중요, `*`은 모든 출처 허용)

예시:
```javascript
let win = window.frames.example;
win.postMessage("hello", "http://example.com");
```

### 📥 메시지 받기 (onmessage 이벤트)
```javascript
window.addEventListener("message", function(event) {
  if (event.origin !== "http://example.com") return; // 보안 체크
  console.log("받은 데이터:", event.data);
  event.source.postMessage("답변", event.origin); // 답장 보내기
});
```

### event 객체 속성
| 속성    | 설명                                  |
|---------|---------------------------------------|
| data    | 받은 데이터                             |
| origin  | 보낸 쪽의 출처                          |
| source  | 보낸 쪽 window 객체 (postMessage 가능) |

## 📝 정리
- 창 간 접근 (팝업, iframe) 조건:
    - **같은 출처**: 자유롭게 접근
    - **다른 출처**: `location` 변경 가능, `postMessage`로 데이터 교환
- 팝업/iframe 레퍼런스:
    - 팝업: `window.open()` -> 새 창 레퍼런스
    - 팝업에서 부모: `window.opener`
    - iframe: `iframe.contentWindow`
    - 부모/자식 계층: `window.frames`, `window.parent`, `window.top`
- postMessage로 다른 출처끼리도 안전하게 데이터 주고받기 가능!
