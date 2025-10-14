
# 🖱️ 브라우저 이벤트 기본 개념 정리

## 1️⃣ 이벤트란?

- **이벤트(event)**: 사용자가 클릭하거나, 마우스를 움직이거나, 키보드를 누르는 등 브라우저 상에서 일어나는 행동에 대한 신호
- **DOM 요소**뿐만 아니라, **window**와 같은 다른 객체들도 이벤트 발생 가능

---

## 2️⃣ 주요 DOM 이벤트

| 분류 | 이벤트 | 설명 |
| --- | --- | --- |
| 마우스 | click, contextmenu, mouseover, mouseout, mousedown, mouseup, mousemove | 클릭, 우클릭, 마우스 이동 등 |
| 폼 | submit, focus | 폼 제출, 포커스 |
| 키보드 | keydown, keyup | 키 누름, 뗌 |
| 문서 | DOMContentLoaded | DOM 생성 완료 |
| CSS | transitionend | 애니메이션 종료 |

---

## 3️⃣ 이벤트 핸들러 등록 방법

| 방식 | 문법 | 특징 |
| --- | --- | --- |
| HTML 속성 | `<button onclick="...">` | HTML과 JS 혼합, 간단하지만 비추천 |
| DOM 프로퍼티 | `elem.onclick = 함수` | 간단하지만 **한 개만 등록 가능** |
| addEventListener | `elem.addEventListener("click", 함수)` | **여러 개 등록 가능**, 범용성 높음 |

> ⚠️ `addEventListener` 사용 시 **삭제하려면 같은 함수 참조 필요**!

---

## 4️⃣ this와 event 객체

- 핸들러 내부의 `this`는 **이벤트가 걸린 요소**를 참조
- `event` 객체는 이벤트 발생 관련 정보를 담고 있음 (위치, 키, 버튼 등)
- 기본 구조:

```javascript
elem.onclick = function(event) {
  alert(event.type); // 이벤트 타입
  alert(this);       // 이벤트 걸린 요소
};
```

---

## 5️⃣ 객체형 핸들러 (handleEvent)

```javascript
let obj = {
  handleEvent(event) {
    alert(event.type);
  }
};

elem.addEventListener("click", obj);
```

- 클래스와 함께 사용하면 이벤트 메서드 분리 가능!

---

## 6️⃣ 주의사항

✅ `addEventListener`는 **핸들러 함수 참조** 필요 (지우려면 동일 함수 사용)  
✅ `DOMContentLoaded` 등 일부 이벤트는 **addEventListener만 가능**  
✅ `setAttribute("onclick", ...)`은 작동 안 함!  
✅ `this`는 HTML 속성, DOM 프로퍼티 모두 동일 (핸들러가 걸린 요소)

---

## 7️⃣ 과제 핵심 코드

- **요소 숨기기**

```javascript
button.onclick = () => document.getElementById("text").hidden = true;
```

- **클릭한 요소 숨기기**

```html
<input type="button" onclick="this.hidden=true" value="Click to hide">
```

- **동일 함수 참조 문제**

```javascript
function handler() { alert(1); }

button.addEventListener("click", handler);
button.removeEventListener("click", handler); // 이렇게 해야 지워짐
```

- **공 위치 이동 (clientX/clientY)**

```javascript
ball.style.left = event.clientX - fieldCoords.left - field.clientLeft - ball.offsetWidth/2 + "px";
ball.style.top = event.clientY - fieldCoords.top - field.clientTop - ball.offsetHeight/2 + "px";
```

- **메뉴 열고 닫기 (.open 클래스 토글)**

```javascript
title.onclick = () => menu.classList.toggle("open");
```

- **닫기 버튼 추가**

```javascript
pane.insertAdjacentHTML("afterbegin", '<button class="remove-button">[x]</button>');
pane.firstChild.onclick = () => pane.remove();
```

---

이벤트는 **브라우저와 사용자 간의 소통 창구**입니다!  
핸들러 등록 방식과 this, event 객체를 꼭 기억해 두세요!
