package cn.objectspace.daomodule.utils;

import cn.objectspace.daomodule.entity.CourseInfo;
import cn.objectspace.daomodule.entity.PDFInfo;
import cn.objectspace.daomodule.mapper.CourseInfoMapper;
import cn.objectspace.daomodule.mapper.PDFInfoMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Bill
 */
@Component
public class CourseOperateUtil {
    @Resource
    CourseInfoMapper courseInfoMapper;
    @Resource
    PDFInfoMapper pdfInfoMapper;

    /**
     * 通过课程id获取PDF文件列表
     * @param courseId 课程id
     * @return pdf类列表
     */
    public List<PDFInfo> getPdfListById(int courseId){
        QueryWrapper<PDFInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        return pdfInfoMapper.selectList(queryWrapper);
    }

    /**
     * 获取课程列表
     * @return 课程类列表
     */
    public List<CourseInfo> getCourseList(){
        return courseInfoMapper.selectList(null);
    }
}
