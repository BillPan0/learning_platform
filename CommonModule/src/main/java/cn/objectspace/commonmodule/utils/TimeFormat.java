package cn.objectspace.commonmodule.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Bill
 */
@Component
public class TimeFormat {
    static public LocalDateTime getLocalDateTime(){
        return LocalDateTime.now(ZoneId.of("GMT+8")).withNano(0);
    }

    static public String  getLocalDateTimeString(LocalDateTime localDateTime){
        return localDateTime.toString().replace("T", " ");
    }

    static public LocalDateTime  getLocalDateTimeFromString(String localDateTimeString){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(localDateTimeString, dateTimeFormatter);
    }
}
