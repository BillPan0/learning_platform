package cn.objectspace.webssh.service.impl;

import cn.objectspace.webssh.constant.ConstantPool;
import cn.objectspace.webssh.entity.AllocatedTerminalInfo;
import cn.objectspace.webssh.kafka.SSHClientKafkaProducer;
import cn.objectspace.webssh.mapper.AllocatedTerminalMapper;
import cn.objectspace.webssh.pojo.SSHConnectInfo;
import cn.objectspace.webssh.pojo.WebSSHData;
import cn.objectspace.webssh.service.WebSSHService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* WebSSH业务逻辑实现
* @Author: BillPan
* @Date: 2023/07/11
*/
@Service("webSSHService")
@Slf4j
public class WebSSHServiceImpl implements WebSSHService {
    @Resource
    SSHClientKafkaProducer sshClientKafkaProducer;
    @Resource
    AllocatedTerminalMapper allocatedTerminalMapper;
    //因为进行了端口映射，实际连接的ip总是宿主机ip而非容器名称
    @Value("${spring.provider.host}")
    String providerHost;

    //存放ssh连接信息的map
    private static Map<String, Object> sshMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(WebSSHServiceImpl.class);
    //线程池
    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 初始化连接
     * @param host 终端ip
     * @Author: BillPan
     * @Date: 2023/07/11
     */
    @Override
    public void initConnection(String host) {
        JSch jSch = new JSch();
        SSHConnectInfo sshConnectInfo = new SSHConnectInfo();
        sshConnectInfo.setjSch(jSch);
        //将这个ssh连接信息放入map中
        sshMap.put(host, sshConnectInfo);
    }

    /**
     * 处理客户端发送的数据
     * @param host 终端ip
     * @param buffer 指令
     * @Author: BillPan
     * @Date: 2023/07/11
     */
    @Override
    public void recvHandle(String host, String buffer) {
        ObjectMapper objectMapper = new ObjectMapper();
        WebSSHData webSSHData = null;
        try {
            webSSHData = objectMapper.readValue(buffer, WebSSHData.class);
        } catch (IOException e) {
            logger.error("Json转换异常");
            logger.error("异常信息:{}", e.getMessage());
            return;
        }
        if (ConstantPool.WEBSSH_OPERATE_CONNECT.equals(webSSHData.getOperate())) {
            initConnection(host);
            //找到刚才存储的ssh连接对象
            SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(host);
            //启动线程异步处理
            WebSSHData finalWebSSHData = webSSHData;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        connectToSSH(sshConnectInfo, finalWebSSHData);
                    } catch (JSchException | IOException e) {
                        logger.error("ssh连接异常");
                        logger.error("异常信息:{}", e.getMessage());
                    }
                }
            });
        } else if (ConstantPool.WEBSSH_OPERATE_COMMAND.equals(webSSHData.getOperate())) {
            String command = webSSHData.getCommand();
            SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(host);
            if (sshConnectInfo != null) {
                try {
                    transToSSH(sshConnectInfo.getChannel(), command);
                } catch (IOException e) {
                    logger.error("webssh连接异常");
                    logger.error("异常信息:{}", e.getMessage());
                }
            }
        } else {
            logger.error("不支持的操作");
        }
    }

    @Override
    public void sendMessage(WebSocketSession session, byte[] buffer) throws IOException {
        session.sendMessage(new TextMessage(buffer));
    }

    @Override
    public void close(String host) {
        SSHConnectInfo sshConnectInfo = (SSHConnectInfo) sshMap.get(host);
        if (sshConnectInfo != null) {
            sshMap.remove(host);
        }
        //删除docker终端并删除空镜像
        String deleteTerminalCmd = "cd " + host +
                "&& docker compose down" +
                "&& cd " + host +
                "&& rm -rf " + host +
                "&& docker rmi $(docker images -f \"dangling=true\" -q)";
        Process process;
        try {
            ProcessBuilder checkProcessBuilder = new ProcessBuilder();
            checkProcessBuilder.command("bash", "-c", deleteTerminalCmd);
            process = checkProcessBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用jsch连接终端
     * @param sshConnectInfo ssh连接信息
     * @param webSSHData 接收的指令信息
     * @Author: BillPan
     * @Date: 2023/07/11
     */
    private void connectToSSH(SSHConnectInfo sshConnectInfo, WebSSHData webSSHData) throws JSchException, IOException {
        Session session = null;
        String host = webSSHData.getHost();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //获取jsch的会话
        session = sshConnectInfo.getjSch().getSession(webSSHData.getUsername(), providerHost, webSSHData.getPort());
        session.setConfig(config);
        //设置密码
        session.setPassword(webSSHData.getPassword());
        //连接  超时时间30s
        session.connect(30000);

        //开启shell通道
        Channel channel = session.openChannel("shell");

        //通道连接 超时时间3s
        channel.connect(3000);

        //设置channel
        sshConnectInfo.setChannel(channel);

        //转发消息
        transToSSH(channel, "\r");

        //读取终端返回的信息流
        try (InputStream inputStream = channel.getInputStream()) {
            //循环读取
            byte[] buffer = new byte[1024];
            int i = 0;
            //如果没有数据来，线程会一直阻塞在这个地方等待数据。
            while ((i = inputStream.read(buffer)) != -1) {
                byte[] response = Arrays.copyOfRange(buffer, 0, i);
                sshClientKafkaProducer.sendMessage(host, response);
            }
        } finally {
            //自动断开连接后关闭会话并删除记录
            session.disconnect();
            channel.disconnect();
            QueryWrapper<AllocatedTerminalInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("host", host);
            allocatedTerminalMapper.delete(queryWrapper);
            close(host);
            log.info("关闭终端" + host + "完成！");
        }

    }

    /**
     * 将消息转发到终端
     * @param channel ssh信道
     * @param command 指令
     * @Author: BillPan
     * @Date: 2023/07/11
     */
    private void transToSSH(Channel channel, String command) throws IOException {
        if (channel != null) {
            OutputStream outputStream = channel.getOutputStream();
            outputStream.write(command.getBytes());
            outputStream.flush();
        }
    }
}
