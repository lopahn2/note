# 🗓️ JavaScript Date 객체 완벽 정리

## 📌 생성 방법

```js
new Date();                     // 현재 시각
new Date(milliseconds);        // 1970-01-01 기준 밀리초
new Date("YYYY-MM-DD");        // 문자열 기반 생성
new Date(year, month, date, hours, minutes, seconds, ms);
```

- `month`: 0 ~ 11
- 최소 정밀도: 1밀리초

---

## 📅 날짜 구성요소 가져오기

| 메서드 | 설명 |
|--------|------|
| `getFullYear()` | 연도 (YYYY) |
| `getMonth()` | 월 (0 ~ 11) |
| `getDate()` | 일 (1 ~ 31) |
| `getDay()` | 요일 (0=일 ~ 6=토) |
| `getHours()` / `getMinutes()` / `getSeconds()` / `getMilliseconds()` | 시분초밀리초 |
| `getUTC...()` | UTC 기준 구성요소 |
| `getTime()` | 타임스탬프 반환 |
| `getTimezoneOffset()` | 현지-UTC 시간차 (분) |

---

## 🛠️ 날짜 구성요소 설정

- `setFullYear()`, `setMonth()`, `setDate()` 등
- 자동 보정 지원 (예: 32일 → 다음 달)

```js
let d = new Date(2016, 1, 28);
d.setDate(d.getDate() + 2); // 3월 1일
```

---

## ⏱️ 시간 측정 & 변환

```js
+new Date()            // 타임스탬프 반환
Date.now()             // 현재 타임스탬프 (빠름)
```

### ⏱️ 예: 실행 시간 측정

```js
let start = Date.now();
// ... 작업 ...
let end = Date.now();
alert(end - start + "ms");
```

---

## 📊 벤치마크

```js
function bench(fn) {
  let date1 = new Date(0), date2 = new Date();
  let start = Date.now();
  for (let i = 0; i < 100000; i++) fn(date1, date2);
  return Date.now() - start;
}
```

- `date2 - date1` vs `date2.getTime() - date1.getTime()`
  - getTime이 형변환을 먼저해서 빠르다
- 여러 번 번갈아 실행하여 정확도 ↑
- 예열(heat-up) 추천

---

## 🧪 Date.parse

```js
Date.parse("2012-01-26T13:51:50.417-07:00"); // 타임스탬프 반환
new Date(Date.parse(...)); // Date 객체로 변환
```

**지원 포맷**: `YYYY-MM-DDTHH:mm:ss.sssZ`

---

## ✅ 요약

- 날짜 조작은 `Date` 객체 메서드 활용
- 자동 보정 기능 강력함
- `Date.now()`는 고성능 타이밍 측정에 유용
- 벤치마크는 반드시 예열 포함 + 반복 측정할 것
