# Spring WebFlux 요청부터 응답까지  

## 비동기 웹 서버 구동  

스프링 부트가 구동 시, ApplicationContext를 선택하는 과정에서, WebFlux 이면  
`AnnotationConfigReactiveWebServerApplicationContext` 을 사용해 구동합니다.  

구동과정에서, `WebServerFactory` 를 통해서 웹 서버를 구동시키는데, 이때 쓰이는 웹 서버가 `ReactiveWebServerFactory` 입니다.  

```java
package org.springframework.boot.web.reactive.context;

public class ReactiveWebServerApplicationContext extends GenericReactiveWebApplicationContext
		implements ConfigurableWebServerApplicationContext {
    // ...
    @Override
    protected void onRefresh() {
        super.onRefresh();
        try {
            createWebServer();
        }
        catch (Throwable ex) {
            // ...
        }
    }
    
    private void createWebServer() {
        WebServerManager serverManager = this.serverManager;
        if (serverManager == null) {
            // ...
            String webServerFactoryBeanName = getWebServerFactoryBeanName();
            ReactiveWebServerFactory webServerFactory = getWebServerFactory(webServerFactoryBeanName);
            // ...
        }
        // ...
    }

    protected String getWebServerFactoryBeanName() {
        // Use bean names so that we don't consider the hierarchy
        String[] beanNames = getBeanFactory().getBeanNamesForType(ReactiveWebServerFactory.class);
        // ...
        return beanNames[0];
    }
}
```

WebFlux에서는 내장 `Netty` 웹서버를 사용하기 때문에 `NettyReactiveWebServerFactory` 구현체가 ReactiveWebServerFactory에 할당됩니다.  

```java
package org.springframework.boot.web.embedded.netty;

public class NettyReactiveWebServerFactory extends AbstractReactiveWebServerFactory {
    // ...
    @Override
    public WebServer getWebServer(HttpHandler httpHandler) {
        HttpServer httpServer = createHttpServer();
        ReactorHttpHandlerAdapter handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
        NettyWebServer webServer = createNettyWebServer(httpServer, handlerAdapter, this.lifecycleTimeout,
                getShutdown());
        webServer.setRouteProviders(this.routeProviders);
        return webServer;
    }

    NettyWebServer createNettyWebServer(HttpServer httpServer, ReactorHttpHandlerAdapter handlerAdapter,
                                        Duration lifecycleTimeout, Shutdown shutdown) {
        return new NettyWebServer(httpServer, handlerAdapter, lifecycleTimeout, shutdown, this.resourceFactory);
    }
}
```

`NettyWebServer` 는 `HttpServer` 와 요청을 처리할 `HandlerAdapter` 등을 받아서 생성됩니다.  
이후 웹 서버가 구동될 때, `DaemonAwaitThread`가 만들어져 서버가 구동됩니다.  

여기까지는 비동기 웹 서버가 구동될 뿐, 우리 코드가 리턴하는 Mono, Flux등의 `Publisher` 들을 처리하지 않습니다.  

## Subscribe

NettyWebServer가 `HttpServer`를 생성자에 넣어서 구동되는데, 이 HttpServer가 가지고 있는 `HttpServerHandler` 내부 클래스에서  
handlerAdapter를 사용해서 요청을 처리합니다.  

```java
package reactor.netty.http.server;

public abstract class HttpServer extends ServerTransport<HttpServer, HttpServerConfig> {
    // ...
    public final HttpServer handle(
            BiFunction<? super HttpServerRequest, ? super HttpServerResponse, ? extends Publisher<Void>> handler) {
        Objects.requireNonNull(handler, "handler");
        return childObserve(new HttpServerHandle(handler));
    }
    
    // ...

    static final class HttpServerHandle implements ConnectionObserver {

        final BiFunction<? super HttpServerRequest, ? super HttpServerResponse, ? extends Publisher<Void>> handler;

        HttpServerHandle(BiFunction<? super HttpServerRequest, ? super HttpServerResponse, ? extends Publisher<Void>> handler) {
            this.handler = handler;
        }

        @Override
        @SuppressWarnings("FutureReturnValueIgnored")
        public void onStateChange(Connection connection, State newState) {
            if (newState == HttpServerState.REQUEST_RECEIVED) {
                try {
                    // ...
                    HttpServerOperations ops = (HttpServerOperations) connection;
                    Publisher<Void> publisher = handler.apply(ops, ops);
                    Mono<Void> mono = Mono.deferContextual(ctx -> {
                        ops.currentContext = Context.of(ctx);
                        return Mono.fromDirect(publisher);
                    });
                    // ...
                    mono.subscribe(ops.disposeSubscriber());
                }
                catch (Throwable t) {
                    // ...
                }
            }
        }
    }
}
```

결과적으로, 해당 HttpServerHandler가 요청을 처리하고, 우리 어플리케이션이 리턴해주는 Publisher를 여기서 subscribe해 처리하는 것입니다.  
