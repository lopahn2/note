
# 속성과 프로퍼티 정리

## 1️⃣ 기본 개념

| 구분 | 속성 (attribute) | 프로퍼티 (property) |
| ---- | ---------------- | ------------------ |
| 위치 | HTML에 작성 | DOM 객체에 생성 |
| 타입 | 문자열 | 모든 타입 가능 (boolean, object 등) |
| 이름 | 대소문자 구분 없음 | 대소문자 구분함 |
| 접근 | getAttribute, setAttribute | 객체 프로퍼티로 바로 접근 (elem.id 등) |

- HTML 속성 → DOM 프로퍼티로 매핑되는 경우도 있고, 안 되는 경우도 있음
- 비표준 속성은 프로퍼티로 매핑되지 않음

---

## 2️⃣ DOM 프로퍼티

- 일반 객체처럼 프로퍼티 추가/수정 가능
```js
document.body.myData = { name: "Caesar" };
document.body.sayHi = function() { alert(this.tagName); };
```

---

## 3️⃣ HTML 속성 접근 메서드

| 메서드 | 설명 |
| ------ | ---- |
| elem.hasAttribute(name) | 속성 존재 여부 확인 |
| elem.getAttribute(name) | 속성값 가져오기 |
| elem.setAttribute(name, value) | 속성값 설정 |
| elem.removeAttribute(name) | 속성 제거 |
| elem.attributes | 모든 속성 컬렉션 반환 (name, value 프로퍼티 포함) |

예시:
```js
alert(elem.getAttribute('data-info'));
```

---

## 4️⃣ 속성-프로퍼티 동기화

- **대부분** 양방향 동기화
```js
input.setAttribute('id', 'newId'); // 속성 → 프로퍼티 동기화
input.id = 'anotherId'; // 프로퍼티 → 속성 동기화
```

- 예외: `value`처럼 초기값만 속성 → 프로퍼티 동기화되고, 프로퍼티 변경은 속성에 반영 안 됨

```js
input.setAttribute('value', 'default'); // 속성 변경 → 프로퍼티 반영
input.value = 'new'; // 프로퍼티 변경 → 속성에 반영 X
```

---

## 5️⃣ DOM 프로퍼티 타입

- 프로퍼티는 타입별 데이터 저장 (boolean, object 등)
```js
alert(input.checked); // true (boolean)
alert(div.style); // [object CSSStyleDeclaration] (object)
```

💡 getAttribute로 가져오면 항상 문자열

---

## 6️⃣ 비표준 속성 & dataset

- 비표준 속성 사용 가능: `data-*` 형식
- `dataset` 객체로 접근
```html
<div data-user-id="123" data-role="admin"></div>
<script>
  alert(div.dataset.userId); // 123
  alert(div.dataset.role); // admin
</script>
```

- dataset 값 수정도 가능
```js
div.dataset.role = "guest";
```

---

## 7️⃣ 과제 예제

### 📌 data-widget-name 속성 읽기

```html
<div data-widget-name="menu"></div>
<script>
  let elem = document.querySelector('[data-widget-name]');
  alert(elem.dataset.widgetName);
  // 또는
  alert(elem.getAttribute('data-widget-name'));
</script>
```

### 📌 외부 링크 주황색으로 만들기

```js
let selector = 'a[href*="://"]:not([href^="http://internal.com"])';
let links = document.querySelectorAll(selector);

links.forEach(link => link.style.color = 'orange');
```

---

## 8️⃣ 결론

- **속성**은 HTML에 작성, **프로퍼티**는 JS에서 다루는 객체
- **getAttribute** / **setAttribute**: 속성 접근
- **프로퍼티 직접 접근**: DOM 프로퍼티 (elem.id, elem.value 등)
- 비표준 데이터는 `data-*` + `dataset`을 사용해 안전하게 저장
