package cn.objectspace.webssh.service;

/**
 * @author Bill
 */
public interface SSHClientCommandManageService {
    void responseToMessageQueue(String host, String response);
    void sendToSSHHost(String host, String command);
}
