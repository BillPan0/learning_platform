package cn.objectspace.webssh.interceptor;

import cn.objectspace.webssh.constant.ConstantPool;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

/**
 * @author Bill
 */
public class WebSocketInterceptor implements HandshakeInterceptor {
    /**
     * Handler处理前调用，参数[serverHttpRequest, serverHttpResponse, webSocketHandler, map]
     * @return boolean
     * @Author: Bill
     * @Date: 2023/7/1
     */
    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest serverHttpRequest, @NotNull ServerHttpResponse serverHttpResponse, @NotNull WebSocketHandler webSocketHandler, @NotNull Map<String, Object> map) {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            //生成一个UUID
            String uuid = UUID.randomUUID().toString().replace("-","");
            //将uuid放到websocketSession中
            map.put(ConstantPool.USER_UUID_KEY, uuid);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void afterHandshake(@NotNull ServerHttpRequest serverHttpRequest, @NotNull ServerHttpResponse serverHttpResponse, @NotNull WebSocketHandler webSocketHandler, Exception e) {

    }
}
