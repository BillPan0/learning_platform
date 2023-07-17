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

@Entity
@TableName(value = "user_info")
@Data
public class UserInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id
    @ApiModelProperty(value = "用户id")
    int id;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "用户密码，初始密码以sha256运算后存储")
    String password;

    @ApiModelProperty(value = "创建时间")
    String createTime;

    @ApiModelProperty(value = "修改时间")
    String modifyTime;

    @ApiModelProperty(value = "用户角色")
    String role;

    @ApiModelProperty(value = "逻辑删除/禁用标志位")
    @TableField(value = "is_deleted")
    IsDeletedStatusEnum isDeletedStatusEnum;
}
