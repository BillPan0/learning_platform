package cn.objectspace.servicemodule.service.impl;

import cn.objectspace.commonmodule.utils.ResponseResult;
import cn.objectspace.commonmodule.utils.ResponseStatus;
import cn.objectspace.daomodule.entity.CourseInfo;
import cn.objectspace.daomodule.entity.PDFInfo;
import cn.objectspace.daomodule.utils.CourseOperateUtil;
import cn.objectspace.servicemodule.service.CourseAccessService;
import cn.objectspace.servicemodule.vo.CourseVO.GetCourseListVO;
import cn.objectspace.servicemodule.vo.CourseVO.GetCoursePDFVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("courseAccessService")
@Slf4j
public class CourseAccessServiceImpl implements CourseAccessService {
    @Resource
    CourseOperateUtil courseOperateUtil;

    @Override
    public ResponseResult<List<GetCoursePDFVO>> getPDFList(int courseId) {
        try{
            List<PDFInfo> pdfInfos = courseOperateUtil.getPDFListById(courseId);
            List<GetCoursePDFVO> getCoursePDFVOS = new ArrayList<>();
            for(PDFInfo pdfInfo: pdfInfos){
                GetCoursePDFVO getCoursePDFVO = new GetCoursePDFVO();
                getCoursePDFVO.setId(pdfInfo.getId());
                getCoursePDFVO.setPdfName(pdfInfo.getPdfName());
                getCoursePDFVO.setPdfDes(pdfInfo.getPdfDes());
                getCoursePDFVOS.add(getCoursePDFVO);
            }
            return new ResponseResult<>(getCoursePDFVOS);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "数据库操作失败，请重试！");
        }
    }

    @Override
    public ResponseResult<List<GetCourseListVO>> getCourseList() {
        try{
            List<CourseInfo> courseInfos = courseOperateUtil.getCourseList();
            List<GetCourseListVO> getCourseListVOS = new ArrayList<>();
            for(CourseInfo courseInfo: courseInfos){
                GetCourseListVO getCourseListVO = new GetCourseListVO();
                getCourseListVO.setId(courseInfo.getId());
                getCourseListVO.setCourseName(courseInfo.getCourseName());
                getCourseListVO.setDes(courseInfo.getCourseDes());
                getCourseListVOS.add(getCourseListVO);
            }
            return new ResponseResult<>(getCourseListVOS);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(ResponseStatus.uncompleted_error.getCode(), "数据库操作失败，请重试！");
        }
    }
}
