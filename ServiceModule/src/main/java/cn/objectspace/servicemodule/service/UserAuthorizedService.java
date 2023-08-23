package cn.objectspace.servicemodule.service;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.servicemodule.vo.UserVO.UserLoginVO;
import cn.objectspace.servicemodule.dto.UserLoginDTO;
import cn.objectspace.servicemodule.dto.UserLogoutDTO;
import cn.objectspace.servicemodule.dto.UserPwdChangeDTO;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.servicemodule.vo.UserVO.UserLogoutVO;
import cn.objectspace.servicemodule.vo.UserVO.UserPwdChangeVO;
import cn.objectspace.servicemodule.vo.UserVO.UserRegisterVO;

/**
 * @author Bill
 */
public interface UserAuthorizedService {
    ResponseResult<UserLoginVO> userLogin(UserLoginDTO userLoginDTO);
    ResponseResult<UserRegisterVO> userRegister(UserRegisterDTO userRegisterDTO);
    ResponseResult<UserPwdChangeVO> userPwdChange(UserPwdChangeDTO userPwdChangeDTO);
    ResponseResult<UserLogoutVO> userLogout(UserLogoutDTO userLogoutDTO);
}
