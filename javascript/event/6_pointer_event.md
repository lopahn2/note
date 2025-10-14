
# 🖱️ 자바스크립트 Pointer Events 정리

## 1️⃣ Pointer Events란?

- 다양한 입력 장치 (마우스, 터치, 펜 등)를 **하나의 이벤트 시스템**으로 처리 가능
- **Pointer Events Level 2**는 모든 주요 브라우저에서 지원 (IE10, Safari 12- 제외)

### 기존 이벤트와 비교
| 과거 | 문제점 | Pointer Events 도입 이유 |
|----|--------|------------------|
| 마우스 이벤트 (mousedown, mouseup...) | 멀티터치 처리 불가 | 다양한 입력장치 처리 |
| 터치 이벤트 (touchstart, touchend...) | 코드 복잡, 중복 | 통합된 API 제공 |

---

## 2️⃣ Pointer Events 종류

| Pointer 이벤트 | 유사 마우스 이벤트 | 설명 |
|----------------|--------------------|-------|
| pointerdown | mousedown | 눌렀을 때 |
| pointerup | mouseup | 뗐을 때 |
| pointermove | mousemove | 움직일 때 |
| pointerover | mouseover | 요소에 진입할 때 |
| pointerout | mouseout | 요소에서 나갈 때 |
| pointerenter | mouseenter | 요소에 진입할 때 (버블링 X) |
| pointerleave | mouseleave | 요소에서 나갈 때 (버블링 X) |
| pointercancel | - | 브라우저가 상호작용 취소 |
| gotpointercapture | - | pointer capture 시작 |
| lostpointercapture | - | pointer capture 종료 |

---

## 3️⃣ Pointer 이벤트 속성

- `pointerId`: 포인터 식별자 (멀티터치 시 각 손가락마다 고유 값)
- `pointerType`: "mouse", "pen", "touch"
- `isPrimary`: 첫 번째 포인터면 true (첫 손가락, 마우스)
- `width`, `height`: 접촉 면적 (터치 기기만)
- `pressure`: 0 ~ 1 (0.5: 마우스 클릭, 0: 터치 X, 1: 최대 압력)
- `tiltX`, `tiltY`, `twist`, `tangentialPressure`: 펜 특화 속성

---

## 4️⃣ 멀티터치 처리

- `pointerId`를 사용해 각각의 터치 추적 가능
- 첫 번째 손가락은 `isPrimary=true`

예시:
```javascript
element.onpointerdown = (event) => {
  console.log(event.pointerId, event.isPrimary, event.pointerType);
};
```

---

## 5️⃣ pointercancel

- 브라우저가 기본 동작(예: 이미지 드래그 앤 드롭)을 처리하려고 할 때 발생
- **방지 방법**:
    - `element.ondragstart = () => false;` (마우스)
    - CSS: `touch-action: none;` (터치)

---

## 6️⃣ Pointer Capture

- **특정 포인터**를 특정 요소에 바인딩 (드래그 중에도 이벤트를 그 요소로 전달)

### 사용법
```javascript
elem.onpointerdown = (event) => {
  elem.setPointerCapture(event.pointerId);
};

elem.onpointermove = (event) => {
  // 포인터 이동 처리
};
```

- `pointerup`, `pointercancel` 시 자동 해제
- 해제 이벤트: `lostpointercapture`

⚠️ 캡처 중에도 이벤트 객체의 좌표(clientX/Y)는 정상적으로 제공됨

---

## 7️⃣ Pointer Events로 마우스 이벤트 대체하기

- 기존 코드의 `mouse*` 이벤트를 `pointer*`로 변경
- `touch-action: none;` 필요할 수 있음 (CSS)
- IE10, Safari 12 이하 지원 필요 없다면 바로 적용 가능

---

## 8️⃣ 요약

✅ Pointer Events 특징:
- **모든 포인터(마우스, 터치, 펜)** 처리 가능
- `pointerId`로 멀티터치 식별
- `pointerType`으로 장치 구분
- `pressure`, `width`, `height` 등 고급 속성 제공
- Pointer Capture로 특정 요소에 이벤트 바인딩
- `touch-action: none;` 으로 기본 동작 방지

✅ 이제는 마우스/터치 이벤트 대신 Pointer Events를 사용하는 게 표준!

---

📌 실습 및 예제 필요하면 알려주세요!
