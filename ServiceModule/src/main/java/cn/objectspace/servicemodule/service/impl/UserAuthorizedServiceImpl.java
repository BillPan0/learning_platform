package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.daomodule.entity.OnlineUserInfo;
import cn.objectspace.servicemodule.dto.UserLoginDTO;
import cn.objectspace.servicemodule.dto.UserLogoutDTO;
import cn.objectspace.servicemodule.dto.UserPwdChangeDTO;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.commonmodule.utils.ResponseStatus;
import cn.objectspace.servicemodule.vo.UserVO.UserLoginVO;
import cn.objectspace.daomodule.utils.UserOperateUtil;
import cn.objectspace.servicemodule.service.UserAuthorizedService;
import cn.objectspace.servicemodule.vo.UserVO.UserLogoutVO;
import cn.objectspace.servicemodule.vo.UserVO.UserPwdChangeVO;
import cn.objectspace.servicemodule.vo.UserVO.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Service("userAuthorizedService")
@Slf4j
public class UserAuthorizedServiceImpl implements UserAuthorizedService {
    @Resource
    UserOperateUtil userOperateUtil;

    @Override
    public ResponseResult<UserLoginVO> userLogin(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        //判断用户名是否存在
        if(!userOperateUtil.userExistCheck(username)) {
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "用户名不存在！");
        }
        //判断用户名是否被禁用
        if(userOperateUtil.userForbiddenCheck(username)) {
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "该用户已被禁用！");
        }
        // 加密
        String hashPass = DigestUtils.sha256Hex(password);
        if(hashPass.equals(userOperateUtil.reachUserPwdByName(username))) {
            int userId = userOperateUtil.reachUserIdByName(username);
            String token = UUID.randomUUID().toString();
            UserLoginVO userLoginVO = new UserLoginVO();
            userLoginVO.setId(userId);
            userLoginVO.setUsername(username);
            userLoginVO.setToken(token);
            //加入在线用户表
            ResponseResult<OnlineUserInfo> responseResult = userOperateUtil.userOnline(userId, username, token);
            //成功时才返回token数据
            if(responseResult.getCode() == ResponseStatus.success.getCode())
                return new ResponseResult<>(userLoginVO, responseResult.getCode(), responseResult.getMsg());
            return new ResponseResult<>(responseResult.getCode(), responseResult.getMsg());
        }
        else
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "密码错误！");
    }

    @Override
    public ResponseResult<UserRegisterVO> userRegister(UserRegisterDTO userRegisterDTO) {
        String username = userRegisterDTO.getUsername();
        String password = userRegisterDTO.getPassword();
        //判断用户名是否存在
        if(userOperateUtil.userExistCheck(username)) {
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "当前用户名已被使用！");
        }
        if(userRegisterDTO.getRole() == null || userRegisterDTO.getStatus() == null){
            try{
                log.info("新用户注册：id为" + userOperateUtil.saveUser(username, password));
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(),
                        "数据插入过程出现错误，请重新注册！");
            }
        }else {
            try{
                log.info("新用户插入：id为" + userOperateUtil.saveUser(username, password, userRegisterDTO.getRole(), userRegisterDTO.getStatus()));
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(),
                        "数据插入过程出现错误，请重新操作！");
            }
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "注册成功！");
    }

    @Override
    public ResponseResult<UserPwdChangeVO> userPwdChange(UserPwdChangeDTO userPwdChangeDTO) {
        int userId = userPwdChangeDTO.getId();
        String oldPassword = userPwdChangeDTO.getOldPassword();
        String newPassword = userPwdChangeDTO.getPassword();
        ResponseResult<Object> responseResult = userOperateUtil.changePwd(userId, oldPassword, newPassword);
        return new ResponseResult<>(responseResult.getCode(), responseResult.getMsg());
    }

    @Override
    public ResponseResult<UserLogoutVO> userLogout(UserLogoutDTO userLogoutDTO) {
        int userId = userLogoutDTO.getId();
        String token = userLogoutDTO.getToken();
        ResponseResult<Object> responseResult = userOperateUtil.userOffline(userId, token);
        return new ResponseResult<>(responseResult.getCode(), responseResult.getMsg());
    }
}
