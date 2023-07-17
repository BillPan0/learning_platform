package cn.objectspace.webssh.pojo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import org.springframework.web.socket.WebSocketSession;
/**
* @Description: ssh连接信息
* @Author: NoCortY
* @Date: 2020/3/8
*/
public class SSHConnectInfo {

    private JSch jSch;
    private Channel channel;

    public JSch getjSch() {
        return jSch;
    }

    public void setjSch(JSch jSch) {
        this.jSch = jSch;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
