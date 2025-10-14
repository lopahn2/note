
# DTO Pattern (Data Transfer Object) - 쉽게 설명한 가이드

## 1. 개요

DTO (Data Transfer Object) 패턴은 서로 다른 계층 간에 데이터를 전달할 때 사용되는 객체입니다. 이 패턴의 목적은 **원격 호출 시 메서드 호출 횟수를 줄이고**, **직렬화 로직을 캡슐화**하여 **도메인 모델과 프레젠테이션 계층을 분리**하는 것입니다.

## 2. DTO 패턴이란?

DTO는 **마틴 파울러(Martin Fowler)**의 저서 *EAA (Enterprise Application Architecture)*에서 소개된 개념으로, 주로 다음과 같은 상황에서 유용합니다:

- 여러 파라미터를 묶어 네트워크 왕복 호출 수를 줄임
- 직렬화 포맷을 한 곳에서 관리 가능 (예: JSON, XML 등)
- 도메인 모델과 프레젠테이션 계층의 독립성 확보

## 3. 어떻게 사용하나?

DTO는 **POJO** 형태로 작성됩니다:

- 비즈니스 로직이 없음
- getter, setter 위주
- 필요시 직렬화 관련 메서드 포함

주로 **프레젠테이션 계층 또는 파사드(facade) 계층**에서 **매퍼 컴포넌트**를 통해 도메인 객체 ↔ DTO 변환을 수행합니다.

## 4. 언제 사용하는가?

- 클라이언트-서버 간 API 통신 (특히 원격 호출)
- 복잡한 도메인 모델을 단순화하여 클라이언트가 필요한 데이터만 제공
- 여러 도메인 객체 데이터를 하나의 응답으로 구성해야 할 때
- 도메인 모델을 그대로 노출하지 않고, 클라이언트 요구에 맞춘 포맷을 제공하고 싶을 때

## 5. 사용 예제

### 5.1. Domain vs DTO

도메인 모델:

```java
public class User {
    private String id;
    private String name;
    private String password;
    private List<Role> roles;

    public User(String name, String password, List<Role> roles) {
        this.name = Objects.requireNonNull(name);
        this.password = this.encrypt(password);
        this.roles = Objects.requireNonNull(roles);
    }

    String encrypt(String password) {
        // encryption logic
    }
}
```

```java
public class Role {
    private String id;
    private String name;
}
```

DTO 예시:

```java
public class UserDTO {
    private String name;
    private List<String> roles;
}
```

```java
public class UserCreationDTO {
    private String name;
    private String password;
    private List<String> roles;
}
```

- UserDTO는 클라이언트에게 전달할 때 사용 (비밀번호 제외)
- UserCreationDTO는 사용자 생성 요청 시 사용

### 5.2. 연결 (Controller + Mapper)

```java
@RestController
@RequestMapping("/users")
class UserController {
    private UserService userService;
    private RoleService roleService;
    private Mapper mapper;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAll()
            .stream()
            .map(mapper::toDto)
            .collect(toList());
    }

    @PostMapping
    public UserIdDTO create(@RequestBody UserCreationDTO userDTO) {
        User user = mapper.toUser(userDTO);

        userDTO.getRoles()
            .stream()
            .map(role -> roleService.getOrCreate(role))
            .forEach(user::addRole);

        userService.save(user);
        return new UserIdDTO(user.getId());
    }
}
```

```java
@Component
class Mapper {
    public UserDTO toDto(User user) {
        List<String> roles = user.getRoles()
            .stream()
            .map(Role::getName)
            .collect(toList());
        return new UserDTO(user.getName(), roles);
    }

    public User toUser(UserCreationDTO userDTO) {
        return new User(userDTO.getName(), userDTO.getPassword(), new ArrayList<>());
    }
}
```

## 6. 흔한 실수

1. **DTO를 남발**: 상황마다 DTO를 새로 만들면 유지보수 지옥. 재사용성 고려.
2. **DTO에 비즈니스 로직 포함**: 절대 안 됨. DTO는 데이터 전달 전용.
3. **LocalDTO 사용**: 도메인 간 전달용 DTO. 유지보수 어려움. CQRS 같은 대체 패턴 고려.

## 7. 결론

DTO는 간단하지만 강력한 패턴입니다. 데이터 전송을 최적화하고, 도메인-프레젠테이션 분리를 통해 유지보수를 쉽게 만들어줍니다.

하지만 남용하지 말고, 목적에 맞게 간결하게 사용하세요.
