
# 📦 Fetch: 다운로드 진행 상태 추적

## 1️⃣ Fetch에서 다운로드 진행 상태를 추적할 수 있다고?

- 맞아! `fetch()`를 사용할 때 **응답(response)** 의 `body`를 읽으면, 다운로드가 진행되는 **실시간 상태**를 추적할 수 있어.
- 하지만! 🚫
    - **업로드(progress)** 는 `fetch()`로는 추적 못해. (업로드 진행 상태는 나중에 `XMLHttpRequest`에서 배우자!)
    - `fetch()`는 **다운로드 진행 상태만 추적 가능**해!

---

## 2️⃣ 핵심: `response.body.getReader()` 사용

```javascript
const reader = response.body.getReader();
```

- `getReader()`는 응답 본문을 한 조각씩 읽을 수 있는 **스트림 리더**를 줘.
- `response.json()`이나 `response.text()`처럼 한 번에 읽지 않고, **조각(chunk)** 단위로 읽는 거야.

---

## 3️⃣ 기본 구조

```javascript
while(true) {
  const {done, value} = await reader.read();
  if (done) break;

  console.log(`Received ${value.length} bytes`);
}
```

- `reader.read()`는 `{done, value}` 객체를 반환해.
    - `done`: 다운로드 끝났으면 `true`
    - `value`: 받은 데이터(바이트 배열, `Uint8Array` 형태)

---

## 4️⃣ 다운로드 진행률 표시 예제 (전체 코드)

```javascript
let response = await fetch('https://api.github.com/repos/javascript-tutorial/en.javascript.info/commits?per_page=100');

const reader = response.body.getReader();
const contentLength = +response.headers.get('Content-Length'); // 전체 크기

let receivedLength = 0;
let chunks = [];

while(true) {
  const {done, value} = await reader.read();

  if (done) break;

  chunks.push(value);
  receivedLength += value.length;

  console.log(`Received ${receivedLength} of ${contentLength}`);
}

// 데이터 합치기
let chunksAll = new Uint8Array(receivedLength);
let position = 0;
for(let chunk of chunks) {
  chunksAll.set(chunk, position);
  position += chunk.length;
}

// 텍스트로 변환
let result = new TextDecoder("utf-8").decode(chunksAll);
let commits = JSON.parse(result);
alert(commits[0].author.login);
```

---

## 5️⃣ 단계별 설명

| 단계 | 설명 |
|----|----|
| 1️⃣ | `fetch()`로 응답 받아옴 |
| 2️⃣ | `response.body.getReader()`로 리더 얻기 |
| 3️⃣ | `Content-Length`로 전체 크기 파악 (없을 수도 있음!) |
| 4️⃣ | `while` 루프로 데이터 읽어오기 |
| 5️⃣ | 각 조각(`chunk`)을 배열에 저장 후 합치기 |
| 6️⃣ | `Uint8Array`로 합치고 `TextDecoder`로 텍스트로 변환 |
| 7️⃣ | 필요한 경우 `JSON.parse()`로 파싱 |

✅ **중요:**
- `response.body`로 읽기 시작하면 `response.json()` 같은 다른 메서드는 못 써! (이미 소비된 응답은 다시 못 읽어!)
- `Content-Length`는 항상 있는 게 아님! (CORS 때문에 없어질 수도 있어)

---

## 6️⃣ 만약 바이너리 데이터(예: 이미지)가 필요하면?

텍스트 변환 대신 간단히 이렇게 해:

```javascript
let blob = new Blob(chunks);
```

그럼 `blob`으로 바이너리 데이터를 처리할 수 있어!

---

## 7️⃣ 초보자가 헷갈리기 쉬운 포인트

✅ **upload progress는 fetch로 못함!**
- 이 방법은 **다운로드** 전용! (업로드는 `XMLHttpRequest` 써야 함)

✅ **response.body vs response.json()**
- 둘 중 하나만 선택해야 함. `body` 읽으면 다른 메서드 못 씀!

✅ **Content-Length 주의**
- 없을 수도 있음! (CORS, 서버 설정 등)

✅ **Uint8Array는 그냥 바이트 배열**
- 텍스트로 바꾸려면 `TextDecoder` 필요

✅ **progress 표시하려면 bytes 단위로 계산**
- `value.length`로 받은 바이트 수를 누적!
