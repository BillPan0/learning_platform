package cn.objectspace.servicemodule.vo.UserVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginVO {
    @ApiModelProperty(value = "用户id")
    int id;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "用户登录token")
    String token;

    @ApiModelProperty(value = "用户身份级别")
    String role;
}
