package com.example.demo.demo.server;

import com.example.demo.demo.domain.BaseMsg;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/wsMessage/{userId}")
@Component
public class WebSocketServer {

    // 用于存放用户id和session映射关系的Map
    private static Map<String,Session> sessionMap = new HashMap<>();

    /**
     * 连接建立成功处理
     **/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) {

        if (sessionMap.containsKey(userId)) {
            try {
                sessionMap.get(userId).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sessionMap.put(userId, session);

        try {
            session.getBasicRemote().sendText("上线成功");
        } catch (IOException e) {
            System.out.println("用户:"+userId+",上线失败");
        }
    }

    /**
     * 收到客户端消息处理
     **/
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("userId") String userId) {
        if(StringUtils.isNotBlank(message)){
            try {
                Gson gson = new Gson();
                BaseMsg clientMsg = gson.fromJson(message, BaseMsg.class);
                String target = clientMsg.getTarget();
                String type = clientMsg.getType();
                String note = clientMsg.getMsg();

                switch (type) {
                    case "sendMsg": {
                        BaseMsg msg = new BaseMsg("msg", userId, target, note);
                        if( StringUtils.isNotBlank(target) && sessionMap.containsKey(target)){
                            String msgStr = gson.toJson(msg);
                            sessionMap.get(target).getBasicRemote().sendText(msgStr);
                        } else {
                            System.out.println("目标用户" + target + "未上线");
                        }
                        break;
                    }
                    default: {

                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭处理
     */
    @OnClose
    public void onClose( @PathParam("userId") String userId) {
        if(sessionMap.containsKey(userId)){
            sessionMap.remove(userId);
        }
    }

    /**
     * 连接错误处理
     */
    @OnError
    public void onError(Session session, Throwable error, @PathParam("userId") String userId) {
        System.out.println("用户错误:"+userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
}