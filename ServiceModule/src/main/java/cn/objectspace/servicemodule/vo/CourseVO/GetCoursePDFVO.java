package cn.objectspace.servicemodule.vo.CourseVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Bill
 */
@Data
public class GetCoursePDFVO {
    @ApiModelProperty(value = "pdf文件id")
    int id;

    @ApiModelProperty(value = "pdf名称")
    String pdfName;

    @ApiModelProperty(value = "pdf描述")
    String pdfDes;
}
