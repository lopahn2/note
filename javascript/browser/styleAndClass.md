
# 자바스크립트로 스타일과 클래스 다루기 - 공식 문서 정리

## 1️⃣ 스타일과 클래스 적용 방법

👉 **요소에 스타일을 적용하는 2가지 방법**
- CSS 클래스 정의 후 `<div class="...">` 처럼 적용
- `style` 속성에 직접 작성 (`<div style="...">`)

💡 **중요한 원칙**:
- 스타일 적용은 **가능하면 CSS 클래스**로!
- `style`은 **계산된 값(좌표)** 등을 동적으로 설정할 때만 사용

✅ 예시: 좌표 계산 후 적용
```js
let top = /* 복잡한 계산식 */;
let left = /* 복잡한 계산식 */;

elem.style.left = left; // '123px'
elem.style.top = top;   // '456px'
```

✅ 배경색, 글자색 등은 CSS 클래스 정의 후 JS에서 클래스 추가!

---

## 2️⃣ 클래스 관리하기 (`className`, `classList`)

🔸 `className`
- 문자열로 클래스 전체를 읽거나 쓸 때 사용
```html
<body class="main page">
  <script>
    alert(document.body.className); // main page
  </script>
</body>
```
**주의!** 전체를 덮어쓰기 때문에 기존 클래스는 사라짐.

🔸 `classList`
- 개별 클래스 추가/제거/토글할 때 사용 (add, remove, toggle, contains)
```html
<body class="main page">
  <script>
    document.body.classList.add('article');
    alert(document.body.className); // main page article
  </script>
</body>
```

---

## 3️⃣ 스타일 관리하기 (`style` 프로퍼티)

🔸 **카멜 표기법 주의!**  

| CSS 속성          | JS 속성                  |
| ----------------- | ----------------------- |
| background-color  | backgroundColor         |
| z-index           | zIndex                  |
| border-left-width | borderLeftWidth         |

🔸 **스타일 설정 예시**
```js
document.body.style.backgroundColor = prompt('배경 색은?', 'green');
```

🔸 **브라우저 접두사 스타일**
```js
button.style.MozBorderRadius = '5px';
button.style.WebkitBorderRadius = '5px';
```

🔸 **스타일 초기화는 `delete` X, 빈 문자열로!**
```js
document.body.style.display = "none"; // 숨기기
setTimeout(() => document.body.style.display = "", 1000); // 원래대로
```

---

## 4️⃣ 스타일 전체 교체하기 (`style.cssText`)

🔸 `style.cssText`는 전체 스타일 덮어쓰기
```html
<div id="div">버튼</div>
<script>
  div.style.cssText = \`
    color: red !important;
    background-color: yellow;
    width: 100px;
    text-align: center;
  \`;
  alert(div.style.cssText);
</script>
```
**주의**: 기존 스타일 다 지워질 수 있음! 꼭 필요한 경우에만 사용.

---

## 5️⃣ 단위 주의! (px 붙이기 꼭!)

```js
document.body.style.margin = 20; // ❌ 동작 안 함
document.body.style.margin = '20px'; // ⭕

alert(document.body.style.margin); // 20px
```

---

## 6️⃣ 스타일 값 읽기 (`getComputedStyle`)

- `style`은 inline 스타일만 읽음. CSS 클래스는 못 읽음!
- CSS 클래스 포함한 스타일은 `getComputedStyle`로 읽어야 함.

```html
<head>
  <style> body { color: red; margin: 5px } </style>
</head>
<body>
  <script>
    let computedStyle = getComputedStyle(document.body);
    alert(computedStyle.marginTop); // 5px
    alert(computedStyle.color); // rgb(255, 0, 0)
  </script>
</body>
```

✅ `getComputedStyle`은 **읽기 전용**, 수정 불가!  
✅ `getComputedStyle(elem).padding` ❌ → `paddingTop` 같이 전체 이름 필요!

**주의!** `:visited` 스타일은 못 읽음 (보안 이슈).

---

## 7️⃣ 과제: 알림 만들기

```js
showNotification({
  top: 10,      // top 위치 (px)
  right: 10,    // right 위치 (px)
  html: "Hello!", // 알림 내용
  className: "welcome" // (선택) 추가 클래스
});
```

- 알림은 **1.5초 후** 사라짐
- **CSS 포지셔닝** 사용 (position, top, right 등)

```html
<!DOCTYPE HTML>
<html>
<head>
  <link rel="stylesheet" href="index.css">
</head>

<body>

  <h2>Notification is on the right</h2>

  <p>
    Lorem ipsum dolor sit amet, consectetur adipisicing elit. Dolorum aspernatur quam ex eaque inventore quod voluptatem adipisci omnis nemo nulla fugit iste numquam ducimus cumque minima porro ea quidem maxime necessitatibus beatae labore soluta voluptatum
    magnam consequatur sit laboriosam velit excepturi laborum sequi eos placeat et quia deleniti? Corrupti velit impedit autem et obcaecati fuga debitis nemo ratione iste veniam amet dicta hic ipsam unde cupiditate incidunt aut iure ipsum officiis soluta
    temporibus. Tempore dicta ullam delectus numquam consectetur quisquam explicabo culpa excepturi placeat quo sequi molestias reprehenderit hic at nemo cumque voluptates quidem repellendus maiores unde earum molestiae ad.
  </p>

  <script>
    function showNotification({top = 0, right = 0, className, html}) {

      let notification = document.createElement('div');
      notification.className = "notification";
      if (className) {
        notification.classList.add(className);
      }

      notification.style.top = top + 'px';
      notification.style.right = right + 'px';

      notification.innerHTML = html;
      document.body.append(notification);

      setTimeout(() => notification.remove(), 1500);
    }

    // test it
    let i = 1;
    setInterval(() => {
      showNotification({
        top: 10,
        right: 10,
        html: 'Hello ' + i++,
        className: "welcome"
      });
    }, 2000);
  </script>


</body>
</html>
```

---

✅ **이 파일은 초보 개발자를 위한 스타일 & 클래스 관리 요약입니다!**
