package cn.objectspace.webmodule.config;

import cn.objectspace.servicemodule.handler.CustomizeWebSocketHandler;
import cn.objectspace.servicemodule.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * websocket
 * 的配置信息
 * @author Bill
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Resource
    CustomizeWebSocketHandler customizeWebSocketHandler;
    @Resource
    WebSocketInterceptor webSocketInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customizeWebSocketHandler, "webssh")
                .addInterceptors(webSocketInterceptor)
                .setAllowedOrigins("*");
    }
}
