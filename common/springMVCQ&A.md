# SpringMVC Q&A

1. ApplicationContextAware랑 initServletBean() 은 언제 다르게 실행되나? 차이가 무엇인가? 둘다 webApplicationContext를 등록하는 역할인데
initServletBean ->servlet container(tomcat)가 servlet의 생애주기를 관리할 때 사용한다. 그때 호출된다.


2. doService가 호출하는 doDispatch가 deprecation됐는데, 그럼 어떻게 되는것? 스프링 3.4.5버전
문제가 있어요. 다른 타입의 아이와 관련이 있어요. Dispatcher머시기와 관련된 통합하기 위해. -> 쓰지말라는 것이 아니라, deprecation되는 것을 dispatcher의 doDispatch가 쓰고있기 때문에 에러 띄우지 마세요. @SuppressWarning!




servlet -> server + -let(something small) = 서버에서 작게 돌아가는 무언가!

Jetbrains IDE TypeHierachy

JakartaEE 공부해보세용

정리 중에 Boot가 하는건지? 스프링인지? 

공통 : 너무 하나만 집중한 타입, 역직렬화는 좋은데 xml은 ? handlerExcution 앞뒤 ? rootContext 와 webContext는 왜 나뉘어있는지?
디자인패턴 지식 중요 dispatcher, chain, adapter 다 패턴 이름이다. 

javax -> java extension -> java스펙을 공개해서 인터페이스를 사용해서 잘 만들어봐! 
javax 중 enterprise에서 쓸만한 것이 javaEE -> oracle -> 여기서 무료라이센스로 가져간게 eclipse foundation이고 이게 jakarta 프로젝트
javaSE 는 jdk 그 자체

jakartaEE의 Bean과 Spring의 Bean은 닮은점도 있고 다른 것도 있음 ( 실제로 아니고 동음이의어 )
IoC 컨테이터의 등록되어서 사용되는게 SpringBean

Boot, core <- IoC Container, tomcat 등등 구분을 잘하는 것이 중요!

Aware(형용사) <- Spring Context
