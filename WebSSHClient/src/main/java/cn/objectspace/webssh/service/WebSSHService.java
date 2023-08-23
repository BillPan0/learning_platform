package cn.objectspace.webssh.service;

import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSSH的业务逻辑
 * @Author: NoCortY
 * @Date: 2020/3/7
 */
public interface WebSSHService {
    /**
     * 初始化ssh连接
     * @Author: BillPan
     * @Date: 2023/8/16
     */
    void initConnection(String host);

    /**
     * 处理客户段发的数据
     * @Author: BillPan
     * @Date: 2023/8/16
     */
    void revHandle(String host, String buffer);

    /**
     * 关闭连接
     * @Author: BillPan
     * @Date: 2023/8/16
     */
    void close(String host);
}
