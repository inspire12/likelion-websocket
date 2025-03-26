package com.inspire12.likelionwebsocket.holder;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketSessionHolder {

	private static Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

	public static Set<WebSocketSession> getSessions() {
		return sessions;
	}

	public static void addSession(WebSocketSession session) {
		sessions.add(session);
	}

	public static void removeSession(WebSocketSession session) {
		sessions.remove(session);
	}
}
