
# 자바스크립트 이벤트 루프, 매크로태스크, 마이크로태스크 쉽게 이해하기

## 🎡 이벤트 루프란?
이벤트 루프(event loop)는 자바스크립트 엔진이 어떻게 태스크(작업)를 처리하는지 보여주는 기본 구조입니다.

- "태스크 큐"에 있는 작업을 하나씩 꺼내서 처리하고
- 새로운 태스크가 없으면 잠시 멈췄다가
- 새 태스크가 생기면 다시 처리하는 반복 작업이에요.

**태스크의 예:**
- 외부 스크립트(`<script src="...">`) 로드
- 마우스 클릭 같은 이벤트
- `setTimeout` 콜백 실행

## 📦 매크로태스크와 마이크로태스크

### 💡 매크로태스크(macrotask)
- `setTimeout`, `setInterval` 등 (큰 작업 덩어리)

### 💡 마이크로태스크(microtask)
- `Promise.then`, `catch`, `finally` 핸들러
- `queueMicrotask()` 사용
- 매크로태스크보다 먼저 처리됨

## 🎬 처리 순서

1. 매크로태스크 하나 처리
2. **마이크로태스크 전부 처리**
3. 렌더링
4. 다시 1번으로

**예제:**
```javascript
setTimeout(() => alert("timeout"));
Promise.resolve().then(() => alert("promise"));
alert("code");
```
실행 순서:
1. code
2. promise
3. timeout

## 💻 실무에서의 활용

### 1️⃣ 무거운 작업은 쪼개서 처리
```javascript
let i = 0;

let start = Date.now();

function count() {

    // 스케줄링 코드를 함수 앞부분으로 옮김
    if (i < 1e9 - 1e6) {
        setTimeout(count); // 새로운 호출을 스케줄링함
    }

    do {
        i++;
    } while (i % 1e6 != 0);

    if (i == 1e9) {
        alert("처리에 걸린 시간: " + (Date.now() - start) + "ms");
    }

}

count();
```

스케쥴링 (appending task into the macro task queue) 을 연산 과정 앞에 넣어두면, 연속적인 스케쥴링 사이 생기는 >4ms 의 지연시간을 연산에 사용할 수 있어서 속도가 빨라집니다.  

### 2️⃣ 프로그레스 바 구현
```html
<div id="progress"></div>
<script>
  let i = 0;
  function count() {
    do {
      i++;
      progress.innerHTML = i;
    } while (i % 1e3 != 0);

    if (i < 1e7) {
      setTimeout(count);
    }
  }
  count();
</script>
```

### 3️⃣ 이벤트 핸들러 후 처리
```javascript
menu.onclick = function() {
  let customEvent = new CustomEvent("menu-open", { bubbles: true });
  setTimeout(() => menu.dispatchEvent(customEvent));
};
```

### 4️⃣ queueMicrotask 사용
```html
<div id="progress"></div>
<script>
  let i = 0;
  function count() {
    do {
      i++;
      progress.innerHTML = i;
    } while (i % 1e3 != 0);

    if (i < 1e6) {
      queueMicrotask(count);
    }
  }
  count();
</script>
```

## 🧠 헷갈리기 쉬운 포인트
- `setTimeout(0)`은 최소 4ms 대기
- 마이크로태스크는 매크로태스크 사이 처리
- 무거운 작업은 반드시 나누어 처리
- `queueMicrotask`: 동기처럼 보이지만 비동기 처리

## 🚀 웹 워커(Web Worker)
- 매우 무거운 연산은 웹 워커로 처리 (메인 스레드 막지 않음)
- DOM 접근 불가, 메시지로 데이터 교환
- 멀티코어 CPU 활용

## 🏁 이벤트 루프 요약

1. 매크로태스크 하나 실행
2. 마이크로태스크 전부 처리
3. 렌더링
4. 새 매크로태스크 대기
5. 새 매크로태스크 오면 반복
