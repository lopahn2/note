
# 📦 자바스크립트 모듈 - 내보내기(export)와 가져오기(import)

---

## 🔸 기본 구조

```js
// say.js
export function sayHi(user) {
  alert(`Hello, ${user}!`);
}

// main.js
import { sayHi } from './say.js';
sayHi('John');
```

---

## 🔸 다양한 export 방식

| 방식 | 예시 |
|------|------|
| 선언 앞 export | `export function sayHi() {}` |
| 나중에 export | `export { sayHi, sayBye }` |
| export as | `export { sayHi as hi }` |
| default export | `export default class User {}` |

---

## 🔸 다양한 import 방식

| 방식 | 예시 |
|------|------|
| 개별 import | `import { sayHi } from './say.js'` |
| 전체 import | `import * as say from './say.js'` |
| 이름 바꾸기 | `import { sayHi as hi } from './say.js'` |
| default import | `import User from './user.js'` |

---

## 🔸 default vs named export

| export 방식 | import 방식 |
|-------------|--------------|
| `export default class` | `import AnyName from './file.js'` |
| `export function x()` | `import { x } from './file.js'` |

- default는 이름 없이도 export 가능
- named는 반드시 이름 필요

---

## 🔸 다시 내보내기 (re-export)

```js
export { login, logout } from './helpers.js';
export { default as User } from './user.js';
```

- default는 별도로 지정 필요: `export { default as X }`
- `export * from`은 default를 포함하지 않음

---

## 🔸 주의할 점

- import/export는 항상 **최상위 레벨**에서만 사용 가능
- `if`, `function`, `block` 내부에선 문법 에러 발생

---

## ✅ 요약

### Export 방식
```js
export function f() {}
export default function() {}
export { x, y as z }
```

### Import 방식
```js
import { x } from 'mod'
import * as obj from 'mod'
import x from 'mod'
import { default as x } from 'mod'
import 'mod' // 코드 실행만
```

---

다음은 **동적으로 모듈을 불러오는 방법**에 대해 알아볼 차례예요!
