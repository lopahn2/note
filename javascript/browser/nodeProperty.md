
# DOM 주요 노드 프로퍼티 학습 정리

## 1️⃣ DOM 노드의 클래스 계층 구조

모든 DOM 노드는 공통 조상인 **EventTarget**부터 출발하며, 아래처럼 계층 구조를 가지고 있어요.

```
EventTarget
  └── Node
        ├── Element
        │     ├── HTMLElement
        │     │     ├── HTMLInputElement (input 태그 전용)
        │     │     ├── HTMLBodyElement (body 태그 전용)
        │     │     ├── ...
        │     ├── SVGElement
        │     ├── ...
        ├── Text (텍스트 노드)
        ├── Comment (주석 노드)
        ├── Document (HTMLDocument 등)
```

**각 클래스의 역할:**
- **EventTarget**: 이벤트 기능을 제공하는 추상 클래스
- **Node**: 트리 탐색 관련 프로퍼티 (parentNode, nextSibling, childNodes 등)
- **Element**: 요소 전용 탐색 메서드 (querySelector, getElementsByTagName 등)
- **HTMLElement**: HTML 전용 요소의 공통 클래스
- **HTMLInputElement**: `<input>` 전용 클래스

💡 **태그별 DOM 클래스 이름 확인:**
```js
alert(document.body.constructor.name); // HTMLBodyElement
alert(elem instanceof HTMLInputElement); // true/false
```

## 2️⃣ DOM 노드 타입 확인

### nodeType
- **1**: 요소 노드
- **3**: 텍스트 노드
- **9**: 문서 객체
```js
alert(document.body.nodeType); // 1
alert(document.firstChild.nodeType); // 3 (텍스트 노드)
alert(document.nodeType); // 9
```

### nodeName vs tagName
- **nodeName**: 모든 노드에서 사용 가능
- **tagName**: 요소 노드에서만 사용
- HTML 모드에선 항상 **대문자** 반환

```js
alert(document.body.nodeName); // BODY
alert(document.body.tagName); // BODY
```

💡 **주석 노드 예시**
```js
body.innerHTML = "<!-- BODY -->";
alert(body.firstChild.data); // "BODY"
```

## 3️⃣ 요소 내용 읽고 쓰기

### innerHTML
- 요소 안의 HTML 코드 전체를 읽거나 쓰기
```js
document.body.innerHTML = "<b>새로운 내용</b>";
```

### outerHTML
- 요소 전체 HTML을 반환하거나 교체
```js
elem.outerHTML = "<p>새로운 요소</p>";
```

💡 **주의:** `outerHTML`로 교체해도 기존 변수(elem)는 그대로 남아있음

### textContent
- 요소의 순수 텍스트(태그 제외)를 읽거나 씀
- **XSS 방지**에 유리

### nodeValue/data
- 텍스트 노드, 주석 노드의 내용 읽거나 쓰기

### hidden
- `true`면 CSS의 `display: none`과 같음
- HTML 속성 `<div hidden>` 또는 JS에서 `elem.hidden = true` 사용

## 4️⃣ console.log vs console.dir

| 명령어 | 특징 |
|--------|---------|
| console.log(elem) | DOM 트리 형태 출력 |
| console.dir(elem) | JS 객체로 출력 (프로퍼티 확인용) |

## 5️⃣ 주의할 점

💡 `innerHTML +=`은 덧셈처럼 보이지만 사실상 **전체 덮어쓰기**라서 성능에 주의!  
💡 `textContent` 사용 시 HTML 태그는 문자로 처리됨 → XSS 방지 가능!  
💡 `hidden`은 요소 숨길 때 깔끔한 방법 (CSS `display:none`과 동일 효과)  
💡 `nodeType`, `instanceof`, `constructor.name`을 적절히 활용해 타입 확인!

## 6️⃣ 과제 풀이

### li 노드 텍스트 출력 & 후손 li 개수 출력

```js
for (let li of document.querySelectorAll('li')) {
  let title = li.firstChild.data.trim(); // li 안 텍스트
  let count = li.getElementsByTagName('li').length; // 후손 li 개수

  console.log(title + ': ' + count);
}
```

### DOM 계층 구조 - document는 어디?
```js
console.log(document.constructor.name); // HTMLDocument
console.log(HTMLDocument.prototype.__proto__.constructor.name); // Document
console.log(Document.prototype.__proto__.constructor.name); // Node
```

---

**✅ DOM은 클래스 계층 기반으로 동작하며, 요소마다 고유한 프로퍼티를 가집니다!**  
**✅ 명세서나 console.dir()로 확인하며 학습해 보세요!**
