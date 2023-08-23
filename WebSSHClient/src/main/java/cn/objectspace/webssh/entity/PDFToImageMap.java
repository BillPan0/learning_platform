package cn.objectspace.webssh.entity;

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
@TableName(value = "pdf_to_image_map")
@Data
public class PDFToImageMap {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id，需要自行实现
    @ApiModelProperty(value = "映射条目id")
    int id;

    @ApiModelProperty(value = "pdf id")
    int pdfId;

    @ApiModelProperty(value = "镜像id")
    int imageId;

    @ApiModelProperty(value = "逻辑删除标志")
    int isDeleted;
}
