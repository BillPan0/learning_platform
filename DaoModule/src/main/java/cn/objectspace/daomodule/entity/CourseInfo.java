package cn.objectspace.daomodule.entity;

import cn.objectspace.commonmodule.enums.IsDeletedStatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Bill
 */
@Entity
@TableName(value = "course_info")
@Data
public class CourseInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id
    @ApiModelProperty(value = "课程id")
    int id;

    @ApiModelProperty(value = "课程名称")
    String courseName;

    @ApiModelProperty(value = "课程描述")
    String courseDes;

    @ApiModelProperty(value = "逻辑删除/禁用标志位")
    int isDeleted;
}
