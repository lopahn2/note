
# 🎯 자바스크립트 focus와 blur 정리

## 1️⃣ focus와 blur 이벤트

| 이벤트 | 설명 |
|--------|-------|
| focus | 요소에 포커스가 주어질 때 발생 |
| blur | 요소에서 포커스가 빠질 때 발생 |

✅ 예시: 이메일 입력 검증
```html
<input type="email" id="input">
<div id="error"></div>

<script>
input.onblur = function() {
  if (!input.value.includes('@')) {
    input.classList.add('invalid');
    error.innerHTML = '올바른 이메일 주소를 입력하세요.';
  }
};

input.onfocus = function() {
  input.classList.remove('invalid');
  error.innerHTML = "";
};
</script>
```

---

## 2️⃣ 메서드: focus(), blur()

- `elem.focus()`: 요소에 포커스 주기
- `elem.blur()`: 요소에서 포커스 제거

✅ 예시: 잘못된 입력 시 포커스 유지
```javascript
input.onblur = function() {
  if (!this.value.includes('@')) {
    this.classList.add('error');
    input.focus(); // 포커스 다시 이동
  } else {
    this.classList.remove('error');
  }
};
```

---

## 3️⃣ tabindex 속성

| 값 | 설명 |
|----|-------|
| 양수 | Tab 키 순서 지정 |
| 0 | 기본 순서 (input 등과 동일) |
| -1 | 스크립트로만 포커스 가능 (Tab 키로는 X) |

✅ 예시
```html
<ul>
  <li tabindex="1">일</li>
  <li tabindex="0">영</li>
  <li tabindex="2">이</li>
  <li tabindex="-1">음수 일</li>
</ul>
```

✅ JavaScript로 설정
```javascript
elem.tabIndex = 1;
```

---

## 4️⃣ 이벤트 위임: focus, blur는 버블링 X

- focus, blur → 캡처링 단계에서만 감지 가능
- focusin, focusout → 버블링 O

✅ 캡처링 사용 예시
```javascript
form.addEventListener("focus", () => form.classList.add('focused'), true);
form.addEventListener("blur", () => form.classList.remove('focused'), true);
```

✅ focusin/focusout 사용 예시
```javascript
form.addEventListener("focusin", () => form.classList.add('focused'));
form.addEventListener("focusout", () => form.classList.remove('focused'));
```

---

## 5️⃣ 기타

- `autofocus` 속성: 페이지 로드 시 자동 포커스
- 현재 포커스된 요소: `document.activeElement`

---

✅ 요약

- focus: 포커스 받음, blur: 포커스 잃음
- 대부분 요소는 기본 포커스 X → tabindex 필요
- focus/blur 버블링 X → 캡처링 or focusin/focusout 사용
- 입력 검증 및 UI 개선에 유용

---

📌 실습 코드나 추가 설명 필요하면 알려주세요!
