package cn.objectspace.daomodule.mapper;

import cn.objectspace.daomodule.entity.PDFInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PDFInfoMapper extends BaseMapper<PDFInfo> {
}
