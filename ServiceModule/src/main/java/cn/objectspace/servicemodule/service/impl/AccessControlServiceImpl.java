package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.commonmodule.utils.ResponseStatus;
import cn.objectspace.daomodule.utils.RedisOperateUtil;
import cn.objectspace.daomodule.utils.UserOperateUtil;
import cn.objectspace.servicemodule.dto.UserRegisterDTO;
import cn.objectspace.servicemodule.utils.UserEntitiesConvert;
import cn.objectspace.servicemodule.vo.UserVO.*;
import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.daomodule.mapper.UserInfoMapper;
import cn.objectspace.servicemodule.service.AccessControlService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bill
 */
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
    RedisOperateUtil<String> redisOperateUtil;

    @Autowired
    public AccessControlServiceImpl(RedisOperateUtil<String> redisOperateUtil) {
        this.redisOperateUtil = redisOperateUtil;
    }

    @Override
    public ResponseResult<UserRolesVO> getUserRoles(int page, int limit, String token) {
        //Redis通过token获取用户名
        String id = redisOperateUtil.getKeyValue(token);
        if("管理员".equals(userOperateUtil.getUserRole(Integer.parseInt(id)))){
            try{
                Page<UserInfo> pageResults = userInfoMapper.selectPage(new Page<>(page, limit), null);
                List<UserInfo> userInfos = pageResults.getRecords();
                return ResponseResult.success(userEntitiesConvert.userInfoToUserRole(userInfos));
            }catch (Exception e){
                e.printStackTrace();
                return ResponseResult.fail("数据库操作失败，请重试！");
            }
        }else{
            return ResponseResult.forbidden("当前用户身份级别无法查看该信息！");
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
        if(responseResult.getCode() == ResponseStatus.success.getCode()) {
            return ResponseResult.success(responseResult.getMsg());
        }
        return ResponseResult.fail(responseResult.getMsg());
    }

    @Override
    public ResponseResult<UserDeleteVO> deleteUser(int id) {
        ResponseResult<Object> responseResult = userOperateUtil.deleteUser(id);
        if(responseResult.getCode() == ResponseStatus.success.getCode()) {
            return ResponseResult.success(responseResult.getMsg());
        }
        return ResponseResult.fail(responseResult.getMsg());
    }

    @Override
    public ResponseResult<UserStatusChangeVO> changeStatus(int id, int status) {
        ResponseResult<Object> responseResult = userOperateUtil.changeStatus(id, status);
        if(responseResult.getCode() == ResponseStatus.success.getCode()) {
            return ResponseResult.success(responseResult.getMsg());
        }
        return ResponseResult.fail(responseResult.getMsg());
    }
}
