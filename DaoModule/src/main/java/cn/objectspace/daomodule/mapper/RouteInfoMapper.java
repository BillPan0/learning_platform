package cn.objectspace.daomodule.mapper;

import cn.objectspace.daomodule.entity.RouteInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Bill
 */
@Repository
@Mapper
public interface RouteInfoMapper extends BaseMapper<RouteInfo> {
}
