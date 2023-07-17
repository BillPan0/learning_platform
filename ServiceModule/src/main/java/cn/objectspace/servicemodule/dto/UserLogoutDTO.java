package cn.objectspace.servicemodule.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLogoutDTO {
    @ApiModelProperty(value = "用户id")
    int id;
    @ApiModelProperty(value = "用户登录时获取的token")
    String token;
}
