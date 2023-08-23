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
@TableName(value = "online_user_info")
@Data
public class OnlineUserInfo {
    @Id
    @TableId(value = "id", type = IdType.ASSIGN_ID)//雪花算法生成id，需要自行实现
    @ApiModelProperty(value = "在线记录id")
    int id;

    @ApiModelProperty(value = "用户id")
    int userid;

    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "用户登录token")
    String token;

    @ApiModelProperty(value = "登录过期时间")
    String expireTime;
}
