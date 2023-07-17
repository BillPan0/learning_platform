package cn.objectspace.servicemodule.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

@Component
@Slf4j
public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, @NotNull ServerHttpResponse response,
                                   @NotNull WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info(attributes.toString());
        // 获得请求参数
        Map<String, String> paramMap = HttpUtil.decodeParamMap(request.getURI().getQuery(), StandardCharsets.UTF_8);
        String host = paramMap.get("host");
        if (StrUtil.isNotBlank(host)) {
            // 放入属性域
            //属性是自己拦截后，从URL中读取出加上的，并传递到handler层伴随session整个生命周期
            attributes.put("host", host);
            System.out.println("握手完成，用户请求终端： " + host + " 握手成功！");
            return true;
        }
        return false;
    }

    @Override
    public void afterHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response,
                               @NotNull WebSocketHandler wsHandler, Exception exception) {

    }
}
