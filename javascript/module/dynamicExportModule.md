
# 📦 동적으로 모듈 가져오기 - `import()`

---

## ⚡ 기존 import 문 한계

1. 문자열 외 동적 경로 불가
```js
import './say.js'; // ✅
import(getModuleName()); // ❌
```

2. 조건문 내부 사용 불가
```js
if (condition) {
  import './module.js'; // ❌
}
```

---

## 🔸 동적 import 사용법

### 기본 사용법

```js
let modulePath = './say.js';

import(modulePath)
  .then(module => {
    module.hi();
    module.bye();
  })
  .catch(err => alert("모듈 로드 실패"));
```

### async/await 사용

```js
let { hi, bye } = await import('./say.js');
hi(); bye();
```

---

## 🔹 default export 사용시

```js
// say.js
export default function() {
  alert("기본 내보내기!");
}

// 사용
let { default: say } = await import('./say.js');
say();
```

---

## 🔸 예제: 버튼 클릭 시 모듈 로드

```html
<script>
  async function load() {
    let say = await import('./say.js');
    say.hi();
    say.bye();
    say.default();
  }
</script>
<button onclick="load()">모듈 로드</button>
```

---

## ⚠ 주의사항

- `import()`는 일반 스크립트에서도 사용 가능 (`type="module"` 필요 없음)
- `import()`는 **함수처럼 생겼지만 함수 아님**
    - call/apply 불가
    - 변수에 대입 불가

---

## ✅ 요약

| 항목 | 설명 |
|------|------|
| 목적 | 런타임에 모듈을 조건부로 불러오기 |
| 반환값 | Promise (모듈 객체 반환) |
| 사용처 | 이벤트 핸들러, 조건문 등 |
| 타입 | 특별한 문법 (`super()`처럼 작동) |

동적 import는 사용자 액션에 따라 모듈을 불러올 수 있도록 도와주는 유용한 기능입니다!
