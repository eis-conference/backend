package com.wuzhengyu.conference.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wuzhengyu.conference.Model.VoteSocketMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



@Component
@ServerEndpoint("/vote/{username}")
public class VoteWebSocket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void openSession(@PathParam("username") String username, Session session) {
        clients.put(username, session);
        String message = "meeting id: " + username;
        logger.info(message);
    }

    /* @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(@PathParam("username") String username, String message) {
        logger.info(message);
        ObjectMapper objectMapper = new ObjectMapper();
        VoteSocketMsg socketMsg;
        try {
            socketMsg = objectMapper.readValue(message, VoteSocketMsg.class);
            // 主持人发的
            if (socketMsg.getType() == 0) {
                sendMessageAll("vote");
            }
            //投票者发的
            else{
                sendMessage(clients.get(socketMsg.getToUser()), socketMsg.getMsg());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        //当前的Session 移除
        clients.remove(username);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }

    private static void sendMessageAll(String message) {
        clients.forEach((sessionId, session) -> sendMessage(session, message));
    }

    private static void sendMessage(Session session, String message) {
        if (session == null) {
            return;
        }
        final RemoteEndpoint.Basic basic = session.getBasicRemote();
        if (basic == null) {
            return;
        }
        try {
            basic.sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

