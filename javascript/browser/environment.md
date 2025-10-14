
# 브라우저 환경과 다양한 명세서 요약

## 1️⃣ 자바스크립트와 **호스트 환경(Host Environment)**

- 자바스크립트는 원래 **웹 브라우저**에서 사용하려고 만든 언어였어요.
- 시간이 지나면서 **Node.js**, **서버**, 심지어 **커피 머신**(!) 같은 다양한 환경에서도 사용하게 되었죠.
- 자바스크립트가 실행되는 환경 = **호스트(Host)**
- 각 호스트는 자신만의 **기능과 객체**를 제공해요.  
  예를 들면:
    - 브라우저: DOM, alert, confirm 같은 기능
    - Node.js: 파일 시스템(fs), 네트워킹(net) 기능 등

✅ **호스트 환경**은 ECMAScript(자바스크립트 핵심 문법) 외의 기능을 포함하는 환경을 의미합니다.

---

## 2️⃣ **브라우저의 전역 객체 `window`**

- **`window` 객체**는 브라우저 환경에서 제일 중요한 객체 중 하나에요.
- 역할 2가지:
    1. 자바스크립트의 **전역 객체** 역할  
       👉 전역 함수/변수는 사실상 `window` 객체의 프로퍼티!
       ```javascript
       function sayHi() {
         alert("안녕하세요.");
       }
  
       window.sayHi(); // 이렇게도 호출 가능
       ```
    2. **브라우저 창** 자체를 대표하는 객체  
       👉 창의 크기, URL, 스크롤 등 제어 가능
       ```javascript
       alert(window.innerHeight); // 브라우저 창의 높이 출력
       ```

---

## 3️⃣ **DOM (Document Object Model)**

- 웹 페이지의 **모든 요소를 객체로 표현**한 구조
- 페이지를 **조작하고 싶을 때 필수적으로 사용하는 API**
- **`document` 객체**: DOM에 접근하는 기본 진입점!
  ```javascript
  document.body.style.background = "red"; // 배경색을 빨간색으로 변경
  setTimeout(() => document.body.style.background = "", 1000); // 1초 후 원상복귀
  ```
- **DOM 명세서**:  
  [https://dom.spec.whatwg.org](https://dom.spec.whatwg.org)

💡 **주의할 점**:
- DOM은 브라우저에서만 쓰이는 게 아님!
- 서버에서도 DOM 일부를 사용할 수 있음 (예: HTML 파싱 및 조작)

---

## 4️⃣ **CSSOM (CSS Object Model)**

- CSS 규칙과 스타일시트를 객체로 다루는 모델
- CSSOM은 **CSS 스타일**을 자바스크립트로 수정하거나 읽을 때 사용해요.
- 다만, CSS는 대부분 정적이기 때문에 실무에서 CSSOM을 자주 쓰진 않음.

✅ **CSSOM 명세서**:  
[https://www.w3.org/TR/cssom-1/](https://www.w3.org/TR/cssom-1/)

---

## 5️⃣ **BOM (Browser Object Model)**

- **브라우저의 부가 기능**을 다루는 객체 모음
- 대표적인 객체:
    - `navigator`: 브라우저와 OS 정보
    - `location`: 현재 URL, 페이지 이동 기능
    - `alert`, `confirm`, `prompt`: 사용자와 상호작용

  ```javascript
  alert(location.href); // 현재 URL 출력
  if (confirm("위키피디아 페이지로 가시겠습니까?")) {
    location.href = "https://wikipedia.org"; // 페이지 이동
  }
  ```

✅ **BOM 관련 명세**는 별도로 없고, **HTML 명세서**에서 함께 다룸:  
[https://html.spec.whatwg.org](https://html.spec.whatwg.org)

---

## 6️⃣ **명세서 정리**

| 명세서 | 설명 | 링크 |
|:---|:---|:---|
| DOM | 문서 구조, 조작, 이벤트 | [DOM 명세서](https://dom.spec.whatwg.org) |
| CSSOM | CSS 규칙과 스타일 객체 다루기 | [CSSOM 명세서](https://www.w3.org/TR/cssom-1/) |
| HTML | 태그, 브라우저 기능(BOM) 포함 | [HTML 명세서](https://html.spec.whatwg.org) |

---

## 7️⃣ **명세서 보는 습관 들이기**

- 실무에서는 **MDN** 문서를 많이 보지만, **명세서**를 직접 읽는 습관도 중요!
- 검색 팁:
  ```
  WHATWG [용어]
  MDN [용어]
  ```
  예:
    - [https://google.com?q=whatwg+localstorage](https://google.com?q=whatwg+localstorage)
    - [https://google.com?q=mdn+localstorage](https://google.com?q=mdn+localstorage)

---

## 8️⃣ **초보자가 실수하기 쉬운 포인트**

- **window 객체**는 모든 코드에서 자동으로 접근 가능하지만, 다른 호스트(Node.js 등)에서는 `window`가 없을 수 있으니 주의!
- **DOM과 CSSOM**은 **브라우저 환경 전용**이라는 걸 기억해두기.
- **명세서 읽기**는 어렵지만, 하나씩 찾아보면서 익숙해지는 게 중요!

---

이제 **DOM**에 대해 더 깊게 배워볼 준비가 되셨습니다! 🚀
