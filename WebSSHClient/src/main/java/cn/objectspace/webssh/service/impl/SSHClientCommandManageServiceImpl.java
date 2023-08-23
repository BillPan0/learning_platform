package cn.objectspace.webssh.service.impl;

import cn.objectspace.webssh.service.SSHClientCommandManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Bill
 */
@Slf4j
@Service("sshClientCommandManageService")
public class SSHClientCommandManageServiceImpl implements SSHClientCommandManageService {
    @Resource
    WebSSHServiceImpl webSSHService;

    /**
     * 从SSH获取指令应答发送至消息队列
     * @param host 终端ip
     * @param response 指令
     */
    @Override
    public void responseToMessageQueue(String host, String response) {
    }

    /**
     * 从消息队列拉取到指令并发给SSH对端终端
     * @param host 终端ip
     * @param command 指令应答
     */
    @Override
    public void sendToSSHHost(String host, String command) {
        webSSHService.revHandle(host, command);
    }
}
