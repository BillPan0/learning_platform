package cn.objectspace.servicemodule.vo.CourseVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Bill
 */
@Data
public class GetCourseListVO {
    @ApiModelProperty(value = "课程id")
    int id;

    @ApiModelProperty(value = "课程名称")
    String courseName;

    @ApiModelProperty(value = "课程描述")
    String des;
}
