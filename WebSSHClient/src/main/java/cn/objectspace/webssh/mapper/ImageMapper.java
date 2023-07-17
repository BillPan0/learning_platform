package cn.objectspace.webssh.mapper;

import cn.objectspace.webssh.entity.ImageInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ImageMapper extends BaseMapper<ImageInfo> {
}
