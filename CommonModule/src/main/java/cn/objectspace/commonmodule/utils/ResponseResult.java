package cn.objectspace.commonmodule.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ResponseResult<T> {
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

}
