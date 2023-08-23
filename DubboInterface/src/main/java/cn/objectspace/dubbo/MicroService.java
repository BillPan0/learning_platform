package cn.objectspace.dubbo;

import cn.objectspace.dubbo.dubbointerface.dto.TerminalInfoDTO;

/**
 * @author Bill
 */
public interface MicroService {
    TerminalInfoDTO requestTerminalInfo (int pdfId) throws Exception;
}