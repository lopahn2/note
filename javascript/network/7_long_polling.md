
# 🔄 롱 폴링 (Long Polling) 정리

## 1️⃣ 폴링(Polling) 기본

- 클라이언트가 일정 주기로 서버에 요청 보내는 방식
- 10초마다 "새로운 메시지 있어?" 요청 → 응답 받음

**단점**:
- 메시지가 늦게 올 수 있음 (최대 지연 = 주기)
- 메시지가 없어도 계속 요청 → 서버 부하

---

## 2️⃣ 롱 폴링(Long Polling)

- 클라이언트 요청 후 **서버는 응답을 바로 보내지 않음**
- 새로운 메시지가 생길 때까지 대기
- 메시지 생기면 서버가 응답 → 클라이언트는 새 요청 보냄 → 반복

**흐름**:

1️⃣ 클라이언트 요청 전송 (`fetch`)

2️⃣ 서버는 메시지 생길 때까지 기다림

3️⃣ 메시지 생기면 응답 반환

4️⃣ 클라이언트는 응답 받고 새 요청 전송

5️⃣ 오류나 연결 끊김 시 → 새 요청으로 재접속

---

## 3️⃣ 클라이언트 코드 예시

```javascript
async function subscribe() {
  let response = await fetch("/subscribe");

  if (response.status == 502) {
    // 서버 타임아웃 발생
    await subscribe(); // 다시 요청
  } else if (response.status != 200) {
    showMessage(response.statusText);
    await new Promise(resolve => setTimeout(resolve, 1000));
    await subscribe(); // 재연결
  } else {
    let message = await response.text();
    showMessage(message);
    await subscribe(); // 다시 요청
  }
}

subscribe();
```

---

## 4️⃣ 서버 주의사항

- **동시 연결(pending connections)** 많아짐
- PHP, Ruby 등 전통적인 서버는 연결마다 프로세스 필요 → 부하 ↑
- Node.js 같은 이벤트 기반 서버는 잘 처리 가능
- 언어보다 **서버 아키텍처**가 중요!

---

## 5️⃣ 롱 폴링의 적합한 상황

- **메시지가 드물게 오는 경우** (ex. 알림, 간헐적 데이터)
- **메시지가 자주 오는 경우** → WebSocket, Server-Sent Events 권장

---

✅ **롱 폴링 핵심**

- 클라이언트 → 요청
- 서버 → 대기 후 응답
- 클라이언트 → 응답 받으면 즉시 새 요청
- 네트워크 오류/타임아웃 → 즉시 새 요청

---

🔍 롱 폴링은 쉽고 간단하지만, 상황에 맞는 선택이 필요! 🚀
