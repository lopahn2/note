# 클라이언트는 과연 누구인가?

## 논쟁

시퀀스 다이어그램을 그릴 때, 클라이언트의 정체가 브라우저인지 리액트서버인지 정확한 답을 할 수 없었습니다.

1. 유저가 브라우저에서 이벤트를 발생시키면 ( click... ) 리액트 서버가 백엔드 서버로 API 통신을 진행한다. 그러므로 시퀀스 다이어그램에서는 리액트 서버가 클라이언트이다.
2. 리액트는 CSR이므로 브라우저가 초기에 리액트 서버에 요청을 보내 index.html를 받아 백엔드 서버와 직접 API 통신을 진행한다. 그러므로 클라이언트는 브라우저이다.
	- 그러면 리액트 서버는 index.html만 주고 끝입니까?
	- 그러게요? 뭐지?

정확히 내용 정리가 되지 않은 채로 퇴근해서, 자료를 찾아 다시 정리해보았습니다.

## CSR
개념의 혼동이 있었던 이유가, 개발 환경과 배포 환경 사이의 차이때문이였습니다.

### 개발 환경

개발 환경에서는 주로 `npm run dev` 혹은 `npm start` 으로 React 서버를 구동시킵니다.
이때는 `webpack dev server` 라는 로컬 개발 서버가 구동, index.html과 번들링된 js를 ***메모리 상***에 생성해 서빙합니다.

#### 오해

이 부분에서 저는, 브라우저가 `리액트 서버` 에 요청을 보내면, 리액트 서버가 이를 받아 백엔드 서버와 API 통신을 하는, 일종의 브라우저와 백엔드 서버 사이의 프록시 서버 역할을 한다고 잘못알고 있었습니다.

### 배포 환경

배포 환경에서는 `webpack dev server` 가 아니라, 실제 웹서버 ( Nginx, Apache ... ) 등이 함께 배포됩니다. 즉, 리액트 서버는 index.html에 root tag만 있는 빈 껍데기와 번들된 JS 파일을 함께 배포된 웹서버를 통해 호스팅 해주는 역할을 수행합니다.

즉, 리액트 서버란 특별히 동적으로 요청을 수행하는 것이 아니라, 웹서버와 함께 배포되어 브라우저에게 정적 파일 제공, 캐싱, 프록시 등의 역할을 수행합니다.

이 과정에서 모든 요청이 동일 origin에서 발생한 것처럼, nginx의 proxy_pass를 통해 CORS 에러가 발생하지 않도록 조절 할 수 있습니다.

```config
server {
	listen 80;
	
	root /var/www/build;
	index index.html;

	location / {
		try_files $url /index.html;
	}
	
	location /api/ {
		proxy_pass http://backend.internal/;
	    proxy_set_header Host $host;
	    proxy_set_header X-Real-IP $remote_addr;
	}
}
```

> [!TIP]
> Q: CSR에서, 브라우저에서 index.html과 번들링된 js를 모두 받고 백엔드 서버와 직접 통신하는데 Front의 origin이 무슨 소용인가?  
> A: 브라우저는 번들링된 JS의 출처(Origin)를 기준으로 요청을 판단합니다. 사용자가 `www.hwany.com` 에 접속해 React 앱을 받아 JS 코드가 실행되고 fetch 요청을 보낼 때 브라우저는 이 요청의 출처를 `www.hwany.com` 으로 간주합니다.
> 
> 그리고 CORS는 브라우저의 정책이기에, curl이나 Postman은 CORS 문제가 발생하지 않습니다.

### 정리

1. React 라이브러리와 Nginx로 웹을 서빙하는 환경에서는 시퀀스 다이어그램 상에서 브라우저가 클라이언트.
2. React 라이브러리는 어떤 호스팅 서버가 붙느냐에 따라 CSR도 가능하고 SSR도 가능하기에 이 내용에 혼돈이 있었음
3. CORS는 접근하는 방식에 따라 여러 우회법이 존재하며, 이에 따른 판단은 개발자의 책임

>[!NOTE]
> - 리액트가 빌드되고 어떻게 서빙되는지 공부하기
> - 리액트 컴포넌트 라이프사이클
> - CSR과 SSR의 최소한의 워크플로우 이해하기

### 출처
[What is Client-side Rendering (CSR)?](https://prismic.io/blog/client-side-rendering)  
[Cross-Origin Resource Sharing (CORS)](https://developer.mozilla.org/en-US/docs/Web/HTTP/Guides/CORS)
