
# 📚 자바스크립트 Cross-window Communication & iframe 쉽게 이해하기

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

## 🖼 iframe이란?
- **iframe**은 내 페이지 안에 다른 페이지를 불러오는 작은 "브라우저 창"
- 예시:
```html
<iframe src="https://example.com" width="300" height="200"></iframe>
```
- 특징:
    - 독립된 window 객체 (`iframe.contentWindow`)
    - 독립된 document 객체 (`iframe.contentDocument`)
    - 부모와 같은 출처라면 JS로 접근 가능
    - 다른 출처라면 JS 접근 ❌ (`location`만 변경 가능)

### iframe 실무 주의사항 (초보자용 팁!)
✅ 반드시 `onload` 이후에 접근!
```javascript
iframe.onload = function() {
  let newDoc = iframe.contentDocument;
  // 안전하게 접근 가능!
};
```

✅ 다른 출처의 iframe은 `contentDocument`, `contentWindow` 접근 ❌  
✅ iframe 내부에서는 부모 창 `window.parent` 참조 가능  
✅ 최상위 창은 `window.top`  
✅ iframe은 필요한 경우에만 (광고, 외부 서비스 등) 사용  
✅ fetch + innerHTML로 대체 고려 (iframe보다 가볍고 안전)

### iframe 속성 정리
| 속성 | 설명 | 예시 |
|---|---|---|
| `src` | 불러올 페이지 URL | `<iframe src="https://example.com">` |
| `name` | 창 이름 (window.frames로 접근) | `<iframe name="myFrame">` |
| `sandbox` | 보안 제한 적용 | `<iframe sandbox>` |
| `allow` | 권한 허용 (`fullscreen` 등) | `<iframe allow="fullscreen">` |
| `width`, `height` | 크기 | `<iframe width="400" height="300">` |

## 🏖 iframe sandbox 속성
```html
<iframe sandbox src="..."></iframe>
```
- 기본적으로 **매우 제한적** (스크립트, 폼 제출, 팝업, 위치 변경 다 ❌)
- 필요 시 제한 해제:
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
- iframe은 "새 창" 개념, onload 이후 접근, 보안 주의 (sandbox)
