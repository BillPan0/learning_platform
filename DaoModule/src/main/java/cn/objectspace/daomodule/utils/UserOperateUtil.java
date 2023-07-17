package cn.objectspace.daomodule.utils;

import cn.objectspace.commonmodule.enums.IsDeletedStatusEnum;
import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.commonmodule.utils.ResponseStatus;
import cn.objectspace.commonmodule.utils.TimeFormat;
import cn.objectspace.daomodule.entity.OnlineUserInfo;
import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.daomodule.mapper.OnlineUserInfoMapper;
import cn.objectspace.daomodule.mapper.UserInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class UserOperateUtil {
    @Resource
    UserInfoMapper userInfoMapper;
    @Resource
    OnlineUserInfoMapper onlineUserInfoMapper;

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 检查结果
     */
    public boolean userExistCheck(String username){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userInfoMapper.exists(queryWrapper);
    }

    /**
     * 检查用户名是否被禁用
     * @param username 用户名
     * @return 检查结果
     */
    public boolean userForbiddenCheck(String username){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return Boolean.parseBoolean(String.valueOf(userInfoMapper.selectOne(queryWrapper).getIsDeletedStatusEnum().getValue()));
    }

    /**
     * 通过用户名获取密码，默认用户存在
     * @param username 用户名
     * @return sha256杂凑后的密码
     */
    public String reachUserPwdByName(String username){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userInfoMapper.selectOne(queryWrapper).getPassword();
    }

    /**
     * 通过用户名获取id，默认用户存在
     * @param username 用户名
     * @return 用户id
     */
    public int reachUserIdByName(String username){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userInfoMapper.selectOne(queryWrapper).getId();
    }

    /**
     * 添加用户
     * @param username 用户名
     * @param password 密码
     * @return 用户信息在数据库中的id
     */
    public int saveUser(String username, String password){
        return saveUser(username, password, "普通用户", "0");
    }

    /**
     * 添加用户
     * @param username 用户名
     * @param password 密码
     * @param role 用户身份
     * @param status 用户状态
     * @return 用户信息在数据库中的id
     */
    public int saveUser(String username, String password, String role, String status){
        // 加密后存储密码
        String storePass = DigestUtils.sha256Hex(password);
        String nowTime = TimeFormat.getLocalDateTimeString(TimeFormat.getLocalDateTime());

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(storePass);
        userInfo.setCreateTime(nowTime);
        userInfo.setModifyTime(nowTime);
        userInfo.setRole(role);
        if(Integer.valueOf(status).equals(IsDeletedStatusEnum.NORMAL.getValue()))
            userInfo.setIsDeletedStatusEnum(IsDeletedStatusEnum.NORMAL);
        else
            userInfo.setIsDeletedStatusEnum(IsDeletedStatusEnum.DELETED);
        return userInfoMapper.insert(userInfo);
    }

    /**
     * 修改密码
     * @param id 用户id
     * @param oldPass 旧密码
     * @param newPass 新的用户密码
     * @return 应答
     */
    public ResponseResult<Object> changePwd(int id, String oldPass, String newPass){
        // 加密后存储密码
        UserInfo userInfo = userInfoMapper.selectById(id);
        //检查旧密码是否正确
        if(!userInfo.getPassword().equals(DigestUtils.sha256Hex(oldPass))){
            return new ResponseResult<>(ResponseStatus.undefined_error.getCode(), "旧密码输入错误，请重新尝试！");
        }
        String nowTime = TimeFormat.getLocalDateTimeString(TimeFormat.getLocalDateTime());
        userInfo.setPassword(DigestUtils.sha256Hex(newPass));
        userInfo.setModifyTime(nowTime);
        try{
            userInfoMapper.updateById(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.undefined_error.getCode(), "数据库操作失败，请重试！");
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "修改密码成功！");
    }

    /**
     * 编辑用户
     * @param username 用户名
     * @param password 密码
     * @param role 用户身份
     * @param status 用户状态
     * @return 应答
     */
    public ResponseResult<Object> editUser(int id, String username, String password, String role, String status){;
        UserInfo userInfo = userInfoMapper.selectById(id);
        String nowTime = TimeFormat.getLocalDateTimeString(TimeFormat.getLocalDateTime());
        String storePass = DigestUtils.sha256Hex(password);
        userInfo.setModifyTime(nowTime);
        userInfo.setUsername(username);
        userInfo.setPassword(storePass);
        userInfo.setRole(role);
        if(Integer.valueOf(status).equals(IsDeletedStatusEnum.NORMAL.getValue()))
            userInfo.setIsDeletedStatusEnum(IsDeletedStatusEnum.NORMAL);
        else
            userInfo.setIsDeletedStatusEnum(IsDeletedStatusEnum.DELETED);
        try{
            userInfoMapper.updateById(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.undefined_error.getCode(), "数据库操作失败，请重试！");
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "编辑用户成功！");
    }

    /**
     * 删除用户
     * @param id 用户id

     * @return 应答
     */
    public ResponseResult<Object> deleteUser(int id){
        try{
            userInfoMapper.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.undefined_error.getCode(), "数据库操作失败，请重试！");
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "删除用户成功！");
    }

    /**
     * 改变用户状态
     * @param id 用户id
     * @param status 用户状态
     * @return 应答
     */
    public ResponseResult<Object> changeStatus(int id, int status){
        UserInfo userInfo = userInfoMapper.selectById(id);
        String nowTime = TimeFormat.getLocalDateTimeString(TimeFormat.getLocalDateTime());
        if(status== IsDeletedStatusEnum.NORMAL.getValue())
            userInfo.setIsDeletedStatusEnum(IsDeletedStatusEnum.NORMAL);
        else
            userInfo.setIsDeletedStatusEnum(IsDeletedStatusEnum.DELETED);
        userInfo.setModifyTime(nowTime);
        try{
            userInfoMapper.updateById(userInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.undefined_error.getCode(), "数据库操作失败，请重试！");
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "改变用户状态成功！");
    }

    /**
     * 检查用户token是否有效，默认用户存在
     * @param token 用户token
     * @return 检查结果
     */
    public boolean checkTokenExpired(String token){
        QueryWrapper<OnlineUserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        if(onlineUserInfoMapper.exists(queryWrapper)){
            LocalDateTime nowTime = TimeFormat.getLocalDateTimeFromString(TimeFormat.getLocalDateTimeString(TimeFormat.getLocalDateTime()));;
            String expireTimeString = onlineUserInfoMapper.selectOne(queryWrapper).getExpireTime();
            LocalDateTime expireTime = TimeFormat.getLocalDateTimeFromString(expireTimeString);
            //已经过期
            return !nowTime.isAfter(expireTime);
        }
        return false;
    }

    /**
     * 用户上线
     * @param id 用户id
     * @param username 用户名
     * @param token 用户token
     * @return 响应
     */
    public ResponseResult<OnlineUserInfo> userOnline(int id, String username, String token){
        OnlineUserInfo onlineUserInfo = new OnlineUserInfo();
        //过期时间为15分钟
        String expireTime = TimeFormat.getLocalDateTimeString(TimeFormat.getLocalDateTime().plusMinutes(15));
        onlineUserInfo.setUserid(id);
        onlineUserInfo.setUsername(username);
        onlineUserInfo.setToken(token);
        onlineUserInfo.setExpireTime(expireTime);
        try{
            QueryWrapper<OnlineUserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userid", id);
            onlineUserInfoMapper.delete(queryWrapper);
            onlineUserInfoMapper.insert(onlineUserInfo);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "数据库操作失败，请重试！");
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "登录成功！");
    }

    /**
     * 通过用户id获取token，拦截器保证了请求用户的token都是有效的
     * @param userId 用户id
     * @return 响应
     */
    public ResponseResult<Object> userOffline(int userId, String token){
        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("token", token);
        try{
            onlineUserInfoMapper.deleteByMap(map);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.undefined_error.getCode(), "数据库操作失败，请重试！");
        }
        return new ResponseResult<>(ResponseStatus.success.getCode(), "注销成功！");
    }
}
