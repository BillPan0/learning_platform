package cn.objectspace.servicemodule.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginDTO {
    @ApiModelProperty(value = "登录用户名")
    String username;
    @ApiModelProperty(value = "登录密码")
    String password;
}
