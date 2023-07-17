package cn.objectspace.servicemodule.vo.ExperimentVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetTerminalInfoVO {
    @ApiModelProperty(value = "终端ip信息")
    String host;

    @ApiModelProperty(value = "终端端口信息")
    String port;

    @ApiModelProperty(value = "终端账号信息")
    String user;

    @ApiModelProperty(value = "终端密码信息")
    String password;
}
