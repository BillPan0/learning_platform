package cn.objectspace.webssh.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Bill
 */
@Component
public class SSHClientKafkaProducer {
    @Resource
    KafkaTemplate<String, byte[]> kafkaTemplate;
    @Value("${spring.kafka.receive.topic}")
    String topic;

    /**
     * 发送指令应答至消息队列
     * @param key 消息键值，此处将终端ip即host作为key，一台终端只会分配给一个会话，保证唯一性
     * @param content 要发送的消息，终端应答
     */
    public void sendMessage(String key, byte[] content){
        kafkaTemplate.send(topic, key, content);
    }
}

