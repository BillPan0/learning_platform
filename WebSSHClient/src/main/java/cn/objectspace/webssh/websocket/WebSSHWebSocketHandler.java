package cn.objectspace.webssh.websocket;

import cn.objectspace.webssh.constant.ConstantPool;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;


/**
* WebSSH的WebSocket处理器
* @Author: Bill
* @Date: 2023/8/22
*/
@Component
public class WebSSHWebSocketHandler implements WebSocketHandler{
    private final Logger logger = LoggerFactory.getLogger(WebSSHWebSocketHandler.class);

    /**
     * 用户连接上WebSocket的回调
     * @param webSocketSession 会话
     * @Author: Bill
     * @Date: 2020/8/22
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        logger.info("用户:{},连接WebSSH", webSocketSession.getAttributes().get(ConstantPool.USER_UUID_KEY));
    }

    /**
     * 收到消息的回调
     * @Author: Bill
     * @Date: 2023/8/22
     */
    @Override
    public void handleMessage(@NotNull WebSocketSession webSocketSession, @NotNull WebSocketMessage<?> webSocketMessage) {

    }

    /**
     * 出现错误的回调
     * @Author: NoCortY
     * @Date: 2020/3/8
     */
    @Override
    public void handleTransportError(@NotNull WebSocketSession webSocketSession, @NotNull Throwable throwable) {
        logger.error("数据传输错误");
    }

    /**
     * 连接关闭的回调
     * @Author: NoCortY
     * @Date: 2020/3/8
     */
    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession webSocketSession, @NotNull CloseStatus closeStatus) {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
