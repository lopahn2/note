
# 🌊 이벤트 버블링과 캡처링 완벽 정리

## 1️⃣ 이벤트 흐름 단계

| 단계 | 설명 | 등록 방법 |
| --- | --- | --- |
| 캡처링 단계 | 최상위 → 타깃으로 내려가는 중 | addEventListener(..., true) |
| 타깃 단계 | 이벤트 발생 요소 | 자동 실행 (따로 등록 X) |
| 버블링 단계 | 타깃 → 최상위로 올라가는 중 | addEventListener(..., false) 또는 기본(onclick 등) |

---

## 2️⃣ 버블링(Bubbling)

- 이벤트는 **타깃 요소**에서 시작해 부모 → 조상 → document로 올라감
- 대부분의 이벤트는 버블링됨 (예: click, keydown...)
- `event.target`: 실제 이벤트 발생 요소
- `this`(=event.currentTarget): 현재 핸들러가 걸린 요소

**예시:**

```html
<form onclick="alert('form')">
  <div onclick="alert('div')">
    <p onclick="alert('p')">P</p>
  </div>
</form>
```

P 클릭 → "p" → "div" → "form" 순서로 실행 / 이때 P가 `event.target`, form이 `this`가 됨 

---

## 3️⃣ 버블링 멈추기

- **event.stopPropagation()**: 상위로 전파 막음, 같은 요소 내 다른 핸들러는 실행
- **event.stopImmediatePropagation()**: 같은 요소의 다른 핸들러도 실행 안 됨

✅ 주의: **특별한 이유 없으면 버블링 멈추지 마세요!** 해당 영역은 죽은 영역이 됩니다. 그래서 logger들이 사용자의 이벤트를 로깅하지 못해요. 

---

## 4️⃣ 캡처링(Capturing)

- 이벤트가 **최상위 → 타깃**으로 내려가는 단계
- 사용법:

```javascript
elem.addEventListener("click", handler, true); // capture: true
```

- 실무에서 잘 안 쓰임 (예외적 케이스에만 사용)

---

## 5️⃣ 단계별 흐름 예시

```html
<form>FORM
  <div>DIV
    <p>P</p>
  </div>
</form>
<script>
  for (let elem of document.querySelectorAll("*")) {
    elem.addEventListener("click", e => alert("캡처링: " + elem.tagName), true);
    elem.addEventListener("click", e => alert("버블링: " + elem.tagName));
  }
</script>
```

P 클릭 → 캡처링 단계 (HTML → BODY → FORM → DIV) → 타깃 단계(P) → 버블링 단계 (DIV → FORM → BODY → HTML)

---

## 6️⃣ 이벤트 객체 중요 프로퍼티

| 프로퍼티 | 설명 |
| --- | --- |
| event.target | 실제 이벤트 발생 요소 |
| event.currentTarget (this) | 현재 핸들러가 걸린 요소 |
| event.eventPhase | 단계 구분 (1: 캡처링, 2: 타깃, 3: 버블링) |

---

## 7️⃣ removeEventListener 주의

- 핸들러 제거 시, 등록 단계(`true`/`false`) 일치해야 함
- 함수 참조 동일해야 함

```javascript
elem.addEventListener("click", handler, true);
elem.removeEventListener("click", handler, true); // 반드시 true
```

---

## 8️⃣ 최종 정리

✅ 이벤트는 타깃 요소에서 시작해 버블링 (거의 모든 이벤트)  
✅ 캡처링은 특별한 경우에만 (capture: true)  
✅ 버블링 중 event.stopPropagation()으로 상위 전파 막을 수 있음 (주의!)  
✅ event.target: 실제 발생 요소, event.currentTarget: 핸들러 등록 요소  
✅ removeEventListener: 같은 함수 참조, 같은 단계 필요

---

버블링과 캡처링은 **이벤트 위임(event delegation)** 의 기반입니다.  
다음 챕터에서 더 자세히 알아봅시다!
