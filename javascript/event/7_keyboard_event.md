
# ⌨️ 자바스크립트 Keyboard Events 정리

## 1️⃣ Keyboard Events 개념

- 키보드 입력에 반응하는 이벤트
- 가상 키보드, 물리 키보드, 단축키, 조합키 등에 사용
- 입력 필드의 변화 추적용으로는 `input` 이벤트를 추천 (copy/paste, 음성 입력 등 포함)

---

## 2️⃣ 주요 이벤트

| 이벤트 | 설명 |
|--------|-------|
| keydown | 키를 눌렀을 때 (반복 발생) |
| keyup | 키를 뗐을 때 |

---

## 3️⃣ 주요 속성

| 속성 | 설명 |
|------|-------|
| event.key | 입력된 문자 값 (ex: "a", "A", "Enter") |
| event.code | 키의 물리적 코드 (ex: "KeyA", "ArrowLeft") |
| event.repeat | 키 반복 중이면 true |
| event.ctrlKey | Ctrl 키 눌림 여부 |
| event.altKey | Alt 키 눌림 여부 |
| event.shiftKey | Shift 키 눌림 여부 |
| event.metaKey | Cmd (Mac) 키 눌림 여부 |

---

## 4️⃣ key vs code

| 상황 | event.key | event.code |
|------|-----------|-----------|
| Z 키 (영문 키보드) | "z" (소문자) | "KeyZ" |
| Shift+Z | "Z" (대문자) | "KeyZ" |
| 독일 키보드에서 Y 키 | "y" | "KeyZ" |

- **key**: 현재 언어 설정에 따라 달라짐 (layout-dependent)
- **code**: 물리적 키 코드 (layout-independent)

✅ 단축키는 **code**로 확인하는 게 일반적  
✅ 입력 값 확인은 **key**로 확인

---

## 5️⃣ 키 자동 반복 (auto-repeat)

- 키를 오래 누르면 `keydown` 반복 발생 (`event.repeat == true`)
- `keyup`은 한 번만 발생

---

## 6️⃣ 기본 동작 (default action)

- 키보드 기본 동작: 문자 입력, 페이지 스크롤, 브라우저 단축키 등
- 대부분 `keydown`에서 `preventDefault()`로 차단 가능
- 단, OS 레벨 단축키(예: Alt+F4)는 차단 불가

---

## 7️⃣ 입력 필드 필터링 예시

```html
<script>
function checkPhoneKey(key) {
  return (key >= '0' && key <= '9') || key == '+' || key == '(' || key == ')' || key == '-' ||
    key == 'ArrowLeft' || key == 'ArrowRight' || key == 'Delete' || key == 'Backspace';
}
</script>
<input onkeydown="return checkPhoneKey(event.key)" placeholder="Phone, please" type="tel">
```

⚠️ 주의: 마우스 우클릭 → 붙여넣기(Paste)는 막을 수 없음. input 이벤트로 최종값 검사 추천!

---

## 8️⃣ 구식 속성 (Legacy)

| 과거 속성 | 상태 |
|------------|--------|
| keypress | **사용 금지** (구식) |
| keyCode | **사용 금지** (구식) |
| charCode | **사용 금지** (구식) |

---

## 9️⃣ 요약

✅ 키보드 이벤트는 키 입력 처리에 사용 (단축키, 조합키 등)  
✅ 입력 필드 변화 추적은 `input`, `change` 이벤트 사용  
✅ `key` vs `code` 구분 잘하기  
✅ `keydown`은 반복 발생, `keyup`은 한 번만 발생  
✅ OS 단축키는 `preventDefault()`로 막을 수 없음

---

📌 실습 코드나 질문 필요하면 알려주세요!
