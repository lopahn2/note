
# 📦 자바스크립트 모듈 소개

애플리케이션이 커질수록 파일을 여러 개로 나누는 게 필요해져요. 그때 사용하는 것이 **모듈**입니다. 이 챕터에서는 **모듈이란 무엇인지**, **어떻게 사용되는지**, 그리고 **브라우저에서 어떤 특징을 가지는지** 알아봐요.

---

## 1. 모듈이란?

- 자바스크립트에서 모듈은 **파일 하나**를 의미합니다.
- 모듈은 `export`로 기능을 내보내고 `import`로 다른 모듈에서 가져올 수 있어요.

```js
// sayHi.js
export function sayHi(user) {
  alert(`Hello, ${user}!`);
}

// main.js
import { sayHi } from './sayHi.js';
sayHi('John');
```

---

## 2. \<script type="module">

- 브라우저에선 `<script type="module">`을 사용해야 모듈을 인식해요.
- 이 스크립트는 **지연 실행**되며, HTML 문서가 준비된 후 실행돼요.

```html
<script type="module">
  import { sayHi } from './say.js';
  sayHi('John');
</script>
```

---

## 3. 모듈의 특징

| 특징 | 설명 |
|------|------|
| 엄격 모드 | 항상 `use strict`가 자동 적용 |
| 모듈 스코프 | 모듈 안 변수는 외부에서 접근 불가 |
| 단 한 번 실행 | 여러 번 import 되어도 한 번만 평가됨 |
| 전역 this | `undefined` (일반 스크립트는 `window`) |

---

## 4. import.meta

```js
console.log(import.meta.url); // 현재 모듈의 URL
```

---

## 5. 외부 모듈

- `file://` 환경에서는 동작하지 않음 → 반드시 **웹서버 필요**
- **상대 또는 절대 경로만 사용 가능** (`'./module.js'` 등)
- **CORS** 허용된 서버에서만 외부 모듈 import 가능

---

## 6. nomodule 사용

```html
<script type="module">alert("모듈!");</script>
<script nomodule>alert("구식 브라우저!");</script>
```

---

## 7. 번들러 도구

- 실제 서비스에서는 **Webpack**, **Vite**, **Parcel** 같은 번들러를 사용
- 모듈을 하나의 파일로 번들링
- import/export는 번들러 전용 함수로 대체됨

---

## ✅ 요약

| 개념 | 설명 |
|------|------|
| 모듈 | 파일 단위로 코드 분리 가능 |
| export/import | 다른 파일 간 기능 공유 |
| script type="module" | 브라우저에 모듈임을 알림 |
| import.meta.url | 현재 모듈의 경로 확인 가능 |
| 번들링 | 실제 서비스에선 번들러로 모듈을 하나로 묶음 |

다음 챕터에서는 다양한 모듈 가져오기 및 내보내기 방법을 배워볼 거예요!
