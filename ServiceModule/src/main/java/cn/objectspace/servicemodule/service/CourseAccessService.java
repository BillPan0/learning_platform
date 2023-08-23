package cn.objectspace.servicemodule.service;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.servicemodule.vo.CourseVO.GetCourseListVO;
import cn.objectspace.servicemodule.vo.CourseVO.GetCoursePDFVO;

import java.util.List;

/**
 * @author Bill
 */
public interface CourseAccessService {
    public ResponseResult<List<GetCoursePDFVO>> getPdfList(int courseId);
    public ResponseResult<List<GetCourseListVO>> getCourseList();
}
