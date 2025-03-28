package com.inspire12.likelionwebsocket.handshake;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler
{

    @Override
    public Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userName = getTokenFromRequest(request);
        if (userName != null && validateToken(userName))  {
            return new Principal() {
                @Override
                public String getName() {
                    return userName;
                }
            };
        }
        return null;
    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        // URL 파라미터에서 토큰 추출
        String query = request.getURI().getQuery();
        if (query != null && query.contains("username=")) {
            return query.split("username=")[1];
        }
        return null;
    }

    private boolean validateToken(String userName) {
        // JWT 토큰 검증 로직 구현 (JWT 라이브러리 이용)
        return true;
    }

    private String extractUsernameFromToken(String userName) {
        // JWT 토큰에서 사용자 정보(username) 추출 로직 구현
        return userName; // 예시로 간략히 처리
    }

}
