package io.namoosori.travelclub.web.service;

import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 백그라운드로 돌아가는 빈을 등록하기 위해 @Service 어노테이션과,
 * WebSocket 의 연결점을 알려주는
 * @ServerEndoint 어노테이션을
 * 지정한 클래스를 생성합니다.
 * @ServerEndpoint 는 WebSocket을 활성화시키는 매핑 정보를 지정합니다.
 */
@Service
@ServerEndpoint(value="/chatt")
public class WebSocketChatt {
    private static Set<Session> clients =
            Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println("receive message : " + message);
        for(Session s : clients) {
            System.out.println("send data : " + message);
            s.getBasicRemote().sendText(message);

        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("open session : " + session.toString());
        if(!clients.contains(session)) {
            clients.add(session);
            System.out.println("session open : " + session);
        }else {
            System.out.println("이미 연결된 session 임!!!");
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("session close : " + session);
        clients.remove(session);
    }

}
