package cn.objectspace.commonmodule.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class SessionManager {
    private static final ConcurrentMap<String, WebSocketSession> sessionConcurrentMap = new ConcurrentHashMap<>();

    public void add(String key, WebSocketSession value) throws IOException {
        //如果已存在则先从库存中清除
        if(sessionConcurrentMap.get(key) != null)
            sessionConcurrentMap.get(key).close();
        sessionConcurrentMap.put(key, value);
    }

    public WebSocketSession remove(String key){
        return sessionConcurrentMap.remove(key);
    }

    public void removeAndClose(String key){
        try {
            WebSocketSession webSocketSession = sessionConcurrentMap.remove(key);
            if(webSocketSession != null && webSocketSession.isOpen()){
                webSocketSession.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public WebSocketSession get(String key) {
        // 获得 session
        return sessionConcurrentMap.get(key);
    }
}
