# Dispatcher Servlet의 초기화 과정

springMVC를 공부하던 중 Dispatcher Servlet의 생성 과정이 궁금해서 공부했었습니다.  
Dispatcher Servlet의 생성자가 두 개인 것부터 시작이였습니다.  

`ApplicationContext` 를 주입하는 과정에서 분기가 갈라졌습니다.  

Dispatcher Servlet은, [springMVC 정리](springMVC.md) 문서에서 알 수 있듯이,  
Tomcat의 GenericServlet의 실제 클래스입니다.  

의문이 들었던 점은, `HttpServletBean.initServletBean()` 을 사용한 초기화와,  
`ApplicationContextAware`를 사용한 초기화 사이의 차이점을 몰랐기에 내용을 정리합니다.  

## Dispatcher Servlet의 빈 등록

Dispatcher Servlet은 스프링부트 어플리케이션에서 `@Bean(package org.springframework.context.annotation;)`으로써 관리됩니다.  

```java
package org.springframework.boot.autoconfigure.web.servlet;

public class DispatcherServletAutoConfiguration {
    protected static class DispatcherServletConfiguration {
        @Bean(name = DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
        public DispatcherServlet dispatcherServlet(WebMvcProperties webMvcProperties) {
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.setDispatchOptionsRequest(webMvcProperties.isDispatchOptionsRequest());
            dispatcherServlet.setDispatchTraceRequest(webMvcProperties.isDispatchTraceRequest());
            dispatcherServlet.setPublishEvents(webMvcProperties.isPublishRequestHandledEvents());
            dispatcherServlet.setEnableLoggingRequestDetails(webMvcProperties.isLogRequestDetails());
            return dispatcherServlet;
        }   
    }
}
```

Dispatcher Servlet의 생성자만 보았을 때, `스프링부트`가 ApplicationContext를 생성자에 주입해서 관리하는 것으로 알았지만,  
이는 틀린 내용이였습니다.  

기본 생성자를 통해, ApplicationContext가 없는 상태로 빈이 등록됩니다.  

## Application Context의 주입

Dispatcher Servlet이 Bean으로 등록된 후 상황을 생각해봅시다.  
우리는 `@SpringBootApplication(org.springframework.boot.autoconfigure.SpringBootApplication;)` 이 붙은 클래스의  
main 메서드에서 스프링 부트 어플리케이션을 실행합니다.  

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DispatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatchApplication.class, args);
    }

}
```

### SpringApplication.run()

해당 메서드는 static 메서드로 사용자에게 진입점을 제공해주는 역할을 합니다.  

그래서 내부로 들어가면 아래의 순서로 함수가 실행되게 됩니다.  

```java
public class SpringApplication {
    // ...
    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class<?>[] { primarySource }, args);
    }
    // ...
    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        return new SpringApplication(primarySources).run(args);
    }
    // ...
    public ConfigurableApplicationContext run(String... args) {
        // ... run logic
    }
}
```

최종 run 메서드에서는 `ConfigurableApplicationContext(org.springframework.context;)`, 즉 스프링 IoC 컨테이너를  
반환하는 것을 확인 할 수 있었습니다.  

#### public ConfigurableApplicationContext run(String... args)

짚고 넘어갈만한 코드만 정리하면 아래와 같습니다.  
기본적으로 context는 null인 상태로 create를 하게 됩니다.  

이후 `ApplicationContextFactory(org.springframework.boot;)`를 사용해서 context를 만들어주는데,  
이때 `DefaultApplicationContextFactory(org.springframework.boot;)` 구상 클래스가 사용됩니다.  

해당 클래스에서 주입해주는 ApplicationContext를 보면, `AnnotationConfigApplicationContext(org.springframework.context.annotation;)` 인데요,  

우리는 [springMVC 정리](springMVC.md)에서 기본 Application Context가 `AnnotationConfigServletWebServerApplicationContext` 를 기본 값으로 설정한다고 보았습니다.  

내용을 잘 읽어보면,

> {@link AnnotationConfigApplicationContext} for non-web applications.

란 내용이 있는데, 찾아보니 AnnotationConfigApplicationContext는 내장 서버(Tomcat) 기능이 없는 컨텍스트(스프링 어플리케이션)로  
스프링 부트에서 AnnotationConfigServletWebServerApplicationContext를 통해 해당 컨텍스트의 모든 기능을 포함하며  
Servlet API와의 통합 기능을 제공한다고 합니다.  

```java
package org.springframework.boot;

public ConfigurableApplicationContext run(String... args) {
    // ...
    ConfigurableApplicationContext context = null;
    // ...
    try {
        // ...
        context = createApplicationContext();
        // ...
        callRunners(context, applicationArguments);
    }
    catch (Throwable ex) {
        // ...
    }
    // ...
    return context;
}

protected ConfigurableApplicationContext createApplicationContext() {
    return this.applicationContextFactory.create(this.properties.getWebApplicationType());
}
```

```java
package org.springframework.boot;

