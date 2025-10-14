# 🧩 자바스크립트 구조 분해 할당 (Destructuring Assignment)

## 📌 개념
- 배열이나 객체에서 값을 꺼내 **변수에 할당**할 수 있는 문법
- 주로 일부 값만 필요하거나, 함수 인자에 기본값을 줄 때 유용

---

## 📚 배열 분해

### 기본 사용
```js
let [firstName, surname] = ["Bora", "Lee"];
```

### 무시할 요소
```js
let [firstName, , title] = ["Julius", "Caesar", "Consul"];
```

### 이터러블 지원
```js
let [a, b, c] = "abc";
let [one, two, three] = new Set([1, 2, 3]);
```

### 객체에 분해
```js
let user = {};
[user.name, user.surname] = "Bora Lee".split(' ');
```

### 나머지 요소
```js
let [name1, name2, ...rest] = ["A", "B", "C", "D"];
```

### 기본값
```js
let [name = "Guest"] = [];
```

---

## 📚 객체 분해

### 기본 사용
```js
let {title, width, height} = { title: "Menu", width: 100, height: 200 };
```

### 다른 변수명으로 저장
```js
let {width: w, height: h} = { width: 100, height: 200 };
```

### 기본값
```js
let {title = "Untitled", width = 100} = {};
```

### 나머지 패턴
```js
let {title, ...rest} = { title: "Menu", width: 100, height: 200 };
```

### let 없이 할당 (괄호 필수)
```js
({title, width} = {title: "Menu", width: 100});
```

---

## 🔁 중첩 구조 분해
```js
let options = {
  size: { width: 100, height: 200 },
  items: ["Cake", "Donut"]
};

let {
  size: {width, height},
  items: [item1, item2],
  title = "Menu"
} = options;
```

---

## 🧠 함수 매개변수 구조 분해

### 기본
```js
function showMenu({title = "Untitled", width = 200, height = 100}) {
  alert(`${title} ${width} ${height}`);
}
```

### 중첩
```js
function showMenu({
  title = "Untitled",
  width: w = 100,
  items: [item1, item2]
}) {}
```

### 안전한 기본값 사용
```js
function showMenu({title = "Menu", width = 100} = {}) {}
```

---

## ✅ 요약

- `...rest`: 반드시 마지막 변수여야 함
- 객체 분해 시 다른 이름으로 저장하려면 `key: newVar` 형태 사용
- `=`로 기본값 설정 가능
- 함수 매개변수에도 동일한 문법 적용 가능
