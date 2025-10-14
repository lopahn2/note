
# 자바스크립트 Promise 체이닝

## 🔗 프라미스 체이닝이란?

비동기 작업을 순차적으로 처리하고 싶을 때 `.then()`을 연속적으로 사용하는 기법입니다.

```javascript
new Promise(resolve => {
  setTimeout(() => resolve(1), 1000);
})
.then(result => {
  alert(result); // 1
  return result * 2;
})
.then(result => {
  alert(result); // 2
  return result * 2;
})
.then(result => {
  alert(result); // 4
});
```

---

## ❗ 주의: 체이닝이 아닌 예

```javascript
let promise = new Promise(resolve => {
  setTimeout(() => resolve(1), 1000);
});

promise.then(result => alert(result)); // 1
promise.then(result => alert(result)); // 1
promise.then(result => alert(result)); // 1
```

동일 프라미스에 여러 핸들러를 붙이면 **독립적으로** 실행됩니다. 체이닝 아님!

---

## 🔄 프라미스를 반환하면?

```javascript
new Promise(resolve => setTimeout(() => resolve(1), 1000))
.then(result => {
  return new Promise(resolve => {
    setTimeout(() => resolve(result * 2), 1000);
  });
})
.then(result => alert(result)); // 2
```

`.then()` 안에서 새로운 프라미스를 반환하면 **다음 then이 그 결과를 기다림**.

---

## 📦 loadScript 개선: 순차적 로딩

```javascript
loadScript("/one.js")
  .then(() => loadScript("/two.js"))
  .then(() => loadScript("/three.js"))
  .then(() => {
    one();
    two();
    three();
  });
```

깔끔한 체이닝으로 순차 처리!

---

## 🌀 중첩 방식 (지양)

```javascript
loadScript("/one.js").then(() => {
  loadScript("/two.js").then(() => {
    loadScript("/three.js").then(() => {
      one(); two(); three();
    });
  });
});
```

이렇게 하면 **콜백 지옥처럼 중첩됨**.

---

## 👀 thenable 객체

```javascript
class Thenable {
  constructor(num) { this.num = num; }
  then(resolve) {
    setTimeout(() => resolve(this.num * 2), 1000);
  }
}

new Promise(resolve => resolve(1))
  .then(result => new Thenable(result))
  .then(alert); // 2
```

`then()` 메서드가 있다면 프라미스처럼 동작.

---

## 🌐 fetch 체이닝 예시

```javascript
fetch('/user.json')
  .then(response => response.json())
  .then(user => fetch(`https://api.github.com/users/${user.name}`))
  .then(response => response.json())
  .then(githubUser => {
    let img = document.createElement('img');
    img.src = githubUser.avatar_url;
    document.body.append(img);
    setTimeout(() => img.remove(), 3000);
  });
```

비동기 호출과 DOM 조작을 체이닝으로 깔끔하게 연결.

---

## ✅ 체인 확장 가능한 showAvatar

```javascript
function showAvatar(githubUser) {
  return new Promise(resolve => {
    let img = document.createElement('img');
    img.src = githubUser.avatar_url;
    document.body.append(img);
    setTimeout(() => {
      img.remove();
      resolve(githubUser);
    }, 3000);
  });
}
```

---

## 🔄 함수 분리로 재사용성 UP

```javascript
function loadJson(url) {
  return fetch(url).then(response => response.json());
}

function loadGithubUser(name) {
  return fetch(`https://api.github.com/users/${name}`).then(r => r.json());
}

loadJson('/user.json')
  .then(user => loadGithubUser(user.name))
  .then(showAvatar)
  .then(githubUser => alert(`Finished showing ${githubUser.name}`));
```

---

## 📝 요약

- `.then()`은 프라미스를 반환하고, 체인을 구성할 수 있음
- 체인 안에서 새로운 프라미스를 반환하면 다음 체인이 기다림
- 중첩보다 **평평한 체이닝**을 선호하자!
