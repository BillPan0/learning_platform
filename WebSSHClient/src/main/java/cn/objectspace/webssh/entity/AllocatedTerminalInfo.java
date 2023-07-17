package cn.objectspace.webssh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@TableName(value = "allocated_host")
@Data
public class AllocatedTerminalInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id，需要自行实现
    @ApiModelProperty(value = "分配记录id")
    int id;

    @ApiModelProperty(value = "终端ip，唯一")
    String host;

    @ApiModelProperty(value = "镜像镜像名称，加上标签后唯一")
    String imageName;

    @ApiModelProperty(value = "镜像标签")
    String tag;

    @ApiModelProperty(value = "端口号")
    String port;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "密码")
    String password;
}
