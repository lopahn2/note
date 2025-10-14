
# 브라우저 기본 동작과 event.preventDefault()

## 1. 브라우저의 기본 동작이란?

HTML 요소들은 특정 이벤트에 대해 브라우저가 자동으로 수행하는 기본 동작이 있습니다.

예를 들어:

- `<a>` 태그를 클릭하면 `href`에 지정된 URL로 이동합니다.
- `<form>`에서 제출 버튼을 클릭하면 폼 데이터가 서버로 전송됩니다.
- 마우스를 드래그하면 텍스트가 선택됩니다.

이러한 기본 동작은 사용자의 편의를 위해 제공되지만, 때로는 자바스크립트를 통해 제어하거나 막아야 할 필요가 있습니다.

## 2. 기본 동작을 막는 방법

### 2.1 `event.preventDefault()` 사용

자바스크립트에서 이벤트 핸들러 내에서 `event.preventDefault()`를 호출하면 해당 이벤트의 기본 동작을 막을 수 있습니다.

```javascript
document.querySelector('a').addEventListener('click', function(event) {
  event.preventDefault(); // 링크 이동을 막음
  alert('링크 클릭이 막혔습니다.');
});
```

### 2.2 `return false` 사용

HTML에서 이벤트 핸들러를 속성으로 지정할 때, `return false`를 사용하면 기본 동작을 막을 수 있습니다.

```html
<a href="https://example.com" onclick="alert('링크 클릭이 막혔습니다.'); return false;">클릭</a>
```

하지만 이 방법은 `on<event>` 속성을 사용할 때만 유효하며, `addEventListener`를 사용할 경우에는 동작하지 않습니다.

## 3. `event.preventDefault()`의 사용 예시

### 3.1 링크의 기본 동작 막기

```javascript
document.querySelector('a').addEventListener('click', function(event) {
  event.preventDefault();
  alert('링크 이동이 막혔습니다.');
});
```

### 3.2 폼 제출 막기

```javascript
document.querySelector('form').addEventListener('submit', function(event) {
  event.preventDefault();
  alert('폼 제출이 막혔습니다.');
});
```

### 3.3 체크박스 선택 막기

```javascript
document.querySelector('input[type="checkbox"]').addEventListener('click', function(event) {
  event.preventDefault();
  alert('체크박스 선택이 막혔습니다.');
});
```

## 4. `event.preventDefault()`와 `event.stopPropagation()`의 차이점

- `event.preventDefault()`: 이벤트의 기본 동작을 막습니다.
- `event.stopPropagation()`: 이벤트의 전파를 막습니다.

두 메서드는 서로 다른 목적을 가지고 있으며, 상황에 따라 함께 사용되기도 합니다.

## 5. `event.defaultPrevented` 속성

이 속성은 이벤트의 기본 동작이 막혔는지를 확인할 수 있습니다.

```javascript
document.querySelector('a').addEventListener('click', function(event) {
  if (!event.defaultPrevented) {
    event.preventDefault();
    alert('기본 동작이 막혔습니다.');
  }
});
```

## 6. `addEventListener`의 `passive` 옵션

모바일 브라우저에서 스크롤 성능을 향상시키기 위해 `addEventListener`에 `passive: true` 옵션을 사용할 수 있습니다.

```javascript
document.addEventListener('touchstart', function(event) {
  // ...
}, { passive: true });
```

이 옵션을 사용하면 `event.preventDefault()`를 호출할 수 없으므로, 스크롤을 막을 필요가 있는 경우에는 `passive: false`로 설정해야 합니다.

## 7. 주의사항

- `event.preventDefault()`는 취소 가능한 이벤트(`cancelable: true`)에서만 동작합니다. 취소할 수 없는 이벤트에서는 효과가 없습니다.
- `return false`는 `on<event>` 속성을 사용할 때만 기본 동작을 막을 수 있습니다. `addEventListener`를 사용할 경우에는 `event.preventDefault()`를 사용해야 합니다.
- `event.preventDefault()`는 브라우저의 기본 동작만 막을 뿐 이벤트 버블링을 막지 않습니다.

---

이 문서는 초보 개발자도 이해하기 쉽도록 브라우저의 기본 동작과 이를 제어하는 방법에 대해 설명하였습니다.
