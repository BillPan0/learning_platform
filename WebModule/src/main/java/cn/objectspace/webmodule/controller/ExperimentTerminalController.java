package cn.objectspace.webmodule.controller;

import cn.objectspace.dubbo.MicroService;
import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.dubbo.dubbointerface.dto.TerminalInfoDTO;
import cn.objectspace.servicemodule.vo.ExperimentVO.GetTerminalInfoVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

/**
 * @author Bill
 */
@RestController
@RequestMapping("/experiment")
@Api(tags = "终端管理")
public class ExperimentTerminalController {
    @DubboReference(timeout = 600000, retries = 0)
    MicroService microService;

    @GetMapping
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("获取新终端信息")
    public ResponseResult<GetTerminalInfoVO> getTerminalInfo(@RequestParam int pdfId){
        try {
            TerminalInfoDTO terminalInfoDTO = microService.requestTerminalInfo(pdfId);
            GetTerminalInfoVO getTerminalInfoVO = new GetTerminalInfoVO();
            //由于进行了端口映射，实际用于连接的host都将是宿主机的ip
            getTerminalInfoVO.setHost(terminalInfoDTO.getHost());
            getTerminalInfoVO.setPort(terminalInfoDTO.getPort());
            getTerminalInfoVO.setUser(terminalInfoDTO.getUser());
            getTerminalInfoVO.setPassword(terminalInfoDTO.getPassword());
            return ResponseResult.success(getTerminalInfoVO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.fail("数据库操作失败，请重试！");
        }
    }
}
