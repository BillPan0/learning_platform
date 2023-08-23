package cn.objectspace.servicemodule.vo.UserVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Bill
 */
@Data
public class UserRolesVO {
    List<UserRole> list;
    int total;

    /**
     * 添加用户权限列表
     * @param id 用户id
     * @param username 用户名
     * @param role 用户角色
     * @param status 用户状态
     */
    public void addNewUserRole(int id, String username, String role, int status){
        if(this.list == null) {
            this.list = new ArrayList<>();
        }
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setUsername(username);
        userRole.setRole(role);
        userRole.setStatus(status);
        this.list.add(userRole);
    }
}

@Data
class UserRole{
    @ApiModelProperty(value = "用户id")
    int id;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "用户角色")
    String role;

    @ApiModelProperty(value = "是否禁用")
    int status;
}
