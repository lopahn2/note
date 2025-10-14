
# DOM 탐색 정리

## 🧭 DOM 탐색의 기본

### 1️⃣ DOM 탐색의 시작점: `document`

- DOM 탐색은 항상 `document` 객체에서 시작
- 주요 진입점 프로퍼티
    - `document.documentElement` → `<html>` 요소
    - `document.body` → `<body>` 요소
    - `document.head` → `<head>` 요소

### 2️⃣ `document.body`가 `null`일 수 있는 경우

- `<head>` 안의 스크립트에서 `document.body`를 읽으면 아직 DOM 트리에 추가되지 않았을 수 있음
- DOM이 로드되기 전에 접근하면 `null` 반환
- 주의: DOMContentLoaded 이벤트 후에 접근하는 게 안전

### 3️⃣ 자식/후손 노드 탐색

| 용어 | 설명 |
|:---|:---|
| 자식 노드 (child) | 바로 아래에 있는 요소 |
| 후손 노드 (descendant) | 자식, 자식의 자식까지 포함 |

- `childNodes`: 모든 자식 노드 (텍스트, 주석 포함)
- `children`: 요소 노드만 포함
- `firstChild`, `lastChild`: 첫/마지막 자식 노드 (모든 노드 포함)
- `firstElementChild`, `lastElementChild`: 첫/마지막 자식 **요소** 노드

### 4️⃣ 형제 및 부모 노드 탐색

| 프로퍼티 | 설명 |
|:---|:---|
| `parentNode` | 부모 노드 (모든 종류) |
| `parentElement` | 부모 **요소** 노드 |
| `nextSibling` | 다음 형제 노드 |
| `previousSibling` | 이전 형제 노드 |
| `nextElementSibling` | 다음 형제 **요소** 노드 |
| `previousElementSibling` | 이전 형제 **요소** 노드 |

### 5️⃣ DOM 컬렉션의 특징

- 배열처럼 보이지만 **배열이 아님**
- `for...of` 사용 가능
- 배열 메서드 (`filter`, `map` 등)는 직접 사용 불가 → `Array.from()` 사용
- **라이브 컬렉션**: DOM 변경 시 컬렉션도 자동 업데이트

### 6️⃣ 테이블 전용 탐색 프로퍼티

| 프로퍼티 | 설명 |
|:---|:---|
| `table.rows` | `<tr>` 요소 컬렉션 |
| `table.tBodies` | `<tbody>` 요소 컬렉션 |
| `table.caption`, `tHead`, `tFoot` | 해당 요소 참조 |
| `tr.cells` | `<td>` / `<th>` 컬렉션 |
| `td.cellIndex` | `<tr>` 내에서 몇 번째 셀인지 |

### 7️⃣ 예제 코드

**대각선 선택하기**

```javascript
for (let i = 0; i < table.rows.length; i++) {
  table.rows[i].cells[i].style.backgroundColor = 'red';
}
```

### 8️⃣ 실습 문제 해답 요약

#### DOM 노드 선택하기

| 목표 | 접근 방법 |
|:---|:---|
| `<div>` | `document.body.firstElementChild` |
| `<ul>` | `document.body.lastElementChild` |
| 두 번째 `<li>` (Pete) | `document.body.lastElementChild.lastElementChild` |

#### 형제 노드 관련 질문

- `elem.lastChild.nextSibling`은 항상 `null`
- `elem.children[0].previousSibling`은 **null 아닐 수도 있음** (앞에 텍스트 노드 있을 수 있음)

---

🌿 다음엔 DOM 조작에 대해 배워보겠습니다!
