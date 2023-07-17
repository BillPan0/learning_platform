package cn.objectspace.commonmodule.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
public enum ResponseStatus {
    success(200,"操作成功"),

    method_error(400,"（错误的请求方法）接口不支持该请求method"),
    authorized_error(401,"（未授权）请求要求身份验证"),
    undefined_error(402,"该状态码是为了将来可能的需求而预留的"),
    access_error(403,"（禁止）服务器拒绝请求"),
    asset_error(404,"（未找到）服务器找不到请求的网页"),
    method_disabled_error(405,"（方法禁用) 禁用请求中指定的方法"),
    timeout_error(408,"（请求超时) 服务器等候请求时发生超时"),
    conflict_error(409,"（冲突）服务器在完成请求时发生冲突。 服务器必须在响应中包含有关冲突的信息"),

    uncompleted_error(450,"自定义操作未完成"),

    fail(500,"服务器异常");

    //响应代码
    private final int code;
    //响应信息
    private final String msg;

    ResponseStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
