package cn.objectspace.servicemodule.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Bill
 */
@Component
public class KafkaProducer {
    @Resource
    KafkaTemplate<String, String> kafkaTemplate;
    @Value("${spring.kafka.send.topic}")
    String topic;

    /**
     * 发送指令至消息队列
     * @param key 消息键值，此处将终端ip即host作为key，一台终端只会分配给一个会话，保证唯一性
     * @param content 要发送的消息，终端指令
     */
    public void sendMessage(String key, String content){
        kafkaTemplate.send(topic, key, content);
    }

    /**
     * 发送断开终端连接的指令至消息队列，websocket断开时调用
     * @param key 消息键值，此处将终端ip即host作为key，一台终端只会分配给一个会话，保证唯一性
     */
    public void sendDisconnectMessage(String key){
        String content = "{\"operate\":\"command\",\"command\":\"exit\\r\"}";
        kafkaTemplate.send(topic, key, content);
    }
}
