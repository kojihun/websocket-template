package com.develop.websocket.handler;

import jakarta.annotation.Nonnull;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedHashSet;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private static LinkedHashSet<WebSocketSession> webSocketSessions = new LinkedHashSet<>();

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @Nonnull TextMessage message) throws Exception {
        if (webSocketSessions.size() > 2) {
            WebSocketSession oldSession = webSocketSessions.iterator().next();
            oldSession.sendMessage(new TextMessage("채팅이 종료되었습니다."));
            webSocketSessions.remove(oldSession);
        }

        boolean isSessionAlive = false;
        for (WebSocketSession webSocketSession : webSocketSessions) {
            if (webSocketSession.getId().equals(session.getId())) {
                isSessionAlive = true;
            }
        }

        if (isSessionAlive) {
            for (WebSocketSession webSocketSession : webSocketSessions) {
                webSocketSession.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(@Nonnull WebSocketSession session) throws Exception {
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(@Nonnull WebSocketSession session, @Nonnull CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
    }
}
