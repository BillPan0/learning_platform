package cn.objectspace.daomodule.mapper;

import cn.objectspace.daomodule.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Bill
 */
@Repository
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
