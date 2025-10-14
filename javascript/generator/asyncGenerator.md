
# 🔄 Async 이터레이터와 제너레이터 완전 정리

비동기 데이터를 다뤄야 할 때 `async 이터레이터`와 `async 제너레이터`는 매우 강력한 도구예요. 특히 **데이터가 순차적으로 들어오거나 네트워크 요청이 필요한 경우**에 적합합니다.

---

## 1. 기본 개념 비교

| 항목 | 일반 이터레이터 | 비동기 이터레이터 |
|------|----------------|------------------|
| 메서드 이름 | Symbol.iterator | Symbol.asyncIterator |
| next() 반환값 | {value, done} | Promise로 감싼 {value, done} |
| 반복문 | for...of | for await...of |

---

## 2. async 이터레이터 예시

```js
let range = {
  from: 1,
  to: 5,
  async *[Symbol.asyncIterator]() {
    for (let value = this.from; value <= this.to; value++) {
      await new Promise(resolve => setTimeout(resolve, 1000));
      yield value;
    }
  }
};

(async () => {
  for await (let value of range) {
    console.log(value); // 1~5, 1초 간격으로 출력
  }
})();
```

---

## 3. async 제너레이터 함수

```js
async function* generateSequence(start, end) {
  for (let i = start; i <= end; i++) {
    await new Promise(resolve => setTimeout(resolve, 1000));
    yield i;
  }
}
```

- `async function*`으로 선언
- `yield`로 값 생성
- 내부에서 `await` 가능

---

## 4. 실제 사례: GitHub 커밋 페이지네이션

```js
async function* fetchCommits(repo) {
  let url = `https://api.github.com/repos/${repo}/commits`;

  while (url) {
    const response = await fetch(url, {
      headers: {'User-Agent': 'Our script'}
    });
    const body = await response.json();

    let nextPage = response.headers.get('Link')?.match(/<(.*?)>; rel="next"/)?.[1];
    url = nextPage;

    for (let commit of body) {
      yield commit;
    }
  }
}

(async () => {
  let count = 0;
  for await (let commit of fetchCommits('javascript-tutorial/en.javascript.info')) {
    console.log(commit.author.login);
    if (++count === 100) break;
  }
})();
```

---

## 5. 요약

| 기능 | 일반 | 비동기 |
|------|------|--------|
| 이터레이터 | `Symbol.iterator` | `Symbol.asyncIterator` |
| 제너레이터 | `function*` | `async function*` |
| 반복문 | `for...of` | `for await...of` |
| next 반환값 | {value, done} | Promise({value, done}) |

---

## ✅ 언제 사용할까요?

- 비동기적으로 **데이터 스트림**이 들어올 때
- **네트워크 요청/딜레이**가 필요한 루프
- **페이지네이션 API**와 같이 반복적으로 요청이 필요한 경우

`async 제너레이터`를 사용하면 복잡한 비동기 흐름을 훨씬 더 직관적으로 작성할 수 있어요!
