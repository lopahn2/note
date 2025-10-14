
# defer, async 스크립트 완전 정리

## 🎓 스크립트 로딩 기본 동작

- HTML 파싱 중 `<script>`를 만나면?  
  ➔ 브라우저는 **DOM 생성 중단** → **스크립트 다운로드 & 실행** → 다시 DOM 생성 재개

- 문제점
  1️⃣ 스크립트 아래 DOM 요소에 접근 불가 (DOM 생성 멈춰서)  
  2️⃣ 스크립트 다운로드가 느리면 페이지 전체 로딩이 느려짐

```html
<p>...스크립트 앞...</p>
<script src="script.js"></script>
<p>...스크립트 뒤...</p>
```

## ✅ 기본 해결법: 스크립트를 페이지 아래에 두기

```html
<body>
  ... 콘텐츠 ...
  <script src="script.js"></script>
</body>
```

하지만! HTML 전체가 커지면 **스크립트 다운로드 자체가 늦어짐**

---

## 🚀 해결책: `defer`와 `async`

### 1️⃣ `defer` (지연 실행)

| 특징                           | 설명                                    |
| ----------------------------- | --------------------------------------- |
| 다운로드                       | HTML 파싱과 **동시에 백그라운드 다운로드**  |
| 실행 타이밍                     | **HTML 파싱 완료 후**, DOMContentLoaded **이전** |
| 실행 순서                      | **문서에 작성된 순서대로** 실행         |
| 다른 스크립트와의 관계           | 서로 기다림 (작성 순서대로 실행)         |
| 적용 대상                       | **외부 스크립트**만 (`src` 필수)        |

```html
<script defer src="long.js"></script>
<script defer src="small.js"></script>
```

**주의**: defer 스크립트는 **DOM 완성 후 실행**되므로, 사용자 화면에선 스크립트 결과가 늦게 반영될 수 있음. **로딩 인디케이터**나 **버튼 비활성화 처리** 필요!

### 2️⃣ `async` (비동기 실행)

| 특징                           | 설명                                    |
| ----------------------------- | --------------------------------------- |
| 다운로드                       | HTML 파싱과 **동시에 백그라운드 다운로드**  |
| 실행 타이밍                     | 다운로드 완료 즉시 실행 (**파싱 중단함**)   |
| 실행 순서                      | **다운로드 완료된 순서대로** (예측 불가)   |
| 다른 스크립트와의 관계           | 서로 기다리지 않음                       |
| 적용 대상                       | **외부 스크립트**만 (`src` 필수)        |

```html
<script async src="long.js"></script>
<script async src="small.js"></script>
```

**실행 순서**는 다운로드 속도에 따라 달라짐 → 실행 순서 보장 필요 없는 독립 스크립트 (광고, 통계 등)에 적합

---

## 🌐 동적 스크립트

자바스크립트로 스크립트를 동적으로 추가하면?  
➔ 기본적으로 `async`처럼 동작 (다운로드 완료 순서대로 실행)

```js
let script = document.createElement('script');
script.src = 'script.js';
document.body.append(script);
```

**`async=false`** 를 설정하면 **문서에 추가된 순서대로** 실행 가능

```js
function loadScript(src) {
  let script = document.createElement('script');
  script.src = src;
  script.async = false;
  document.body.append(script);
}
loadScript("long.js");
loadScript("small.js");
```

---

## ⚡ `defer` vs `async` 비교

| 특징            | defer                        | async                       |
| --------------- | ---------------------------- | --------------------------- |
| 다운로드        | 백그라운드                    | 백그라운드                   |
| 실행 타이밍      | DOMContentLoaded **이전**      | 다운로드 완료 즉시 (순서 무관) |
| 실행 순서        | 작성 순서                     | 다운로드 완료 순 (예측 불가)   |
| DOMContentLoaded와 관계 | 실행 후 발생                  | 서로 기다리지 않음             |
| 용도            | DOM 전체 필요 + 순서 중요      | 광고, 통계 등 독립 스크립트     |

---

## 🧩 실무 팁

✅ **defer**:
- DOM 전체 필요, 순서 중요할 때
- 실행 전 페이지가 출력되므로 **로딩 UI** 표시 필요

✅ **async**:
- 광고, 방문자 수 카운터 등 독립적인 스크립트에 적합
- 실행 순서 신경 안 써도 될 때

✅ **동적 스크립트**:
- 기본은 async처럼, 순서 필요하면 `script.async = false`

---

## ✨ 정리

- `defer`와 `async`를 잘 활용하면 페이지 속도 & 사용자 경험 개선!
- 각각의 특성을 이해하고 상황에 맞게 선택하자 💪
