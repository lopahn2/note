
# 📄 문서 수정하기 (DOM 조작 기법)

## 1️⃣ 요소 생성 및 삽입

| 메서드 | 설명 |
| ------ | ---- |
| document.createElement(tag) | 요소 생성 |
| document.createTextNode(text) | 텍스트 노드 생성 |
| elem.cloneNode(deep) | 요소 복제 (deep = true: 자손까지 복사) |

💡 예시:
```js
let div = document.createElement('div');
div.className = "alert";
div.innerHTML = "<strong>안녕하세요!</strong>";
document.body.append(div);
```

---

## 2️⃣ 요소 삽입 메서드

| 메서드 | 동작 |
| ------ | ---- |
| node.append() | 마지막에 추가 |
| node.prepend() | 처음에 추가 |
| node.before() | 이전에 추가 |
| node.after() | 다음에 추가 |
| node.replaceWith() | 대체 |
| node.remove() | 삭제 |

💡 문자열 삽입도 가능 (자동으로 텍스트 노드 처리)

---

## 3️⃣ HTML 문자열 삽입: insertAdjacentHTML

```js
elem.insertAdjacentHTML(where, html);
```
| where | 위치 |
| ------ | ---- |
| beforebegin | 요소 앞 |
| afterbegin | 첫 자식 앞 |
| beforeend | 마지막 자식 뒤 |
| afterend | 요소 뒤 |

예시:
```js
div.insertAdjacentHTML('beforebegin', '<p>안녕하세요</p>');
```

---

## 4️⃣ DocumentFragment

- 임시 노드 모음
- append하면 fragment는 사라지고 내부 요소만 DOM에 추가됨

```js
let fragment = new DocumentFragment();
fragment.append(...nodes);
parent.append(fragment);
```

---

## 5️⃣ 구식 메서드

| 메서드 | 설명 |
| ------ | ---- |
| parent.appendChild(node) | 마지막에 추가 |
| parent.insertBefore(newNode, refNode) | refNode 앞에 추가 |
| parent.replaceChild(newNode, oldNode) | 교체 |
| parent.removeChild(node) | 삭제 |

💡 요즘은 append, before, remove 등을 권장

---

## 6️⃣ document.write

- 페이지 로딩 중에만 사용 가능 (이후 호출하면 페이지 내용 삭제됨)
- 과거 코드에서 볼 수 있음

```js
document.write('<b>동적 콘텐츠</b>');
```

---

## 7️⃣ 주요 과제 정리

### ✔️ createTextNode vs innerHTML vs textContent

| 코드 | 특징 |
| --- | --- |
| elem.append(document.createTextNode(text)) | 안전하게 텍스트 삽입 |
| elem.innerHTML = text | 태그로 처리됨 (HTML 해석됨) |
| elem.textContent = text | 안전하게 텍스트 삽입 |

### ✔️ clear(elem): 요소 비우기

```js
function clear(elem) {
  while (elem.firstChild) {
    elem.firstChild.remove();
  }
}
// 또는 elem.innerHTML = '';
```

### ✔️ li 중간에 삽입

```js
one.insertAdjacentHTML('afterend', '<li>2</li><li>3</li>');
```

### ✔️ 표 정렬 (name 기준)

```js
let rows = Array.from(table.tBodies[0].rows);
rows.sort((a, b) => a.cells[0].innerHTML.localeCompare(b.cells[0].innerHTML));
table.tBodies[0].append(...rows);
```

---

✅ 문서 수정은 DOM 조작의 핵심! 다양한 메서드를 익히고 활용해 보세요!
