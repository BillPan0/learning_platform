package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.servicemodule.handler.CustomizeWebSocketHandler;
import cn.objectspace.servicemodule.kafka.KafkaProducer;
import cn.objectspace.servicemodule.service.CommandManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("commandManageService")
@Slf4j
public class CommandManageServiceImpl implements CommandManageService {
    @Resource
    CustomizeWebSocketHandler customizeWebSocketHandler;

    /**
     * 从消息队列拉取到指令应答并写入websocket传回
     * @param host 终端ip
     * @param response 指令应答
     */
    @Override
    public void responseToWebsocket(String host, byte[] response) {
        customizeWebSocketHandler.sendCommandResponseMsg(host, response);
    }
}
