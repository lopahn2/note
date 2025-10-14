
# 📦 브라우저 창 사이즈와 스크롤

## 1️⃣ 브라우저 창 크기 구하기

- 브라우저 창(스크롤바 제외된 **보이는 영역**)의 너비와 높이:

  ```javascript
  document.documentElement.clientWidth
  document.documentElement.clientHeight
  ```

- 참고: `window.innerWidth`와 `innerHeight`도 크기를 구할 수 있지만, **스크롤바를 포함**하므로 정확한 콘텐츠 영역 크기를 구할 땐 `document.documentElement`를 사용하는 게 안전합니다.

---

## 2️⃣ 전체 문서 크기 구하기

**문서 전체 크기** (스크롤 포함) 구하려면 아래와 같이 6개 중 최대값을 사용:

```javascript
let scrollHeight = Math.max(
  document.body.scrollHeight, document.documentElement.scrollHeight,
  document.body.offsetHeight, document.documentElement.offsetHeight,
  document.body.clientHeight, document.documentElement.clientHeight
);
```

> ⚠️ 이유는 딱히 없고, 옛날 브라우저들에서 생긴 버그나 특성 때문. 그냥 이렇게 쓰세요!

---

## 3️⃣ 스크롤 상태 구하기

- 현재 스크롤 위치 (세로/가로):

  ```javascript
  window.pageYOffset  // 세로
  window.pageXOffset  // 가로
  ```

- `document.documentElement.scrollTop` 또는 `scrollLeft`도 가능하지만, **Safari 예외 처리** 필요.  
  (구버전 Safari에서는 `document.body.scrollTop`/`scrollLeft`를 써야 했음)

> 🌟 초보자 팁: **window.pageYOffset/pageXOffset** 쓰면 브라우저 호환성 걱정 NO!

---

## 4️⃣ 스크롤 이동하기

- 현재 위치 기준으로 상대 이동:

  ```javascript
  window.scrollBy(x, y)
  ```

- 특정 좌표로 절대 이동:

  ```javascript
  window.scrollTo(pageX, pageY)
  ```

- 특정 요소가 보이도록 이동:

  ```javascript
  elem.scrollIntoView(true)    // 요소 상단이 창 상단에 붙도록
  elem.scrollIntoView(false)   // 요소 하단이 창 하단에 붙도록
  ```

> 🌟 주의: 스크롤 이동은 **DOM이 완전히 로드된 이후**(DOMContentLoaded 이후) 실행해야 정상 작동!

---

## 5️⃣ 스크롤 막기 (숨기기)

- 페이지 스크롤 잠그기:

  ```javascript
  document.body.style.overflow = 'hidden'
  ```

- 다시 풀기:

  ```javascript
  document.body.style.overflow = ''
  ```

⚠️ **주의**: 스크롤바가 사라지면 창 너비가 변해 콘텐츠가 '움찔'할 수 있음.  
이때 `document.body.clientWidth` 값을 비교해서 패딩으로 보정해주는 게 좋음.

---

## 6️⃣ 반드시 기억하세요

✅ `clientWidth`/`clientHeight`: **스크롤바 제외된 보이는 크기**  
✅ `scrollHeight`/`scrollWidth`: **전체 크기** (스크롤 포함)  
✅ `scrollTop`/`scrollLeft`: 스크롤 위치 (요소 또는 문서)  
✅ `window.pageYOffset`/`pageXOffset`: 스크롤 위치 (브라우저 호환)  
✅ 스크롤 이동: `scrollBy`, `scrollTo`, `scrollIntoView`  
✅ 스크롤 고정: `overflow: hidden` (단, 너비 보정 주의)  
✅ **HTML 문서엔 꼭 `<!DOCTYPE html>` 선언하기!** (안 그러면 이상한 값 나올 수 있음)
