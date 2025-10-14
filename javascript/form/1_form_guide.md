
# 📝 자바스크립트 Form 프로퍼티와 메서드 정리

## 1️⃣ Form 요소 탐색

- `document.forms` → 문서 내 모든 폼의 컬렉션
- `document.forms.name` 또는 `document.forms[index]` → 특정 폼 접근
- `form.elements` → 폼 내 요소 컬렉션

### 예시
```html
<form name="myForm">
  <input name="one" value="1">
  <input name="two" value="2">
</form>

<script>
  let form = document.forms.myForm;
  let input = form.elements.one;
  alert(input.value); // 1
</script>
```

---

## 2️⃣ 같은 name의 요소들 (예: 라디오 버튼)

- `form.elements[name]`은 컬렉션으로 반환

```html
<form>
  <input type="radio" name="age" value="10">
  <input type="radio" name="age" value="20">
</form>

<script>
  let radios = document.forms[0].elements.age;
  alert(radios[0].value); // 10
</script>
```

---

## 3️⃣ fieldset의 elements

- `<fieldset>`은 내부 요소들을 `elements`로 접근 가능

```html
<form>
  <fieldset name="group">
    <input name="login">
  </fieldset>
</form>

<script>
  let fieldset = form.elements.group;
  let input = fieldset.elements.login;
</script>
```

---

## 4️⃣ 짧은 표기법: form.name

- `form.login` → `form.elements.login`과 동일
- name 변경 시 주의 (기존 이름도 접근 가능)

---

## 5️⃣ element.form (역참조)

- 요소에서 자신이 속한 폼에 접근 가능

```html
<form id="form">
  <input name="login">
</form>

<script>
  let input = form.login;
  alert(input.form); // <form> 요소
</script>
```

---

## 6️⃣ input, textarea, select 프로퍼티

| 요소 | 주요 프로퍼티 |
|------|----------------|
| input | value, checked (체크박스/라디오) |
| textarea | value (innerHTML X) |
| select | value, options[], selectedIndex |

### select 예시

```javascript
select.options[2].selected = true;
select.selectedIndex = 2;
select.value = 'banana';
```

### 다중 선택 (multiple)

```javascript
let selected = Array.from(select.options)
  .filter(o => o.selected)
  .map(o => o.value);
```

---

## 7️⃣ Option 생성자

```javascript
let option = new Option("Text", "value", true, true);
```

- `defaultSelected`: HTML 속성 `selected` 생성
- `selected`: 선택 상태

---

## 8️⃣ select에 옵션 추가 실습

### 과제

```html
<select id="genres">
  <option value="rock">Rock</option>
  <option value="blues" selected>Blues</option>
</select>

<script>
  // 선택한 옵션 표시
  let selectedOption = genres.options[genres.selectedIndex];
  alert(selectedOption.value);

  // 새 옵션 추가
  let newOption = new Option("Classic", "classic");
  genres.append(newOption);

  // 새 옵션 선택
  newOption.selected = true;
</script>
```

---

✅ 폼 요소의 값과 상태는 `.value`, `.checked`, `.selected`로 접근!  
✅ `form.elements`와 `element.form` 기억해 두면 편리!

---

📌 실습이나 추가 설명 필요하면 알려주세요!
