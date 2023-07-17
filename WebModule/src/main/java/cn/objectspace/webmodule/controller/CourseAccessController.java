package cn.objectspace.webmodule.controller;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.servicemodule.service.impl.CourseAccessServiceImpl;
import cn.objectspace.servicemodule.vo.CourseVO.GetCourseListVO;
import cn.objectspace.servicemodule.vo.CourseVO.GetCoursePDFVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor.DATE_FORMAT;

@RestController
@RequestMapping("/course")
@Api(tags = "课程信息管理")
public class CourseAccessController {
    @Resource
    CourseAccessServiceImpl courseAccessService;

    @GetMapping("/pdf")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("获取课程PDF列表")
    public ResponseResult<List<GetCoursePDFVO>> getPDFList(@RequestParam int courseId){
        return courseAccessService.getPDFList(courseId);
    }

    @GetMapping("/course-info")
    @JsonFormat(pattern = DATE_FORMAT,timezone="GMT+8")
    @ApiOperation("获取课程列表")
    public ResponseResult<List<GetCourseListVO>> getCourseList(){
        return courseAccessService.getCourseList();
    }
}
