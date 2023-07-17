package cn.objectspace.webssh.mapper;

import cn.objectspace.webssh.entity.PDFToImageMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PDFToImageMapper extends BaseMapper<PDFToImageMap> {
}
