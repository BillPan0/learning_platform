package cn.objectspace.webssh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@TableName(value = "image_info")
@Data
public class ImageInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id，需要自行实现
    @ApiModelProperty(value = "镜像id")
    int id;

    @ApiModelProperty(value = "镜像名称")
    String imageName;

    @ApiModelProperty(value = "镜像标签")
    String tag;

    @ApiModelProperty(value = "镜像ssh端口号")
    int port;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "用户密码")
    String password;

    @ApiModelProperty(value = "逻辑删除标志")
    int isDeleted;
}
