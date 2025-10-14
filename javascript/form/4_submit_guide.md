
# 🚀 자바스크립트 submit 이벤트와 메서드 정리

## 1️⃣ submit 이벤트

- **폼 제출 시** 트리거
- 주요 역할:
    - 제출 전 **데이터 검증**
    - `event.preventDefault()`로 제출 취소

✅ 발생 조건
- `<input type="submit">` 클릭
- 인풋 필드에서 **Enter 키**

✅ 예시
```html
<form onsubmit="alert('submit!'); return false;">
  <input type="text" placeholder="Enter 키 누르기">
  <input type="submit" value="제출">
</form>
```

---

## 2️⃣ submit과 click의 관계

- Enter 키로 폼 제출 시, `<input type="submit">`의 `click` 이벤트도 **자동 실행**
- 클릭 안 해도 click 발생! (주의)

✅ 예시
```html
<form onsubmit="return false;">
  <input type="text" value="Enter 키 누르기">
  <input type="submit" value="제출" onclick="alert('click 이벤트 발생!')">
</form>
```

---

## 3️⃣ form.submit() 메서드

- 자바스크립트에서 **폼을 직접 제출**
- **submit 이벤트 트리거 X** (핸들러 호출 안 됨)

✅ 예시
```javascript
let form = document.createElement('form');
form.action = 'https://google.com/search';
form.method = 'GET';

form.innerHTML = '<input name="q" value="테스트">';

document.body.append(form);
form.submit();
```

✅ 주의: `form.submit()` 호출 전 반드시 폼을 문서에 추가해야 함

---

## 4️⃣ 요약

| 구분 | 설명 |
|------|-------|
| submit 이벤트 | 폼 제출 시 발생 (데이터 검증, 제출 취소) |
| form.submit() | JS로 폼 제출 (submit 이벤트 X) |
| Enter 키 | `submit` + `click` 이벤트 트리거 |

---

✅ 실습 코드나 추가 설명 필요하면 알려주세요!
