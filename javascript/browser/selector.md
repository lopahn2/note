
# DOM 요소 검색하기

## 1️⃣ 요소 검색 방법

웹 페이지에서 특정 요소를 찾을 땐, 아래의 메서드들을 사용해요.

| 메서드 | 검색 기준 | 사용 대상 | 컬렉션 갱신 여부 |
|--------|-------------|------------|----------------|
| `querySelector` | CSS 선택자 | ✔ | - |
| `querySelectorAll` | CSS 선택자 | ✔ | - |
| `getElementById` | id | - | - |
| `getElementsByName` | name | - | ✔ |
| `getElementsByTagName` | 태그 이름 | ✔ | ✔ |
| `getElementsByClassName` | class | ✔ | ✔ |

**실무에서는 `querySelector`와 `querySelectorAll`을 주로 사용해요!**  
`getElementBy*` 계열은 오래된 코드에서 볼 수 있으니 참고만!

---

## 2️⃣ 주요 메서드 설명

### 📌 `getElementById(id)`
- **id**가 있는 요소를 찾을 때 사용해요.
- 예시:
  ```html
  <div id="elem">...</div>
  ```
  ```js
  let elem = document.getElementById('elem');
  elem.style.background = 'red';
  ```

💡 **주의!** id로 전역 변수가 생성되지만, 다른 변수와 이름이 겹치면 문제가 생겨요!
```js
let elem = 5; // 기존의 elem 변수와 충돌!
```

---

### 📌 `querySelector`, `querySelectorAll`
- CSS 선택자로 요소를 찾을 때 사용해요.
- 예시:
  ```js
  let elements = document.querySelectorAll('ul > li:last-child');
  ```

💡 `querySelectorAll`은 정적인 컬렉션을 반환해요.  
💡 `querySelector`는 첫 번째 요소만 찾아서 반환해요.

---

### 📌 `matches`, `closest`
- **matches(css)**: 요소가 선택자에 맞는지 확인.
- **closest(css)**: 가장 가까운 조상 요소(자기 자신 포함)를 찾아줘요.

예시:
```js
let chapter = document.querySelector('.chapter');
chapter.closest('.book'); // UL 반환
```

---

### 📌 `getElementsBy*` 계열
- `getElementsByTagName('tag')` - 태그 이름으로 찾기
- `getElementsByClassName('class')` - 클래스 이름으로 찾기
- `getElementsByName('name')` - name 속성으로 찾기 (폼 요소에서 종종 사용)

💡 `getElementsBy*`는 **살아있는 컬렉션**을 반환해요! DOM이 바뀌면 컬렉션도 자동 갱신돼요.  
반면 `querySelectorAll`은 **정적인 컬렉션**이기 때문에, DOM이 바뀌어도 그대로 남아요.

---

## 3️⃣ 실수 주의사항

💡 **'s' 빠뜨리지 않기!**  
`getElementByTagName` ❌ → `getElementsByTagName` ✔  
(컬렉션 반환이니까 s가 꼭 들어가요!)

💡 **컬렉션에 바로 값을 넣으면 안 돼요!**
```js
document.getElementsByTagName('input').value = 5; // ❌
document.getElementsByTagName('input')[0].value = 5; // ✔
```

---

## 4️⃣ 예제 문제

다음 HTML에서 요소를 찾는 방법은?

### HTML 예시
```html
<table id="age-table">
  <tr>
    <td>Age:</td>
    <td>
      <label><input type="radio" name="age" value="young"> 18세 미만</label>
      <label><input type="radio" name="age" value="mature"> 18세 이상, 60세 미만</label>
      <label><input type="radio" name="age" value="senior"> 60세 이상</label>
    </td>
  </tr>
</table>

<form name="search">
  <input type="text">
  <input type="submit">
</form>
```

### 해답
```js
// 1. id="age-table"인 테이블
let table = document.getElementById('age-table');

// 2. 테이블 내 label 요소 모두
table.getElementsByTagName('label');
// 또는
document.querySelectorAll('#age-table label');

// 3. 테이블 내 첫 번째 td
table.rows[0].cells[0];
// 또는
table.getElementsByTagName('td')[0];

// 4. name="search"인 form
let form = document.getElementsByName('search')[0];
// 또는
document.querySelector('form[name="search"]');

// 5. form의 첫 번째 input
form.getElementsByTagName('input')[0];

// 6. form의 마지막 input
let inputs = form.querySelectorAll('input');
inputs[inputs.length - 1];
```

---

## 5️⃣ 추가 메서드
- `elem.contains(elemB)`: `elemB`가 `elem`의 후손이거나 같은 요소면 true 반환.
