# 📦 배열 메서드 요약 정리

## ✅ 요소 추가 및 제거
- `push(...items)` – 배열 끝에 요소 추가
- `pop()` – 배열 끝 요소 제거
- `shift()` – 배열 앞 요소 제거
- `unshift(...items)` – 배열 앞에 요소 추가
- `splice(index, deleteCount, ...items)` – 요소 삭제 및 삽입
- `slice(start, end)` – 배열 일부 복사 (새 배열 반환)
- `concat(...items)` – 배열 합치기 (새 배열 반환)

## 🔍 요소 검색
- `indexOf`, `lastIndexOf` – 값의 인덱스 검색
- `includes` – 포함 여부 확인
- `find`, `findIndex` – 조건을 만족하는 첫 요소/인덱스 찾기
- `filter` – 조건을 만족하는 모든 요소를 새 배열로 반환

## 🔁 반복/변형
- `forEach` – 각 요소에 대해 함수 실행 (반환 없음)
- `map` – 변환된 값으로 새 배열 생성
- `reduce`, `reduceRight` – 누적값 계산 (왼→오/오→왼)

## 🔀 정렬 및 순서 변경
- `sort([fn])` – 정렬
- `reverse()` – 순서 반전
- `copySorted(arr)` – 원본을 유지한 채 정렬 복사

## 🧱 문자열과 배열 변환
- `split(delim)` – 문자열 → 배열
- `join(glue)` – 배열 → 문자열

## 🧪 기타 유틸리티
- `Array.isArray(value)` – 배열 여부 확인
- `some`, `every` – 조건을 만족하는 요소 일부/모두 여부
- `fill(value, start, end)` – 특정 값으로 배열 채우기
- `copyWithin(target, start, end)` – 일부 요소 복사/덮어쓰기

---

# 🧪 배열 메서드 실전 예제 코드

## ✅ 요소 추가/삭제
```js
let arr = [1, 2];
arr.push(3);           // [1, 2, 3]
arr.pop();             // [1, 2]
arr.shift();           // [2]
arr.unshift(0);        // [0, 2]
arr.splice(1, 1, 'a'); // [0, 'a']
arr.slice(0, 1);       // [0]
[1, 2].concat([3]);    // [1, 2, 3]
```

## 🔍 검색 & 탐색
```js
arr.indexOf(0);       // 1
arr.includes(false);  // true
users.find(u => u.id === 1);
users.filter(u => u.id < 2);
users.findIndex(u => u.name === "Pete");
```

## 🔁 순회 & 변형
```js
["a", "b"].forEach((v, i) => console.log(i, v));
["a", "ab"].map(s => s.length);          // [1, 2]
[1, 2].reduce((a, b) => a + b, 0);       // 3
["a", "b"].reduceRight((a, b) => a + b); // "ba"
```

## 🔀 정렬 & 순서
```js
[1, 15, 2].sort();                  // [1, 15, 2]
// 배열에서 정렬시, 문자열로 판단한다고 함.
[1, 15, 2].sort((a, b) => a - b);   // [1, 2, 15]
// 그래서 이런식으로 정렬 기준을 명확히 숫자형으로 만들어야 함
[1, 2, 3].reverse();                // [3, 2, 1]
[..."cba"].sort();                 // ['a','b','c']
```

## 🧱 문자열 변환
```js
"John, Pete".split(", ");         // ["John", "Pete"]
["a", "b"].join("-");             // "a-b"
```

## 🧪 기타
```js
Array.isArray([]);                // true
[1, 2].some(x => x > 1);          // true
[1, 2].every(x => x > 0);         // true
[1, 2, 3].fill(0, 1, 3);          // [1, 0, 0]
[1, 2, 3, 4].copyWithin(1, 2, 4); // [1, 3, 4, 4]
```
