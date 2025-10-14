
# 📏 요소 사이즈와 스크롤, 그리고 기하 프로퍼티

## 1️⃣ 요소의 크기와 위치를 다루는 기하 프로퍼티

**기하 프로퍼티(Geometry Properties)** 는 HTML 요소의 크기와 위치를 다루는 자바스크립트 속성들이에요.  
이걸 잘 알아야 레이아웃 제어나 스크롤 제어 같은 작업을 할 수 있습니다.

### **기하 프로퍼티 종류**
(외워둘 필요는 없지만, 이름과 개념을 파악해두세요!)

| 프로퍼티 | 의미 | 포함 범위 | 비고 |
| --- | --- | --- | --- |
| `offsetParent` | 좌표 계산 기준이 되는 조상 요소 | - | `position` 속성 가진 조상, 없으면 `body` |
| `offsetLeft` / `offsetTop` | `offsetParent` 기준 위치 | 테두리 포함 | px 단위, 정수 반환 |
| `offsetWidth` / `offsetHeight` | 요소의 전체 크기 | 콘텐츠 + 패딩 + 테두리 | 스크롤바 포함 (브라우저마다 다름) |
| `clientLeft` / `clientTop` | 테두리 두께 | - | RTL 언어일 땐 주의 (스크롤바 포함될 수도 있음) |
| `clientWidth` / `clientHeight` | 콘텐츠 + 패딩 크기 | 테두리 제외, 스크롤바 제외 | 가로/세로 스크롤바 유무에 따라 값 다를 수 있음 |
| `scrollWidth` / `scrollHeight` | 콘텐츠 전체 크기 | 콘텐츠 + 패딩 + 스크롤로 가려진 영역 포함 | |
| `scrollLeft` / `scrollTop` | 스크롤된 거리 | - | 값 수정 가능 (스크롤 조작) |

---

## 2️⃣ 스크롤과 크기 계산의 함정

### 📌 스크롤바 주의!

- **스크롤바는 크기를 먹는다.**  
  예를 들어 `width:300px`이어도, 세로 스크롤바가 16px이면 실제 콘텐츠 영역은 284px이 될 수 있어요.
- 브라우저마다 처리 방식 다름 (크로스 브라우저 이슈)
- 스크롤바 고려 안 하면 레이아웃 깨질 수 있음

### 📌 `getComputedStyle` vs. 기하 프로퍼티

| 비교 항목 | `getComputedStyle(...).width` | `clientWidth` |
| --- | --- | --- |
| 반환 타입 | 문자열 ("300px") | 숫자 (300) |
| 값의 의미 | CSS width (box-sizing 영향 받음) | 콘텐츠 + 패딩 (테두리 제외, 스크롤바 제외) |
| 스크롤바 고려 | 브라우저마다 다름 | 항상 제외 |
| 인라인 요소 | "auto" 반환 | 0 또는 오류 가능성 |

> 초보자가 많이 헷갈리는 부분: `getComputedStyle`로 너비 구하면 `auto` 나올 수 있고, 스크롤바 처리도 다를 수 있음. **`clientWidth` 쓰세요!**

---

## 3️⃣ 주요 코드 예제

### 📌 스크롤바 크기 구하기

```javascript
let div = document.createElement('div');

div.style.overflowY = 'scroll';
div.style.width = '50px';
div.style.height = '50px';

document.body.append(div);
let scrollWidth = div.offsetWidth - div.clientWidth;

div.remove();

alert(scrollWidth);
```

### 📌 아래쪽 스크롤 크기 구하기

```javascript
let scrollBottom = elem.scrollHeight - elem.scrollTop - elem.clientHeight;
```

### 📌 요소를 가운데에 배치하기

```javascript
ball.style.left = Math.round(field.clientWidth / 2 - ball.offsetWidth / 2) + 'px';
ball.style.top = Math.round(field.clientHeight / 2 - ball.offsetHeight / 2) + 'px';
```

**주의**: 이미지 요소(`<img>`)는 `width`/`height`가 설정되지 않으면 `offsetWidth`가 0이 될 수 있음!  
꼭 CSS나 속성으로 `width`/`height`를 지정해 주세요.

---

## 4️⃣ 초보자가 주의해야 할 점

✅ **스크롤바는 브라우저마다 다르다**: 항상 `clientWidth`, `scrollWidth`로 계산  
✅ **getComputedStyle로는 정확한 크기를 못 얻을 수 있다**  
✅ **스크롤된 위치는 `scrollTop`, `scrollLeft`**  
✅ **기하 프로퍼티는 display: none 상태에선 무효(0 반환)**  
✅ **이미지 요소의 크기는 로드 후에야 알 수 있다**
