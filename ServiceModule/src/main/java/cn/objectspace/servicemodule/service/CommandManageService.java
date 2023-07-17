package cn.objectspace.servicemodule.service;

public interface CommandManageService {
    void responseTOWebsocket(String host, byte[] response);
}
