
# 🌐 WebSocket 완벽 가이드

## 1️⃣ WebSocket 개요

- **RFC 6455** 명세서 기반 프로토콜
- 서버와 브라우저 간 **양방향 통신** 지원
- **연결 유지 상태**에서 데이터 교환 (HTTP 요청/응답 필요 없음)

**사용 사례**: 온라인 게임, 주식 트레이딩, 채팅 앱 등

---

## 2️⃣ WebSocket 기본 사용법

```javascript
let socket = new WebSocket("wss://example.com");
```

- `ws://` (비보안) vs `wss://` (보안, 추천)
- 이벤트:
    - `open`: 연결 성공
    - `message`: 데이터 수신
    - `error`: 에러 발생
    - `close`: 연결 종료

---

## 3️⃣ 기본 예제

```javascript
let socket = new WebSocket("wss://javascript.info/article/websocket/demo/hello");

socket.onopen = function(e) {
  socket.send("Hello from client");
};

socket.onmessage = function(event) {
  console.log("Received:", event.data);
};

socket.onclose = function(event) {
  console.log("Closed", event.code, event.reason);
};

socket.onerror = function(error) {
  console.error("Error", error);
};
```

---

## 4️⃣ WebSocket 핸드셰이크

클라이언트 요청 (예시):

```http
GET /chat
Host: javascript.info
Connection: Upgrade
Upgrade: websocket
Sec-WebSocket-Key: Iv8io/9s+lYFgZWcXczP8Q==
Sec-WebSocket-Version: 13
```

서버 응답 (예시):

```http
101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: hsBlbuDTkk24srzEOTBUlZAlC2g=
```

---

## 5️⃣ Extensions & Subprotocols

```javascript
let socket = new WebSocket("wss://example.com", ["soap", "wamp"]);
```

서버 응답 (예시):

```http
Sec-WebSocket-Extensions: deflate-frame
Sec-WebSocket-Protocol: soap
```

---

## 6️⃣ 데이터 전송

```javascript
socket.send("text"); // 문자열
socket.send(blob);   // Blob
socket.send(arrayBuffer); // ArrayBuffer
```

수신 데이터 처리:

```javascript
socket.binaryType = "arraybuffer"; // 기본: "blob"

socket.onmessage = (event) => {
  console.log(event.data); // 텍스트 또는 바이너리
};
```

---

## 7️⃣ 송신 버퍼 관리

```javascript
if (socket.bufferedAmount === 0) {
  socket.send("data");
}
```

---

## 8️⃣ 연결 종료

```javascript
socket.close(1000, "Work complete");

socket.onclose = (event) => {
  console.log(event.code, event.reason);
};
```

- 코드:
    - `1000`: 정상 종료
    - `1001`: 클라이언트/서버 종료
    - `1006`: 네트워크 오류 등 비정상 종료

---

## 9️⃣ 상태 확인

```javascript
socket.readyState
// 0: CONNECTING
// 1: OPEN
// 2: CLOSING
// 3: CLOSED
```

---

## 🔨 채팅 앱 예제 (브라우저 코드)

```html
<form name="publish">
  <input type="text" name="message">
  <input type="submit" value="Send">
</form>
<div id="messages"></div>

<script>
  let socket = new WebSocket("wss://javascript.info/article/websocket/chat/ws");

  document.forms.publish.onsubmit = function() {
    socket.send(this.message.value);
    return false;
  };

  socket.onmessage = function(event) {
    let div = document.createElement("div");
    div.textContent = event.data;
    document.getElementById("messages").prepend(div);
  };
</script>
```

---

## 🖥️ 서버 코드 (Node.js + ws)

```javascript
const ws = require('ws');
const http = require('http');

const clients = new Set();
const wss = new ws.Server({ noServer: true });

http.createServer((req, res) => {
  wss.handleUpgrade(req, req.socket, Buffer.alloc(0), onSocketConnect);
}).listen(8080);

function onSocketConnect(ws) {
  clients.add(ws);

  ws.on('message', (message) => {
    for (let client of clients) {
      client.send(message);
    }
  });

  ws.on('close', () => clients.delete(ws));
}
```

---

## ✅ 요약

- WebSocket은 브라우저-서버 간 **지속 연결**을 유지해 **양방향 통신** 지원
- 크로스 오리진 제한 없음, 문자열/바이너리 데이터 전송 가능
- `socket.send()`, `socket.close()`
- `open`, `message`, `error`, `close` 이벤트

💡 주의: 자동 재연결/인증은 직접 구현 or 라이브러리 사용 필요

---
