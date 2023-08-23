package cn.objectspace.webssh.pojo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
/**
* ssh连接信息
* @Author: Bill
* @Date: 2023/8/8
*/
public class SSHConnectInfo {

    private JSch jsch;
    private Channel channel;

    public JSch getJsch() {
        return jsch;
    }

    public void setJsch(JSch jsch) {
        this.jsch = jsch;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