class DefaultApplicationContextFactory implements ApplicationContextFactory {
    @Override
    public ConfigurableApplicationContext create(WebApplicationType webApplicationType) {
        try {
            return getFromSpringFactories(webApplicationType, ApplicationContextFactory::create,
                    this::createDefaultApplicationContext);
        }
        catch (Exception ex) {
            // ...
        }
    }

    private ConfigurableApplicationContext createDefaultApplicationContext() {
        if (!AotDetector.useGeneratedArtifacts()) {
            return new AnnotationConfigApplicationContext();
        }
        return new GenericApplicationContext();
    }    
}
```

그런데, 사실 우리가 사용하는 스프링부트 어플리케이션에서는 Spring 스펙이 아닌, Servlet 스펙을 사용해야 합니다.  
우리는 그 명세를, SpringApplication 생성자에서 확인할 수 있습니다. 

이후 ApplicationContext의 책임은 ApplicationContextFactory(여기에선 DefaultApplicationContextFactory(org.springframework.boot;))  
에 위임됩니다.  

```java
public class SpringApplication {
    public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
        // ...
        this.webApplicationType = WebApplicationType.deduceFromClasspath();
        // ...
    }

    // ...
    
    private ConfigurableEnvironment getOrCreateEnvironment() {
        if (this.environment != null) {
            return this.environment;
        }
        WebApplicationType webApplicationType = this.properties.getWebApplicationType();
        ConfigurableEnvironment environment = this.applicationContextFactory.createEnvironment(webApplicationType);
        // ...
    }
    // ...
}
```

```java
public enum WebApplicationType {
    NONE,
    SERVLET,
    REACTIVE;

    static WebApplicationType deduceFromClasspath() {
        if (ClassUtils.isPresent(WEBFLUX_INDICATOR_CLASS, null) && !ClassUtils.isPresent(WEBMVC_INDICATOR_CLASS, null)
                && !ClassUtils.isPresent(JERSEY_INDICATOR_CLASS, null)) {
            return WebApplicationType.REACTIVE;
        }
        for (String className : SERVLET_INDICATOR_CLASSES) {
            if (!ClassUtils.isPresent(className, null)) {
                return WebApplicationType.NONE;
            }
        }
        return WebApplicationType.SERVLET;
    }
}
```

이후 callRunner 메서드를 통해서 Bean 등록이 진행됩니다.  
이때 스프링부트는 `ConfigurableListableBeanFactory(org.springframework.beans.factory.config;)`를 사용해 Bean 생성요청을 보내고  
스프링 IoC 컨테이너 (application context) 가 빈 생성을 하기 시작합니다.  

```java
private void callRunners(ConfigurableApplicationContext context, ApplicationArguments args) {
    ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
    String[] beanNames = beanFactory.getBeanNamesForType(Runner.class);
    Map<Runner, String> instancesToBeanNames = new IdentityHashMap<>();
    for (String beanName : beanNames) {
        instancesToBeanNames.put(beanFactory.getBean(beanName, Runner.class), beanName);
    }
    Comparator<Object> comparator = getOrderComparator(beanFactory)
        .withSourceProvider(new FactoryAwareOrderSourceProvider(beanFactory, instancesToBeanNames));
    instancesToBeanNames.keySet().stream().sorted(comparator).forEach((runner) -> callRunner(runner, args));
}
```

## Aware

Spring 어플리케이션은 `Aware` 인터페이스를 통해 빈에 특정 자원을 주입해 줄수 있도록 합니다.

> Interface to be implemented by any object that wishes to be notified of the ApplicationContext that it runs in.
> 
> [ApplicationContextAware](https://docs.spring.io/spring-framework/docs/3.0.x/javadoc-api/org/springframework/context/ApplicationContextAware.html)

```java
package org.springframework.beans.factory;
public interface Aware { }
```

```java
package org.springframework.context;
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
```

`ApplicationContextAware(org.springframework.context;)`를 상속받은 Bean은 Spring Container가 빈을 등록할 때  
해당 Aware를 감지하고, 필요한 자원을(여기선 application context) 주입시켜줍니다.  

### DispatcherServlet

DispatcherServlet은 ApplicationContextAware 인터페이스를 구현한 FrameworkServlet 추상클래스를 상속받은 실제 클래스입니다.
따라서 스프링 컨테이너가 빈 생성시 ApplicationContext를 주입해줍니다.  

```java
package org.springframework.web.servlet;

public abstract class FrameworkServlet extends HttpServletBean implements ApplicationContextAware {
    // ...
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (this.webApplicationContext == null && applicationContext instanceof WebApplicationContext wac) {
            this.webApplicationContext = wac;
            this.webApplicationContextInjected = true;
        }
    }
    // ...
}
```

```java
package org.springframework.web.servlet;

