package cn.objectspace.servicemodule.handler;

import cn.objectspace.commonmodule.utils.SessionManager;
import cn.objectspace.servicemodule.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Bill
 */
@Component
@Slf4j
public class CustomizeWebSocketHandler extends TextWebSocketHandler {
    @Resource
    SessionManager sessionManager;
    @Resource
    KafkaProducer kafkaProducer;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object host = session.getAttributes().get("host");
        if (host != null) {
            // 用户连接成功，放入在线用户缓存
            sessionManager.add(host.toString(), session);
        } else {
            throw new RuntimeException("用户未被分配终端host!");
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 获得客户端传来的消息
        String payload = message.getPayload();
        Object host = session.getAttributes().get("host");
        if(host != null){
            log.info(host + " 终端即将被推送指令：" + payload);
            //推送指令
            kafkaProducer.sendMessage(host.toString(), payload);
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus status){
        Object host = session.getAttributes().get("host");
        if (host != null) {
            // 用户退出，移除缓存
            try {
                WebSocketSession ws = sessionManager.remove(host.toString());
                kafkaProducer.sendDisconnectMessage(host.toString());
                //TODO 删除终端容器记录（WebSSH端已实现）
                log.info("用户与终端： " + host + "断开连接成功！");
                ws.close();
            }catch (Exception e){
                e.printStackTrace();
                log.error("用户与终端： " + host + "断开连接出错！");
            }
        }
    }

    public void sendCommandResponseMsg(String host, byte[] response){
        if (host != null) {
            WebSocketSession session = sessionManager.get(host);
            if(session != null){
                try {
                    if(new String(response).contains("auto-logout")) {
                        session.close();
                    }else {
                        session.sendMessage(new TextMessage(response));
                    }
                }catch (IOException e){
                    e.printStackTrace();
                    log.error("返回指令应答" + new String(response) + "失败");
                }
            }
        }
    }
}
