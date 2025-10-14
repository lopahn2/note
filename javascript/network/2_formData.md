
# 📦 FormData 객체 완벽 가이드

## 1️⃣ FormData란?

- **HTML 폼 데이터를 쉽게 다루기 위한 객체**야.
- `fetch()`나 다른 네트워크 요청 메서드에서 요청 본문으로 넣을 수 있어.
- 폼 데이터를 전송하면 브라우저가 자동으로 아래처럼 처리해줘:
    - `Content-Type`: `multipart/form-data`로 자동 설정
    - 데이터 인코딩까지 자동 처리

✅ 서버 입장에서는 FormData로 전송된 데이터나 일반 `<form>` 전송된 데이터나 똑같이 처리해.

---

## 2️⃣ FormData 생성

```javascript
let formData = new FormData([form]);
```

- `form` 파라미터에 HTML `<form>` 요소를 넣으면, 해당 폼의 필드를 자동으로 포함해줘.
- form 없이 비어있는 FormData를 만들 수도 있음.

✅ 초보자 주의!
- FormData에 담긴 데이터는 **multipart/form-data 형식**으로 전송되기 때문에, JSON이나 `application/x-www-form-urlencoded`처럼 동작하길 기대하면 안 돼!

---

## 3️⃣ FormData로 간단한 폼 전송

### HTML 코드

```html
<form id="formElem">
  <input type="text" name="name" value="Bora">
  <input type="text" name="surname" value="Lee">
  <input type="submit">
</form>
```

### JavaScript 코드

```javascript
formElem.onsubmit = async (e) => {
  e.preventDefault();

  let response = await fetch('/article/formdata/post/user', {
    method: 'POST',
    body: new FormData(formElem)
  });

  let result = await response.json();
  alert(result.message);
};
```

✅ **주의:**  
`new FormData(form)`을 `fetch()`의 `body`에 넣으면 자동으로 폼 데이터 전송 완료!  
`Content-Type`을 직접 지정하면 오히려 문제가 될 수 있어.

---

## 4️⃣ FormData 메서드

| 메서드 | 설명 |
|--------|--------|
| `append(name, value)` | 필드 추가 (중복 이름 허용) |
| `append(name, blob, fileName)` | 파일 필드 추가 (파일명은 3번째 인자로) |
| `set(name, value)` | 동일 이름 필드 모두 삭제 후 추가 |
| `set(name, blob, fileName)` | 파일 필드 추가 (이름 중복 제거) |
| `delete(name)` | 해당 이름의 필드 삭제 |
| `get(name)` | 첫 번째 필드 값 가져오기 |
| `has(name)` | 해당 이름의 필드 존재 여부 확인 |

### for...of 반복문으로 데이터 조회

```javascript
let formData = new FormData();
formData.append('key1', 'value1');
formData.append('key2', 'value2');

for (let [name, value] of formData) {
  alert(`${name} = ${value}`);
}
```

---

## 5️⃣ 파일 전송

### 파일이 있는 폼 전송

```html
<form id="formElem">
  <input type="text" name="firstName" value="Bora">
  Picture: <input type="file" name="picture" accept="image/*">
  <input type="submit">
</form>
```

```javascript
formElem.onsubmit = async (e) => {
  e.preventDefault();

  let response = await fetch('/article/formdata/post/user-avatar', {
    method: 'POST',
    body: new FormData(formElem)
  });

  let result = await response.json();
  alert(result.message);
};
```

✅ 파일이 포함된 경우도 `multipart/form-data` 형식으로 자동 처리됨!

---

## 6️⃣ Blob과 FormData 함께 사용하기

### 예시: Canvas에서 이미지 생성 후 전송

```html
<body style="margin:0">
  <canvas id="canvasElem" width="100" height="80" style="border:1px solid"></canvas>

  <input type="button" value="이미지 전송" onclick="submit()">

  <script>
    canvasElem.onmousemove = function(e) {
      let ctx = canvasElem.getContext('2d');
      ctx.lineTo(e.clientX, e.clientY);
      ctx.stroke();
    };

    async function submit() {
      let imageBlob = await new Promise(resolve => canvasElem.toBlob(resolve, 'image/png'));

      let formData = new FormData();
      formData.append("firstName", "Bora");
      formData.append("image", imageBlob, "image.png");

      let response = await fetch('/article/formdata/post/image-form', {
        method: 'POST',
        body: formData
      });
      let result = await response.json();
      alert(result.message);
    }
  </script>
</body>
```

✅ `formData.append("image", imageBlob, "image.png")`  
→ 파일 이름까지 지정해주는 게 중요!

---

## 7️⃣ 초보자가 헷갈리기 쉬운 포인트

✅ `set()` vs `append()`
- `append()`는 이름이 같은 필드를 계속 추가
- `set()`은 기존 필드 삭제 후 추가

✅ FormData는 `application/json` 아님
- `fetch()`로 FormData를 보내면 `Content-Type`은 `multipart/form-data`
- JSON으로 보내려면 `JSON.stringify()`와 `Content-Type: application/json` 필요

✅ `toBlob()`은 비동기!
- Canvas에서 이미지 추출할 때는 항상 `await` 필요

✅ FormData로 파일 전송할 때는 파일 이름 주의
- `formData.append(name, blob, fileName)`에서 **fileName** 지정 필수!
