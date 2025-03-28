package com.inspire12.likelionwebsocket.handshake;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
  @Override
  protected Principal determineUser(
      ServerHttpRequest request,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes
  ) {
    return (UserPrincipal) () -> UUID.randomUUID().toString();
  }
}
