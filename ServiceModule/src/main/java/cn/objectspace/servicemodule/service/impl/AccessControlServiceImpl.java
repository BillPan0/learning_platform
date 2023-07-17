package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.commonmodule.utils.ResponseStatus;
import cn.objectspace.daomodule.utils.UserOperateUtil;
import cn.objectspace.servicemodule.dto.UserPwdChangeDTO;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.servicemodule.utils.UserEntitiesConvert;
import cn.objectspace.servicemodule.vo.UserVO.*;
import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.daomodule.mapper.UserInfoMapper;
import cn.objectspace.servicemodule.service.AccessControlService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("accessControlService")
@Slf4j
public class AccessControlServiceImpl implements AccessControlService {
    @Resource
    UserEntitiesConvert userEntitiesConvert;
    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    UserAuthorizedServiceImpl userAuthorizedService;
    @Resource
    UserOperateUtil userOperateUtil;

    @Override
    public ResponseResult<UserRolesVO> getUserRoles(int page, int limit) {
        try{
            Page<UserInfo> pageResults = userInfoMapper.selectPage(new Page<>(1, 2), null);
            List<UserInfo> userInfos = pageResults.getRecords();
            return new ResponseResult<>(userEntitiesConvert.userInfoTOUserRole(userInfos));
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "数据库操作失败，请重试！");
        }
    }

    @Override
    public ResponseResult<UserRegisterVO> addUser(UserRegisterDTO userRegisterDTO) {
        return userAuthorizedService.userRegister(userRegisterDTO);
    }

    @Override
    public ResponseResult<UserRegisterVO> editUser(UserRegisterDTO userRegisterDTO, int id) {
        ResponseResult<Object> responseResult = userOperateUtil.editUser(id, userRegisterDTO.getUsername(),
                userRegisterDTO.getPassword(), userRegisterDTO.getRole(), userRegisterDTO.getStatus());
        return new ResponseResult<>(responseResult.getCode(), responseResult.getMsg());
    }

    @Override
    public ResponseResult<UserDeleteVO> deleteUser(int id) {
        ResponseResult<Object> responseResult = userOperateUtil.deleteUser(id);
        return new ResponseResult<>(responseResult.getCode(), responseResult.getMsg());
    }

    @Override
    public ResponseResult<UserStatusChangeVO> changeStatus(int id, int status) {
        ResponseResult<Object> responseResult = userOperateUtil.changeStatus(id, status);
        return new ResponseResult<>(responseResult.getCode(), responseResult.getMsg());
    }
}
