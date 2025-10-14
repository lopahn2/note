
# 📚 Fetch API 정리

## 1️⃣ Fetch API란?

- **서버에 네트워크 요청을 보내고, 응답을 받아오는 기능**을 제공해줌.
- 주로 사용되는 경우:
    - 주문 전송
    - 사용자 정보 읽기
    - 서버에서 최신 변경 사항 가져오기
- 페이지 새로고침 없이 데이터를 가져올 수 있음. (이게 바로 AJAX의 핵심 개념!)

---

## 2️⃣ fetch()의 기본 사용법

### 기본 문법

```javascript
let promise = fetch(url, [options]);
```

- `url`: 요청을 보낼 대상 주소
- `options`(선택): 요청 방식(method), 헤더(headers) 등을 설정

### GET 요청 기본 형태

```javascript
let response = await fetch(url);
```

- `options`를 생략하면 기본적으로 **GET 요청**을 보냄.
- 결과는 `Response` 객체 형태의 **Promise**로 반환됨.

---

## 3️⃣ 응답 처리하기

### 단계 1️⃣ - 응답 헤더 확인

```javascript
if (response.ok) { // HTTP 상태 코드가 200~299면 성공
  let json = await response.json();
} else {
  alert("HTTP-Error: " + response.status);
}
```

- `response.status`: HTTP 상태 코드 (예: 200)
- `response.ok`: 상태 코드가 200~299면 `true`

### 단계 2️⃣ - 응답 본문(body) 읽기

**본문 읽기 메서드 (딱 하나만 사용할 수 있음!)**

| 메서드 | 설명 |
|-------|------|
| `response.text()` | 텍스트 형태로 읽기 |
| `response.json()` | JSON으로 파싱 |
| `response.formData()` | FormData 객체로 읽기 |
| `response.blob()` | Blob(Binary Large Object)으로 읽기 |
| `response.arrayBuffer()` | ArrayBuffer(저수준 바이너리 데이터)로 읽기 |

🚨 **주의:**  
본문 읽기는 **한 번만 가능**!  
한 번 `response.text()`나 `response.json()`을 호출하면, 이후에는 본문을 다시 읽을 수 없어!

---

## 4️⃣ 예제: GitHub에서 데이터 가져오기

```javascript
let url = 'https://api.github.com/repos/javascript-tutorial/ko.javascript.info/commits';
let response = await fetch(url);
let commits = await response.json();

alert(commits[0].author.login);
```

---

## 5️⃣ POST 요청 보내기

### JSON 데이터 전송 예시

```javascript
let user = {
  name: 'John',
  surname: 'Smith'
};

let response = await fetch('/article/fetch/post/user', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  },
  body: JSON.stringify(user)
});

let result = await response.json();
alert(result.message);
```

🚨 **주의:**  
`Content-Type` 헤더를 `application/json`으로 명시해야 함!  
안 그러면 기본값인 `text/plain`으로 처리돼서 문제가 생길 수 있어.

---

## 6️⃣ 이미지 (바이너리 데이터) 전송

### Blob 데이터 전송 예시

```javascript
let blob = await new Promise(resolve => canvasElem.toBlob(resolve, 'image/png'));

let response = await fetch('/article/fetch/post/image', {
  method: 'POST',
  body: blob
});
```

**주의:**  
Blob은 타입 정보를 포함하고 있어서 `Content-Type`을 따로 지정하지 않아도 됨!

---

## 7️⃣ 응답 헤더 다루기

```javascript
alert(response.headers.get('Content-Type'));

for (let [key, value] of response.headers) {
  alert(`${key} = ${value}`);
}
```

---

## 8️⃣ fetch 과제: GitHub 사용자 정보 가져오기

### 요구사항

- 배열에 있는 사용자 이름으로 각각 `fetch` 요청을 보냄
- 응답을 JSON으로 파싱 (상태 코드 200인 경우만)
- 실패하면 `null` 반환
- 요청은 **동시에** 진행 (각 요청이 다른 요청을 기다리지 않음)

### 정답 코드

```javascript
async function getUsers(names) {
  let jobs = [];

  for(let name of names) {
    let job = fetch(`https://api.github.com/users/${name}`).then(
      successResponse => {
        if (successResponse.status != 200) {
          return null;
        } else {
          return successResponse.json();
        }
      },
      failResponse => {
        return null;
      }
    );
    jobs.push(job);
  }

  let results = await Promise.all(jobs);
  return results;
}
```

---

## 9️⃣ 초보자가 주의해야 할 실수 포인트!

✅ **본문 읽기 메서드는 한 번만 호출 가능!**  
예: `response.text()`와 `response.json()`을 동시에 호출하면 에러

✅ **Content-Type 헤더 주의!**  
POST 요청 시 JSON 데이터면 꼭 `'Content-Type': 'application/json'` 설정

✅ **fetch의 에러 처리**
- 네트워크 에러는 `.catch()`로 잡거나 `.then()`의 두 번째 인자로 처리
- HTTP 404 같은 서버 응답은 에러가 아님 (status 확인 필요)

✅ **CORS (Cross-Origin Resource Sharing) 문제**  
다른 도메인에 요청할 때는 CORS 정책 주의! 브라우저가 막을 수도 있음
