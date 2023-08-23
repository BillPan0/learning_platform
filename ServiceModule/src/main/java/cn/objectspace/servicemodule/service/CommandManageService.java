package cn.objectspace.servicemodule.service;

/**
 * @author Bill
 */
public interface CommandManageService {
    void responseToWebsocket(String host, byte[] response);
}
