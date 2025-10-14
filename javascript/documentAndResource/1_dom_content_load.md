
# HTML 문서 로드 이벤트 정리

## 📌 이벤트 종류와 역할

| 이벤트 이름            | 발생 시점                                  | 주 사용 용도                          |
| --------------------- | --------------------------------------- | ------------------------------------ |
| **DOMContentLoaded**  | DOM 트리가 완성되면 (이미지, 스타일은 아직 X) | JS로 DOM 조작 시작 (초기화, 핸들러 등록) |
| **load**              | 모든 리소스(이미지, 스타일 등) 로드 완료 후 | 외부 리소스 크기 확인, 전체 페이지 로드 후 처리 |
| **beforeunload**      | 페이지 떠나기 직전                         | 떠나기 전 사용자에게 확인 요청, 경고창 띄우기 |
| **unload**            | 페이지 완전히 떠날 때                      | 딜레이 없는 작업(예: 통계 전송)       |
| **document.readyState** | 문서의 현재 상태 확인용                   | 상태별 분기 처리(loading, interactive, complete) |

---

## 🎓 이벤트별 상세 설명

### 🧱 1️⃣ DOMContentLoaded

- **언제 발생?**
    - HTML **구조만** 다 파싱되고, DOM 트리가 완성된 후
    - **이미지, CSS 등 외부 리소스는 아직 로드 중일 수 있음**

- **어떻게 등록?**
  ```js
  document.addEventListener("DOMContentLoaded", ready);
  ```

- **주의할 점**
    - `document.onDOMContentLoaded = ...` 방식은 안 됨
    - `<script>` 태그를 만나면 브라우저는 DOM 생성 중단 → 스크립트 실행 후 다시 DOM 생성
    - 따라서 **스크립트 로딩이 끝나야 DOMContentLoaded 발생**

- **실수 주의!**
    - DOMContentLoaded 이벤트는 **이미지 크기** 등에는 사용 불가!
        - 이미지 사이즈는 0으로 나올 수도 있음 😅
    - 스타일시트 로딩은 기다리지 않음 (예외: 스타일시트 바로 아래 `<script>`)

- **예시 코드**
  ```html
  <script>
    function ready() {
      alert('DOM 준비 완료!');
      alert(`이미지 사이즈: ${img.offsetWidth}x${img.offsetHeight}`); // 0x0일 수 있음!
    }

    document.addEventListener("DOMContentLoaded", ready);
  </script>

  <img id="img" src="https://en.js.cx/clipart/train.gif">
  ```

### 🏞️ 2️⃣ load

- **언제 발생?**
    - HTML + 이미지, 스타일 등 **모든 리소스 로드 완료 후**
- **어디에 등록?**
  ```js
  window.onload = function() { ... }
  // 또는
  window.addEventListener('load', function() { ... })
  ```

- **사용 예시**
  ```js
  window.onload = function() {
    alert('페이지 전체 로드 완료!');
    alert(`이미지 사이즈: ${img.offsetWidth}x${img.offsetHeight}`); // 이제는 OK!
  };
  ```

### 🛑 3️⃣ beforeunload

- **언제 발생?**
    - 사용자가 페이지 떠나기 직전 (새로고침, 다른 페이지 이동, 창 닫기 등)
- **사용 목적**
    - "저장 안 했는데 정말 떠날래?" 경고창 띄우기

- **예시 코드**
  ```js
  window.onbeforeunload = function() {
    return "저장 안 됐는데, 정말 떠날래?";
  };
  ```

- **주의할 점**
    - 반환 문자열은 사용자에게 직접 보이지 않을 수도 있음 (브라우저마다 다름)
    - **불필요하게 남용 금지** (사용자 불편)

### 🚀 4️⃣ unload

- **언제 발생?**
    - 페이지를 **진짜 떠날 때**

- **주 용도**
    - **빠른 처리**: 통계 데이터 전송 (`sendBeacon` 사용)
    - **느린 작업 X**: 네트워크 요청 외의 작업은 거의 불가능

- **예시 코드**
  ```js
  let analyticsData = { ... };

  window.addEventListener("unload", function() {
    navigator.sendBeacon("/analytics", JSON.stringify(analyticsData));
  });
  ```

### 🔍 5️⃣ document.readyState

| 값          | 의미                                         |
| --------- | ------------------------------------------ |
| loading    | 문서 로드 중                                  |
| interactive | DOM 파싱 완료 (DOMContentLoaded 직전)           |
| complete   | DOM + 리소스 모두 로드 완료 (window.onload 직전) |

- **사용 예시**
  ```js
  function init() { ... }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init(); // 이미 DOM 완성된 상태면 바로 실행
  }
  ```

---

## 🧭 로드 이벤트 순서 정리

```
초기 readyState: loading
→ readyState: interactive (DOM 완성됨)
→ DOMContentLoaded
→ iframe onload
→ img onload
→ readyState: complete (모든 리소스 로드 완료)
→ window.onload
```

---

## 🧩 초보 개발자 주의사항

✅ **DOMContentLoaded**와 **load**의 차이를 꼭 구분하자  
✅ DOMContentLoaded에서는 **이미지/스타일** 준비 안 된 상태임 주의  
✅ **beforeunload**는 사용자에게 **경고창 띄우는 용도**로만 쓰고, **남용 금지**  
✅ **unload**는 거의 안 쓰지만, **sendBeacon**은 필요할 땐 유용함  
✅ **readyState**는 간단한 DOM 상태 체크용으로 OK, 하지만 요즘은 `DOMContentLoaded`가 더 많이 쓰임
