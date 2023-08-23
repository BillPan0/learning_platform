package cn.objectspace.servicemodule.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Bill
 */
@Data
public class UserPwdChangeDTO {
    @ApiModelProperty(value = "用户id")
    int id;
    @ApiModelProperty(value = "用户的旧密码")
    String oldPassword;
    @ApiModelProperty(value = "用户的新密码")
    String password;
}
