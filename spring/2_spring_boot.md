
# Spring Boot 개념 정리

## 1. Spring Boot의 목적과 탄생 배경

### Spring Framework의 등장 이유
- 과거 EJB 방식의 문제점: 비즈니스 로직이 특정 기술(EJB)에 종속됨 → 유지보수, 확장 어려움
- 이를 해결하기 위해 **Spring Framework** 등장
    - 핵심 원칙: **IoC(제어의 역전)**, **DI(의존성 주입)** → 개발자는 비즈니스 로직에 집중, 나머지는 Spring이 처리

### Spring Framework의 단점
- 기능은 많아졌지만, 설정 복잡성 증가
    - Servlet, Listener, Filter, Bean 등록 등 XML 설정
    - 예시:
      ```xml
      <servlet-mapping>...</servlet-mapping>
      <filter>...</filter>
      ```
- 개발자가 직접 설정해야 하니, 번거롭고 실수하기 쉽다!

### Spring Boot의 탄생 목적
- **독립 실행형 애플리케이션** 쉽게 제작
- 복잡한 설정을 최소화하고, 기본 설정(Auto-Configuration) 제공
- 필요한 부분만 Customize
- 즉, **Spring Boot = Spring의 생산성을 극대화한 도구!**

---

## 2. Spring Boot vs Spring Framework 차이점

| 구분 | Spring Framework | Spring Boot |
|---|---|---|
| 정의 | Java 기반 웹 애플리케이션 프레임워크 | Spring Framework의 확장 모듈 |
| 주요 특징 | 유연성, 의견 비제시형 | 생산성, 의견 제시형 |
| 내장 서버 | 없음 (Tomcat, Jetty 직접 설정 필요) | 내장 서버 제공 (Tomcat 기본) |
| 설정 방식 | XML 기반 설정 필요 | Auto-Configuration으로 자동 설정 |
| CLI 도구 | 없음 | 있음 (spring-boot-cli) |
| 사용 목적 | 유연한 기업용 앱 | 빠른 개발, REST API, 서비스 앱 등 |
| 의존성 주입 | 수동 설정 | 자동 구성 (Auto-Configuration) |

⚠️ **주의:**  
Spring Framework의 유연성은 강점이지만, 설정을 잘못하면 애플리케이션 실행 자체가 안 될 수 있음.  
Spring Boot는 기본값이 있지만, 필요에 따라 오버라이드 가능!

---

## 3. Convention over Configuration (CoC)

### CoC란?
- "설정보다는 관례": 기본값을 우선 사용, 필요한 부분만 따로 설정
- 덕분에 **필요한 것만 설정 = 설정 지옥 탈출**

### Spring Boot의 CoC 적용 예시
- `Auto-Configuration`이 바로 CoC의 구현체
- 기본 설정을 제공하고, 필요하면 직접 수정 (Override)

---

## 4. Auto-Configuration

### 핵심 개념
- `@SpringBootApplication` = 3개의 Annotation 조합
    - `@ComponentScan`: Bean 자동 탐지
    - `@EnableAutoConfiguration`: Auto-Configuration 활성화
    - `@Configuration`: 설정 클래스

### 실수하기 쉬운 부분
- **조건부 설정:** 이미 Bean이 존재하면 Auto-Config가 대체하지 않음!
    - `@ConditionalOnClass`, `@ConditionalOnMissingBean` 주의

### 코드 예시
```java
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@ConditionalOnMissingBean({WebMvcConfigurationSupport.class})
@AutoConfigureOrder(-2147483638)
@AutoConfigureAfter({DispatcherServletAutoConfiguration.class, ValidationAutoConfiguration.class})
public class WebMvcAutoConfiguration {
    ...
}
```

---

## 5. Custom Auto-Configuration

### 새로운 Auto-Configuration 작성
1. `@Configuration` 클래스 작성
    ```java
    @Configuration
    public class MySQLAutoconfiguration {
        //...
    }
    ```
2. `spring.factories`에 자동으로 등록됨
    ```properties
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.baeldung.autoconfiguration.MySQLAutoconfiguration
    ```
3. 조건부 Bean 등록
    ```java
    @Bean
    @ConditionalOnBean(name = "dataSource")
    @ConditionalOnMissingBean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() { ... }
    ```

### 실수 방지 포인트
- `@ConditionalOnBean` → 자신에게 의존하고 있는 Bean 존재 여부 체크 → 있을 때에만 만들어!
- `@ConditionalOnMissingBean` → 중복 방지! → 없을 때에만 만들어!

---

## 6. Properties 설정 및 활용

### Spring Framework vs Spring Boot
- Spring Framework: `@PropertySource` + `@Value` 필요
    ```java
    @Configuration
    @PropertySource("classpath:foo.properties")
    public class PropertiesWithJavaConfig { ... }
    ```
- Spring Boot: 간단하게 `application.properties` 또는 `application.yaml` 사용
    - 위치: `src/main/resources/`
    - 환경별 분리 가능: `application-dev.properties`, `application-prod.properties` → 추천하지 않는다. 다른 방법을 사용할 

### 환경 변수 & 커맨드라인 주입
- 환경 변수 주입
    ```bash
    $ export name=value
    $ java -jar app.jar
    ```
- 커맨드라인 주입
    ```bash
    $ java -jar app.jar --property=value
    $ java -Dproperty.name=value -jar app.jar
    ```

### `@ConfigurationProperties`로 그룹화된 Properties 관리
- `application.yaml` 예시
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/my-db
        username: myuser
        password: mypassword
        driver-class-name: org.postgresql.Driver
    ```
- Property 클래스 예시
    ```java
    // SpringDataSourceProperties.java
    @ConfigurationProperties("spring.datasource")    // spring.datasource 내 속성을 가져오기 위해 사용
    @ConstructorBinding    // 불변성을 위해 사용
    public class SpringDataSourceProperties {
        private final String url;
        private final String username;
        private final String password;
        private final String driverClassName;
    
        public SpringDataSourceProperties(
                @DefaultValue("jdbc:postgresql://localhost:5432/my-db") String url,    // default값 설정도 가능함(optional)
                @DefaultValue("myuser") String username,
                @DefaultValue("mypassword")String password,
                @DefaultValue("org.postgresql.Driver") String driverClassName) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.driverClassName = driverClassName;
        }

    // getter
    }
    // SpringDataSourcePropertiesConfig.java
    @Configuration
    @ConditionalOnMissingBean(SpringDataSourceProperties.class)    // 해당 Property 클래스가 없으면 생성하라는 조건부 annotation
    @EnableConfigurationProperties(SpringDataSourceProperties.class)    // 해당 Properties 클래스를 Bean으로 등록하기 위해 사용
    public class SpringDataSourcePropertiesConfig {
        // @Bean 으로 따로 등록할 필요 없음 
    }
    ```

---

✅ 끝! 필요한 부분은 언제든 질문해줘! 실무에서는 **자동 구성의 기본 동작과 Override 방법**을 꼭 숙지하자!
