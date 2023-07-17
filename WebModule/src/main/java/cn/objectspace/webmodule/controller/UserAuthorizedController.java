package cn.objectspace.webmodule.controller;

import cn.objectspace.servicemodule.vo.UserVO.UserLoginVO;
import cn.objectspace.servicemodule.dto.UserLogoutDTO;
import cn.objectspace.servicemodule.dto.UserPwdChangeDTO;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.servicemodule.service.impl.UserAuthorizedServiceImpl;
import cn.objectspace.servicemodule.dto.UserLoginDTO;
import cn.objectspace.servicemodule.vo.UserVO.UserLogoutVO;
import cn.objectspace.servicemodule.vo.UserVO.UserPwdChangeVO;
import cn.objectspace.servicemodule.vo.UserVO.UserRegisterVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserAuthorizedController {
    @Resource
    UserAuthorizedServiceImpl userAuthorizedService;

    @PostMapping("/register")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("进行用户注册")
    public ResponseResult<UserRegisterVO> userRegisterController(@RequestBody @ApiParam("用户注册信息") UserRegisterDTO userRegisterDTO){
        return userAuthorizedService.userRegister(userRegisterDTO);
    }

    @PostMapping("/login")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("进行用户登录")
    public ResponseResult<UserLoginVO> userLoginController(@RequestBody @ApiParam("用户登录信息") UserLoginDTO userLoginDTO){
         return userAuthorizedService.userLogin(userLoginDTO);
    }

    @PostMapping("/logout")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("进行用户登出")
    public ResponseResult<UserLogoutVO> userLogoutController(@RequestBody @ApiParam("用户登出信息") UserLogoutDTO userLogoutDTO){
        return userAuthorizedService.userLogout(userLogoutDTO);
    }

    @PostMapping("/change-password")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("用户密码修改")
    public ResponseResult<UserPwdChangeVO> userPwdChangeController(@RequestBody @ApiParam("用户登录信息修改") UserPwdChangeDTO userPwdChangeDTO){
        return userAuthorizedService.userPwdChange(userPwdChangeDTO);
    }
}
