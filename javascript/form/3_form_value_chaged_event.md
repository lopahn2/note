
# ✏️ 자바스크립트: change, input, cut, copy, paste 이벤트 정리

## 1️⃣ change 이벤트

- 요소의 **값이 변경된 후** (포커스 잃을 때) 발생
- 텍스트 입력: 포커스 벗어나면 발생
- select, checkbox, radio: 값 변경 즉시 발생

✅ 예시
```html
<input type="text" onchange="alert(this.value)">
<select onchange="alert(this.value)">
  <option value="1">옵션 1</option>
  <option value="2">옵션 2</option>
</select>
```

---

## 2️⃣ input 이벤트

- **값이 입력될 때마다** 발생
- 키보드 외 입력 (붙여넣기, 음성 입력 등)에도 반응
- ⇦, ⇨ 키에는 반응 X
- **preventDefault()로 기본 동작 막기 불가**

✅ 예시
```html
<input type="text" id="input">
<span id="result"></span>

<script>
input.oninput = function() {
  result.innerHTML = input.value;
};
</script>
```

---

## 3️⃣ cut, copy, paste 이벤트

- 잘라내기, 복사하기, 붙여넣기 할 때 발생
- `ClipboardEvent` 객체 제공 → `event.clipboardData` 사용 가능
- **preventDefault()** 로 기본 동작 차단 가능

✅ 예시
```html
<input type="text" id="input">

<script>
input.oncut = input.oncopy = input.onpaste = function(event) {
  alert(event.type + ' - ' + event.clipboardData.getData('text/plain'));
  return false; // 기본 동작 차단
};
</script>
```

✅ 주의사항
- 모든 종류의 데이터(텍스트, 파일 등)를 복사/붙여넣기 가능
- 클립보드는 OS 레벨, 보안 제한 있음 (사용자 동작 안에서만 허용)
- Firefox를 제외하면 dispatchEvent로 클립보드 이벤트 생성 불가

---

## 4️⃣ 요약

| 이벤트 | 설명 | 특징 |
|--------|-------|------|
| change | 값 변경 후 실행 | 텍스트 입력: 포커스 벗어날 때 |
| input | 값 입력될 때마다 실행 | 키보드 외 입력도 포함, preventDefault() 불가 |
| cut, copy, paste | 잘라내기/복사/붙여넣기 | preventDefault()로 차단 가능, event.clipboardData 제공 |

---

✅ 실습 코드나 추가 설명 필요하면 알려주세요!
