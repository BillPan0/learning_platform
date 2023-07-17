package cn.objectspace.daomodule.mapper;

import cn.objectspace.daomodule.entity.OnlineUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OnlineUserInfoMapper extends BaseMapper<OnlineUserInfo> {
}
