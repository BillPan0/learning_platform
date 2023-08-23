package cn.objectspace.commonmodule.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Bill
 */
@Component
public class SessionManager {
    private static final ConcurrentMap<String, WebSocketSession> SESSION_CONCURRENT_MAP = new ConcurrentHashMap<>();

    public void add(String key, WebSocketSession value) throws IOException {
        //如果已存在则先从库存中清除
        if(SESSION_CONCURRENT_MAP.get(key) != null) {
            SESSION_CONCURRENT_MAP.get(key).close();
        }
        SESSION_CONCURRENT_MAP.put(key, value);
    }

    public WebSocketSession remove(String key){
        return SESSION_CONCURRENT_MAP.remove(key);
    }

    public void removeAndClose(String key){
        try {
            WebSocketSession webSocketSession = SESSION_CONCURRENT_MAP.remove(key);
            if(webSocketSession != null && webSocketSession.isOpen()){
                webSocketSession.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public WebSocketSession get(String key) {
        // 获得 session
        return SESSION_CONCURRENT_MAP.get(key);
    }
}
