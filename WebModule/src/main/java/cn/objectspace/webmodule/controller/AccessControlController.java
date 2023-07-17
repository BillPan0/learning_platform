package cn.objectspace.webmodule.controller;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.servicemodule.dto.UserLoginDTO;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.servicemodule.service.impl.UserAuthorizedServiceImpl;
import cn.objectspace.servicemodule.vo.UserVO.UserDeleteVO;
import cn.objectspace.servicemodule.vo.UserVO.UserRegisterVO;
import cn.objectspace.servicemodule.vo.UserVO.UserRolesVO;
import cn.objectspace.servicemodule.service.impl.AccessControlServiceImpl;
import cn.objectspace.servicemodule.vo.UserVO.UserStatusChangeVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

@RestController
@RequestMapping("/role")
@Api(tags = "权限管理")
public class AccessControlController {
    @Resource
    AccessControlServiceImpl accessControlService;

    @GetMapping("/roles")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("获取用户权限列表")
    public ResponseResult<UserRolesVO> getUserRolesList(@RequestParam int page, @RequestParam int limit){
        return accessControlService.getUserRoles(page, limit);
    }

    @PostMapping("/add-user")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("新增用户")
    public ResponseResult<UserRegisterVO> addUser(@RequestBody UserRegisterDTO userRegisterDTO){
        return accessControlService.addUser(userRegisterDTO);
    }

    @PostMapping("/edit-user")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("编辑用户")
    public ResponseResult<UserRegisterVO> editUser(@RequestBody UserRegisterDTO userRegisterDTO, @RequestParam int id){
        return accessControlService.editUser(userRegisterDTO, id);
    }

    @DeleteMapping("/delete-user")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("删除用户")
    public ResponseResult<UserDeleteVO> deleteUser(@RequestParam int id){
        return accessControlService.deleteUser(id);
    }

    @GetMapping("/change-status")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("改变用户状态")
    public ResponseResult<UserStatusChangeVO> changeStatus(@RequestParam int id, @RequestParam int status){
        return accessControlService.changeStatus(id, status);
    }
}
