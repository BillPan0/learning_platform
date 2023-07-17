package cn.objectspace.webssh.service;

public interface SSHClientCommandManageService {
    void responseTOMessageQueue(String host, String response);
    void sendTOSSHHost(String host, String command);
}
