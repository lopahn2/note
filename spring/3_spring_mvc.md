
# Servlet과 Spring DispatcherServlet의 구조와 흐름

## 1️⃣ Java EE와 Servlet 기본 개념

- **Java EE (현재는 Jakarta EE)**: 엔터프라이즈 애플리케이션을 위한 플랫폼. 1990년대에 등장.
- **Servlet**: Java EE의 핵심 구성 요소 중 하나. 클라이언트의 요청(request)과 서버의 응답(response)을 처리하는 자바 클래스.

### Servlet과 Servlet Container
- **Servlet Container (예: Tomcat, Jetty)**: Servlet을 실행시켜주는 환경.
- Servlet은 혼자 동작하지 못하고, 반드시 Container 안에서 실행.
- **실수 포인트**: Servlet과 Tomcat의 관계를 헷갈리면 안 됨! Tomcat은 Servlet을 실행시켜주는 "서버" 역할.

## 2️⃣ Spring의 DispatcherServlet

### DispatcherServlet의 계층 구조
```java
public class DispatcherServlet extends FrameworkServlet { }
public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware { }
public abstract class HttpServletBean extends HttpServlet implements EnvironmentCapable, EnvironmentAware { }
public abstract class HttpServlet extends GenericServlet { }
public abstract class GenericServlet implements Servlet, ServletConfig, java.io.Serializable { }
```
- 결국 DispatcherServlet도 Servlet의 일종 (GenericServlet → HttpServlet → DispatcherServlet)

### DispatcherServlet의 역할
- HTTP 요청을 받아서 적절한 핸들러를 찾고 (HandlerMapping)
- 그 핸들러를 실행시켜 줄 어댑터를 찾고 (HandlerAdapter)
- 최종적으로 View를 렌더링하거나 JSON 데이터를 응답.

## 3️⃣ DispatcherServlet의 요청 처리 흐름

### 핵심 메서드: `doDispatch`
```java
protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
  // Handler 검색 및 실행 과정
}
```
- 요청을 받으면
    1. Multipart 요청인지 확인
    2. HandlerMapping으로 Handler 찾기
    3. HandlerAdapter로 Handler 실행
    4. 예외 처리 (ExceptionHandler)
    5. View 렌더링

### DispatcherServlet이 사용하는 주요 Bean
- **HandlerMapping**: URL과 컨트롤러 매핑
- **HandlerAdapter**: 컨트롤러 실행
- **HandlerExceptionResolver**: 예외 처리
- **LocaleResolver**: 로케일 처리
- **MultipartResolver**: 파일 업로드 처리

#### 실수 포인트
- HandlerMapping과 HandlerAdapter의 역할을 혼동하지 말기!
- HandlerMapping은 "누구를" 실행할지, HandlerAdapter는 "어떻게" 실행할지.

## 4️⃣ @Controller와 @RestController

### @Controller
```java
@Controller
public class HelloController {
  @GetMapping("/hello")
  public String handle(Model model) {
    model.addAttribute("message", "Hello World!");
    return "index";
  }
}
```
- 반환값은 뷰 이름 (뷰를 찾아 렌더링).

### @RestController
```java
@RestController
public class ApiController {
  @GetMapping("/hello")
  public String hello() {
    return "Hello World!";
  }
}
```
- 반환값은 그대로 HTTP Response Body로 사용.
- 실수 포인트: `@RestController == @Controller + @ResponseBody`임을 잊지 말기!

### @RequestMapping vs @GetMapping 등
- @RequestMapping(method = RequestMethod.GET, path = "/") → @GetMapping("/")
- @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping도 제공.

## 5️⃣ 요청 데이터 바인딩 어노테이션

- `@RequestParam`: 쿼리 파라미터 바인딩
- `@RequestHeader`: 요청 헤더 바인딩
- `@CookieValue`: 쿠키 값 바인딩
- `@RequestBody`: JSON → 객체 변환
- `HttpEntity`: 요청 헤더 + 바디 동시 접근
- `@ResponseBody`: 반환 객체를 JSON으로 직렬화

## 6️⃣ 예외 처리와 ControllerAdvice

### @ExceptionHandler
```java
@ExceptionHandler(IOException.class)
public ResponseEntity<String> handle(IOException ex) {
  return ResponseEntity.status(500).body("IO Error!");
}
```

> `ResponseEntity`
> 
> 별도의 어노테이션 없이 메소드의 반환으로 사용하면 응답의 본문으로 사용됩니다.
> 다만, 상태와 헤더를 추가로 조작할 수 있는 여지가 있습니다.
 
### @ControllerAdvice
- 여러 Controller에 공통 ExceptionHandler 적용.

```java
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler { }
```

---

**실수 방지 체크리스트**
- DispatcherServlet은 단순한 컨트롤러가 아닌 **프론트 컨트롤러** 역할!
- HandlerMapping과 HandlerAdapter의 역할 구분!
- @RestController = @Controller + @ResponseBody
- ExceptionHandler의 범위 (@Controller 단위 vs @ControllerAdvice)

---
