package cn.objectspace.webssh.kafka;

import cn.objectspace.webssh.entity.AllocatedTerminalInfo;
import cn.objectspace.webssh.mapper.AllocatedTerminalMapper;
import cn.objectspace.webssh.service.impl.SSHClientCommandManageServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Bill
 */
@Component
@Slf4j
public class SSHClientKafkaConsumer {
    @Resource
    SSHClientCommandManageServiceImpl sshClientCommandManageService;
    @Resource
    AllocatedTerminalMapper allocatedTerminalMapper;
    @Resource
    SSHClientKafkaProducer sshClientKafkaProducer;

    /**
     * 处理从消息队列拉取的指令
     * @param record 拉取指令信息
     */
    @KafkaListener(topics = {"${spring.kafka.send.topic}"})
    public void handleMessage(ConsumerRecord<String, String> record){
        log.info("收到发往终端 " + record.key() + " 的指令： " + record.value());
        QueryWrapper<AllocatedTerminalInfo> queryWrapper = new QueryWrapper<>();
        if(allocatedTerminalMapper.exists(queryWrapper)) {
            sshClientCommandManageService.sendToSSHHost(record.key(), record.value());
        } else {
            log.info("终端不存在或已关闭");
            sshClientKafkaProducer.sendMessage(record.key(), "终端不存在或已关闭".getBytes());
        }
    }
}
