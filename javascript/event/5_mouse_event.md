
# 🖱️ 자바스크립트 마우스 이벤트 정리

## 1️⃣ 마우스 이벤트란?

- 마우스를 클릭하거나 움직일 때 발생하는 다양한 이벤트를 의미해.
- **주의:** 마우스 이벤트는 꼭 "마우스"라는 장치에서만 발생하는 게 아니야. **터치스크린** 기기(핸드폰, 태블릿 등)에서도 발생할 수 있어!

---

## 2️⃣ 주요 마우스 이벤트

| 이벤트 | 설명 |
|-------|-------|
| mousedown | 마우스 버튼을 누를 때 발생 |
| mouseup | 마우스 버튼을 뗄 때 발생 |
| click | mousedown + mouseup 순서로 발생 |
| dblclick | 빠르게 두 번 클릭할 때 발생 (요즘은 잘 안 씀) |
| mouseover | 요소에 마우스가 들어올 때 발생 |
| mouseout | 요소에서 마우스가 나갈 때 발생 |
| mousemove | 마우스가 움직일 때 발생 |
| contextmenu | 마우스 오른쪽 버튼 클릭 시 발생 |

⚠️ **click**은 항상 mousedown → mouseup 순서 뒤에 발생!

---

## 3️⃣ 마우스 이벤트의 순서

예를 들어, **왼쪽 버튼 클릭** 시 발생하는 순서:
1. `mousedown`
2. `mouseup`
3. `click`

---

## 4️⃣ `event.button` (어떤 버튼이 눌렸는지)

| 버튼 | `event.button` 값 |
|-------|------------------|
| 왼쪽(주요 버튼) | 0 |
| 가운데(스크롤 휠 버튼) | 1 |
| 오른쪽(컨텍스트 메뉴) | 2 |
| X1(뒤로 가기 버튼) | 3 |
| X2(앞으로 가기 버튼) | 4 |

⚠️ click과 contextmenu 이벤트에선 `button`을 잘 안 씀. mousedown, mouseup 이벤트에서 주로 사용해!

---

## 5️⃣ 보조키 (modifier keys)

마우스 이벤트엔 어떤 **보조키**(Shift, Alt, Ctrl, Cmd)가 눌렸는지도 함께 알려줘.

| 프로퍼티 | 설명 |
|----------|-------|
| shiftKey | Shift 키 눌림 여부 |
| altKey | Alt(Windows) 또는 Opt(Mac) 키 눌림 여부 |
| ctrlKey | Ctrl 키 눌림 여부 |
| metaKey | Cmd(Mac) 키 눌림 여부 |

**Mac 사용자를 고려해야 해!**  
Ctrl + Click → contextmenu로 해석되므로, Cmd 키를 함께 고려해주는 코드 작성 필요.

```javascript
if (event.ctrlKey || event.metaKey) { /* Mac과 Windows 사용자 모두 지원 */ }
```

---

## 6️⃣ 좌표 정보

| 프로퍼티 | 설명 |
|----------|-------|
| clientX, clientY | **뷰포트(브라우저 창)** 기준 좌표 (스크롤 영향 없음) |
| pageX, pageY | **페이지 전체** 기준 좌표 (스크롤 반영됨) |

---

## 7️⃣ 선택 방지하기

글자 선택을 막고 싶다면? `mousedown` 이벤트에서 기본 동작을 막아줘!

```html
<b ondblclick="alert('클릭!')" onmousedown="return false">
  여기를 더블클릭해주세요.
</b>
```

이렇게 하면 **드래그로 글자가 선택되는 것**도 막을 수 있어!

---

## 8️⃣ 복사 방지하기

`oncopy` 이벤트를 활용해 복사 기능을 막을 수 있어.

```html
<div oncopy="alert('복사 금지!'); return false;">
  중요한 콘텐츠
</div>
```

⚠️ 완벽한 보안은 아님! (페이지 소스를 보는 건 막을 수 없음)

---

## 9️⃣ 실습: 선택 가능한 리스트

요구사항:
- 클릭하면 하나만 선택
- Ctrl(Mac에선 Cmd) + 클릭하면 여러 개 선택
- 텍스트 선택 방지

**예제 코드:**

```html
<!DOCTYPE HTML>
<html>

<head>
  <meta charset="utf-8">
  <style>
    .selected {
      background: #0f0;
    }
    li {
      cursor: pointer;
    }
  </style>
</head>

<body>

  선택하고자 하는 요소를 클릭하세요.
  <br>

  <ul id="ul">
    <li>React</li>
    <li>Angular</li>
    <li>Vue</li>
    <li>Svelte</li>
    <li>Blazor</li>
  </ul>

  <script>
    ul.onclick = function(event) {
      if (event.target.tagName != "LI") return;

      if (event.ctrlKey || event.metaKey) {
        toggleSelect(event.target);
      } else {
        singleSelect(event.target);
      }
    }

    ul.onmousedown = function() {
      return false;
    };

    function toggleSelect(li) {
      li.classList.toggle('selected');
    }

    function singleSelect(li) {
      let selected = ul.querySelectorAll('.selected');
      for(let elem of selected) {
        elem.classList.remove('selected');
      }
      li.classList.add('selected');
    }
  </script>

</body>
</html>
```

---

## 10️⃣ 요약

- `button` → 어떤 버튼을 눌렀는지
- `shiftKey`, `altKey`, `ctrlKey`, `metaKey` → 보조키 상태
- `clientX`, `clientY` → 뷰포트 기준 좌표
- `pageX`, `pageY` → 페이지 기준 좌표
- MacOS 사용자도 고려해 코드를 작성하자 (`ctrlKey`와 `metaKey` 함께 고려)
- 기본 동작(글자 선택 등)을 막을 땐 `onmousedown="return false"` 활용

---

**다음 주제**: 포인터 이벤트와 포인터 아래 요소 추적
