
# 🚀 이벤트 위임 (Event Delegation)

## 1️⃣ 이벤트 위임이란?

- **핸들러를 개별 요소마다 할당하지 않고**, **공통 조상에 단 하나의 핸들러**를 두어 하위 요소의 이벤트를 처리하는 패턴
- 핵심: `event.target`을 통해 실제 클릭된 요소를 구분

---

## 2️⃣ 동작 원리

| 단계 | 설명 |
| --- | --- |
| 1 | 상위 요소(컨테이너)에 핸들러를 단다 |
| 2 | 이벤트 발생 시 `event.target`으로 실제 타겟 요소 확인 |
| 3 | 필요한 작업 수행 (ex: 클릭된 요소 강조) |

---

## 3️⃣ 예제: <td> 클릭 시 강조

```javascript
let selectedTd;

table.onclick = function(event) {
  let td = event.target.closest('td');
  if (!td || !table.contains(td)) return;
  highlight(td);
};

function highlight(td) {
  if (selectedTd) selectedTd.classList.remove('highlight');
  selectedTd = td;
  selectedTd.classList.add('highlight');
}
```

✅ `closest()`로 상위 td 탐색  
✅ `contains()`로 테이블 내 요소인지 확인

---

## 4️⃣ 이벤트 위임의 장점

✅ 핸들러 1개로 여러 요소 처리 (메모리 절약)  
✅ 동적 추가/삭제된 요소도 자동 처리  
✅ HTML에 의미론적 속성(data-*) 사용 가능

---

## 5️⃣ data-* 속성을 활용한 행동 패턴

### 버튼 기능 구현 예시

```html
<div id="menu">
  <button data-action="save">저장</button>
  <button data-action="load">불러오기</button>
</div>

<script>
  class Menu {
    constructor(elem) {
      this._elem = elem;
      elem.onclick = this.onClick.bind(this);
    }

    save() { alert("저장!"); }
    load() { alert("불러오기!"); }

    onClick(event) {
      let action = event.target.dataset.action;
      if (action) this[action]();
    }
  }

  new Menu(menu);
</script>
```

---

### 카운터

```html
<input type="button" value="1" data-counter>
<script>
  document.addEventListener('click', event => {
    if (event.target.dataset.counter != undefined) event.target.value++;
  });
</script>
```

### 토글러

```html
<button data-toggle-id="subscribe">구독폼</button>
<form id="subscribe" hidden>...</form>

<script>
  document.addEventListener('click', event => {
    let id = event.target.dataset.toggleId;
    if (!id) return;
    document.getElementById(id).hidden = !document.getElementById(id).hidden;
  });
</script>
```

✅ `data-*` 속성: 행동(behavior) 정의  
✅ 문서 전체 핸들러는 반드시 `addEventListener` 사용 (충돌 방지)

---

## 6️⃣ 이벤트 위임의 한계

⚠️ 반드시 **버블링**되는 이벤트여야 함  
⚠️ `event.stopPropagation()`이 하위 요소에서 호출되면 위임 동작 X  
⚠️ 컨테이너 핸들러는 모든 이벤트를 처리 → CPU 부하 가능 (대체로 무시해도 OK)

---

## 7️⃣ 요약

✅ 이벤트 위임: **컨테이너 하나로 다수 요소 처리**  
✅ `event.target`, `closest()`, `data-*` 속성 활용  
✅ 선언적 행동 구현 → 코드 간결, 유지보수 용이

🔥 이벤트 위임은 **강력한 패턴**! 실무에서 꼭 활용해 보세요!
