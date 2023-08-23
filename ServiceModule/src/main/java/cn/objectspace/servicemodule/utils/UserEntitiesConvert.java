package cn.objectspace.servicemodule.utils;

import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.servicemodule.vo.UserVO.UserRolesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Bill
 */
@Component
@Slf4j
public class UserEntitiesConvert {
    /**
     * UserInfo类转UserRole
     * @param userInfos userInfo列表
     * @return userRolesVO列表
     */
    public UserRolesVO userInfoToUserRole(List<UserInfo> userInfos){
        UserRolesVO userRolesVos = new UserRolesVO();
        for(UserInfo userInfo: userInfos){
            userRolesVos.addNewUserRole(userInfo.getId(), userInfo.getUsername(),
                    userInfo.getRole(), userInfo.getIsDeletedStatusEnum().getValue());
        }
        userRolesVos.setTotal(userInfos.size());
        return userRolesVos;
    }
}
