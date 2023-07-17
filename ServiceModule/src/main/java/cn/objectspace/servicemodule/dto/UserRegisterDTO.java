package cn.objectspace.servicemodule.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @ApiModelProperty(value = "注册用户名")
    String username;
    @ApiModelProperty(value = "注册密码")
    String password;
    @ApiModelProperty(value = "用户角色")
    String role;
    @ApiModelProperty(value = "用户状态")
    String status;
}
