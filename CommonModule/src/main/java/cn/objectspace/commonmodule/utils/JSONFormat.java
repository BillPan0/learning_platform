package cn.objectspace.commonmodule.utils;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JSONFormat {
    static public JSON stringTOJSON(String jsonString){
        return (JSON) JSON.parse(jsonString);
    }
}
