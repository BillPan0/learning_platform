package cn.objectspace.daomodule.entity;

import cn.objectspace.commonmodule.enums.IsDeletedStatusEnum;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@TableName(value = "route_info")
@Data
public class RouteInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id
    @ApiModelProperty(value = "页面路由id")
    int id;

    @ApiModelProperty(value = "页面路由名称")
    String routeName;

    //用01权限控制向量表示，向量的第i位是1则表示用户组i允许访问
    @ApiModelProperty(value = "页面访问权限控制向量")
    String accessVector;
}