public class DispatcherServlet extends FrameworkServlet {}
```

## Servlet Container Notice

여기까지가 빈 등록의 라이프사이클이고, 아직 서블릿 컨테이너에 연결은 되지 않은 상태입니다.  
결론적으로, applicationContext.refresh() 과정에서 `ServletWebServerApplicationContext`와 `ServletRegistrationBean`이  
ServletContext에 Dispatcher Servlet을 등록합니다.  


![](./assets/images/dispatcherServletLifeCycle.png)  

> 가장 큰 의문, 누가 `dispatcherServlet.init()` 을 호출하는가?
> Tomcat이 구동되면서, Servlet LifeCycle을 관리하는데, 이때 ServletContext에 등록된 DispatcherServlet의 init() 메서드를 호출합니다.

```java
package org.springframework.boot;

public ConfigurableApplicationContext run(String... args) {
    // ...
    ConfigurableApplicationContext context = null;
    // ...
    try {
        // ...
        context = createApplicationContext();
        // ...
        callRunners(context, applicationArguments);
        // ...
        refreshContext(context);
    }
    catch (Throwable ex) {
        // ...
    }
    // ...
    return context;
}

private void refreshContext(ConfigurableApplicationContext context) {
    // ...
    refresh(context);
}

protected void refresh(ConfigurableApplicationContext applicationContext) {
    applicationContext.refresh();
}
```
```java
package org.springframework.boot.web.servlet.context;
public class ServletWebServerApplicationContext extends GenericWebApplicationContext
        implements ConfigurableWebServerApplicationContext {
    // ...
    @Override
    public final void refresh() throws BeansException, IllegalStateException {
        try {
            super.refresh();
        }
        catch (RuntimeException ex) {
            // ...
        }
    }

    @Override
    protected void onRefresh() {
        super.onRefresh(); // GenericWebApplicationContext에 위임
        try {
            createWebServer();
        }
        catch (Throwable ex) {
            throw new ApplicationContextException("Unable to start web server", ex);
        }
    }

    private void createWebServer() {
        WebServer webServer = this.webServer;
        ServletContext servletContext = getServletContext(); // Context 생성시 필드에 넣어줌 @Nullable in GenericWebApplicationContext
        if (webServer == null && servletContext == null) {
            // ...
            ServletWebServerFactory factory = getWebServerFactory(); // TomcatServletWebServerFactory를 가져온다.
            // ...
            this.webServer = factory.getWebServer(getSelfInitializer());
            // ...
        }
        else if (servletContext != null) {
            try {
                getSelfInitializer().onStartup(servletContext); // ServletContextInitializer를 불러와서 onStartUp 실행. 그게 자신의 selfInitialize 함수를 호
            }
            catch (ServletException ex) {
                // ...
            }
        }
        // ...
    }

    private void selfInitialize(ServletContext servletContext) throws ServletException {
        // ...
        for (ServletContextInitializer initializerBean : getServletContextInitializerBeans()) {
            initializerBean.onStartup(servletContext); // ServletRegisterBean 등 여기서 시
        }
    }
}
```

```java
package org.springframework.context.support;
public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException, IllegalStateException {
        // ...
        try {
            // ...
            try {
                // ...
                // Initialize other special beans in specific context subclasses.
                onRefresh();
                // ...
            }
            catch (RuntimeException | Error ex ) {
                // ...
            }
            finally {
                // ...
            }
        }
        finally {
            // ...
        }
    }
    protected void onRefresh() throws BeansException {
        // For subclasses: do nothing by default.
        // ServletWebServerApplicationContext에서 위임받고, 다시 super 클래스로 올립니다.
        // 이때 super 클래스는 GenericWebApplicationContext 입니다.
    }
}
```
```java
package org.springframework.boot.web.embedded.tomcat;
public class TomcatServletWebServerFactory extends AbstractServletWebServerFactory
        implements ConfigurableTomcatWebServerFactory, ResourceLoaderAware {

    @Override
    public WebServer getWebServer(ServletContextInitializer... initializers) {
        if (this.disableMBeanRegistry) {
            Registry.disableRegistry();
        }
        Tomcat tomcat = new Tomcat();
        // ...
        for (LifecycleListener listener : getDefaultServerLifecycleListeners()) {
            tomcat.getServer().addLifecycleListener(listener);
        }
        Connector connector = new Connector(this.protocol);
        // ...
        tomcat.getService().addConnector(connector);
        // ...
        tomcat.setConnector(connector);
        // ...
        configureEngine(tomcat.getEngine());
        // ...
        prepareContext(tomcat.getHost(), initializers);
        return getTomcatWebServer(tomcat);
    }

    protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
        return new TomcatWebServer(tomcat, getPort() >= 0, getShutdown());
    }
}
```
```java
package org.springframework.boot.web.servlet;
public class ServletRegistrationBean<T extends Servlet> extends DynamicRegistrationBean<ServletRegistration.Dynamic> {
    // ...
    @Override
    protected ServletRegistration.Dynamic addRegistration(String description, ServletContext servletContext) {
        String name = getServletName();
        return servletContext.addServlet(name, this.servlet); // ServletContext에 DispatcherServlet 등록
    }
    // ...
}

```
