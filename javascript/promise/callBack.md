
# 자바스크립트에서 콜백을 이용한 비동기 처리

## 1. 콜백이란?

콜백(callback)은 **특정 작업이 끝난 후 실행되는 함수**야. 자바스크립트에서는 이런 방식을 통해 비동기 작업(예: 파일 읽기, 서버 요청 등)이 끝난 후 실행될 코드를 정의할 수 있어.

---

## 2. 비동기 작업과 `loadScript` 예제

브라우저에서 외부 스크립트를 불러오는 `loadScript` 함수를 보자.

```javascript
function loadScript(src) {
  let script = document.createElement('script');
  script.src = src;
  document.head.append(script);
}
```

이 함수는 `<script>` 태그를 만들어 문서에 추가해. 하지만 **스크립트가 비동기적으로 로드되기 때문에,** `loadScript` 이후의 코드가 먼저 실행될 수 있어.

---

## 3. 콜백 함수 추가

```javascript
function loadScript(src, callback) {
  let script = document.createElement('script');
  script.src = src;
  script.onload = () => callback(script);
  document.head.append(script);
}
```

이제 `loadScript`는 스크립트 로딩이 끝난 뒤 콜백 함수를 실행해. 예시:

```javascript
loadScript('/my/script.js', function() {
  newFunction(); // 스크립트 로딩 완료 후 실행
});
```

---

## 4. 콜백 안에 콜백 (중첩 호출)

```javascript
loadScript('/my/script.js', function(script) {
  loadScript('/my/script2.js', function(script) {
    loadScript('/my/script3.js', function(script) {
      // 세 스크립트가 모두 로딩된 후 실행
    });
  });
});
```

이렇게 중첩이 많아지면 코드가 **복잡하고 읽기 어려워지는 '콜백 지옥(callback hell)'** 이 발생해.

---

## 5. 에러 처리: 오류 우선 콜백 패턴

```javascript
function loadScript(src, callback) {
  let script = document.createElement('script');
  script.src = src;

  script.onload = () => callback(null, script);
  script.onerror = () => callback(new Error(`${src}를 불러오는 도중에 에러가 발생했습니다.`));

  document.head.append(script);
}
```

사용 예시:

```javascript
loadScript('/my/script.js', function(error, script) {
  if (error) {
    // 에러 처리
  } else {
    // 스크립트 사용
  }
});
```

**오류 우선 콜백(error-first callback)** 은 콜백의 첫 번째 인수로 에러를 받고, 두 번째 인수로 정상 결과를 받아. 이건 Node.js에서도 자주 사용하는 방식이야.

---

## 6. 멸망의 피라미드(Pyramid of Doom)

```javascript
loadScript('1.js', function(error, script) {
  if (error) handleError(error);
  else {
    loadScript('2.js', function(error, script) {
      if (error) handleError(error);
      else {
        loadScript('3.js', function(error, script) {
          if (error) handleError(error);
          else {
            // 모든 스크립트 로딩 후 작업
          }
        });
      }
    });
  }
});
```

이런 식으로 **중첩이 깊어질수록 코드 유지보수가 어려워져.**

---

## 7. 중첩 피하기: 함수로 분리

```javascript
loadScript('1.js', step1);

function step1(error, script) {
  if (error) handleError(error);
  else loadScript('2.js', step2);
}

function step2(error, script) {
  if (error) handleError(error);
  else loadScript('3.js', step3);
}

function step3(error, script) {
  if (error) handleError(error);
  else {
    // 후속 작업
  }
}
```

중첩을 피하기 위해 함수를 분리할 수 있어. 하지만 이 방식도 **코드가 흩어져 가독성이 떨어지고 재사용이 어렵다는 단점이 있어.**

---

## 요약

- 콜백은 비동기 작업 후 실행되는 함수.
- 콜백 지옥은 중첩된 콜백 구조로 인해 발생.
- 에러 처리는 오류 우선 콜백 패턴을 따르는 것이 일반적.
- 중첩을 피하기 위해 함수 분리 가능하지만 코드 가독성이 떨어짐.
- 다음 장에서는 더 나은 해결책인 **프라미스(Promise)** 를 다룰 예정.

---

## 초보 개발자가 주의할 점

- `loadScript` 같은 함수는 **스크립트 로딩이 끝나기 전에 다음 코드가 실행**될 수 있으므로 콜백 없이 함수를 바로 호출하면 안 돼.
- 에러 처리를 잊지 말고, 항상 `onerror` 또는 `callback(error)` 를 체크하자.
- 콜백 중첩은 가독성을 해치므로, 되도록 함수 분리나 다른 방식(Promise 등)을 고민해보자.
