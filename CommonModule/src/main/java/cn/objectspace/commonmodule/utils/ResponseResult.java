package cn.objectspace.commonmodule.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author Bill
 */
@Data
@Component
public class ResponseResult<T> implements Serializable {
    //响应代码
    private int code;
    //响应信息
    private String msg;
    //响应数据
    private T data;

    public ResponseResult(){
        this.code = ResponseStatus.success.getCode();
        this.msg = ResponseStatus.success.getMsg();
    }

    public ResponseResult(T data){
        this.data = data;
        this.code = ResponseStatus.success.getCode();
        this.msg = ResponseStatus.success.getMsg();
    }

    public ResponseResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(T data, int code, String msg){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }


    /**
     * 成功返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param msg 成功信息
     */
    public static <T> ResponseResult<T> success(String msg) {
        return new ResponseResult<>(null, ResponseStatus.success.getCode(), msg);
    }

    /**
     * 成功返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 成功数据
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(data, ResponseStatus.success.getCode(), ResponseStatus.success.getMsg());
    }

    /**
     * 成功返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 成功数据
     * @param msg 成功信息
     */
    public static <T> ResponseResult<T> success(T data, String msg) {
        return new ResponseResult<>(data, ResponseStatus.success.getCode(), msg);
    }


    /**
     * 服务端错误返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param msg 服务端错误信息
     */
    public static <T> ResponseResult<T> fail(String msg) {
        return new ResponseResult<>(null, ResponseStatus.fail.getCode(), msg);
    }

    /**
     * 服务端错误返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 服务端错误数据
     */
    public static <T> ResponseResult<T> fail(T data) {
        return new ResponseResult<>(data, ResponseStatus.fail.getCode(), ResponseStatus.fail.getMsg());
    }

    /**
     * 服务端错误返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 服务端错误数据
     * @param msg 服务端错误信息
     */
    public static <T> ResponseResult<T> fail(T data, String msg) {
        return new ResponseResult<>(data, ResponseStatus.fail.getCode(), msg);
    }


    /**
     * 未授权返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param msg 未授权信息
     */
    public static <T> ResponseResult<T> unauthorized(String msg) {
        return new ResponseResult<>(null, ResponseStatus.authorized_error.getCode(), msg);
    }

    /**
     * 未授权返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 未授权数据
     */
    public static <T> ResponseResult<T> unauthorized(T data) {
        return new ResponseResult<>(data, ResponseStatus.authorized_error.getCode(), ResponseStatus.authorized_error.getMsg());
    }

    /**
     * 未授权返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 未授权数据
     * @param msg 未授权信息
     */
    public static <T> ResponseResult<T> unauthorized(T data, String msg) {
        return new ResponseResult<>(data, ResponseStatus.authorized_error.getCode(), msg);
    }


    /**
     * 禁止访问返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param msg 禁止访问信息
     */
    public static <T> ResponseResult<T> forbidden(String msg) {
        return new ResponseResult<>(null, ResponseStatus.access_error.getCode(), msg);
    }

    /**
     * 禁止访问返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 禁止访问数据
     */
    public static <T> ResponseResult<T> forbidden(T data) {
        return new ResponseResult<>(data, ResponseStatus.access_error.getCode(), ResponseStatus.access_error.getMsg());
    }

    /**
     * 禁止访问返回结果，第一个<T>为方法的泛型，第二个<T>是返回值的泛型，第三个<T>为操作数据的泛型。
     * @param data 禁止访问数据
     * @param msg 禁止访问信息
     */
    public static <T> ResponseResult<T> forbidden(T data, String msg) {
        return new ResponseResult<>(data, ResponseStatus.access_error.getCode(), msg);
    }
}
