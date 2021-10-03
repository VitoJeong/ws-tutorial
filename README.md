# ws-tutorial
> Spring WebSocket을 사용해보는 리포지토리
<br/>

##### WebSocket을 알아보기전에 Socket에 대해 알고 싶다면 [이 글](https://github.com/VitoJeong/java_socket/blob/master/README.md) 를 참고한다.

<br/>

### 일반적인 http통신을 하는 서버들과 달리 채팅 서버는 socket통신을 하는 서버가 필요하다.

클라이언트가 먼저 요청하지 않아도 서버가 먼저 데이터를 보내기 위해 WebSocket이 존재하기 전에는 **Polling, Long Polling, Streaming** 등의 방식으로 해결했다.

### Websocket
* Websocket은 기존의 단방향 HTTP프로토콜과 호환되어 양방향 통신을 제공하기 위해 개발된 프로토콜
* TCP/UDP를 이용하는 일반 Socket통신과 달리 HTTP 80 port를 이용하므로 방화벽에 제약이 없다
* 접속까지는 HTTP 프로토콜을 이용하고 그 이후의 통신은 자체적인 Websocket 프로토콜로 통신하게 된다.
* 지속적으로 서버와 클라이언트가 high frequency/low latency로 통신해야하는 경우 http보다 WebSocket이 유리하다.
<br/>

### WebSocket 접속 과정

![image](https://user-images.githubusercontent.com/63029576/135759551-2067cc23-343d-41d6-9153-fe3514eab11a.png)
웹소켓 접속 과정은 **TCP/IP 접속** 그리고 **웹소켓 열기 HandShake** 과정으로 나눌 수 있다.

## Spring WebSocket

> 스프링 4.x부터는 WebSocket 을 아래와 같이 지원한다.

1. 메시지 전송과 수신을 위한 하위 레벨 API
2. 스프링 MVC 컨트롤러에서의 메시지 처리를 이한 상위 레벨 API
3. 메시지 전송을 위한 메시징 템플릿
4. 브라우저, 서버, 프록시에서 웹 소켓 지원 부족을 지원하기 위한 SockJS
<br/>

### Spring Web Socket 구현 순서

1. 의존성 추가
    ``` 
    implementation 'org.springframework.boot:spring-boot-starter-websocket'
    ```
2. WebSocketHandler 구현
    ```
    @Slf4j
    @Component
    public class WebSocketHandler extends TextWebSocketHandler
    ```
3. WebSocket 설정 (`WebSocketHandler`를 사용할 수 있게끔 설정)
    ```
    @Configuration
    @EnableWebSocket
    @RequiredArgsConstructor
    public class WebSocketConfig implements WebSocketConfigurer {
        private final WebSocketHandler webSocketHandler;
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(webSocketHandler,"/chat");
        }
    }
    ```
