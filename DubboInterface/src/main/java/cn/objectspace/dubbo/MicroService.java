package cn.objectspace.dubbo;

import cn.objectspace.dubbo.dubbointerface.dto.TerminalInfoDTO;

public interface MicroService {
    TerminalInfoDTO requestTerminalInfo (int pdfId) throws Exception;
}