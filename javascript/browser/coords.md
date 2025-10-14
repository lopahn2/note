
# 📍 좌표와 위치 - 요소 위치 제어의 핵심

## 1️⃣ 좌표 체계

- **창 기준 좌표 (clientX/clientY)**: 브라우저 창의 왼쪽 상단(0,0) 기준. `position:fixed`와 비슷.
- **문서 기준 좌표 (pageX/pageY)**: 문서 전체의 왼쪽 상단(0,0) 기준. `position:absolute`와 비슷.

📌 스크롤 전에는 두 좌표 체계 동일하지만, 스크롤 후엔 clientX/clientY 값이 변하고 pageX/pageY는 그대로!

---

## 2️⃣ getBoundingClientRect()

```javascript
let rect = elem.getBoundingClientRect();
```

- 반환값: DOMRect 객체 (x, y, width, height, top, bottom, left, right)
- **좌표 기준**: 창 기준 (스크롤 포함 X)
- 주의:
    - 소수점 좌표 가능 (10.5 등)
    - 음수 가능 (스크롤 위쪽으로 벗어난 경우)
    - x, y = left, top (대부분의 경우)

예: 요소 아래 메시지 띄우기

```javascript
let coords = elem.getBoundingClientRect();
message.style.left = coords.left + 'px';
message.style.top = coords.bottom + 'px';
```

---

## 3️⃣ 문서 기준 좌표 구하기

```javascript
function getCoords(elem) {
  let box = elem.getBoundingClientRect();
  return {
    top: box.top + window.pageYOffset,
    left: box.left + window.pageXOffset,
    bottom: box.bottom + window.pageYOffset,
    right: box.right + window.pageXOffset
  };
}
```

✅ `position:absolute` 사용 시 문서 기준 좌표 필요!  
✅ `position:fixed`는 getBoundingClientRect() 값 바로 사용.

---

## 4️⃣ elementFromPoint(x, y)

```javascript
let elem = document.elementFromPoint(x, y);
```

- **창 기준 좌표**에서 가장 위에 있는 요소 반환
- 스크롤 위치에 따라 다른 요소 반환될 수 있음
- 창 밖 좌표는 `null` 반환 (주의!)

예: 정중앙 요소 선택

```javascript
let centerX = document.documentElement.clientWidth / 2;
let centerY = document.documentElement.clientHeight / 2;
let elem = document.elementFromPoint(centerX, centerY);
```

---

## 5️⃣ 위치 고정 - position:fixed vs position:absolute

| 위치 | 좌표 기준 | 스크롤 영향 | 사용법 |
| --- | --- | --- | --- |
| fixed | 창 기준 | 스크롤 무관 | UI 고정 (ex. 모달) |
| absolute | 문서 기준 | 스크롤 영향 받음 | 요소 따라가기 (ex. 툴팁, 말풍선) |

---

## 6️⃣ 과제 핵심 코드

- **필드 좌표 구하기**

```javascript
let coords = field.getBoundingClientRect();
let answer1 = [coords.left, coords.top];
let answer2 = [coords.right, coords.bottom];
let answer3 = [coords.left + field.clientLeft, coords.top + field.clientTop];
let answer4 = [coords.left + field.clientLeft + field.clientWidth, coords.top + field.clientTop + field.clientHeight];
```

- **요소 근처 노트 띄우기**

```javascript
function positionAt(anchor, position, elem) {
  let coords = getCoords(anchor);
  if (position == "top") {
    elem.style.left = coords.left + 'px';
    elem.style.top = (coords.top - elem.offsetHeight) + 'px';
  } else if (position == "right") {
    elem.style.left = (coords.left + anchor.offsetWidth) + 'px';
    elem.style.top = coords.top + 'px';
  } else if (position == "bottom") {
    elem.style.left = coords.left + 'px';
    elem.style.top = (coords.top + anchor.offsetHeight) + 'px';
  }
}
```

✅ position:absolute로 위치 고정, getCoords()로 문서 기준 좌표!

✅ position:fixed 대신 absolute 사용하면 스크롤에도 따라다님!

✅ positionAt 함수에 top-in, bottom-in, right-in 추가하면 내부에 붙는 것도 가능!

---

## 7️⃣ 정리

- 창 기준: getBoundingClientRect() (스크롤 무관)
- 문서 기준: getBoundingClientRect() + 스크롤
- elementFromPoint(x,y): 창 기준 요소 탐색
- 위치 고정: absolute(스크롤 따라감), fixed(스크롤 무관)
