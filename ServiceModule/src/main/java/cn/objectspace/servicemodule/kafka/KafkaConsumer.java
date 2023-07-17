package cn.objectspace.servicemodule.kafka;

import cn.objectspace.servicemodule.service.impl.CommandManageServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class KafkaConsumer {
    @Resource
    CommandManageServiceImpl commandManageService;
    /**
     * 处理WebSSHClient发回的终端应答
     * @param record 拉取指令应答
     */
    @KafkaListener(topics = {"${spring.kafka.receive.topic}"})
    public void handleMessage(ConsumerRecord<String, byte[]> record){
        log.info("收到来自终端 " + record.key() + " 的指令应答");
        commandManageService.responseTOWebsocket(record.key(), record.value());
    }
}
