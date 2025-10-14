
# 🖱️ 자바스크립트 마우스 이동 관련 이벤트 정리

## 1️⃣ 마우스 이동 관련 이벤트 종류

| 이벤트 | 설명 |
|--------|-------|
| mouseover | 마우스가 요소 위로 진입할 때 발생 |
| mouseout | 마우스가 요소에서 나갈 때 발생 |
| mouseenter | 요소 위로 진입할 때 발생 (버블링 X) |
| mouseleave | 요소에서 나갈 때 발생 (버블링 X) |
| mousemove | 마우스가 움직일 때마다 발생 |

---

## 2️⃣ mouseover / mouseout의 특징

- `relatedTarget` 프로퍼티를 제공
- 마우스가 요소에 들어오거나 나갈 때, 어디서 어디로 이동했는지 정보 제공

### 이벤트 속성

| 속성 | 설명 |
|------|-------|
| event.target | 이벤트가 발생한 요소 |
| event.relatedTarget | 마우스가 이동해 온/나간 요소 |

### 예시

```javascript
element.onmouseover = function(event) {
  console.log("들어온 곳:", event.target);
  console.log("나간 곳:", event.relatedTarget);
};
```

⚠️ `relatedTarget`은 `null`일 수 있어! (창 밖에서 들어오거나 나간 경우)

---

## 3️⃣ mousemove의 특징

- 마우스가 움직일 때마다 발생 (모든 픽셀마다 발생하는 건 아님)
- 성능 최적화를 위해 브라우저가 주기적으로 체크

⚠️ 마우스를 빠르게 움직이면 일부 요소를 "건너뛸 수" 있음 (mouseover, mouseout 이벤트가 중간 요소에 안 발생할 수 있음)

---

## 4️⃣ mouseout이 자식 요소로 이동할 때도 발생하는 이유

- 마우스는 "한 번에 하나의 요소" 위에만 있을 수 있음 (가장 안쪽, z-index 높은 요소)
- 부모에서 자식으로 들어가면, 부모에선 mouseout이 발생

```html
<div id="parent">
  <div id="child"></div>
</div>
```

- `parent`에서 `child`로 이동 → `mouseout`이 `parent`에서 발생
- 하지만 `mouseover`는 `parent`에서도 버블링되어 올라옴

### 해결 방법

- `mouseout` 핸들러에서 `relatedTarget`을 확인해
- 만약 아직 부모 안에 있으면 무시!

---

## 5️⃣ mouseenter / mouseleave

| 이벤트 | 특징 |
|--------|-------|
| mouseenter | 요소에 들어올 때만 발생 (버블링 X, 자식 요소 이동 무시) |
| mouseleave | 요소에서 나갈 때만 발생 (버블링 X, 자식 요소 이동 무시) |

### 주의사항

- 버블링 안 됨 → 이벤트 위임(Delegation)에 사용 불가
- 간단한 경우엔 좋지만, 많은 요소를 관리해야 한다면 불리함

---

## 6️⃣ 이벤트 위임 (mouseover/out 활용)

`mouseenter/leave`는 위임 불가!  
`mouseover/out`은 버블링되므로 위임 가능!

### 예시: 테이블의 모든 `<td>`에 이벤트 처리

```javascript
let currentElem = null;

table.onmouseover = function(event) {
  if (currentElem) return; // 이미 안에 있음
  let target = event.target.closest('td');
  if (!target || !table.contains(target)) return;

  currentElem = target;
  onEnter(currentElem);
};

table.onmouseout = function(event) {
  if (!currentElem) return;

  let relatedTarget = event.relatedTarget;
  while (relatedTarget) {
    if (relatedTarget == currentElem) return; // 내부 이동 → 무시
    relatedTarget = relatedTarget.parentNode;
  }

  onLeave(currentElem);
  currentElem = null;
};

function onEnter(elem) {
  elem.style.background = 'pink';
}

function onLeave(elem) {
  elem.style.background = '';
}
```

✅ 특징:
- `<td>` 전체에 대한 들어옴/나감만 처리
- 자식 요소 사이 이동은 무시

---

## 7️⃣ 요약

- `mouseover`/`mouseout`: `relatedTarget` 제공, 버블링 O, 자식 이동 포함
- `mouseenter`/`mouseleave`: 버블링 X, 자식 이동 포함 안 함
- 빠른 마우스 이동 → 중간 요소 건너뛸 수 있음
- 위임 처리 필요하면 `mouseover`/`mouseout` 사용
- 성능 최적화 및 사용자 경험 고려해서 이벤트 선택!
