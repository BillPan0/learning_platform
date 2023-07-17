package cn.objectspace.dubbo.dubbointerface.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TerminalInfoVO implements Serializable {
    @ApiModelProperty(value = "终端ip")
    String host;
    @ApiModelProperty(value = "终端端口")
    String port;
    @ApiModelProperty(value = "终端用户")
    String user;
    @ApiModelProperty(value = "终端密码")
    String password;
}
