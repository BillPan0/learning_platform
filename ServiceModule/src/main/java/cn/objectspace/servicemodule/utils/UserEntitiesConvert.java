package cn.objectspace.servicemodule.utils;

import cn.objectspace.daomodule.entity.UserInfo;
import cn.objectspace.servicemodule.vo.UserVO.UserRolesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UserEntitiesConvert {
    /**
     * UserInfo类转UserRole
     * @param userInfos userInfo列表
     * @return userRolesVO列表
     */
    public UserRolesVO userInfoTOUserRole(List<UserInfo> userInfos){
        UserRolesVO userRolesVOS = new UserRolesVO();
        for(UserInfo userInfo: userInfos){
            userRolesVOS.addNewUserRole(userInfo.getId(), userInfo.getUsername(),
                    userInfo.getRole(), userInfo.getIsDeletedStatusEnum().getValue());
        }
        userRolesVOS.setTotal(userInfos.size());
        return userRolesVOS;
    }
}
