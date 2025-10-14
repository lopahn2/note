
# 🖱️ 자바스크립트 드래그 앤 드롭 (Drag & Drop) 정리

## 1️⃣ 드래그 앤 드롭 개념

- 사용자와 상호작용하는 **직관적인 방법** (파일 이동, 장바구니 등)
- HTML5 표준: `dragstart`, `dragend` 등의 이벤트 제공
- 하지만 기본 드래그 이벤트의 한계:
    - 특정 영역 제한 불가
    - 방향 제한 불가
    - 모바일 지원 미흡
    - 사용자 경험 제어 부족

그래서 **마우스 이벤트 기반 드래그 앤 드롭** 직접 구현 필요!

---

## 2️⃣ 기본 드래그 앤 드롭 알고리즘

1. `mousedown` – 드래그할 요소 준비 (복사, 클래스 추가 등)
2. `mousemove` – `position: absolute`로 위치 이동
3. `mouseup` – 드래그 완료 처리

---

## 3️⃣ 예제 코드 (공 드래그)

```javascript
ball.onmousedown = function(event) {
  ball.style.position = 'absolute';
  ball.style.zIndex = 1000;
  document.body.append(ball);

  function moveAt(pageX, pageY) {
    ball.style.left = pageX - ball.offsetWidth / 2 + 'px';
    ball.style.top = pageY - ball.offsetHeight / 2 + 'px';
  }

  moveAt(event.pageX, event.pageY);

  function onMouseMove(event) {
    moveAt(event.pageX, event.pageY);
  }

  document.addEventListener('mousemove', onMouseMove);

  ball.onmouseup = function() {
    document.removeEventListener('mousemove', onMouseMove);
    ball.onmouseup = null;
  };
};

ball.ondragstart = function() {
  return false;
};
```

⚠️ 기본 브라우저 드래그 기능 끄기: `ondragstart = false`

⚠️ `mousemove`는 `document`에 등록 (빠른 이동 시 요소 건너뜀 방지)

---

## 4️⃣ 공이 점프하지 않게 개선

- `mousedown` 시 클릭 지점과 요소 왼쪽 상단 모서리의 거리 저장
- 마우스 좌표에서 이 거리만큼 빼서 이동

```javascript
ball.onmousedown = function(event) {
  let shiftX = event.clientX - ball.getBoundingClientRect().left;
  let shiftY = event.clientY - ball.getBoundingClientRect().top;

  function moveAt(pageX, pageY) {
    ball.style.left = pageX - shiftX + 'px';
    ball.style.top = pageY - shiftY + 'px';
  }

  moveAt(event.pageX, event.pageY);

  function onMouseMove(event) {
    moveAt(event.pageX, event.pageY);
  }

  document.addEventListener('mousemove', onMouseMove);

  ball.onmouseup = function() {
    document.removeEventListener('mousemove', onMouseMove);
    ball.onmouseup = null;
  };
};

ball.ondragstart = function() {
  return false;
};
```

---

## 5️⃣ 드롭 대상 찾기 (elementFromPoint)

- 드래그 중인 요소는 다른 요소 위에 있음 → 하위 요소 이벤트 못 받음
- 해결: `document.elementFromPoint(x, y)`로 포인터 아래 요소 찾기

```javascript
ball.hidden = true; // 잠시 숨김
let elemBelow = document.elementFromPoint(event.clientX, event.clientY);
ball.hidden = false;
```

---

## 6️⃣ 드롭 대상 강조 처리

```javascript
let currentDroppable = null;

function onMouseMove(event) {
  moveAt(event.pageX, event.pageY);

  ball.hidden = true;
  let elemBelow = document.elementFromPoint(event.clientX, event.clientY);
  ball.hidden = false;

  let droppableBelow = elemBelow?.closest('.droppable');

  if (currentDroppable != droppableBelow) {
    if (currentDroppable) leaveDroppable(currentDroppable);
    currentDroppable = droppableBelow;
    if (currentDroppable) enterDroppable(currentDroppable);
  }
}
```

---

## 7️⃣ 핵심 정리

- 이벤트 흐름: `ball.mousedown` → `document.mousemove` → `ball.mouseup`
- 클릭 지점과 요소 모서리의 거리 유지 (`shiftX`, `shiftY`)
- `elementFromPoint`로 포인터 아래 요소 찾기
- 강조 처리 및 드롭 처리 가능

---

## 8️⃣ 추가 팁

- 드래그 대상 강조 (`enterDroppable`, `leaveDroppable`)
- 드래그 방향 제한 (수직/수평)
- 이벤트 위임으로 수백 개 요소 처리 (`event.target` 확인)
- 필요하면 `DragZone`, `Droppable`, `Draggable` 클래스 구조화

---

✅ **이해가 잘 안 되거나 실습 코드 필요하면 언제든 알려줘!**
