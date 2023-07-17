package cn.objectspace.servicemodule.service;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.servicemodule.vo.UserVO.*;

import java.util.List;

public interface AccessControlService {
    ResponseResult<UserRolesVO> getUserRoles(int page, int limit);
    ResponseResult<UserRegisterVO> addUser(UserRegisterDTO userRegisterDTO);
    ResponseResult<UserRegisterVO> editUser(UserRegisterDTO userRegisterDTO, int id);
    ResponseResult<UserDeleteVO> deleteUser(int id);
    ResponseResult<UserStatusChangeVO> changeStatus(int id, int status);
}