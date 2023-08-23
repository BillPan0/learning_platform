package cn.objectspace.daomodule.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Bill
 */
@Entity
@TableName(value = "pdf_info")
@Data
public class PDFInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id
    @ApiModelProperty(value = "pdf的id")
    int id;

    @ApiModelProperty(value = "pdf名称")
    String pdfName;

    @ApiModelProperty(value = "pdf描述")
    String pdfDes;

    @ApiModelProperty(value = "pdf路径")
    String pdfPath;

    @ApiModelProperty(value = "pdf所属的课程id")
    int courseId;

    @ApiModelProperty(value = "逻辑删除/禁用标志位")
    int isDeleted;
}
