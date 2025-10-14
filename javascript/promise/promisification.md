
# 🔄 자바스크립트의 프라미스화(Promisification)

프라미스화는 기존 **콜백 기반의 함수를 프라미스를 반환하는 형태로 바꾸는 것**을 말해요. 요즘은 async/await와 같은 문법 덕분에 프라미스가 더 사용하기 편해서 많이 쓰여요.

---

## 1. 왜 프라미스화가 필요할까요?

콜백은 비동기 처리에서 많이 쓰이지만, 코드가 길어지고 복잡해지기 쉬워요.
프라미스로 바꾸면 `.then()`, `.catch()`로 가독성도 좋고 예외 처리도 명확하게 할 수 있어요.

---

## 2. 예시: loadScript 함수

콜백 기반 함수:
```js
function loadScript(src, callback) {
  let script = document.createElement('script');
  script.src = src;

  script.onload = () => callback(null, script);
  script.onerror = () => callback(new Error(`${src}를 불러오는 도중에 에러가 발생함`));

  document.head.append(script);
}
```

프라미스화한 버전:
```js
let loadScriptPromise = function(src) {
  return new Promise((resolve, reject) => {
    loadScript(src, (err, script) => {
      if (err) reject(err);
      else resolve(script);
    });
  });
}
```

---

## 3. 헬퍼 함수 만들기: `promisify`

```js
function promisify(f) {
  return function (...args) {
    return new Promise((resolve, reject) => {
      function callback(err, result) {
        if (err) reject(err);
        else resolve(result);
      }
      args.push(callback);
      f.call(this, ...args);
    });
  };
}
```

사용 예시:
```js
let loadScriptPromise = promisify(loadScript);
loadScriptPromise('script.js').then(...);
```

---

## 4. 여러 결과를 반환하는 콜백 지원: `promisify(f, true)`

```js
function promisify(f, manyArgs = false) {
  return function (...args) {
    return new Promise((resolve, reject) => {
      function callback(err, ...results) {
        if (err) reject(err);
        else resolve(manyArgs ? results : results[0]);
      }
      args.push(callback);
      f.call(this, ...args);
    });
  };
}
```

사용 예시:
```js
f = promisify(f, true);
f(...).then(results => console.log(results));
```

---

## ❗ 주의할 점

- 프라미스는 **한 번만 결과를 반환**할 수 있어요. 콜백처럼 여러 번 호출되는 함수에는 적합하지 않아요.
- `callback(result)` 처럼 에러가 없는 단일 인자 콜백은 직접 프라미스화 해주세요.
- Node.js에서는 `util.promisify`를 사용하면 간편하게 프라미스화를 할 수 있어요.
- 외부 모듈로는 `es6-promisify`도 많이 사용돼요.

---

## ✅ 요약

| 개념 | 설명 |
|------|------|
| 프라미스화 | 콜백 함수 → 프라미스 반환 함수로 변환 |
| 사용 이유 | 코드 가독성 증가, async/await 사용 가능 |
| 주의 사항 | 콜백이 한 번만 호출되는 함수에만 사용 |

이제 콜백 기반 함수도 async/await 문법과 잘 어울리는 형태로 쓸 수 있어요!
