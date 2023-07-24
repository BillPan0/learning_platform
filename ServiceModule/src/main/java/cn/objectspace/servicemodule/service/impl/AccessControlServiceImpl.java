package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.commonmodule.utils.ResponseStatus;
import cn.objectspace.daomodule.entity.OnlineUserInfo;
import cn.objectspace.daomodule.mapper.OnlineUserInfoMapper;
import cn.objectspace.daomodule.utils.UserOperateUtil;
import cn.objectspace.servicemodule.dto.UserPwdChangeDTO;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.servicemodule.utils.UserEntitiesConvert;
import cn.objectspace.servicemodule.vo.UserVO.*;
import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.daomodule.mapper.UserInfoMapper;
import cn.objectspace.servicemodule.service.AccessControlService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    OnlineUserInfoMapper onlineUserInfoMapper;
    @Resource
    UserAuthorizedServiceImpl userAuthorizedService;
    @Resource
    UserOperateUtil userOperateUtil;

    @Override
    public ResponseResult<UserRolesVO> getUserRoles(int page, int limit, String token) {
        QueryWrapper<OnlineUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        String username = onlineUserInfoMapper.selectOne(queryWrapper).getUsername();
        if(userOperateUtil.getUserRole(username).equals("管理员")){
            try{
                Page<UserInfo> pageResults = userInfoMapper.selectPage(new Page<>(1, 2), null);
                List<UserInfo> userInfos = pageResults.getRecords();
                return new ResponseResult<>(userEntitiesConvert.userInfoTOUserRole(userInfos));
            }catch (Exception e){
                e.printStackTrace();
                return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "数据库操作失败，请重试！");
            }
        }else{
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "当前用户身份级别无法查看该信息！");
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
