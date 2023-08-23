package cn.objectspace.commonmodule.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Bill
 */

@Getter
public enum IsDeletedStatusEnum {
    NORMAL(0, "未删除/禁用"),
    DELETED(1, "已删除/禁用");

    @EnumValue//传入数据库的
    private final Integer value;

    @JsonValue//给前端看的
    private final String remark;

    IsDeletedStatusEnum(int status, String remark) {
        this.value = status;
        this.remark = remark;
    }
}
