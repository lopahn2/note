
# 📚 자바스크립트 팝업과 window 메서드 이해하기

## 🎈 팝업(Popup)이란?
- 팝업은 새 창을 열어 추가 문서를 보여주는 방법입니다.
- 아주 오래된 방식이며, 주로 `window.open()`을 사용해 열 수 있습니다.
- 모바일에서는 잘 사용되지 않아요.

### 예제
```javascript
window.open('https://javascript.info/')
```
- 대부분의 브라우저는 새 "창" 대신 새 "탭"으로 열어요.

## 🎯 팝업의 특징과 사용처
- **OAuth 로그인(구글/페이스북 로그인)**: 팝업이 독립된 JS 환경이라 안전하게 처리 가능
- 팝업은 새 URL로 이동하거나 메시지를 부모 창으로 보낼 수 있음

## 🚫 팝업 차단 (Popup Blocking)
- 악성 사이트가 팝업을 남발해 광고를 띄우는 걸 막기 위해, **사용자 이벤트 없이 호출된 `window.open()`은 대부분 차단**돼요.
- 예제:
```javascript
// 차단됨
window.open('https://javascript.info');

// 허용됨 (사용자 이벤트 안에서 실행)
button.onclick = () => {
  window.open('https://javascript.info');
};
```
- `setTimeout` 안에서 열 경우, 1~2초 이내는 허용, 그 이상은 차단될 수 있음.

## 🛠️ window.open() 사용법
```javascript
window.open(url, name, params)
```
- **url**: 열 페이지 주소
- **name**: 창 이름 (같은 이름이면 새로 안 열리고 기존 창에 로드)
- **params**: 창 설정 문자열 (쉼표로 구분, 공백 ❌)

예시:
```javascript
let params = "width=600,height=300,left=100,top=100";
window.open('/', 'test', params);
```

### params 옵션
| 옵션       | 설명                                      |
|------------|-------------------------------------------|
| left/top   | 위치 (숫자)                              |
| width/height | 크기 (숫자, 최소값 제한 있음)            |
| menubar    | 메뉴바 표시 여부 (yes/no)                  |
| toolbar    | 툴바 표시 여부 (yes/no)                     |
| location   | 주소 표시줄 표시 여부 (yes/no) (FF/IE는 강제 표시) |
| status     | 상태바 표시 여부 (yes/no)                  |
| resizable  | 크기 조절 가능 여부 (yes/no, 비추천)         |
| scrollbars | 스크롤바 표시 여부 (yes/no, 비추천)         |

## 🎨 팝업 콘텐츠 다루기
팝업 열고 내용 쓰기:
```javascript
let newWin = window.open("about:blank", "hello", "width=200,height=200");
newWin.document.write("Hello, world!");
```

팝업 로드 후 조작:
```javascript
let newWindow = open('/', 'example', 'width=300,height=300');
newWindow.onload = function() {
  newWindow.document.body.insertAdjacentHTML('afterbegin', '<div>Welcome!</div>');
};
```

## 🌐 Same Origin Policy (같은 출처 정책)
- 팝업과 부모 창이 같은 출처면 JS로 서로 접근 가능
- 다른 출처면 접근 제한 (보안 이유)

## 🔄 팝업에서 부모 창 접근
```javascript
let newWin = window.open("about:blank", "hello", "width=200,height=200");
newWin.document.write(
  "<script>window.opener.document.body.innerHTML = 'Test'<\/script>"
);
```

## ❌ 팝업 닫기
```javascript
win.close(); // 팝업 창 닫기
win.closed;  // 닫혔는지 확인 (true/false)
```

주의: `window.close()`는 대부분의 브라우저에서 팝업에서만 작동 (직접 열지 않은 창에선 무시)

## 📏 팝업 위치/크기 조정
- `win.moveBy(x,y)` / `win.moveTo(x,y)`
- `win.resizeBy(w,h)` / `win.resizeTo(w,h)`

단, 보안상 제한 많음 (팝업 외엔 거의 동작 안 함)

## 🖱️ 스크롤 제어
- `win.scrollBy(x,y)` / `win.scrollTo(x,y)`
- `elem.scrollIntoView()`

## 🔍 포커스/블러
- `win.focus()`: 창에 포커스 줌
- `win.blur()`: 포커스 제거
- `window.onfocus`, `window.onblur` 이벤트로 포커스 변화 감지

하지만 광고 악용으로 제한 많음. 모바일에선 `focus()` 거의 무시.

## 📌 정리
- 팝업은 `window.open()`으로 열며, 주로 로그인(OAuth) 등에 사용
- 팝업과 부모 창은 서로 참조 (`window.opener`)
- 팝업은 같은 출처일 때만 JS로 접근 가능
- `close()`로 팝업 닫을 수 있음
- `focus()`/`blur()` 메서드와 이벤트는 제한적으로 동작
- 브라우저마다 팝업 처리 방식에 차이 있음 (차단 여부, 새 창/탭 등)

팝업은 적절한 경우에만 사용하고, 사용자에게 "새 창 열림"을 알려주는 아이콘 등을 표시하는 것이 좋습니다!
